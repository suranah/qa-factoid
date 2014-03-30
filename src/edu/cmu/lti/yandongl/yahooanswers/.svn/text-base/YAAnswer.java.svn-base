package edu.cmu.lti.yandongl.yahooanswers;
import java.util.Date;

public class YAAnswer {
	
	private String question_id;	
	
	private String user_id;
	
	//private Date date;
	private String date;
	
	private long timestamp;
	
	private String content;

//	private int votes_pos;
//
//	private int votes_neg;
	
	private int rank = 0;
	
	private int thumb_up = 0;
	
	private int thumb_down = 0;
	
	public boolean matched = false;
	
	public int year = -1;
	public int month = -1;

	// float score;

	// int position;

	int votes;

	private boolean isBest = false;
	private boolean isBest2 = false;
	
	public void setQuestionID(String q_id) {
		this.question_id = q_id;
	}
	public String getQuestionID() {
	    return this.question_id;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return this.content;
	}

	public void setVotes(int v) {
		this.votes = v;
	}
	
	public int getVotes() {
		return this.votes;
	}

	public void setBest(boolean b) {
		this.isBest = b;
	}
	
	public boolean isBest() {
		return this.isBest;
	}
	
	public void setBest2(boolean b) {
		this.isBest2 = b;
	}
	
	public boolean isBest2() {
		return this.isBest2;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public void setTimestamp(long l) {
		this.timestamp = l;
	}
	
	public long getTimestamp() {
		return this.timestamp;
	}
	
	public void setUserID(String user_id) {
		this.user_id = user_id;
	}
	
	public String getUserID() {
		return this.user_id;
	}
	
	public int getThumbUp() {
	    return this.thumb_up;
	}
	
	public void setThumbUp(int i) {
	    this.thumb_up = i;
	}
	
	public int getThumbDown() {
	    return this.thumb_down;
	}
	
	public void setThumbDown(int i) {
	    this.thumb_down = i;
	}
	
//	public void setVotesPos(int votes_pos) {
//		this.votes_pos = votes_pos;
//	}
	
//	public void setVotesNeg(int votes_neg) {
//		this.votes_neg = votes_neg;
//	}
	
	public void setRank(int p) {
		this.rank = p;
	}
	
	public int getRank() {
		return this.rank;
	}

	public String toString() {
		return content + "/" + isBest;
	}
}
