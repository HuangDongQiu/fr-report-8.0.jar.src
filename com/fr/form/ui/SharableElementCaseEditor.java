package com.fr.form.ui;

import com.fr.base.ParameterMapNameSpace;
import com.fr.base.TableData;
import com.fr.data.TableDataSource;
import com.fr.data.core.DataCoreXmlUtils;
import com.fr.data.impl.EmbeddedTableData;
import com.fr.form.main.Form;
import com.fr.general.FRLogger;
import com.fr.script.Calculator;
import com.fr.stable.script.NameSpace;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLReadable;
import com.fr.stable.xml.XMLableReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SharableElementCaseEditor extends Widget {
  private String id;
  
  private ElementCaseEditor elementCaseEditor;
  
  private TableDataSource tableDataSource = (TableDataSource)new Form();
  
  private Map<String, Object> paraMap;
  
  public SharableElementCaseEditor() {}
  
  public SharableElementCaseEditor(String paramString, ElementCaseEditor paramElementCaseEditor, TableDataSource paramTableDataSource) {
    this(paramString, paramElementCaseEditor, paramTableDataSource, Collections.EMPTY_MAP);
  }
  
  public SharableElementCaseEditor(String paramString, ElementCaseEditor paramElementCaseEditor, TableDataSource paramTableDataSource, Map<String, Object> paramMap) {
    this.id = paramString;
    this.elementCaseEditor = paramElementCaseEditor;
    this.tableDataSource = paramTableDataSource;
    this.paraMap = paramMap;
  }
  
  public String getId() {
    return this.id;
  }
  
  public void setId(String paramString) {
    this.id = paramString;
  }
  
  public ElementCaseEditor getElementCaseEditor() {
    return this.elementCaseEditor;
  }
  
  public void setElementCaseEditor(ElementCaseEditor paramElementCaseEditor) {
    this.elementCaseEditor = paramElementCaseEditor;
  }
  
  public TableDataSource getTableDataSource() {
    return this.tableDataSource;
  }
  
  public void setTableDataSource(TableDataSource paramTableDataSource) {
    this.tableDataSource = paramTableDataSource;
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    if (paramXMLableReader.isChildNode()) {
      String str = paramXMLableReader.getTagName();
      if ("FormElementCaseFlag".equals(str)) {
        this.elementCaseEditor = new ElementCaseEditor();
        paramXMLableReader.readXMLObject((XMLReadable)this.elementCaseEditor);
      } else if ("UUID".equals(str)) {
        this.id = paramXMLableReader.getElementValue();
      } else if (str.equals("TableDataMap")) {
        this.tableDataSource.readXML(paramXMLableReader);
      } 
    } 
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    try {
      convertTableDataSource();
    } catch (Exception exception) {
      FRLogger.getLogger().error(exception.getMessage(), exception);
    } 
    if (this.elementCaseEditor != null) {
      paramXMLPrintWriter.startTAG("FormElementCaseFlag");
      this.elementCaseEditor.writeXML(paramXMLPrintWriter);
      paramXMLPrintWriter.end();
    } 
    paramXMLPrintWriter.startTAG("UUID").textNode(this.id).end();
    if (this.tableDataSource != null) {
      Iterator<String> iterator = this.tableDataSource.getTableDataNameIterator();
      if (iterator.hasNext()) {
        paramXMLPrintWriter.startTAG("TableDataMap");
        while (iterator.hasNext()) {
          String str = iterator.next();
          TableData tableData = this.tableDataSource.getTableData(str);
          DataCoreXmlUtils.writeXMLTableData(paramXMLPrintWriter, tableData, str);
        } 
        paramXMLPrintWriter.end();
      } 
    } 
  }
  
  private void convertTableDataSource() throws Exception {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    Iterator<String> iterator = this.tableDataSource.getTableDataNameIterator();
    while (iterator.hasNext()) {
      String str = iterator.next();
      TableData tableData = this.tableDataSource.getTableData(str);
      hashMap.put(str, tableData);
    } 
    this.elementCaseEditor.getElementCase().convertCellElementDictionary(hashMap);
    for (Map.Entry<Object, Object> entry : hashMap.entrySet()) {
      Calculator calculator = null;
      if (this.paraMap != null) {
        calculator = Calculator.createCalculator();
        calculator.pushNameSpace((NameSpace)ParameterMapNameSpace.create(this.paraMap));
      } 
      this.tableDataSource.putTableData(entry.getKey().toString(), (TableData)EmbeddedTableData.embedify((TableData)entry.getValue(), calculator));
    } 
  }
  
  public String getXType() {
    return "sharableElementCase";
  }
  
  public boolean isEditor() {
    return false;
  }
  
  public String[] supportedEvents() {
    return new String[] { "click" };
  }
  
  public boolean batchRenameTdName(Map<String, String> paramMap) {
    return (this.elementCaseEditor == null) ? true : this.elementCaseEditor.batchRenameTdName(paramMap);
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\SharableElementCaseEditor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */