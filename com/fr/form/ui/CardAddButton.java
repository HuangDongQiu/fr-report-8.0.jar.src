package com.fr.form.ui;

import com.fr.stable.StringUtils;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;

public class CardAddButton extends FreeButton {
  public static final int DEF_WIDTH = 30;
  
  private static final String XML_TAG = "AddTagAttr";
  
  private static final String NAME_TAG = "layoutName";
  
  private static final String TAB_NAME = "Add";
  
  private String cardLayoutName;
  
  public CardAddButton() {}
  
  public CardAddButton(String paramString) {
    this(paramString, "Add");
  }
  
  public CardAddButton(String paramString1, String paramString2) {
    this.cardLayoutName = paramString1;
    setWidgetName(paramString2);
  }
  
  public String getCardLayoutName() {
    return this.cardLayoutName;
  }
  
  public void setCardLayoutName(String paramString) {
    this.cardLayoutName = paramString;
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    super.writeXML(paramXMLPrintWriter);
    paramXMLPrintWriter.startTAG("AddTagAttr");
    if (StringUtils.isNotEmpty(this.cardLayoutName))
      paramXMLPrintWriter.attr("layoutName", this.cardLayoutName); 
    paramXMLPrintWriter.end();
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    super.readXML(paramXMLableReader);
    if (paramXMLableReader.isChildNode()) {
      String str = paramXMLableReader.getTagName();
      if (str.equals("AddTagAttr"))
        this.cardLayoutName = paramXMLableReader.getAttrAsString("layoutName", ""); 
    } 
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\CardAddButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
