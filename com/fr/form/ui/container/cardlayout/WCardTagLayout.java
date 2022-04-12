package com.fr.form.ui.container.cardlayout;

import com.fr.base.GraphHelper;
import com.fr.form.ui.CardSwitchButton;
import com.fr.form.ui.FreeButton;
import com.fr.form.ui.Label;
import com.fr.form.ui.Widget;
import com.fr.form.ui.container.WHorizontalBoxLayout;
import com.fr.general.FRFont;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.core.UUID;
import com.fr.stable.web.Repository;
import java.awt.FontMetrics;

public class WCardTagLayout extends WHorizontalBoxLayout {
  private static final int DESIGNER_DEFAULT_GAP = 1;
  
  private static final int WEB_DEFAULT_GAP = 3;
  
  private static final int FIRST = 0;
  
  private static final int FIRST_OPACITY = 1;
  
  private static final int DEFAULT_PADING = 15;
  
  private transient FRFont titleFont;
  
  private boolean WEB_WIDGET_INITIALIZED = false;
  
  public WCardTagLayout() {
    setHgap(1);
    setAlignment(0);
    clearMargin();
    setWidgetName(UUID.randomUUID().toString());
  }
  
  public FRFont getTitleFont() {
    return this.titleFont;
  }
  
  public void setTitleFont(FRFont paramFRFont) {
    this.titleFont = paramFRFont;
  }
  
  public String getXType() {
    return "cardtaglayout";
  }
  
  public void adjustPreferWidth() {
    FontMetrics fontMetrics = getDefaultFontMetrics();
    byte b = 0;
    int i = this.widgetList.size();
    while (b < i) {
      FreeButton freeButton = this.widgetList.get(b);
      String str = freeButton.getText();
      freeButton.setFont(this.titleFont);
      setWidthAtIndex(b, fontMetrics.stringWidth(str) + 15);
      b++;
    } 
  }
  
  private FontMetrics getDefaultFontMetrics() {
    return GraphHelper.getFontMetrics(this.titleFont.applyResolutionNP(96));
  }
  
  public CardSwitchButton getSwitchButton(int paramInt) {
    if (paramInt == -1 || paramInt >= this.widgetList.size())
      throw new RuntimeException("Error Switch Button Index !"); 
    return this.widgetList.get(paramInt);
  }
  
  private void prepareWebStyle() {
    if (this.WEB_WIDGET_INITIALIZED)
      return; 
    CardSwitchButton cardSwitchButton = getSwitchButton(0);
    cardSwitchButton.setOpacity(1.0D);
    setHgap(3);
    addWidget((Widget)new Label(), 0);
    setWidthAtIndex(0, 0);
    this.WEB_WIDGET_INITIALIZED = true;
  }
  
  public JSONObject createJSONConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    prepareWebStyle();
    return super.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\cardlayout\WCardTagLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */