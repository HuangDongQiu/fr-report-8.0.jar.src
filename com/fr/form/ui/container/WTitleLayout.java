package com.fr.form.ui.container;

import com.fr.base.ResizableElement;
import com.fr.form.main.Form;
import com.fr.form.main.WidgetGather;
import com.fr.form.main.WidgetGatherAdapter;
import com.fr.form.ui.PaddingMargin;
import com.fr.form.ui.Widget;
import com.fr.form.ui.WidgetXmlUtils;
import com.fr.general.ComparatorUtils;
import com.fr.general.xml.GeneralXMLTools;
import com.fr.stable.StringUtils;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLReadable;
import com.fr.stable.xml.XMLable;
import com.fr.stable.xml.XMLableReader;
import java.awt.Dimension;
import java.awt.Rectangle;

public class WTitleLayout extends WLayout {
  public static final String TITLE = "Title";
  
  public static final String BODY = "Body";
  
  public static final int TITLE_HEIGHT = 36;
  
  private Widget title;
  
  private Widget body;
  
  private static final long serialVersionUID = -5896314449303649779L;
  
  public WTitleLayout() {
    this(0);
  }
  
  public WTitleLayout(int paramInt) {
    setCompInterval(paramInt);
    this.margin = new PaddingMargin(0, 0, 0, 0);
  }
  
  public WTitleLayout(String paramString) {
    this(0);
    if (StringUtils.isNotEmpty(paramString))
      setWidgetName(paramString); 
  }
  
  public String getLayoutToolTip() {
    return null;
  }
  
  public Dimension getMinDesignSize() {
    int i = (this.title == null) ? MIN_HEIGHT : (MIN_HEIGHT + 36 + this.compInterval);
    return new Dimension(MIN_WIDTH, i);
  }
  
  public String getXType() {
    return "title";
  }
  
  public void addTitle(Widget paramWidget, Rectangle paramRectangle) {
    this.title = paramWidget;
    WAbsoluteLayout.BoundsWidget boundsWidget = new WAbsoluteLayout.BoundsWidget(paramWidget, paramRectangle);
    if (this.widgetList.contains(boundsWidget))
      return; 
    this.widgetList.add(new WAbsoluteLayout.BoundsWidget(paramWidget, paramRectangle));
  }
  
  public void addBody(Widget paramWidget, Rectangle paramRectangle) {
    this.body = paramWidget;
    this.widgetList.add(new WAbsoluteLayout.BoundsWidget(paramWidget, paramRectangle));
  }
  
  public void removeWidget(Widget paramWidget) {
    if (ComparatorUtils.equals(this.title, paramWidget)) {
      super.removeWidget(getTitleBoundsWidget());
      this.title = null;
    } else if (ComparatorUtils.equals(this.body, paramWidget)) {
      super.removeWidget(getBodyBoundsWidget());
      this.body = null;
    } else if (this.widgetList.size() > 1) {
      super.removeWidget(getTitleBoundsWidget());
      this.title = null;
    } 
  }
  
  public void removeAll() {
    super.removeAll();
    this.title = null;
    this.body = null;
  }
  
  private WAbsoluteLayout.BoundsWidget getTitleBoundsWidget() {
    return (getWidgetCount() == 1) ? (WAbsoluteLayout.BoundsWidget)getWidget(0) : (isBodyWidgetIndexFirst() ? (WAbsoluteLayout.BoundsWidget)getWidget(1) : (WAbsoluteLayout.BoundsWidget)getWidget(0));
  }
  
  public WAbsoluteLayout.BoundsWidget getBodyBoundsWidget() {
    return (getWidgetCount() == 1) ? (WAbsoluteLayout.BoundsWidget)getWidget(0) : (isBodyWidgetIndexFirst() ? (WAbsoluteLayout.BoundsWidget)getWidget(0) : (WAbsoluteLayout.BoundsWidget)getWidget(1));
  }
  
  private boolean isBodyWidgetIndexFirst() {
    WAbsoluteLayout.BoundsWidget boundsWidget = (WAbsoluteLayout.BoundsWidget)getWidget(0);
    return ComparatorUtils.equals(boundsWidget.getWidget(), this.body);
  }
  
  public void updateChildBounds(Rectangle paramRectangle) {
    Rectangle rectangle = new Rectangle(paramRectangle);
    updateTitleBounds(rectangle);
    updateBodyBounds(rectangle);
  }
  
  private void updateTitleBounds(Rectangle paramRectangle) {
    if (getWidgetCount() > 1) {
      WAbsoluteLayout.BoundsWidget boundsWidget = getTitleBoundsWidget();
      boundsWidget.setBounds(new Rectangle(0, 0, paramRectangle.width, 36));
    } 
  }
  
  public void resize(final double width, final double height, final double fontScale) {
    Form.traversalWidget((Widget)this, (WidgetGather)new WidgetGatherAdapter() {
          public void dealWith(Widget param1Widget) {
            boolean bool = (WTitleLayout.this.title == null) ? false : true;
            param1Widget.resize(width, height - bool, fontScale);
          }
        }ResizableElement.class);
  }
  
  private void updateBodyBounds(Rectangle paramRectangle) {
    byte b = (getWidgetCount() > 1) ? (36 + this.compInterval) : 0;
    WAbsoluteLayout.BoundsWidget boundsWidget = getBodyBoundsWidget();
    paramRectangle.y = b;
    paramRectangle.height -= b;
    boundsWidget.setBounds(paramRectangle);
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    super.readXML(paramXMLableReader);
    if (paramXMLableReader.isChildNode()) {
      String str = paramXMLableReader.getTagName();
      if (str.equals("title")) {
        paramXMLableReader.readXMLObject(new XMLReadable() {
              public void readXML(XMLableReader param1XMLableReader) {
                WTitleLayout.this.title = WidgetXmlUtils.readWidget(param1XMLableReader);
              }
            });
      } else if (str.equals("body")) {
        paramXMLableReader.readXMLObject(new XMLReadable() {
              public void readXML(XMLableReader param1XMLableReader) {
                WTitleLayout.this.body = WidgetXmlUtils.readWidget(param1XMLableReader);
              }
            });
      } 
    } 
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    super.writeXML(paramXMLPrintWriter);
    if (this.title != null)
      GeneralXMLTools.writeXMLable(paramXMLPrintWriter, (XMLable)this.title, "title"); 
    if (this.body != null)
      GeneralXMLTools.writeXMLable(paramXMLPrintWriter, (XMLable)this.body, "body"); 
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\WTitleLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */