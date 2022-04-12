package com.fr.form.ui.container;

import com.fr.base.background.ColorBackground;
import com.fr.data.TableDataSource;
import com.fr.form.main.ExtraFormClassManager;
import com.fr.form.main.Form;
import com.fr.form.main.WidgetGather;
import com.fr.form.main.WidgetGatherAdapter;
import com.fr.form.parameter.FormSubmitButton;
import com.fr.form.ui.Label;
import com.fr.form.ui.Widget;
import com.fr.form.ui.WidgetValue;
import com.fr.general.Background;
import com.fr.general.ComparatorUtils;
import com.fr.js.AbstractJavaScript;
import com.fr.json.JSONArray;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.StringUtils;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.fun.MobileJSONProcessor;
import com.fr.stable.web.Repository;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLReadable;
import com.fr.stable.xml.XMLableReader;
import com.fr.web.RepositoryHelper;
import com.fr.web.core.SessionIDInfor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WParameterLayout extends WAbsoluteLayout {
  private ArrayList<String> mobileWidgetList = new ArrayList<String>();
  
  private boolean isSorted = false;
  
  private HashMap<String, String> nameTagMap = new HashMap<String, String>();
  
  private boolean delayDisplayContent = true;
  
  private boolean display = true;
  
  private int position = 0;
  
  private int design_width = 960;
  
  public WParameterLayout() {
    setBackground((Background)ColorBackground.getInstance());
  }
  
  public boolean isDisplay() {
    return this.display;
  }
  
  public void setDisplay(boolean paramBoolean) {
    this.display = paramBoolean;
  }
  
  public boolean isDelayDisplayContent() {
    return this.delayDisplayContent;
  }
  
  public void setDelayDisplayContent(boolean paramBoolean) {
    this.delayDisplayContent = paramBoolean;
  }
  
  public int getPosition() {
    return this.position;
  }
  
  public void setPosition(int paramInt) {
    this.position = paramInt;
  }
  
  public int getDesignWidth() {
    return this.design_width;
  }
  
  public void setDesignWidth(int paramInt) {
    this.design_width = paramInt;
  }
  
  public String getXType() {
    return "parameter";
  }
  
  public Dimension getMinDesignSize() {
    byte b1 = 0;
    byte b2 = 0;
    byte b3 = 0;
    int i = getWidgetCount();
    while (b3 < i) {
      WAbsoluteLayout.BoundsWidget boundsWidget = (WAbsoluteLayout.BoundsWidget)getWidget(b3);
      if (boundsWidget.isVisible()) {
        Rectangle rectangle = boundsWidget.getBounds();
        b1 = (rectangle.x + rectangle.width > b1) ? (rectangle.x + rectangle.width) : b1;
        b2 = (rectangle.y + rectangle.height > b2) ? (rectangle.y + rectangle.height) : b2;
      } 
      b3++;
    } 
    return new Dimension(b1, b2);
  }
  
  public JSONObject createJSONConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    JSONObject jSONObject = super.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
    jSONObject.put("paraDisplay", this.display);
    jSONObject.put("delayDisplayContent", this.delayDisplayContent);
    jSONObject.put("position", asPositionString(this.position));
    jSONObject.put("width", this.design_width);
    jSONObject.put("parambg", jSONObject.opt("widgetBackground"));
    jSONObject.put("widgetBackground", "");
    return jSONObject;
  }
  
  private String asPositionString(int paramInt) {
    switch (paramInt) {
      case 2:
        return "right";
      case 1:
        return "center";
    } 
    return "left";
  }
  
  public String[][] getWidgetNameTag() {
    refreshTagList();
    int i = this.mobileWidgetList.size();
    String[][] arrayOfString = new String[i][2];
    for (byte b = 0; b < i; b++) {
      String str1 = this.mobileWidgetList.get(b);
      String str2 = this.nameTagMap.get(str1);
      arrayOfString[b][1] = str1;
      arrayOfString[b][0] = (str2 == null) ? "" : str2;
    } 
    return arrayOfString;
  }
  
  public void refreshTagList() {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    byte b1 = 0;
    int i = getWidgetCount();
    while (b1 < i) {
      WAbsoluteLayout.BoundsWidget boundsWidget = (WAbsoluteLayout.BoundsWidget)getWidget(b1);
      if (boundsWidget != null && !shouldIgnoureSubmitButtonJSON(boundsWidget))
        initXYMap(boundsWidget, (HashMap)hashMap); 
      b1++;
    } 
    Object[] arrayOfObject = hashMap.keySet().toArray();
    Arrays.sort(arrayOfObject);
    if (!this.isSorted)
      this.mobileWidgetList.clear(); 
    i = 0;
    int j = arrayOfObject.length;
    while (i < j) {
      HashMap<Double, ArrayList<String>> hashMap1 = (HashMap)hashMap.get(arrayOfObject[i]);
      Object[] arrayOfObject1 = hashMap1.keySet().toArray();
      Arrays.sort(arrayOfObject1);
      byte b = 0;
      int k = arrayOfObject1.length;
      while (b < k) {
        ArrayList arrayList1 = (ArrayList)hashMap1.get(arrayOfObject1[b]);
        for (String str : arrayList1) {
          addToMoblieList(str);
          traversePreWidget(b, hashMap1, arrayOfObject1, str);
        } 
        b++;
      } 
      i++;
    } 
    for (i = 0; i < this.mobileWidgetList.size(); i++) {
      if (getWidget(this.mobileWidgetList.get(i)) == null)
        this.mobileWidgetList.remove(i); 
    } 
    Iterator<String> iterator = this.nameTagMap.keySet().iterator();
    ArrayList<String> arrayList = new ArrayList();
    while (iterator.hasNext()) {
      String str = iterator.next();
      if (!this.mobileWidgetList.contains(str))
        arrayList.add(str); 
    } 
    for (byte b2 = 0; b2 < arrayList.size(); b2++)
      this.nameTagMap.remove(arrayList.get(b2)); 
  }
  
  private boolean shouldIgnoureSubmitButtonJSON(WAbsoluteLayout.BoundsWidget paramBoundsWidget) {
    MobileJSONProcessor mobileJSONProcessor = (MobileJSONProcessor)ExtraFormClassManager.getInstance().getSingle("MobileJSONProcessor");
    return (paramBoundsWidget.getWidget().acceptType(new Class[] { FormSubmitButton.class }) && (mobileJSONProcessor == null || !mobileJSONProcessor.shouldCreateSubmitBtnJSON()));
  }
  
  private void addToMoblieList(String paramString) {
    addToMoblieList(-1, paramString);
  }
  
  private void addToMoblieList(int paramInt, String paramString) {
    Widget widget = getWidget(paramString);
    if (widget == null || widget.acceptType(new Class[] { Label.class }))
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
  
  private void traversePreWidget(int paramInt, HashMap<Double, ArrayList<String>> paramHashMap, Object[] paramArrayOfObject, final String curName) {
    if (paramInt == 0)
      return; 
    final String preName = ((ArrayList<String>)paramHashMap.get(paramArrayOfObject[paramInt - 1])).get(0);
    Form.traversalWidget((Widget)this, (WidgetGather)new WidgetGatherAdapter() {
          public void dealWith(Widget param1Widget) {
            if (ComparatorUtils.equals(preName, param1Widget.getWidgetName())) {
              WidgetValue widgetValue = ((Label)param1Widget).getWidgetValue();
              if (StringUtils.isNotEmpty(widgetValue.toString()))
                WParameterLayout.this.nameTagMap.put(curName, widgetValue.toString()); 
            } 
          }
        }Label.class);
  }
  
  public void add2NameTagMap(String paramString1, String paramString2) {
    if (StringUtils.isNotEmpty(paramString1)) {
      this.nameTagMap.put(paramString2, paramString1);
    } else if (this.nameTagMap.containsKey(paramString2)) {
      this.nameTagMap.remove(paramString2);
    } 
  }
  
  private void initXYMap(WAbsoluteLayout.BoundsWidget paramBoundsWidget, HashMap<Double, HashMap<Double, ArrayList<String>>> paramHashMap) {
    Rectangle rectangle = paramBoundsWidget.getBounds();
    HashMap<Double, ArrayList<String>> hashMap = paramHashMap.get(Double.valueOf(rectangle.getY()));
    if (hashMap != null) {
      ArrayList<String> arrayList1 = (ArrayList)hashMap.get(Double.valueOf(rectangle.getX()));
      if (arrayList1 == null)
        arrayList1 = new ArrayList(); 
      if (!arrayList1.contains(paramBoundsWidget.getWidgetName()))
        arrayList1.add(paramBoundsWidget.getWidgetName()); 
      hashMap.put(Double.valueOf(rectangle.getX()), arrayList1);
      return;
    } 
    hashMap = new HashMap<Double, ArrayList<String>>();
    ArrayList<String> arrayList = new ArrayList();
    arrayList.add(paramBoundsWidget.getWidgetName());
    hashMap.put(Double.valueOf(rectangle.getX()), arrayList);
    paramHashMap.put(Double.valueOf(rectangle.getY()), hashMap);
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
  
  private Calculator initCalculator(Repository paramRepository, Form paramForm) {
    SessionIDInfor sessionIDInfor = RepositoryHelper.getSessionIDInfor(paramRepository);
    sessionIDInfor.setAttribute("paramsheet", paramForm);
    Calculator calculator = Calculator.createCalculator();
    calculator.setAttribute(TableDataSource.class, sessionIDInfor.getTableDataSource());
    calculator.setAttribute(AbstractJavaScript.RECALCULATE_TAG, Boolean.TRUE);
    return calculator;
  }
  
  public void createPara4Mobile(Repository paramRepository, JSONObject paramJSONObject, Form paramForm) throws JSONException {
    Calculator calculator = initCalculator(paramRepository, paramForm);
    if (!paramForm.initWidgetData((Widget)this, paramRepository, calculator))
      return; 
    JSONArray jSONArray = new JSONArray();
    paramJSONObject.put("parameter", jSONArray);
    if (!paramJSONObject.has("delay"))
      paramJSONObject.put("delay", this.delayDisplayContent); 
    if (!paramJSONObject.has("isShowWindow"))
      paramJSONObject.put("isShowWindow", this.display); 
    String[][] arrayOfString = getWidgetNameTag();
    byte b = 0;
    int i = arrayOfString.length;
    while (b < i) {
      JSONObject jSONObject = new JSONObject();
      String str1 = arrayOfString[b][0];
      String str2 = arrayOfString[b][1];
      if (StringUtils.isNotEmpty(str1))
        jSONObject.put("label", str1); 
      if (StringUtils.isNotEmpty(str2)) {
        JSONObject jSONObject1 = paramForm.getWidgetByName(str2).createJSONConfig(paramRepository, calculator, null);
        jSONObject.put("widget", jSONObject1);
      } 
      jSONArray.put(jSONObject);
      b++;
    } 
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    super.readXML(paramXMLableReader);
    if (paramXMLableReader.isChildNode()) {
      String str = paramXMLableReader.getTagName();
      if ("Sorted".equals(str)) {
        this.isSorted = paramXMLableReader.getAttrAsBoolean("sorted", false);
      } else if (str.equals("Display")) {
        setDisplay(paramXMLableReader.getAttrAsBoolean("display", true));
      } else if (str.equals("DelayDisplayContent")) {
        setDelayDisplayContent(paramXMLableReader.getAttrAsBoolean("delay", true));
      } else if (str.equals("Position")) {
        setPosition(paramXMLableReader.getAttrAsInt("position", 0));
      } else if (str.equals("Design_Width")) {
        setDesignWidth(paramXMLableReader.getAttrAsInt("design_width", 0));
      } else if (str.equals("MobileWidgetList")) {
        paramXMLableReader.readXMLObject(new XMLReadable() {
              public void readXML(XMLableReader param1XMLableReader) {
                if ("Widget".equals(param1XMLableReader.getTagName()))
                  WParameterLayout.this.addToMoblieList(param1XMLableReader.getAttrAsString("widgetName", "")); 
              }
            });
      } else if (str.equals("WidgetNameTagMap")) {
        paramXMLableReader.readXMLObject(new XMLReadable() {
              public void readXML(XMLableReader param1XMLableReader) {
                if ("NameTag".equals(param1XMLableReader.getTagName()))
                  WParameterLayout.this.nameTagMap.put(param1XMLableReader.getAttrAsString("name", ""), param1XMLableReader.getAttrAsString("tag", "")); 
              }
            });
      } 
    } 
  }
  
  protected void writeScalingAttrXML(XMLPrintWriter paramXMLPrintWriter) {}
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    super.writeXML(paramXMLPrintWriter);
    paramXMLPrintWriter.startTAG("Sorted").attr("sorted", this.isSorted).end();
    paramXMLPrintWriter.startTAG("Display").attr("display", this.display).end();
    paramXMLPrintWriter.startTAG("DelayDisplayContent").attr("delay", this.delayDisplayContent).end();
    paramXMLPrintWriter.startTAG("Position").attr("position", this.position).end();
    paramXMLPrintWriter.startTAG("Design_Width").attr("design_width", this.design_width).end();
    paramXMLPrintWriter.startTAG("MobileWidgetList");
    byte b = 0;
    int i = this.mobileWidgetList.size();
    while (b < i) {
      paramXMLPrintWriter.startTAG("Widget").attr("widgetName", this.mobileWidgetList.get(b)).end();
      b++;
    } 
    paramXMLPrintWriter.end();
    paramXMLPrintWriter.startTAG("WidgetNameTagMap");
    for (Map.Entry<String, String> entry : this.nameTagMap.entrySet())
      paramXMLPrintWriter.startTAG("NameTag").attr("name", (String)entry.getKey()).attr("tag", (String)entry.getValue()).end(); 
    paramXMLPrintWriter.end();
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\WParameterLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */