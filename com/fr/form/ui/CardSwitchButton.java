package com.fr.form.ui;

import com.fr.base.background.ColorBackground;
import com.fr.form.event.Listener;
import com.fr.general.Background;
import com.fr.general.FRFont;
import com.fr.js.JavaScript;
import com.fr.js.JavaScriptImpl;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.StringUtils;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.core.UUID;
import com.fr.stable.web.Repository;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;
import java.awt.Color;

public class CardSwitchButton extends FreeButton {
  public static final int DEF_WIDTH = 80;
  
  private static final String XML_TAG = "SwitchTagAttr";
  
  private static final String NAME_TAG = "layoutName";
  
  private static final String IDX_TAG = "index";
  
  private boolean isShowButton = false;
  
  private double opacity = 0.6D;
  
  private static final int START_INDEX = 3;
  
  private int index;
  
  private String cardLayoutName;
  
  public CardSwitchButton() {}
  
  public CardSwitchButton(String paramString) {
    this(0, paramString);
  }
  
  public CardSwitchButton(int paramInt, String paramString) {
    this.index = paramInt;
    this.cardLayoutName = paramString;
    initDesignerStyle(UUID.randomUUID().toString());
  }
  
  public String getXType() {
    return "cardswitch";
  }
  
  public void initDesignerStyle(String paramString) {
    setWidgetName(paramString);
    setFont(FRFont.getInstance());
  }
  
  public boolean isShowButton() {
    return this.isShowButton;
  }
  
  public void setShowButton(boolean paramBoolean) {
    this.isShowButton = paramBoolean;
  }
  
  public double getOpacity() {
    return this.opacity;
  }
  
  public void setOpacity(double paramDouble) {
    this.opacity = paramDouble;
  }
  
  private String getClickJs() {
    return (StringUtils.isEmpty(this.cardLayoutName) || this.index < 0) ? "" : ("this.options.form.getWidgetByName('" + this.cardLayoutName + "').showCardByIndex(" + this.index + ")");
  }
  
  public int getIndex() {
    return this.index;
  }
  
  public void setIndex(int paramInt) {
    this.index = (paramInt > 0) ? paramInt : 0;
  }
  
  public String getCardLayoutName() {
    return this.cardLayoutName;
  }
  
  public void setCardLayoutName(String paramString) {
    this.cardLayoutName = paramString;
  }
  
  public JSONObject createJSONConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    initWebStyle();
    JSONObject jSONObject = super.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
    jSONObject.put("opacity", this.opacity);
    return jSONObject;
  }
  
  private void initWebStyle() {
    Listener listener = new Listener("click");
    JavaScriptImpl javaScriptImpl = new JavaScriptImpl(getClickJs());
    listener.setAction((JavaScript)javaScriptImpl);
    addListener(listener);
    setInitialBackground((Background)ColorBackground.getInstance(Color.WHITE));
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    super.writeXML(paramXMLPrintWriter);
    paramXMLPrintWriter.startTAG("SwitchTagAttr");
    if (StringUtils.isNotEmpty(this.cardLayoutName))
      paramXMLPrintWriter.attr("layoutName", this.cardLayoutName); 
    if (this.index != 0)
      paramXMLPrintWriter.attr("index", this.index); 
    paramXMLPrintWriter.end();
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    super.readXML(paramXMLableReader);
    if (paramXMLableReader.isChildNode()) {
      String str = paramXMLableReader.getTagName();
      if (str.equals("SwitchTagAttr")) {
        this.cardLayoutName = paramXMLableReader.getAttrAsString("layoutName", "");
        this.index = paramXMLableReader.getAttrAsInt("index", 0);
      } 
    } 
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\CardSwitchButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */