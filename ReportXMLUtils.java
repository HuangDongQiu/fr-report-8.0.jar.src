package com.fr.xml;

import com.fr.base.BaseObjectTokenizer;
import com.fr.base.BaseUtils;
import com.fr.base.ConfigManager;
import com.fr.base.DynamicUnitList;
import com.fr.base.EmailManager;
import com.fr.base.FRContext;
import com.fr.base.Formula;
import com.fr.base.Parameter;
import com.fr.base.ScreenResolution;
import com.fr.base.Style;
import com.fr.base.chart.BaseChartCollection;
import com.fr.base.chart.BaseChartPainter;
import com.fr.base.parameter.ParameterUI;
import com.fr.base.present.DictPresent;
import com.fr.base.present.FormulaPresent;
import com.fr.base.present.Present;
import com.fr.data.impl.TableColumn;
import com.fr.form.ui.Button;
import com.fr.form.ui.CellWidget;
import com.fr.form.ui.DataControl;
import com.fr.form.ui.FieldEditor;
import com.fr.form.ui.FreeButton;
import com.fr.form.ui.Label;
import com.fr.form.ui.Widget;
import com.fr.form.ui.WidgetValue;
import com.fr.form.ui.WidgetXmlUtils;
import com.fr.general.BaseObjectXMLWriterFinder;
import com.fr.general.ComparatorUtils;
import com.fr.general.GeneralUtils;
import com.fr.general.PageCalObj;
import com.fr.general.xml.GeneralXMLTools;
import com.fr.main.parameter.processor.FormulaUnitProcessor;
import com.fr.main.parameter.processor.SQLQueryUnitProcessor;
import com.fr.main.parameter.processor.UnitProcessor;
import com.fr.report.ExtraReportClassManager;
import com.fr.report.ReportHelper;
import com.fr.report.cell.CellElement;
import com.fr.report.cell.FloatElement;
import com.fr.report.cell.TemplateCellElement;
import com.fr.report.cell.cellattr.BarcodePresent;
import com.fr.report.cell.cellattr.DefaultPresent;
import com.fr.report.cell.cellattr.PageExportCellElement;
import com.fr.report.cell.cellattr.core.ResultSubReport;
import com.fr.report.cell.cellattr.core.RichText;
import com.fr.report.cell.cellattr.core.SubReport;
import com.fr.report.cell.cellattr.core.group.ConditionGroup;
import com.fr.report.cell.cellattr.core.group.CustomGrouper;
import com.fr.report.cell.cellattr.core.group.DSColumn;
import com.fr.report.cell.cellattr.core.group.FunctionGrouper;
import com.fr.report.cell.cellattr.core.group.IndexGrouper;
import com.fr.report.cell.cellattr.core.group.MonoGrouper;
import com.fr.report.cell.cellattr.core.group.RecordGrouper;
import com.fr.report.cell.cellattr.core.group.SummaryGrouper;
import com.fr.report.cell.cellattr.highlight.BackgroundHighlightAction;
import com.fr.report.cell.cellattr.highlight.BorderHighlightAction;
import com.fr.report.cell.cellattr.highlight.ColWidthHighlightAction;
import com.fr.report.cell.cellattr.highlight.DefaultHighlight;
import com.fr.report.cell.cellattr.highlight.FRFontHighlightAction;
import com.fr.report.cell.cellattr.highlight.ForegroundHighlightAction;
import com.fr.report.cell.cellattr.highlight.Highlight;
import com.fr.report.cell.cellattr.highlight.HighlightAction;
import com.fr.report.cell.cellattr.highlight.HyperlinkHighlightAction;
import com.fr.report.cell.cellattr.highlight.PaddingHighlightAction;
import com.fr.report.cell.cellattr.highlight.PageHighlightAction;
import com.fr.report.cell.cellattr.highlight.PresentHighlightAction;
import com.fr.report.cell.cellattr.highlight.RowHeightHighlightAction;
import com.fr.report.cell.cellattr.highlight.ValueHighlightAction;
import com.fr.report.cell.cellattr.highlight.WidgetHighlightAction;
import com.fr.report.cell.painter.BiasTextPainter;
import com.fr.report.cell.painter.shape.IssoscelesTriangleShapePainter;
import com.fr.report.cell.painter.shape.LineShapePainter;
import com.fr.report.cell.painter.shape.OvalShapePainter;
import com.fr.report.cell.painter.shape.RectangleShapePainter;
import com.fr.report.cell.painter.shape.RoundedRectangleShapePainter;
import com.fr.report.cell.painter.shape.ShapePainter;
import com.fr.report.cellcase.CellElementCaseGetter;
import com.fr.report.elementcase.ElementCase;
import com.fr.report.fun.ObjectMakeProvider;
import com.fr.report.report.Report;
import com.fr.report.stable.PolyBlockAttr;
import com.fr.report.web.button.form.FormResetButton;
import com.fr.report.worksheet.AnalysisRWorkSheet;
import com.fr.report.worksheet.PageRWorkSheet;
import com.fr.report.worksheet.WorkSheet;
import com.fr.stable.StringUtils;
import com.fr.stable.bridge.StableFactory;
import com.fr.stable.xml.FRFile;
import com.fr.stable.xml.ObjectXMLWriter;
import com.fr.stable.xml.XML;
import com.fr.stable.xml.XMLObject;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLReadable;
import com.fr.stable.xml.XMLWriter;
import com.fr.stable.xml.XMLable;
import com.fr.stable.xml.XMLableReader;
import com.fr.web.attr.ReportWebAttr;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ReportXMLUtils {
  private static Object tokenizeDetailObject(XMLableReader paramXMLableReader, String paramString1, String paramString2) {
    if (ComparatorUtils.equals("CC", paramString1)) {
      XMLable xMLable = StableFactory.createXmlObject("CC");
      if (xMLable != null)
        paramXMLableReader.readXMLObject((XMLReadable)xMLable); 
      return xMLable;
    } 
    if (ComparatorUtils.equals("CP", paramString1)) {
      XMLable xMLable = StableFactory.createXmlObject("CP");
      paramXMLableReader.readXMLObject((XMLReadable)xMLable);
      return xMLable;
    } 
    if (ComparatorUtils.equals("BiasTextPainter", paramString1)) {
      BiasTextPainter biasTextPainter = new BiasTextPainter("");
      paramXMLableReader.readXMLObject((XMLReadable)biasTextPainter);
      return biasTextPainter;
    } 
    if (isDSColumn(paramString1)) {
      DSColumn dSColumn = new DSColumn();
      paramXMLableReader.readXMLObject((XMLReadable)dSColumn);
      return dSColumn;
    } 
    if (ComparatorUtils.equals("SubReport", paramString1)) {
      SubReport subReport = new SubReport();
      paramXMLableReader.readXMLObject((XMLReadable)subReport);
      return subReport;
    } 
    if (ComparatorUtils.equals("ResultSubReport", paramString1)) {
      ResultSubReport resultSubReport = new ResultSubReport();
      paramXMLableReader.readXMLObject((XMLReadable)resultSubReport);
      return resultSubReport;
    } 
    if (ComparatorUtils.equals("RichText", paramString1)) {
      RichText richText = new RichText();
      paramXMLableReader.readXMLObject((XMLReadable)richText);
      return richText;
    } 
    if (ComparatorUtils.equals("TableColumn", paramString1)) {
      TableColumn tableColumn = new TableColumn();
      paramXMLableReader.readXMLObject((XMLReadable)tableColumn);
      return tableColumn;
    } 
    return "ShapePainter".equals(paramString1) ? tokenizeShapePainter(paramXMLableReader, paramString2) : ("Report".equals(paramString1) ? tokenizeSubReport(paramXMLableReader, paramString2) : ("PB".equals(paramString1) ? readOldPBElement(paramXMLableReader) : ("PE".equals(paramString1) ? readOldPEElement(paramXMLableReader) : ("PR".equals(paramString1) ? tokenizeParameterRef(paramXMLableReader) : readExtraObject(paramXMLableReader, paramString1)))));
  }
  
  private static Object readExtraObject(XMLableReader paramXMLableReader, String paramString) {
    Set set = ExtraReportClassManager.getInstance().getArray("ObjectMakeProvider");
    for (ObjectMakeProvider objectMakeProvider : set) {
      if (ComparatorUtils.equals(objectMakeProvider.xmlTag(), paramString)) {
        ObjectMakeProvider objectMakeProvider1 = objectMakeProvider.clone();
        paramXMLableReader.readXMLObject((XMLReadable)objectMakeProvider1);
        return objectMakeProvider1;
      } 
    } 
    return null;
  }
  
  private static boolean isDSColumn(String paramString) {
    return (ComparatorUtils.equals("DSColumn", paramString) || ComparatorUtils.equals(paramString, "LinearDSColumn") || ComparatorUtils.equals(paramString, "ComplexDSColumn"));
  }
  
  private static Object tokenizeShapePainter(XMLableReader paramXMLableReader, String paramString) {
    RectangleShapePainter rectangleShapePainter;
    ShapePainter shapePainter = null;
    if ((paramString = paramXMLableReader.getAttrAsString("class", null)) != null)
      try {
        shapePainter = GeneralUtils.classForName(paramString).newInstance();
      } catch (Exception exception) {
        rectangleShapePainter = new RectangleShapePainter();
        FRContext.getLogger().error(exception.getMessage(), exception);
      }  
    paramXMLableReader.readXMLObject((XMLReadable)rectangleShapePainter);
    return rectangleShapePainter;
  }
  
  private static Object tokenizeSubReport(XMLableReader paramXMLableReader, String paramString) {
    WorkSheet workSheet;
    Report report = null;
    if ((paramString = paramXMLableReader.getAttrAsString("class", null)) != null)
      try {
        report = GeneralUtils.classForName(paramString).newInstance();
      } catch (Exception exception) {
        workSheet = new WorkSheet();
        FRContext.getLogger().error(exception.getMessage(), exception);
      }  
    paramXMLableReader.readXMLObject((XMLReadable)workSheet);
    return workSheet;
  }
  
  private static Object tokenizeParameterRef(XMLableReader paramXMLableReader) {
    XMLObject xMLObject = new XMLObject() {
        public void readXML(XMLableReader param1XMLableReader) {
          String str;
          if (param1XMLableReader.isChildNode() && ComparatorUtils.equals(param1XMLableReader.getTagName(), "ParameterRef") && (str = param1XMLableReader.getAttrAsString("name", null)) != null)
            this.obj = SynchronizedNameWidget.get(str); 
        }
      };
    paramXMLableReader.readXMLObject((XMLReadable)xMLObject);
    return xMLObject.getObject();
  }
  
  public static Report readReportFromClassName(String paramString) {
    WorkSheet workSheet;
    if (paramString.endsWith(".WorkSheet")) {
      workSheet = new WorkSheet();
    } else if (paramString.endsWith(".CommonERReport")) {
      PageRWorkSheet pageRWorkSheet = new PageRWorkSheet();
    } else if (paramString.endsWith(".AnalysisRWorkSheet")) {
      AnalysisRWorkSheet analysisRWorkSheet = new AnalysisRWorkSheet();
    } else if (paramString.endsWith(".PageRWorkSheet")) {
      PageRWorkSheet pageRWorkSheet = new PageRWorkSheet();
    } else {
      try {
        Class<Report> clazz = GeneralUtils.classForName(paramString);
        Report report = clazz.newInstance();
      } catch (Exception exception) {
        FRContext.getLogger().error(exception.getMessage(), exception);
        workSheet = new WorkSheet();
      } 
    } 
    if (workSheet == null)
      workSheet = new WorkSheet(); 
    return (Report)workSheet;
  }
  
  public static Highlight readHighlight(XMLableReader paramXMLableReader) {
    Highlight highlight;
    DefaultHighlight defaultHighlight = null;
    String str;
    if ((str = paramXMLableReader.getAttrAsString("class", null)) != null)
      if (str.endsWith(".DefaultHighlight") || str.endsWith(".CellHighlight")) {
        defaultHighlight = new DefaultHighlight();
      } else {
        try {
          highlight = GeneralUtils.classForName(str).newInstance();
        } catch (Exception exception) {
          FRContext.getLogger().error(exception.getMessage(), exception);
        } 
      }  
    if (highlight == null)
      return highlight; 
    paramXMLableReader.readXMLObject((XMLReadable)highlight);
    return highlight;
  }
  
  public static HighlightAction readHighlightAction(XMLableReader paramXMLableReader) {
    HighlightAction highlightAction = null;
    String str = paramXMLableReader.getAttrAsString("class", null);
    if (str == null)
      return highlightAction; 
    if (str.endsWith(".ValueHighlightAction") || str.endsWith(".NewValueHighlightAction")) {
      ValueHighlightAction valueHighlightAction = new ValueHighlightAction();
    } else if (str.endsWith(".PresentHighlightAction")) {
      PresentHighlightAction presentHighlightAction = new PresentHighlightAction();
    } else if (str.endsWith(".ForegroundHighlightAction")) {
      ForegroundHighlightAction foregroundHighlightAction = new ForegroundHighlightAction();
    } else if (str.endsWith(".BackgroundHighlightAction")) {
      BackgroundHighlightAction backgroundHighlightAction = new BackgroundHighlightAction();
    } else if (str.endsWith(".RowHeightHighlightAction")) {
      RowHeightHighlightAction rowHeightHighlightAction = new RowHeightHighlightAction();
    } else if (str.endsWith(".ColWidthHighlightAction")) {
      ColWidthHighlightAction colWidthHighlightAction = new ColWidthHighlightAction();
    } else if (str.endsWith(".PageHighlightAction")) {
      PageHighlightAction pageHighlightAction = new PageHighlightAction();
    } else if (str.endsWith(".PaddingHighlightAction")) {
      PaddingHighlightAction paddingHighlightAction = new PaddingHighlightAction();
    } else if (str.endsWith(".FRFontHighlightAction")) {
      FRFontHighlightAction fRFontHighlightAction = new FRFontHighlightAction();
    } else if (str.endsWith(".HyperlinkHighlightAction")) {
      HyperlinkHighlightAction hyperlinkHighlightAction = new HyperlinkHighlightAction();
    } else if (str.endsWith(".BorderHighlightAction")) {
      BorderHighlightAction borderHighlightAction = new BorderHighlightAction();
    } else if (str.endsWith(".WidgetHighlightAction")) {
      WidgetHighlightAction widgetHighlightAction = new WidgetHighlightAction();
    } else {
      try {
        highlightAction = GeneralUtils.classForName(str).newInstance();
      } catch (InstantiationException instantiationException) {
        FRContext.getLogger().error(instantiationException.getMessage(), instantiationException);
      } catch (IllegalAccessException illegalAccessException) {
        FRContext.getLogger().error(illegalAccessException.getMessage(), illegalAccessException);
      } catch (ClassNotFoundException classNotFoundException) {
        FRContext.getLogger().error(classNotFoundException.getMessage(), classNotFoundException);
      } 
    } 
    if (highlightAction != null)
      paramXMLableReader.readXMLObject((XMLReadable)highlightAction); 
    return highlightAction;
  }
  
  public static void writeReportPageCellElement(XMLPrintWriter paramXMLPrintWriter, PageExportCellElement paramPageExportCellElement) {
    if (paramPageExportCellElement == null)
      return; 
    paramXMLPrintWriter.startTAG("C");
    writeCellCR(paramXMLPrintWriter, (CellElement)paramPageExportCellElement);
    paramPageExportCellElement.writeXML(paramXMLPrintWriter);
    paramXMLPrintWriter.end();
  }
  
  private static void writeCellCR(XMLPrintWriter paramXMLPrintWriter, CellElement paramCellElement) {
    paramXMLPrintWriter.attr("c", paramCellElement.getColumn()).attr("r", paramCellElement.getRow());
    if (paramCellElement.getColumnSpan() != 1)
      paramXMLPrintWriter.attr("cs", paramCellElement.getColumnSpan()); 
    if (paramCellElement.getRowSpan() != 1)
      paramXMLPrintWriter.attr("rs", paramCellElement.getRowSpan()); 
  }
  
  private static void writeCellCRAndStyle(XMLPrintWriter paramXMLPrintWriter, CellElement paramCellElement) {
    writeCellCR(paramXMLPrintWriter, paramCellElement);
    if (paramCellElement.getStyle() != null && !ComparatorUtils.equals(paramCellElement.getStyle(), Style.DEFAULT_STYLE)) {
      SynchronizedStyleList synchronizedStyleList = SynchronizedStyleList.getSynchronizedStyleList();
      paramXMLPrintWriter.attr("s", synchronizedStyleList.indexOfStyle(paramCellElement.getStyle()));
    } 
  }
  
  public static void writeCellElement(XMLPrintWriter paramXMLPrintWriter, CellElement paramCellElement) {
    if (paramCellElement == null)
      return; 
    paramXMLPrintWriter.startTAG("C");
    writeCellCRAndStyle(paramXMLPrintWriter, paramCellElement);
    paramCellElement.writeXML(paramXMLPrintWriter);
    paramXMLPrintWriter.end();
  }
  
  public static void writeCellElementWithCommonResultAttr(XMLPrintWriter paramXMLPrintWriter, CellElement paramCellElement) {
    if (paramCellElement == null)
      return; 
    paramXMLPrintWriter.startTAG("C");
    writeCellCRAndStyle(paramXMLPrintWriter, paramCellElement);
    paramCellElement.writeCommonResultAttrXML(paramXMLPrintWriter);
    paramXMLPrintWriter.end();
  }
  
  public static Button readOldPBElement(XMLableReader paramXMLableReader) {
    final ArrayList<Button> list = new ArrayList();
    paramXMLableReader.readXMLObject(new XMLReadable() {
          public void readXML(XMLableReader param1XMLableReader) {
            String str;
            if (param1XMLableReader.isChildNode() && param1XMLableReader.getTagName().equals("ParameterButton") && StringUtils.isNotBlank(str = param1XMLableReader.getAttrAsString("type", null)))
              if (str.equals("0")) {
                FreeButton freeButton = (FreeButton)StableFactory.getMarkedInstanceObjectFromClass("SubmitButton", FreeButton.class);
                if (freeButton != null)
                  list.add(freeButton); 
              } else if (str.equals("1")) {
                list.add(new FormResetButton(param1XMLableReader.getAttrAsString("name", null)));
              }  
          }
        });
    return arrayList.isEmpty() ? null : arrayList.get(0);
  }
  
  public static FieldEditor readOldPEElement(XMLableReader paramXMLableReader) {
    final ArrayList<ParameterEditor> list = new ArrayList();
    paramXMLableReader.readXMLObject(new XMLReadable() {
          public void readXML(XMLableReader param1XMLableReader) {
            if (param1XMLableReader.isChildNode() && param1XMLableReader.getTagName().equals("ParameterEditor")) {
              ReportXMLUtils.ParameterEditor parameterEditor = new ReportXMLUtils.ParameterEditor();
              param1XMLableReader.readXMLObject(parameterEditor);
              list.add(parameterEditor);
            } 
          }
        });
    return arrayList.isEmpty() ? null : ((ParameterEditor)arrayList.get(0)).toFieldEditor();
  }
  
  public static void writeIndexStyle(XMLPrintWriter paramXMLPrintWriter, Style paramStyle) {
    if (paramStyle == null)
      return; 
    SynchronizedStyleList synchronizedStyleList = SynchronizedStyleList.getSynchronizedStyleList();
    paramXMLPrintWriter.startTAG("Style").attr("index", synchronizedStyleList.indexOfStyle(paramStyle)).end();
  }
  
  public static void writeFloatElement(XMLPrintWriter paramXMLPrintWriter, FloatElement paramFloatElement) {
    if (paramFloatElement == null)
      return; 
    paramXMLPrintWriter.startTAG(paramFloatElement.getClass().getName());
    paramFloatElement.writeXML(paramXMLPrintWriter);
    paramXMLPrintWriter.end();
  }
  
  public static void readFloatElementList(XMLableReader paramXMLableReader, final List floatElementList, CellElementCaseGetter paramCellElementCaseGetter) {
    paramXMLableReader.setContextAttribute(CellElementCaseGetter.class, paramCellElementCaseGetter);
    paramXMLableReader.readXMLObject(new XMLReadable() {
          public void readXML(XMLableReader param1XMLableReader) {
            if (param1XMLableReader.isChildNode()) {
              String str = param1XMLableReader.getTagName();
              Object object = null;
              if (str.equals("ShapeFloatElement")) {
                String str1;
                if ((str1 = param1XMLableReader.getAttrAsString("class", null)) != null)
                  if (str1.endsWith(".IssoscelesTriangleShapeFloatElement")) {
                    object = new IssoscelesTriangleShapePainter();
                  } else if (str1.endsWith(".LineShapeFloatElement")) {
                    LineShapePainter lineShapePainter = new LineShapePainter();
                  } else if (str1.endsWith(".OvalShapeFloatElement")) {
                    OvalShapePainter ovalShapePainter = new OvalShapePainter();
                  } else if (str1.endsWith(".RectangleShapeFloatElement")) {
                    RectangleShapePainter rectangleShapePainter = new RectangleShapePainter();
                  } else if (str1.endsWith(".RoundedRectangleShapeFloatElement")) {
                    RoundedRectangleShapePainter roundedRectangleShapePainter = new RoundedRectangleShapePainter();
                  }  
              } else if (str.equals("ImageFloatElement")) {
                object = ((XMLObject)param1XMLableReader.readXMLObject((XMLReadable)new XMLObject() {
                      public void readXML(XMLableReader param2XMLableReader) {
                        if (param2XMLableReader.isChildNode())
                          if (param2XMLableReader.getTagName().equals("IM")) {
                            this.obj = GeneralXMLTools.readImage(param2XMLableReader);
                          } else if ("Image".equals(param2XMLableReader.getTagName())) {
                            this.obj = GeneralXMLTools.deprecatedReadImage(param2XMLableReader);
                          }  
                      }
                    })).getObject();
              } 
              if (object != null && object instanceof com.fr.base.Painter)
                param1XMLableReader.readXMLObject((XMLReadable)object); 
              FloatElement floatElement = new FloatElement();
              floatElement.setValue(object);
              param1XMLableReader.readXMLObject((XMLReadable)floatElement);
              floatElementList.add(floatElement);
            } 
          }
        });
    paramXMLableReader.removeContextAttribute(CellElementCaseGetter.class);
  }
  
  public static ParameterUI readParameterUI(XMLableReader paramXMLableReader) {
    String str = paramXMLableReader.getAttrAsString("class", null);
    ParameterUI parameterUI = null;
    if (str != null) {
      if ("com.fr.main.parameter.UI.CustomParameterUI".equals(str)) {
        str = "com.fr.form.main.parameter.FormParameterUI";
      } else if ("com.fr.main.parameter.UI.FormParameterUI".equals("")) {
        str = "com.fr.form.main.parameter.FormParameterUI";
      } 
      if (str.endsWith(".FormParameterUI")) {
        parameterUI = (ParameterUI)StableFactory.getMarkedInstanceObjectFromClass("FormParameterUI", ParameterUI.class);
        paramXMLableReader.readXMLObject((XMLReadable)parameterUI);
        if (!paramXMLableReader.getXMLVersion().isAfterREPORT_REFECT_FOR65_XML_VERSION());
        parameterUI.checkContainer();
      } else if (isCustomParameterUI(str)) {
        WorkSheet workSheet = new WorkSheet();
        paramXMLableReader.readXMLObject((XMLReadable)workSheet);
        Dimension dimension = new Dimension();
        CellWidget[] arrayOfCellWidget = getCellWidgets(workSheet, dimension);
        parameterUI = (ParameterUI)StableFactory.getMarkedInstanceObjectFromClass("FormParameterUI", ParameterUI.class);
        parameterUI.setCellWidgets(arrayOfCellWidget);
        parameterUI.setDesignSize(dimension);
      } else {
        try {
          parameterUI = (ParameterUI)StableFactory.getMarkedInstanceObjectFromClass("FormParameterUI", ParameterUI.class);
        } catch (Exception exception) {
          FRContext.getLogger().error(exception.getMessage());
        } 
      } 
    } 
    return parameterUI;
  }
  
  public static void writeParameterUI(XMLPrintWriter paramXMLPrintWriter, ParameterUI paramParameterUI) {
    if (paramParameterUI == null)
      return; 
    paramXMLPrintWriter.startTAG("ParameterUI");
    paramXMLPrintWriter.attr("class", paramParameterUI.getClass().getName());
    paramParameterUI.writeXML(paramXMLPrintWriter);
    paramXMLPrintWriter.end();
  }
  
  private static CellWidget[] getCellWidgets(WorkSheet paramWorkSheet, Dimension paramDimension) {
    ArrayList arrayList = new ArrayList();
    DynamicUnitList dynamicUnitList1 = ReportHelper.createColumnWidthList((CellElementCaseGetter)paramWorkSheet);
    DynamicUnitList dynamicUnitList2 = ReportHelper.createRowHeightList((CellElementCaseGetter)paramWorkSheet);
    int i = ScreenResolution.getScreenResolution();
    int j = ReportHelper.getTotalColumnWidth((ElementCase)paramWorkSheet).toPixI(i);
    Iterator<TemplateCellElement> iterator = paramWorkSheet.cellIterator();
    byte b1 = 0;
    byte b2 = 0;
    while (iterator.hasNext()) {
      TemplateCellElement templateCellElement = iterator.next();
      Style style = templateCellElement.getStyle();
      Object object = templateCellElement.getValue();
      Widget widget = templateCellElement.getWidget();
      if ((object != null && StringUtils.isNotEmpty(object.toString())) || widget != null || style.getBackground() != null) {
        b1++;
        int m = templateCellElement.getColumn();
        int n = templateCellElement.getColumnSpan();
        int i1 = templateCellElement.getRow();
        int i2 = templateCellElement.getRowSpan();
        int i3 = dynamicUnitList1.getRangeValueFromZero(m).toPixI(i);
        int i4 = dynamicUnitList2.getRangeValueFromZero(i1).toPixI(i);
        int i5 = dynamicUnitList1.getRangeValue(m, m + n).toPixI(i);
        int i6 = dynamicUnitList2.getRangeValue(i1, i1 + i2).toPixI(i);
        b2 = (i6 == 19) ? 2 : 0;
        Rectangle rectangle = new Rectangle(i3, i4, i5, i6);
        dealWithWidgets(arrayList, widget, object, style, rectangle, b1);
      } 
    } 
    int k = ReportHelper.getTotalRowHeight((ElementCase)paramWorkSheet).toPixI(i);
    k += b2;
    paramDimension.height = k;
    paramDimension.width = (j <= 800) ? 800 : j;
    return (CellWidget[])arrayList.toArray((Object[])new CellWidget[arrayList.size()]);
  }
  
  private static void dealWithWidgets(List<CellWidget> paramList, Widget paramWidget, Object paramObject, Style paramStyle, Rectangle paramRectangle, int paramInt) {
    if (paramWidget == null) {
      Label label = new Label();
      label.setWidgetName("label" + paramInt);
      if (paramObject != null && StringUtils.isNotEmpty(paramObject.toString())) {
        WidgetValue.convertWidgetValue((DataControl)label, paramObject);
        label.setFont(paramStyle.getFRFont());
        int i = BaseUtils.getAlignment4Horizontal(paramStyle);
        label.setTextalign((i == -1) ? 2 : i);
        label.setVerticalCenter(true);
      } 
      paramList.add(new CellWidget((Widget)label, paramStyle, paramRectangle, paramObject));
    } else {
      if (StringUtils.isEmpty(paramWidget.getWidgetName()))
        paramWidget.setWidgetName("widget" + paramInt); 
      if (paramObject != null && StringUtils.isNotEmpty(paramObject.toString()))
        if (paramWidget instanceof DataControl) {
          WidgetValue.convertWidgetValue((DataControl)paramWidget, paramObject);
        } else {
          try {
            Method method = paramWidget.getClass().getMethod("setText", new Class[] { String.class });
            method.invoke(paramWidget, new Object[] { paramObject.toString() });
          } catch (Exception exception) {}
        }  
      paramList.add(new CellWidget(paramWidget, paramStyle, paramRectangle, paramObject));
    } 
  }
  
  private static boolean isCustomParameterUI(String paramString) {
    return (paramString.endsWith(".CustomParameterUI") || paramString.endsWith(".CustomParameterInterface") || paramString.endsWith(".CustomEmbeddedParameterInterface"));
  }
  
  public static UnitProcessor readUnitProcessor(XMLableReader paramXMLableReader) {
    SQLQueryUnitProcessor sQLQueryUnitProcessor = null;
    String str;
    if ((str = paramXMLableReader.getAttrAsString("class", null)) != null)
      if (str.endsWith(".SQLQueryUnitProcessor")) {
        sQLQueryUnitProcessor = new SQLQueryUnitProcessor();
      } else if (str.endsWith(".FormulaUnitProcessor")) {
        FormulaUnitProcessor formulaUnitProcessor = new FormulaUnitProcessor();
      } else {
        try {
          UnitProcessor unitProcessor = GeneralUtils.classForName(str).newInstance();
        } catch (Exception exception) {
          sQLQueryUnitProcessor = null;
        } 
      }  
    if (sQLQueryUnitProcessor instanceof XMLable)
      paramXMLableReader.readXMLObject((XMLReadable)sQLQueryUnitProcessor); 
    return (UnitProcessor)sQLQueryUnitProcessor;
  }
  
  public static void writeUnitProcessor(XMLPrintWriter paramXMLPrintWriter, UnitProcessor paramUnitProcessor) {
    paramXMLPrintWriter.startTAG("UP").attr("class", paramUnitProcessor.getClass().getName());
    if (paramUnitProcessor instanceof XMLable)
      ((XMLable)paramUnitProcessor).writeXML(paramXMLPrintWriter); 
    paramXMLPrintWriter.end();
  }
  
  public static Formula readFormula(XMLableReader paramXMLableReader) {
    Formula formula = null;
    String str;
    if ((str = paramXMLableReader.getAttrAsString("class", null)) != null)
      try {
        Class<Formula> clazz = GeneralUtils.classForName(str);
        formula = clazz.newInstance();
        paramXMLableReader.readXMLObject((XMLReadable)formula);
      } catch (Exception exception) {
        FRContext.getLogger().error(exception.getMessage(), exception);
      }  
    return formula;
  }
  
  public static Present readPresent(XMLableReader paramXMLableReader) {
    Present present;
    DictPresent dictPresent = null;
    String str;
    if ((str = paramXMLableReader.getAttrAsString("class", null)) != null) {
      if (str.endsWith(".DictPresent")) {
        dictPresent = new DictPresent();
      } else if (str.endsWith(".BarcodePresent")) {
        BarcodePresent barcodePresent = new BarcodePresent();
      } else if (str.endsWith(".FormulaPresent")) {
        FormulaPresent formulaPresent = new FormulaPresent();
      } else if (str.endsWith(".DefaultPresent")) {
        DefaultPresent defaultPresent = new DefaultPresent();
      } else {
        try {
          if (str.startsWith("com.fr.report.cellElement"))
            str = str.replaceFirst("com.fr.report.cellElement", "com.fr.report.cell.cellattr"); 
          if (str.startsWith("com.fr.report.cellattr"))
            str = str.replaceFirst("com.fr.report.cellattr", "com.fr.report.cell.cellattr"); 
          Class<Present> clazz = GeneralUtils.classForName(str);
          present = clazz.newInstance();
        } catch (Exception exception) {
          FRContext.getLogger().error(exception.getMessage(), exception);
        } 
      } 
      if (present != null)
        paramXMLableReader.readXMLObject((XMLReadable)present); 
    } 
    return present;
  }
  
  public static void writeRectangle(XMLPrintWriter paramXMLPrintWriter, Rectangle paramRectangle, String paramString) {
    if (paramRectangle == null)
      return; 
    paramXMLPrintWriter.startTAG(paramString).attr("x", paramRectangle.x).attr("y", paramRectangle.y).attr("w", paramRectangle.width).attr("h", paramRectangle.height).end();
  }
  
  public static Rectangle readRectangle(XMLableReader paramXMLableReader) {
    return new Rectangle(paramXMLableReader.getAttrAsInt("x", 0), paramXMLableReader.getAttrAsInt("y", 0), paramXMLableReader.getAttrAsInt("w", 0), paramXMLableReader.getAttrAsInt("h", 0));
  }
  
  public static RecordGrouper readXMLRecordGrouper(XMLableReader paramXMLableReader) {
    RecordGrouper recordGrouper;
    String str1 = paramXMLableReader.getTagName();
    if ("GC".equals(str1))
      return readGroupCalculator2RecordGrouper(paramXMLableReader); 
    if ("SpecifiedGroupAttr".equals(str1))
      return readGA2RecordGrouper(paramXMLableReader); 
    FunctionGrouper functionGrouper = null;
    String str2;
    if ((str2 = paramXMLableReader.getAttrAsString("class", null)) != null) {
      if (str2.endsWith(".ValueGrouper")) {
        functionGrouper = new FunctionGrouper();
      } else if (str2.endsWith(".IndexGrouper")) {
        IndexGrouper indexGrouper = new IndexGrouper();
      } else if (str2.endsWith(".MonoGrouper")) {
        MonoGrouper monoGrouper = new MonoGrouper();
      } else if (str2.endsWith(".SummaryGrouper")) {
        SummaryGrouper summaryGrouper = new SummaryGrouper();
      } else if (str2.endsWith(".CustomGrouper")) {
        CustomGrouper customGrouper = new CustomGrouper();
      } else if (str2.endsWith(".FunctionGrouper")) {
        functionGrouper = new FunctionGrouper();
      } else {
        try {
          if (str2.startsWith("com.fr.report.cellElement"))
            str2 = str2.replaceAll("com.fr.report.cellElement.core", "com.fr.report.cell.cellattr.core.group"); 
          recordGrouper = GeneralUtils.classForName(str2).newInstance();
        } catch (Exception exception) {
          FRContext.getLogger().error(exception.getMessage(), exception);
        } 
      } 
      paramXMLableReader.readXMLObject((XMLReadable)recordGrouper);
    } 
    return recordGrouper;
  }
  
  private static RecordGrouper readGroupCalculator2RecordGrouper(XMLableReader paramXMLableReader) {
    FunctionGrouper functionGrouper;
    RecordGrouper recordGrouper = null;
    String str = paramXMLableReader.getAttrAsString("class", null);
    if (str.endsWith(".DefaultGroupCalculator")) {
      XMLObject xMLObject;
      paramXMLableReader.readXMLObject((XMLReadable)(xMLObject = new XMLObject() {
            public void readXML(XMLableReader param1XMLableReader) {
              if (param1XMLableReader.isChildNode()) {
                String str = param1XMLableReader.getTagName();
                if ("GA".equals(str) || "SpecifiedGroupAttr".equals(str))
                  this.obj = ReportXMLUtils.readGA2RecordGrouper(param1XMLableReader); 
              } 
            }
          }));
      recordGrouper = (RecordGrouper)xMLObject.getObject();
    } else if (str.endsWith(".FormulaGroupCalculator")) {
      String str1 = paramXMLableReader.getElementValue();
      if (str1 != null && str1.length() > 0) {
        functionGrouper = new FunctionGrouper();
        functionGrouper.setFormulaContent(str1);
      } 
    } 
    return (RecordGrouper)functionGrouper;
  }
  
  private static RecordGrouper readGA2RecordGrouper(XMLableReader paramXMLableReader) {
    CustomGrouper customGrouper = new CustomGrouper();
    String str;
    if ((str = paramXMLableReader.getAttrAsString("other", null)) != null)
      customGrouper.setOther(Integer.parseInt(str)); 
    if ((str = paramXMLableReader.getAttrAsString("odisplay", null)) != null)
      customGrouper.setOdisplay(str); 
    if ((str = paramXMLableReader.getAttrAsString("force", null)) != null)
      customGrouper.setForce(Boolean.valueOf(str).booleanValue()); 
    final ArrayList g_list = new ArrayList();
    paramXMLableReader.readXMLObject(new XMLReadable() {
          public void readXML(XMLableReader param1XMLableReader) {
            if (param1XMLableReader.isChildNode() && param1XMLableReader.getTagName().equals("ConditionListGroup")) {
              ConditionGroup conditionGroup = new ConditionGroup();
              param1XMLableReader.readXMLObject((XMLReadable)conditionGroup);
              g_list.add(conditionGroup);
            } 
          }
        });
    customGrouper.setConditionGroups((ConditionGroup[])arrayList.toArray((Object[])new ConditionGroup[arrayList.size()]));
    return (RecordGrouper)customGrouper;
  }
  
  public static void writeXMLPloyBlockAttr(XMLPrintWriter paramXMLPrintWriter, PolyBlockAttr paramPolyBlockAttr) {
    if (paramPolyBlockAttr == null)
      return; 
    paramXMLPrintWriter.startTAG("PolyBlockAttr");
    paramXMLPrintWriter.attr("class", paramPolyBlockAttr.getClass().getName());
    paramPolyBlockAttr.writeXML(paramXMLPrintWriter);
    paramXMLPrintWriter.end();
  }
  
  public static PolyBlockAttr readXMLPolyBlockAttr(XMLableReader paramXMLableReader) {
    String str = paramXMLableReader.getAttrAsString("class", null);
    if ("com.fr.report.poly.PolyBlockAttr".equals(str))
      str = "com.fr.report.stable.PolyBlockAttr"; 
    PolyBlockAttr polyBlockAttr = null;
    if (StringUtils.isNotEmpty(str))
      try {
        polyBlockAttr = GeneralUtils.classForName(str).newInstance();
        polyBlockAttr.readXML(paramXMLableReader);
      } catch (InstantiationException instantiationException) {
        FRContext.getLogger().error(instantiationException.getMessage(), instantiationException);
      } catch (IllegalAccessException illegalAccessException) {
        FRContext.getLogger().error(illegalAccessException.getMessage(), illegalAccessException);
      } catch (ClassNotFoundException classNotFoundException) {
        FRContext.getLogger().error(classNotFoundException.getMessage(), classNotFoundException);
      }  
    return polyBlockAttr;
  }
  
  private static class REPORT extends XML {
    REPORT(Report param1Report) {
      this.xml = (XMLWriter)param1Report;
    }
    
    public String type() {
      return "Report";
    }
    
    public String className() {
      return this.xml.getClass().getName();
    }
  }
  
  private static class SP extends XML {
    SP(ShapePainter param1ShapePainter) {
      this.xml = (XMLWriter)param1ShapePainter;
    }
    
    public String type() {
      return "ShapePainter";
    }
    
    public String className() {
      return this.xml.getClass().getName();
    }
  }
  
  private static class TABLECOLUMN extends XML {
    TABLECOLUMN(TableColumn param1TableColumn) {
      this.xml = (XMLWriter)param1TableColumn;
    }
    
    public String type() {
      return "TableColumn";
    }
  }
  
  private static class RESULTSUBREPORT extends XML {
    RESULTSUBREPORT(ResultSubReport param1ResultSubReport) {
      this.xml = (XMLWriter)param1ResultSubReport;
    }
    
    public String type() {
      return "ResultSubReport";
    }
  }
  
  private static class RICHTEXT extends XML {
    RICHTEXT(RichText param1RichText) {
      this.xml = (XMLWriter)param1RichText;
    }
    
    public String type() {
      return "RichText";
    }
  }
  
  private static class SUBREPORT extends XML {
    SUBREPORT(SubReport param1SubReport) {
      this.xml = (XMLWriter)param1SubReport;
    }
    
    public String type() {
      return "SubReport";
    }
  }
  
  private static class DSCOLUMN extends XML {
    DSCOLUMN(DSColumn param1DSColumn) {
      this.xml = (XMLWriter)param1DSColumn;
    }
    
    public String type() {
      return "DSColumn";
    }
  }
  
  private static class BTP extends XML {
    BTP(BiasTextPainter param1BiasTextPainter) {
      this.xml = (XMLWriter)param1BiasTextPainter;
    }
    
    public String type() {
      return "BiasTextPainter";
    }
  }
  
  private static class CP extends XML {
    CP(BaseChartPainter param1BaseChartPainter) {
      this.xml = (XMLWriter)param1BaseChartPainter;
    }
    
    public String type() {
      return "CP";
    }
  }
  
  private static class CC extends XML {
    CC(BaseChartCollection param1BaseChartCollection) {
      this.xml = (XMLWriter)param1BaseChartCollection;
    }
    
    public String type() {
      return "CC";
    }
  }
  
  private static class XMLABLE extends XML {
    public XMLABLE(XMLable param1XMLable) {
      this.xml = (XMLWriter)param1XMLable;
    }
    
    public String type() {
      return "XMLable";
    }
    
    public String className() {
      return this.xml.getClass().getName();
    }
  }
  
  private static class FRFILEXML extends XML {
    public FRFILEXML(FRFile param1FRFile) {
      this.xml = (XMLWriter)param1FRFile;
    }
    
    public String type() {
      return "FRF";
    }
  }
  
  private static class PAGECALOBJXML extends XML {
    public PAGECALOBJXML(PageCalObj param1PageCalObj) {
      this.xml = (XMLWriter)param1PageCalObj;
    }
    
    public String type() {
      return "PCOBJ";
    }
  }
  
  public static class ReportObjectXMLWriterFinder extends BaseObjectXMLWriterFinder {
    public ObjectXMLWriter as(Object param1Object) {
      ObjectXMLWriter objectXMLWriter = super.as(param1Object);
      if (objectXMLWriter instanceof BaseObjectXMLWriterFinder.ElseAsString) {
        Set set = ExtraReportClassManager.getInstance().getArray("ObjectMakeProvider");
        for (ObjectMakeProvider objectMakeProvider : set) {
          if (objectMakeProvider.accept(param1Object))
            return (ObjectXMLWriter)objectMakeProvider.asXMLObject(param1Object); 
        } 
        return (ObjectXMLWriter)((param1Object instanceof BaseChartCollection) ? new ReportXMLUtils.CC((BaseChartCollection)param1Object) : ((param1Object instanceof BaseChartPainter) ? new ReportXMLUtils.CP((BaseChartPainter)param1Object) : ((param1Object instanceof BiasTextPainter) ? new ReportXMLUtils.BTP((BiasTextPainter)param1Object) : ((param1Object instanceof DSColumn) ? new ReportXMLUtils.DSCOLUMN((DSColumn)param1Object) : ((param1Object instanceof SubReport) ? new ReportXMLUtils.SUBREPORT((SubReport)param1Object) : ((param1Object instanceof ResultSubReport) ? new ReportXMLUtils.RESULTSUBREPORT((ResultSubReport)param1Object) : ((param1Object instanceof ShapePainter) ? new ReportXMLUtils.SP((ShapePainter)param1Object) : ((param1Object instanceof Report) ? new ReportXMLUtils.REPORT((Report)param1Object) : ((param1Object instanceof TableColumn) ? new ReportXMLUtils.TABLECOLUMN((TableColumn)param1Object) : ((param1Object instanceof PageCalObj) ? new ReportXMLUtils.PAGECALOBJXML((PageCalObj)param1Object) : ((param1Object instanceof FRFile) ? new ReportXMLUtils.FRFILEXML((FRFile)param1Object) : ((param1Object instanceof RichText) ? new ReportXMLUtils.RICHTEXT((RichText)param1Object) : ((param1Object instanceof XMLable) ? new ReportXMLUtils.XMLABLE((XMLable)param1Object) : new BaseObjectXMLWriterFinder.ElseAsString(param1Object))))))))))))));
      } 
      return objectXMLWriter;
    }
  }
  
  private static final class ParameterEditor implements XMLReadable {
    private String name;
    
    private FieldEditor editor;
    
    private Object defaultValue;
    
    public void readXML(XMLableReader param1XMLableReader) {
      if (param1XMLableReader.isChildNode()) {
        String str = param1XMLableReader.getTagName();
        if ("CellEditorDef".equals(str)) {
          this.editor = WidgetXmlUtils.readFieldEditor(param1XMLableReader);
        } else if ("O".equals(str)) {
          this.defaultValue = GeneralXMLTools.readObject(param1XMLableReader);
        } else if ("Name".equals(str)) {
          this.name = param1XMLableReader.getElementValue();
        } 
      } 
    }
    
    public FieldEditor toFieldEditor() {
      if (this.editor != null)
        this.editor.setWidgetName(this.name); 
      return this.editor;
    }
  }
  
  private static class CompateEmailManagerInReportWebAttrWhen652 implements XMLReadable {
    private ReportWebAttr attr;
    
    private ConfigManager configManager;
    
    CompateEmailManagerInReportWebAttrWhen652(ConfigManager param1ConfigManager, ReportWebAttr param1ReportWebAttr) {
      this.configManager = param1ConfigManager;
      this.attr = param1ReportWebAttr;
    }
    
    public void readXML(XMLableReader param1XMLableReader) {
      this.attr.readXML(param1XMLableReader);
      if (param1XMLableReader.isChildNode() && "EmailManager".equals(param1XMLableReader.getTagName())) {
        EmailManager emailManager = new EmailManager();
        param1XMLableReader.readXMLObject((XMLReadable)emailManager);
        this.configManager.setEmailManager(emailManager);
      } 
    }
  }
  
  public static class ReportObjectTokenizer extends BaseObjectTokenizer {
    public Object tokenizerObject(XMLableReader param1XMLableReader, boolean param1Boolean, String param1String, ThreadLocal<Parameter> param1ThreadLocal) {
      String str = null;
      Object object = super.tokenizerObject(param1XMLableReader, param1Boolean, param1String, param1ThreadLocal);
      if (param1ThreadLocal != null) {
        if (param1ThreadLocal.get() instanceof Parameter) {
          if ("Widget".equals(param1String) || "CellEditorDef".equals(param1String)) {
            Parameter parameter = param1ThreadLocal.get();
            FieldEditor fieldEditor = WidgetXmlUtils.readFieldEditor(param1XMLableReader);
            SynchronizedNameWidget.put(parameter.getName(), fieldEditor);
            SynchronizedNameWidget.put(parameter.getName(), parameter.getValue());
            return fieldEditor;
          } 
        } else if (param1ThreadLocal.get() instanceof ConfigManager && "ReportWebAttr".equals(param1String)) {
          ReportWebAttr reportWebAttr = new ReportWebAttr();
          ConfigManager configManager = (ConfigManager)param1ThreadLocal.get();
          configManager.putGlobalAttribute(ReportWebAttr.class, reportWebAttr);
          param1XMLableReader.readXMLObject(new ReportXMLUtils.CompateEmailManagerInReportWebAttrWhen652(configManager, reportWebAttr));
        } 
        param1ThreadLocal.set(null);
      } 
      return (object != null) ? object : ReportXMLUtils.tokenizeDetailObject(param1XMLableReader, param1String, str);
    }
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\xml\ReportXMLUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */