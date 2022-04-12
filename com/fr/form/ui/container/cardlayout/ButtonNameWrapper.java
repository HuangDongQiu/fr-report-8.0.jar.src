package com.fr.form.ui.container.cardlayout;

import com.fr.stable.ArrayUtils;

public class ButtonNameWrapper {
  private String[] allTagName;
  
  private String preBtnName;
  
  private String nextBtnName;
  
  private int titleWidth;
  
  private String tagLayoutName;
  
  public int getTagCount() {
    return ArrayUtils.getLength(this.allTagName);
  }
  
  public String getIndexTab(int paramInt) {
    return (paramInt == -1 || paramInt >= getTagCount()) ? "" : this.allTagName[paramInt];
  }
  
  public String[] getAllTagName() {
    return this.allTagName;
  }
  
  public void setAllTagName(String[] paramArrayOfString) {
    this.allTagName = paramArrayOfString;
  }
  
  public String getPreBtnName() {
    return this.preBtnName;
  }
  
  public void setPreBtnName(String paramString) {
    this.preBtnName = paramString;
  }
  
  public String getNextBtnName() {
    return this.nextBtnName;
  }
  
  public void setNextBtnName(String paramString) {
    this.nextBtnName = paramString;
  }
  
  public int getTitleWidth() {
    return this.titleWidth;
  }
  
  public void setTitleWidth(int paramInt) {
    this.titleWidth = paramInt;
  }
  
  public String getTagLayoutName() {
    return this.tagLayoutName;
  }
  
  public void setTagLayoutName(String paramString) {
    this.tagLayoutName = paramString;
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\cardlayout\ButtonNameWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */