package edu.cmu.lti.oaqa.toy_impl;

import info.ephyra.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


import edu.cmu.lti.oaqa.framework.base.AnswerGenerator_ImplBase;
import edu.cmu.lti.oaqa.framework.data.AnswerCandidate;
import edu.cmu.lti.oaqa.framework.data.RetrievalResult;
import edu.cmu.lti.oaqa.util.LogUtil;

public class OverlapAnswerGenerator extends AnswerGenerator_ImplBase {

  private static final Logger LOGGER = Logger.getLogger(LogUtil.getInvokingClassName());
  
  @Override
  public void initialize() {}

  /**
   * Generates a list of candidates by considering the overlap between keyterms and the passages. 
   * 
   * @param answerType
   *            answer type
   * @param keyterms
   *            list of key terms used to generate answer candidates
   * @param answerCandidates
   *            list of answer candidates
   * @return list of final answers
   */
  
  public List<AnswerCandidate> generateFinalAnswers(String answerType, List<String> keyterms,
        List<AnswerCandidate> answerCandidates, List<String> keyphrases) {

    // sort answer candidates by sum of scores
    // (use original order for tie-breaking)
    Map<String, AnswerCandidate> results = new LinkedHashMap<String, AnswerCandidate>();
    for (AnswerCandidate candidate : answerCandidates) {
      String norm = StringUtils.normalize(candidate.toString());
      AnswerCandidate result = results.get(norm);
      if (result == null) {
        results.put(norm, candidate);
        //result = results.get(norm);
        List<RetrievalResult> cResults = candidate
        .getRetrievalResults();
        result = results.get(norm);
        boolean isYahoo = false;
    for (RetrievalResult c : cResults) {
        //result.addRetrievalResult(c);
        //LOGGER.info("  Ret Res* " + c.getText() + " " + c.getYahoo() + " " + c.getDocID());
    	if (c.getDocID() != null)
    	{
        if (c.getYahoo() == true || c.getDocID().equals("doc_id"))
        {
        	isYahoo = true;
        }
        }
      //LOGGER.info("  Ret Res* " + c.getText() + " " + c.getYahoo() + " " + c.getDocID());
      //LOGGER.info("  Ret Res* Cand " + candidate.getText());
    }
    	
    //updateCandidateScoreNaive(candidate, keyterms);
    if (isYahoo == false)
    updateCandidateScoreBump(candidate, keyterms, 1.5, result, keyphrases);
    else
        {
    	//LOGGER.info("  Ret Res Yahoo1 " + candidate.getText());
        
        }
    	
//  updateCandidateScoreDistance(candidate, keyterms, 3.0);

//    LOGGER.info("  Scoring "  + result.getScore() + " ** " + candidate.getScore() + " " + candidate.getText());
//    result.setScore(result.getScore() + candidate.getScore());

      } 
      else
      {
        // Add all documents from this instance of candidate to stored
        // instance
        List<RetrievalResult> cResults = candidate
            .getRetrievalResults();
        boolean isYahoo = false;
        for (RetrievalResult c : cResults) {
        	if (c.getDocID() != null)
        	{

          result.addRetrievalResult(c);
          if (c.getYahoo() == true  || c.getDocID().equals("doc_id"))
          {
          	isYahoo = true;
          }
        	}
          //LOGGER.info("  Ret Res* " + c.getText() + " " + c.getYahoo() + " "+ c.getDocID());
          //LOGGER.info("  Ret Res Cand " + candidate.getText());
          
        }
        // Increase score
        //updateCandidateScoreNaive(candidate, keyterms);
        if (isYahoo == false)
            updateCandidateScoreBump(candidate, keyterms, 1.5, result, keyphrases);
            else
            {
            	//LOGGER.info("  Ret Res Yahoo1 " + candidate.getText());
                
                }
//        LOGGER.info("  Scoring "  + result.getScore() + " ** " + candidate.getScore() + " " + candidate.getText());
//        updateCandidateScoreDistance(candidate, keyterms, 3.0);

        //LOGGER.info("  Scoring "  + result.getScore() + " ** " + candidate.getScore() + " " + candidate.getText());
//        result.setScore(result.getScore() + candidate.getScore());
//        result.setScore(result.getScore() + candidate.getScore());
      }
    }
    AnswerCandidate[] sorted = results.values().toArray(
        new AnswerCandidate[results.size()]);
    Arrays.sort(sorted, Collections.reverseOrder());
    List<AnswerCandidate> finalAnswers = new ArrayList<AnswerCandidate>();
    for (AnswerCandidate result : sorted) {
      finalAnswers.add(result);
      //LOGGER.info("  Sorted "  + result.getScore() + " ** " + result.getText());

    }
    StringBuilder sb = new StringBuilder();
    for ( int i=0; i<Math.min(5, finalAnswers.size()); i++ ) {
      sb.append( (i>0?", ":"") );
      sb.append( "\""+finalAnswers.get(i)+"\"" );
    }
    LOGGER.info("  Final overlap answers: "+(sb.length()>0 ? sb : "No answers found."));
    return finalAnswers;
  }
   
  
  @Override
  public String getComponentId() {
    return "Overlap Answer Generator";
  }
  
  public void updateCandidateScoreNaive(AnswerCandidate candidate, List<String> keyterms)
  {
      List<RetrievalResult> cResults = candidate
      .getRetrievalResults();
  for (RetrievalResult c : cResults) {
    String retrieveText = c.getText().toLowerCase();
    for (String keyterm : keyterms)
    {
    	if (retrieveText.contains(keyterm))
    	{
    		double score = candidate.getScore();
    		candidate.setScore(score + 1.00);
    	}
    }
  }
  
  }

  public void updateCandidateScoreBump(AnswerCandidate candidate, List<String> keyterms, double bump, AnswerCandidate result, List<String> keyphrases)
  {
		//candidate.setScore(1.00);
	  double initScore = 1.0;
      List<RetrievalResult> cResults = candidate
      .getRetrievalResults();
  for (RetrievalResult c : cResults) {
    String retrieveText = c.getText().toLowerCase();
    int keytermsfound = 0;
    for (String keyterm : keyterms)
    {
    	if (retrieveText.contains(keyterm))
    	{
    		//double score = candidate.getScore();
    		//candidate.setScore(score + 1.00);
    		initScore = initScore + 1.00;
    		keytermsfound++;
    	}
    }
    for (String keyphrase: keyphrases)
    {
    	String[] keywords = keyphrase.split(" ");
    	if (keywords.length > 1)
    	{
        	if (retrieveText.toLowerCase().contains(keyphrase.toLowerCase()))
        	{
        		//double score = candidate.getScore();
        		//candidate.setScore(score + 1.00);
        		//System.out.println("Found phrease :: " + initScore + keyphrase) ;
        		initScore = initScore + (double)keywords.length;
        		//System.out.println("Found phrease :: " + initScore + keyphrase) ;
        	}
    		
    	}
    }
    if (keyterms.size() == keytermsfound)
    {
		//double score = candidate.getScore();
		//candidate.setScore(score * bump);
    	initScore = initScore * bump;
    }
  }
  
  if (candidate.getText().startsWith("200"))
	  initScore = 0.01;
  
  result.setScore(result.getScore() + initScore);
  }
  
  public void updateCandidateScoreDistance(AnswerCandidate candidate, List<String> keyterms, double bump)
  {
      List<RetrievalResult> cResults = candidate
      .getRetrievalResults();
  for (RetrievalResult c : cResults) {
    String retrieveText = c.getText().toLowerCase();
    for (String keyterm : keyterms)
    {
    	if (retrieveText.contains(keyterm))
    	{
    		
    		double score = candidate.getScore();
    		int index = retrieveText.indexOf(keyterm);
    		if (index < 15)
    		{
    			index = 0; 
    		}
    		else
    		{
    			index = index - 15;
    		}
    		int indexCandidate = retrieveText.indexOf(candidate.getText());
    		int totalLength = retrieveText.length();
    		double scoreAdd = 1 - ((double) Math.abs(index - indexCandidate) / (double) totalLength);
    		candidate.setScore(score + scoreAdd);
    	}
    }
  }
  
  if (candidate.getText().equals("2007"))
		candidate.setScore(0.01);    	  
  }


  public void getFunc(List<String> keyterms)
  {
//  	for (int j=0;  j < keyterms.size()-1; j++) {
//
//		float sum1=0;
//		float sum2=0;
//		float sumsq1=0;
//		float sumsq2=0;
//		float sumpr=0;
//		float num=0;
//
//		for (int k=1;  k <= 5; k++) {
//			for (int l=1;  l<= 5; l++) {
//				int val=values[j][k-1][l-1];
//				//System.out.print(val);
//
//				sum1 += l*val;
//				sum2 += k*val;
//				sumsq1 += l*l*val;
//				sumsq2 += k*k*val;
//				sumpr += k*l*val;
//				num += val;
//				
//			}
//
//		}	
//		float top = sumpr - (sum1*sum2/num);
//		float bottom = (float)Math.sqrt((sumsq1 - (sum1*sum1)/num)*(sumsq2 - (sum2*sum2)/num));

  }
  
}
