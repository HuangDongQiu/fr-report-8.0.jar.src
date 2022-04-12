package com.fr.xml;

import com.fr.base.BaseXMLUtils;
import com.fr.base.Style;
import com.fr.report.cell.StyleProvider;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLReadable;
import com.fr.stable.xml.XMLableReader;
import java.util.ArrayList;
import java.util.List;

public class SynchronizedStyleList {
  private static ThreadLocal<SynchronizedStyleList> threadStyleHash = new ThreadLocal<SynchronizedStyleList>();
  
  private List<Style> styleList = new ArrayList<Style>();
  
  private List<StyleProvider>[] index_cellLists = (List<StyleProvider>[])new List[0];
  
  public static SynchronizedStyleList getSynchronizedStyleList() {
    SynchronizedStyleList synchronizedStyleList = threadStyleHash.get();
    if (synchronizedStyleList == null) {
      synchronizedStyleList = new SynchronizedStyleList();
      threadStyleHash.set(synchronizedStyleList);
    } 
    return synchronizedStyleList;
  }
  
  public void put(int paramInt, StyleProvider paramStyleProvider) {
    synchronized (this.index_cellLists) {
      if (this.index_cellLists.length <= paramInt) {
        int i = this.index_cellLists.length * 3 / 2 + 1;
        if (i <= paramInt)
          i = paramInt + 1; 
        List<StyleProvider>[] arrayOfList = this.index_cellLists;
        this.index_cellLists = (List<StyleProvider>[])new List[i];
        System.arraycopy(arrayOfList, 0, this.index_cellLists, 0, arrayOfList.length);
        this.index_cellLists[paramInt] = new ArrayList<StyleProvider>();
      } 
      if (this.index_cellLists[paramInt] == null)
        this.index_cellLists[paramInt] = new ArrayList<StyleProvider>(); 
      List<StyleProvider> list = this.index_cellLists[paramInt];
      list.add(paramStyleProvider);
    } 
  }
  
  public void deXmlizeStyleList(XMLableReader paramXMLableReader) {
    paramXMLableReader.readXMLObject(new XMLReadable() {
          public void readXML(XMLableReader param1XMLableReader) {
            if (param1XMLableReader.isChildNode()) {
              String str = param1XMLableReader.getTagName();
              if ("Style".equals(str))
                synchronized (SynchronizedStyleList.this.styleList) {
                  SynchronizedStyleList.this.styleList.add(BaseXMLUtils.readFullStyle(param1XMLableReader));
                }  
            } 
          }
        });
    flush_current_thread_styles();
  }
  
  private void flush_current_thread_styles() {
    synchronized (this) {
      List<StyleProvider>[] arrayOfList = this.index_cellLists;
      for (byte b = 0; b < arrayOfList.length; b++) {
        List<StyleProvider> list = arrayOfList[b];
        if (list != null)
          for (byte b1 = 0; b1 < list.size(); b1++)
            ((StyleProvider)list.get(b1)).setStyle(getStyle(b));  
      } 
      threadStyleHash.set(null);
    } 
  }
  
  private Style getStyle(int paramInt) {
    synchronized (this.styleList) {
      if (paramInt < 0 || paramInt >= this.styleList.size())
        return Style.DEFAULT_STYLE; 
      return this.styleList.get(paramInt);
    } 
  }
  
  public int indexOfStyle(Style paramStyle) {
    synchronized (this.styleList) {
      int i = this.styleList.indexOf(paramStyle);
      if (i != -1)
        return i; 
      this.styleList.add(paramStyle);
      return this.styleList.size() - 1;
    } 
  }
  
  public void xmlizeStyleList(XMLPrintWriter paramXMLPrintWriter) {
    synchronized (this) {
      paramXMLPrintWriter.startTAG("StyleList");
      byte b = 0;
      int i = this.styleList.size();
      while (b < i) {
        Style style = this.styleList.get(b);
        if (style != null)
          BaseXMLUtils.writeStyle(paramXMLPrintWriter, style); 
        b++;
      } 
      paramXMLPrintWriter.end();
      threadStyleHash.set(null);
    } 
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\xml\SynchronizedStyleList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */