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

public class WHorizontalBoxLayout extends WLayout {
  public static final int LEFT = 0;
  
  public static final int CENTER = 1;
  
  public static final int RIGHT = 2;
  
  private static final int DEFAULT_WIDTH = 80;
  
  public static final int DEFAULT_HGAP = 0;
  
  public static final int DEFAULT_VGAP = 0;
  
  private int alignment;
  
  private DynamicNumberList widgetsWidthList_DEC;
  
  public WHorizontalBoxLayout() {
    this(1);
  }
  
  public WHorizontalBoxLayout(int paramInt) {
    this(paramInt, 0, 0);
  }
  
  public WHorizontalBoxLayout(int paramInt1, int paramInt2, int paramInt3) {
    setAlignment(paramInt1);
    setHgap(paramInt2);
    setVgap(paramInt3);
    this.widgetsWidthList_DEC = new DynamicNumberList(80);
  }
  
  public int getAlignment() {
    return this.alignment;
  }
  
  public void setAlignment(int paramInt) {
    this.alignment = paramInt;
  }
  
  public String getXType() {
    return "horizontal";
  }
  
  public void addWidget(Widget paramWidget) {
    addWidget(paramWidget, -1);
  }
  
  public void addWidget(Widget paramWidget, int paramInt) {
    if (paramInt > -1 && paramInt < this.widgetList.size()) {
      this.widgetList.add(paramInt, paramWidget);
      this.widgetsWidthList_DEC.insert(paramInt);
    } else {
      this.widgetList.add(paramWidget);
    } 
  }
  
  public void addWidthWidget(Widget paramWidget, int paramInt) {
    addWidget(paramWidget);
    this.widgetsWidthList_DEC.set(this.widgetList.size() - 1, paramInt);
  }
  
  public void removeWidget(Widget paramWidget) {
    int i = getWidgetIndex(paramWidget);
    super.removeWidget(paramWidget);
    this.widgetsWidthList_DEC.remove(i);
  }
  
  public int getWidthAtWidget(Widget paramWidget) {
    byte b = 0;
    int i = getWidgetCount();
    while (b < i) {
      if (ComparatorUtils.equals(paramWidget, getWidget(b)))
        return this.widgetsWidthList_DEC.get(b); 
      b++;
    } 
    return 80;
  }
  
  public void setWidthAtWidget(Widget paramWidget, int paramInt) {
    byte b = 0;
    int i = getWidgetCount();
    while (b < i) {
      if (ComparatorUtils.equals(paramWidget, getWidget(b))) {
        this.widgetsWidthList_DEC.set(b, paramInt);
        break;
      } 
      b++;
    } 
  }
  
  protected void setWidthAtIndex(int paramInt1, int paramInt2) {
    this.widgetsWidthList_DEC.set(paramInt1, paramInt2);
  }
  
  private String asAlignString(int paramInt) {
    switch (paramInt) {
      case 0:
        return "left";
      case 2:
        return "right";
    } 
    return "center";
  }
  
  public String getLayoutToolTip() {
    return Inter.getLocText("FR-Designer_WLayout-Horizontail-ToolTips");
  }
  
  public Dimension getMinDesignSize() {
    int i = 0;
    int j = 0;
    byte b = 0;
    int k = getWidgetCount();
    while (b < k) {
      Widget widget = getWidget(b);
      if (widget.isVisible()) {
        i += this.widgetsWidthList_DEC.get(b);
        if (widget instanceof WLayout) {
          int m = (((WLayout)widget).getMinDesignSize()).height;
          if (m > j)
            j = m; 
        } 
      } 
      b++;
    } 
    return new Dimension(i, j);
  }
  
  public JSONObject createJSONConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    JSONObject jSONObject = super.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
    jSONObject.put("alignment", asAlignString(this.alignment));
    return jSONObject;
  }
  
  protected JSONArray createJSONItems(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    JSONArray jSONArray = new JSONArray();
    byte b = 0;
    int i = this.widgetList.size();
    while (b < i) {
      Widget widget = this.widgetList.get(b);
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("width", getWidthAtWidget(widget));
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
      if (str.equals("FLAttr")) {
        this.alignment = paramXMLableReader.getAttrAsInt("alignment", 0);
      } else if (ComparatorUtils.equals(str, "ColumnWidth")) {
        this.widgetsWidthList_DEC = BaseXMLUtils.readDynamicNumberList(paramXMLableReader);
      } 
    } 
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    super.writeXML(paramXMLPrintWriter);
    paramXMLPrintWriter.startTAG("FLAttr").attr("alignment", this.alignment).end();
    paramXMLPrintWriter.startTAG("ColumnWidth");
    BaseXMLUtils.writeDynamicNumberList(paramXMLPrintWriter, this.widgetsWidthList_DEC);
    paramXMLPrintWriter.end();
  }
  
  public Object clone() throws CloneNotSupportedException {
    WHorizontalBoxLayout wHorizontalBoxLayout = (WHorizontalBoxLayout)super.clone();
    wHorizontalBoxLayout.widgetsWidthList_DEC = (DynamicNumberList)this.widgetsWidthList_DEC.clone();
    return wHorizontalBoxLayout;
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\WHorizontalBoxLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */