package edu.cmu.lti.oaqa.toy_impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cmu.lti.oaqa.framework.data.AnswerCandidate;

public class WikiScoreGenerator {


  public static AnswerCandidate[] wikiScore(String answerType, List<String> keyterms,
          AnswerCandidate[] sorted) {
    AnswerCandidate[] wikiSorted = sorted.clone();
    String google = null;
    String wiki = null;
    String googleWiki = null;
    try {
      google = getGoogle(keyterms, answerType, "");
      google = googleFilter(google);
      googleWiki = getGoogle(keyterms, answerType, "+wikipedia");
      wiki = getWiki(googleWiki);
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (wiki != null) {
      generateScore(wikiSorted, wiki, google, googleWiki);
      Arrays.sort(wikiSorted, Collections.reverseOrder());
    }

    //if(answerType.equalsIgnoreCase("NEproperName->NEperson"))
      //answerMerge(wikiSorted, answerType);
    return wikiSorted;
  }

  private static String getGoogle(List<String> keyterms, String answerType, String addon) throws Exception {
    String url;
    url = "http://google.com/search?q=";
    int number = keyterms.size();
    for (int i = 0; i < number; i++) {
      String sth = keyterms.get(i);
      if (i == 0)
        url = url + sth;
      else
        url = url + "+" + sth;
    }
    url = url + addon;

    URL urlDir;
    urlDir = new URL(url);
    StringBuffer conBuffer = new StringBuffer();
    URLConnection conn = urlDir.openConnection();
    conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String line = null;
    while ((line = br.readLine()) != null) {
      conBuffer.append(line + "\n");
    }
    br.close();
    String contentString = conBuffer.toString();
    String google = contentString;
    return google;
  }
  private static String getWiki(String contentString) throws Exception {
    String wikiPattern = "\\?q=(http://en.wikipedia.org/wiki/.*?)&amp";
    Pattern p = Pattern.compile(wikiPattern, Pattern.DOTALL | Pattern.MULTILINE);
    Matcher m = p.matcher(contentString);
    URL wiki;
    String wikicontent;
    if (m.find()) {
      System.out.println(m.group());
      System.out.println("Wiki Fetched");
      StringBuffer wconBuffer = new StringBuffer();
      String wikiurl = m.group(1);
      wiki = new URL(wikiurl);
      URLConnection wconn = wiki.openConnection();
      wconn.setRequestProperty("User-Agent",
              "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
      BufferedReader wbr = new BufferedReader(new InputStreamReader(wconn.getInputStream()));
      String line = null;
      while ((line = wbr.readLine()) != null) {
        wconBuffer.append(line + "\n");
      }
      wbr.close();
      wikicontent = wconBuffer.toString();
      return wikicontent;
    } else {
      return null;
    }
  }

  private static void answerMerge(AnswerCandidate[] sorted, String answerType){
    {
      for(AnswerCandidate ac : sorted){
        String curAns = ac.getText();
        for(AnswerCandidate acc : sorted){
          String tmpAns = acc.getText();
          if(tmpAns.indexOf(curAns)!=-1&&tmpAns.indexOf(curAns)!=0&&!tmpAns.equalsIgnoreCase(curAns)){
            if(acc.getScore()!=0)
              acc.setScore(acc.getScore()+ac.getScore());
            //ac.setScore(0);
          }
        }
      }
    }
  }

  
  private static String googleFilter(String google){
    String result = " ";
    String pattern = "<td class=\"j\"><font size=\"-1\">(.*?)<br><font color=\"green\">";
    Pattern p = Pattern.compile(pattern,Pattern.MULTILINE);
    Matcher m = p.matcher(google);
    int i = 0;
    while(m.find()){
      i++;
      String tmp = m.group(1).toString();
      tmp = tmp.replaceAll("<.*?>", "");
      if(tmp!=null)
        result = result.concat(tmp).concat(" \n ");
      }
    return result;
  }
  
  private static void generateScore(AnswerCandidate[] sorted, String wiki, String google, String googleWiki) {
    int size = sorted.length;
    int maxScore = 0;
    int[] scores = new int[size];
    int start = 0;
    int end = 0;
    int length = 0;
    
    boolean noMatch = true;
    
    
    //Get titles from Google search results.
    String[] titles = new String[10];
    String pattern = "http://en.wikipedia.org/wiki.*?ved.*?\">(.*?) - <b>Wikipedia</b>, the free encyclopedia<";
    Pattern gp = Pattern.compile(pattern,Pattern.MULTILINE);
    Matcher gm = gp.matcher(googleWiki);
    int tNum = 0;    
    while(gm.find()){
      String tmp = gm.group(1).toString();
      tmp = tmp.replaceAll("<.*?>", "");
      titles[tNum]=tmp;
      tNum++;
      }
    
    
    
    length = "<title>".length();
    start = wiki.indexOf("<title>");
    end = wiki.indexOf("</title>");
    String title;
    title = wiki.substring(start+length, end);
    title = title.replaceAll(" - Wikipedia, the free encyclopedia", "");
    
    for (int i = 0; i < size; i++) {
      if(sorted[i].getText().equalsIgnoreCase(title))
        noMatch = false;
    }
    
    
    
    
    wiki = ContentFilter.filter(wiki);
    for (int i = 0; i < size; i++) {
      AnswerCandidate ac = sorted[i];
      int score = 0;
      String answer = ac.getText();
      String originAns;
      //if (answer.equalsIgnoreCase(title))
        //score = 10;
      answer = answer.replaceAll("\\*|\\+|\\?|\\[|\\]|\\{|\\}|\\.|\\-|\\\\|\\$|\\^|,", "");
      answer = answer.replaceAll("\\|", "");
      answer = answer.replaceAll("\\s\\s+", " ");
      originAns = answer.toString();
      if (!answer.equalsIgnoreCase(",")&& answer.indexOf(",")!=1 &&!answer.equalsIgnoreCase("") && !answer.equalsIgnoreCase(" ") && answer.indexOf("(")==-1 && answer.indexOf(")")==-1) {
        answer = "[^\\w]" + answer + "[^\\w]";
        Pattern p = Pattern.compile(answer, Pattern.MULTILINE);
        Matcher m = p.matcher(wiki);
        if(noMatch){
          while (m.find())
            score++;
        }
        m = p.matcher(google);
        while(m.find())
          score+=4;
        for(int k = 0; k < tNum; k++)
        {
          if (originAns.equalsIgnoreCase(titles[k]))
            score+=40;
        }
      }
      scores[i] = score;
      if (originAns.equalsIgnoreCase("2007"))
        scores[i]=0;
      if (maxScore < score)
        maxScore = score;
    }
    //if (maxScore != 0) 
    {
      for (int i = 0; i < size; i++) {
        AnswerCandidate ac = sorted[i];
        ac.setScore(scores[i]);
      }
    }
  }
}
