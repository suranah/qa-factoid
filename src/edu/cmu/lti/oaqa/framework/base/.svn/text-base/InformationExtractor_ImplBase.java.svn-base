package edu.cmu.lti.oaqa.framework.base;

import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import edu.cmu.lti.oaqa.framework.IComponent;
import edu.cmu.lti.oaqa.framework.JCasManipulator;
import edu.cmu.lti.oaqa.framework.ViewManager;
import edu.cmu.lti.oaqa.framework.data.AnswerCandidate;
import edu.cmu.lti.oaqa.framework.data.RetrievalResult;

public abstract class InformationExtractor_ImplBase implements IComponent {

  public abstract void initialize();
  
  public abstract List<AnswerCandidate> extractAnswerCandidates(String answerType,
          List<String> keyterms, List<RetrievalResult> documents);
  
  public void process(JCas jcas) throws AnalysisEngineProcessException {
    try {
      JCas questionView = ViewManager.getQuestionView(jcas);
      JCas documentView = ViewManager.getDocumentView(jcas);
      String answerType = JCasManipulator.loadAnswerType(questionView);
      List<String> keyterms = JCasManipulator.loadKeyterms(questionView);
      List<RetrievalResult> documents = JCasManipulator.loadDocuments(documentView);
      List<AnswerCandidate> answers = extractAnswerCandidates(
          answerType, keyterms, documents);
      JCas candidateView = ViewManager.getCandidateView(jcas);
      JCasManipulator.storeCandidates(candidateView, answers);
    } catch (Exception e) {
      throw new AnalysisEngineProcessException(e);
    }
  }
  
}
