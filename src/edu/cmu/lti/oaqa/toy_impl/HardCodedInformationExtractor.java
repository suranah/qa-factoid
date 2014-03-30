package edu.cmu.lti.oaqa.toy_impl;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.lti.oaqa.framework.base.InformationExtractor_ImplBase;
import edu.cmu.lti.oaqa.framework.data.AnswerCandidate;
import edu.cmu.lti.oaqa.framework.data.RetrievalResult;

public class HardCodedInformationExtractor extends InformationExtractor_ImplBase {

  @Override
  public void initialize() {}

  @Override
  public List<AnswerCandidate> extractAnswerCandidates( String answerType, 
          List<String> keyterms, List<RetrievalResult> documents ) {
      List<AnswerCandidate> result = new ArrayList<AnswerCandidate>();
      List<RetrievalResult> retrievalList = new ArrayList<RetrievalResult>();
      retrievalList.add(new RetrievalResult(
              "1",
              0.0,
              "This is a dummy document including answer1 and answer2.",
              1, "dummy query1"));
      result.add(new AnswerCandidate("answer1", retrievalList));
      result.add(new AnswerCandidate("answer2", retrievalList));
      return result;
  }
 
  @Override
  public String getComponentId() {
    return "Hard-Coded Information Extractor";
  }
  
}