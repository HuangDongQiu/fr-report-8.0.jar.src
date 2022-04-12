package com.fr.form.parameter;

import com.fr.base.TemplateUtils;
import junit.framework.TestCase;

public class FormSubmitButtonTest extends TestCase {
  public void testReadTpl() {
    assertTrue((TemplateUtils.readTemplate2String("/com/fr/form/parameter/formsubmitbutton.js", "UTF-8").length() > 0));
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\parameter\FormSubmitButtonTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */