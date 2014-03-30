package edu.cmu.lti.yandongl.yahooanswers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;


public class QuestionHandler extends DefaultHandler {

	Vector<YAQuestion> questions;

	YAQuestion currentQues = null;

	String charValue = "";

	StringBuffer buf = new StringBuffer();

	boolean inSection = false;
	
	Vector<String> usersid, questionsid;
	
	
	int position = 0;

	public QuestionHandler(Vector<YAQuestion> questions, Vector<String> users, Vector<String> questionsid) {
		this.questions = questions;
		this.usersid = users;
		this.questionsid = questionsid;
	}

	public void startDocument() {
	}

	public void endDocument() {
	}

	public void startElement(String uri, String name, String qName,
			Attributes atts) {
		//Question
		// Subject
		// Content
		// Link
		// Category
		// NumAnswers
		// NumComments
		// ChosenAnswer
		//

		inSection = true;

		if (name.equals("Question")) { // open a new question
			YAQuestion ques = new YAQuestion();
			ques.setPosition(position++);
			questions.add(ques);

			if (currentQues != null) {
				// System.out.println("------Finished one question------");
			}

			currentQues = ques;

			// set id
			currentQues.setID(atts.getValue("id"));
			if(!questionsid.contains(atts.getValue("id")))
				questionsid.add(atts.getValue("id"));
			// set type
			String _type = atts.getValue("type");
			if (_type.equals("Answered")) {
				currentQues.setType(YAQuestion.TYPE_ANSWERED);
			} else if (_type.equals("Open")) {
				currentQues.setType(YAQuestion.TYPE_OPEN);
			} else if (_type.equals("Voting")) {
				currentQues.setType(YAQuestion.TYPE_VOTING);
			} else {
			    System.err.println("other type:" + _type);
			}
		}
		
		if(name.equals("Category")) {
		    currentQues.setCategoryID(atts.getValue("id"));
		}

		// System.out.println("[Start element]:" + qName + "/" + name);
		// int len = atts.getLength();
		// for (int i = 0; i < len; i++) {
		// System.out.println(" [" + i + "]:" + atts.getValue(i));
		// }
	}

	public void endElement(String uri, String name, String qName) {

		inSection = false;

		// System.out.println("[End element]:" + qName + "/" + name);
		// System.out.println("[[Current charValue:]]" + buf.toString());
		// System.out.println("[[]]");

		if (currentQues == null) {
			//ERROR!
			//System.err.println("Null question!");
			return;
		}

		if (name.equals("Subject")) {
			currentQues.setSubject(buf.toString());
		}

		if (name.equals("Content")) {
			currentQues.setContent(buf.toString());			
		}

		if (name.equals("Link")) {
			currentQues.setLink(buf.toString());
		}

		if (name.equals("Category")) {
			currentQues.setCategory(buf.toString());
//			System.out.println("Added category:" + buf.toString());
		}
		
		if (name.equals("UserId")) {
			currentQues.setUserID(buf.toString());			
		}
		
		if (name.equals("Date")) {
			currentQues.setDateTime(buf.toString());
		}
		if (name.equals("Timestamp")) {
		    long l = Long.parseLong(buf.toString());
		    currentQues.setTimestamp(l);
		}
		if (name.equals("NumAnswers")) {
		    int i = Integer.parseInt(buf.toString());
		    currentQues.setNumAnswers(i);
		}
		if(name.equals("ChosenAnswererId")) {//ChosenAnswererId
		    currentQues.setBestAnswerID(buf.toString());
		}
		
	}

	public void characters(char ch[], int start, int length) {

		charValue = (new String(ch, start, length)).trim();

		charValue = charValue.replace("\n", "\\n");
		charValue = charValue.trim();
		charValue = charValue.replace("\\n", " ");
		
		if (inSection) {
			buf.append(charValue);
			// System.out.println("***appened: " + charValue);
		} else {
			buf = new StringBuffer();
		}
	}
}
