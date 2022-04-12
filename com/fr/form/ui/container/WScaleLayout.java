package com.fr.form.ui.container;

import com.fr.form.event.Listener;
import com.fr.form.ui.Widget;
import com.fr.stable.StringUtils;
import java.awt.Dimension;
import java.awt.Rectangle;

public class WScaleLayout extends WLayout {
  public WScaleLayout() {
    this("");
  }
  
  public WScaleLayout(String paramString) {
    if (StringUtils.isNotEmpty(paramString))
      setWidgetName(paramString); 
    clearMargin();
  }
  
  public String getLayoutToolTip() {
    return null;
  }
  
  public Dimension getMinDesignSize() {
    return new Dimension(MIN_WIDTH, MIN_HEIGHT);
  }
  
  public String getXType() {
    return "scale";
  }
  
  public void addListener(Listener paramListener) {
    if (getWidgetCount() > 0)
      ((WAbsoluteLayout.BoundsWidget)getBoundsWidget()).getWidget().addListener(paramListener); 
  }
  
  public void clearListeners() {
    if (getWidgetCount() > 0)
      ((WAbsoluteLayout.BoundsWidget)getBoundsWidget()).getWidget().clearListeners(); 
  }
  
  public int getListenerSize() {
    return (getWidgetCount() > 0) ? ((WAbsoluteLayout.BoundsWidget)getBoundsWidget()).getWidget().getListenerSize() : super.getListenerSize();
  }
  
  public Listener getListener(int paramInt) {
    return (getWidgetCount() > 0) ? ((WAbsoluteLayout.BoundsWidget)getBoundsWidget()).getWidget().getListener(paramInt) : super.getListener(paramInt);
  }
  
  public void updateChildBounds(Rectangle paramRectangle) {
    Rectangle rectangle = new Rectangle(paramRectangle);
    WAbsoluteLayout.BoundsWidget boundsWidget = (WAbsoluteLayout.BoundsWidget)getWidget(0);
    if (boundsWidget != null) {
      rectangle.height = MIN_HEIGHT;
      boundsWidget.setBounds(rectangle);
    } 
  }
  
  public Widget getBoundsWidget() {
    return getWidget(0);
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\WScaleLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */