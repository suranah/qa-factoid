package edu.cmu.lti.oaqa.stable_impl;

import info.ephyra.nlp.NETagger;
import info.ephyra.nlp.OpenNLP;
import info.ephyra.nlp.SnowballStemmer;
import info.ephyra.nlp.StanfordNeTagger;
import info.ephyra.nlp.StanfordParser;
import info.ephyra.nlp.indices.FunctionWords;
import info.ephyra.nlp.indices.IrregularVerbs;
import info.ephyra.nlp.semantics.ontologies.WordNet;
import info.ephyra.questionanalysis.KeywordExtractor;
import info.ephyra.questionanalysis.QuestionNormalizer;
import info.ephyra.questionanalysis.Term;
import info.ephyra.questionanalysis.TermExtractor;
import info.ephyra.util.Dictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import edu.cmu.lti.oaqa.framework.IComponent;
import edu.cmu.lti.oaqa.framework.JCasManipulator;
import edu.cmu.lti.oaqa.framework.ViewManager;
import edu.cmu.lti.oaqa.util.LogUtil;

public class EphyraKeytermExtractor implements IComponent {

  private static final Logger LOGGER = Logger.getLogger(LogUtil.getInvokingClassName());
  
  public static final String TOKENIZER_PATH = "res/ephyra/nlp/tokenizer/opennlp/EnglishTok.bin.gz";

  public static final String SENT_DETECTOR_PATH = "res/ephyra/nlp/sentencedetector/opennlp/EnglishSD.bin.gz";
  
  public static final String TAGGER_PATH = "res/ephyra/nlp/postagger/opennlp/tag.bin.gz";
  public static final String TAGGER_DICT_PATH = "res/ephyra/nlp/postagger/opennlp/tagdict";

  public static final String CHUNKER_PATH = "res/ephyra/nlp/phrasechunker/opennlp/EnglishChunk.bin.gz";

  public static final String NER_LIST_PATH = "res/ephyra/nlp/netagger/lists/";
  public static final String NER_REGEX_PATH = "res/ephyra/nlp/netagger/patterns.lst";
  public static final String NER_STANFORD_PATH = "res/ephyra/nlp/netagger/stanford/ner-eng-ie.crf-3-all2006-distsim.ser.gz";
  
  
  @Override
  public void initialize() {
    // tokenizer
    if (!OpenNLP.createTokenizer(TOKENIZER_PATH))
      LOGGER.fatal("Could not initialize tokenizer.");

    // sentence segmenter
    if (!OpenNLP.createSentenceDetector(SENT_DETECTOR_PATH))
      LOGGER.fatal("Could not initialize sentence segmenter.");

    // stemmer
    SnowballStemmer.create();

    // part of speech tagger
    if (!OpenNLP.createPosTagger(TAGGER_PATH,TAGGER_DICT_PATH))
      LOGGER.fatal("Could not initialize POS tagger.");

    // phrase chunker
    if (!OpenNLP.createChunker(CHUNKER_PATH))
      LOGGER.fatal("Could not initialize phrase chunker.");

    // syntactic parser
    try {
      StanfordParser.initialize();
    } catch (Exception e) {
      LOGGER.fatal("Could not initialize syntactic parser.");
    }

    // named entity recognizers
    NETagger.loadListTaggers(NER_LIST_PATH);
    NETagger.loadRegExTaggers(NER_REGEX_PATH);
    if (!StanfordNeTagger.isInitialized()
        && !StanfordNeTagger.init(NER_STANFORD_PATH))
      LOGGER.fatal("Could not initialize NE tagger.");

    // function words
    if (!FunctionWords
        .loadIndex("res/ephyra/indices/functionwords_nonumbers"))
      System.err.println("Could not load function words.");

    // irregular verbs
    if (!IrregularVerbs.loadVerbs("res/ephyra/indices/irregularverbs"))
      LOGGER.fatal("Could not load irregular verbs.");
  }
  
  @Override
  public void process(JCas jcas) throws AnalysisEngineProcessException {
    try {
      JCas questionView = ViewManager.getQuestionView(jcas);
      String questionText = questionView.getDocumentText();

      // normalize question
      String qn = QuestionNormalizer.normalize(questionText);

      // resolve verb constructions with auxiliaries
      String verbMod = (QuestionNormalizer.handleAuxiliaries(qn))[0];

      // extract named entities
      String[][] nes = TermExtractor.getNes(questionText);

      // extract keywords
      String[] kws = KeywordExtractor.getKeywords(verbMod);
      List<String> keyterms = new ArrayList<String>();
      for (String kw : kws) {
        keyterms.add(kw);
      }
      LOGGER.info("  Keywords: " + Arrays.toString(kws));

      // extract phrases based on WordNet and named entities
      Dictionary wordNet = new WordNet();
      Term[] terms = TermExtractor.getTerms(verbMod, qn, nes,
          new Dictionary[] { wordNet });
      List<String> keyphrases = new ArrayList<String>(terms.length);
      for (int i = 0; i < terms.length; i++) {
        keyphrases.add( terms[i].getText().replace("\"", "") );
      }
      LOGGER.info("  Phrases:  " + keyphrases);

      // Save result into a view
      JCasManipulator.storeKeyTermsAndPhrases( questionView, keyterms, keyphrases );
    } catch (Exception e) {
      throw new AnalysisEngineProcessException(e);
    }
  }
  
  @Override
  public String getComponentId() {
    return "Ephyra Keyterm Extractor";
  }  
}
