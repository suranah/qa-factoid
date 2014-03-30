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

public class YahooAnswerGenerator extends AnswerGenerator_ImplBase {

  private static final Logger LOGGER = Logger.getLogger(LogUtil.getInvokingClassName());
  
  @Override
  public void initialize() {}

  /**
   * Generates a list of candidates by considering the Yahoo! answer results. 
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
            //LOGGER.info("  Ret Res* " + c.getText() + " " + c.getYahoo() + " " + c.getDocID() + " " + c.getRank());

        }
        }
      //LOGGER.info("  Ret Res* " + c.getText() + " " + c.getYahoo() + " " + c.getDocID());
      //LOGGER.info("  Ret Res* Cand " + candidate.getText());
    }
    	
    //updateCandidateScoreNaive(candidate, keyterms);
    if (isYahoo == false)
    	//updateCandidateScoreBump(candidate, keyterms, 3.0);
    	result.setScore(0.0);
        else
        {
            //LOGGER.info("  Ret Res Yahoo " + candidate.getText());
            updateCandidateYahoo(candidate, keyterms, 1.5, result, keyphrases);
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
            //LOGGER.info("  Ret Res* " + c.getText() + " " + c.getYahoo() + " " + c.getDocID() + " " + c.getRank());
          }
        	}
          //LOGGER.info("  Ret Res* " + c.getText() + " " + c.getYahoo() + " "+ c.getDocID());
          //LOGGER.info("  Ret Res Cand " + candidate.getText());
          
        }
        // Increase score
        //updateCandidateScoreNaive(candidate, keyterms);
        
        if (isYahoo == false)
            //updateCandidateScoreBump(candidate, keyterms, 3.0);
        {}
            else
            {
                //LOGGER.info("  Ret Res Yahoo " + candidate.getText());
                updateCandidateYahoo(candidate, keyterms, 1.5, result, keyphrases);
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
    LOGGER.info("  Final yahoo answers: "+(sb.length()>0 ? sb : "No answers found."));
    return finalAnswers;
  }
   
  
  @Override
  public String getComponentId() {
    return "Yahoo Answer Generator";
  }
  
  public void updateCandidateYahoo(AnswerCandidate candidate, List<String> keyterms, double bump, AnswerCandidate result, List<String> keyphrases)
  {
		//candidate.setScore(1.00);
	  double initScore = 0.01;
      List<RetrievalResult> cResults = candidate
      .getRetrievalResults();
  for (RetrievalResult c : cResults) {
  	if (c.getDocID() != null)
	{
    if (c.getYahoo() == true || c.getDocID().equals("doc_id"))
    {
        String retrieveText = c.getText().toLowerCase();
        int rank = c.getRank();
        initScore = initScore + 1/ (double) (1+rank);
        //System.out.println(c.getText() + " " + c.getRank() + "  " + initScore);
    }
    }


  }
  
  if (candidate.getText().startsWith("200"))
	  initScore = 0.01;
  
  result.setScore(result.getScore() + initScore);
  }
  
}