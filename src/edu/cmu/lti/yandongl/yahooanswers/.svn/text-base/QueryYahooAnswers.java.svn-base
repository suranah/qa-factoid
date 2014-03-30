package edu.cmu.lti.yandongl.yahooanswers;


import java.awt.image.ImagingOpException;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class QueryYahooAnswers {

	// ArrayList<Question> questions = new ArrayList<Question>(); // to store
	// all the questions

	QuestionHandler handlerQuestion; // SAX handler for question XML

	AnswerHandler handlerAnswer; // SAX handler for answer XML

	String appID = "blQR0drV34FWfQnBP255TXnhbiE11O2dpOQcZp4ej8NkmncMzgFhnjKjSA1l56Q8Ks4pzw--";

	// "JQRNT_zV34GhV69jpmdizYvnoOezfbGUIOmuVkhQnKoBISPdY1qENNZUhTrTS0yhbXR48g--";

	// "blQR0drV34FWfQnBP255TXnhbiE11O2dpOQcZp4ej8NkmncMzgFhnjKjSA1l56Q8Ks4pzw--";
	// //id used for Yahoo Answer

	// String numResults = "5"; //

	// get a list of related questions by keywords
	String urlQuestionSearch = "http://answers.yahooapis.com/AnswersService/V1/questionSearch?appid="
			+ appID
			+ "&query=_query_&search_in=_searchin_&results=_results_&start=_start_";

	// get the list of answers to a question by its id
	String urlAnswerSearch = "http://answers.yahooapis.com/AnswersService/V1/getQuestion?appid="
			+ appID + "&question_id=";

	InputStream is1, is2;

	Vector<String> users, questionsid;

	public QueryYahooAnswers(Vector<String> usersid, Vector<String> questionsid) {
		this.users = usersid;
		this.questionsid = questionsid;
	}

	// public YAQuestion getSingleQuestions(String yq_id) {
	//	
	// }

	public Vector<YAQuestion> getQuestions(Vector<YAQuestion> questions,
			String keyword, int num_questions, String searchin, int start)
			throws TimeOutException {
		// get questions by keyword

		XMLReader xr = null;
		try {
			xr = XMLReaderFactory.createXMLReader();
		} catch (SAXException ex) {
			ex.printStackTrace();
		}

		URL url = null;
		String query_url = urlQuestionSearch.replace("_query_", keyword)
				.replace("_searchin_", searchin).replace("_results_",
						num_questions + "").replace("_start_", start + "");
		try {
			System.err.println("url:" + query_url);
			url = new URL(query_url);
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}
		handlerQuestion = new QuestionHandler(questions, users, questionsid);
		xr.setContentHandler(handlerQuestion);
		HttpURLConnection conn = null;
		try {
			// URLConnection conn = url.openConnection();
			conn = (HttpURLConnection) url.openConnection();
			// System.out.println("timeout time:"+conn.getConnectTimeout());
			conn.setConnectTimeout(30000);
			conn.setRequestProperty("User-agent", "Mozilla/4.0");
			// is = url.openStream();
			is1 = conn.getInputStream();
			// int i = -1;
			// while((i = is1.read()) !=-1) {
			// System.out.print((char)i);
			// }
			xr.parse(new InputSource(is1));
		} catch (SAXException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			// System.out.println("error code:"+conn.getResponseCode());
			// System.out.println("error code:"+conn.getResponseMessage());
			try {
				if (conn.getResponseCode() == HttpURLConnection.HTTP_CLIENT_TIMEOUT) {
					throw new TimeOutException();
				} else {
					throw ex;
				}
			} catch (IOException ex2) {
				ex2.printStackTrace();
			}
		}

		return questions;
	}

	public YAQuestion getAnswers(YAQuestion q) {
		System.err.println("querying answer :" + q.getID());
		URL url = null;
		XMLReader xr = null;
		try {
			xr = XMLReaderFactory.createXMLReader();
		} catch (SAXException ex) {
			ex.printStackTrace();
		}
		// get answers for each question by question id
		//	

		// System.out.println("Current question:" + q.getID() + "/" +
		// q.getSubject());

		// Vector<String> answers_id = new Vector<String>();

		try {
			url = new URL(urlAnswerSearch + q.getID());
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}

		// handlerAnswer = new AnswerHandler(q, answers_id, users,
		// parseUserInfo, parseVoteInfo);
		// xr.setContentHandler(handlerAnswer);

		handlerAnswer = new AnswerHandler(q, users);
		xr.setContentHandler(handlerAnswer);

		try {
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-agent", "Mozilla/4.0");
			// is = url.openStream();
			is2 = conn.getInputStream();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			xr.parse(new InputSource(is2));
		} catch (SAXException ex) {
			System.err.println("error when fetching y!a answer");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.err.println("error when fetching y!a answer");
			ex.printStackTrace();
		}

		// System.out.println("There are [" + q.getAllAnswers().size()
		// + "] answer(s) for question." + q.getID());

		// put best answer to the top of the list
		Vector<YAAnswer> list = q.getAllAnswers();
		for (int i = 0; i < list.size(); i++) {
			YAAnswer ans = list.get(i);
			if (ans.isBest()) {
				// System.out.println(i + ":" + ans.getContent());
				list.remove(i);
				list.add(0, ans);
				break;
			}
		}

		// }
		return q;

	}

	
	public Vector<YAQuestion> query(String keyword, int num_questions,
			String searchin, int start) throws TimeOutException, IOException {// ,
		// OutputStream
		// os1,
		// OutputStream
		// os2)
		// {

		Vector<YAQuestion> questions = new Vector<YAQuestion>();

		String question = "";
		if (keyword.contains(" ")) {
			question = keyword.replace(" ", "%20");
		} else {
			question = keyword;
		}

		questions = getQuestions(questions, question, num_questions, searchin,
				start);

		// System.out.println("Query:" + query1);

		// we now get a list of questions which contain the keyword we issued
		// System.out.println("We got [" + questions.size()
		// + "] related questions");

		Iterator<YAQuestion> itor = questions.iterator();
		while (itor.hasNext()) {
			YAQuestion q = itor.next();
			q = getAnswers(q);
		}

		try {
			if (is1 != null)
				is1.close();
			if (is2 != null)
				is2.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return questions;

		// } catch (Exception ex) {
		// ex.printStackTrace();
		// } finally {
		// try {
		// if(is1 != null) is1.close();
		// if(is2 != null) is2.close();
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
		// }

		// return null;// this should never be reached
	}
}
