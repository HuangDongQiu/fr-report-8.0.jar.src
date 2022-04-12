package com.fr.form.main;

import com.fr.form.ui.Widget;
import com.fr.form.ui.container.WLayout;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.web.Repository;

public class WebClassForm extends Form {
  private Form form;
  
  private String classPath;
  
  public WebClassForm(Form paramForm, String paramString) {
    this.form = paramForm;
    this.classPath = paramString;
  }
  
  public WLayout getContainer() {
    return this.form.getContainer();
  }
  
  public Widget getWidgetByName(String paramString) {
    return this.form.getWidgetByName(paramString);
  }
  
  public boolean isNameExist(String paramString) {
    return this.form.isNameExist(paramString);
  }
  
  public boolean renameTableData(String paramString1, String paramString2) {
    return this.form.renameTableData(paramString1, paramString2);
  }
  
  public JSONObject createJSONConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    return this.form.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\main\WebClassForm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */