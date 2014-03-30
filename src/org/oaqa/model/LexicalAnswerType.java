

/* First created by JCasGen Tue Mar 02 13:59:18 EST 2010 */
package org.oaqa.model;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** The lexical answer type found in the question.
 * Updated by JCasGen Tue Mar 16 14:06:28 EDT 2010
 * XML source: C:/Users/hideki/Research/workspace/OAQA Example/descriptors/OAQATypes.xml
 * @generated */
public class LexicalAnswerType extends OAQAAnnotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(LexicalAnswerType.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected LexicalAnswerType() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public LexicalAnswerType(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public LexicalAnswerType(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public LexicalAnswerType(JCas jcas, int begin, int end) {
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
  //* Feature: predicate

  /** getter for predicate - gets The corresponding predicate for the LAT.
   * @generated */
  public Predicate getPredicate() {
    if (LexicalAnswerType_Type.featOkTst && ((LexicalAnswerType_Type)jcasType).casFeat_predicate == null)
      jcasType.jcas.throwFeatMissing("predicate", "org.oaqa.model.LexicalAnswerType");
    return (Predicate)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((LexicalAnswerType_Type)jcasType).casFeatCode_predicate)));}
    
  /** setter for predicate - sets The corresponding predicate for the LAT. 
   * @generated */
  public void setPredicate(Predicate v) {
    if (LexicalAnswerType_Type.featOkTst && ((LexicalAnswerType_Type)jcasType).casFeat_predicate == null)
      jcasType.jcas.throwFeatMissing("predicate", "org.oaqa.model.LexicalAnswerType");
    jcasType.ll_cas.ll_setRefValue(addr, ((LexicalAnswerType_Type)jcasType).casFeatCode_predicate, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: label

  /** getter for label - gets The normalized LAT string.
   * @generated */
  public String getLabel() {
    if (LexicalAnswerType_Type.featOkTst && ((LexicalAnswerType_Type)jcasType).casFeat_label == null)
      jcasType.jcas.throwFeatMissing("label", "org.oaqa.model.LexicalAnswerType");
    return jcasType.ll_cas.ll_getStringValue(addr, ((LexicalAnswerType_Type)jcasType).casFeatCode_label);}
    
  /** setter for label - sets The normalized LAT string. 
   * @generated */
  public void setLabel(String v) {
    if (LexicalAnswerType_Type.featOkTst && ((LexicalAnswerType_Type)jcasType).casFeat_label == null)
      jcasType.jcas.throwFeatMissing("label", "org.oaqa.model.LexicalAnswerType");
    jcasType.ll_cas.ll_setStringValue(addr, ((LexicalAnswerType_Type)jcasType).casFeatCode_label, v);}    
  }

    