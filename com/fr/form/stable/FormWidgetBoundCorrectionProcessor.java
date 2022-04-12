package com.fr.form.stable;

import com.fr.form.ui.Widget;
import com.fr.stable.fun.mark.Immutable;

public interface FormWidgetBoundCorrectionProcessor extends Immutable {
  public static final String MARK_STRING = "FormWidgetBoundCorrectionProcessor";
  
  public static final int CURRENT_LEVEL = 1;
  
  void processWidgetLocation(Widget paramWidget);
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\stable\FormWidgetBoundCorrectionProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */