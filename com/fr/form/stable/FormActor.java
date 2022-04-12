package com.fr.form.stable;

import com.fr.stable.fun.Level;
import com.fr.stable.fun.Provider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FormActor extends Level, Provider {
  public static final String XML_TAG = "FormActor";
  
  public static final int CURRENT_LEVEL = 1;
  
  String panelType();
  
  void dealWithFormData(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString) throws Exception;
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\stable\FormActor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */