package com.fr.form.stable;

import com.fr.stable.fun.mark.Immutable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FormExportProcessor extends Immutable {
  public static final String MARK_STRING = "FormExportProcessor";
  
  public static final int CURRENT_LEVEL = 1;
  
  void dealWithExport(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString);
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\stable\FormExportProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */