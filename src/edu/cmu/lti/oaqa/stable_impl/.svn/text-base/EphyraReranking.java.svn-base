package edu.cmu.lti.oaqa.stable_impl;

import info.ephyra.nlp.NETagger;
import info.ephyra.nlp.OpenNLP;
import info.ephyra.nlp.SnowballStemmer;
import info.ephyra.nlp.StanfordNeTagger;
import info.ephyra.nlp.StanfordParser;
import info.ephyra.nlp.indices.FunctionWords;
import info.ephyra.nlp.indices.IrregularVerbs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.cmu.lti.oaqa.framework.data.RetrievalResult;

public class EphyraReranking {
	final int PRINT_DOCUMENT_NUM=5;
	  
	class DocumentScore implements Comparable<DocumentScore>{
		public int originalRank;
		public double keyTermScore;
		public double cohesiveScore;
		public double weightedScore;
		public RetrievalResult document;
		@Override
		public int compareTo(DocumentScore o) {
			// TODO Auto-generated method stub
			if(this.keyTermScore-o.keyTermScore!=0)
			    return (int) (this.keyTermScore-o.keyTermScore);
			else
				return (int) (this.cohesiveScore-o.cohesiveScore);
		}
	}
	public List<RetrievalResult> rerank(List<String> keyTerms, List<RetrievalResult> documents) {
		List<RetrievalResult> rerankedDocument=new ArrayList<RetrievalResult>();
		List<DocumentScore> documentScore=new ArrayList<DocumentScore>();
		for(int i=0;i<documents.size();i++){
			DocumentScore documentScoreItem=new DocumentScore();
			documentScoreItem.originalRank=i;
			documentScoreItem.document=documents.get(i);
			documentScore.add(documentScoreItem);
		}
		
		int docCounter = 0;
		int maxDocId = 0;
		int maxSentenceId = 0;
		double maxSentenceScore = -1;
		double maxDocScore = -1;
		String currentBestSentence = "";
		ArrayList<String> matchBestSentences = new ArrayList<String>();
		ArrayList<double[]> highPriorPassage = new ArrayList<double[]>();
		ArrayList<int[]> highPriorPassageIndex = new ArrayList<int[]>();

		for (RetrievalResult result : documents) {
			// 2.1 Normalizing Passages
			String text = result.getText();
			
			// System.out.println(text);

			// 2.2 Tokenizing and Sentence Split.......
			String[] sentences =  OpenNLP.sentDetect(text);
			// Weight
			// passages-------------------------------------------------------
			// 1.Which passages contain the most keyTerms?
			// 2.Which passages are more important?

			String distinctWords = "";

			for (int j = 0; j < keyTerms.size() ; j++) {
				for (int k = 0; k < keyTerms.get(j).length(); k++) {
					if (!distinctWords.contains(keyTerms.get(j).substring(k,
							k + 1))) {
						distinctWords += keyTerms.get(j).substring(k, k + 1);
					}
				}
			}
			double inSentenceMaxScore=0;
			for (int i = 0; i <sentences.length; i++) {

				ArrayList<String> distinctTerms = new ArrayList<String>();
				
				double sentenceScore = 0;
				for (int k = 0; k < distinctWords.length(); k++) {
					if (sentences[i]
							.contains(distinctWords.substring(k, k + 1))) {
						sentenceScore++;
						distinctTerms.add(distinctWords.substring(k, k + 1));
					}
				}
				
				if (sentenceScore > maxSentenceScore) {
					maxSentenceScore = sentenceScore;
					maxDocScore = maxSentenceScore;
					maxDocId = docCounter;
					maxSentenceId = i;
					currentBestSentence = sentences[i];
					highPriorPassage = new ArrayList<double[]>();
					highPriorPassageIndex = new ArrayList<int[]>();
					matchBestSentences = new ArrayList<String>();					
				}
				
				double[] currentScore = new double[2];
				currentScore[0] = sentenceScore;
				currentScore[1] = computeCohesiveScore(distinctTerms,
						sentences[i]);
							
				if(inSentenceMaxScore<=sentenceScore){
					inSentenceMaxScore=sentenceScore;
					documentScore.get(docCounter).keyTermScore=inSentenceMaxScore;
					documentScore.get(docCounter).cohesiveScore=currentScore[1];
				}
				
				if (sentenceScore == maxSentenceScore) {
					int[] docSenIndex = new int[2];
					docSenIndex[0] = docCounter;
					docSenIndex[1] = i;

					highPriorPassage.add(currentScore);
					highPriorPassageIndex.add(docSenIndex);
					matchBestSentences.add(sentences[i]);
				}
			}
			// -------------------------------------------------------
			docCounter++;
		}

		double minCohesionScore = 0;
		if (highPriorPassage.size() > 0) {
			minCohesionScore = highPriorPassage.get(0)[1];
		}
		for (int i = 0; i < highPriorPassageIndex.size(); i++) {
			if (highPriorPassage.get(i)[1] < minCohesionScore) {
				minCohesionScore = highPriorPassage.get(i)[1];
				maxDocId = highPriorPassageIndex.get(i)[0];
				maxSentenceId = highPriorPassageIndex.get(i)[1];
				currentBestSentence = matchBestSentences.get(i);
			}
		}
		Collections.sort(documentScore, Collections.reverseOrder()); 
		for(int i=0;i<documentScore.size();i++){
			rerankedDocument.add(documentScore.get(i).document);
		}
		for (int count=0;count < PRINT_DOCUMENT_NUM;count++)
		{
			System.out.println(count+"	DocID: " + rerankedDocument.get(count).getDocID() + "\t" + rerankedDocument.get(count).getText());
		}
		return rerankedDocument;
	}
	
	private double computeCohesiveScore(ArrayList<String> distinctTerms,
			String sentence) {
		double score = 0;
		int startPos = 0;
		for (int i = 0; i < distinctTerms.size(); i++) {
			startPos = sentence.indexOf(distinctTerms.get(i));
			for (int j = 0; j < distinctTerms.size(); j++) {
				int wordPos = sentence.indexOf(distinctTerms.get(j));
				score += Math.abs(wordPos - startPos);
			}
		}
		if (distinctTerms.size() == 0) {
			score = 100000;
		} else {
			score = score
					/ (2 * (distinctTerms.size() - 0.999) * (distinctTerms
							.size() - 0.999));
		}
		return score;
	}
}

