package edu.cmu.lti.oaqa.toy_impl;

import edu.cmu.lti.oaqa.framework.base.AnswerTypeExtractor_ImplBase;

public class RamdomAnswerTypeExtractor extends AnswerTypeExtractor_ImplBase {

  @Override
  public void initialize() {}

  @Override
  public String extractAnswerType(String question) {
    return Math.random()>0.5 ? "PERSON" : "LOCATION";
  }
  
  @Override
  public String getComponentId() {
    return "Random Answer Type Extractor";
  }

}