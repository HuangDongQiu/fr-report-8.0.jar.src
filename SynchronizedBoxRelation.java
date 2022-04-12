package com.fr.xml;

import com.fr.report.cell.AbstractAnalyCellElement;
import com.fr.report.cell.AnalyCellElement;
import com.fr.report.cell.DefaultViewCellElement;
import com.fr.report.core.A.Z;
import com.fr.report.core.A.i;
import com.fr.report.core.box.BoxElement;
import com.fr.report.core.box.BoxElementBox;
import com.fr.report.worksheet.AnalysisRWorkSheet;
import com.fr.stable.ColumnRow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SynchronizedBoxRelation {
  private static final ThreadLocal threadBEParList = new ThreadLocal();
  
  private static final AC LEFT_AC = new AC() {
      public void modParent(Z param1Z, DefaultViewCellElement param1DefaultViewCellElement) {
        param1Z.B((AnalyCellElement)param1DefaultViewCellElement);
      }
      
      public void setLeadBEB(i param1i1, i param1i2) {
        param1i1.setLeftLeadBEB((BoxElementBox)param1i2);
      }
      
      public void setParentBEB(i param1i1, i param1i2) {
        param1i1.setLeftParBEB((BoxElementBox)param1i2);
      }
    };
  
  private static final AC UP_AC = new AC() {
      public void modParent(Z param1Z, DefaultViewCellElement param1DefaultViewCellElement) {
        param1Z.A((AnalyCellElement)param1DefaultViewCellElement);
      }
      
      public void setLeadBEB(i param1i1, i param1i2) {
        param1i1.setUpLeadBEB((BoxElementBox)param1i2);
      }
      
      public void setParentBEB(i param1i1, i param1i2) {
        param1i1.setUpParBEB((BoxElementBox)param1i2);
      }
    };
  
  private static void flushParent(AnalysisRWorkSheet paramAnalysisRWorkSheet, AC paramAC) {
    Map map = paramAC.localColumnRow2CommonBEList().get();
    if (map != null) {
      Set set = map.keySet();
      for (ColumnRow columnRow : set) {
        DefaultViewCellElement defaultViewCellElement = (DefaultViewCellElement)paramAnalysisRWorkSheet.getCellElement(columnRow.getColumn(), columnRow.getRow());
        List<Z> list = (List)map.get(columnRow);
        for (byte b = 0; b < list.size(); b++) {
          Z z = list.get(b);
          paramAC.modParent(z, defaultViewCellElement);
          z.B((AnalyCellElement)defaultViewCellElement);
          Iterator<AbstractAnalyCellElement> iterator = z.getResultBoxIterator();
          while (iterator.hasNext())
            defaultViewCellElement.addSonBoxCE(iterator.next()); 
        } 
      } 
    } 
    paramAC.localColumnRow2CommonBEList().set(null);
  }
  
  private static void putParent(ColumnRow paramColumnRow, Z paramZ, AC paramAC) {
    Map<Object, Object> map = paramAC.localColumnRow2CommonBEList().get();
    if (map == null) {
      map = new HashMap<Object, Object>();
      paramAC.localColumnRow2CommonBEList().set(map);
    } 
    List<Z> list = (List)map.get(paramColumnRow);
    if (list == null) {
      list = new ArrayList();
      map.put(paramColumnRow, list);
    } 
    list.add(paramZ);
  }
  
  public static void putLeftPar(ColumnRow paramColumnRow, Z paramZ) {
    putParent(paramColumnRow, paramZ, LEFT_AC);
  }
  
  public static void putUpPar(ColumnRow paramColumnRow, Z paramZ) {
    putParent(paramColumnRow, paramZ, UP_AC);
  }
  
  public static void putBEPar(int paramInt1, int paramInt2, int paramInt3, DefaultViewCellElement paramDefaultViewCellElement) {
    Map<Object, Object> map = threadBEParList.get();
    if (map == null) {
      map = new HashMap<Object, Object>();
      threadBEParList.set(map);
    } 
    BEI bEI = new BEI(paramInt1, paramInt2, paramInt3);
    List<DefaultViewCellElement> list = (List)map.get(bEI);
    if (list == null) {
      list = new ArrayList();
      map.put(bEI, list);
    } 
    list.add(paramDefaultViewCellElement);
  }
  
  private static void flushBEPar(AnalysisRWorkSheet paramAnalysisRWorkSheet) {
    Map map = threadBEParList.get();
    if (map != null) {
      Set set = map.keySet();
      for (BEI bEI : set) {
        Z z = (Z)paramAnalysisRWorkSheet.getBEB(bEI.col, bEI.row).getSonBoxElement()[bEI.idx];
        List<DefaultViewCellElement> list = (List)map.get(bEI);
        for (byte b = 0; b < list.size(); b++)
          ((DefaultViewCellElement)list.get(b)).setBE((BoxElement)z); 
        z.A((AnalyCellElement[])list.<DefaultViewCellElement>toArray(new DefaultViewCellElement[list.size()]));
      } 
    } 
    threadBEParList.set(null);
  }
  
  private static void putColumnRow2CommonBEB(ColumnRow paramColumnRow, i parami, AC paramAC) {
    Map<Object, Object> map = paramAC.localColumnRow2CommonBEBList().get();
    if (map == null) {
      map = new HashMap<Object, Object>();
      paramAC.localColumnRow2CommonBEBList().set(map);
    } 
    List<i> list = (List)map.get(paramColumnRow);
    if (list == null) {
      list = new ArrayList();
      map.put(paramColumnRow, list);
    } 
    list.add(parami);
  }
  
  public static void putLSB(ColumnRow paramColumnRow, i parami) {
    putColumnRow2CommonBEB(paramColumnRow, parami, LEFT_AC);
  }
  
  public static void putUSB(ColumnRow paramColumnRow, i parami) {
    putColumnRow2CommonBEB(paramColumnRow, parami, UP_AC);
  }
  
  private static void flushSB(AnalysisRWorkSheet paramAnalysisRWorkSheet, AC paramAC) {
    Map map = paramAC.localColumnRow2CommonBEBList().get();
    if (map != null) {
      Set set = map.keySet();
      for (ColumnRow columnRow : set) {
        i i = (i)paramAnalysisRWorkSheet.getBEB(columnRow.getColumn(), columnRow.getRow());
        List<i> list = (List)map.get(columnRow);
        for (byte b = 0; b < list.size(); b++)
          paramAC.setLeadBEB(list.get(b), i); 
      } 
    } 
    paramAC.localColumnRow2CommonBEBList().set(null);
  }
  
  private static void putPB(ColumnRow paramColumnRow, i parami, AC paramAC) {
    Map<Object, Object> map = paramAC.localPB().get();
    if (map == null) {
      map = new HashMap<Object, Object>();
      paramAC.localPB().set(map);
    } 
    List<i> list = (List)map.get(paramColumnRow);
    if (list == null) {
      list = new ArrayList();
      map.put(paramColumnRow, list);
    } 
    list.add(parami);
  }
  
  public static void putLPB(ColumnRow paramColumnRow, i parami) {
    putPB(paramColumnRow, parami, LEFT_AC);
  }
  
  public static void putUPB(ColumnRow paramColumnRow, i parami) {
    putPB(paramColumnRow, parami, UP_AC);
  }
  
  private static void flushPB(AnalysisRWorkSheet paramAnalysisRWorkSheet, Map<i, List> paramMap, AC paramAC) {
    Map map = paramAC.localPB().get();
    if (map != null) {
      Set set = map.keySet();
      for (ColumnRow columnRow : set) {
        i i = (i)paramAnalysisRWorkSheet.getBEB(columnRow.getColumn(), columnRow.getRow());
        List<i> list1 = (List)map.get(columnRow);
        for (byte b = 0; b < list1.size(); b++)
          paramAC.setParentBEB(list1.get(b), i); 
        List<i> list2 = (List)paramMap.get(i);
        if (list2 == null) {
          list2 = new ArrayList();
          paramMap.put(i, list2);
        } 
        list2.addAll(list1);
      } 
    } 
    paramAC.localPB().set(null);
  }
  
  private static void flushLUPB(AnalysisRWorkSheet paramAnalysisRWorkSheet) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>(8);
    flushPB(paramAnalysisRWorkSheet, hashMap, LEFT_AC);
    flushPB(paramAnalysisRWorkSheet, hashMap, UP_AC);
    Set set = hashMap.keySet();
    for (i i : set) {
      List list = (List)hashMap.get(i);
      i.setSonBEBs((BoxElementBox[])list.toArray((Object[])new i[list.size()]));
    } 
  }
  
  public static void flushAll(AnalysisRWorkSheet paramAnalysisRWorkSheet) {
    flushBEPar(paramAnalysisRWorkSheet);
    flushParent(paramAnalysisRWorkSheet, LEFT_AC);
    flushParent(paramAnalysisRWorkSheet, UP_AC);
    flushSB(paramAnalysisRWorkSheet, LEFT_AC);
    flushSB(paramAnalysisRWorkSheet, UP_AC);
    flushLUPB(paramAnalysisRWorkSheet);
  }
  
  private static class BEI {
    private int row;
    
    private int col;
    
    private int idx;
    
    private BEI(int param1Int1, int param1Int2, int param1Int3) {
      this.row = param1Int1;
      this.col = param1Int2;
      this.idx = param1Int3;
    }
    
    public int hashCode() {
      byte b = 31;
      null = 1;
      null = 31 * null + this.col;
      null = 31 * null + this.idx;
      return 31 * null + this.row;
    }
    
    public boolean equals(Object param1Object) {
      return (((BEI)param1Object).row == this.row && ((BEI)param1Object).col == this.col && ((BEI)param1Object).idx == this.idx);
    }
  }
  
  private static abstract class AC {
    private ThreadLocal threadParList = new ThreadLocal();
    
    private ThreadLocal threadSBList = new ThreadLocal();
    
    private ThreadLocal threadPBList = new ThreadLocal();
    
    private AC() {}
    
    protected ThreadLocal localColumnRow2CommonBEList() {
      return this.threadParList;
    }
    
    protected abstract void modParent(Z param1Z, DefaultViewCellElement param1DefaultViewCellElement);
    
    protected ThreadLocal localColumnRow2CommonBEBList() {
      return this.threadSBList;
    }
    
    protected abstract void setLeadBEB(i param1i1, i param1i2);
    
    protected ThreadLocal localPB() {
      return this.threadPBList;
    }
    
    protected abstract void setParentBEB(i param1i1, i param1i2);
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\xml\SynchronizedBoxRelation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */