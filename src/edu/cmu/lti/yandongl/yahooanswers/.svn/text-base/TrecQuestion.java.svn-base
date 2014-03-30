package edu.cmu.lti.yandongl.yahooanswers;

import java.util.HashMap;
import java.util.Vector;

public class TrecQuestion {
	
	private String id;
	private String type;
	private String text;
	private String target;
	
	private Vector<YAQuestion> v = new Vector<YAQuestion>();
	private HashMap<String, YAQuestion> map_yqid2yq = new HashMap<String, YAQuestion>();
	
	
	public TrecQuestion(String id) {
		this.id = id;
	}
	
	public void setID(String id) {
	    this.id = id;
	    type = "no_type";
	    text = "no_text";
	    target = "";
	}
	public String getID() {
		return this.id;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getTarget() {
		return this.target;
	}
	
	public void addYAQuestion(YAQuestion q) {
	    v.add(q);
	    map_yqid2yq.put(q.getID(), q);
	}
	
	public YAQuestion getFromYQId(String y_q_id) {
	    return this.map_yqid2yq.get(y_q_id);
	}
	
	public Vector<YAQuestion> getAllYAQuestions() {
	    return this.v;
	}

	

}
