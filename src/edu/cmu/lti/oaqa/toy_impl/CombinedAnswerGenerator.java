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

public class CombinedAnswerGenerator extends AnswerGenerator_ImplBase {

  private static final Logger LOGGER = Logger.getLogger(LogUtil.getInvokingClassName());
  
  private static WebReinforcementAnswerGenerator webGen = new WebReinforcementAnswerGenerator();

  private static OverlapAnswerGenerator overlapGen = new OverlapAnswerGenerator();
  
  private static YahooAnswerGenerator yahooGen = new YahooAnswerGenerator();

  @Override
  public void initialize() {}

  /**
   * Generates a final list of answers by combining different approaches.
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
	  
	  double factorWeb = .07;
	  
	  double overlapMaxScore = 100.0;
	  double webMaxScore = 100.0;
	  double yahooMaxScore = 100.0;

	  List<AnswerCandidate> overlapAnswers = overlapGen.generateFinalAnswers(answerType, keyterms, answerCandidates, keyphrases);
	  if (overlapAnswers.size() > 0) 
		  overlapMaxScore = overlapAnswers.get(0).getScore();

	  Map<String, AnswerCandidate> results = new LinkedHashMap<String, AnswerCandidate>();

	  for (AnswerCandidate candidate: overlapAnswers)
	  {
	      String norm = StringUtils.normalize(candidate.toString());
	      double scoreNorm = candidate.getScore() / overlapMaxScore;
	        //LOGGER.info("  Overlap0: " + candidate.getText() + "   " + candidate.getScore() + "  " + scoreNorm);
	      AnswerCandidate candPut = new AnswerCandidate(candidate.getText(), candidate.getRetrievalResults());
	      candPut.setScore(scoreNorm);
	      results.put(norm, candPut);
	  }
	  
	  List<AnswerCandidate> webAnswers = webGen.generateFinalAnswers(answerType, keyterms, answerCandidates, keyphrases);
	  if (webAnswers.size() > 0)
	  webMaxScore = webAnswers.get(0).getScore();


//      LOGGER.info("  Overlap Max: " + overlapMaxScore);
	  
	  for (AnswerCandidate candidate: webAnswers)
	  {
	      String norm = StringUtils.normalize(candidate.toString());
	      double scoreNorm = candidate.getScore() / webMaxScore;
	      
	       // LOGGER.info("  Web: " + candidate.getText() + "   " + candidate.getScore());
	      
	      AnswerCandidate result = results.get(norm);
	      
	      if (result == null)
	      {
		      AnswerCandidate candPut = new AnswerCandidate(candidate.getText(), candidate.getRetrievalResults());
		      candPut.setScore(scoreNorm);
		      results.put(norm, candPut);
	      }
	      else
	      {
	    	 //double scoreTotal = factor * result.getScore() + (1-factor) * scoreNorm;
	    	 double scoreTotal =  result.getScore() + scoreNorm;

		   //     LOGGER.info("  CombInit: " + result.getText() + "   " + result.getScore());
	    	 result.setScore(scoreTotal);
	    	 results.put(norm, result);
		   //     LOGGER.info("  Comb: " + result.getText() + "   " + result.getScore());

	      }
	     }

	  List<AnswerCandidate> yahooAnswers = yahooGen.generateFinalAnswers(answerType, keyterms, answerCandidates, keyphrases);

	  if (yahooAnswers.size() > 0)
		  yahooMaxScore = yahooAnswers.get(0).getScore();
	  if (yahooMaxScore == 0.0)
	  {
		  yahooMaxScore = 1.0;
	  }

	  
	  for (AnswerCandidate candidate: yahooAnswers)
	  {
	      String norm = StringUtils.normalize(candidate.toString());
	      double scoreNorm = candidate.getScore() / yahooMaxScore;
	      
	       // LOGGER.info("  Web: " + candidate.getText() + "   " + candidate.getScore());
	      
	      AnswerCandidate result = results.get(norm);
	      
	      if (result == null)
	      {
		      AnswerCandidate candPut = new AnswerCandidate(candidate.getText(), candidate.getRetrievalResults());
		      candPut.setScore(scoreNorm);
		      results.put(norm, candPut);
	      }
	      else
	      {
	    	 //double scoreTotal = factor * result.getScore() + (1-factor) * scoreNorm;
	    	 double scoreTotal =   result.getScore() + scoreNorm;

		   //     LOGGER.info("  CombInit: " + result.getText() + "   " + result.getScore());
	    	 result.setScore(scoreTotal);
	    	 results.put(norm, result);
		   //     LOGGER.info("  Comb: " + result.getText() + "   " + result.getScore());

	      }
	     }

	    AnswerCandidate[] sorted = results.values().toArray(
	            new AnswerCandidate[results.size()]);
	        Arrays.sort(sorted, Collections.reverseOrder());
	        List<AnswerCandidate> finalAnswers = new ArrayList<AnswerCandidate>();
	        for (AnswerCandidate result : sorted) {
	          finalAnswers.add(result);
	        }
	        StringBuilder sb = new StringBuilder();
	        for ( int i=0; i<Math.min(5, finalAnswers.size()); i++ ) {
	          sb.append( (i>0?", ":"") );
	          sb.append( "\""+finalAnswers.get(i)+"\"" );
	        }
	        LOGGER.info("  Final top answers: "+(sb.length()>0 ? sb : "No answers found."));
	  
    return finalAnswers;
  }
   
  
  @Override
  public String getComponentId() {
    return "Combined Answer Generator";
  }
  
  
}
