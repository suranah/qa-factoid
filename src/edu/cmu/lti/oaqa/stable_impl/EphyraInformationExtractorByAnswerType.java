package edu.cmu.lti.oaqa.stable_impl;

import info.ephyra.nlp.NETagger;
import info.ephyra.nlp.OpenNLP;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import edu.cmu.lti.oaqa.framework.IComponent;
import edu.cmu.lti.oaqa.framework.base.InformationExtractor_ImplBase;
import edu.cmu.lti.oaqa.framework.data.AnswerCandidate;
import edu.cmu.lti.oaqa.framework.data.RetrievalResult;
import edu.cmu.lti.oaqa.util.LogUtil;

public class EphyraInformationExtractorByAnswerType extends InformationExtractor_ImplBase implements IComponent {

  private static final Logger LOGGER = Logger.getLogger(LogUtil.getInvokingClassName());
  
  @Override
  public void initialize() {}
  
  @Override
  /**
   * Extracts candidates of the expected answer type.
   * 
   * @param answerType
   *            answer type
   * @param documents
   *            list of search results
   * @param candidates
   *            list of answer candidates
   */
   public List<AnswerCandidate> extractAnswerCandidates(String answerType, List<String> keyterms,
          List<RetrievalResult> documents) {
    
    List<AnswerCandidate> candidates = new ArrayList<AnswerCandidate>();
    
    for (RetrievalResult document : documents) {
      // split search result into sentences and tokenize sentences
      String[] sentences = OpenNLP.sentDetect(document.getText());
      String[][] tokens = new String[sentences.length][];
      for (int i = 0; i < sentences.length; i++)
        tokens[i] = NETagger.tokenize(sentences[i]);

      // get IDs of taggers for most specific NE type that can be tagged
      String[] neTypes = answerType.split("->");
      int neIds[] = new int[0];
      for (String neType : neTypes) {
        int[] thisIds = NETagger.getNeIds(neType);
        if (thisIds.length > 0)
          neIds = thisIds;
      }

      // No filtering/merging of candidates being done in this function,
      // so each candidate will have a list with only one retrieval result
      List<RetrievalResult> documentList = new ArrayList<RetrievalResult>();
      documentList.add(document);

      // extract NEs of that type
      for (int neId : neIds) {
        String[][] nes = NETagger.extractNes(tokens, neId);

        for (int i = 0; i < sentences.length; i++) {
          // untokenize NEs
          for (int j = 0; j < nes[i].length; j++)
            nes[i][j] = OpenNLP.untokenize(nes[i][j], sentences[i]);

          // add NEs to candidates
          for (String ne : nes[i]) {
            AnswerCandidate candidate = new AnswerCandidate(ne
                .trim(), documentList);
            candidate.setScore(1);
            candidates.add(candidate);
          }
        }
      }
    }
    LOGGER.info("  Extracted "+candidates.size()+" candidates.");
    return candidates;
  }

  @Override
  public String getComponentId() {
    return "Ephyra Information Extractor by Answer Type";
  }
}
