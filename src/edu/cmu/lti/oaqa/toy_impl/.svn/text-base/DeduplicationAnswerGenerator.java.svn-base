package edu.cmu.lti.oaqa.toy_impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.cmu.lti.oaqa.framework.base.AnswerGenerator_ImplBase;
import edu.cmu.lti.oaqa.framework.data.AnswerCandidate;

public class DeduplicationAnswerGenerator extends AnswerGenerator_ImplBase {

  @Override
  public void initialize() {}

  @Override
  public List<AnswerCandidate> generateFinalAnswers( String answerType, 
          List<String> keyterms, List<AnswerCandidate> answerCandidates ) {
    Set<String> history = new HashSet<String>();
    List<AnswerCandidate> finalAnswers = new ArrayList<AnswerCandidate>(answerCandidates.size());
    for ( AnswerCandidate ac : finalAnswers ) {
      if ( history.contains(ac.getText()) ) continue;
      history.add(ac.getText());
      finalAnswers.add( ac );
    }
    return finalAnswers;
  }
 
  @Override
  public String getComponentId() {
    return "Deduplication Answer Generator";
  }
}