package com.fr.form.main;

import com.fr.base.Env;
import com.fr.base.io.IOFile;
import com.fr.base.io.XMLEncryptUtils;
import com.fr.data.TableDataSource;
import com.fr.form.module.FormModule;
import com.fr.general.FRLogManager;
import com.fr.general.Inter;
import com.fr.general.ModuleContext;
import com.fr.script.Calculator;
import com.fr.stable.CoreGraphHelper;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

public class FormIO {
  public static Form readForm(Env paramEnv, String paramString) throws Exception {
    if (paramString == null)
      return null; 
    FRLogManager.declareResourceReadStart(paramString);
    ModuleContext.startModule(FormModule.class.getName());
    Form form = new Form();
    try {
      InputStream inputStream = paramEnv.readBean(paramString, "reportlets");
      if (inputStream == null)
        throw new FileNotFoundException(Inter.getLocText("Cannot_Found_Template_File") + ":" + paramString); 
      form.readStream(XMLEncryptUtils.decodeInputStream(inputStream));
      inputStream.close();
    } catch (Exception exception) {
      throw FRLogManager.createLogPackedException(exception);
    } finally {
      FRLogManager.declareResourceReadEnd();
    } 
    return form;
  }
  
  public static boolean writeForm(Env paramEnv, IOFile paramIOFile, String paramString) throws Exception {
    FRLogManager.declareResourceWriteStart(paramString);
    ModuleContext.startModule(FormModule.class.getName());
    try {
      OutputStream outputStream = paramEnv.writeBean(paramString, "reportlets");
      paramIOFile.export(outputStream);
      outputStream.flush();
      outputStream.close();
    } catch (Exception exception) {
      throw FRLogManager.createLogPackedException(exception);
    } finally {
      FRLogManager.declareResourceWriteEnd();
    } 
    return true;
  }
  
  public static BufferedImage exportFormAsImage(Form paramForm) {
    char c1 = 'Ѐ';
    char c2 = '̀';
    BufferedImage bufferedImage = CoreGraphHelper.createBufferedImage(c1, c2);
    Graphics2D graphics2D = bufferedImage.createGraphics();
    graphics2D.setColor(Color.WHITE);
    graphics2D.fillRect(0, 0, c1, c2);
    Calculator calculator = Calculator.createCalculator();
    calculator.setAttribute(TableDataSource.class, paramForm);
    paramForm.getContainer().toImage(calculator, new Rectangle(), graphics2D);
    return bufferedImage;
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\main\FormIO.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */