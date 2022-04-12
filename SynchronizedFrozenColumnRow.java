package com.fr.xml;

import com.fr.stable.ColumnRow;

public class SynchronizedFrozenColumnRow {
  private static ThreadLocal threadColumnRowList = new ThreadLocal();
  
  public static void putSynchronizedFrozenColumnRow(ColumnRow paramColumnRow) {
    if (paramColumnRow != null && threadColumnRowList.get() == null)
      threadColumnRowList.set(paramColumnRow); 
  }
  
  public static ThreadLocal getThreadColumnRowList() {
    return threadColumnRowList;
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\xml\SynchronizedFrozenColumnRow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */