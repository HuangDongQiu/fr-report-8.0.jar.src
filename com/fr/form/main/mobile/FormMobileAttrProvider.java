package com.fr.form.main.mobile;

import com.fr.stable.xml.XMLable;

public interface FormMobileAttrProvider extends XMLable {
  public static final String XML_TAG = "FormMobileAttrProvider";
  
  boolean isRefresh();
  
  void setRefresh(boolean paramBoolean);
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\main\mobile\FormMobileAttrProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
