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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.impl.XmiCasSerializer;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;
import org.apache.uima.util.XMLSerializer;
import org.xml.sax.SAXException;

/**
 * A simple CAS consumer that writes the CAS to XMI format.
 * <p>
 * This CAS Consumer takes one parameter:
 * <ul>
 * <li><code>OutputDirectory</code> - path to directory into which output files will be written</li>
 * </ul>
 */
public class ZipXmiWriterCasConsumer extends CasConsumer_ImplBase {
  /**
   * Name of configuration parameter that must be set to the path of a directory into which the
   * output files will be written.
   */
  public static final String PARAM_OUTPUTDIR = "OutputDirectory";

  private File mOutputDir;

  private int mDocNum;

  public void initialize() throws ResourceInitializationException {
    mDocNum = 0;
    mOutputDir = new File((String) getConfigParameterValue(PARAM_OUTPUTDIR));
    if (!mOutputDir.exists()) {
      mOutputDir.mkdirs();
    }
    
    // delete all .xmi files in the output dir
    for (File f : mOutputDir.listFiles()) {
      if (f.getName().endsWith(".xmi")) {
        f.delete();
      }
    }
  }

  /**
   * Processes the CAS which was populated by the TextAnalysisEngines. <br>
   * In this case, the CAS is converted to XMI and written into the output file .
   * 
   * @param aCAS
   *          a CAS which has been populated by the TAEs
   * 
   * @throws ResourceProcessException
   *           if there is an error in processing the Resource
   * 
   * @see org.apache.uima.collection.base_cpm.CasObjectProcessor#processCas(org.apache.uima.cas.CAS)
   */
  public void processCas(CAS aCAS) throws ResourceProcessException {
    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new ResourceProcessException(e);
    }

    // retreive the filename of the input file from the CAS
    FSIterator<?> it = jcas.getAnnotationIndex(SourceDocumentInformation.type).iterator();
    File outFile = null;
    if (it.hasNext()) {
      SourceDocumentInformation fileLoc = (SourceDocumentInformation) it.next();
      File inFile;
      try {
        inFile = new File(new URL(fileLoc.getUri()).getPath());
        String outFileName = inFile.getName();
        if (fileLoc.getOffsetInSource() > 0) {
          outFileName += ("_" + fileLoc.getOffsetInSource());
        }
        outFileName += ".xmi";
        outFile = new File(mOutputDir, outFileName);
      } catch (MalformedURLException e1) {
        // invalid URL, use default processing below
      }
    }
    if (outFile == null) {
      outFile = new File(mOutputDir, "doc" + mDocNum++);
    }
    // serialize XCAS and write to output file
    try {
      writeXmi(jcas.getCas(), outFile);
    } catch (IOException e) {
      throw new ResourceProcessException(e);
    } catch (SAXException e) {
      throw new ResourceProcessException(e);
    }
  }
  
  /**
   * Serialize a CAS to a file in XMI format
   * 
   * @param aCas
   *          CAS to serialize
   * @param name
   *          output file
   * @throws SAXException
   * @throws Exception
   * 
   * @throws ResourceProcessException
   */
  private void writeXmi(CAS aCas, File name) throws IOException, SAXException {
    FileOutputStream out = null;

    try {
      // Redirect noisy stderr messages
      System.err.flush();
      PrintStream _err = System.err;
      ByteArrayOutputStream bErr = new ByteArrayOutputStream();//don't need to close
      System.setErr(new PrintStream(bErr));
      
      // write XMI
      out = new FileOutputStream(name);
      XmiCasSerializer ser = new XmiCasSerializer(aCas.getTypeSystem());
      XMLSerializer xmlSer = new XMLSerializer(out, false);
      ser.serialize(aCas, xmlSer.getContentHandler());

      // Get the original stderr back
      System.setErr(_err);
    } finally {
      if (out != null) {
        out.close();
      }
    }
  }

  /**
   * We want to zip up the result files in the end.
   * Thus we use this destroy() as the finalize() that is guaranteed to be called.  
   */
  @Override
  public void destroy() {
    
    String date = TimeKeeper.getInstance().getBeginDate();
    File outputDir = new File(mOutputDir,"oaqa-"+date);
    if (!outputDir.exists()) outputDir.mkdirs();
    File xmiZipPath = new File(outputDir, "xmi.zip");
    
    OutputStream os = null;
    ZipOutputStream zipos = null;
    try {
      os = new FileOutputStream(xmiZipPath);
      zipos = new ZipOutputStream(os);

      for (File f : mOutputDir.listFiles()) {
        if (!f.getName().endsWith(".xmi")) {
          continue; // only zip xmi files
        }
        ZipEntry entry = new ZipEntry(f.getName());
        entry.setMethod(ZipOutputStream.DEFLATED);
        zipos.putNextEntry(entry);
        byte[] bytearray = getByteArrayFromFile(f);
        zipos.write(bytearray);
        zipos.closeEntry();
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        zipos.close();
        os.close();
      } catch (Exception e2) {}
    }
    
  }
  
  // This method can be moved to more domain independent class
  private static byte[] getByteArrayFromFile(File file) throws IOException {
    InputStream is = null;
    byte[] bytes;
    try {
      is = new FileInputStream(file);
      long length = file.length();
  
      bytes = new byte[(int) length];
  
      int offset = 0;
      int numRead = 0;
      while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
        offset += numRead;
      }
  
      if (offset < bytes.length) {
        throw new IOException("Could not completely read file " + file.getName());
      }
    } finally {
      if (is!=null) {
        is.close();
      }
    }
    return bytes;
  }
  
}
