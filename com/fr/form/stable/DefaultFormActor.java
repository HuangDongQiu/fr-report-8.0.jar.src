package com.fr.form.stable;

import com.fr.base.ChartPreStyleServerManager;
import com.fr.base.ConfigManager;
import com.fr.base.FRContext;
import com.fr.data.TableDataSource;
import com.fr.form.main.ExtraFormClassManager;
import com.fr.form.main.Form;
import com.fr.form.stable.fun.AbstractFormActor;
import com.fr.general.DeclareRecordType;
import com.fr.general.ExecuteInfo;
import com.fr.general.FRLogManager;
import com.fr.general.FRLogger;
import com.fr.general.LogDuration;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.StringUtils;
import com.fr.stable.fun.JavaScriptPlaceHolder;
import com.fr.stable.fun.StylePlaceHolder;
import com.fr.stable.web.Repository;
import com.fr.web.Browser;
import com.fr.web.RepositoryDeal;
import com.fr.web.core.FormSessionIDInfor;
import com.fr.web.core.SessionDealWith;
import com.fr.web.core.SessionIDInfor;
import com.fr.web.utils.WebUtils;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultFormActor extends AbstractFormActor {
  private String CONFIG = "config";
  
  private String PARA_CONFIG = "paraConfig";
  
  private String DOCTYPE = "DOCTYPE";
  
  private long TEN = 10L;
  
  private String[] EXCLUDE_PARA = new String[] { this.CONFIG, this.PARA_CONFIG, this.DOCTYPE };
  
  private static DefaultFormActor defaultFormActor;
  
  public static DefaultFormActor getInstance() {
    if (defaultFormActor == null)
      defaultFormActor = new DefaultFormActor(); 
    return defaultFormActor;
  }
  
  public String panelType() {
    return "form";
  }
  
  public void dealWithFormData(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString) throws Exception {
    FormSessionIDInfor formSessionIDInfor = (FormSessionIDInfor)SessionDealWith.getSessionIDInfor(paramString);
    if (formSessionIDInfor == null)
      return; 
    RepositoryDeal repositoryDeal = new RepositoryDeal(paramHttpServletRequest, (SessionIDInfor)formSessionIDInfor, 96);
    long l1 = System.currentTimeMillis();
    if (repositoryDeal.getDevice().isMobile()) {
      writeJSON(paramHttpServletResponse, formSessionIDInfor, (Repository)repositoryDeal);
    } else {
      boolean bool = !Browser.resolve(paramHttpServletRequest).shouldCheckHTMLType() ? true : false;
      writeHtml(paramHttpServletResponse, formSessionIDInfor, (Repository)repositoryDeal, paramString, bool);
    } 
    long l2 = System.currentTimeMillis();
    if (ConfigManager.getProviderInstance().getLogConfig().isRecordExe4form())
      formExecuteLogRecord(formSessionIDInfor, l1, l2, new HashMap<Object, Object>(), FRLogManager.getDeclareSQLContent()); 
  }
  
  public int currentAPILevel() {
    return super.currentAPILevel();
  }
  
  private JSONObject getJSON(HttpServletResponse paramHttpServletResponse, FormSessionIDInfor paramFormSessionIDInfor, Repository paramRepository) throws Exception {
    Form form = paramFormSessionIDInfor.getForm2Show();
    Calculator calculator = mixCalculate2Form(form);
    form.initWidgetData(paramRepository, calculator);
    return form.createPara4Mobile(paramRepository, (SessionIDInfor)paramFormSessionIDInfor);
  }
  
  private void writeJSON(HttpServletResponse paramHttpServletResponse, FormSessionIDInfor paramFormSessionIDInfor, Repository paramRepository) throws Exception {
    PrintWriter printWriter = WebUtils.createPrintWriter(paramHttpServletResponse);
    printWriter.print(getJSON(paramHttpServletResponse, paramFormSessionIDInfor, paramRepository));
    printWriter.flush();
    printWriter.close();
  }
  
  private void writeHtml(HttpServletResponse paramHttpServletResponse, FormSessionIDInfor paramFormSessionIDInfor, Repository paramRepository, String paramString, boolean paramBoolean) throws Exception {
    noCache(paramHttpServletResponse);
    Map<String, Object> map = initMap4Form(paramFormSessionIDInfor, paramRepository, paramString, paramBoolean);
    WebUtils.writeOutTemplate("/com/fr/web/core/form.html", paramHttpServletResponse, map);
  }
  
  private Map<String, Object> initMap4Form(FormSessionIDInfor paramFormSessionIDInfor, Repository paramRepository, String paramString, boolean paramBoolean) {
    Form form = paramFormSessionIDInfor.getForm2Show();
    Calculator calculator = mixCalculate2Form(form);
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    try {
      hashMap.put("charset", ConfigManager.getProviderInstance().getServerCharset());
      hashMap.put("formlet_title", paramFormSessionIDInfor.getWebTitle());
      JSONObject jSONObject = form.createParaJSONConfig(paramRepository, calculator);
      hashMap.put("paraConfig", jSONObject.optBoolean("paraDisplay") ? jSONObject : JSONObject.EMPTY);
      hashMap.put("config", form.createContentJSONConfig(paramRepository, calculator, false));
      hashMap.put("Baidu", ChartPreStyleServerManager.getProviderInstance().getBaiduSource());
      hashMap.put("Google", ChartPreStyleServerManager.getProviderInstance().getGoogleSource());
      hashMap.put("csslink", WebUtils.getCSSLinks(calculator));
      hashMap.put("jslink", WebUtils.getJSLinks(calculator));
      if (paramBoolean)
        hashMap.put("DOCTYPE", "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"); 
      String str1 = createScriptPlaceHolderString();
      if (StringUtils.isNotEmpty(str1))
        hashMap.put("FormScript", str1); 
      String str2 = createStylePlaceHolderString();
      if (StringUtils.isNotEmpty(str2))
        hashMap.put("FormStyle", str2); 
    } catch (Exception exception) {
      FRContext.getLogger().error(exception.getMessage());
    } 
    return (Map)hashMap;
  }
  
  private String createScriptPlaceHolderString() {
    StringBuilder stringBuilder = new StringBuilder();
    Set set = ExtraFormClassManager.getInstance().getArray("JavaScriptPlaceHolder");
    for (JavaScriptPlaceHolder javaScriptPlaceHolder : set)
      stringBuilder.append(javaScriptPlaceHolder.placeHolderContent()); 
    return stringBuilder.toString();
  }
  
  private String createStylePlaceHolderString() {
    StringBuilder stringBuilder = new StringBuilder();
    Set set = ExtraFormClassManager.getInstance().getArray("StylePlaceHolder");
    for (StylePlaceHolder stylePlaceHolder : set)
      stringBuilder.append(stylePlaceHolder.placeHolderContent()); 
    return stringBuilder.toString();
  }
  
  private Map<String, Object> getPara4EC(FormSessionIDInfor paramFormSessionIDInfor, Form paramForm, Repository paramRepository) {
    Map map = paramFormSessionIDInfor.getParameterMap4Execute();
    Map<String, Object> map1 = paramForm.getWidgetDefaultValueMap(map, paramRepository);
    map1.putAll(map);
    return map1;
  }
  
  private void noCache(HttpServletResponse paramHttpServletResponse) {
    paramHttpServletResponse.setHeader("Pragma", "No-cache");
    paramHttpServletResponse.setHeader("Cache-Control", "no-cache, no-store");
    paramHttpServletResponse.setDateHeader("Expires", -this.TEN);
  }
  
  private Calculator mixCalculate2Form(Form paramForm) {
    Calculator calculator = Calculator.createCalculator();
    calculator.setAttribute(TableDataSource.class, paramForm);
    return calculator;
  }
  
  private void formExecuteLogRecord(FormSessionIDInfor paramFormSessionIDInfor, long paramLong1, long paramLong2, Map paramMap, String paramString) {
    FRLogManager.setSession((LogDuration)paramFormSessionIDInfor);
    try {
      String str = FRLogger.createParamString(paramMap, this.EXCLUDE_PARA);
      long l = paramLong2 - paramLong1;
      ExecuteInfo executeInfo = new ExecuteInfo(str, l, paramString);
      FRContext.getLogger().getRecordManager().recordExecuteInfo(paramFormSessionIDInfor.getBookPath(), DeclareRecordType.EXECUTE_TYPE_FORM, executeInfo);
    } catch (Throwable throwable) {
      FRContext.getLogger().log(Level.WARNING, throwable.getMessage(), throwable);
      FRContext.getLogger().log(Level.WARNING, "RecordManager error. Record is close.");
    } 
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\stable\DefaultFormActor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */