package com.fr.form.main;

import com.fr.form.stable.FormActor;
import com.fr.form.stable.FormActorFactory;
import com.fr.general.GeneralContext;
import com.fr.plugin.ExtraXMLFileManager;
import com.fr.stable.EnvChangedListener;
import com.fr.stable.fun.Level;
import com.fr.stable.plugin.ExtraFormClassManagerProvider;
import com.fr.stable.plugin.PluginSimplify;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;

public class ExtraFormClassManager extends ExtraXMLFileManager implements ExtraFormClassManagerProvider {
  private static final String XML_TAG = "ExtraFormClassManager";
  
  private static ExtraFormClassManager reportClassManager;
  
  public static synchronized ExtraFormClassManager getInstance() {
    if (reportClassManager == null) {
      reportClassManager = new ExtraFormClassManager();
      reportClassManager.readXMLFile();
    } 
    return reportClassManager;
  }
  
  private static synchronized void envChanged() {
    reportClassManager = null;
  }
  
  public String fileName() {
    return "form.xml";
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    readXML(paramXMLableReader, null, PluginSimplify.NULL);
  }
  
  protected void readSpecific(String paramString, Level paramLevel, PluginSimplify paramPluginSimplify, XMLableReader paramXMLableReader) throws Exception {
    if (paramString.equals("FormActor")) {
      validAPILevel(paramLevel, 1, paramPluginSimplify.getPluginName());
      FormActor formActor = (FormActor)paramLevel;
      FormActorFactory.registerActor(formActor.panelType(), formActor);
    } 
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {}
  
  static {
    GeneralContext.addEnvChangedListener(new EnvChangedListener() {
          public void envChanged() {
            ExtraFormClassManager.envChanged();
          }
        });
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\main\ExtraFormClassManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */