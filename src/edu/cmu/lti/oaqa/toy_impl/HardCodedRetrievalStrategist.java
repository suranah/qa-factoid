package edu.cmu.lti.oaqa.toy_impl;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.lti.oaqa.framework.base.RetrievalStrategist_ImplBase;
import edu.cmu.lti.oaqa.framework.data.RetrievalResult;

public class HardCodedRetrievalStrategist extends RetrievalStrategist_ImplBase {

  @Override
  public void initialize() {}

  @Override
  public List<RetrievalResult> retrieveDocuments(List<String> keyterms) {
    List<RetrievalResult> result = new ArrayList<RetrievalResult>();
    result.add(new RetrievalResult(
            "1",
            0.0,
            "This is a dummy document including answer1.",
            1, "dummy query1"));
    result.add(new RetrievalResult(
            "1",
            0.0,
            "This is a dummy document including answer2.",
            2, "dummy query1"));
    return result;
  }
  
  @Override
  public String getComponentId() {
    return "Hard-Coded Retrieval Strategist";
  }
}