package com.fr.form.ui.container;

import com.fr.form.ui.Connector;
import com.fr.form.ui.PaddingMargin;
import com.fr.form.ui.Widget;
import com.fr.form.ui.WidgetXmlUtils;
import com.fr.general.ComparatorUtils;
import com.fr.general.FRScreen;
import com.fr.general.Inter;
import com.fr.general.xml.GeneralXMLTools;
import com.fr.json.JSONArray;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.StringUtils;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.web.Repository;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLReadable;
import com.fr.stable.xml.XMLable;
import com.fr.stable.xml.XMLableReader;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;

public class WAbsoluteLayout extends WLayout {
  public static final int STATE_FIT = 0;
  
  public static final int STATE_FIXED = 1;
  
  private static final double DEFAULT_SLIDER = 100.0D;
  
  private static final Dimension DEFAULT_RESOLUTION = new Dimension(1440, 900);
  
  private static final Dimension DEFAULT_ABSOLUTE_LAYOUT_SIZE = new Dimension(80, 21);
  
  private int compState = 0;
  
  private Dimension designingResolution = DEFAULT_RESOLUTION;
  
  private ArrayList connectorList = new ArrayList();
  
  public WAbsoluteLayout() {
    this.margin = new PaddingMargin(0, 0, 0, 0);
  }
  
  public WAbsoluteLayout(String paramString) {
    if (StringUtils.isNotEmpty(paramString))
      setWidgetName(paramString); 
  }
  
  public void setDesigningResolution(Dimension paramDimension) {
    this.designingResolution = paramDimension;
  }
  
  public Dimension getDesigningResolution() {
    return this.designingResolution;
  }
  
  public void setBounds(Widget paramWidget, Rectangle paramRectangle) {
    BoundsWidget boundsWidget = getBoundsWidget(paramWidget);
    if (boundsWidget != null)
      boundsWidget.setBounds(paramRectangle); 
  }
  
  public String getXType() {
    return "absolute";
  }
  
  public void addConnector(Connector paramConnector) {
    this.connectorList.add(paramConnector);
  }
  
  public int connectorCount() {
    return this.connectorList.size();
  }
  
  public void removeConnector(Connector paramConnector) {
    this.connectorList.remove(paramConnector);
  }
  
  public void clearLinkLine() {
    this.connectorList.clear();
  }
  
  public Connector getConnectorIndex(int paramInt) {
    return this.connectorList.get(paramInt);
  }
  
  public Connector[] getConnector() {
    return (Connector[])this.connectorList.toArray((Object[])new Connector[0]);
  }
  
  public Connector[] getCoveredConnectors(Rectangle paramRectangle) {
    ArrayList<Connector> arrayList = new ArrayList();
    for (byte b = 0; b < this.connectorList.size(); b++) {
      Connector connector = this.connectorList.get(b);
      if (connector.coveredByRectangle(paramRectangle))
        arrayList.add(connector); 
    } 
    return arrayList.<Connector>toArray(new Connector[arrayList.size()]);
  }
  
  public String getLayoutToolTip() {
    return Inter.getLocText("FR-Designer_WLayout-Absolute-ToolTips");
  }
  
  public BoundsWidget getBoundsWidget(Widget paramWidget) {
    byte b = 0;
    int i = getWidgetCount();
    while (b < i) {
      BoundsWidget boundsWidget = (BoundsWidget)getWidget(b);
      if (ComparatorUtils.equals(boundsWidget.getWidget(), paramWidget))
        return boundsWidget; 
      b++;
    } 
    return null;
  }
  
  public JSONObject createJSONConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    double d1;
    double d2;
    this.margin = new PaddingMargin(0, 0, 0, 0);
    JSONObject jSONObject = super.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
    jSONObject.put("absoluteCompState", this.compState);
    if (paramRepository.getDevice().isMobile())
      return createMobileJSONConfig(jSONObject); 
    if (FRScreen.findByDimension(getDesigningResolution())) {
      d1 = FRScreen.getByDimension(getDesigningResolution()).getValue().doubleValue() / FRScreen.p1440.getValue().doubleValue();
      d2 = d1;
    } else {
      d1 = getDesigningResolution().getWidth() / DEFAULT_RESOLUTION.getWidth();
      d2 = getDesigningResolution().getHeight() / DEFAULT_RESOLUTION.getHeight();
    } 
    jSONObject.put("absoluteResolutionScaleW", d1);
    jSONObject.put("absoluteResolutionScaleH", d2);
    return jSONObject;
  }
  
  private JSONObject createMobileJSONConfig(JSONObject paramJSONObject) throws JSONException {
    paramJSONObject.put("designingResolutionW", getDesigningResolution().getWidth());
    paramJSONObject.put("designingResolutionH", getDesigningResolution().getHeight());
    return paramJSONObject;
  }
  
  protected JSONArray createJSONItems(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    Collections.sort(this.widgetList);
    return super.createJSONItems(paramRepository, paramCalculator, paramNodeVisitor);
  }
  
  public Dimension getMinDesignSize() {
    return DEFAULT_ABSOLUTE_LAYOUT_SIZE;
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    super.readXML(paramXMLableReader);
    if (paramXMLableReader.isChildNode()) {
      String str = paramXMLableReader.getTagName();
      if ("connector".equals(str)) {
        Connector connector = new Connector();
        paramXMLableReader.readXMLObject((XMLReadable)connector);
        addConnector(connector);
      } else if ("WidgetScalingAttr".equals(str)) {
        setCompState(paramXMLableReader.getAttrAsInt("compState", 0));
      } else if ("DesignResolution".equals(str)) {
        setDesigningResolution(new Dimension(paramXMLableReader.getAttrAsInt("absoluteResolutionScaleW", DEFAULT_RESOLUTION.width), paramXMLableReader.getAttrAsInt("absoluteResolutionScaleH", DEFAULT_RESOLUTION.height)));
      } 
    } 
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    super.writeXML(paramXMLPrintWriter);
    writeScalingAttrXML(paramXMLPrintWriter);
    Connector[] arrayOfConnector = getConnector();
    byte b = 0;
    int i = arrayOfConnector.length;
    while (b < i) {
      paramXMLPrintWriter.startTAG("connector");
      arrayOfConnector[b].writeXML(paramXMLPrintWriter);
      paramXMLPrintWriter.end();
      b++;
    } 
  }
  
  protected void writeScalingAttrXML(XMLPrintWriter paramXMLPrintWriter) {
    paramXMLPrintWriter.startTAG("WidgetScalingAttr").attr("compState", this.compState).end();
    paramXMLPrintWriter.startTAG("DesignResolution").attr("absoluteResolutionScaleW", this.designingResolution.width).attr("absoluteResolutionScaleH", this.designingResolution.height).end();
  }
  
  public int getCompState() {
    return this.compState;
  }
  
  public void setCompState(int paramInt) {
    this.compState = paramInt;
  }
  
  public static class BoundsWidget extends Widget implements Comparable {
    private Widget widget;
    
    private Rectangle bounds;
    
    private Rectangle backupBounds;
    
    public BoundsWidget() {}
    
    public BoundsWidget(Widget param1Widget, Rectangle param1Rectangle) {
      this.widget = param1Widget;
      this.bounds = param1Rectangle;
    }
    
    public Rectangle getBounds() {
      return this.bounds;
    }
    
    public void setBounds(Rectangle param1Rectangle) {
      this.bounds = param1Rectangle;
    }
    
    public Rectangle getBackupBounds() {
      return this.backupBounds;
    }
    
    public void setBackupBounds(Rectangle param1Rectangle) {
      this.backupBounds = param1Rectangle;
    }
    
    public Widget getWidget() {
      return this.widget;
    }
    
    public String getWidgetName() {
      return this.widget.getWidgetName();
    }
    
    public String getXType() {
      return (this.widget == null) ? null : this.widget.getXType();
    }
    
    public boolean acceptType(Class<?>... param1VarArgs) {
      return this.widget.acceptType(param1VarArgs);
    }
    
    public boolean isEditor() {
      return this.widget.isEditor();
    }
    
    public String[] supportedEvents() {
      return this.widget.supportedEvents();
    }
    
    public JSONObject createJSONConfig(Repository param1Repository, Calculator param1Calculator, NodeVisitor param1NodeVisitor) throws JSONException {
      return this.widget.acceptType(new Class[] { WParameterLayout.class }) ? JSONObject.EMPTY : createJSON(param1Repository, param1Calculator, param1NodeVisitor);
    }
    
    private JSONObject createJSON(Repository param1Repository, Calculator param1Calculator, NodeVisitor param1NodeVisitor) throws JSONException {
      JSONObject jSONObject = this.widget.createJSONConfig(param1Repository, param1Calculator, param1NodeVisitor);
      jSONObject.put("x", this.bounds.x);
      jSONObject.put("y", this.bounds.y);
      jSONObject.put("width", this.bounds.width);
      jSONObject.put("height", this.bounds.height);
      return jSONObject;
    }
    
    public void toImage(Calculator param1Calculator, Rectangle param1Rectangle, Graphics param1Graphics) {
      this.widget.toImage(param1Calculator, this.bounds, param1Graphics);
    }
    
    public void resize(double param1Double1, double param1Double2, double param1Double3) {
      int i = (int)(this.bounds.width * param1Double1);
      int j = (int)(this.bounds.height * param1Double2);
      int k = (int)(this.bounds.x * param1Double1);
      int m = (int)(this.bounds.y * param1Double2);
      this.bounds.setBounds(k, m, i, j);
      getWidget().resize(i, j, param1Double3);
    }
    
    public int compareTo(Object param1Object) {
      BoundsWidget boundsWidget = (BoundsWidget)param1Object;
      return (boundsWidget.bounds.y < this.bounds.y) ? 1 : ((boundsWidget.bounds.y == this.bounds.y && boundsWidget.bounds.x < this.bounds.x) ? 1 : ((boundsWidget.bounds.y == this.bounds.y && boundsWidget.bounds.x == this.bounds.x) ? 0 : -1));
    }
    
    public void readXML(XMLableReader param1XMLableReader) {
      if (param1XMLableReader.isChildNode()) {
        String str = param1XMLableReader.getTagName();
        if ("InnerWidget".equals(str)) {
          this.widget = WidgetXmlUtils.readWidget(param1XMLableReader);
        } else if ("BoundsAttr".equals(str)) {
          this.bounds = new Rectangle(param1XMLableReader.getAttrAsInt("x", 0), param1XMLableReader.getAttrAsInt("y", 0), param1XMLableReader.getAttrAsInt("width", 0), param1XMLableReader.getAttrAsInt("height", 0));
        } 
      } 
    }
    
    public void writeXML(XMLPrintWriter param1XMLPrintWriter) {
      if (this.widget != null)
        GeneralXMLTools.writeXMLable(param1XMLPrintWriter, (XMLable)this.widget, "InnerWidget"); 
      if (this.bounds != null)
        param1XMLPrintWriter.startTAG("BoundsAttr").attr("x", this.bounds.x).attr("y", this.bounds.y).attr("width", this.bounds.width).attr("height", this.bounds.height).end(); 
    }
    
    public boolean equals(Object param1Object) {
      return (param1Object instanceof BoundsWidget && ComparatorUtils.equals(((BoundsWidget)param1Object).widget, this.widget));
    }
    
    public Object clone() throws CloneNotSupportedException {
      BoundsWidget boundsWidget = (BoundsWidget)super.clone();
      if (this.widget != null)
        boundsWidget.widget = (Widget)this.widget.clone(); 
      if (this.bounds != null)
        boundsWidget.bounds = (Rectangle)this.bounds.clone(); 
      return boundsWidget;
    }
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\WAbsoluteLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */