package com.fr.form.main;

import com.fr.base.FRContext;
import com.fr.form.event.Listener;
import com.fr.form.ui.DataControl;
import com.fr.form.ui.Interactive;
import com.fr.form.ui.NameWidget;
import com.fr.form.ui.Widget;
import com.fr.js.JavaScript;
import com.fr.json.JSONArray;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.script.CalculatorMap;
import com.fr.stable.ArrayUtils;
import com.fr.stable.DeathCycleException;
import com.fr.stable.FormulaProvider;
import com.fr.stable.ParameterProvider;
import com.fr.stable.Primitive;
import com.fr.stable.StringUtils;
import com.fr.stable.UtilEvalError;
import com.fr.stable.core.UUID;
import com.fr.stable.script.AbstractNameSpace;
import com.fr.stable.script.CalculatorProvider;
import com.fr.stable.script.NameSpace;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class FormConfig {
  private static FormConfig formConfig = new FormConfig();
  
  private static Object DeathLoopGetWidgetValue = new Object();
  
  public static FormConfig getInstance() {
    return formConfig;
  }
  
  public JSONObject dealWithWidgetData(HttpServletRequest paramHttpServletRequest, Form paramForm, JSONArray paramJSONArray, Calculator paramCalculator) throws JSONException {
    CalculatorMap calculatorMap = CalculatorMap.createEmptyMap();
    ArrayList<Interactive> arrayList = new ArrayList();
    ArrayList<Widget> arrayList1 = new ArrayList();
    for (byte b = 0; b < paramJSONArray.length(); b++) {
      String str = (String)paramJSONArray.get(b);
      Widget widget = paramForm.getWidgetByName(str);
      if (widget != null)
        traversalFormWidget(widget, (Map<String, Widget>)calculatorMap, arrayList, arrayList1); 
    } 
    return dealWithWidgetData(paramHttpServletRequest, (Map<String, Widget>)calculatorMap, arrayList, paramCalculator, arrayList1);
  }
  
  public JSONObject dealWithWidgetData(HttpServletRequest paramHttpServletRequest, Widget paramWidget, Calculator paramCalculator) throws JSONException {
    CalculatorMap calculatorMap = CalculatorMap.createEmptyMap();
    ArrayList<Interactive> arrayList = new ArrayList();
    ArrayList<Widget> arrayList1 = new ArrayList();
    traversalFormWidget(paramWidget, (Map<String, Widget>)calculatorMap, arrayList, arrayList1);
    return dealWithWidgetData(paramHttpServletRequest, (Map<String, Widget>)calculatorMap, arrayList, paramCalculator, arrayList1);
  }
  
  private void traversalFormWidget(Widget paramWidget, final Map<String, Widget> dataControlMap, final List<Interactive> interactiveList, final List<Widget> eventWidgetList) {
    Form.traversalFormWidget(paramWidget, new WidgetGatherAdapter() {
          public boolean dealWithAllCards() {
            return true;
          }
          
          public void dealWith(Widget param1Widget) {
            if (param1Widget instanceof DataControl) {
              if (StringUtils.isEmpty(param1Widget.getWidgetName()))
                param1Widget.setWidgetName(UUID.randomUUID().toString().substring(0, 8)); 
              dataControlMap.put(param1Widget.getWidgetName(), param1Widget);
            } else if (param1Widget instanceof Interactive) {
              interactiveList.add((Interactive)param1Widget);
            } else if (param1Widget instanceof NameWidget) {
              Widget widget = ((NameWidget)param1Widget).createWidget();
              if (widget instanceof DataControl)
                dataControlMap.put(widget.getWidgetName(), widget); 
            } else {
              FormConfig.this.lookupWidgetEventDependence(param1Widget, eventWidgetList);
            } 
          }
        });
  }
  
  private void lookupWidgetEventDependence(Widget paramWidget, List<Widget> paramList) {
    byte b = 0;
    int i = paramWidget.getListenerSize();
    while (b < i) {
      Listener listener = paramWidget.getListener(b);
      if (listener != null) {
        JavaScript javaScript = listener.getAction();
        if (ArrayUtils.isNotEmpty((Object[])javaScript.getParameters())) {
          paramList.add(paramWidget);
          break;
        } 
      } 
      b++;
    } 
  }
  
  private JSONObject dealWithWidgetData(HttpServletRequest paramHttpServletRequest, Map<String, Widget> paramMap, List<Interactive> paramList, Calculator paramCalculator, List<Widget> paramList1) throws JSONException {
    JSONObject jSONObject1 = JSONObject.create();
    JSONObject jSONObject2 = JSONObject.create();
    WidgetValueNameSpace widgetValueNameSpace = new WidgetValueNameSpace(jSONObject2);
    paramCalculator.pushNameSpace((NameSpace)widgetValueNameSpace);
    paramCalculator.setAttribute(DeathLoopGetWidgetValue, new LinkedList());
    for (Map.Entry<String, Widget> entry : paramMap.entrySet()) {
      Widget widget = (Widget)entry.getValue();
      setWidgetValue(widget, paramMap, paramCalculator, jSONObject1, jSONObject2);
    } 
    for (Widget widget : paramList1)
      calWidgetEventPara(paramCalculator, widget); 
    for (Interactive interactive : paramList)
      interactive.mixinReturnData(paramHttpServletRequest, paramCalculator, jSONObject2); 
    paramCalculator.removeNameSpace((NameSpace)widgetValueNameSpace);
    return jSONObject2;
  }
  
  private void calWidgetEventPara(Calculator paramCalculator, Widget paramWidget) {
    byte b = 0;
    int i = paramWidget.getListenerSize();
    while (b < i) {
      Listener listener = paramWidget.getListener(b);
      if (listener != null) {
        JavaScript javaScript = listener.getAction();
        ParameterProvider[] arrayOfParameterProvider = javaScript.getParameters();
        for (ParameterProvider parameterProvider : arrayOfParameterProvider) {
          Object object = parameterProvider.getValue();
          if (object instanceof FormulaProvider)
            upateFormulaParaValue(paramCalculator, parameterProvider, object); 
        } 
      } 
      b++;
    } 
  }
  
  private void upateFormulaParaValue(Calculator paramCalculator, ParameterProvider paramParameterProvider, Object paramObject) {
    try {
      paramObject = paramCalculator.eval((FormulaProvider)paramObject);
    } catch (UtilEvalError utilEvalError) {}
    if (paramObject != null && paramObject != Primitive.NULL)
      paramParameterProvider.setValue(paramObject); 
  }
  
  private void setWidgetValue(Widget paramWidget, Map paramMap, Calculator paramCalculator, JSONObject paramJSONObject1, JSONObject paramJSONObject2) throws JSONException {
    if (paramJSONObject2.has(paramWidget.getWidgetName().toUpperCase()))
      return; 
    List<Widget> list = (List)paramCalculator.getAttribute(DeathLoopGetWidgetValue);
    if (list.contains(paramWidget)) {
      StringBuffer stringBuffer = new StringBuffer();
      int i = list.size();
      for (byte b1 = 0; b1 < i; b1++) {
        stringBuffer.append(((Widget)list.get(b1)).getWidgetName());
        stringBuffer.append("->");
      } 
      stringBuffer.append(paramWidget.getWidgetName());
      FRContext.getLogger().error("Death cycle exists at calculating widgets:" + stringBuffer, (Throwable)new DeathCycleException(paramWidget.getWidgetName()));
      return;
    } 
    list.add(paramWidget);
    String[] arrayOfString = ((DataControl)paramWidget).dependence((CalculatorProvider)paramCalculator);
    for (byte b = 0; b < arrayOfString.length; b++) {
      if (arrayOfString[b].length() > 1) {
        String str = (arrayOfString[b].charAt(0) == '$') ? arrayOfString[b].substring(1, arrayOfString[b].length()) : arrayOfString[b];
        Widget widget = (Widget)paramMap.get(str);
        if (widget != null)
          setWidgetValue(widget, paramMap, paramCalculator, paramJSONObject1, paramJSONObject2); 
      } 
    } 
    ((DataControl)paramWidget).createValueResult((DataControl)paramWidget, paramCalculator, paramJSONObject2, paramJSONObject1);
    list.remove(paramWidget);
  }
  
  public static class WidgetValueNameSpace extends AbstractNameSpace {
    private JSONObject result;
    
    public WidgetValueNameSpace(JSONObject param1JSONObject) {
      this.result = param1JSONObject;
    }
    
    public Object getVariable(Object param1Object, CalculatorProvider param1CalculatorProvider) {
      if (this.result != null && this.result.length() > 0 && (param1Object instanceof com.fr.stable.script.ColumnRowRange || param1Object instanceof com.fr.parser.Ambiguity || param1Object instanceof String)) {
        String str = param1Object.toString().toUpperCase();
        if (str.length() == 0)
          return null; 
        if (str.charAt(0) == '$')
          str = str.substring(1, str.length()); 
        if (this.result.has(str))
          try {
            Object object = this.result.get(str);
            return (object instanceof JSONObject) ? ((JSONObject)object).get("value") : object;
          } catch (JSONException jSONException) {
            FRContext.getLogger().error(jSONException.getMessage(), (Throwable)jSONException);
          }  
      } 
      return null;
    }
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\main\FormConfig.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */