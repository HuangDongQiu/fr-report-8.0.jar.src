package com.fr.form.main;

import com.fr.base.ParameterMapNameSpace;
import com.fr.base.TableData;
import com.fr.data.impl.EmbeddedTableData;
import com.fr.form.ui.ElementCaseEditorProvider;
import com.fr.script.Calculator;
import com.fr.stable.script.NameSpace;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FormEmbeddedTableDataExporter {
  public void export(OutputStream paramOutputStream, Form paramForm) throws Exception {
    export(paramOutputStream, paramForm, null);
  }
  
  public void export(OutputStream paramOutputStream, Form paramForm, Map paramMap) throws Exception {
    paramForm = (Form)paramForm.clone();
    convertWorkBook(paramForm, paramMap);
    paramForm.export(paramOutputStream);
  }
  
  private void convertWorkBook(Form paramForm, Map paramMap) throws Exception {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    Iterator<String> iterator = paramForm.getTableDataNameIterator();
    while (iterator.hasNext()) {
      String str = iterator.next();
      TableData tableData = paramForm.getTableData(str);
      hashMap.put(str, tableData);
    } 
    ElementCaseEditorProvider[] arrayOfElementCaseEditorProvider = paramForm.getElementCases();
    for (ElementCaseEditorProvider elementCaseEditorProvider : arrayOfElementCaseEditorProvider)
      elementCaseEditorProvider.getElementCase().convertCellElementDictionary(hashMap); 
    for (Map.Entry<Object, Object> entry : hashMap.entrySet()) {
      Calculator calculator = null;
      if (paramMap != null) {
        calculator = Calculator.createCalculator();
        calculator.pushNameSpace((NameSpace)ParameterMapNameSpace.create(paramMap));
      } 
      paramForm.putTableData(entry.getKey().toString(), (TableData)EmbeddedTableData.embedify((TableData)entry.getValue(), calculator));
    } 
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\main\FormEmbeddedTableDataExporter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */