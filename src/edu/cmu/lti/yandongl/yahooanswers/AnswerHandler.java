package edu.cmu.lti.yandongl.yahooanswers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class AnswerHandler extends DefaultHandler {

	YAQuestion question;

	YAAnswer currentAnswer;

	String charValue = "";

	StringBuffer buf = new StringBuffer();

	Vector<String> answer_ids;

	Vector<String> users;

	boolean parseUserInfo, parseVotesInfo;

	boolean inSection = false;

	int position = 0;

	boolean inAnswer = false;

	boolean inAnswers = false;

	boolean inQuestion = false;

	boolean inComments = false;

	/**
	 * 
	 * @param question
	 *            question to these answers
	 * @param answer_ids
	 * @param users
	 *            to save user id
	 * @param b1
	 * @param b2
	 */
	// public AnswerHandler(YAQuestion question, Vector<String> answer_ids,
	public AnswerHandler(YAQuestion question, Vector<String> users) {
		this.question = question;
		// this.answer_ids = answer_ids;
		this.users = users;
	}

	public AnswerHandler(YAQuestion question) {
		this.question = question;
	}

	public void startDocument() {
	}

	public void endDocument() {
	}

	// No hierarchy information!!!
	public void startElement(String uri, String name, String qName,
			Attributes atts) {
		// get attributes here
		// get text in endElement()

		// System.out.println("start : " + name);

		inSection = true;

		if (name.equals("Question")) {// the question for all these answers
			inQuestion = true;
			// inAnswer = false;
			String ques_id = atts.getValue("id");
			String ques_type = atts.getValue("type");
			this.question.setID(ques_id);
			if (ques_type.equals("Answered"))
				this.question.setType(YAQuestion.TYPE_ANSWERED);
			else if (ques_type.equals("Open"))
				this.question.setType(YAQuestion.TYPE_OPEN);
			else if (ques_type.equals("Voting"))
				this.question.setType(YAQuestion.TYPE_VOTING);
		} else if (name.equals("Answers")) {
			// inAnswer = true;
			// inQuestion = false;
		} else if (name.equals("Answer")) { // open a new answer
			inAnswer = true;
			// System.out.println("answer on");
			// System.out.println("inAnswer:" + inAnswer);
			currentAnswer = new YAAnswer();
			currentAnswer.setRank(position++);
			currentAnswer.setQuestionID(this.question.getID());
			question.addAnswer(currentAnswer);

			if (currentAnswer != null) {
				// System.out.println("------Finished one answer------");
			}
		} else if (name.equals("Comments")) {
			inComments = true;
		} else {
			// System.out.println("start else:" + name);
		}

		if (inQuestion) {
			if (name.equals("Category")) {
				this.question.setCategoryID(atts.getValue("id"));
			}
		} else {
			// inQuestion = false;
			// inAnswer = false;
		}

		// System.out.println("[Start element]:" + qName + "/" + name);
	}

	public void endElement(String uri, String name, String qName) {

		// System.out.println("end : " + name);

		// !! Answer is included in Question

		inSection = false;

		if (name.equals("Answer")) {
			inAnswer = false;
			// System.out.println("buf:" + buf.toString());
			// System.out.println("answer off:");
			// System.out.println("inAnswer:" + inAnswer);
		} else if (name.equals("Question")) {
			inQuestion = false;
		} else if (name.equals("Comments")) {
			inComments = false;
		} else
		// if(name.equals("Content") && inAnswer) {
		// System.out.println("ans content:" + buf.toString());
		// }

		// if (currentAnswer == null) { // Null answer
		// // ERROR
		// return;
		// }
		if (name.equals("Category")) {
			if (inQuestion)
				this.question.setCategory(buf.toString());
		} else if (name.equals("NumAnswers")) {
			if (inQuestion) {
				// System.out.println("num ans in q:"+buf.toString());
				this.question.setNumAnswers(Integer.parseInt(buf.toString()));
			}
			// else {
			// System.out.println("num ans:"+buf.toString());
			// }
		} else if (name.equals("ChosenAnswererId")) {
			if (inQuestion) {
				this.question.setBestAnswerID(buf.toString());
				currentAnswer.setUserID(buf.toString());
			}
		} else if (name.equals("Link")) {
			if (inQuestion)
				this.question.setLink(buf.toString());
		} else if (name.equals("Subject")) {
			if (inQuestion) {
				String ques_subj = buf.toString();
				this.question.setSubject(ques_subj);
			}
		} else if (name.equals("Content")) {
			if (inComments) {

			} else if (inAnswer) {
				currentAnswer.setContent(buf.toString());
			} else if (inQuestion) {
				this.question.setContent(buf.toString());
			}

			// System.out.println("content:" + currentAnswer.getContent());
			// String s = currentAnswer.getContent();
			// if (s.length() > 80)
			// System.out.println(currentAnswer.getContent().substring(0,
			// 80));
			// else
			// System.out.println(s);
			// System.out.println("content:" + buf.toString());
		} else if (name.equals("Best")) {
			if (inAnswer)
				if (buf.toString().equals("")) { // Not best

				} else {
					// System.out.println("best answser!!!");
					int v = Integer.parseInt(buf.toString());
					currentAnswer.setVotes(v);
					currentAnswer.setBest2(true);
				}
		} else if (name.equals("Date")) {
			if (inComments) {

			} else if (inAnswer) {
				currentAnswer.setDate(buf.toString());
			} else if (inQuestion) {
				this.question.setDateTime(buf.toString());
			}
		} else if (name.equals("UserId")) {
			if (inComments) {

			} else if (inAnswer) {
				currentAnswer.setUserID(buf.toString());
			} else if (inQuestion) {
				this.question.setUserID(buf.toString());
			}
			// System.out.println(buf.toString());
			// answer_ids.add(buf.toString());
		} else if (name.equals("Timestamp")) {
			long l = 0l;
			try {
				l = Long.parseLong(buf.toString());
			} catch (NumberFormatException ex) {
				this.question.setTimestamp(0l);
				// ex.printStackTrace();
				System.err.println("Can't parse this timestamp!");
			}
			if (inComments) {

			} else if (inAnswer) {
				currentAnswer.setTimestamp(l);
			} else if (inQuestion) {
				this.question.setTimestamp(l);
			}
		} else if (name.equals("ChosenAnswer")) {
			currentAnswer = new YAAnswer();
			currentAnswer.setBest(true);
			currentAnswer.setRank(position++);
			currentAnswer.setQuestionID(this.question.getID());
			question.addAnswer(currentAnswer);
			currentAnswer.setContent(buf.toString());
			if (inQuestion) {
				// System.out.println("chosenans:" + buf.toString());
				// System.out.println();
			}
			// } else if (name.equals("ChosenAnswererId")) {// ChosenAnswererId
			// if (inQuestion) {
			// System.out.println("answerer:" + buf.toString());
			// System.out.println();
			// } else {
			// System.out.println("abc");
			// }
		} else if (name.equals("ChosenAnswerTimestamp")) {
			long l = 0l;
			try {
				l = Long.parseLong(buf.toString());
			} catch (NumberFormatException ex) {
				this.currentAnswer.setTimestamp(0l);
				// ex.printStackTrace();
				System.err.println("Can't parse this award timestamp!");
			}
			if (inQuestion) {// best ans for this question
				currentAnswer.setTimestamp(l);
				// System.out.println("timestamp:" + buf.toString());
				// System.out.println();
			}
		} else if (name.equals("ChosenAnswerAwardTimestamp")) {
			if (inQuestion) {
				long l = 0l;
				try {
					l = Long.parseLong(buf.toString());
				} catch (NumberFormatException ex) {
					this.question.setAwardTimestamp(0l);
//					ex.printStackTrace();
				}
				this.question.setAwardTimestamp(l);
				// System.out.println("timestamp:" + buf.toString());
				// System.out.println();
			}
		} else if (name.equals("NumComments")) {
			if (inQuestion) {
				// System.out.println(buf.toString());
				this.question.setNumComments(Integer.parseInt(buf.toString()));
			}
		} else {
			// System.out.println("end name:" + name);
		}

	}

	public void characters(char ch[], int start, int length) {

		charValue = new String(ch, start, length);
		// System.out.println("charValue:" + charValue);
		// if(charValue.contains("\n")) {
		// System.out.println("contains \\n");
		// }

		charValue = charValue.replace("\n", "\\n");
		charValue = charValue.trim();
		charValue = charValue.replace("\\n", " ");

		if (inSection) {
			buf.append(charValue);
		} else {
			buf = new StringBuffer();
		}
	}
}