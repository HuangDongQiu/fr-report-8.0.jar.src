package com.fr.form.main.mobile;

import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;

public class FormMobileAttr implements FormMobileAttrProvider {
  public static final String XML_TAG = "FormMobileAttr";
  
  private boolean refresh = false;
  
  public boolean isRefresh() {
    return this.refresh;
  }
  
  public void setRefresh(boolean paramBoolean) {
    this.refresh = paramBoolean;
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    this.refresh = paramXMLableReader.getAttrAsBoolean("refresh", false);
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    paramXMLPrintWriter.startTAG("FormMobileAttr").attr("refresh", this.refresh).end();
  }
  
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\main\mobile\FormMobileAttr.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
