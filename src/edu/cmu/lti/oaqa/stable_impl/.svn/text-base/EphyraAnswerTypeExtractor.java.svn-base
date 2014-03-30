package edu.cmu.lti.oaqa.stable_impl;

import info.ephyra.nlp.semantics.ontologies.WordNet;
import info.ephyra.questionanalysis.atype.AnswerType;
import info.ephyra.questionanalysis.atype.QuestionClassifier;
import info.ephyra.questionanalysis.atype.QuestionClassifierFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import edu.cmu.lti.javelin.util.Language;
import edu.cmu.lti.oaqa.framework.base.AnswerTypeExtractor_ImplBase;
import edu.cmu.lti.oaqa.util.LogUtil;
import edu.cmu.lti.util.Pair;

public class EphyraAnswerTypeExtractor extends AnswerTypeExtractor_ImplBase {

  private static final Logger LOGGER = Logger.getLogger(LogUtil.getInvokingClassName());
  
  public static final String WORDNET_PATH = "res/ephyra/ontologies/wordnet/file_properties.xml";
  
  /** Answer type classifier. */
  private static QuestionClassifier qc;

  @Override
  public void initialize() {
    // answer type classifier
    try {
      // WordNet (used to map answer types to hierarchy)
      if (!WordNet.initialize(WORDNET_PATH))
        LOGGER.fatal("Could not initialize WordNet.");

      // Question Classification
      Pair<Language,Language> languagePair = new Pair<Language, Language>(Language
          .valueOf("en_US"), Language.valueOf("en_US"));
      qc = QuestionClassifierFactory.getInstance(languagePair);
      if( qc == null ){
        LOGGER.error( "qc is null !" );
        System.exit(0);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public String extractAnswerType(String question) {
    // initialized Ephyra components on first call

    // predict answer type(s)
    List<AnswerType> atypes = new ArrayList<AnswerType>();
    // int totalQwGL = 0;
    // int totalErrors = 0;

    try {
      atypes = qc.getAnswerTypes(question);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    // map answer type(s) to Ephyra naming conventions

    String[] res = new String[atypes.size()];
    for (int i = 0; i < atypes.size(); i++) {
      String atype = atypes.get(i).getFullType(-1).toLowerCase()
          .replaceAll("\\.", "->NE").replaceAll("^", "NE");
      StringBuilder sb = new StringBuilder(atype);
      Matcher m = Pattern.compile("_(\\w)").matcher(atype);
      while (m.find()) {
        sb.replace(m.start(), m.end(), m.group(1).toUpperCase());
        m = Pattern.compile("_(\\w)").matcher(sb.toString());
      }
      res[i] = sb.toString();
    }
    
    StringBuilder atypeCandidate = new StringBuilder();
    for (int i = 0; i < Math.min(atypes.size(),5); i++) {
      atypeCandidate.append((i>0?", ":"")+(i+1)+" "+res[i]);
    }
    atypeCandidate.append(atypes.size()>5?" ...":"");
    LOGGER.debug("  Answer type candidate(s): "+atypeCandidate);

    // currently only use one type
    String answerType = null;
    if (res.length > 0) {
      answerType = res[0];
      LOGGER.info("  Answer type: " + answerType);
    }

    return answerType;
  }

  @Override
  public String getComponentId() {
    return "Ephyra Answer Type Extractor";
  }
}
