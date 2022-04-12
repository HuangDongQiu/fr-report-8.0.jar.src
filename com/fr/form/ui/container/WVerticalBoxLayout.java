package com.fr.form.ui.container;

import com.fr.base.BaseXMLUtils;
import com.fr.base.DynamicNumberList;
import com.fr.form.ui.Widget;
import com.fr.general.ComparatorUtils;
import com.fr.general.Inter;
import com.fr.json.JSONArray;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.web.Repository;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;
import java.awt.Dimension;

public class WVerticalBoxLayout extends WLayout {
  public static final int DEFAULT_HEIGHT = 22;
  
  private DynamicNumberList widgetsHeightList_DEC;
  
  public WVerticalBoxLayout() {
    this(0, 0);
  }
  
  public WVerticalBoxLayout(int paramInt1, int paramInt2) {
    setHgap(paramInt1);
    setVgap(paramInt2);
    this.widgetsHeightList_DEC = new DynamicNumberList(22);
  }
  
  public String getXType() {
    return "vertical";
  }
  
  public void addWidget(Widget paramWidget) {
    addWidget(paramWidget, -1);
  }
  
  public void addWidget(Widget paramWidget, int paramInt) {
    if (paramInt > -1 && paramInt < this.widgetList.size()) {
      this.widgetList.add(paramInt, paramWidget);
      this.widgetsHeightList_DEC.insert(paramInt);
    } else {
      this.widgetList.add(paramWidget);
    } 
  }
  
  public void removeWidget(Widget paramWidget) {
    int i = getWidgetIndex(paramWidget);
    super.removeWidget(paramWidget);
    this.widgetsHeightList_DEC.remove(i);
  }
  
  public int getHeightAtWidget(Widget paramWidget) {
    byte b = 0;
    int i = getWidgetCount();
    while (b < i) {
      if (ComparatorUtils.equals(paramWidget, getWidget(b)))
        return this.widgetsHeightList_DEC.get(b); 
      b++;
    } 
    return 22;
  }
  
  public void setHeightAtWidget(Widget paramWidget, int paramInt) {
    byte b = 0;
    int i = getWidgetCount();
    while (b < i) {
      if (ComparatorUtils.equals(paramWidget, getWidget(b))) {
        this.widgetsHeightList_DEC.set(b, paramInt);
        break;
      } 
      b++;
    } 
  }
  
  public String getLayoutToolTip() {
    return Inter.getLocText("WLayout-Vertical-ToolTips");
  }
  
  public Dimension getMinDesignSize() {
    int i = 0;
    int j = 0;
    byte b = 0;
    int k = getWidgetCount();
    while (b < k) {
      Widget widget = getWidget(b);
      if (widget.isVisible()) {
        j += this.widgetsHeightList_DEC.get(b);
        if (widget instanceof WLayout) {
          int m = (((WLayout)widget).getMinDesignSize()).width;
          if (m > i)
            i = m; 
        } 
      } 
      b++;
    } 
    return new Dimension(i, j);
  }
  
  protected JSONArray createJSONItems(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    JSONArray jSONArray = new JSONArray();
    byte b = 0;
    int i = this.widgetList.size();
    while (b < i) {
      Widget widget = this.widgetList.get(b);
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("height", getHeightAtWidget(widget));
      jSONObject.put("el", widget.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor));
      jSONArray.put(jSONObject);
      b++;
    } 
    return jSONArray;
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    super.readXML(paramXMLableReader);
    if (paramXMLableReader.isChildNode()) {
      String str = paramXMLableReader.getTagName();
      if (ComparatorUtils.equals(str, "RowHeight"))
        this.widgetsHeightList_DEC = BaseXMLUtils.readDynamicNumberList(paramXMLableReader); 
    } 
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    super.writeXML(paramXMLPrintWriter);
    paramXMLPrintWriter.startTAG("RowHeight");
    BaseXMLUtils.writeDynamicNumberList(paramXMLPrintWriter, this.widgetsHeightList_DEC);
    paramXMLPrintWriter.end();
  }
  
  public Object clone() throws CloneNotSupportedException {
    WVerticalBoxLayout wVerticalBoxLayout = (WVerticalBoxLayout)super.clone();
    wVerticalBoxLayout.widgetsHeightList_DEC = (DynamicNumberList)this.widgetsHeightList_DEC.clone();
    return wVerticalBoxLayout;
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\WVerticalBoxLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */