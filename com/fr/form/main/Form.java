package com.fr.form.main;

import com.fr.base.BaseXMLUtils;
import com.fr.base.FRContext;
import com.fr.base.Formula;
import com.fr.base.Parameter;
import com.fr.base.ParameterHelper;
import com.fr.base.ParameterHolder;
import com.fr.base.ParameterMapNameSpace;
import com.fr.base.TableData;
import com.fr.base.Utils;
import com.fr.base.io.IOFile;
import com.fr.base.io.XMLReadHelper;
import com.fr.data.DataSourcePool;
import com.fr.data.DataSourcePoolProvider;
import com.fr.data.TableDataSource;
import com.fr.form.FormElementCaseProvider;
import com.fr.form.FormProvider;
import com.fr.form.main.mobile.FormMobileAttr;
import com.fr.form.ui.ChartEditorProvider;
import com.fr.form.ui.DataControl;
import com.fr.form.ui.ElementCaseEditor;
import com.fr.form.ui.ElementCaseEditorProvider;
import com.fr.form.ui.Widget;
import com.fr.form.ui.WidgetValue;
import com.fr.form.ui.WidgetXmlUtils;
import com.fr.form.ui.container.WAbsoluteLayout;
import com.fr.form.ui.container.WBorderLayout;
import com.fr.form.ui.container.WCardLayout;
import com.fr.form.ui.container.WLayout;
import com.fr.form.ui.container.WParameterLayout;
import com.fr.form.ui.container.WScaleLayout;
import com.fr.form.ui.container.WTitleLayout;
import com.fr.general.ComparatorUtils;
import com.fr.general.FRLogger;
import com.fr.general.xml.GeneralXMLTools;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.plugin.ExtraClassManager;
import com.fr.privilege.PrivilegeEditedRoleProvider;
import com.fr.privilege.finegrain.WidgetPrivilegeControl;
import com.fr.report.fun.ReportFitProcessor;
import com.fr.script.Calculator;
import com.fr.stable.EmbParaFilter;
import com.fr.stable.FormulaProvider;
import com.fr.stable.ParameterProvider;
import com.fr.stable.Primitive;
import com.fr.stable.StringUtils;
import com.fr.stable.UtilEvalError;
import com.fr.stable.bridge.StableFactory;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.fun.FitProvider;
import com.fr.stable.fun.ReportFitAttrProvider;
import com.fr.stable.script.NameSpace;
import com.fr.stable.web.Repository;
import com.fr.stable.xml.StableXMLUtils;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLReadable;
import com.fr.stable.xml.XMLable;
import com.fr.stable.xml.XMLableReader;
import com.fr.web.core.SessionDealWith;
import com.fr.web.core.SessionIDInfor;
import com.fr.web.core.WidgetSessionIDInfor;
import com.fr.web.utils.WebUtils;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Form extends IOFile implements PrivilegeEditedRoleProvider, FormProvider, ParameterHolder, FitProvider, DataSourcePoolProvider {
  public static final String FIT = "__FIT__";
  
  public static final String PAPER_WHDTH = "_PAPERWIDTH";
  
  public static final String PAPER_HEIGHT = "_PAPERHEIGHT";
  
  public static final String _SHOWPARA = "_SHOWPARA";
  
  private static final String XML_TAG = "Form";
  
  private static final int SCROLL_BAR_HEIGHT = 10;
  
  private static final int DEFAULT_FIT = 1;
  
  private static final int NONE_FIT = -1;
  
  private WLayout container;
  
  protected ReportFitAttrProvider fitAttr = null;
  
  protected Set<Parameter> parameters = new HashSet<Parameter>();
  
  private DataSourcePool dataSourcePool;
  
  protected FormMobileAttr mobileAttr;
  
  public Form() {
    this(new WAbsoluteLayout());
  }
  
  public Form(WAbsoluteLayout paramWAbsoluteLayout) {
    setContainer((WLayout)paramWAbsoluteLayout);
  }
  
  public Form(WLayout paramWLayout) {
    setContainer(paramWLayout);
  }
  
  public WLayout getContainer() {
    return this.container;
  }
  
  public void setContainer(WLayout paramWLayout) {
    this.container = paramWLayout;
  }
  
  public Widget getWidgetByName(String paramString) {
    return (this.container == null) ? null : findWidgetByName((Widget)this.container, paramString);
  }
  
  public FormMobileAttr getFormMobileAttr() {
    if (this.mobileAttr == null)
      this.mobileAttr = new FormMobileAttr(); 
    return this.mobileAttr;
  }
  
  public void setFormMobileAttr(FormMobileAttr paramFormMobileAttr) {
    this.mobileAttr = paramFormMobileAttr;
  }
  
  public FormElementCaseProvider getElementCaseByName(String paramString) {
    Widget widget = getWidgetByName(paramString);
    return (widget == null || !widget.acceptType(new Class[] { ElementCaseEditor.class })) ? (FormElementCaseProvider)StableFactory.getMarkedInstanceObjectFromClass("FormElementCase", FormElementCaseProvider.class) : ((ElementCaseEditor)widget).getElementCase();
  }
  
  public Parameter[] getTemplateParameters() {
    return this.parameters.<Parameter>toArray(new Parameter[this.parameters.size()]);
  }
  
  public Parameter[] getParameters() {
    HashSet<Parameter> hashSet = new HashSet();
    Calculator calculator = Calculator.createCalculator();
    calculator.setAttribute(TableDataSource.class, this);
    ParameterHelper.addGlobal_ParameterToSet(hashSet);
    hashSet.addAll(this.parameters);
    try {
      byte b = 0;
      int i = this.tableDataList.size();
      while (b < i) {
        TableData tableData = this.tableDataList.get(b);
        ParameterProvider[] arrayOfParameterProvider = FRContext.getCurrentEnv().getTableDataParameters(tableData);
        if (tableData != null && arrayOfParameterProvider != null)
          for (ParameterProvider parameterProvider : arrayOfParameterProvider) {
            if (!EmbParaFilter.isFRLayerTypePara(parameterProvider))
              hashSet.add(parameterProvider); 
          }  
        b++;
      } 
    } catch (Exception exception) {
      FRLogger.getLogger().error(exception.getMessage());
    } 
    return hashSet.<Parameter>toArray(new Parameter[hashSet.size()]);
  }
  
  private Widget findWidgetByName(Widget paramWidget, String paramString) {
    if (ComparatorUtils.equalsIgnoreCase(paramWidget.getWidgetName(), paramString)) {
      if (paramWidget.acceptType(new Class[] { WTitleLayout.class }) || paramWidget.acceptType(new Class[] { WScaleLayout.class })) {
        int i = ((WLayout)paramWidget).getWidgetCount();
        byte b = 0;
        while (b < i) {
          Widget widget1 = ((WLayout)paramWidget).getWidget(b);
          if (widget1 instanceof WAbsoluteLayout.BoundsWidget)
            widget1 = ((WAbsoluteLayout.BoundsWidget)widget1).getWidget(); 
          Widget widget2 = findWidgetByName(widget1, paramString);
          if (widget2 == null) {
            b++;
            continue;
          } 
          return widget2;
        } 
      } 
      return paramWidget;
    } 
    if (paramWidget instanceof WLayout) {
      int i = ((WLayout)paramWidget).getWidgetCount();
      byte b = 0;
      while (b < i) {
        Widget widget1 = ((WLayout)paramWidget).getWidget(b);
        if (widget1 instanceof WAbsoluteLayout.BoundsWidget)
          widget1 = ((WAbsoluteLayout.BoundsWidget)widget1).getWidget(); 
        Widget widget2 = findWidgetByName(widget1, paramString);
        if (widget2 == null) {
          b++;
          continue;
        } 
        return widget2;
      } 
    } 
    return null;
  }
  
  public boolean isNameExist(String paramString) {
    return (getWidgetByName(paramString) != null);
  }
  
  public boolean renameTableData(String paramString1, String paramString2) {
    if (super.renameTableData(paramString1, paramString2)) {
      if (getTableData(paramString1) == null)
        _renameTableData(paramString1, paramString2); 
      return true;
    } 
    return false;
  }
  
  protected void _renameTableData(final String oldName, final String newName) {
    traversalWidget((Widget)this.container, new WidgetGather() {
          public void dealWith(Widget param1Widget) {
            DataControl dataControl = (DataControl)param1Widget;
            if (dataControl.getWidgetValue() != null)
              dataControl.getWidgetValue().renameTableData(oldName, newName); 
          }
          
          public boolean dealWithAllCards() {
            return true;
          }
        }DataControl.class);
    traversalWidget((Widget)this.container, new WidgetGather() {
          public void dealWith(Widget param1Widget) {
            ElementCaseEditor elementCaseEditor = (ElementCaseEditor)param1Widget;
            elementCaseEditor.renameTableData(oldName, newName);
          }
          
          public boolean dealWithAllCards() {
            return true;
          }
        }ElementCaseEditor.class);
  }
  
  public JSONObject createJSONConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    if (!initWidgetData(paramRepository, paramCalculator))
      return JSONObject.EMPTY; 
    JSONObject jSONObject = this.container.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
    jSONObject.put("__FIT__", isFitInBrowser());
    jSONObject.put("refresh", getFormMobileAttr().isRefresh());
    return jSONObject;
  }
  
  public boolean isFitInBrowser() {
    ReportFitProcessor reportFitProcessor = (ReportFitProcessor)ExtraClassManager.getInstance().getSingle("ReportFitProcessor");
    return (reportFitProcessor == null) ? false : reportFitProcessor.isFrmFitInBrowser(getFitAttr());
  }
  
  public JSONObject createContentJSONConfig(Repository paramRepository, Calculator paramCalculator) throws JSONException {
    return createContentJSONConfig(paramRepository, paramCalculator, true);
  }
  
  public JSONObject createContentJSONConfig(Repository paramRepository, Calculator paramCalculator, boolean paramBoolean) throws JSONException {
    WLayout wLayout1 = getParaContainer();
    NodeVisitor nodeVisitor = new NodeVisitor();
    if (wLayout1 == null)
      return !paramBoolean ? createEmptyContainerConfig(paramRepository, paramCalculator, nodeVisitor) : createJSONConfig(paramRepository, paramCalculator, nodeVisitor); 
    WLayout wLayout2 = null;
    try {
      wLayout2 = (WLayout)this.container.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      FRContext.getLogger().error(cloneNotSupportedException.getMessage());
    } 
    wLayout2.removeWidget(wLayout2.getWidget(wLayout1.getWidgetName()));
    if (!paramBoolean)
      wLayout2.removeAll(); 
    if (!initWidgetData((Widget)wLayout2, paramRepository, paramCalculator))
      return JSONObject.EMPTY; 
    JSONObject jSONObject = wLayout2.createJSONConfig(paramRepository, paramCalculator, nodeVisitor);
    jSONObject.put("__FIT__", isFitInBrowser());
    jSONObject.put("refresh", getFormMobileAttr().isRefresh());
    return jSONObject;
  }
  
  public JSONObject createParaJSONConfig(Repository paramRepository, Calculator paramCalculator) throws JSONException {
    WLayout wLayout = getParaContainer();
    if (initWidgetData((Widget)wLayout, paramRepository, paramCalculator)) {
      JSONObject jSONObject = wLayout.createJSONConfig(paramRepository, paramCalculator, null);
      jSONObject.put("height", getParameterUIHeight());
      return jSONObject;
    } 
    return JSONObject.EMPTY;
  }
  
  private int getParameterUIHeight() {
    return ((WBorderLayout)this.container).getNorthSize();
  }
  
  public JSONObject createPara4Mobile(Repository paramRepository, SessionIDInfor paramSessionIDInfor) throws JSONException {
    JSONObject jSONObject = new JSONObject();
    jSONObject.put("sessionid", paramSessionIDInfor.getSessionID());
    WLayout wLayout = getParaContainer();
    if (wLayout != null)
      wLayout.createPara4Mobile(paramRepository, jSONObject, this); 
    return jSONObject;
  }
  
  private WLayout getParaContainer() {
    byte b = 0;
    int i = this.container.getWidgetCount();
    while (b < i) {
      Widget widget = this.container.getWidget(b);
      if (!widget.acceptType(new Class[] { WParameterLayout.class })) {
        b++;
        continue;
      } 
      return (WLayout)widget;
    } 
    return null;
  }
  
  public boolean initWidgetData(Repository paramRepository, Calculator paramCalculator) {
    return initWidgetData((Widget)this.container, paramRepository, paramCalculator);
  }
  
  public boolean initWidgetData(Widget paramWidget, Repository paramRepository, Calculator paramCalculator) {
    if (paramWidget == null)
      return false; 
    try {
      Map<String, Object> map = WebUtils.parameters4SessionIDInfor(paramRepository.getHttpServletRequest());
      paramCalculator.pushNameSpace((NameSpace)ParameterMapNameSpace.create(map));
      paramCalculator.pushNameSpace((NameSpace)ParameterMapNameSpace.create(getWidgetDefaultValueMap(map, paramRepository)));
      FormConfig.getInstance().dealWithWidgetData(paramRepository.getHttpServletRequest(), paramWidget, paramCalculator);
    } catch (Exception exception) {
      FRContext.getLogger().error(exception.getMessage());
      return false;
    } 
    return true;
  }
  
  public Map<String, Object> getWidgetDefaultValueMap() {
    return getWidgetDefaultValueMap(new HashMap<String, Object>(), (Repository)null);
  }
  
  public Map<String, Object> getWidgetDefaultValueMap(final Map<String, Object> para, Repository paramRepository) {
    if (paramRepository != null) {
      SessionIDInfor sessionIDInfor = SessionDealWith.getSessionIDInfor(paramRepository.getSessionID());
      if (sessionIDInfor != null)
        para.putAll(sessionIDInfor.getParameterMap4Execute()); 
    } 
    final HashMap<Object, Object> defaultPara = new HashMap<Object, Object>();
    final Calculator calculator = Calculator.createCalculator();
    calculator.pushNameSpace((NameSpace)ParameterMapNameSpace.create(para));
    addFormParameterDefaultValue(para, (Map)hashMap);
    traversalWidget((Widget)getContainer(), new WidgetGatherAdapter() {
          public void dealWith(Widget param1Widget) {
            DataControl dataControl = (DataControl)param1Widget;
            WidgetValue widgetValue = dataControl.getWidgetValue();
            if (widgetValue == null)
              return; 
            String str = param1Widget.getWidgetName();
            if (Form.this.validValue(widgetValue, str, para)) {
              Object object = widgetValue.getValue();
              if (object instanceof Formula)
                object = Form.this.processFormula((Formula)object, calculator); 
              if (object instanceof java.util.Date)
                object = Form.this.processDate(str, object, para); 
              if (object instanceof com.fr.form.data.DataBinding)
                object = Form.this.processDataBinding(widgetValue, str, calculator); 
              defaultPara.put(str, object);
            } 
          }
        }DataControl.class);
    return (Map)hashMap;
  }
  
  private void addFormParameterDefaultValue(Map<String, Object> paramMap1, Map<String, Object> paramMap2) {
    Parameter[] arrayOfParameter = getParameters();
    for (Parameter parameter : arrayOfParameter) {
      String str = parameter.getName();
      Object object1 = parameter.getValue();
      Object object2 = paramMap1.get(str.toUpperCase());
      if (!notNull(object2) && notNull(object1) && StringUtils.isNotEmpty(Utils.objectToString(object1)))
        paramMap2.put(str, object1); 
    } 
  }
  
  private Object processDataBinding(WidgetValue paramWidgetValue, String paramString, Calculator paramCalculator) {
    WidgetValue.WidgetValueInfo widgetValueInfo = new WidgetValue.WidgetValueInfo(paramString);
    paramCalculator.setAttribute(TableDataSource.class, this);
    return paramWidgetValue.createAttrResult(widgetValueInfo, paramCalculator, new JSONObject());
  }
  
  private boolean notNull(Object paramObject) {
    return (paramObject != null && paramObject != Primitive.NULL);
  }
  
  private Object processFormula(Formula paramFormula, Calculator paramCalculator) {
    Object object = null;
    try {
      Parameter[] arrayOfParameter = ParameterHelper.analyze4ParametersFromFormula(paramFormula.getContent());
      for (Parameter parameter : arrayOfParameter) {
        Object object1 = paramCalculator.eval(parameter.getName());
        if (!notNull(object1))
          return paramFormula; 
      } 
      object = paramCalculator.eval((FormulaProvider)paramFormula);
    } catch (UtilEvalError utilEvalError) {}
    return notNull(object) ? object : paramFormula;
  }
  
  private Object processDate(String paramString, Object paramObject, Map<String, Object> paramMap) {
    Object object = paramMap.get(paramObject);
    return notNull(object) ? object : Utils.objectToString(paramObject);
  }
  
  private boolean validValue(WidgetValue paramWidgetValue, String paramString, Map<String, Object> paramMap) {
    Object object = paramMap.get(paramString.toUpperCase());
    return notNull(object) ? ComparatorUtils.equals(object, paramWidgetValue.getValue()) : ((paramWidgetValue != null && paramWidgetValue.getValue() != null));
  }
  
  protected String openTag() {
    return "Form";
  }
  
  protected void mainContent(XMLPrintWriter paramXMLPrintWriter) {
    writeXML(paramXMLPrintWriter);
  }
  
  public void readStream(InputStream paramInputStream) throws Exception {
    XMLableReader xMLableReader = XMLReadHelper.createXMLableReader(paramInputStream, "UTF-8");
    String str = xMLableReader.getTagName();
    if (!"Form".equals(str) && !"FormBook".equals(str))
      FRContext.getLogger().info("nodeName[" + str + "] does not match as " + "Form" + ", might not read success"); 
    xMLableReader.readXMLObject((XMLReadable)this);
    findTableDataSet();
    xMLableReader.close();
    paramInputStream.close();
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    super.readXML(paramXMLableReader);
    readDesign(paramXMLableReader);
    readExtra(paramXMLableReader);
    if (paramXMLableReader.isChildNode())
      if (paramXMLableReader.getTagName().equals("Layout") || paramXMLableReader.getTagName().equals("Container")) {
        compatibleOldParameter(paramXMLableReader);
      } else if ("ReportFitAttr".equals(paramXMLableReader.getTagName())) {
        readReportFitAttr(paramXMLableReader);
      } else if ("Parameters".equals(paramXMLableReader.getTagName())) {
        this.parameters.addAll(Arrays.asList(BaseXMLUtils.readParameters(paramXMLableReader)));
      } else if ("FormMobileAttr".equals(paramXMLableReader.getTagName())) {
        FormMobileAttr formMobileAttr = new FormMobileAttr();
        paramXMLableReader.readXMLObject((XMLReadable)formMobileAttr);
        this.mobileAttr = formMobileAttr;
      }  
  }
  
  private void readReportFitAttr(XMLableReader paramXMLableReader) {
    ReportFitProcessor reportFitProcessor = (ReportFitProcessor)ExtraClassManager.getInstance().getSingle("ReportFitProcessor");
    if (reportFitProcessor == null)
      return; 
    ReportFitAttrProvider reportFitAttrProvider = reportFitProcessor.newInstanceFitAttr();
    paramXMLableReader.readXMLObject((XMLReadable)reportFitAttrProvider);
    setFitAttr(reportFitAttrProvider);
  }
  
  protected void compatibleOldParameter(XMLableReader paramXMLableReader) {
    this.container = (WLayout)WidgetXmlUtils.readWidget(paramXMLableReader);
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    super.writeXML(paramXMLPrintWriter);
    ReportFitAttrProvider reportFitAttrProvider = getFitAttr();
    if (reportFitAttrProvider != null)
      reportFitAttrProvider.writeXML(paramXMLPrintWriter); 
    if (this.mobileAttr != null) {
      paramXMLPrintWriter.startTAG("FormMobileAttr");
      this.mobileAttr.writeXML(paramXMLPrintWriter);
      paramXMLPrintWriter.end();
    } 
    Parameter[] arrayOfParameter = getTemplateParameters();
    if (arrayOfParameter != null)
      StableXMLUtils.writeParameters(paramXMLPrintWriter, (ParameterProvider[])arrayOfParameter); 
    GeneralXMLTools.writeXMLable(paramXMLPrintWriter, (XMLable)this.container, "Layout");
  }
  
  public Object clone() throws CloneNotSupportedException {
    Form form = (Form)super.clone();
    if (this.container != null)
      form.container = (WLayout)this.container.clone(); 
    return form;
  }
  
  private void resizeFormElems(Map<String, Object> paramMap) {
    try {
      boolean bool1 = Boolean.valueOf(Utils.objectToString(paramMap.get("__FIT__"))).booleanValue();
      if (!bool1)
        return; 
      int i = Integer.parseInt(Utils.objectToString(paramMap.get("_PAPERWIDTH")));
      int j = Integer.parseInt(Utils.objectToString(paramMap.get("_PAPERHEIGHT")));
      boolean bool2 = Boolean.valueOf(Utils.objectToString(paramMap.get("_SHOWPARA"))).booleanValue();
      if (getParaContainer() != null) {
        byte b1 = bool2 ? getParameterUIHeight() : 0;
        j -= b1 + 10;
      } 
      if (i <= 0 && j <= 0)
        return; 
      boolean bool3 = false;
      ReportFitProcessor reportFitProcessor = (ReportFitProcessor)ExtraClassManager.getInstance().getSingle("ReportFitProcessor");
      if (reportFitProcessor != null)
        bool3 = reportFitProcessor.parseFontFit(getFitAttr()); 
      boolean bool = bool3 ? true : true;
      int k = this.container.getWidgetCount();
      for (byte b = 0; b < k; b++) {
        Widget widget = this.container.getWidget(b);
        widget.resize(i, j, bool);
      } 
    } catch (Exception exception) {
      return;
    } 
  }
  
  public void executeElementCases(WidgetSessionIDInfor paramWidgetSessionIDInfor, Map<String, Object> paramMap) {
    resizeFormElems(paramMap);
    ElementCaseEditorProvider[] arrayOfElementCaseEditorProvider = getElementCases();
    int i = arrayOfElementCaseEditorProvider.length;
    if (i == 0)
      return; 
    FormElementCaseProvider formElementCaseProvider = null;
    for (byte b = 0; b < i; b++) {
      formElementCaseProvider = arrayOfElementCaseEditorProvider[b].getElementCase();
      formElementCaseProvider.setName(arrayOfElementCaseEditorProvider[b].getWidgetName());
      formElementCaseProvider.setTabledataSource((TableDataSource)this);
    } 
    formElementCaseProvider.executeAll(paramWidgetSessionIDInfor, paramMap);
  }
  
  public ElementCaseEditorProvider[] getElementCases() {
    final ArrayList al = new ArrayList();
    traversalWidget((Widget)this.container, new WidgetGatherAdapter() {
          public boolean dealWithAllCards() {
            return true;
          }
          
          public void dealWith(Widget param1Widget) {
            al.add((ElementCaseEditor)param1Widget);
          }
        },  ElementCaseEditorProvider.class);
    return (ElementCaseEditorProvider[])arrayList.toArray((Object[])new ElementCaseEditorProvider[arrayList.size()]);
  }
  
  public ChartEditorProvider[] getAllCharts() {
    final ArrayList al = new ArrayList();
    traversalWidget((Widget)this.container, new WidgetGatherAdapter() {
          public boolean dealWithAllCards() {
            return true;
          }
          
          public void dealWith(Widget param1Widget) {
            al.add((ChartEditorProvider)param1Widget);
          }
        },  ChartEditorProvider.class);
    return (ChartEditorProvider[])arrayList.toArray((Object[])new ChartEditorProvider[arrayList.size()]);
  }
  
  public static void traversalFormWidget(Widget paramWidget, WidgetGather paramWidgetGather) {
    traversalWidget(paramWidget, paramWidgetGather, (Class)null);
  }
  
  public static void traversalWidget(Widget paramWidget, WidgetGather paramWidgetGather, Class paramClass) {
    if (paramWidget instanceof WAbsoluteLayout.BoundsWidget)
      paramWidget = ((WAbsoluteLayout.BoundsWidget)paramWidget).getWidget(); 
    if (paramClass == null || paramClass.isInstance(paramWidget))
      paramWidgetGather.dealWith(paramWidget); 
    if (paramWidget instanceof WLayout)
      if (paramWidget instanceof WCardLayout && !paramWidgetGather.dealWithAllCards()) {
        int i = ((WCardLayout)paramWidget).getShowIndex();
        if (((WCardLayout)paramWidget).getWidgetCount() > i)
          traversalWidget(((WCardLayout)paramWidget).getWidget(i), paramWidgetGather, paramClass); 
      } else {
        byte b = 0;
        int i = ((WLayout)paramWidget).getWidgetCount();
        while (b < i) {
          traversalWidget(((WLayout)paramWidget).getWidget(b), paramWidgetGather, paramClass);
          b++;
        } 
      }  
  }
  
  public String[] getAllEditedRoleSet() {
    final HashSet<?> allEditedRoleSet = new HashSet();
    traversalFormWidget((Widget)getContainer(), new WidgetGatherAdapter() {
          public void dealWith(Widget param1Widget) {
            if (!(param1Widget instanceof WLayout) && !(param1Widget instanceof WAbsoluteLayout.BoundsWidget)) {
              WidgetPrivilegeControl widgetPrivilegeControl = param1Widget.getWidgetPrivilegeControl();
              allEditedRoleSet.addAll(Arrays.asList(widgetPrivilegeControl.getAllEditedRoles()));
            } 
          }
        });
    return (new ArrayList(hashSet)).<String>toArray(new String[hashSet.size()]);
  }
  
  public ReportFitAttrProvider getFitAttr() {
    return this.fitAttr;
  }
  
  public void setFitAttr(ReportFitAttrProvider paramReportFitAttrProvider) {
    this.fitAttr = paramReportFitAttrProvider;
  }
  
  public void addParameter(Parameter paramParameter) {
    this.parameters.add(paramParameter);
  }
  
  public void clearParameters() {
    this.parameters.clear();
  }
  
  public void removeParameter(Parameter paramParameter) {
    this.parameters.remove(paramParameter);
  }
  
  private JSONObject createEmptyContainerConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    WLayout wLayout = null;
    try {
      wLayout = (WLayout)this.container.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      FRContext.getLogger().error(cloneNotSupportedException.getMessage());
    } 
    wLayout.removeAll();
    return wLayout.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
  }
  
  public void setDataSourcePool(DataSourcePool paramDataSourcePool) {
    this.dataSourcePool = paramDataSourcePool;
  }
  
  public DataSourcePool getDataSourcePool() {
    if (this.dataSourcePool == null)
      this.dataSourcePool = new DataSourcePool((TableDataSource)this); 
    return this.dataSourcePool;
  }
  
  private void findTableDataSet() {
    ElementCaseEditorProvider[] arrayOfElementCaseEditorProvider = getElementCases();
    byte b = 0;
    int i = arrayOfElementCaseEditorProvider.length;
    while (b < i) {
      FormElementCaseProvider formElementCaseProvider = arrayOfElementCaseEditorProvider[b].getElementCase();
      if (formElementCaseProvider != null)
        getDataSourcePool().addDataSource(formElementCaseProvider.getCellTableDataSet()); 
      b++;
    } 
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\main\Form.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */