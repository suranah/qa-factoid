package edu.cmu.lti.yandongl.yahooanswers;

import java.util.Vector; 



public class YAQuestion {
	private String id;

	private String user_id;

	// private Date date;
	private String date;

	private String subject;

	private String content;

	private String top_ten_answer_content;

	private String link;

	private String category;

	private String category_id;

	private Vector<YAAnswer> answers;

	private Vector votesInfo;

	private int rank = -1;

	private float score = 0.0f;

	private int type = -1;

	private int position = -1;

	private long timestamp = 0;

	private int numAnswers = 0;

	private int numComments = 0;

	private String bestAnswerID = "";

	private int stars = 0;

	private long awardTimeStamp = 0l;

	public final static int TYPE_OPEN = 0;

	public final static int TYPE_ANSWERED = 1;

	public final static int TYPE_VOTING = 2;

	public YAQuestion() {
		this.answers = new Vector<YAAnswer>();
		tf_idf = new TF_IDF();
	}

	public YAQuestion(String id) {
		this();
		this.id = id;

	}

	public void setID(String id) {
		this.id = id;
	}

	public String getID() {
		return this.id;
	}

	public void setUserID(String user_id) {
		this.user_id = user_id;
	}

	public String getUserID() {
		return this.user_id;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setTopTenAnsContent(String content) {
		this.top_ten_answer_content = content;
	}

	public String getTopTenAnsContent() {
		return this.top_ten_answer_content;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLink() {
		return this.link;
	}

	public int getNumComments() {
		return this.numComments;
	}

	public void setNumComments(int i) {
		this.numComments = i;
	}

	public int getNumAnswers() {
		return this.numAnswers;
	}

	public void setNumAnswers(int n) {
		// System.out.println("set num ans:" + n);
		this.numAnswers = n;
	}

	public void setBestAnswerID(String id) {
		this.bestAnswerID = id;
	}

	public String getBestAnswerID() {
		return this.bestAnswerID;
	}

	public void setStars(int n) {
		this.stars = n;
	}

	public int getStars() {
		return this.stars;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategoryID(String category) {
		this.category_id = category;
	}

	public String getCategoryID() {
		return this.category_id;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return this.type;
	}

	public void setDateTime(String date) {
		this.date = date;
	}

	public String getDateTime() {
		return this.date;
	}

	public void setTimestamp(long l) {
		this.timestamp = l;
	}

	public long getTimestamp() {
		return this.timestamp;
	}

	public void setAwardTimestamp(long l) {
		this.awardTimeStamp = l;
	}

	public long getAwardTimestamp() {
		return this.awardTimeStamp;
	}

	public void addAnswer(YAAnswer answer) {
		this.answers.add(answer);
	}

	public Vector<YAAnswer> getAllAnswers() {
		return this.answers;
	}

	public void setPosition(int p) {
		this.position = p;
	}

	public int getPosition() {
		return this.position;
	}

	public void setVotesInfo(Vector v) {
		this.votesInfo = v;
	}

	public Vector getVotesInfo() {
		return this.votesInfo;
	}

	public String toString() {
		StringBuffer rc = new StringBuffer();
		rc.append("id:" + id);
		rc.append("\n");
		rc.append("subject:" + subject);
		rc.append("\n");
		rc.append("content:" + content);
		rc.append("\n");
		rc.append("link:" + link);
		rc.append("\n");
		rc.append("category:" + category);
		rc.append("\n");
		rc.append("answers:");
		rc.append("\n");
		for (int i = 0; i < answers.size(); i++) {
			rc.append("   ");
			rc.append("[" + i + "]:" + answers.get(i));
		}
		rc.append("\n");
		return rc.toString();
	}

	public TF_IDF tf_idf = null;
}
