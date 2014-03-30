package edu.cmu.lti.oaqa.framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceConfigurationException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

import org.oaqa.model.Question;

import edu.cmu.lti.oaqa.util.LogUtil;

/**
 * File System Collection Reader for questions
 * with oaqa-specific initialization 
 *
 */
public class QuestionReader extends CollectionReader_ImplBase {

  private static final Logger LOGGER = Logger.getLogger(LogUtil.getInvokingClassName());
  
  public static final String PARAM_QUESTIONFILE = "QuestionFile";

  private int mCurrentIndex;
  
  private File questionFile;
  
  private List<String> questionIds;
  private List<String> questionTexts;

  public static Pattern pQuestionLine = Pattern.compile("^([^\\s]+)\\s+([^\\s].+)$");

  public void initialize() throws ResourceInitializationException {
    questionFile = new File(((String) getConfigParameterValue(PARAM_QUESTIONFILE)).trim());
    mCurrentIndex = 0;

    LOGGER.info("Loading questions from "+questionFile.getAbsolutePath());
    
    // if input file does not exist, throw exception
    if (!questionFile.exists()) {
      LOGGER.fatal("The question file does not exist.");
      throw new ResourceInitializationException(ResourceConfigurationException.DIRECTORY_NOT_FOUND,
              new Object[] { PARAM_QUESTIONFILE, this.getMetaData().getName(), questionFile.getPath() });
    }

    
    // get list of questions in the specified directory
    questionIds = new ArrayList<String>();
    questionTexts = new ArrayList<String>();
    
    // Assuming a file contains multiple lines each with id, space, question.
    // E.g. Q0001 Where is Obama from?
    FileInputStream fis = null;
    InputStreamReader isr = null;
    BufferedReader br = null;
    try {
      fis = new FileInputStream(questionFile);
      isr = new InputStreamReader(fis,"utf-8");
      br = new BufferedReader(isr);
      String line = null;
      while ((line=br.readLine())!=null) {
        line = line.trim();
        if (line.length()==0 || line.matches("^\\s*#.+")) {
          continue;
        }
        
        Matcher mQuestionLine = pQuestionLine.matcher(line);
        
        if ( mQuestionLine.find() ) {
          questionIds.add( mQuestionLine.group(1) );
          questionTexts.add( mQuestionLine.group(2) );
        }
      }
    } catch (Exception e) {
      throw new ResourceInitializationException(e);
    } finally {
      try {
        br.close();
        isr.close();
        fis.close();
      } catch (Exception e2) {}
    }
    LOGGER.info("Done loading "+questionIds.size() +" questions.");
  }
  
  /**
   * @see org.apache.uima.collection.CollectionReader#hasNext()
   */
  public boolean hasNext() {
    return mCurrentIndex < questionIds.size();
  }

  /**
   * @see org.apache.uima.collection.CollectionReader#getNext(org.apache.uima.cas.CAS)
   */
  @SuppressWarnings("deprecation")
  public void getNext(CAS aCAS) throws IOException, CollectionException { 
    JCas jcas;
    JCas questionView;
    try {
      jcas = aCAS.getJCas();
      createViews(jcas);
      questionView = ViewManager.getQuestionView(jcas);
    } catch (CASException e) {
      throw new CollectionException(e);
    }
    
    String questionId = questionIds.get(mCurrentIndex);
    String questionText = questionTexts.get(mCurrentIndex);
    
    // put question "document" in CAS
    questionView.setDocumentText( questionText );
    
    // Annotate as question
    Question qAnnotation = new Question(questionView);
    qAnnotation.setBegin(0);
    qAnnotation.setEnd(questionText.length());
    qAnnotation.setId(questionId);
    qAnnotation.addToIndexes();  
 
    // Also store location of source document in CAS. This information is critical
    // if CAS Consumers will need to know where the original document contents are located.
    // For example, the Semantic Search CAS Indexer writes this information into the
    // search index that it creates, which allows applications that use the search index to
    // locate the documents that satisfy their semantic queries.
    File inputFile = new File(questionFile.getParentFile(),questionId);
    
    SourceDocumentInformation srcDocInfo = new SourceDocumentInformation(questionView);
    srcDocInfo.setUri(inputFile.getAbsoluteFile().toURL().toString());
    srcDocInfo.setOffsetInSource(0);
    //srcDocInfo.setDocumentSize((int) questionFile.length());
    srcDocInfo.setLastSegment(mCurrentIndex == questionIds.size()-1);
    srcDocInfo.addToIndexes();
    
    SourceDocumentInformation globalSrcDocInfo = new SourceDocumentInformation(jcas);
    globalSrcDocInfo.setUri(inputFile.getAbsoluteFile().toURL().toString());
    globalSrcDocInfo.setOffsetInSource(0);
    globalSrcDocInfo.addToIndexes();
    
    mCurrentIndex++;
  }

  /**
   * @see org.apache.uima.collection.base_cpm.BaseCollectionReader#close()
   */
  public void close() throws IOException {
  }

  /**
   * @see org.apache.uima.collection.base_cpm.BaseCollectionReader#getProgress()
   */
  public Progress[] getProgress() {
    return new Progress[] { new ProgressImpl(mCurrentIndex, questionIds.size(), Progress.ENTITIES) };
  }

  /**
   * Gets the total number of questions that will be returned by this collection reader. This is not
   * part of the general collection reader interface.
   * 
   * @return the number of documents in the collection
   */
  public int getNumberOfDocuments() {
    return questionIds.size();
  }
  	
	public static void createViews(JCas jcas) throws CASException {
    ViewManager.createQuestionView( jcas );
    ViewManager.createDocumentView( jcas );
    ViewManager.createCandidateView( jcas );  
    ViewManager.createFinalAnswerView( jcas );  
	}
	
}
