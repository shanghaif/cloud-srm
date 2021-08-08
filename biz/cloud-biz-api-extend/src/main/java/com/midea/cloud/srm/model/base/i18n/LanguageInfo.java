package com.midea.cloud.srm.model.base.i18n;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@ColumnWidth(45)
public class LanguageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "多语言键[开发定义]", index = 0)
    private String key;

    @ExcelProperty(value = "中文[顾问翻译]", index = 1)
    private String zh_value;

    @ExcelProperty(value = "英文[顾问翻译]", index = 2)
    private String en_value;

    @ExcelProperty(value = "日文[顾问翻译]", index = 3)
    private String ja_value;

}

