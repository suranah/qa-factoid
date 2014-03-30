package edu.cmu.lti.oaqa.toy_impl;

import java.util.Arrays;
import java.util.List;

import edu.cmu.lti.oaqa.framework.base.KeytermExtractor_ImplBase;

public class SimplestKeytermExtractor extends KeytermExtractor_ImplBase {

  @Override
  public void initialize() {}

  @Override
  public List<String> extractKeyterms(String question) {
    String[] terms = question.split("[,\\.\"\\s\\p{P}]+");
    return Arrays.asList(terms);
  }
  
  @Override
  public String getComponentId() {
    return "Simplest Keyterm Extractor";
  }
}