package com.fr.form.stable.fun;

import com.fr.form.stable.FormExportProcessor;
import com.fr.stable.fun.mark.API;

@API(level = 1)
public abstract class AbstractFormExportProcessor implements FormExportProcessor {
  public int currentAPILevel() {
    return 1;
  }
  
  public int layerIndex() {
    return -1;
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\stable\fun\AbstractFormExportProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */