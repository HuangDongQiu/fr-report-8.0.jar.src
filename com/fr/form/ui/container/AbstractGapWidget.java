package com.fr.form.ui.container;

import com.fr.form.ui.AbstractBorderStyleWidget;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.web.Repository;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;

public abstract class AbstractGapWidget extends AbstractBorderStyleWidget {
  protected int vgap;
  
  protected int hgap;
  
  protected int compInterval;
  
  public abstract String getXType();
  
  public abstract boolean isEditor();
  
  public abstract String[] supportedEvents();
  
  public int getVgap() {
    return this.vgap;
  }
  
  public void setVgap(int paramInt) {
    this.vgap = paramInt;
  }
  
  public int getHgap() {
    return this.hgap;
  }
  
  public void setHgap(int paramInt) {
    this.hgap = paramInt;
  }
  
  public int getCompInterval() {
    return this.compInterval;
  }
  
  public void setCompInterval(int paramInt) {
    this.compInterval = paramInt;
  }
  
  public JSONObject createJSONConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    JSONObject jSONObject = super.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
    jSONObject.put("vgap", this.vgap);
    jSONObject.put("hgap", this.hgap);
    jSONObject.put("compInterval", this.compInterval);
    return jSONObject;
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    super.readXML(paramXMLableReader);
    if (paramXMLableReader.isChildNode()) {
      String str = paramXMLableReader.getTagName();
      if ("LCAttr".equals(str)) {
        this.vgap = paramXMLableReader.getAttrAsInt("vgap", 0);
        this.hgap = paramXMLableReader.getAttrAsInt("hgap", 0);
        setCompInterval(paramXMLableReader.getAttrAsInt("compInterval", 0));
      } 
    } 
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    super.writeXML(paramXMLPrintWriter);
    paramXMLPrintWriter.startTAG("LCAttr");
    paramXMLPrintWriter.attr("vgap", this.vgap);
    paramXMLPrintWriter.attr("hgap", this.hgap);
    paramXMLPrintWriter.attr("compInterval", this.compInterval);
    paramXMLPrintWriter.end();
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\AbstractGapWidget.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
