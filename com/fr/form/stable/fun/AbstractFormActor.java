package com.fr.form.stable.fun;

import com.fr.form.stable.FormActor;
import com.fr.stable.fun.impl.AbstractProvider;

public abstract class AbstractFormActor extends AbstractProvider implements FormActor {
  public int currentAPILevel() {
    return 1;
  }
  
  public String mark4Provider() {
    return getClass().getName();
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\stable\fun\AbstractFormActor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
