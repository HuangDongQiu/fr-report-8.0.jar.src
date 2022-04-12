package com.fr.form.main;

import com.fr.base.FRContext;
import com.fr.base.TemplateUtils;
import com.fr.general.ComparatorUtils;
import com.fr.general.FRLogger;
import com.fr.js.FormHyperlinkProvider;
import com.fr.js.Hyperlink;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.stable.web.Repository;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;
import java.util.HashMap;

public class FormHyperlink extends Hyperlink implements FormHyperlinkProvider {
  private static final String CHART_SOURCENAME = "__CHARTSOURCENAME__";
  
  private static final String CHART_SIZE = "__CHARTSIZE__";
  
  protected String relateEditorName;
  
  private int type = 0;
  
  public int getType() {
    return this.type;
  }
  
  public void setType(int paramInt) {
    this.type = paramInt;
  }
  
  public String getRelateEditorName() {
    return this.relateEditorName;
  }
  
  public void setRelateEditorName(String paramString) {
    this.relateEditorName = paramString;
  }
  
  public JSONObject createJSONObject(Repository paramRepository) throws JSONException {
    JSONObject jSONObject = super.createJSONObject(paramRepository);
    jSONObject.put("related", this.relateEditorName);
    return jSONObject;
  }
  
  protected String getHyperlinkType() {
    return "widget";
  }
  
  protected JSONObject createPara(Repository paramRepository) throws JSONException {
    return createJsonPara(paramRepository, false);
  }
  
  protected String actionJS(Repository paramRepository) {
    return (getType() == 0) ? actionJS4Chart(paramRepository) : actionJS4EC(paramRepository);
  }
  
  protected String actionJS4Chart(Repository paramRepository) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("chartID", this.relateEditorName);
    hashMap.put("titleAction", actionJS4Title(paramRepository));
    try {
      hashMap.put("para", createPara(paramRepository));
    } catch (Exception exception) {
      FRLogger.getLogger().error("Error in Relate Chart Para");
    } 
    try {
      return TemplateUtils.renderParameter4Tpl(TemplateUtils.readTemplate2String("com/fr/form/main/chartFormRelateJS.tpl", "UTF-8"), hashMap);
    } catch (Exception exception) {
      FRLogger.getLogger().error("Error in Form HyperlinkJS.");
      return "";
    } 
  }
  
  protected String actionJS4EC(Repository paramRepository) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("_g().name_widgets['");
    stringBuffer.append(this.relateEditorName.toUpperCase());
    stringBuffer.append("'].gotoPage(1, ");
    stringBuffer.append(createJsonPara4EC(paramRepository));
    stringBuffer.append(", 'T');");
    stringBuffer.append(actionJS4Title(paramRepository));
    return stringBuffer.toString();
  }
  
  private StringBuffer actionJS4Title(Repository paramRepository) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("FR.recalculateElementTitle(");
    stringBuffer.append(createJsonPara4EC(paramRepository));
    stringBuffer.append(", '");
    stringBuffer.append(("Title_" + this.relateEditorName.toUpperCase()).toUpperCase());
    stringBuffer.append("', 'T');");
    return stringBuffer;
  }
  
  private JSONObject createJsonPara4EC(Repository paramRepository) {
    JSONObject jSONObject = createJsonPara(paramRepository, true);
    jSONObject.remove("__CHARTSOURCENAME__");
    jSONObject.remove("__CHARTSIZE__");
    return jSONObject;
  }
  
  protected JSONObject createJsonPara(Repository paramRepository, boolean paramBoolean) {
    JSONObject jSONObject = JSONObject.create();
    try {
      putExtendParameters(paramRepository, jSONObject, paramBoolean);
      para2JSON(jSONObject, paramBoolean);
    } catch (Exception exception) {
      FRContext.getLogger().error(exception.getMessage());
    } 
    return jSONObject;
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    super.readXML(paramXMLableReader);
    if (paramXMLableReader.isChildNode()) {
      Object object = null;
      if ("realateName".equals(paramXMLableReader.getTagName())) {
        setRelateEditorName(paramXMLableReader.getAttrAsString("realateValue", ""));
      } else if ("linkType".equals(paramXMLableReader.getTagName())) {
        setType(paramXMLableReader.getAttrAsInt("type", 0));
      } 
    } 
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    paramXMLPrintWriter.startTAG("JavaScript").attr("class", getClass().getName());
    super.writeXML(paramXMLPrintWriter);
    paramXMLPrintWriter.startTAG("realateName").attr("realateValue", this.relateEditorName).end();
    paramXMLPrintWriter.startTAG("linkType").attr("type", getType()).end();
    paramXMLPrintWriter.end();
  }
  
  public boolean equals(Object paramObject) {
    return (paramObject instanceof FormHyperlink && super.equals(paramObject) && ComparatorUtils.equals(((FormHyperlink)paramObject).getRelateEditorName(), this.relateEditorName));
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\main\FormHyperlink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */