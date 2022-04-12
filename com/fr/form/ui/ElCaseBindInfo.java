package com.fr.form.ui;

import com.fr.general.xml.GeneralXMLTools;
import com.fr.stable.ProductConstants;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLable;
import com.fr.stable.xml.XMLableReader;
import java.awt.Image;

public class ElCaseBindInfo implements XMLable {
  public static final String XML_TAG = "ElementCaseModule";
  
  public static final String XML_TAG_ID = "id";
  
  public static final String XML_TAG_NAME = "name";
  
  public static final String XML_TAG_VENDER = "vendor";
  
  public static final String XML_TAG_GI = "guideInfo";
  
  public static final String XML_TAG_PRICE = "price";
  
  public static final String XML_TAG_TAG = "tag";
  
  public static final String XML_TAG_CLASSIFY = "classify";
  
  public static final String XML_TAG_CLASSIFY_CN = "classifyCN";
  
  public static final String XML_TAG_ENV_VERSION = "envVersion";
  
  private String id;
  
  private String vendor;
  
  private String name;
  
  private String envVersion;
  
  private String guideInfo;
  
  private String price;
  
  private String tag;
  
  private String classify;
  
  private String classifycn;
  
  private Image cover;
  
  public ElCaseBindInfo() {}
  
  public ElCaseBindInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, Image paramImage) {
    this(paramString1, null, paramString2, ProductConstants.MAIN_VERSION, paramString3, paramString4, paramString5, paramString6, paramString7, paramImage);
  }
  
  public ElCaseBindInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, Image paramImage) {
    this(paramString1, paramString2, paramString3, ProductConstants.MAIN_VERSION, paramString4, paramString5, paramString6, paramString7, paramString8, paramImage);
  }
  
  public ElCaseBindInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, Image paramImage) {
    this.id = paramString1;
    this.vendor = paramString2;
    this.name = paramString3;
    this.envVersion = paramString4;
    this.guideInfo = paramString5;
    this.price = paramString6;
    this.tag = paramString7;
    this.classify = paramString8;
    this.classifycn = paramString9;
    this.cover = paramImage;
  }
  
  public String getId() {
    return this.id;
  }
  
  public void setId(String paramString) {
    this.id = paramString;
  }
  
  public String getVendor() {
    return this.vendor;
  }
  
  public void setVendor(String paramString) {
    this.vendor = paramString;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String paramString) {
    this.name = paramString;
  }
  
  public String getEnvVersion() {
    return this.envVersion;
  }
  
  public void setEnvVersion(String paramString) {
    this.envVersion = paramString;
  }
  
  public String getGuideInfo() {
    return this.guideInfo;
  }
  
  public void setGuideInfo(String paramString) {
    this.guideInfo = paramString;
  }
  
  public String getPrice() {
    return this.price;
  }
  
  public void setPrice(String paramString) {
    this.price = paramString;
  }
  
  public String getTag() {
    return this.tag;
  }
  
  public void setTag(String paramString) {
    this.tag = paramString;
  }
  
  public String getClassify() {
    return this.classify;
  }
  
  public void setClassify(String paramString) {
    this.classify = paramString;
  }
  
  public String getClassifycn() {
    return this.classifycn;
  }
  
  public void setClassifycn(String paramString) {
    this.classifycn = paramString;
  }
  
  public String getNameWithID() {
    return this.name + "." + this.id + ".reu";
  }
  
  public Image getCover() {
    return this.cover;
  }
  
  public void setCover(Image paramImage) {
    this.cover = paramImage;
  }
  
  public void readXML(XMLableReader paramXMLableReader) {
    if (paramXMLableReader.isChildNode()) {
      String str = paramXMLableReader.getTagName();
      if ("id".equals(str)) {
        this.id = paramXMLableReader.getElementValue();
      } else if ("name".equals(str)) {
        this.name = paramXMLableReader.getElementValue();
      } else if ("vendor".equals(str)) {
        this.vendor = paramXMLableReader.getElementValue();
      } else if ("classify".equals(str)) {
        this.classify = paramXMLableReader.getElementValue();
      } else if ("classifyCN".equals(str)) {
        this.classifycn = paramXMLableReader.getElementValue();
      } else if ("tag".equals(str)) {
        this.tag = paramXMLableReader.getElementValue();
      } else if ("price".equals(str)) {
        this.price = paramXMLableReader.getElementValue();
      } else if ("guideInfo".equals(str)) {
        this.guideInfo = paramXMLableReader.getElementValue();
      } else if (paramXMLableReader.getTagName().equals("IM")) {
        Image image = GeneralXMLTools.readImage(paramXMLableReader);
        setCover(image);
      } 
    } 
  }
  
  public void writeXML(XMLPrintWriter paramXMLPrintWriter) {
    paramXMLPrintWriter.startTAG("id").textNode(this.id).end();
    paramXMLPrintWriter.startTAG("name").textNode(this.name).end();
    paramXMLPrintWriter.startTAG("classify").textNode(this.classify).end();
    paramXMLPrintWriter.startTAG("classifyCN").textNode(this.classifycn).end();
    paramXMLPrintWriter.startTAG("envVersion").textNode(this.envVersion).end();
    paramXMLPrintWriter.startTAG("vendor").textNode(this.vendor).end();
    paramXMLPrintWriter.startTAG("tag").textNode(this.tag).end();
    paramXMLPrintWriter.startTAG("price").textNode(this.price).end();
    paramXMLPrintWriter.startTAG("guideInfo").textNode(this.guideInfo).end();
    if (this.cover != null)
      GeneralXMLTools.writeImage(paramXMLPrintWriter, this.cover); 
  }
  
  public ElCaseBindInfo clone() throws CloneNotSupportedException {
    ElCaseBindInfo elCaseBindInfo = (ElCaseBindInfo)super.clone();
    elCaseBindInfo.id = this.id;
    elCaseBindInfo.name = this.name;
    elCaseBindInfo.guideInfo = this.guideInfo;
    elCaseBindInfo.classify = this.classify;
    elCaseBindInfo.classifycn = this.classifycn;
    elCaseBindInfo.tag = this.tag;
    elCaseBindInfo.price = this.price;
    elCaseBindInfo.vendor = this.vendor;
    return elCaseBindInfo;
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\for\\ui\ElCaseBindInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */