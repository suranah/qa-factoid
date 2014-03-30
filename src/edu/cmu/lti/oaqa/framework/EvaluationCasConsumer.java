/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package edu.cmu.lti.oaqa.framework;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;
import org.apache.uima.util.FileUtils;

import edu.cmu.lti.oaqa.framework.evaluation.EvaluationDAO;
import edu.cmu.lti.oaqa.framework.evaluation.HtmlGenerator;
import edu.cmu.lti.oaqa.framework.evaluation.HtmlUtil;
import edu.cmu.lti.oaqa.framework.evaluation.SpreadSheetGenerator;
import edu.cmu.lti.oaqa.util.LogUtil;

/**
 * A simple CAS consumer that writes the CAS to XMI format.
 * <p>
 * This CAS Consumer takes one parameter:
 * <ul>
 * <li><code>OutputDirectory</code> - path to directory into which output files will be written</li>
 * </ul>
 */
public class EvaluationCasConsumer extends CasConsumer_ImplBase {
  /**
   * Name of configuration parameter that must be set to the path of a directory into which the
   * output files will be written.
   */
  public static final String PARAM_OUTPUTDIR = "InputDirectory";
  public static final String PARAM_SPLIT_SIZE = "SystemOutputSplitSize";

  private static final Logger LOGGER = Logger.getLogger(LogUtil.getInvokingClassName());
  
  private static NumberFormat nf;
  
  //Input to this module is an output of xmi files
  private File mInputDir;
  //every how many results shall we create detailed system output page?
  private int splitSize;
  
  private String templateIndex = "res/eval_template/index.html";
  private String templateSideBar = "res/eval_template/side_bar.html";
  private String templateTimeStat = "res/eval_template/time.html";
  private String templateBasic = "res/eval_template/basic.html";
  private String timeStat;
  private String template;

  private String LOG_FILE_NAME = "OAQA-pipeline.log";
  
  public void initialize() throws ResourceInitializationException {
    mInputDir = new File((String) getConfigParameterValue(PARAM_OUTPUTDIR));
    splitSize = (Integer) getConfigParameterValue(PARAM_SPLIT_SIZE);
    nf = NumberFormat.getNumberInstance();
    nf.setMaximumFractionDigits(2);
    nf.setMinimumFractionDigits(2);
    
    //reuse
    try {
      timeStat = FileUtils.file2String(new File(templateTimeStat), "utf-8");
      template = FileUtils.file2String(new File(templateBasic), "utf-8");
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    
  }

  public void processCas(CAS aCAS) throws ResourceProcessException {}
  
  /**
   * Evaluates system output
   */
  @Override
  public void destroy() {
    String date = TimeKeeper.getInstance().getBeginDate();
    try {
      File outputDir = new File(mInputDir,"oaqa-"+date);
      if (!outputDir.exists()) outputDir.mkdirs();
      
      String unzippedPath = mInputDir.getPath();
      
      // Evaluation logic happens here!
      EvaluationDAO dao = new EvaluationDAO(unzippedPath);

      //key: question id, value: html output per question id
      Map<String, String> qidOutputMap = HtmlGenerator.xmiToHtmlTables(dao);
      List<String> qids = new ArrayList<String>( qidOutputMap.keySet() );
      
      // generating summary
      String summaryHtml = generateSummary(qidOutputMap, dao);
      FileUtils.saveString2File(summaryHtml, new File(outputDir, "summary.html"), "utf-8");
      
      SpreadSheetGenerator.createSpreadSheet(new File(outputDir, "analysis-sheet.xls"), dao);
      
      // copy the log4j file
      File log = new File(System.getProperty("java.io.tmpdir"), LOG_FILE_NAME);
      if (log.exists()) {
        FileUtils.copyFile(log, outputDir);
      }
      
      String timeStat = generateTimeStat();
      FileUtils.saveString2File(timeStat, new File(outputDir, "process-time.html"), "utf-8");
      
      String index = generateIndexPage();
      FileUtils.saveString2File(index, new File(outputDir, "index.html"), "utf-8");

      // generating system output and qid-file mapping
      Map<String,String> qidToFileName = generateSystemOutput(qidOutputMap, qids, outputDir);
      
      String sideBar = generateSideBar( qids, qidToFileName );
      FileUtils.saveString2File(sideBar, new File(outputDir, "side_bar.html"), "utf-8");
      
      String summaryText = HtmlGenerator.generateSummaryText(dao, qidOutputMap);
      LOGGER.info("------------------ Evaluation Summary ------------------");
      LOGGER.info(summaryText);
      LOGGER.info("------------------ Completed ------------------");
      LOGGER.info("Log, stats, outputs are available at \n"+outputDir.getAbsolutePath());
    } catch (Exception e) {
      e.printStackTrace();
    }
    // Making System.out quiet
    System.setOut(new PrintStream(new ByteArrayOutputStream()));
  }
  
  private String generateSummary( Map<String, String> qidOutputMap,
          EvaluationDAO dao ) throws Exception {
    String body = HtmlGenerator.generateSummaryHtml(dao)+
    "<br><a href=\"http://mu.lti.cs.cmu.edu/trac/oaqa/wiki/OAQADocumentation/EvaluationMetrics\">Metric definition</a>\n";
    Map<String,String> nameValuePair = new HashMap<String,String>();
    nameValuePair.put("TITLE", "Evaluation Summary");
    nameValuePair.put("BODY", body);
    String html = HtmlUtil.substituteVariables(template, nameValuePair);
    return html;
  }
  
  private Map<String,String> generateSystemOutput( Map<String, String> qidOutputMap, 
          List<String> qids, File outputDir ) throws Exception {
    int numPages = splitSize > 0
      ? (int)Math.ceil((double)qidOutputMap.size() / (double)splitSize)
      : 1;
    
    File detailDir = new File(outputDir, "system-output");
    detailDir.mkdir();
    
    Map<String,String> qidToFileName = new LinkedHashMap<String,String>();
    // duplicated code..
    for ( int i=0; i<numPages; i++ ) {
      int start = splitSize * i;
      int end = -1;
      for ( int j=start+1; j<Math.min(splitSize * (i+1), qids.size()); j++ ) {
        if ( qids.get(j)!=null ) {
          end = j;
        } else {
          break;
        }
      }
      String filename = String.format("%04d", start+1)
      + (end>0 ? "-"+String.format("%04d", end+1):"")+".html";

      if (end==-1) end = start;
      // Create a mapping from qid to file
      for (int j=start; j<=end; j++) {
        qidToFileName.put(qids.get(j), filename);
      }
    }
    for ( int i=0; i<numPages; i++ ) {
      int start = splitSize * i;
      int end = -1;
      for ( int j=start+1; j<Math.min(splitSize * (i+1), qids.size()); j++ ) {
        if ( qids.get(j)!=null ) {
          end = j;
        } else {
          break;
        }
      }
      String filename = String.format("%04d", start+1)
      + (end>0 ? "-"+String.format("%04d", end+1):"")+".html";
      File detailFile = new File(detailDir, filename);
      
      String body = HtmlGenerator.generateDetailPage(qids,
                qidOutputMap, qidToFileName, start, end);
      Map<String,String> nameValuePair = new HashMap<String,String>();
      nameValuePair.put("TITLE", "System output "+start+(end>0?" - "+end:""));
      nameValuePair.put("BODY", body);
      String htmlContent = HtmlUtil.substituteVariables(template, nameValuePair);
      FileUtils.saveString2File(htmlContent, detailFile, "utf-8");
    }
  
    //  If no exceptions so far, delte temp xmi files that are already zipped.
    for (File f : mInputDir.listFiles()) {
      if (f.getName().endsWith(".xmi")) {
        f.delete();
      }
    }
    return qidToFileName;
  }
  
  private String generateTimeStat() throws IOException {
    // generate process time stat
    TimeKeeper tk = TimeKeeper.getInstance();
    Map<String,Map<String,Integer>> processTime = tk.getProcessTimeMap();
    //calc avg and total
    for ( String componentId : processTime.keySet() ) {
      Map<String,Integer> map = processTime.get(componentId);
      int total = 0;
      int counter = 0;
      for ( String qid : map.keySet() ) {
        if (counter++==0) continue; // skip first item - initialization time
        total += map.get(qid);
      }
      if (map.size()>1) {
        map.put("avg", total/(map.size()-1));
        map.put("total", total);
      }
    }
    Set<String> qids = tk.getQids();
    qids.add("avg");
    qids.add("total");
    
    StringBuilder body = new StringBuilder();
    body.append( "<h3>Process Time Statistics (in Sec.)</h3>" );
    body.append( "<table class=\"datatable\">\n<thead><tr>" );
    body.append( "<th style=\"text-align:left\">QID</th>" );
    for ( String componentId : processTime.keySet() ) {
      body.append( "<th>"+componentId+"</th>" );
    }
    body.append( "</tr></thead>\n" );
    int counter = 0;
    for ( String qid : qids ) {
      String style = "";
      if ( qid.equals("init") ) {
        style = "style=\"border-bottom: 1px solid #999999;\"";
      } else if (qid.equals("avg") ) {
        style = "style=\"border-top: 1px solid #999999;\"";
      }
      body.append( "<tr class=\""+(counter%2==0?"even":"odd")+"\" "+style+">" );
      body.append( "<td style=\"text-align:left\">"+(qid.matches("init|avg|total")?("("+qid+")"):qid)+"</td>" );
      for ( String componentId : processTime.keySet() ) {
        Map<String,Integer> map = processTime.get(componentId); 
        int time = map!=null&&map.get(qid)!=null?map.get(qid):-1;
        String timeText = time>=0 ? nf.format(time/1000D) : "N/A";
        body.append("<td>"+timeText+"</td>");
      }
      body.append("</tr>\n");
      counter++;
    }
    if ( tk.getQids().size()>10 ) {
      body.append( "<tfoot><th>QID</th>" );
      for ( String componentId : processTime.keySet() ) {
        body.append( "<th>"+componentId+"</th>" );
      }
      body.append( "</tr></tfoot>\n" );
    }
    body.append( "</table>\n</body>\n</html>" );
    return timeStat.replaceAll("@@BODY@@", body.toString());
  }
  
  private String generateIndexPage() throws IOException {
    return FileUtils.file2String(new File(templateIndex));
  }
  
  private String generateSideBar( List<String> qids, Map<String,String> qidToFileName ) throws IOException {
    StringBuilder allQuestions = new StringBuilder();
    for ( String qid : qids ) {
      allQuestions.append( "<a href=\"system-output/"+qidToFileName.get(qid)+"#"+qid+"\" target=\"main\">"+qid+"</a> \n");
    }
    String content = FileUtils.file2String(new File(templateSideBar),"utf-8");
    
    Map<String,String> nameValuePair = new HashMap<String,String>();
    nameValuePair.put("ALL_QUESTIONS", allQuestions.toString());
    nameValuePair.put("DATE", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
    
    content = HtmlUtil.substituteVariables(content, nameValuePair);
    
    return content;
  }
  
}
