package edu.cmu.lti.oaqa.stable_impl;

import info.ephyra.nlp.SnowballStemmer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import edu.cmu.lti.oaqa.framework.IComponent;
import edu.cmu.lti.oaqa.framework.JCasManipulator;
import edu.cmu.lti.oaqa.framework.ViewManager;
import edu.cmu.lti.oaqa.framework.data.AnswerCandidate;
import edu.cmu.lti.oaqa.util.LogUtil;

/**
 * Kevin's Filtering Combo 04/05/2010 approach to Candidate Filtering.
 * 
 * 
 * @author Kevin Dela Rosa
 *
 */
public class ComboCandidateFilter implements IComponent {

  private static final Logger LOGGER = Logger.getLogger(LogUtil.getInvokingClassName());
  
  /** Word lists used in filtering. */
  private static final String STOPWORD_LIST_PATH = "res/experimental/stoplists/answer_candidate_stoplist2.txt";

  private static final String FUNCTION_WORD_PATH = "res/ephyra/indices/functionwords_nonumbers";

  private static ArrayList<String> answerCandidateStopWords;

  @Override
  public void initialize() {
    // Load stop words
    loadCandidateAnswerStopwordList(true);
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
      String answerType = JCasManipulator.loadAnswerType(questionView);
      List<String> keyterms = JCasManipulator.loadKeyterms(questionView);
      JCas candidateView = ViewManager.getCandidateView(jcas);
      List<AnswerCandidate> candidates = JCasManipulator.loadAnswerCandidates(candidateView);
      int originalSize = candidates.size();
      List<AnswerCandidate> filteredCandidates = filterAnswerCandidatesCombo4(candidates,
              answerType, keyterms);
      int filteredSize = filteredCandidates.size();

      JCasManipulator.storeCandidates(candidateView, filteredCandidates);
      LOGGER.info("  Filtered " + (originalSize - filteredSize) + " candidates.");
    } catch (Exception e) {
      throw new AnalysisEngineProcessException(e);
    }
  }

  /**
   * Loads the answer candidate stop word list
   * 
   * @param addFunctionWords
   *          if true, function words are added to stop word list
   */
  private void loadCandidateAnswerStopwordList(boolean addFunctionWords) {
    answerCandidateStopWords = new ArrayList<String>(0);
    try {
      BufferedReader cin = new BufferedReader(new FileReader(STOPWORD_LIST_PATH));
      String line = null;
      while ((line = cin.readLine()) != null) {
        answerCandidateStopWords.add(line.toLowerCase().trim());
      }

      if (addFunctionWords) {
        cin = new BufferedReader(new FileReader(FUNCTION_WORD_PATH));
        while ((line = cin.readLine()) != null) {
          answerCandidateStopWords.add(line.toLowerCase().trim());
        }
      }
    } catch (FileNotFoundException e) {
      // Stop word list not found, forget about it
      System.err.println("Could not load answer candidate stopword list.");
    } catch (IOException e) {
      System.err.println("Could not load answer candidate stopword list.");
    }
  }

  /**
   * Filters answer candidates, based on best filtering technique on 04/05/2010 (stop word /function
   * word and key term repetition in answer removal)
   * 
   * @param originalCandidates
   *          list of original answer candidates
   * @param answerType
   *          answer type
   * @param keyterms
   *          list of keywords
   * @return list of filtered answer candidates
   */
  private List<AnswerCandidate> filterAnswerCandidatesCombo4(
          List<AnswerCandidate> originalCandidates, String answerType, List<String> keyterms) {
    List<AnswerCandidate> candidates = new ArrayList<AnswerCandidate>(0);

    // Initialize stemmer
    SnowballStemmer.create();

    // Create stop word list if necessary
    if (answerCandidateStopWords == null) {
      loadCandidateAnswerStopwordList(true);
    }

    ArrayList<String> normKeyTerms = new ArrayList<String>(0);
    for (String keyTerm : keyterms) {
      normKeyTerms.add(SnowballStemmer.stem(keyTerm.toLowerCase()));
    }

    // Loop through answer candidates, filtering those that fail the various
    // criteria tests
    for (AnswerCandidate candidate : originalCandidates) {
      // Filter if candidate is only stop words
      boolean onlyContainsStopWords = true;
      String ne = candidate.getText();
      String[] ne_tokens = ne.trim().split("\\s+");
      for (String ne_token : ne_tokens) {
        if (!answerCandidateStopWords.contains(ne_token.toLowerCase())) {
          onlyContainsStopWords = false;
          break;
        }
      }
      if (onlyContainsStopWords) {
        // skip answer candidate
        continue;
      }

      // Filter answers consisting of at least one key term (uses
      // stemming)
      boolean containsKeyTerm = false;
      String stemmed = SnowballStemmer.stemAllTokens(candidate.getText().trim().toLowerCase());
      ne_tokens = stemmed.split("\\s+");

      for (String ne_token : ne_tokens) {
        if (normKeyTerms.contains(ne_token)) {
          containsKeyTerm = true;
          break;
        }
      }
      if (!containsKeyTerm) {
        // Survived filtering, add to candidate list
        candidates.add(candidate);
      }
    }
    return candidates;
  }

  @Override
  public String getComponentId() {
    return "Combo Answer Candidate Filter";
  }
}
