package com.fr.form.ui.container.cardlayout;

import com.fr.form.ui.Label;
import com.fr.form.ui.Widget;
import com.fr.form.ui.container.WBorderLayout;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.StringUtils;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.core.UUID;
import com.fr.stable.web.Repository;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;

public class WCardTitleLayout extends WBorderLayout {
  private static final int SWITCH_SIZE = 15;
  
  private static final int NORTH_SIZE = 5;
  
  private static final String XML_TAG = "CardTitleLayout";
  
  private static final String NAME_TAG = "layoutName";
  
  private static final int ADDBUTON_WIDTH = 25;
  
  private String cardName;
  
  private transient String preBtnName;
  
  private transient String nextBtnName;
  
  public WCardTitleLayout() {
    this("");
  }
  
  public WCardTitleLayout(String paramString) {
    this.cardName = paramString;
    setEastSize(25);
    this.preBtnName = UUID.randomUUID().toString();
    this.nextBtnName = UUID.randomUUID().toString();
    clearMargin();
  }
  
  public String getCardName() {
    return this.cardName;
  }
  
  public void setCardName(String paramString) {
    this.cardName = paramString;
  }
  
  public WCardTagLayout getTagPart() {
    return (WCardTagLayout)getLayoutWidget("Center");
  }
  
  public String getPreBtnName() {
    return this.preBtnName;
  }
  
  public String getNextBtnName() {
    return this.nextBtnName;
  }
  
  public JSONObject createJSONConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    initWebStyle();
    return super.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
  }
  
  private void initNorthSize() {
    setNorthSize(5);
    Label label = new Label();
    label.setBackground(getBackground());
    addNorth((Widget)label);
  }
  
  private void initEastSize() {
    setEastSize(15);
    CardNextTabButton cardNextTabButton = new CardNextTabButton(this.cardName);
    cardNextTabButton.setWidgetName(this.nextBtnName);
    addEast((Widget)cardNextTabButton);
  }
  
  private void initWestSize() {
    setWestSize(15);
    CardPreviousTabButton cardPreviousTabButton = new CardPreviousTabButton(this.cardName);
    cardPreviousTabButton.setWidgetName(this.preBtnName);
    addWest((Widget)cardPreviousTabButton);
  }
  
  private void initWebStyle() {
    initNorthSize();
    initEastSize();
    initWestSize();
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    super.writeXML(paramXMLPrintWriter);
    paramXMLPrintWriter.startTAG("CardTitleLayout");
    if (StringUtils.isNotEmpty(this.cardName))
      paramXMLPrintWriter.attr("layoutName", this.cardName); 
    paramXMLPrintWriter.end();
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    super.readXML(paramXMLableReader);
    if (paramXMLableReader.isChildNode()) {
      String str = paramXMLableReader.getTagName();
      if (str.equals("CardTitleLayout"))
        this.cardName = paramXMLableReader.getAttrAsString("layoutName", ""); 
    } 
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\cardlayout\WCardTitleLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */