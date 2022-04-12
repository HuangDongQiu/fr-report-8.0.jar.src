package com.fr.form.ui;

import com.fr.base.FRContext;
import com.fr.base.GraphHelper;
import com.fr.base.ResizableElement;
import com.fr.base.mobile.ElementCaseMobileAttrProvider;
import com.fr.base.mobile.MobileFitAttrState;
import com.fr.form.FormElementCaseProvider;
import com.fr.general.xml.GeneralXMLTools;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.plugin.ExtraClassManager;
import com.fr.report.fun.ReportFitProcessor;
import com.fr.script.Calculator;
import com.fr.stable.ArrayUtils;
import com.fr.stable.DependenceProvider;
import com.fr.stable.bridge.StableFactory;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.fun.ExtraAttrMapProvider;
import com.fr.stable.fun.IOFileAttrMark;
import com.fr.stable.fun.ReportFitAttrProvider;
import com.fr.stable.script.CalculatorProvider;
import com.fr.stable.web.Repository;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLReadable;
import com.fr.stable.xml.XMLable;
import com.fr.stable.xml.XMLableReader;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ElementCaseEditor extends AbstractBorderStyleWidget implements FormHyperlinkEditor, DependenceProvider, ElementCaseEditorProvider, ResizableElement {
  private static final int DEFAULT_PC = 1;
  
  private static final double DEFAULT_APP = 0.75D;
  
  private static final double MAX_HEIGHT = 0.8D;
  
  private double editorWidth;
  
  private double editorHeight;
  
  private double heightPercent;
  
  private FormElementCaseProvider elementcase = (FormElementCaseProvider)StableFactory.getMarkedInstanceObjectFromClass("FormElementCase", FormElementCaseProvider.class);
  
  private boolean showToolBar = false;
  
  private boolean heightRestrict = false;
  
  private ReportFitAttrProvider reportFitAttr;
  
  private ElementCaseMobileAttrProvider mobileAttr = (ElementCaseMobileAttrProvider)StableFactory.getMarkedInstanceObjectFromClass("ElementCaseMobileAttrProvider", ElementCaseMobileAttrProvider.class);
  
  private int reportFitInPc = 1;
  
  private Map<String, IOFileAttrMark> attrMarkMap;
  
  public ExtraAttrMapProvider getMapProvider() {
    if (this.attrMarkMap != null) {
      Iterator<Map.Entry> iterator = this.attrMarkMap.entrySet().iterator();
      if (iterator.hasNext()) {
        Map.Entry entry = iterator.next();
        return getAttrMark((String)entry.getKey());
      } 
    } 
    return null;
  }
  
  public boolean isShowRefresh() {
    if (this.attrMarkMap != null) {
      Set set = ExtraClassManager.getInstance().getArray("ExtraAttrMapProvider");
      for (ExtraAttrMapProvider extraAttrMapProvider : set) {
        if (extraAttrMapProvider == null)
          continue; 
        extraAttrMapProvider = getMapProvider();
        return extraAttrMapProvider.isShowExtraAttr(this.attrMarkMap);
      } 
    } 
    return false;
  }
  
  public void setShowRefresh(boolean paramBoolean) {
    Set set = ExtraClassManager.getInstance().getArray("ExtraAttrMapProvider");
    for (ExtraAttrMapProvider extraAttrMapProvider : set) {
      if (extraAttrMapProvider == null)
        continue; 
      if (this.attrMarkMap == null)
        addAttrMark((IOFileAttrMark)extraAttrMapProvider); 
      extraAttrMapProvider = getMapProvider();
      extraAttrMapProvider.setShowExtraAttr(paramBoolean);
    } 
  }
  
  public double getShowRefreshRrequency() {
    if (this.attrMarkMap != null) {
      Set set = ExtraClassManager.getInstance().getArray("ExtraAttrMapProvider");
      for (ExtraAttrMapProvider extraAttrMapProvider : set) {
        if (extraAttrMapProvider == null)
          continue; 
        extraAttrMapProvider = getMapProvider();
        return extraAttrMapProvider.getAttrValue(this.attrMarkMap);
      } 
    } 
    return 0.0D;
  }
  
  public void setShowRefreshRrequency(double paramDouble) {
    if (this.attrMarkMap != null) {
      Set set = ExtraClassManager.getInstance().getArray("ExtraAttrMapProvider");
      for (ExtraAttrMapProvider extraAttrMapProvider : set) {
        if (extraAttrMapProvider == null)
          continue; 
        extraAttrMapProvider = getMapProvider();
        extraAttrMapProvider.setAttrValue(paramDouble);
      } 
    } 
  }
  
  public MobileFitAttrState getHorziontalAttr() {
    return this.mobileAttr.getHorziontalAttr();
  }
  
  public void setHorziontalAttr(MobileFitAttrState paramMobileFitAttrState) {
    this.mobileAttr.setHorziontalAttr(paramMobileFitAttrState);
  }
  
  public MobileFitAttrState getVerticalAttr() {
    return this.mobileAttr.getVerticalAttr();
  }
  
  public void setVerticalAttr(MobileFitAttrState paramMobileFitAttrState) {
    this.mobileAttr.setVerticalAttr(paramMobileFitAttrState);
  }
  
  public String getXType() {
    return "elementcase";
  }
  
  public boolean isHeightRestrict() {
    return this.heightRestrict;
  }
  
  public double getHeightPercent() {
    return (this.heightPercent == 0.0D) ? 0.75D : this.heightPercent;
  }
  
  public boolean isShowToolBar() {
    return this.showToolBar;
  }
  
  public void setShowToolBar(boolean paramBoolean) {
    this.showToolBar = paramBoolean;
  }
  
  public void setHeightRestrict(boolean paramBoolean) {
    this.heightRestrict = paramBoolean;
  }
  
  public void setHeightPercent(double paramDouble) {
    if (paramDouble > 0.8D)
      paramDouble = 0.8D; 
    if (paramDouble < 0.0D)
      paramDouble = 0.0D; 
    this.heightPercent = paramDouble;
  }
  
  public void setReportFitInPc(int paramInt) {
    this.reportFitInPc = paramInt;
  }
  
  public int getFitStateInPC() {
    return (this.reportFitAttr != null) ? this.reportFitAttr.fitStateInPC() : this.reportFitInPc;
  }
  
  public void setReportFitAttr(ReportFitAttrProvider paramReportFitAttrProvider) {
    this.reportFitAttr = paramReportFitAttrProvider;
  }
  
  public ReportFitAttrProvider getReportFitAttr() {
    return this.reportFitAttr;
  }
  
  public void setFitStateInPC(int paramInt) {
    if (this.reportFitAttr == null) {
      ReportFitProcessor reportFitProcessor = (ReportFitProcessor)ExtraClassManager.getInstance().getSingle("ReportFitProcessor");
      if (reportFitProcessor != null)
        this.reportFitAttr = reportFitProcessor.newInstanceFitAttr(); 
    } 
    this.reportFitAttr.setFitStateInPC(paramInt);
  }
  
  public boolean isEditor() {
    return false;
  }
  
  public String[] supportedEvents() {
    return new String[] { "click" };
  }
  
  public FormElementCaseProvider getElementCase() {
    return this.elementcase;
  }
  
  public void setElementCase(FormElementCaseProvider paramFormElementCaseProvider) {
    this.elementcase = paramFormElementCaseProvider;
  }
  
  public void resize(double paramDouble1, double paramDouble2, double paramDouble3) {
    int i = GraphHelper.getLineStyleSize(getBorderStyle().getBorder());
    PaddingMargin paddingMargin = getMargin();
    int j = paddingMargin.getLeft() + paddingMargin.getRight();
    int k = paddingMargin.getTop() + paddingMargin.getBottom();
    this.editorWidth = paramDouble1 - (i * 2) - j;
    this.editorHeight = paramDouble2 - (i * 2) - k;
    ReportFitProcessor reportFitProcessor = (ReportFitProcessor)ExtraClassManager.getInstance().getSingle("ReportFitProcessor");
    if (reportFitProcessor != null)
      reportFitProcessor.scaleFontSize(paramDouble3, this.elementcase); 
  }
  
  public int getContentHeight() {
    return (int)this.editorHeight;
  }
  
  public int getContentWidth() {
    return (int)this.editorWidth - this.margin.getLeft() + this.margin.getRight();
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    super.readXML(paramXMLableReader);
    if (paramXMLableReader.isChildNode())
      if (paramXMLableReader.getTagName().equals("FormElementCase")) {
        FormElementCaseProvider formElementCaseProvider = (FormElementCaseProvider)StableFactory.getMarkedInstanceObjectFromClass("FormElementCase", FormElementCaseProvider.class);
        paramXMLableReader.readXMLObject((XMLReadable)formElementCaseProvider);
        this.elementcase = formElementCaseProvider;
      } else if ("StyleList".equals(paramXMLableReader.getTagName())) {
        this.elementcase.readStyleXML(paramXMLableReader);
      } else if (paramXMLableReader.getTagName().equals("showToolbar")) {
        setShowToolBar(paramXMLableReader.getAttrAsBoolean("showtoolbar", true));
      } else if (paramXMLableReader.getTagName().equals("heightRestrict")) {
        setHeightRestrict(paramXMLableReader.getAttrAsBoolean("heightrestrict", true));
      } else if (paramXMLableReader.getTagName().equals("heightPercent")) {
        setHeightPercent(paramXMLableReader.getAttrAsDouble("heightpercent", 0.75D));
      } else if (paramXMLableReader.getTagName().equals("ReportFitAttr")) {
        readFitAttr(paramXMLableReader);
        compatiableOldXml(paramXMLableReader);
      } else if (paramXMLableReader.getTagName().equals("ElementCaseMobileAttrProvider")) {
        this.mobileAttr.readXML(paramXMLableReader);
      }  
    if (paramXMLableReader.isChildNode()) {
      String str = paramXMLableReader.getTagName();
      Set set = ExtraClassManager.getInstance().getArray("IOFileAttrMark");
      for (IOFileAttrMark iOFileAttrMark : set) {
        if (str.equals(iOFileAttrMark.xmlTag())) {
          IOFileAttrMark iOFileAttrMark1 = iOFileAttrMark.clone();
          paramXMLableReader.readXMLObject((XMLReadable)iOFileAttrMark1);
          addAttrMark(iOFileAttrMark1);
        } 
      } 
    } 
  }
  
  private void compatiableOldXml(XMLableReader paramXMLableReader) {
    int i = paramXMLableReader.getAttrAsInt("fitHorizontalInApp", -1);
    int j = paramXMLableReader.getAttrAsInt("fitVerticalInApp", -1);
    if (i != -1 || j != -1) {
      setHorziontalAttr(MobileFitAttrState.parse(i));
      setVerticalAttr(MobileFitAttrState.parse(j));
    } 
  }
  
  private void readFitAttr(XMLableReader paramXMLableReader) {
    ReportFitProcessor reportFitProcessor = (ReportFitProcessor)ExtraClassManager.getInstance().getSingle("ReportFitProcessor");
    if (reportFitProcessor == null)
      return; 
    ReportFitAttrProvider reportFitAttrProvider = reportFitProcessor.newInstanceFitAttr();
    reportFitAttrProvider.readXML(paramXMLableReader);
    this.reportFitAttr = reportFitAttrProvider;
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    super.writeXML(paramXMLPrintWriter);
    if (this.elementcase != null) {
      paramXMLPrintWriter.startTAG("FormElementCase");
      this.elementcase.writeXML(paramXMLPrintWriter);
      paramXMLPrintWriter.end();
      this.elementcase.writeStyleXML(paramXMLPrintWriter);
    } 
    paramXMLPrintWriter.startTAG("showToolbar").attr("showtoolbar", this.showToolBar).end();
    paramXMLPrintWriter.startTAG("heightRestrict").attr("heightrestrict", this.heightRestrict).end();
    paramXMLPrintWriter.startTAG("heightPercent").attr("heightpercent", getHeightPercent()).end();
    if (this.attrMarkMap != null)
      for (Map.Entry<String, IOFileAttrMark> entry : this.attrMarkMap.entrySet())
        GeneralXMLTools.writeXMLable(paramXMLPrintWriter, (XMLable)entry.getValue(), (String)entry.getKey());  
    if (this.reportFitAttr != null)
      this.reportFitAttr.writeXML(paramXMLPrintWriter); 
    this.mobileAttr.writeXML(paramXMLPrintWriter);
  }
  
  public Object clone() throws CloneNotSupportedException {
    ElementCaseEditor elementCaseEditor = (ElementCaseEditor)super.clone();
    if (this.elementcase != null)
      elementCaseEditor.elementcase = (FormElementCaseProvider)this.elementcase.clone(); 
    if (this.attrMarkMap != null) {
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      for (Map.Entry<String, IOFileAttrMark> entry : this.attrMarkMap.entrySet())
        hashMap.put(entry.getKey(), ((IOFileAttrMark)entry.getValue()).clone()); 
      elementCaseEditor.attrMarkMap = (Map)hashMap;
    } 
    return elementCaseEditor;
  }
  
  public void toImage(Calculator paramCalculator, Rectangle paramRectangle, Graphics paramGraphics) {
    this.elementcase.setName(getWidgetName());
    BufferedImage bufferedImage = this.elementcase.toImage(paramCalculator, paramRectangle.width, paramRectangle.height);
    paramGraphics.drawImage(bufferedImage, paramRectangle.x, paramRectangle.y, null);
  }
  
  public JSONObject createJSONConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    JSONObject jSONObject = super.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
    jSONObject.put("maxHeightPercent4Phone", getHeightPercent());
    jSONObject.put("isMaxHeightLimit4Phone", this.heightRestrict);
    jSONObject.put("showToolbar", this.showToolBar);
    this.mobileAttr.createJSONConfig(jSONObject);
    createExtraJSONConfig(jSONObject);
    String[] arrayOfString = dependence((CalculatorProvider)paramCalculator);
    if (!ArrayUtils.isEmpty((Object[])arrayOfString))
      jSONObject.put("valueDependence", arrayOfString); 
    paramNodeVisitor.visit("report", jSONObject);
    return jSONObject;
  }
  
  public void createExtraJSONConfig(JSONObject paramJSONObject) throws JSONException {
    if (this.attrMarkMap != null)
      for (Map.Entry<String, IOFileAttrMark> entry : this.attrMarkMap.entrySet()) {
        ExtraAttrMapProvider extraAttrMapProvider = getAttrMark((String)entry.getKey());
        extraAttrMapProvider.createJSONConfig(paramJSONObject);
      }  
  }
  
  public int[] getValueType() {
    return new int[0];
  }
  
  public void createValueResult(DataControl paramDataControl, Calculator paramCalculator, JSONObject paramJSONObject1, JSONObject paramJSONObject2) {
    try {
      paramJSONObject1.put(this.widgetName, "recal");
    } catch (JSONException jSONException) {
      FRContext.getLogger().error(jSONException.getMessage());
    } 
  }
  
  public String[] dependence(CalculatorProvider paramCalculatorProvider) {
    return this.elementcase.dependence(paramCalculatorProvider);
  }
  
  public boolean canCurrentMarginAvailable(PaddingMargin paramPaddingMargin) {
    return true;
  }
  
  public void addAttrMark(IOFileAttrMark paramIOFileAttrMark) {
    if (this.attrMarkMap == null)
      this.attrMarkMap = new HashMap<String, IOFileAttrMark>(); 
    this.attrMarkMap.put(paramIOFileAttrMark.xmlTag(), paramIOFileAttrMark);
  }
  
  public <T extends IOFileAttrMark> T getAttrMark(String paramString) {
    return (T)((this.attrMarkMap == null) ? null : this.attrMarkMap.get(paramString));
  }
  
  public boolean batchRenameTdName(Map<String, String> paramMap) {
    if (this.elementcase == null || paramMap == null || paramMap.isEmpty())
      return true; 
    for (Map.Entry<String, String> entry : paramMap.entrySet()) {
      if (!this.elementcase.renameTableData((String)entry.getKey(), (String)entry.getValue()))
        return false; 
    } 
    return true;
  }
  
  public boolean renameTableData(String paramString1, String paramString2) {
    return (this.elementcase == null) ? true : this.elementcase.renameTableData(paramString1, paramString2);
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\ElementCaseEditor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */