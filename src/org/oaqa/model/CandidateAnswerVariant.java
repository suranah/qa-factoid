

/* First created by JCasGen Tue Mar 02 13:59:18 EST 2010 */
package org.oaqa.model;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.cas.StringList;


/** A variant of a candidate answer.  A variant may have multiple occurrences, all of which are collected in a variant object.
 * Updated by JCasGen Tue Mar 16 14:06:28 EDT 2010
 * XML source: C:/Users/hideki/Research/workspace/OAQA Example/descriptors/OAQATypes.xml
 * @generated */
public class CandidateAnswerVariant extends OAQATop {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(CandidateAnswerVariant.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected CandidateAnswerVariant() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public CandidateAnswerVariant(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public CandidateAnswerVariant(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {}
     
 
    
  //*--------------*
  //* Feature: occurrences

  /** getter for occurrences - gets The occurrences of this variant.
   * @generated */
  public FSList getOccurrences() {
    if (CandidateAnswerVariant_Type.featOkTst && ((CandidateAnswerVariant_Type)jcasType).casFeat_occurrences == null)
      jcasType.jcas.throwFeatMissing("occurrences", "org.oaqa.model.CandidateAnswerVariant");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((CandidateAnswerVariant_Type)jcasType).casFeatCode_occurrences)));}
    
  /** setter for occurrences - sets The occurrences of this variant. 
   * @generated */
  public void setOccurrences(FSList v) {
    if (CandidateAnswerVariant_Type.featOkTst && ((CandidateAnswerVariant_Type)jcasType).casFeat_occurrences == null)
      jcasType.jcas.throwFeatMissing("occurrences", "org.oaqa.model.CandidateAnswerVariant");
    jcasType.ll_cas.ll_setRefValue(addr, ((CandidateAnswerVariant_Type)jcasType).casFeatCode_occurrences, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: candidateId

  /** getter for candidateId - gets Unique id of this candidate answer variant.
   * @generated */
  public String getCandidateId() {
    if (CandidateAnswerVariant_Type.featOkTst && ((CandidateAnswerVariant_Type)jcasType).casFeat_candidateId == null)
      jcasType.jcas.throwFeatMissing("candidateId", "org.oaqa.model.CandidateAnswerVariant");
    return jcasType.ll_cas.ll_getStringValue(addr, ((CandidateAnswerVariant_Type)jcasType).casFeatCode_candidateId);}
    
  /** setter for candidateId - sets Unique id of this candidate answer variant. 
   * @generated */
  public void setCandidateId(String v) {
    if (CandidateAnswerVariant_Type.featOkTst && ((CandidateAnswerVariant_Type)jcasType).casFeat_candidateId == null)
      jcasType.jcas.throwFeatMissing("candidateId", "org.oaqa.model.CandidateAnswerVariant");
    jcasType.ll_cas.ll_setStringValue(addr, ((CandidateAnswerVariant_Type)jcasType).casFeatCode_candidateId, v);}    
   
    
  //*--------------*
  //* Feature: text

  /** getter for text - gets The candidate answer string.
   * @generated */
  public String getText() {
    if (CandidateAnswerVariant_Type.featOkTst && ((CandidateAnswerVariant_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "org.oaqa.model.CandidateAnswerVariant");
    return jcasType.ll_cas.ll_getStringValue(addr, ((CandidateAnswerVariant_Type)jcasType).casFeatCode_text);}
    
  /** setter for text - sets The candidate answer string. 
   * @generated */
  public void setText(String v) {
    if (CandidateAnswerVariant_Type.featOkTst && ((CandidateAnswerVariant_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "org.oaqa.model.CandidateAnswerVariant");
    jcasType.ll_cas.ll_setStringValue(addr, ((CandidateAnswerVariant_Type)jcasType).casFeatCode_text, v);}    
   
    
  //*--------------*
  //* Feature: features

  /** getter for features - gets The features associated with this candidate answer.
   * @generated */
  public FSList getFeatures() {
    if (CandidateAnswerVariant_Type.featOkTst && ((CandidateAnswerVariant_Type)jcasType).casFeat_features == null)
      jcasType.jcas.throwFeatMissing("features", "org.oaqa.model.CandidateAnswerVariant");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((CandidateAnswerVariant_Type)jcasType).casFeatCode_features)));}
    
  /** setter for features - sets The features associated with this candidate answer. 
   * @generated */
  public void setFeatures(FSList v) {
    if (CandidateAnswerVariant_Type.featOkTst && ((CandidateAnswerVariant_Type)jcasType).casFeat_features == null)
      jcasType.jcas.throwFeatMissing("features", "org.oaqa.model.CandidateAnswerVariant");
    jcasType.ll_cas.ll_setRefValue(addr, ((CandidateAnswerVariant_Type)jcasType).casFeatCode_features, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: supportingEvidenceSearches

  /** getter for supportingEvidenceSearches - gets Reference to Searches performed to retrieve supporting evidence for this candidate answer.  The Search objects in turn reference the retrieved SearchResults.
   * @generated */
  public FSArray getSupportingEvidenceSearches() {
    if (CandidateAnswerVariant_Type.featOkTst && ((CandidateAnswerVariant_Type)jcasType).casFeat_supportingEvidenceSearches == null)
      jcasType.jcas.throwFeatMissing("supportingEvidenceSearches", "org.oaqa.model.CandidateAnswerVariant");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((CandidateAnswerVariant_Type)jcasType).casFeatCode_supportingEvidenceSearches)));}
    
  /** setter for supportingEvidenceSearches - sets Reference to Searches performed to retrieve supporting evidence for this candidate answer.  The Search objects in turn reference the retrieved SearchResults. 
   * @generated */
  public void setSupportingEvidenceSearches(FSArray v) {
    if (CandidateAnswerVariant_Type.featOkTst && ((CandidateAnswerVariant_Type)jcasType).casFeat_supportingEvidenceSearches == null)
      jcasType.jcas.throwFeatMissing("supportingEvidenceSearches", "org.oaqa.model.CandidateAnswerVariant");
    jcasType.ll_cas.ll_setRefValue(addr, ((CandidateAnswerVariant_Type)jcasType).casFeatCode_supportingEvidenceSearches, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for supportingEvidenceSearches - gets an indexed value - Reference to Searches performed to retrieve supporting evidence for this candidate answer.  The Search objects in turn reference the retrieved SearchResults.
   * @generated */
  public Search getSupportingEvidenceSearches(int i) {
    if (CandidateAnswerVariant_Type.featOkTst && ((CandidateAnswerVariant_Type)jcasType).casFeat_supportingEvidenceSearches == null)
      jcasType.jcas.throwFeatMissing("supportingEvidenceSearches", "org.oaqa.model.CandidateAnswerVariant");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((CandidateAnswerVariant_Type)jcasType).casFeatCode_supportingEvidenceSearches), i);
    return (Search)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((CandidateAnswerVariant_Type)jcasType).casFeatCode_supportingEvidenceSearches), i)));}

  /** indexed setter for supportingEvidenceSearches - sets an indexed value - Reference to Searches performed to retrieve supporting evidence for this candidate answer.  The Search objects in turn reference the retrieved SearchResults.
   * @generated */
  public void setSupportingEvidenceSearches(int i, Search v) { 
    if (CandidateAnswerVariant_Type.featOkTst && ((CandidateAnswerVariant_Type)jcasType).casFeat_supportingEvidenceSearches == null)
      jcasType.jcas.throwFeatMissing("supportingEvidenceSearches", "org.oaqa.model.CandidateAnswerVariant");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((CandidateAnswerVariant_Type)jcasType).casFeatCode_supportingEvidenceSearches), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((CandidateAnswerVariant_Type)jcasType).casFeatCode_supportingEvidenceSearches), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: alternativeNames

  /** getter for alternativeNames - gets alternative names for a given candidate answer variant, e.g. Tandy, Tandy Inc. for candidate answer Variant Tandy Incorporated
   * @generated */
  public StringList getAlternativeNames() {
    if (CandidateAnswerVariant_Type.featOkTst && ((CandidateAnswerVariant_Type)jcasType).casFeat_alternativeNames == null)
      jcasType.jcas.throwFeatMissing("alternativeNames", "org.oaqa.model.CandidateAnswerVariant");
    return (StringList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((CandidateAnswerVariant_Type)jcasType).casFeatCode_alternativeNames)));}
    
  /** setter for alternativeNames - sets alternative names for a given candidate answer variant, e.g. Tandy, Tandy Inc. for candidate answer Variant Tandy Incorporated 
   * @generated */
  public void setAlternativeNames(StringList v) {
    if (CandidateAnswerVariant_Type.featOkTst && ((CandidateAnswerVariant_Type)jcasType).casFeat_alternativeNames == null)
      jcasType.jcas.throwFeatMissing("alternativeNames", "org.oaqa.model.CandidateAnswerVariant");
    jcasType.ll_cas.ll_setRefValue(addr, ((CandidateAnswerVariant_Type)jcasType).casFeatCode_alternativeNames, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: uri

  /** getter for uri - gets A unique identifier for this candidate.  All candidate answers in the same candidate answer group will have the same uri.  A candidate answer group is formed at candidate generation time when additional derived candidate answers are created from the root candidate answer.
   * @generated */
  public String getUri() {
    if (CandidateAnswerVariant_Type.featOkTst && ((CandidateAnswerVariant_Type)jcasType).casFeat_uri == null)
      jcasType.jcas.throwFeatMissing("uri", "org.oaqa.model.CandidateAnswerVariant");
    return jcasType.ll_cas.ll_getStringValue(addr, ((CandidateAnswerVariant_Type)jcasType).casFeatCode_uri);}
    
  /** setter for uri - sets A unique identifier for this candidate.  All candidate answers in the same candidate answer group will have the same uri.  A candidate answer group is formed at candidate generation time when additional derived candidate answers are created from the root candidate answer. 
   * @generated */
  public void setUri(String v) {
    if (CandidateAnswerVariant_Type.featOkTst && ((CandidateAnswerVariant_Type)jcasType).casFeat_uri == null)
      jcasType.jcas.throwFeatMissing("uri", "org.oaqa.model.CandidateAnswerVariant");
    jcasType.ll_cas.ll_setStringValue(addr, ((CandidateAnswerVariant_Type)jcasType).casFeatCode_uri, v);}    
   
    
  //*--------------*
  //* Feature: docId

  /** getter for docId - gets The unique id of the document (if any) from which this candidate answer was generated.
   * @generated */
  public String getDocId() {
    if (CandidateAnswerVariant_Type.featOkTst && ((CandidateAnswerVariant_Type)jcasType).casFeat_docId == null)
      jcasType.jcas.throwFeatMissing("docId", "org.oaqa.model.CandidateAnswerVariant");
    return jcasType.ll_cas.ll_getStringValue(addr, ((CandidateAnswerVariant_Type)jcasType).casFeatCode_docId);}
    
  /** setter for docId - sets The unique id of the document (if any) from which this candidate answer was generated. 
   * @generated */
  public void setDocId(String v) {
    if (CandidateAnswerVariant_Type.featOkTst && ((CandidateAnswerVariant_Type)jcasType).casFeat_docId == null)
      jcasType.jcas.throwFeatMissing("docId", "org.oaqa.model.CandidateAnswerVariant");
    jcasType.ll_cas.ll_setStringValue(addr, ((CandidateAnswerVariant_Type)jcasType).casFeatCode_docId, v);}    
  }

    