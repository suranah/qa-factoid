

/* First created by JCasGen Tue Mar 02 13:59:19 EST 2010 */
package org.oaqa.model;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** A semantic role label.
 * Updated by JCasGen Tue Mar 16 14:06:28 EDT 2010
 * XML source: C:/Users/hideki/Research/workspace/OAQA Example/descriptors/OAQATypes.xml
 * @generated */
public class SemanticRole extends OAQAAnnotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(SemanticRole.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected SemanticRole() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public SemanticRole(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public SemanticRole(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public SemanticRole(JCas jcas, int begin, int end) {
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
  //* Feature: label

  /** getter for label - gets The semantic role label.
   * @generated */
  public String getLabel() {
    if (SemanticRole_Type.featOkTst && ((SemanticRole_Type)jcasType).casFeat_label == null)
      jcasType.jcas.throwFeatMissing("label", "org.oaqa.model.SemanticRole");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SemanticRole_Type)jcasType).casFeatCode_label);}
    
  /** setter for label - sets The semantic role label. 
   * @generated */
  public void setLabel(String v) {
    if (SemanticRole_Type.featOkTst && ((SemanticRole_Type)jcasType).casFeat_label == null)
      jcasType.jcas.throwFeatMissing("label", "org.oaqa.model.SemanticRole");
    jcasType.ll_cas.ll_setStringValue(addr, ((SemanticRole_Type)jcasType).casFeatCode_label, v);}    
  }

    