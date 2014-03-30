package edu.cmu.lti.yandongl.yahooanswers;
import java.util.Date;


public class User {
	
	private String id;
	private String username;
	private String membersince;
	private int total_points;
	private int total_answers;
	private int best_answers;
	private int questions_asked;
	private int questions_resolved;
	private int stars_received;
	private boolean hasPhoto;
	private boolean isTopContributor;
	private boolean isOfficial;
	
	public User() {
//		this.id = "N/A";
//		this.username = "N/A";
		this.membersince = "";
		this.total_points = -1;
		this.total_answers = -1;
		this.best_answers = -1;
		this.questions_asked = -1;
		this.questions_resolved = -1;
		this.stars_received = -1;
	}
	
	public User(String id) {
		this.id = id;
		//this.username = username;
	}	
	
	
	//public void setUsername(String username) {
		//this.username = username;
	//}
	
	public String getID() {
		return this.id;
	}
	
	public void setTotalPoints(int total_points) {
		this.total_points = total_points;
	}
	
	public int getTotalPoints() {
		return this.total_points;
	}
	
	public void setMembersince(String membersince) {
		this.membersince = membersince;
	}
	public String getMembersince() {
	    return this.membersince;
	}
	
	public void setTotalAnswers(int total_answers) {
		this.total_answers = total_answers;
	}
	
	public int getTotalAnswers() {
		return this.total_answers;		
	}
	
	public void setBestAnswers(int best_answers) {
		this.best_answers = best_answers;
	}
	
	public int getBestAnswers() {
		return this.best_answers;
	}
	
	public void setQuestionsAsked(int questions_asked) {
		this.questions_asked = questions_asked;
	}
	
	public int getQuestionAsked() {
		return this.questions_asked;
	}
	
	public void setQuestionsResolved(int questions_resolved) {
		this.questions_resolved = questions_resolved;
	}
	
	public int getQuestionsResolved() {
		return this.questions_resolved;
	}
	
	public void setStarsResolved(int stars_received) {
		this.stars_received = stars_received;
	}
	
	public int getStarsReceived() {
		return this.stars_received;
	}
	
	public boolean ifHasPhoto() {
	    return this.hasPhoto;
	}
	public void setHasPhoto(boolean b) {
	    this.hasPhoto = b;
	}
	
	public boolean isTopContributor() {
	    return this.isTopContributor;
	}
	public void setTopContributor(boolean b) {
	    this.isTopContributor = b;
	}
	public boolean isCelebrity() {
	    return this.isOfficial;
	}
	public void setCelebrity(boolean b) {
	    this.isOfficial = b;
	}
	
	public String toString() {
		return id+"/" + this.total_points;
	}

}
