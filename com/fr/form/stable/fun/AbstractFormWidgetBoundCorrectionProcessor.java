package com.fr.form.stable.fun;

import com.fr.form.stable.FormWidgetBoundCorrectionProcessor;
import com.fr.form.ui.Widget;
import com.fr.stable.fun.mark.API;

@API(level = 1)
public abstract class AbstractFormWidgetBoundCorrectionProcessor implements FormWidgetBoundCorrectionProcessor {
  public int layerIndex() {
    return -1;
  }
  
  public void processWidgetLocation(Widget paramWidget) {}
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\stable\fun\AbstractFormWidgetBoundCorrectionProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */