

/* First created by JCasGen Tue Mar 02 13:59:18 EST 2010 */
package org.oaqa.model;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.jcas.cas.StringArray;


/** Annotation on a predicate, which may have arguments; useful in transforming text into a "logical form", with affordances similar to the ones provided in Extended WordNet and the like. --- Source: Murdock
 * Updated by JCasGen Tue Mar 16 14:06:28 EDT 2010
 * XML source: C:/Users/hideki/Research/workspace/OAQA Example/descriptors/OAQATypes.xml
 * @generated */
public class Predicate extends OAQAAnnotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Predicate.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Predicate() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Predicate(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Predicate(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Predicate(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {}
     
 
    
  //*--------------*
  //* Feature: arguments

  /** getter for arguments - gets The arguments
   * @generated */
  public FSArray getArguments() {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "org.oaqa.model.Predicate");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_arguments)));}
    
  /** setter for arguments - sets The arguments 
   * @generated */
  public void setArguments(FSArray v) {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "org.oaqa.model.Predicate");
    jcasType.ll_cas.ll_setRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_arguments, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for arguments - gets an indexed value - The arguments
   * @generated */
  public Predicate getArguments(int i) {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "org.oaqa.model.Predicate");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_arguments), i);
    return (Predicate)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_arguments), i)));}

  /** indexed setter for arguments - sets an indexed value - The arguments
   * @generated */
  public void setArguments(int i, Predicate v) { 
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "org.oaqa.model.Predicate");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_arguments), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_arguments), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: argumentLabels

  /** getter for argumentLabels - gets The slot labels for the arguments (e.g., subj, obj, objprep)
   * @generated */
  public StringArray getArgumentLabels() {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_argumentLabels == null)
      jcasType.jcas.throwFeatMissing("argumentLabels", "org.oaqa.model.Predicate");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_argumentLabels)));}
    
  /** setter for argumentLabels - sets The slot labels for the arguments (e.g., subj, obj, objprep) 
   * @generated */
  public void setArgumentLabels(StringArray v) {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_argumentLabels == null)
      jcasType.jcas.throwFeatMissing("argumentLabels", "org.oaqa.model.Predicate");
    jcasType.ll_cas.ll_setRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_argumentLabels, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for argumentLabels - gets an indexed value - The slot labels for the arguments (e.g., subj, obj, objprep)
   * @generated */
  public String getArgumentLabels(int i) {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_argumentLabels == null)
      jcasType.jcas.throwFeatMissing("argumentLabels", "org.oaqa.model.Predicate");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_argumentLabels), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_argumentLabels), i);}

  /** indexed setter for argumentLabels - sets an indexed value - The slot labels for the arguments (e.g., subj, obj, objprep)
   * @generated */
  public void setArgumentLabels(int i, String v) { 
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_argumentLabels == null)
      jcasType.jcas.throwFeatMissing("argumentLabels", "org.oaqa.model.Predicate");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_argumentLabels), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_argumentLabels), i, v);}
   
    
  //*--------------*
  //* Feature: sources

  /** getter for sources - gets The annotations from which this predicate was derived, e.g., token or entity annotations.
   * @generated */
  public FSArray getSources() {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_sources == null)
      jcasType.jcas.throwFeatMissing("sources", "org.oaqa.model.Predicate");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_sources)));}
    
  /** setter for sources - sets The annotations from which this predicate was derived, e.g., token or entity annotations. 
   * @generated */
  public void setSources(FSArray v) {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_sources == null)
      jcasType.jcas.throwFeatMissing("sources", "org.oaqa.model.Predicate");
    jcasType.ll_cas.ll_setRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_sources, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for sources - gets an indexed value - The annotations from which this predicate was derived, e.g., token or entity annotations.
   * @generated */
  public Annotation getSources(int i) {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_sources == null)
      jcasType.jcas.throwFeatMissing("sources", "org.oaqa.model.Predicate");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_sources), i);
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_sources), i)));}

  /** indexed setter for sources - sets an indexed value - The annotations from which this predicate was derived, e.g., token or entity annotations.
   * @generated */
  public void setSources(int i, Annotation v) { 
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_sources == null)
      jcasType.jcas.throwFeatMissing("sources", "org.oaqa.model.Predicate");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_sources), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_sources), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: parse

  /** getter for parse - gets A parse for which this predicate was a head (if any)
   * @generated */
  public Annotation getParse() {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_parse == null)
      jcasType.jcas.throwFeatMissing("parse", "org.oaqa.model.Predicate");
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_parse)));}
    
  /** setter for parse - sets A parse for which this predicate was a head (if any) 
   * @generated */
  public void setParse(Annotation v) {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_parse == null)
      jcasType.jcas.throwFeatMissing("parse", "org.oaqa.model.Predicate");
    jcasType.ll_cas.ll_setRefValue(addr, ((Predicate_Type)jcasType).casFeatCode_parse, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: semanticType

  /** getter for semanticType - gets A semantic type, typically the name of an Entity Annotation type
   * @generated */
  public String getSemanticType() {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_semanticType == null)
      jcasType.jcas.throwFeatMissing("semanticType", "org.oaqa.model.Predicate");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Predicate_Type)jcasType).casFeatCode_semanticType);}
    
  /** setter for semanticType - sets A semantic type, typically the name of an Entity Annotation type 
   * @generated */
  public void setSemanticType(String v) {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_semanticType == null)
      jcasType.jcas.throwFeatMissing("semanticType", "org.oaqa.model.Predicate");
    jcasType.ll_cas.ll_setStringValue(addr, ((Predicate_Type)jcasType).casFeatCode_semanticType, v);}    
   
    
  //*--------------*
  //* Feature: partOfSpeech

  /** getter for partOfSpeech - gets Coarse-grained part of speech --- Example: noun, verb, adj, cord
   * @generated */
  public String getPartOfSpeech() {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_partOfSpeech == null)
      jcasType.jcas.throwFeatMissing("partOfSpeech", "org.oaqa.model.Predicate");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Predicate_Type)jcasType).casFeatCode_partOfSpeech);}
    
  /** setter for partOfSpeech - sets Coarse-grained part of speech --- Example: noun, verb, adj, cord 
   * @generated */
  public void setPartOfSpeech(String v) {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_partOfSpeech == null)
      jcasType.jcas.throwFeatMissing("partOfSpeech", "org.oaqa.model.Predicate");
    jcasType.ll_cas.ll_setStringValue(addr, ((Predicate_Type)jcasType).casFeatCode_partOfSpeech, v);}    
   
    
  //*--------------*
  //* Feature: lemmaForm

  /** getter for lemmaForm - gets A canonical/lemmatized form of the covered text
   * @generated */
  public String getLemmaForm() {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_lemmaForm == null)
      jcasType.jcas.throwFeatMissing("lemmaForm", "org.oaqa.model.Predicate");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Predicate_Type)jcasType).casFeatCode_lemmaForm);}
    
  /** setter for lemmaForm - sets A canonical/lemmatized form of the covered text 
   * @generated */
  public void setLemmaForm(String v) {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_lemmaForm == null)
      jcasType.jcas.throwFeatMissing("lemmaForm", "org.oaqa.model.Predicate");
    jcasType.ll_cas.ll_setStringValue(addr, ((Predicate_Type)jcasType).casFeatCode_lemmaForm, v);}    
   
    
  //*--------------*
  //* Feature: isMainReference

  /** getter for isMainReference - gets If true, then this is the main reference to the first argument.  Modifiers and anaphoric references do not have isMainReference set. --- Example: "A dark blue [hat]"
   * @generated */
  public boolean getIsMainReference() {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_isMainReference == null)
      jcasType.jcas.throwFeatMissing("isMainReference", "org.oaqa.model.Predicate");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Predicate_Type)jcasType).casFeatCode_isMainReference);}
    
  /** setter for isMainReference - sets If true, then this is the main reference to the first argument.  Modifiers and anaphoric references do not have isMainReference set. --- Example: "A dark blue [hat]" 
   * @generated */
  public void setIsMainReference(boolean v) {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_isMainReference == null)
      jcasType.jcas.throwFeatMissing("isMainReference", "org.oaqa.model.Predicate");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Predicate_Type)jcasType).casFeatCode_isMainReference, v);}    
   
    
  //*--------------*
  //* Feature: isVariable

  /** getter for isVariable - gets True iff the predicate expresses some unknown entity, typically the focus of a question:  --- Example: "[Who] shot JR?", "What [city] was JR born in?"
   * @generated */
  public boolean getIsVariable() {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_isVariable == null)
      jcasType.jcas.throwFeatMissing("isVariable", "org.oaqa.model.Predicate");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Predicate_Type)jcasType).casFeatCode_isVariable);}
    
  /** setter for isVariable - sets True iff the predicate expresses some unknown entity, typically the focus of a question:  --- Example: "[Who] shot JR?", "What [city] was JR born in?" 
   * @generated */
  public void setIsVariable(boolean v) {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_isVariable == null)
      jcasType.jcas.throwFeatMissing("isVariable", "org.oaqa.model.Predicate");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Predicate_Type)jcasType).casFeatCode_isVariable, v);}    
   
    
  //*--------------*
  //* Feature: determiner

  /** getter for determiner - gets The determiner attached to the node, if any --- Example: [the] book
   * @generated */
  public String getDeterminer() {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_determiner == null)
      jcasType.jcas.throwFeatMissing("determiner", "org.oaqa.model.Predicate");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Predicate_Type)jcasType).casFeatCode_determiner);}
    
  /** setter for determiner - sets The determiner attached to the node, if any --- Example: [the] book 
   * @generated */
  public void setDeterminer(String v) {
    if (Predicate_Type.featOkTst && ((Predicate_Type)jcasType).casFeat_determiner == null)
      jcasType.jcas.throwFeatMissing("determiner", "org.oaqa.model.Predicate");
    jcasType.ll_cas.ll_setStringValue(addr, ((Predicate_Type)jcasType).casFeatCode_determiner, v);}    
  }

    