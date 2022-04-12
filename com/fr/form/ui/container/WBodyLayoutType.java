package com.fr.form.ui.container;

import com.fr.form.ui.Widget;
import com.fr.general.Inter;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.web.Repository;

public enum WBodyLayoutType {
  FIT(0) {
    public String description() {
      return Inter.getLocText("FR-Designer-Layout_Adaptive_Layout");
    }
  },
  ABSOLUTE(1) {
    public String description() {
      return Inter.getLocText("FR-Designer_AbsoluteLayout");
    }
    
    public JSONObject createMobileJSONConfig(WLayout param1WLayout, Repository param1Repository, Calculator param1Calculator, NodeVisitor param1NodeVisitor) throws JSONException {
      return (param1WLayout.widgetList.get(0) != null) ? ((Widget)param1WLayout.widgetList.get(0)).createJSONConfig(param1Repository, param1Calculator, param1NodeVisitor) : null;
    }
  };
  
  private int type;
  
  WBodyLayoutType(int paramInt1) {
    this.type = paramInt1;
  }
  
  public String description() {
    return "";
  }
  
  public int getTypeValue() {
    return this.type;
  }
  
  public static WBodyLayoutType parse(int paramInt) {
    for (WBodyLayoutType wBodyLayoutType : values()) {
      if (wBodyLayoutType.getTypeValue() == paramInt)
        return wBodyLayoutType; 
    } 
    return FIT;
  }
  
  public JSONObject createMobileJSONConfig(WLayout paramWLayout, Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    return null;
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\WBodyLayoutType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */