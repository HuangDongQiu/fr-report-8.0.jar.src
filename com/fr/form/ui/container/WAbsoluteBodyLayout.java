package com.fr.form.ui.container;

import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.web.Repository;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;

public class WAbsoluteBodyLayout extends WAbsoluteLayout {
  private boolean appRelayout = true;
  
  public WAbsoluteBodyLayout() {}
  
  public WAbsoluteBodyLayout(String paramString) {
    super(paramString);
  }
  
  public boolean isAppRelayout() {
    return this.appRelayout;
  }
  
  public void setAppRelayout(boolean paramBoolean) {
    this.appRelayout = paramBoolean;
  }
  
  public JSONObject createJSONConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    JSONObject jSONObject = super.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
    jSONObject.put("appRelayout", this.appRelayout);
    return jSONObject;
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    super.readXML(paramXMLableReader);
    if (paramXMLableReader.isChildNode()) {
      String str = paramXMLableReader.getTagName();
      if ("AppRelayout".equals(str))
        setAppRelayout(paramXMLableReader.getAttrAsBoolean("appRelayout", true)); 
    } 
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    super.writeXML(paramXMLPrintWriter);
    paramXMLPrintWriter.startTAG("AppRelayout").attr("appRelayout", this.appRelayout).end();
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\WAbsoluteBodyLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */