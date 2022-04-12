package com.fr.form.share;

import com.fr.base.Env;
import com.fr.base.FRContext;
import com.fr.file.CacheManager;
import com.fr.form.ui.ElCaseBindInfo;
import com.fr.form.ui.ElementCaseEditor;
import com.fr.form.ui.SharableElementCaseEditor;
import com.fr.general.ComparatorUtils;
import com.fr.general.FRLogger;
import com.fr.general.GeneralContext;
import com.fr.general.IOUtils;
import com.fr.general.Inter;
import com.fr.stable.ArrayUtils;
import com.fr.stable.EnvChangedListener;
import com.fr.stable.StableUtils;
import com.fr.stable.StringUtils;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLReadable;
import com.fr.stable.xml.XMLTools;
import com.fr.stable.xml.XMLWriter;
import com.fr.stable.xml.XMLableReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShareLoader implements XMLWriter, XMLReadable {
  private static ShareLoader loader = null;
  
  private static Set<String> moduleCategory;
  
  private Map<String, ElCaseBindInfo> bindInfoMap = new HashMap<String, ElCaseBindInfo>();
  
  private Map<String, SharableElementCaseEditor> elementCaseMap = new HashMap<String, SharableElementCaseEditor>();
  
  private List<String> removedModuleList = new ArrayList<String>();
  
  public static synchronized ShareLoader getLoader() {
    init();
    return loader;
  }
  
  public static synchronized void init() {
    if (loader == null) {
      loader = new ShareLoader();
      try {
        loader.readShareElements();
      } catch (Exception exception) {
        FRLogger.getLogger().error(exception.getMessage(), exception);
      } 
    } 
  }
  
  public static synchronized void foreInit() {
    loader = null;
    init();
  }
  
  private void readShareElements() throws Exception {
    Env env = FRContext.getCurrentEnv();
    if (env == null)
      return; 
    readFromEnv();
  }
  
  public ElCaseBindInfo getElCaseBindInfoById(String paramString) {
    ElCaseBindInfo elCaseBindInfo = this.bindInfoMap.get(paramString);
    try {
      return (elCaseBindInfo == null) ? null : elCaseBindInfo.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      FRLogger.getLogger().error(cloneNotSupportedException.getMessage(), cloneNotSupportedException);
      return null;
    } 
  }
  
  public SharableElementCaseEditor getSharedElCaseEditorById(String paramString) {
    SharableElementCaseEditor sharableElementCaseEditor = this.elementCaseMap.get(paramString);
    try {
      return (sharableElementCaseEditor == null) ? null : (SharableElementCaseEditor)sharableElementCaseEditor.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      FRLogger.getLogger().error(cloneNotSupportedException.getMessage(), cloneNotSupportedException);
      return null;
    } 
  }
  
  public ElementCaseEditor getElCaseEditorById(String paramString) {
    ElementCaseEditor elementCaseEditor = this.elementCaseMap.containsKey(paramString) ? ((SharableElementCaseEditor)this.elementCaseMap.get(paramString)).getElementCaseEditor() : new ElementCaseEditor();
    try {
      return (elementCaseEditor == null) ? null : (ElementCaseEditor)elementCaseEditor.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      FRLogger.getLogger().error(cloneNotSupportedException.getMessage(), cloneNotSupportedException);
      return null;
    } 
  }
  
  public boolean installModuleFromDiskZipFile(File paramFile) throws IOException {
    return FRContext.getCurrentEnv().installREUFile(paramFile);
  }
  
  public boolean removeModulesFromList() {
    boolean bool = false;
    for (byte b = 0; b < this.removedModuleList.size(); b++)
      bool = FRContext.getCurrentEnv().removeREUFilesByName(this.removedModuleList.get(b)); 
    this.removedModuleList.clear();
    return bool;
  }
  
  public void addModuleToList(String paramString) {
    this.removedModuleList.add(paramString);
  }
  
  public void removeModuleForList(String paramString) {
    this.removedModuleList.remove(paramString);
  }
  
  public List<String> getRemovedModuleList() {
    return this.removedModuleList;
  }
  
  public void resetRemovedModuleList() {
    this.removedModuleList.clear();
  }
  
  public void refreshModule() throws Exception {
    readFromEnv();
  }
  
  private void readFromEnv() throws Exception {
    File[] arrayOfFile;
    checkReadMe();
    this.bindInfoMap.clear();
    this.elementCaseMap.clear();
    Env env = FRContext.getCurrentEnv();
    File file1 = new File(CacheManager.getProviderInstance().getCacheDirectory(), "fr_share");
    StableUtils.deleteFile(file1);
    StableUtils.mkdirs(file1);
    try {
      arrayOfFile = env.loadREUFile();
    } catch (Exception exception) {
      FRLogger.getLogger().info("reu files loaded failed!");
      return;
    } 
    if (arrayOfFile == null || ArrayUtils.isEmpty((Object[])arrayOfFile)) {
      FRLogger.getLogger().info("no reu file exists!");
      return;
    } 
    String str1 = file1.getAbsolutePath();
    String str2 = StableUtils.pathJoin(new String[] { str1, "tmp_share" });
    File file2 = new File(str2);
    StableUtils.deleteFile(file2);
    StableUtils.mkdirs(file2);
    readReuFiles(arrayOfFile, str2);
    StableUtils.deleteFile(file2);
  }
  
  private void readReuFiles(File[] paramArrayOfFile, String paramString) throws Exception {
    moduleCategory = new HashSet<String>();
    for (File file : paramArrayOfFile) {
      if (file.getName().endsWith(".reu")) {
        IOUtils.unzip(file, paramString);
        String str1 = StableUtils.pathJoin(new String[] { paramString, "help.xml" });
        String str2 = StableUtils.pathJoin(new String[] { paramString, "module.xml" });
        ElCaseBindInfo elCaseBindInfo = new ElCaseBindInfo();
        SharableElementCaseEditor sharableElementCaseEditor = new SharableElementCaseEditor();
        File file1 = new File(str1);
        File file2 = new File(str2);
        XMLTools.readFileXML((XMLReadable)elCaseBindInfo, file1);
        XMLTools.readFileXML((XMLReadable)sharableElementCaseEditor, file2);
        file1.delete();
        file2.delete();
        String str3 = elCaseBindInfo.getId();
        String str4 = elCaseBindInfo.getClassifycn();
        if (str4 != null)
          moduleCategory.add(str4); 
        ElementCaseEditor elementCaseEditor = sharableElementCaseEditor.getElementCaseEditor();
        if (elementCaseEditor != null)
          elementCaseEditor.setDescription(elCaseBindInfo.getGuideInfo()); 
        if (str3 != null) {
          this.bindInfoMap.put(str3, elCaseBindInfo);
          this.elementCaseMap.put(str3, sharableElementCaseEditor);
        } 
      } 
    } 
  }
  
  private void checkReadMe() {
    String str1 = FRContext.getCurrentEnv().getSharePath();
    if (StringUtils.isEmpty(str1))
      return; 
    String str2 = StableUtils.pathJoin(new String[] { str1, "readme.txt" });
    File file = new File(str2);
    if (file != null && file.exists())
      return; 
    BufferedWriter bufferedWriter = null;
    try {
      StableUtils.makesureFileExist(file);
      String str = Inter.getLocText("FR-Plugin_Share_Read_Me_Tip");
      bufferedWriter = new BufferedWriter(new FileWriter(file));
      bufferedWriter.write(str, 0, str.length() - 1);
    } catch (IOException iOException) {
      FRLogger.getLogger().info("readme.txt create failed");
    } finally {
      try {
        if (bufferedWriter != null)
          bufferedWriter.close(); 
      } catch (IOException iOException) {
        FRLogger.getLogger().info("readme.txt create failed");
      } 
    } 
  }
  
  public Map<String, ElCaseBindInfo> getAllBindInfos() {
    return this.bindInfoMap;
  }
  
  public ElCaseBindInfo[] getAllBindInfoList() {
    if (this.bindInfoMap.isEmpty())
      return null; 
    ArrayList<ElCaseBindInfo> arrayList = new ArrayList();
    try {
      for (ElCaseBindInfo elCaseBindInfo : this.bindInfoMap.values())
        arrayList.add(elCaseBindInfo.clone()); 
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      FRLogger.getLogger().error(cloneNotSupportedException.getMessage(), cloneNotSupportedException);
    } 
    return arrayList.<ElCaseBindInfo>toArray(new ElCaseBindInfo[arrayList.size()]);
  }
  
  public ElCaseBindInfo[] getFilterBindInfoList(String paramString) {
    if (this.bindInfoMap.isEmpty())
      return null; 
    ArrayList<ElCaseBindInfo> arrayList = new ArrayList();
    try {
      for (ElCaseBindInfo elCaseBindInfo : this.bindInfoMap.values()) {
        String str = elCaseBindInfo.getClassifycn();
        if (ComparatorUtils.equals(str, paramString))
          arrayList.add(elCaseBindInfo.clone()); 
      } 
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      FRLogger.getLogger().error(cloneNotSupportedException.getMessage(), cloneNotSupportedException);
    } 
    return arrayList.<ElCaseBindInfo>toArray(new ElCaseBindInfo[arrayList.size()]);
  }
  
  public Map<String, SharableElementCaseEditor> getAllElementCase() {
    return this.elementCaseMap;
  }
  
  public String[] getModuleCategory() {
    return (moduleCategory == null) ? new String[0] : moduleCategory.<String>toArray(new String[moduleCategory.size()]);
  }
  
  public void readXML(XMLableReader paramXMLableReader) {}
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {}
  
  static {
    GeneralContext.addEnvChangedListenerToLast(new EnvChangedListener() {
          public void envChanged() {
            ShareLoader.foreInit();
          }
        });
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\share\ShareLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
