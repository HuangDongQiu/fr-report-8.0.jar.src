package com.fr.form.main.parameter;

import com.fr.base.FRContext;
import com.fr.base.Parameter;
import com.fr.base.parameter.ParameterUI;
import com.fr.data.TableDataSource;
import com.fr.form.main.Form;
import com.fr.form.main.WidgetGather;
import com.fr.form.main.WidgetGatherAdapter;
import com.fr.form.ui.CellWidget;
import com.fr.form.ui.DataControl;
import com.fr.form.ui.EditorHolder;
import com.fr.form.ui.FieldEditor;
import com.fr.form.ui.Label;
import com.fr.form.ui.TextEditor;
import com.fr.form.ui.Widget;
import com.fr.form.ui.WidgetValue;
import com.fr.form.ui.WidgetXmlUtils;
import com.fr.form.ui.container.WAbsoluteLayout;
import com.fr.form.ui.container.WBorderLayout;
import com.fr.form.ui.container.WHorizontalBoxLayout;
import com.fr.form.ui.container.WLayout;
import com.fr.form.ui.container.WParameterLayout;
import com.fr.form.ui.container.WVerticalBoxLayout;
import com.fr.general.GeneralUtils;
import com.fr.js.AbstractJavaScript;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.StringUtils;
import com.fr.stable.web.Repository;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLReadable;
import com.fr.stable.xml.XMLableReader;
import com.fr.web.RepositoryHelper;
import com.fr.web.core.SessionIDInfor;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class FormParameterUI extends Form implements ParameterUI {
  private boolean needToConvert;
  
  private CellWidget[] cellWidgets;
  
  private Dimension designSize;
  
  private boolean useDefaultSize = true;
  
  public FormParameterUI() {
    WParameterLayout wParameterLayout = new WParameterLayout();
    wParameterLayout.setWidgetName("para");
    setContainer((WLayout)wParameterLayout);
    setDesignSize(new Dimension(1000, 80));
  }
  
  public FormParameterUI(WLayout paramWLayout) {
    setContainer(paramWLayout);
    setDesignSize(new Dimension(1000, 80));
  }
  
  public FormParameterUI(CellWidget[] paramArrayOfCellWidget) {
    this.needToConvert = true;
    this.cellWidgets = paramArrayOfCellWidget;
  }
  
  public void setCellWidgets(CellWidget[] paramArrayOfCellWidget) {
    this.needToConvert = true;
    this.cellWidgets = paramArrayOfCellWidget;
  }
  
  public boolean isUseDefaultSize() {
    return this.useDefaultSize;
  }
  
  public String[][] getWidgetNameTag() {
    return getContainer().acceptType(new Class[] { WParameterLayout.class }) ? ((WParameterLayout)getContainer()).getWidgetNameTag() : new String[0][];
  }
  
  public void executeMobileParaWidgets(Repository paramRepository, JSONObject paramJSONObject) throws JSONException {
    WLayout wLayout = getContainer();
    wLayout.createPara4Mobile(paramRepository, paramJSONObject, this);
  }
  
  public Widget[] getAllWidgets() {
    final ArrayList widgetList = new ArrayList();
    Form.traversalFormWidget((Widget)getContainer(), (WidgetGather)new WidgetGatherAdapter() {
          public void dealWith(Widget param1Widget) {
            widgetList.add(param1Widget);
          }
        });
    return (Widget[])arrayList.toArray((Object[])new Widget[arrayList.size()]);
  }
  
  private void convertCellWidgets() {
    if (this.cellWidgets == null)
      return; 
    WParameterLayout wParameterLayout = new WParameterLayout();
    wParameterLayout.setWidgetName("para");
    for (byte b = 0; b < this.cellWidgets.length; b++) {
      CellWidget cellWidget = this.cellWidgets[b];
      if (cellWidget.getWidget() instanceof Label) {
        if (cellWidget.getStyle().getBackground() == null) {
          wParameterLayout.addWidget((Widget)new WAbsoluteLayout.BoundsWidget(cellWidget.getWidget(), cellWidget.getRect()));
        } else {
          WBorderLayout wBorderLayout = new WBorderLayout();
          wBorderLayout.setWidgetName("border" + b);
          wBorderLayout.setBackground(cellWidget.getStyle().getBackground());
          if (cellWidget.getCellValue() != null)
            wBorderLayout.addCenter(cellWidget.getWidget()); 
          wParameterLayout.addWidget((Widget)new WAbsoluteLayout.BoundsWidget((Widget)wBorderLayout, cellWidget.getRect()));
        } 
      } else {
        wParameterLayout.addWidget((Widget)new WAbsoluteLayout.BoundsWidget(cellWidget.getWidget(), cellWidget.getRect()));
      } 
    } 
    setContainer((WLayout)wParameterLayout);
  }
  
  public JSONObject executedFormJS(Repository paramRepository, JSONObject paramJSONObject) throws JSONException {
    SessionIDInfor sessionIDInfor = RepositoryHelper.getSessionIDInfor(paramRepository);
    sessionIDInfor.setAttribute("paramsheet", this);
    Calculator calculator = Calculator.createCalculator();
    calculator.setAttribute(TableDataSource.class, sessionIDInfor.getTableDataSource());
    paramJSONObject.put("width", getDesignSize().getWidth());
    paramJSONObject.put("height", getDesignSize().getHeight());
    calculator.setAttribute(AbstractJavaScript.RECALCULATE_TAG, Boolean.TRUE);
    paramJSONObject.put("html", createJSONConfig(paramRepository, calculator, null));
    calculator.removeAttribute(AbstractJavaScript.RECALCULATE_TAG);
    return paramJSONObject;
  }
  
  public static FormParameterUI createDefaultParameterUI() {
    return new FormParameterUI();
  }
  
  private boolean isInitialSize(Dimension paramDimension) {
    return (paramDimension == null || (paramDimension.width == 1000 && paramDimension.height == 80));
  }
  
  public static FormParameterUI createDefaultParameterUI(Parameter[] paramArrayOfParameter, Map paramMap1, Map paramMap2) {
    byte b1 = 3;
    WBorderLayout wBorderLayout = new WBorderLayout();
    wBorderLayout.setWidgetName("para");
    WVerticalBoxLayout wVerticalBoxLayout = new WVerticalBoxLayout();
    wVerticalBoxLayout.setWidgetName("wv");
    HashSet<String> hashSet = new HashSet();
    ArrayList<Parameter> arrayList = new ArrayList();
    int i;
    for (i = 0; i < paramArrayOfParameter.length; i++) {
      Parameter parameter = paramArrayOfParameter[i];
      if (parameter != null) {
        String str = parameter.getName();
        if (str != null && !hashSet.contains(str.toUpperCase())) {
          arrayList.add(paramArrayOfParameter[i]);
          hashSet.add(str.toUpperCase());
        } 
      } 
    } 
    i = arrayList.size();
    WHorizontalBoxLayout[] arrayOfWHorizontalBoxLayout = new WHorizontalBoxLayout[i / b1 + 1];
    byte b2;
    for (b2 = 0; b2 < arrayOfWHorizontalBoxLayout.length; b2++)
      arrayOfWHorizontalBoxLayout[b2].setHgap(5); 
    for (b2 = 0; b2 < i; b2++) {
      TextEditor textEditor;
      Parameter parameter = arrayList.get(b2);
      if (b2 % i == 0)
        parameter.getName(); 
      Label label = new Label();
      label.setWidgetValue(new WidgetValue(parameter.getName()));
      label.setWidgetName("label" + b2);
      arrayOfWHorizontalBoxLayout[b2 / i].addWidget((Widget)new Label());
      FieldEditor fieldEditor = null;
      if (paramMap1 != null)
        fieldEditor = (FieldEditor)paramMap1.get(parameter.getName()); 
      if (fieldEditor == null)
        textEditor = new TextEditor(); 
      textEditor.setWidgetName(parameter.getName());
      arrayOfWHorizontalBoxLayout[b2 / i].addWidget((Widget)textEditor);
    } 
    FormParameterUI formParameterUI = new FormParameterUI((WLayout)wBorderLayout);
    formParameterUI.setDesignSize(new Dimension(800, 200));
    return formParameterUI;
  }
  
  public void setTableDataSource(TableDataSource paramTableDataSource) {}
  
  public void setDefaultSize() {
    setDesignSize(new Dimension(1000, 80));
  }
  
  public void setDesignSize(Dimension paramDimension) {
    this.designSize = paramDimension;
  }
  
  public Dimension getDesignSize() {
    return this.designSize;
  }
  
  public boolean renameTableData(String paramString1, String paramString2) {
    _renameTableData(paramString1, paramString2);
    return true;
  }
  
  public ParameterUI convert() {
    if (this.needToConvert) {
      convertCellWidgets();
      this.cellWidgets = null;
      this.needToConvert = false;
    } 
    return this;
  }
  
  public void checkContainer() {
    if (getContainer() instanceof WAbsoluteLayout)
      return; 
    WLayout wLayout = getContainer();
    while (wLayout.getWidgetCount() == 1) {
      Widget widget = wLayout.getWidget(0);
      if (!(widget instanceof WLayout))
        break; 
      wLayout = (WLayout)widget;
      if (wLayout instanceof WAbsoluteLayout)
        setContainer(wLayout); 
    } 
  }
  
  public void setParameterValueFor_7_0_Version(Parameter[] paramArrayOfParameter) {
    if (!(getContainer() instanceof WAbsoluteLayout) || paramArrayOfParameter.length == 0)
      return; 
    for (byte b = 0; b < paramArrayOfParameter.length; b++) {
      Widget widget = getWidgetByName(paramArrayOfParameter[b].getName());
      if (widget instanceof DataControl && (((DataControl)widget).getWidgetValue() == null || StringUtils.isEmpty(((DataControl)widget).getWidgetValue().toString())))
        if (widget instanceof EditorHolder) {
          ((EditorHolder)widget).setWidgetValue(new WidgetValue(paramArrayOfParameter[b].getValue()));
        } else {
          WidgetValue.convertWidgetValue((DataControl)widget, paramArrayOfParameter[b].getValue());
        }  
    } 
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    super.readXML(paramXMLableReader);
    if (paramXMLableReader.isChildNode() && paramXMLableReader.getTagName().equals("DesignAttr")) {
      this.designSize = new Dimension(paramXMLableReader.getAttrAsInt("width", 1000), paramXMLableReader.getAttrAsInt("height", 80));
      this.useDefaultSize = false;
    } 
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    super.writeXML(paramXMLPrintWriter);
    if (!isInitialSize(this.designSize))
      paramXMLPrintWriter.startTAG("DesignAttr").attr("width", this.designSize.width).attr("height", this.designSize.height).end(); 
  }
  
  public Object clone() throws CloneNotSupportedException {
    FormParameterUI formParameterUI = (FormParameterUI)super.clone();
    formParameterUI.designSize = (getDesignSize() == null) ? new Dimension(1000, 80) : (Dimension)getDesignSize().clone();
    return super.clone();
  }
  
  protected void compatibleOldParameter(XMLableReader paramXMLableReader) {
    Widget widget = null;
    String str;
    if ((str = paramXMLableReader.getAttrAsString("class", null)) != null) {
      if (str.endsWith(".WAbsoluteLayout"))
        str = str.replaceAll(".WAbsoluteLayout", ".WParameterLayout"); 
      try {
        Class<Widget> clazz = GeneralUtils.classForName(str);
        widget = clazz.newInstance();
      } catch (ClassNotFoundException classNotFoundException) {
        setContainer((WLayout)WidgetXmlUtils.readWidget(paramXMLableReader));
        return;
      } catch (Exception exception) {
        FRContext.getLogger().error(exception.getMessage(), exception);
      } 
      if (widget != null)
        paramXMLableReader.readXMLObject((XMLReadable)widget); 
      setContainer((WLayout)widget);
    } 
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\main\parameter\FormParameterUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
