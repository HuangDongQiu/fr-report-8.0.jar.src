package com.fr.form.ui.container.cardlayout;

import com.fr.base.background.ImageBackground;
import com.fr.general.Background;
import com.fr.general.IOUtils;

public class CardNextTabButton extends AbstractSwitchTabButton {
  public CardNextTabButton() {}
  
  public CardNextTabButton(String paramString) {
    this.cardName = paramString;
    init();
  }
  
  protected void initBackground() {
    this.initialBackground = (Background)new ImageBackground(IOUtils.readImageWithCache("com/fr/web/images/form/cardlayout/nextTab.png"));
  }
  
  protected String getClickFuncName() {
    return "showNextCard";
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\cardlayout\CardNextTabButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */