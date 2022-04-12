package com.fr.form.ui;

import com.fr.json.JSONObject;
import com.fr.script.Calculator;

public class DataTable extends AbstractDataControl {
  public String getXType() {
    return "datatable";
  }
  
  public String[] supportedEvents() {
    return new String[] { "click" };
  }
  
  public int[] getValueType() {
    return new int[] { 6, 3 };
  }
  
  public void createValueResult(DataControl paramDataControl, Calculator paramCalculator, JSONObject paramJSONObject1, JSONObject paramJSONObject2) {
    WidgetValue.createWidgetValueResult((DataControl)this, paramCalculator, paramJSONObject1, paramJSONObject2);
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\DataTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */