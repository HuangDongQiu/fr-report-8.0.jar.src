package com.fr.form.main;

import com.fr.form.ui.Widget;
import com.fr.form.ui.container.WLayout;
import java.util.ArrayList;

public class ClonedWidgetCreator {
  private Form form;
  
  private ArrayList clonedNameList;
  
  public ClonedWidgetCreator(Form paramForm) {
    this.form = paramForm;
  }
  
  public Widget clonedWidgetWithNoRepeatName(Widget paramWidget) throws CloneNotSupportedException {
    this.clonedNameList = new ArrayList();
    Widget widget = (Widget)paramWidget.clone();
    setClonedName(widget);
    if (widget instanceof WLayout)
      for (byte b = 0; b < ((WLayout)widget).getWidgetCount(); b++)
        setClonedName(((WLayout)widget).getWidget(b));  
    return widget;
  }
  
  public void setClonedName(Widget paramWidget) {
    String str = paramWidget.getWidgetName();
    while (true) {
      str = str + "_c";
      if (!this.form.isNameExist(str) && !this.clonedNameList.contains(str)) {
        paramWidget.setWidgetName(str);
        this.clonedNameList.add(str);
        return;
      } 
    } 
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\main\ClonedWidgetCreator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */