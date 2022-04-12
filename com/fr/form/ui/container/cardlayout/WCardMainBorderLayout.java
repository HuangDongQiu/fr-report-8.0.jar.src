package com.fr.form.ui.container.cardlayout;

import com.fr.form.ui.LayoutBorderStyle;
import com.fr.form.ui.Widget;
import com.fr.form.ui.WidgetTitle;
import com.fr.form.ui.container.WBorderLayout;
import com.fr.form.ui.container.WCardLayout;
import com.fr.general.Background;
import com.fr.general.FRLogger;
import com.fr.json.JSONArray;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.web.Repository;

public class WCardMainBorderLayout extends WBorderLayout {
  public static final int TAB_HEIGHT = 36;
  
  private boolean WEB_WIDGET_INITIALIZED = false;
  
  public WCardMainBorderLayout() {
    setNorthSize(36);
    clearMargin();
  }
  
  public WCardTitleLayout getTitlePart() {
    return (WCardTitleLayout)getLayoutWidget("North");
  }
  
  public WCardLayout getCardPart() {
    return (WCardLayout)getLayoutWidget("Center");
  }
  
  public String getXType() {
    return "cardborder";
  }
  
  public int getNorthSize() {
    return 36;
  }
  
  public String getNorthTitle() {
    return "";
  }
  
  public JSONObject createJSONConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    prepareChildStyle();
    return paramRepository.getDevice().isMobile() ? createMobileJSONConfig(paramRepository, paramCalculator, paramNodeVisitor) : super.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
  }
  
  private JSONObject createMobileJSONConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    JSONObject jSONObject = new JSONObject();
    jSONObject.put("type", getXType());
    if (this.widgetName != null)
      jSONObject.put("widgetName", this.widgetName.toUpperCase()); 
    if (this.background != null)
      jSONObject.put("widgetBackground", this.background.toJSONObject()); 
    if (this.borderStyle != null)
      this.borderStyle.createJSONConfig(paramRepository, jSONObject); 
    if (getTitlePart() != null && getCardPart() != null) {
      WCardTitleLayout wCardTitleLayout = getTitlePart();
      WCardTagLayout wCardTagLayout = wCardTitleLayout.getTagPart();
      WCardLayout wCardLayout = getCardPart();
      if (wCardTagLayout != null) {
        int i = wCardTagLayout.getWidgetCount();
        int j = wCardLayout.getWidgetCount();
        if (i == j) {
          JSONArray jSONArray1 = JSONArray.create();
          for (byte b = 0; b < j; b++) {
            JSONObject jSONObject1 = wCardTagLayout.getSwitchButton(b).createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
            JSONObject jSONObject2 = wCardLayout.getWidget(b).createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
            JSONObject jSONObject3 = JSONObject.create();
            jSONObject3.put("tag", jSONObject1);
            jSONObject3.put("content", jSONObject2);
            jSONArray1.put(jSONObject3);
          } 
          jSONObject.put("item", jSONArray1);
          JSONArray jSONArray2 = wCardLayout.createJSONListener(paramRepository);
          if (jSONArray2.length() > 0)
            jSONObject.put("listeners", jSONArray2); 
        } else {
          FRLogger.getLogger().error("error!numbers of cards and cardTags isn't equal.");
        } 
      } else {
        FRLogger.getLogger().error("CardTagLayout null.");
      } 
    } else {
      FRLogger.getLogger().error("titleLayout or cardLayout null.");
    } 
    return jSONObject;
  }
  
  private void prepareChildStyle() {
    if (this.WEB_WIDGET_INITIALIZED)
      return; 
    WCardLayout wCardLayout = getCardPart();
    LayoutBorderStyle layoutBorderStyle = wCardLayout.getBorderStyle();
    setBorderStyle(layoutBorderStyle);
    wCardLayout.setBorderStyle(new LayoutBorderStyle());
    int i = layoutBorderStyle.getType();
    if (i == 1) {
      prepareTitleConfig(layoutBorderStyle, wCardLayout);
    } else {
      removeAll();
      addCenter((Widget)wCardLayout);
    } 
    this.WEB_WIDGET_INITIALIZED = true;
  }
  
  private void prepareTitleConfig(LayoutBorderStyle paramLayoutBorderStyle, WCardLayout paramWCardLayout) {
    WidgetTitle widgetTitle = paramLayoutBorderStyle.getTitle();
    Background background = widgetTitle.getBackground();
    WCardTitleLayout wCardTitleLayout = getTitlePart();
    wCardTitleLayout.setBackground(background);
    WCardTagLayout wCardTagLayout = wCardTitleLayout.getTagPart();
    wCardTagLayout.setBackground(background);
    wCardTagLayout.setTitleFont(widgetTitle.getFrFont());
    ButtonNameWrapper buttonNameWrapper = getAllTagName();
    paramWCardLayout.setBtnsName(buttonNameWrapper);
  }
  
  private ButtonNameWrapper getAllTagName() {
    ButtonNameWrapper buttonNameWrapper = new ButtonNameWrapper();
    WCardTitleLayout wCardTitleLayout = getTitlePart();
    String str1 = wCardTitleLayout.getPreBtnName();
    String str2 = wCardTitleLayout.getNextBtnName();
    WCardTagLayout wCardTagLayout = wCardTitleLayout.getTagPart();
    wCardTagLayout.adjustPreferWidth();
    int i = 0;
    int j = wCardTagLayout.getWidgetCount();
    String[] arrayOfString = new String[j];
    for (byte b = 0; b < j; b++) {
      Widget widget = wCardTagLayout.getWidget(b);
      arrayOfString[b] = widget.getWidgetName();
      i += wCardTagLayout.getWidthAtWidget(widget);
    } 
    buttonNameWrapper.setAllTagName(arrayOfString);
    buttonNameWrapper.setPreBtnName(str1);
    buttonNameWrapper.setNextBtnName(str2);
    buttonNameWrapper.setTitleWidth(i);
    buttonNameWrapper.setTagLayoutName(wCardTagLayout.getWidgetName());
    return buttonNameWrapper;
  }
  
  public void resize(double paramDouble1, double paramDouble2, double paramDouble3) {
    WCardLayout wCardLayout = getCardPart();
    double d = (getTitlePart() == null) ? paramDouble2 : (paramDouble2 - 36.0D);
    wCardLayout.resize(paramDouble1, d, paramDouble3);
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\cardlayout\WCardMainBorderLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */