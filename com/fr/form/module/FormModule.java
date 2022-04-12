package com.fr.form.module;

import com.fr.form.DefaultFormOperator;
import com.fr.form.main.ExtraFormClassManager;
import com.fr.form.main.Form;
import com.fr.form.main.FormHyperlink;
import com.fr.form.main.parameter.FormParameterUI;
import com.fr.form.parameter.FormSubmitButton;
import com.fr.form.plugin.DefaultSwitcherImpl;
import com.fr.general.Inter;
import com.fr.general.ModuleContext;
import com.fr.module.BaseModule;
import com.fr.plugin.PluginLoader;
import com.fr.stable.ArrayUtils;
import com.fr.stable.bridge.StableFactory;
import com.fr.stable.fun.CssFileHandler;
import com.fr.stable.fun.JavaScriptFileHandler;
import com.fr.stable.fun.Service;
import com.fr.stable.web.WebletCreator;
import com.fr.web.core.A.EA;
import com.fr.web.core.A.JB;
import com.fr.web.weblet.EmbeddedTplFormlet;
import com.fr.web.weblet.FSFormlet;
import com.fr.web.weblet.FormletCreator;
import com.fr.web.weblet.ProcessFormlet;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class FormModule extends BaseModule {
  public void start() {
    super.start();
    ModuleContext.startModule("com.fr.chart.module.ChartModule");
    registerWeblet();
    registerMarkedClass();
    startFinish();
  }
  
  public void startFinish() {
    PluginLoader.foreInit();
    StableFactory.registerJavaScriptFiles("finereport.js", getExtraJavaScriptFiles());
    StableFactory.registerStyleFiles("finereport.css", getExtraCssFiles());
  }
  
  public Service[] service4Register() {
    return (Service[])ArrayUtils.addAll((Object[])super.service4Register(), (Object[])new Service[] { (Service)new EA(), (Service)new JB() });
  }
  
  private void registerWeblet() {
    StableFactory.registerWeblet("FSFormlet", FSFormlet.class);
    StableFactory.registerWeblet("EmbeddedTplFormlet", EmbeddedTplFormlet.class);
    StableFactory.registerWeblet("ProcessFormlet", ProcessFormlet.class);
  }
  
  private void registerMarkedClass() {
    StableFactory.registerMarkedClass("SubmitButton", FormSubmitButton.class);
    StableFactory.registerMarkedClass("FormOperator", DefaultFormOperator.class);
    StableFactory.registerMarkedClass("com.fr.form.module.FormModule", Form.class);
    StableFactory.registerMarkedClass("FormParameterUI", FormParameterUI.class);
    StableFactory.registerMarkedClass("FormHyperlink", FormHyperlink.class);
    StableFactory.registerMarkedObject("WidgetSwitcher", DefaultSwitcherImpl.class);
    StableFactory.registerMarkedClass("ExtraFormClassManagerProvider", ExtraFormClassManager.class);
  }
  
  public WebletCreator[] webletCreator4Register() {
    return (WebletCreator[])ArrayUtils.addAll((Object[])super.webletCreator4Register(), (Object[])new WebletCreator[] { (WebletCreator)FormletCreator.getInstance() });
  }
  
  public String[] getFiles4WebUnitTest() {
    return (String[])ArrayUtils.addAll((Object[])super.getFiles4WebUnitTest(), (Object[])new String[0]);
  }
  
  public String[] getFiles4WebClient() {
    String[] arrayOfString = getExtraJavaScriptFiles();
    arrayOfString = (String[])ArrayUtils.addAll((Object[])new String[] { 
          "/com/fr/web/core/js/hScrollPane.js", "/com/fr/web/core/js/form.js", "/com/fr/web/core/js/jLayout.extend.js", "/com/fr/web/core/js/widget.layout.extend.js", "/com/fr/web/core/js/widget.menu.js", "/com/fr/web/core/js/widget.data.js", "/com/fr/web/core/js/widget.datepicker.js", "/com/fr/web/core/js/widget.editor.core.js", "/com/fr/web/core/js/widget.button.js", "/com/fr/web/core/js/widget.search.js", 
          "/com/fr/web/core/js/widget.datatable.js", "/com/fr/web/core/js/widget.synceditor.js", "/com/fr/web/core/js/widget.toolbar.js", "/com/fr/web/core/js/widget.plaineditor.js", "/com/fr/web/core/js/widget.iframe.js", "/com/fr/web/core/js/widget.label.js", "/com/fr/web/core/js/widget.number.js", "/com/fr/web/core/js/widget.triggereditor.js", "/com/fr/web/core/js/widget.fileeditor.js", "/com/fr/web/core/js/widget.tabpane.js", 
          "/com/fr/web/core/js/widget.elementcase.js", "/com/fr/web/core/js/widget.combo.js", "/com/fr/web/core/js/widget.check.js", "/com/fr/web/core/js/widget.tagcheckboxeditor.js", "/com/fr/web/core/js/widget.treecomboboxeditor.js", "/com/fr/web/core/js/widget.datetime.js", "/com/fr/web/core/js/widget.group.js", "/com/fr/web/core/js/widget.group.radio.js", "/com/fr/web/core/js/idcard.js" }, (Object[])arrayOfString);
    return (String[])ArrayUtils.addAll((Object[])super.getFiles4WebClient(), (Object[])arrayOfString);
  }
  
  private String[] getExtraJavaScriptFiles() {
    ExtraFormClassManager extraFormClassManager = ExtraFormClassManager.getInstance();
    Set set = extraFormClassManager.getArray("JavaScriptFileHandler");
    LinkedHashSet linkedHashSet = new LinkedHashSet();
    for (JavaScriptFileHandler javaScriptFileHandler : set)
      linkedHashSet.addAll(Arrays.asList(javaScriptFileHandler.pathsForFiles())); 
    return (String[])linkedHashSet.toArray((Object[])new String[linkedHashSet.size()]);
  }
  
  public String[] getCssFiles4WebClient() {
    String[] arrayOfString = getExtraCssFiles();
    arrayOfString = (String[])ArrayUtils.addAll((Object[])new String[] { 
          "/com/fr/web/core/css/hScrollPane.css", "/com/fr/web/core/css/widget/basic/widget.datatable.css", "/com/fr/web/core/css/widget/basic/widget.flat.button.css", "/com/fr/web/core/css/widget/basic/widget.button.css", "/com/fr/web/core/css/widget/basic/widget.menu.css", "/com/fr/web/core/css/widget/basic/widget.flat.datepicker.css", "/com/fr/web/core/css/widget/basic/widget.datepicker.css", "/com/fr/web/core/css/widget/basic/widget.search.css", "/com/fr/web/core/css/widget/basic/widget.synceditor.css", "/com/fr/web/core/css/widget/basic/widget.flat.plaineditor.css", 
          "/com/fr/web/core/css/widget/basic/widget.plaineditor.css", "/com/fr/web/core/css/widget/basic/widget.fileeditor.css", "/com/fr/web/core/css/widget/basic/widget.tabpane.css", "/com/fr/web/core/css/widget/basic/widget.checkboxeditor.css", "/com/fr/web/core/css/widget/basic/widget.tagcheckboxeditor.css", "/com/fr/web/core/css/widget/basic/widget.flat.comboboxeditor.css", "/com/fr/web/core/css/widget/basic/widget.comboboxeditor.css", "/com/fr/web/core/css/widget/basic/widget.flat.treeeditor.css", "/com/fr/web/core/css/widget/basic/widget.treeeditor.css" }, (Object[])arrayOfString);
    return (String[])ArrayUtils.addAll((Object[])super.getCssFiles4WebClient(), (Object[])arrayOfString);
  }
  
  private String[] getExtraCssFiles() {
    ExtraFormClassManager extraFormClassManager = ExtraFormClassManager.getInstance();
    Set set = extraFormClassManager.getArray("CssFileHandler");
    LinkedHashSet linkedHashSet = new LinkedHashSet();
    for (CssFileHandler cssFileHandler : set)
      linkedHashSet.addAll(Arrays.asList(cssFileHandler.pathsForFiles())); 
    return (String[])linkedHashSet.toArray((Object[])new String[linkedHashSet.size()]);
  }
  
  public String getInterNationalName() {
    return Inter.getLocText("FR-Designer_Form_Module");
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\module\FormModule.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
