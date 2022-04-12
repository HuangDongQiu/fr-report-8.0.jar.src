package com.fr.form.parameter;

import com.fr.base.TemplateUtils;
import com.fr.form.event.Listener;
import com.fr.form.ui.FreeButton;
import com.fr.general.Inter;
import com.fr.js.JavaScript;
import com.fr.js.JavaScriptImpl;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.ArrayUtils;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.web.Repository;

public class FormSubmitButton extends FreeButton {
  public FormSubmitButton() {
    super(Inter.getLocText("FR-Designer_Query"));
    setHotkeys("enter");
  }
  
  public FormSubmitButton(String paramString) {
    super(paramString);
    setHotkeys("enter");
  }
  
  public String getXType() {
    return this.isCustomStyle ? "freebutton" : "formsubmit";
  }
  
  public boolean supportMobile() {
    return false;
  }
  
  public Listener[] createListeners(Repository paramRepository) {
    return paramRepository.getDevice().isMobile() ? super.createListeners(paramRepository) : (Listener[])ArrayUtils.addAll((Object[])super.createListeners(paramRepository), (Object[])new Listener[] { new Listener("click", (JavaScript)new JavaScriptImpl(getDisableAction())), new Listener("click", (JavaScript)new JavaScriptImpl(TemplateUtils.readTemplate2String("/com/fr/form/parameter/formsubmitbutton.js", "UTF-8"))) });
  }
  
  public JSONObject createJSONConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    JSONObject jSONObject = super.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
    jSONObject.put("key", "formsubmit");
    return jSONObject;
  }
  
  public boolean equals(Object paramObject) {
    return (paramObject instanceof FormSubmitButton && super.equals(paramObject));
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\parameter\FormSubmitButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
