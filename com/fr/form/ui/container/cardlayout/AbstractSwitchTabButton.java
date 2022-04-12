package com.fr.form.ui.container.cardlayout;

import com.fr.form.event.Listener;
import com.fr.form.ui.FreeButton;
import com.fr.js.JavaScript;
import com.fr.js.JavaScriptImpl;
import com.fr.stable.StringUtils;

public abstract class AbstractSwitchTabButton extends FreeButton {
  protected String cardName;
  
  public void init() {
    this.isCustomStyle = true;
    initBackground();
    Listener listener = new Listener("click");
    JavaScriptImpl javaScriptImpl = new JavaScriptImpl(getClickJs());
    listener.setAction((JavaScript)javaScriptImpl);
    setVisible(false);
    addListener(listener);
  }
  
  protected abstract void initBackground();
  
  private String getClickJs() {
    return StringUtils.isEmpty(this.cardName) ? "" : ("this.options.form.getWidgetByName('" + this.cardName + "')." + getClickFuncName() + "()");
  }
  
  protected abstract String getClickFuncName();
  
  public String getCardName() {
    return this.cardName;
  }
  
  public void setCardName(String paramString) {
    this.cardName = paramString;
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\cardlayout\AbstractSwitchTabButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
