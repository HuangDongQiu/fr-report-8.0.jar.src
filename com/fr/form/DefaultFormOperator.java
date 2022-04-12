package com.fr.form;

import com.fr.base.BaseUtils;
import com.fr.base.Env;
import com.fr.base.io.IOFile;
import com.fr.form.main.FormIO;
import com.fr.form.ui.container.WAbsoluteLayout;
import com.fr.form.ui.container.WBorderLayout;
import com.fr.form.ui.container.WCardLayout;
import com.fr.form.ui.container.WGridLayout;
import com.fr.form.ui.container.WHorizontalBoxLayout;
import com.fr.form.ui.container.WHorizontalSplitLayout;
import com.fr.form.ui.container.WVerticalBoxLayout;
import com.fr.form.ui.container.WVerticalSplitLayout;
import java.util.HashMap;
import java.util.Map;

public class DefaultFormOperator implements FormOperator {
  public Map getFormIconMap() {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put(WBorderLayout.class, BaseUtils.readIcon("/com/fr/web/images/form/resources/layout_border.png"));
    hashMap.put(WHorizontalBoxLayout.class, BaseUtils.readIcon("/com/fr/web/images/form/resources/boxlayout_h_16.png"));
    hashMap.put(WGridLayout.class, BaseUtils.readIcon("/com/fr/web/images/form/resources/layout_grid.png"));
    hashMap.put(WCardLayout.class, BaseUtils.readIcon("/com/fr/web/images/form/resources/card_layout_16.png"));
    hashMap.put(WAbsoluteLayout.class, BaseUtils.readIcon("/com/fr/web/images/form/resources/layout_absolute_new.png"));
    hashMap.put(WHorizontalSplitLayout.class, BaseUtils.readIcon("/com/fr/web/images/form/resources/split_pane_16.png"));
    hashMap.put(WVerticalSplitLayout.class, BaseUtils.readIcon("/com/fr/web/images/form/resources/separator_16.png"));
    hashMap.put(WVerticalBoxLayout.class, BaseUtils.readIcon("/com/fr/web/images/form/resources/boxlayout_v_16.png"));
    return hashMap;
  }
  
  public boolean writeForm(Env paramEnv, IOFile paramIOFile, String paramString) throws Exception {
    return FormIO.writeForm(paramEnv, paramIOFile, paramString);
  }
}


/* Location:              C:\Users\Administrator\Downloads\fr-report-8.0.jar!\com\fr\form\DefaultFormOperator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */