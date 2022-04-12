package com.fr.form.ui.container.cardlayout;

import com.fr.form.ui.CardSwitchButton;
import com.fr.form.ui.container.WFitLayout;
import com.fr.stable.StringUtils;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;

public class WTabFitLayout extends WFitLayout {
  private static final String XML_TAG = "tabFitAttr";
  
  private static final String IDX_TAG = "index";
  
  private static final String TABNAMEIDX_TAG = "tabNameIndex";
  
  private int index;
  
  private CardSwitchButton currentCard;
  
  private int tabNameIndex;
  
  public WTabFitLayout() {
    this("");
  }
  
  public WTabFitLayout(String paramString) {
    this(paramString, 0);
  }
  
  public WTabFitLayout(String paramString, int paramInt) {
    this(paramString, 0, (CardSwitchButton)null);
  }
  
  public WTabFitLayout(String paramString, int paramInt, CardSwitchButton paramCardSwitchButton) {
    if (StringUtils.isNotEmpty(paramString))
      setWidgetName(paramString); 
    setIndex(paramInt);
    setCurrentCard(paramCardSwitchButton);
    clearMargin();
  }
  
  public CardSwitchButton getCurrentCard() {
    return this.currentCard;
  }
  
  public void setCurrentCard(CardSwitchButton paramCardSwitchButton) {
    this.currentCard = paramCardSwitchButton;
  }
  
  public int getIndex() {
    return this.index;
  }
  
  public void setIndex(int paramInt) {
    this.index = paramInt;
  }
  
  public int getTabNameIndex() {
    return this.tabNameIndex;
  }
  
  public void setTabNameIndex(int paramInt) {
    this.tabNameIndex = paramInt;
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    super.writeXML(paramXMLPrintWriter);
    paramXMLPrintWriter.startTAG("tabFitAttr");
    paramXMLPrintWriter.attr("index", this.index);
    paramXMLPrintWriter.attr("tabNameIndex", this.tabNameIndex);
    paramXMLPrintWriter.end();
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    super.readXML(paramXMLableReader);
    if (paramXMLableReader.isChildNode()) {
      String str = paramXMLableReader.getTagName();
      if (str.equals("tabFitAttr")) {
        this.index = paramXMLableReader.getAttrAsInt("index", 0);
        this.tabNameIndex = paramXMLableReader.getAttrAsInt("tabNameIndex", 0);
      } 
    } 
  }
  
  protected void writeLayoutTypeAttr(XMLPrintWriter paramXMLPrintWriter) {}
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\cardlayout\WTabFitLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */