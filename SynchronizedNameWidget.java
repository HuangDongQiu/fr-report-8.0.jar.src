package com.fr.xml;

import com.fr.form.ui.FieldEditor;
import com.fr.form.ui.TextEditor;
import java.util.HashMap;
import java.util.Map;

public class SynchronizedNameWidget {
  private static final ThreadLocal threadNameWidgetMap = new ThreadLocal();
  
  private static final ThreadLocal threadDefaultValueMpa = new ThreadLocal();
  
  public static void put(String paramString, FieldEditor paramFieldEditor) {
    Map<Object, Object> map = threadNameWidgetMap.get();
    if (map == null) {
      map = new HashMap<Object, Object>();
      threadNameWidgetMap.set(map);
    } 
    map.put(paramString, paramFieldEditor);
  }
  
  public static FieldEditor get(String paramString) {
    Map map = threadNameWidgetMap.get();
    if (map != null) {
      TextEditor textEditor;
      FieldEditor fieldEditor = (FieldEditor)map.get(paramString);
      if (fieldEditor == null)
        textEditor = new TextEditor(); 
      textEditor.setWidgetName(paramString);
      return (FieldEditor)textEditor;
    } 
    return null;
  }
  
  public static void put(String paramString, Object paramObject) {
    Map<Object, Object> map = threadDefaultValueMpa.get();
    if (map == null) {
      map = new HashMap<Object, Object>();
      threadDefaultValueMpa.set(map);
    } 
    map.put(paramString, paramObject);
  }
  
  public static Object getDefaultValue(String paramString) {
    Map map = threadDefaultValueMpa.get();
    return (map != null) ? map.get(paramString) : null;
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\xml\SynchronizedNameWidget.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */