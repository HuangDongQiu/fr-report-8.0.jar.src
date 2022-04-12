package com.fr.form.ui.container;

import com.fr.form.main.Form;
import com.fr.form.main.WidgetGather;
import com.fr.form.main.WidgetGatherAdapter;
import com.fr.form.ui.LayoutBorderStyle;
import com.fr.form.ui.PaddingMargin;
import com.fr.form.ui.Widget;
import com.fr.form.ui.WidgetXmlUtils;
import com.fr.general.Background;
import com.fr.general.ComparatorUtils;
import com.fr.general.xml.GeneralXMLTools;
import com.fr.json.JSONArray;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.web.Repository;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLable;
import com.fr.stable.xml.XMLableReader;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public abstract class WLayout extends AbstractGapWidget {
  public static final String EVENT_AFTERINIT = "afterinit";
  
  public static final String EVENT_CLICK = "click";
  
  public static final String XML_TAG = "Layout";
  
  public static final int DEFAULT_WIDTH = 960;
  
  public static final int DEFAULT_HEIGHT = 540;
  
  public static int MIN_WIDTH = 36;
  
  public static int MIN_HEIGHT = 21;
  
  protected List widgetList = new ArrayList();
  
  private boolean scrollable;
  
  public boolean isEditor() {
    return false;
  }
  
  public String[] supportedEvents() {
    return new String[] { "afterinit", "click" };
  }
  
  public void addWidget(Widget paramWidget) {
    this.widgetList.add(paramWidget);
  }
  
  public void removeWidget(Widget paramWidget) {
    this.widgetList.remove(paramWidget);
  }
  
  public void removeAll() {
    this.widgetList.clear();
  }
  
  public int getWidgetCount() {
    return this.widgetList.size();
  }
  
  public Widget getWidget(int paramInt) {
    return this.widgetList.get(paramInt);
  }
  
  public Widget getWidget(String paramString) {
    for (byte b = 0; b < getWidgetCount(); b++) {
      Widget widget = getWidget(b);
      if (ComparatorUtils.equals(widget.getWidgetName(), paramString))
        return widget; 
    } 
    return null;
  }
  
  public int getWidgetIndex(Widget paramWidget) {
    for (byte b = 0; b < getWidgetCount(); b++) {
      if (ComparatorUtils.equals(paramWidget, getWidget(b)))
        return b; 
    } 
    return -1;
  }
  
  public void setWidgetIndex(Widget paramWidget, int paramInt) {
    if (paramInt < 0 || paramInt > getWidgetCount() - 1)
      return; 
    int i = getWidgetIndex(paramWidget);
    if (i < 0 || i > getWidgetCount() - 1)
      return; 
    Widget widget = getWidget(paramInt);
    this.widgetList.set(paramInt, paramWidget);
    this.widgetList.set(i, widget);
  }
  
  public void replace(Widget paramWidget1, Widget paramWidget2) {
    int i;
    if ((i = getWidgetIndex(paramWidget2)) != -1)
      this.widgetList.set(i, paramWidget1); 
  }
  
  public boolean isScrollable() {
    return this.scrollable;
  }
  
  public void setScrollable(boolean paramBoolean) {
    this.scrollable = paramBoolean;
  }
  
  public abstract String getLayoutToolTip();
  
  public abstract Dimension getMinDesignSize();
  
  public boolean canCurrentMarginAvailable(PaddingMargin paramPaddingMargin) {
    return true;
  }
  
  public JSONObject createJSONConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    JSONObject jSONObject = super.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
    jSONObject.put("scrollable", this.scrollable);
    jSONObject.put("items", createJSONItems(paramRepository, paramCalculator, paramNodeVisitor));
    if (paramNodeVisitor != null) {
      jSONObject.put("charts", paramNodeVisitor.findsToJSONArray("simplechart"));
      jSONObject.put("reports", paramNodeVisitor.findsToJSONArray("report"));
    } 
    return jSONObject;
  }
  
  protected JSONArray createJSONItems(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    JSONArray jSONArray = new JSONArray();
    byte b = 0;
    int i = this.widgetList.size();
    while (b < i) {
      JSONObject jSONObject = ((Widget)this.widgetList.get(b)).createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
      if (jSONObject != JSONObject.EMPTY)
        jSONArray.put(jSONObject); 
      b++;
    } 
    return jSONArray;
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    super.readXML(paramXMLableReader);
    if (paramXMLableReader.isChildNode()) {
      String str = paramXMLableReader.getTagName();
      if ("Widget".equals(str)) {
        Widget widget = WidgetXmlUtils.readWidget(paramXMLableReader);
        if (widget != null)
          this.widgetList.add(widget); 
      } else if ("ScrollAttr".equals(str)) {
        this.scrollable = paramXMLableReader.getAttrAsBoolean("scrollable", false);
      } 
    } 
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    super.writeXML(paramXMLPrintWriter);
    if (this.widgetList != null) {
      byte b = 0;
      int i = this.widgetList.size();
      while (b < i) {
        GeneralXMLTools.writeXMLable(paramXMLPrintWriter, (XMLable)this.widgetList.get(b), "Widget");
        b++;
      } 
    } 
    if (this.scrollable)
      paramXMLPrintWriter.startTAG("ScrollAttr").attr("scrollable", this.scrollable).end(); 
  }
  
  public int hashCode() {
    null = 7;
    null = 97 * null + ((this.widgetList != null) ? this.widgetList.hashCode() : 0);
    null = 97 * null + ((this.margin != null) ? this.margin.hashCode() : 0);
    null = 97 * null + ((this.background != null) ? this.background.hashCode() : 0);
    null = 97 * null + ((this.borderStyle != null) ? this.borderStyle.hashCode() : 0);
    null = 97 * null + this.vgap;
    null = 97 * null + this.hgap;
    return 97 * null + (this.scrollable ? 1 : 3);
  }
  
  public boolean equals(Object paramObject) {
    return (paramObject instanceof WLayout && super.equals(paramObject) && ComparatorUtils.equals(((WLayout)paramObject).margin, this.margin) && ComparatorUtils.equals(((WLayout)paramObject).background, this.background) && ComparatorUtils.equals(((WLayout)paramObject).borderStyle, this.borderStyle) && ComparatorUtils.equals(((WLayout)paramObject).widgetList, this.widgetList) && ((WLayout)paramObject).vgap == this.vgap && ((WLayout)paramObject).hgap == this.hgap && this.scrollable == ((WLayout)paramObject).scrollable);
  }
  
  public Object clone() throws CloneNotSupportedException {
    WLayout wLayout = (WLayout)super.clone();
    if (this.widgetList != null) {
      wLayout.widgetList = new ArrayList();
      byte b = 0;
      int i = this.widgetList.size();
      while (b < i) {
        wLayout.widgetList.add(((Widget)this.widgetList.get(b)).clone());
        b++;
      } 
    } 
    cloneExceptName(wLayout);
    return wLayout;
  }
  
  private void cloneExceptName(WLayout paramWLayout) throws CloneNotSupportedException {
    if (this.background != null)
      paramWLayout.background = (Background)this.background.clone(); 
    if (this.borderStyle != null)
      paramWLayout.borderStyle = (LayoutBorderStyle)this.borderStyle.clone(); 
  }
  
  public void toImage(Calculator paramCalculator, Rectangle paramRectangle, Graphics paramGraphics) {
    byte b = 0;
    int i = getWidgetCount();
    while (b < i) {
      getWidget(b).toImage(paramCalculator, paramRectangle, paramGraphics);
      b++;
    } 
  }
  
  public void createPara4Mobile(Repository paramRepository, JSONObject paramJSONObject, Form paramForm) throws JSONException {}
  
  public void resize(final double width, final double height, final double fontScale) {
    if (width > 0.0D || height > 0.0D)
      Form.traversalWidget((Widget)this, (WidgetGather)new WidgetGatherAdapter() {
            public boolean dealWithAllCards() {
              return true;
            }
            
            public void dealWith(Widget param1Widget) {
              param1Widget.resize(width, height, fontScale);
            }
          }WFitLayout.class); 
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\WLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */