package com.fr.form.ui.container;

import com.fr.form.main.Form;
import com.fr.form.main.WidgetGather;
import com.fr.form.main.WidgetGatherAdapter;
import com.fr.form.ui.Widget;
import com.fr.form.ui.container.cardlayout.ButtonNameWrapper;
import com.fr.general.Inter;
import com.fr.json.JSONArray;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.web.Repository;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class WCardLayout extends WLayout {
  public static final String CARD_CHANGE = "cardchange";
  
  private int showIndex = 0;
  
  private transient ButtonNameWrapper btnsName;
  
  private transient boolean showAllCardOnInit = false;
  
  public WCardLayout() {
    this(0, 0);
  }
  
  public WCardLayout(int paramInt1, int paramInt2) {
    setHgap(paramInt1);
    setVgap(paramInt2);
    clearMargin();
  }
  
  public int getShowIndex() {
    return this.showIndex;
  }
  
  public void setShowIndex(int paramInt) {
    this.showIndex = paramInt;
  }
  
  public void setBtnsName(ButtonNameWrapper paramButtonNameWrapper) {
    this.btnsName = paramButtonNameWrapper;
  }
  
  public String getShowIndex2Name() {
    return getWidget(this.showIndex).getWidgetName();
  }
  
  public void setShowIndexByName(String paramString) {
    this.showIndex = getWidgetIndex(getWidget(paramString));
  }
  
  public String[] supportedEvents() {
    return new String[] { "afterinit", "cardchange" };
  }
  
  public String getXType() {
    return "cardlayout";
  }
  
  public String getLayoutToolTip() {
    return Inter.getLocText("FR-Designer_WLayout-Card-ToolTips");
  }
  
  public Dimension getMinDesignSize() {
    if (getWidgetCount() > this.showIndex) {
      Widget widget = getWidget(this.showIndex);
      if (widget instanceof WLayout && widget.isVisible())
        return ((WLayout)widget).getMinDesignSize(); 
    } 
    return new Dimension();
  }
  
  public JSONObject createJSONConfig(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    JSONObject jSONObject = super.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor);
    if (this.btnsName == null)
      return jSONObject; 
    int i = this.btnsName.getTagCount();
    JSONArray jSONArray = new JSONArray();
    for (byte b = 0; b < i; b++)
      jSONArray.put(this.btnsName.getIndexTab(b)); 
    jSONObject.put("allTagName", jSONArray);
    jSONObject.put("preBtnName", this.btnsName.getPreBtnName());
    jSONObject.put("nextBtnName", this.btnsName.getNextBtnName());
    jSONObject.put("titleWidth", this.btnsName.getTitleWidth());
    jSONObject.put("tagLayoutName", this.btnsName.getTagLayoutName());
    jSONObject.put("showAllCardOnInit", this.showAllCardOnInit);
    return jSONObject;
  }
  
  public void resize(double paramDouble1, double paramDouble2, double paramDouble3) {
    if (paramDouble1 > 0.0D || paramDouble2 > 0.0D) {
      this.showAllCardOnInit = true;
      List<Widget> list1 = findDelWidget();
      List<Widget> list2 = traversalFitInCardLayout(this);
      for (Widget widget : list2) {
        if (list1.contains(widget))
          continue; 
        paramDouble3 = (paramDouble3 == 1.0D) ? -1.0D : paramDouble3;
        widget.resize(paramDouble1, paramDouble2, paramDouble3);
      } 
    } 
  }
  
  private List<Widget> findDelWidget() {
    final ArrayList cards = new ArrayList();
    Form.traversalWidget((Widget)this, (WidgetGather)new WidgetGatherAdapter() {
          public boolean dealWithAllCards() {
            return true;
          }
          
          public void dealWith(Widget param1Widget) {
            cards.add((WCardLayout)param1Widget);
          }
        },  WCardLayout.class);
    ArrayList<Widget> arrayList1 = new ArrayList();
    for (WCardLayout wCardLayout : arrayList) {
      if (getWidgetName().equals(wCardLayout.getWidgetName()))
        continue; 
      arrayList1.addAll(traversalFitInCardLayout(wCardLayout));
    } 
    return arrayList1;
  }
  
  private List<Widget> traversalFitInCardLayout(WCardLayout paramWCardLayout) {
    final ArrayList<Widget> fitWidgets = new ArrayList();
    Form.traversalWidget((Widget)paramWCardLayout, (WidgetGather)new WidgetGatherAdapter() {
          public boolean dealWithAllCards() {
            return true;
          }
          
          public void dealWith(Widget param1Widget) {
            fitWidgets.add(param1Widget);
          }
        },  WFitLayout.class);
    return arrayList;
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\WCardLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */