package edu.cmu.lti.oaqa.toy_impl;

public class ContentFilter {

  public static String filter(String wikicontent){
    int start = 0;
    int end = 0;
    int length = 0;
    length = "<title>".length();
    start = wikicontent.indexOf("<title>");
    end = wikicontent.indexOf("</title>");
    String title;
    title = wikicontent.substring(start+length, end);
    start = wikicontent.indexOf("<body");
    end = wikicontent.indexOf("<div class=\"references");
    if(start!=-1&&end!=-1){
      wikicontent = wikicontent.substring(start, end);
    }
    wikicontent = wikicontent.replaceAll("<.*?>", "");
    wikicontent = title+wikicontent;
    return wikicontent;
  }
  
}
