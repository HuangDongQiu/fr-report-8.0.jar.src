package com.fr.form.ui.container;

import com.fr.form.ui.Widget;
import com.fr.general.ComparatorUtils;
import com.fr.general.Inter;
import com.fr.general.xml.GeneralXMLTools;
import com.fr.json.JSONArray;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.web.Repository;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLReadable;
import com.fr.stable.xml.XMLable;
import com.fr.stable.xml.XMLableReader;
import java.awt.Dimension;

public abstract class WSplitLayout extends WLayout {
  public static final String CENTER = "center";
  
  public static final String ASIDE = "aside";
  
  private double ratio = 0.5D;
  
  private Widget center;
  
  private Widget aside;
  
  public double getRatio() {
    return this.ratio;
  }
  
  public void setRatio(double paramDouble) {
    this.ratio = paramDouble;
  }
  
  public void addWidget(Widget paramWidget, Object paramObject) {
    if (ComparatorUtils.equals("aside", paramObject)) {
      addAside(paramWidget);
    } else {
      addCenter(paramWidget);
    } 
  }
  
  public void addCenter(Widget paramWidget) {
    addWidget(paramWidget);
    this.center = paramWidget;
  }
  
  public void addAside(Widget paramWidget) {
    addWidget(paramWidget);
    this.aside = paramWidget;
  }
  
  public Widget getLayoutWidget(Object paramObject) {
    return ComparatorUtils.equals("aside", paramObject) ? this.aside : this.center;
  }
  
  public void removeWidget(Widget paramWidget) {
    super.removeWidget(paramWidget);
    if (ComparatorUtils.equals(paramWidget, this.center)) {
      this.center = null;
    } else if (ComparatorUtils.equals(paramWidget, this.aside)) {
      this.aside = null;
    } 
  }
  
  public String getLayoutToolTip() {
    return Inter.getLocText("WLayout-Split-ToolTips");
  }
  
  public Dimension getMinDesignSize() {
    return new Dimension();
  }
  
  public void removeAll() {
    super.removeAll();
    this.center = null;
    this.aside = null;
  }
  
  public JSONObject createJSONConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    JSONObject jSONObject = super.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
    jSONObject.put("ratio", this.ratio);
    return jSONObject;
  }
  
  protected JSONArray createJSONItems(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    JSONArray jSONArray = new JSONArray();
    if (this.aside != null) {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("region", "aside");
      jSONObject.put("el", this.aside.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor));
      jSONArray.put(jSONObject);
    } 
    if (this.center != null) {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("region", "center");
      jSONObject.put("el", this.center.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor));
      jSONArray.put(jSONObject);
    } 
    return jSONArray;
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    super.readXML(paramXMLableReader);
    if (paramXMLableReader.isChildNode()) {
      String str = paramXMLableReader.getTagName();
      if (str.equals("Center")) {
        paramXMLableReader.readXMLObject(new XMLReadable() {
              public void readXML(XMLableReader param1XMLableReader) {
                WSplitLayout.this.addCenter((Widget)GeneralXMLTools.readXMLable(param1XMLableReader));
              }
            });
      } else if (str.equals("Aside")) {
        paramXMLableReader.readXMLObject(new XMLReadable() {
              public void readXML(XMLableReader param1XMLableReader) {
                WSplitLayout.this.addAside((Widget)GeneralXMLTools.readXMLable(param1XMLableReader));
              }
            });
      } else if (str.equals("SplitAttr")) {
        setRatio(paramXMLableReader.getAttrAsDouble("ratio", 0.5D));
      } 
    } 
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    this.widgetList.clear();
    super.writeXML(paramXMLPrintWriter);
    paramXMLPrintWriter.startTAG("SplitAttr").attr("ratio", this.ratio).end();
    if (this.center != null)
      GeneralXMLTools.writeXMLable(paramXMLPrintWriter, (XMLable)this.center, "Center"); 
    if (this.aside != null)
      GeneralXMLTools.writeXMLable(paramXMLPrintWriter, (XMLable)this.aside, "Aside"); 
  }
  
  public Object clone() throws CloneNotSupportedException {
    WSplitLayout wSplitLayout = (WSplitLayout)super.clone();
    if (this.center != null)
      wSplitLayout.center = this.center; 
    if (this.aside != null)
      wSplitLayout.aside = this.aside; 
    return this.aside;
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\WSplitLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */