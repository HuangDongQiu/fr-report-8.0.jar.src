package com.fr.form.stable;

import com.fr.stable.StringUtils;
import com.fr.web.utils.WebUtils;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class FormActorFactory {
  private static Map<String, FormActor> actorMap = new HashMap<String, FormActor>();
  
  public static void registerActor(String paramString, FormActor paramFormActor) {
    actorMap.put(paramString, paramFormActor);
  }
  
  public static FormActor getActor(String paramString) {
    return (FormActor)((StringUtils.isEmpty(paramString) || actorMap.get(paramString) == null) ? DefaultFormActor.getInstance() : actorMap.get(paramString));
  }
  
  public static FormActor getActor(HttpServletRequest paramHttpServletRequest) {
    return getActor(WebUtils.getHTTPRequestParameter(paramHttpServletRequest, "op"));
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\stable\FormActorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */