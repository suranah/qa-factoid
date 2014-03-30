package edu.cmu.lti.oaqa.framework.data;

import org.oaqa.model.SearchResult;
import info.ephyra.search.Result;

/**
 * Class that represents a search result
 * 
 * @author Kevin Dela Rosa
 *@version 2010-03-02
 */
public class RetrievalResult {
	private String docID;
	private double score;
	private String text;
	private int rank;
	private String queryString;
	private boolean isYahoo = false;

	/**
	 * Constructor
	 * 
	 * @param docID
	 *            document identifier
	 * @param score
	 *            score assigned to search result
	 * @param text
	 *            text of result
	 * @param rank
	 *            rank of result
	 * @param queryString
	 *            query string that was used to find result
	 */
	public RetrievalResult(String docID, double score, String text, int rank,
			String queryString) {
		this.docID = docID;
		this.score = score;
		this.text = text;
		this.rank = rank;
		this.queryString = queryString;
	}

	/**
	 * Constructor
	 * 
	 * @param result
	 *            result object
	 */
	public RetrievalResult(Result result) {
		this.docID = result.getDocID();
		this.score = result.getScore();
		this.text = result.getAnswer();
		this.rank = result.getHitPos();
		this.queryString = result.getQuery().getQueryString();
	}

	/**
	 * Constructor
	 * 
	 * @param result
	 *            search result model object
	 */
	public RetrievalResult(SearchResult result) {
		this.docID = result.getUri();
		this.score = result.getScore();
		this.text = result.getText();
		this.rank = result.getRank();
		this.queryString = result.getQueryString();
	}

	public boolean equals(Object o) {
		try {
			// Attempt to cast object and compare docIDs (used by
			// Collections.contains operator, only compares document ID)
			return ((RetrievalResult) o).getDocID().equals(this.docID);
		} catch (Exception e) {
			return false;
		}
	}

	public String toString() {
		return this.text;
	}

	/**
	 * Get query string
	 * 
	 * @return query string used to retrieve document
	 */
	public String getQueryString() {
		return this.queryString;
	}

	/**
	 * Get document ID
	 * 
	 * @return document identifier
	 */
	public String getDocID() {
		return this.docID;
	}

	/**
	 * Get result score
	 * 
	 * @return result score
	 */
	public double getScore() {
		return this.score;
	}

	/**
	 * Get text of document/search result
	 * 
	 * @return text of result
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Get rank of document/search result
	 * 
	 * @return result rank
	 */
	public int getRank() {
		return this.rank;
	}
	
	public boolean getYahoo()
	{
		return this.isYahoo;
	}
	
	public void setYahoo(boolean yahoo)
	{
		this.isYahoo = yahoo;
	}
}
