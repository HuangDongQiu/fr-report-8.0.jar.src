package com.fr.form;

import com.fr.stable.fun.FunctionHelper;
import com.fr.stable.fun.FunctionProcessor;
import com.fr.stable.fun.impl.AbstractFunctionProcessor;

public class FormFunctionProcessor {
  private static final String ID_FORM = "com.fr.form";
  
  public static final FunctionProcessor FORM = (FunctionProcessor)new AbstractFunctionProcessor() {
      public int getId() {
        return FunctionHelper.generateFunctionID("com.fr.form");
      }
      
      public String getLocaleKey() {
        return "FR-Engine_FormPreview";
      }
    };
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\FormFunctionProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */