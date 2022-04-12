package com.fr.form.ui.container;

import com.fr.form.main.ExtraFormClassManager;
import com.fr.form.stable.FormWidgetBoundCorrectionProcessor;
import com.fr.form.ui.Widget;
import com.fr.form.ui.WidgetXmlUtils;
import com.fr.general.ComparatorUtils;
import com.fr.general.Inter;
import com.fr.general.xml.GeneralXMLTools;
import com.fr.json.JSONArray;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.ArrayUtils;
import com.fr.stable.StringUtils;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.web.Repository;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLReadable;
import com.fr.stable.xml.XMLable;
import com.fr.stable.xml.XMLableReader;
import java.awt.Dimension;

public class WBorderLayout extends WLayout {
  public static final String NORTH = "North";
  
  public static final String SOUTH = "South";
  
  public static final String EAST = "East";
  
  public static final String WEST = "West";
  
  public static final String CENTER = "Center";
  
  public static final String[] DEFAULT_DIRECTIONS = new String[] { "North", "South", "East", "West" };
  
  public static final int DEFAULT_SIZE = 65;
  
  private Widget north;
  
  private int northSize = 65;
  
  private String northTitle;
  
  private Widget south;
  
  private int southSize = 65;
  
  private String southTitle;
  
  private Widget east;
  
  private int eastSize = 65;
  
  private String eastTitle;
  
  private Widget west;
  
  private int westSize = 65;
  
  private String westTitle;
  
  private Widget center;
  
  private String centerTitle;
  
  private String[] directions;
  
  public WBorderLayout() {
    this(0, 0);
  }
  
  public WBorderLayout(String paramString) {
    this(0, 0);
    setWidgetName(paramString);
  }
  
  public WBorderLayout(int paramInt1, int paramInt2) {
    this(paramInt1, paramInt2, DEFAULT_DIRECTIONS);
  }
  
  public WBorderLayout(int paramInt1, int paramInt2, String[] paramArrayOfString) {
    setHgap(paramInt1);
    setVgap(paramInt2);
    this.directions = paramArrayOfString;
    clearMargin();
  }
  
  public String getXType() {
    return "border";
  }
  
  public String[] getDirections() {
    return (this.directions == null) ? ArrayUtils.EMPTY_STRING_ARRAY : this.directions;
  }
  
  public void setDirections(String[] paramArrayOfString) {
    this.directions = paramArrayOfString;
  }
  
  public void refreshDirections(String[] paramArrayOfString) {
    if (paramArrayOfString == null)
      return; 
    this.directions = paramArrayOfString;
    if (ArrayUtils.indexOf((Object[])paramArrayOfString, "North") == -1)
      this.north = null; 
    if (ArrayUtils.indexOf((Object[])paramArrayOfString, "South") == -1)
      this.south = null; 
    if (ArrayUtils.indexOf((Object[])paramArrayOfString, "East") == -1)
      this.east = null; 
    if (ArrayUtils.indexOf((Object[])paramArrayOfString, "West") == -1)
      this.west = null; 
  }
  
  public void addNorth(Widget paramWidget) {
    this.north = paramWidget;
  }
  
  public void addSouth(Widget paramWidget) {
    this.south = paramWidget;
  }
  
  public void addWest(Widget paramWidget) {
    this.west = paramWidget;
  }
  
  public void addEast(Widget paramWidget) {
    this.east = paramWidget;
  }
  
  public void addCenter(Widget paramWidget) {
    this.center = paramWidget;
  }
  
  public int getEastSize() {
    return ArrayUtils.contains((Object[])this.directions, "East") ? this.eastSize : 0;
  }
  
  public void setEastSize(int paramInt) {
    this.eastSize = paramInt;
  }
  
  public int getNorthSize() {
    return ArrayUtils.contains((Object[])this.directions, "North") ? this.northSize : 0;
  }
  
  public void setNorthSize(int paramInt) {
    this.northSize = paramInt;
  }
  
  public int getSouthSize() {
    return ArrayUtils.contains((Object[])this.directions, "South") ? this.southSize : 0;
  }
  
  public void setSouthSize(int paramInt) {
    this.southSize = paramInt;
  }
  
  public int getWestSize() {
    return ArrayUtils.contains((Object[])this.directions, "West") ? this.westSize : 0;
  }
  
  public void setWestSize(int paramInt) {
    this.westSize = paramInt;
  }
  
  public String getCenterTitle() {
    return this.centerTitle;
  }
  
  public void setCenterTitle(String paramString) {
    this.centerTitle = paramString;
  }
  
  public String getEastTitle() {
    return this.eastTitle;
  }
  
  public void setEastTitle(String paramString) {
    this.eastTitle = paramString;
  }
  
  public String getNorthTitle() {
    return this.northTitle;
  }
  
  public void setNorthTitle(String paramString) {
    this.northTitle = paramString;
  }
  
  public String getSouthTitle() {
    return this.southTitle;
  }
  
  public void setSouthTitle(String paramString) {
    this.southTitle = paramString;
  }
  
  public String getWestTitle() {
    return this.westTitle;
  }
  
  public void setWestTitle(String paramString) {
    this.westTitle = paramString;
  }
  
  public Object getConstraints(Widget paramWidget) {
    return ComparatorUtils.equals(paramWidget, this.north) ? "North" : (ComparatorUtils.equals(paramWidget, this.south) ? "South" : (ComparatorUtils.equals(paramWidget, this.east) ? "East" : (ComparatorUtils.equals(paramWidget, this.west) ? "West" : (ComparatorUtils.equals(paramWidget, this.center) ? "Center" : null))));
  }
  
  public Widget getLayoutWidget(Object paramObject) {
    return ComparatorUtils.equals("North", paramObject) ? (ArrayUtils.contains((Object[])this.directions, "North") ? this.north : null) : (ComparatorUtils.equals("South", paramObject) ? (ArrayUtils.contains((Object[])this.directions, "South") ? this.south : null) : (ComparatorUtils.equals("West", paramObject) ? (ArrayUtils.contains((Object[])this.directions, "West") ? this.west : null) : (ComparatorUtils.equals("East", paramObject) ? (ArrayUtils.contains((Object[])this.directions, "East") ? this.east : null) : this.center)));
  }
  
  public int getWidgetCount() {
    byte b = 0;
    if (this.north != null)
      b++; 
    if (this.south != null)
      b++; 
    if (this.east != null)
      b++; 
    if (this.west != null)
      b++; 
    if (this.center != null)
      b++; 
    return b;
  }
  
  public Widget getWidget(int paramInt) {
    if (paramInt < 0)
      throw new IndexOutOfBoundsException("Index: " + paramInt + " is less than 0"); 
    if (this.north != null && paramInt-- == 0)
      return this.north; 
    if (this.south != null && paramInt-- == 0)
      return this.south; 
    if (this.east != null && paramInt-- == 0)
      return this.east; 
    if (this.west != null && paramInt-- == 0)
      return this.west; 
    if (this.center != null && paramInt-- == 0)
      return this.center; 
    throw new IndexOutOfBoundsException();
  }
  
  public void removeWidget(Widget paramWidget) {
    if (ComparatorUtils.equals(this.north, paramWidget)) {
      this.north = null;
    } else if (ComparatorUtils.equals(this.south, paramWidget)) {
      this.south = null;
    } else if (ComparatorUtils.equals(this.east, paramWidget)) {
      this.east = null;
    } else if (ComparatorUtils.equals(this.west, paramWidget)) {
      this.west = null;
    } else if (ComparatorUtils.equals(this.center, paramWidget)) {
      this.center = null;
    } 
  }
  
  public void removeAll() {
    super.removeAll();
    this.north = null;
    this.south = null;
    this.east = null;
    this.west = null;
    this.center = null;
  }
  
  public String getLayoutToolTip() {
    return Inter.getLocText("FR-Designer_WLayout-Border-ToolTips");
  }
  
  public Dimension getMinDesignSize() {
    Dimension dimension = (this.center instanceof WLayout && this.center.isVisible()) ? ((WLayout)this.center).getMinDesignSize() : new Dimension();
    return new Dimension(dimension.width + getWestSize() + getEastSize(), dimension.height + getNorthSize() + getSouthSize());
  }
  
  protected JSONArray createJSONItems(Repository paramRepository, Calculator paramCalculator, NodeVisitor paramNodeVisitor) throws JSONException {
    JSONArray jSONArray = new JSONArray();
    if (this.north != null) {
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("region", "north");
      jSONObject1.put("height", getNorthSize());
      jSONObject1.put("el", this.north.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor));
      jSONArray.put(jSONObject1);
    } 
    if (this.south != null) {
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("region", "south");
      jSONObject1.put("height", getSouthSize());
      jSONObject1.put("el", this.south.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor));
      jSONArray.put(jSONObject1);
    } 
    if (this.east != null) {
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("region", "east");
      jSONObject1.put("width", getEastSize());
      jSONObject1.put("el", this.east.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor));
      jSONArray.put(jSONObject1);
    } 
    if (this.west != null) {
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("region", "west");
      jSONObject1.put("width", getWestSize());
      jSONObject1.put("el", this.west.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor));
      jSONArray.put(jSONObject1);
    } 
    JSONObject jSONObject = new JSONObject();
    jSONObject.put("region", "center");
    jSONObject.put("el", (this.center == null) ? (new WHorizontalBoxLayout()).createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor) : this.center.createJSONConfig(paramRepository, paramCalculator, paramNodeVisitor));
    jSONArray.put(jSONObject);
    return jSONArray;
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    super.readXML(paramXMLableReader);
    if (paramXMLableReader.isChildNode()) {
      String str = paramXMLableReader.getTagName();
      if (str.equals("North")) {
        paramXMLableReader.readXMLObject(new XMLReadable() {
              public void readXML(XMLableReader param1XMLableReader) {
                WBorderLayout.this.addNorth(WidgetXmlUtils.readWidget(param1XMLableReader));
              }
            });
      } else if (str.equals("NorthAttr")) {
        setNorthSize(paramXMLableReader.getAttrAsInt("size", 65));
        setNorthTitle(paramXMLableReader.getAttrAsString("title", ""));
      } else if (str.equals("South")) {
        paramXMLableReader.readXMLObject(new XMLReadable() {
              public void readXML(XMLableReader param1XMLableReader) {
                WBorderLayout.this.addSouth(WidgetXmlUtils.readWidget(param1XMLableReader));
              }
            });
      } else if (str.equals("SouthAttr")) {
        setSouthSize(paramXMLableReader.getAttrAsInt("size", 65));
        setSouthTitle(paramXMLableReader.getAttrAsString("title", ""));
      } else if (str.equals("East")) {
        paramXMLableReader.readXMLObject(new XMLReadable() {
              public void readXML(XMLableReader param1XMLableReader) {
                WBorderLayout.this.addEast(WidgetXmlUtils.readWidget(param1XMLableReader));
              }
            });
      } else if (str.equals("EastAttr")) {
        setEastSize(paramXMLableReader.getAttrAsInt("size", 65));
        setEastTitle(paramXMLableReader.getAttrAsString("title", ""));
      } else if (str.equals("West")) {
        paramXMLableReader.readXMLObject(new XMLReadable() {
              public void readXML(XMLableReader param1XMLableReader) {
                WBorderLayout.this.addWest(WidgetXmlUtils.readWidget(param1XMLableReader));
              }
            });
      } else if (str.equals("WestAttr")) {
        setWestSize(paramXMLableReader.getAttrAsInt("size", 65));
        setWestTitle(paramXMLableReader.getAttrAsString("title", ""));
      } else if (str.equals("Center")) {
        paramXMLableReader.readXMLObject(new XMLReadable() {
              public void readXML(XMLableReader param1XMLableReader) {
                Widget widget = WidgetXmlUtils.readWidget(param1XMLableReader);
                FormWidgetBoundCorrectionProcessor formWidgetBoundCorrectionProcessor = (FormWidgetBoundCorrectionProcessor)ExtraFormClassManager.getInstance().getSingle("FormWidgetBoundCorrectionProcessor");
                if (null != formWidgetBoundCorrectionProcessor)
                  formWidgetBoundCorrectionProcessor.processWidgetLocation(widget); 
                WBorderLayout.this.addCenter(widget);
              }
            });
      } else if (str.equals("CenterAttr")) {
        setCenterTitle(paramXMLableReader.getAttrAsString("title", ""));
      } 
    } 
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    this.widgetList.clear();
    super.writeXML(paramXMLPrintWriter);
    writeNorthSouth(paramXMLPrintWriter);
    writeEastWest(paramXMLPrintWriter);
    if (this.center != null) {
      if (StringUtils.isNotEmpty(this.centerTitle))
        paramXMLPrintWriter.startTAG("CenterAttr").attr("title", this.centerTitle).end(); 
      GeneralXMLTools.writeXMLable(paramXMLPrintWriter, (XMLable)this.center, "Center");
    } 
  }
  
  private void writeNorthSouth(XMLPrintWriter paramXMLPrintWriter) {
    if (this.north != null) {
      paramXMLPrintWriter.startTAG("NorthAttr");
      if (this.northSize != 65)
        paramXMLPrintWriter.attr("size", this.northSize); 
      if (StringUtils.isNotEmpty(this.northTitle))
        paramXMLPrintWriter.attr("title", this.northTitle); 
      paramXMLPrintWriter.end();
      GeneralXMLTools.writeXMLable(paramXMLPrintWriter, (XMLable)this.north, "North");
    } 
    if (this.south != null) {
      paramXMLPrintWriter.startTAG("SouthAttr");
      if (this.southSize != 65)
        paramXMLPrintWriter.attr("size", this.southSize); 
      if (StringUtils.isNotEmpty(this.southTitle))
        paramXMLPrintWriter.attr("title", this.southTitle); 
      paramXMLPrintWriter.end();
      GeneralXMLTools.writeXMLable(paramXMLPrintWriter, (XMLable)this.south, "South");
    } 
  }
  
  private void writeEastWest(XMLPrintWriter paramXMLPrintWriter) {
    if (this.west != null) {
      paramXMLPrintWriter.startTAG("WestAttr");
      if (this.westSize != 65)
        paramXMLPrintWriter.attr("size", this.westSize); 
      if (StringUtils.isNotEmpty(this.westTitle))
        paramXMLPrintWriter.attr("title", this.westTitle); 
      paramXMLPrintWriter.end();
      GeneralXMLTools.writeXMLable(paramXMLPrintWriter, (XMLable)this.west, "West");
    } 
    if (this.east != null) {
      paramXMLPrintWriter.startTAG("EastAttr");
      if (this.eastSize != 65)
        paramXMLPrintWriter.attr("size", this.eastSize); 
      if (StringUtils.isNotEmpty(this.eastTitle))
        paramXMLPrintWriter.attr("title", this.eastTitle); 
      paramXMLPrintWriter.end();
      GeneralXMLTools.writeXMLable(paramXMLPrintWriter, (XMLable)this.east, "East");
    } 
  }
  
  public Object clone() throws CloneNotSupportedException {
    WBorderLayout wBorderLayout = (WBorderLayout)super.clone();
    if (this.north != null)
      wBorderLayout.north = (Widget)this.north.clone(); 
    if (this.south != null)
      wBorderLayout.south = (Widget)this.south.clone(); 
    if (this.west != null)
      wBorderLayout.west = (Widget)this.west.clone(); 
    if (this.east != null)
      wBorderLayout.east = (Widget)this.east.clone(); 
    if (this.center != null)
      wBorderLayout.center = (Widget)this.center.clone(); 
    return wBorderLayout;
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\container\WBorderLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */