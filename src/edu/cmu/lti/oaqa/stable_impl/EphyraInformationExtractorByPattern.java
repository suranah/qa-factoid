package edu.cmu.lti.oaqa.stable_impl;

import info.ephyra.answerselection.filters.AnswerPatternFilter;
import info.ephyra.answerselection.filters.ScoreSorterFilter;
import info.ephyra.querygeneration.Query;
import info.ephyra.questionanalysis.QuestionInterpretation;
import info.ephyra.questionanalysis.QuestionInterpreter;
import info.ephyra.questionanalysis.QuestionNormalizer;
import info.ephyra.search.Result;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import edu.cmu.lti.oaqa.framework.IComponent;
import edu.cmu.lti.oaqa.framework.JCasManipulator;
import edu.cmu.lti.oaqa.framework.ViewManager;
import edu.cmu.lti.oaqa.framework.data.AnswerCandidate;
import edu.cmu.lti.oaqa.framework.data.RetrievalResult;
import edu.cmu.lti.oaqa.util.LogUtil;

public class EphyraInformationExtractorByPattern implements IComponent {

  private static final Logger LOGGER = Logger.getLogger(LogUtil.getInvokingClassName());
  
  @Override
  public void initialize() {
    // question patterns
    if (!QuestionInterpreter
        .loadPatterns("res/ephyra/patternlearning/questionpatterns/"))
      System.err.println("Could not load question patterns.");

    // answer patterns
    if (!AnswerPatternFilter
//        .loadPatterns("res/ephyra/patternlearning/espressobase/"))
//        .loadPatterns("res/ephyra/patternlearning/espressoexpanded/"))
//        .loadPatterns("res/ephyra/patternlearning/mixed/"))
        .loadPatterns("res/ephyra/patternlearning/answerpatterns/"))
      System.err.println("Could not load answer patterns.");
  }
  
  @Override
  /**
   * Extracts candidates using surface patterns.
   * 
   * @param documents
   *            list of search results
   * @param candidates
   *            list of answer candidates
   */
  public void process(JCas jcas) throws AnalysisEngineProcessException {
    try {
      JCas questionView = ViewManager.getQuestionView(jcas);
      JCas documentView = ViewManager.getDocumentView(jcas);
      String questionText = questionView.getDocumentText();

      List<RetrievalResult> documents = JCasManipulator.loadDocuments(documentView);
      
      List<AnswerCandidate> candidates = new ArrayList<AnswerCandidate>();
      
      QuestionInterpretation[] qis = generateQuestionInterpretations(questionText);
      // create Ephyra result objects
      Result[] results = new Result[qis.length * documents.size()];
      int i = 0;
      for (QuestionInterpretation qi : qis) {
        for (RetrievalResult doc : documents) {
          Query query = new Query(null);
          query.addExtractionTechnique(AnswerPatternFilter.ID);
          query.setInterpretation(qi);
          results[i] = new Result(doc.getText(), query);
          results[i++].setScore(Float.NEGATIVE_INFINITY);
        }
      }
  
      // apply Ephyra's pattern-based answer extractor
      Result[] allResults = (new AnswerPatternFilter()).apply(results);
      allResults = (new ScoreSorterFilter()).apply(allResults);
      // scores = new Hashtable<String, Float>();
      for (int j = 0; j < allResults.length - results.length; j++) {
        String candidate = allResults[j].getAnswer();
        float score = allResults[j].getScore();
  
        // Added following lines, because answer candidate stores list of
        // documents
        List<RetrievalResult> resultList = new ArrayList<RetrievalResult>();
        resultList.add(new RetrievalResult(allResults[j]));
  
        // Add answer candidate
        AnswerCandidate ac = new AnswerCandidate(candidate, resultList);
        ac.setScore(score);
        candidates.add(ac);
      }
      JCas candidateView = ViewManager.getCandidateView(jcas);
      JCasManipulator.storeCandidates(candidateView, candidates);
      LOGGER.info("  Extracted "+candidates.size()+" candidates.");
    } catch (Exception e) {
      throw new AnalysisEngineProcessException(e);
    }
  }
  
  /**
   * Generates question interpretations for candidate extraction using surface
   * patterns.
   * 
   * @param question
   *            question string
   */
  private static QuestionInterpretation[] generateQuestionInterpretations(String question) {
    String qn = QuestionNormalizer.normalize(question);
    String stemmed = QuestionNormalizer.stemVerbsAndNouns(qn);
    QuestionInterpretation[] qis = QuestionInterpreter.interpret(qn, stemmed);

    StringBuilder sb = new StringBuilder();
    sb.append("  Question interpretation(s): ");
    for (QuestionInterpretation qi : qis) {
      sb.append(qi.toString().trim().replaceAll("\n", ", ")+" ");
    }
    if ( qis.length==0 ) sb.append( "N/A" );
    LOGGER.info(sb.toString());
    return qis;
  }

  @Override
  public String getComponentId() {
    return "Ephyra Information Extractor by Pattern";
  }
}
