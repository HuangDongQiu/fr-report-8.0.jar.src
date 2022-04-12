package com.fr.form.ui.container;

import com.fr.form.ui.Widget;
import com.fr.form.ui.WidgetXmlUtils;
import com.fr.general.ComparatorUtils;
import com.fr.general.Inter;
import com.fr.json.JSONArray;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.web.Repository;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLReadable;
import com.fr.stable.xml.XMLableReader;
import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class WGridLayout extends WLayout {
  private int cols;
  
  private int rows;
  
  private Map map;
  
  public WGridLayout() {
    this(1, 1, 0, 0);
  }
  
  public WGridLayout(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    setRows(paramInt1);
    setColumns(paramInt2);
    setHgap(paramInt3);
    setVgap(paramInt4);
    this.map = new HashMap<Object, Object>();
  }
  
  public void addWidget(Widget paramWidget) {
    for (byte b = 0; b < this.rows; b++) {
      for (byte b1 = 0; b1 < this.cols; b1++) {
        Point point = new Point(b1, b);
        if (this.map.get(point) == null) {
          addWidget(paramWidget, point);
          return;
        } 
      } 
    } 
  }
  
  public void addWidget(Widget paramWidget, int paramInt1, int paramInt2) {
    addWidget(paramWidget, new Point(paramInt1, paramInt2));
  }
  
  public void addWidget(Widget paramWidget, Point paramPoint) {
    super.addWidget(paramWidget);
    this.map.put(paramPoint, paramWidget);
  }
  
  public void removeWidget(Widget paramWidget) {
    super.removeWidget(paramWidget);
    for (Point point : this.map.keySet()) {
      if (ComparatorUtils.equals(this.map.get(point), paramWidget)) {
        this.map.remove(point);
        return;
      } 
    } 
  }
  
  public String getLayoutToolTip() {
    return Inter.getLocText("WLayout-Grid-ToolTips");
  }
  
  public void removeAll() {
    super.removeAll();
    this.map.clear();
  }
  
  public Dimension getMinDesignSize() {
    return new Dimension();
  }
  
  public Iterator iterator() {
    return this.map.keySet().iterator();
  }
  
  public Widget getWidget(Point paramPoint) {
    Object object = this.map.get(paramPoint);
    return (object == null) ? null : (Widget)object;
  }
  
  public int getRows() {
    return this.rows;
  }
  
  public void setRows(int paramInt) {
    this.rows = (paramInt == 0) ? 1 : paramInt;
  }
  
  public int getColumns() {
    return this.cols;
  }
  
  public void setColumns(int paramInt) {
    this.cols = (paramInt == 0) ? 1 : paramInt;
  }
  
  public String getXType() {
    return "grid";
  }
  
  public JSONObject createJSONConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    JSONObject jSONObject = super.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
    jSONObject.put("columns", this.cols);
    jSONObject.put("rows", this.rows);
    return jSONObject;
  }
  
  protected JSONArray createJSONItems(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    JSONArray jSONArray = new JSONArray();
    for (Point point : this.map.keySet()) {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("column", point.x);
      jSONObject.put("row", point.y);
      jSONObject.put("el", ((Widget)this.map.get(point)).createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor));
      jSONArray.put(jSONObject);
    } 
    return jSONArray;
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    super.readXML(paramXMLableReader);
    if (paramXMLableReader.isAttr())
      this.map.clear(); 
    if (paramXMLableReader.isChildNode()) {
      String str = paramXMLableReader.getTagName();
      if (str.equals("GLAttr")) {
        this.rows = paramXMLableReader.getAttrAsInt("rows", 1);
        this.cols = paramXMLableReader.getAttrAsInt("cols", 1);
      } else if (str.equals("WMap")) {
        paramXMLableReader.readXMLObject(new XMLReadable() {
              public void readXML(XMLableReader param1XMLableReader) {
                if (param1XMLableReader.isChildNode() && param1XMLableReader.getTagName().equals("Widget")) {
                  Point point = new Point(param1XMLableReader.getAttrAsInt("col", 0), param1XMLableReader.getAttrAsInt("row", 0));
                  Widget widget = WidgetXmlUtils.readWidget(param1XMLableReader);
                  WGridLayout.this.map.put(point, widget);
                } 
              }
            });
      } 
    } 
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    super.writeXML(paramXMLPrintWriter);
    paramXMLPrintWriter.startTAG("GLAttr").attr("rows", this.rows).attr("cols", this.cols).end();
    paramXMLPrintWriter.startTAG("WMap");
    for (Point point : this.map.keySet())
      WidgetXmlUtils.writeXMLWidget(paramXMLPrintWriter, (Widget)this.map.get(point), point); 
    paramXMLPrintWriter.end();
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\WGridLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */