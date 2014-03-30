package edu.cmu.lti.oaqa.framework.base;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import edu.cmu.lti.oaqa.framework.IComponent;
import edu.cmu.lti.oaqa.framework.JCasManipulator;
import edu.cmu.lti.oaqa.framework.ViewManager;

public abstract class AnswerTypeExtractor_ImplBase implements IComponent {
  
  public abstract void initialize();
  
  public abstract String extractAnswerType(String question);
  
  public void process(JCas jcas) throws AnalysisEngineProcessException {
    try {
      JCas questionView = ViewManager.getQuestionView(jcas);
      String questionText = questionView.getDocumentText();
      String answerType = extractAnswerType(questionText);
      JCasManipulator.storeAnswerType( questionView, answerType );
    } catch (Exception e) {
      throw new AnalysisEngineProcessException(e);
    }
  }
  
}
