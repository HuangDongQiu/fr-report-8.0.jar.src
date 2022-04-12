package com.fr.form.ui.container;

import com.fr.form.main.Form;
import com.fr.form.main.WidgetGather;
import com.fr.form.main.WidgetGatherAdapter;
import com.fr.form.ui.Label;
import com.fr.form.ui.PaddingMargin;
import com.fr.form.ui.Widget;
import com.fr.general.ComparatorUtils;
import com.fr.general.Inter;
import com.fr.json.JSONArray;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.plugin.ExtraClassManager;
import com.fr.report.fun.ReportFitProcessor;
import com.fr.script.Calculator;
import com.fr.stable.ArrayUtils;
import com.fr.stable.StringUtils;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.web.Repository;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLReadable;
import com.fr.stable.xml.XMLableReader;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class WFitLayout extends WLayout {
  public static final int STATE_FULL = 0;
  
  public static final int STATE_ORIGIN = 1;
  
  private static final long serialVersionUID = -2411890197815749646L;
  
  private static final double DEFAULT_PERCENT = 1.0D;
  
  private int compState = 0;
  
  private WBodyLayoutType layoutType = WBodyLayoutType.FIT;
  
  private double resolutionScaling = 1.0D;
  
  private int containerWidth = 960;
  
  private int containerHeight = 540;
  
  private ArrayList<String> mobileWidgetList = new ArrayList<String>();
  
  private boolean isSorted = false;
  
  private boolean hasResized = false;
  
  private boolean appRelayout = true;
  
  public WFitLayout() {}
  
  public WFitLayout(String paramString) {
    if (StringUtils.isNotEmpty(paramString))
      setWidgetName(paramString); 
  }
  
  public String getLayoutToolTip() {
    return Inter.getLocText("FR-Designer-Layout_Adaptive_Layout");
  }
  
  private ArrayList<String> traverseWidgetName() {
    final ArrayList<String> WidgetLists = new ArrayList();
    Form.traversalWidget((Widget)this, (WidgetGather)new WidgetGatherAdapter() {
          public void dealWith(Widget param1Widget) {
            if (param1Widget.acceptType(new Class[] { Label.class }, ) && StringUtils.isNotEmpty(((Label)param1Widget).getWidgetValue().toString())) {
              WidgetLists.add(param1Widget.getWidgetName());
            } else if (!param1Widget.acceptType(new Class[] { Label.class }, )) {
              WidgetLists.add(param1Widget.getWidgetName());
            } 
          }
        },  Widget.class);
    return arrayList;
  }
  
  public ArrayList<String> getMobileWidgetList() {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    byte b = 0;
    int i = getWidgetCount();
    while (b < i) {
      WAbsoluteLayout.BoundsWidget boundsWidget = (WAbsoluteLayout.BoundsWidget)getWidget(b);
      if (boundsWidget != null)
        if (boundsWidget.getWidget().acceptType(new Class[] { WScaleLayout.class })) {
          Rectangle rectangle = boundsWidget.getBounds();
          boundsWidget = (WAbsoluteLayout.BoundsWidget)((WScaleLayout)boundsWidget.getWidget()).getBoundsWidget();
          initXYMap(boundsWidget, rectangle, (HashMap)hashMap);
        } else {
          initXYMap(boundsWidget, boundsWidget.getBounds(), (HashMap)hashMap);
        }  
      b++;
    } 
    Object[] arrayOfObject = hashMap.keySet().toArray();
    Arrays.sort(arrayOfObject);
    if (!this.isSorted)
      this.mobileWidgetList.clear(); 
    i = 0;
    int j = arrayOfObject.length;
    while (i < j) {
      HashMap hashMap1 = (HashMap)hashMap.get(arrayOfObject[i]);
      Object[] arrayOfObject1 = hashMap1.keySet().toArray();
      Arrays.sort(arrayOfObject1);
      byte b1 = 0;
      int k = arrayOfObject1.length;
      while (b1 < k) {
        String str = (String)hashMap1.get(arrayOfObject1[b1]);
        addToMoblieList(str);
        b1++;
      } 
      i++;
    } 
    for (i = 0; i < this.mobileWidgetList.size(); i++) {
      if (!traverseWidgetName().contains(this.mobileWidgetList.get(i)))
        this.mobileWidgetList.remove(i); 
    } 
    return this.mobileWidgetList;
  }
  
  private void addToMoblieList(String paramString) {
    addToMoblieList(-1, paramString);
  }
  
  private void addToMoblieList(int paramInt, String paramString) {
    if (!traverseWidgetName().contains(paramString))
      return; 
    if (this.mobileWidgetList.contains(paramString))
      return; 
    if (paramInt == -1) {
      this.mobileWidgetList.add(paramString);
    } else if (paramInt > this.mobileWidgetList.size()) {
      this.mobileWidgetList.add(this.mobileWidgetList.size(), paramString);
    } else {
      this.mobileWidgetList.add(paramInt, paramString);
    } 
  }
  
  public void adjustOrder(int paramInt1, int paramInt2) {
    if (paramInt1 == paramInt2)
      return; 
    int i = this.mobileWidgetList.size();
    if (paramInt1 > i)
      return; 
    if (paramInt2 > i)
      paramInt2 = i; 
    String str = this.mobileWidgetList.get(paramInt1);
    this.mobileWidgetList.remove(paramInt1);
    if (paramInt1 < paramInt2)
      paramInt2--; 
    addToMoblieList(paramInt2, str);
    this.isSorted = true;
  }
  
  private void initXYMap(WAbsoluteLayout.BoundsWidget paramBoundsWidget, Rectangle paramRectangle, HashMap<Double, HashMap<Double, String>> paramHashMap) {
    HashMap<Double, String> hashMap = paramHashMap.get(Double.valueOf(paramRectangle.getY()));
    if (hashMap != null) {
      hashMap.put(Double.valueOf(paramRectangle.getX()), paramBoundsWidget.getWidget().getWidgetName());
      return;
    } 
    hashMap = new HashMap<Double, String>();
    hashMap.put(Double.valueOf(paramRectangle.getX()), paramBoundsWidget.getWidget().getWidgetName());
    paramHashMap.put(Double.valueOf(paramRectangle.getY()), hashMap);
  }
  
  public boolean canCurrentMarginAvailable(PaddingMargin paramPaddingMargin) {
    byte b = 0;
    int i = getWidgetCount();
    while (b < i) {
      WAbsoluteLayout.BoundsWidget boundsWidget = (WAbsoluteLayout.BoundsWidget)getWidget(b);
      Rectangle rectangle1 = boundsWidget.getBounds();
      Rectangle rectangle2 = new Rectangle(rectangle1);
      if (rectangle1.x == 0)
        rectangle2.width -= paramPaddingMargin.getLeft(); 
      if (rectangle1.y == 0)
        rectangle2.height -= paramPaddingMargin.getTop(); 
      if (rectangle1.x + rectangle1.width == this.containerWidth)
        rectangle2.width -= paramPaddingMargin.getRight(); 
      if (rectangle1.y + rectangle1.height == this.containerHeight)
        rectangle2.height -= paramPaddingMargin.getBottom(); 
      if (rectangle2.width < MIN_WIDTH || rectangle2.height < MIN_HEIGHT)
        return false; 
      b++;
    } 
    return true;
  }
  
  public Dimension getMinDesignSize() {
    return new Dimension(MIN_WIDTH * (getHorComps()).length, MIN_HEIGHT * (getVertiComps()).length);
  }
  
  public int[] getVertiComps() {
    ArrayList<Integer> arrayList = new ArrayList();
    arrayList.add(Integer.valueOf(0));
    arrayList.add(Integer.valueOf(this.containerHeight));
    byte b = 0;
    int i = getWidgetCount();
    while (b < i) {
      WAbsoluteLayout.BoundsWidget boundsWidget = (WAbsoluteLayout.BoundsWidget)getWidget(b);
      Rectangle rectangle = boundsWidget.getBounds();
      if (!arrayList.contains(Integer.valueOf(rectangle.y)))
        arrayList.add(Integer.valueOf(rectangle.y)); 
      b++;
    } 
    Collections.sort(arrayList);
    return ArrayUtils.toPrimitive(arrayList.<Integer>toArray(new Integer[] { Integer.valueOf(arrayList.size()) }));
  }
  
  public int[] getHorComps() {
    ArrayList<Integer> arrayList = new ArrayList();
    arrayList.add(Integer.valueOf(0));
    arrayList.add(Integer.valueOf(this.containerWidth));
    byte b = 0;
    int i = getWidgetCount();
    while (b < i) {
      WAbsoluteLayout.BoundsWidget boundsWidget = (WAbsoluteLayout.BoundsWidget)getWidget(b);
      Rectangle rectangle = boundsWidget.getBounds();
      if (!arrayList.contains(Integer.valueOf(rectangle.x)))
        arrayList.add(Integer.valueOf(rectangle.x)); 
      b++;
    } 
    Collections.sort(arrayList);
    return ArrayUtils.toPrimitive(arrayList.<Integer>toArray(new Integer[] { Integer.valueOf(arrayList.size()) }));
  }
  
  public String getXType() {
    return "fit";
  }
  
  public WAbsoluteLayout.BoundsWidget getBoundsWidget(Widget paramWidget) {
    byte b = 0;
    int i = getWidgetCount();
    while (b < i) {
      WAbsoluteLayout.BoundsWidget boundsWidget = (WAbsoluteLayout.BoundsWidget)getWidget(b);
      if (ComparatorUtils.equals(boundsWidget.getWidget(), paramWidget))
        return boundsWidget; 
      b++;
    } 
    return null;
  }
  
  public JSONObject createJSONConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    if (paramRepository.getDevice().isMobile()) {
      JSONObject jSONObject1 = getBodyLayoutType().createMobileJSONConfig(this, paramRepository, paramCalculator, paramNodeVisitor);
      if (jSONObject1 != null)
        return jSONObject1; 
    } 
    JSONObject jSONObject = super.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
    jSONObject.put("compState", this.compState);
    jSONObject.put("appRelayout", this.appRelayout);
    jSONObject.put("fitLayoutWidth", this.containerWidth);
    jSONObject.put("fitLayoutHeight", this.containerHeight);
    jSONObject.put("itemsIndex", createItemIndexList());
    jSONObject.put("hasResized", this.hasResized);
    return jSONObject;
  }
  
  public JSONArray createItemIndexList() {
    JSONArray jSONArray = new JSONArray();
    this.mobileWidgetList = getMobileWidgetList();
    byte b = 0;
    int i = this.mobileWidgetList.size();
    while (b < i) {
      jSONArray.put(((String)this.mobileWidgetList.get(b)).toUpperCase());
      b++;
    } 
    return jSONArray;
  }
  
  public void processFitLayoutMargin() {
    for (byte b = 0; b < this.widgetList.size(); b++) {
      WAbsoluteLayout.BoundsWidget boundsWidget = this.widgetList.get(b);
      Rectangle rectangle1 = boundsWidget.getBounds();
      Rectangle rectangle2 = new Rectangle(rectangle1);
      if (rectangle1.x == 0) {
        rectangle2.x = (int)(rectangle2.x + this.margin.getLeft() / this.resolutionScaling);
        rectangle2.width = (int)(rectangle2.width - this.margin.getLeft() / this.resolutionScaling);
      } 
      if (rectangle1.x + rectangle1.width == this.containerWidth)
        rectangle2.width = (int)(rectangle2.width - this.margin.getRight() / this.resolutionScaling); 
      if (rectangle1.y == 0) {
        rectangle2.y = (int)(rectangle2.y + this.margin.getTop() / this.resolutionScaling);
        rectangle2.height = (int)(rectangle2.height - this.margin.getTop() / this.resolutionScaling);
      } 
      if (rectangle1.y + rectangle1.height == this.containerHeight)
        rectangle2.height = (int)(rectangle2.height - this.margin.getBottom() / this.resolutionScaling); 
      rectangle2.y = (int)(rectangle2.y - this.margin.getTop() / this.resolutionScaling);
      rectangle2.x = (int)(rectangle2.x - this.margin.getLeft() / this.resolutionScaling);
      rectangle1.setBounds(rectangle2);
      boundsWidget.getWidget().updateChildBounds(rectangle2);
    } 
  }
  
  public int getActualWidth() {
    int i = 0;
    for (byte b = 0; b < this.widgetList.size(); b++) {
      WAbsoluteLayout.BoundsWidget boundsWidget = this.widgetList.get(b);
      Rectangle rectangle = boundsWidget.getBounds();
      if (rectangle.x + rectangle.width > i)
        i = rectangle.x + rectangle.width; 
    } 
    return i;
  }
  
  public int getActualHeight() {
    int i = 0;
    for (byte b = 0; b < this.widgetList.size(); b++) {
      WAbsoluteLayout.BoundsWidget boundsWidget = this.widgetList.get(b);
      Rectangle rectangle = boundsWidget.getBounds();
      if (rectangle.y + rectangle.height > i)
        i = rectangle.y + rectangle.height; 
    } 
    return i;
  }
  
  public void processFitLayoutInterval(int paramInt1, int paramInt2) {
    if (this.compInterval == 0)
      return; 
    int i = this.compInterval / 2;
    for (byte b = 0; b < this.widgetList.size(); b++) {
      WAbsoluteLayout.BoundsWidget boundsWidget = this.widgetList.get(b);
      Rectangle rectangle1 = boundsWidget.getBounds();
      Rectangle rectangle2 = new Rectangle(rectangle1);
      if (rectangle1.x > 0) {
        rectangle2.x += i;
        rectangle2.width -= i;
      } 
      if (rectangle1.width + rectangle1.x < paramInt1)
        rectangle2.width -= i; 
      if (rectangle1.y > 0) {
        rectangle2.y += i;
        rectangle2.height -= i;
      } 
      if (rectangle1.height + rectangle1.y < paramInt2)
        rectangle2.height -= i; 
      rectangle1.setBounds(rectangle2);
    } 
  }
  
  protected JSONArray createJSONItems(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    Collections.sort(this.widgetList);
    ReportFitProcessor reportFitProcessor = (ReportFitProcessor)ExtraClassManager.getInstance().getSingle("ReportFitProcessor");
    if (reportFitProcessor == null)
      processFitLayoutMargin(); 
    return super.createJSONItems(paramRepository, paramCalculator, paramNodeVisitor);
  }
  
  public void setBounds(Widget paramWidget, Rectangle paramRectangle) {
    WAbsoluteLayout.BoundsWidget boundsWidget = getBoundsWidget(paramWidget);
    if (boundsWidget != null)
      boundsWidget.setBounds(paramRectangle); 
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    super.readXML(paramXMLableReader);
    if (paramXMLableReader.isChildNode()) {
      String str = paramXMLableReader.getTagName();
      if ("Sorted".equals(str)) {
        this.isSorted = paramXMLableReader.getAttrAsBoolean("sorted", false);
      } else if ("WidgetZoomAttr".equals(str)) {
        setCompState(paramXMLableReader.getAttrAsInt("compState", 0));
      } else if ("AppRelayout".equals(str)) {
        setAppRelayout(paramXMLableReader.getAttrAsBoolean("appRelayout", true));
      } else if ("Size".equals(str)) {
        setContainerWidth(paramXMLableReader.getAttrAsInt("width", 960));
        setContainerHeight(paramXMLableReader.getAttrAsInt("height", 540));
      } else if (str.equals("ResolutionScalingAttr")) {
        setResolutionScaling(paramXMLableReader.getAttrAsDouble("percent", 1.0D));
      } else if ("BodyLayoutType".equals(str)) {
        setLayoutType(WBodyLayoutType.parse(paramXMLableReader.getAttrAsInt("type", WBodyLayoutType.FIT.getTypeValue())));
      } else if (str.equals("MobileWidgetList")) {
        paramXMLableReader.readXMLObject(new XMLReadable() {
              public void readXML(XMLableReader param1XMLableReader) {
                if ("Widget".equals(param1XMLableReader.getTagName()))
                  WFitLayout.this.addToMoblieList(param1XMLableReader.getAttrAsString("widgetName", "")); 
              }
            });
      } 
    } 
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    super.writeXML(paramXMLPrintWriter);
    paramXMLPrintWriter.startTAG("Sorted").attr("sorted", this.isSorted).end();
    paramXMLPrintWriter.startTAG("WidgetZoomAttr").attr("compState", this.compState).end();
    paramXMLPrintWriter.startTAG("AppRelayout").attr("appRelayout", this.appRelayout).end();
    paramXMLPrintWriter.startTAG("Size").attr("width", this.containerWidth).attr("height", this.containerHeight).end();
    paramXMLPrintWriter.startTAG("ResolutionScalingAttr").attr("percent", this.resolutionScaling).end();
    writeLayoutTypeAttr(paramXMLPrintWriter);
    paramXMLPrintWriter.startTAG("MobileWidgetList");
    byte b = 0;
    int i = this.mobileWidgetList.size();
    while (b < i) {
      paramXMLPrintWriter.startTAG("Widget").attr("widgetName", this.mobileWidgetList.get(b)).end();
      b++;
    } 
    paramXMLPrintWriter.end();
  }
  
  protected void writeLayoutTypeAttr(XMLPrintWriter paramXMLPrintWriter) {
    paramXMLPrintWriter.startTAG("BodyLayoutType").attr("type", this.layoutType.getTypeValue()).end();
  }
  
  public int getCompState() {
    return this.compState;
  }
  
  public void setCompState(int paramInt) {
    this.compState = paramInt;
  }
  
  public double getResolutionScaling() {
    return this.resolutionScaling;
  }
  
  public void setResolutionScaling(double paramDouble) {
    this.resolutionScaling = paramDouble;
  }
  
  public WBodyLayoutType getBodyLayoutType() {
    return this.layoutType;
  }
  
  public void setLayoutType(WBodyLayoutType paramWBodyLayoutType) {
    this.layoutType = paramWBodyLayoutType;
  }
  
  public int getContainerWidth() {
    return this.containerWidth;
  }
  
  public void setContainerWidth(int paramInt) {
    this.containerWidth = paramInt;
  }
  
  public int getContainerHeight() {
    return this.containerHeight;
  }
  
  public void setContainerHeight(int paramInt) {
    this.containerHeight = paramInt;
  }
  
  public boolean isAppRelayout() {
    return this.appRelayout;
  }
  
  public void setAppRelayout(boolean paramBoolean) {
    this.appRelayout = paramBoolean;
  }
  
  public int getContentWidth() {
    return this.containerWidth;
  }
  
  public int getContentHeight() {
    return this.containerHeight;
  }
  
  public void setHasResized(boolean paramBoolean) {
    this.hasResized = paramBoolean;
  }
  
  public void resize(double paramDouble1, double paramDouble2, double paramDouble3) {
    ReportFitProcessor reportFitProcessor = (ReportFitProcessor)ExtraClassManager.getInstance().getSingle("ReportFitProcessor");
    if (reportFitProcessor == null)
      return; 
    reportFitProcessor.resizeFitLayout((Widget)this, paramDouble1, paramDouble2, paramDouble3);
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\WFitLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */