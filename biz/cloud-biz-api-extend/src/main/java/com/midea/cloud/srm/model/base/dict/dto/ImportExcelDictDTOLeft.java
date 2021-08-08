package com.midea.cloud.srm.model.base.dict.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@ColumnWidth(15) //列宽
public class ImportExcelDictDTOLeft {
    private static final long serialVersionUID = 8212341L;
    /**
     * 字典编码
     */
    @ExcelProperty(value = "*字典编码",index =0)
    private String dictCode;

    /**
     * 字典名称
     */
    @ExcelProperty(value = "*字典名称",index =1)
    private String dictName;

    /**
     * 字典描述
     */
    @ExcelProperty(value = "字典描述",index =2)
    private String description;

    /**
     * 字典语言名称
     */
    @ExcelProperty(value = "*字典语言名称",index =3)
    private String languageName;
    /**
     * 字典语言
     */
    @ExcelIgnore
    private String language;
    /**
     * 生效日期
     */
    @ExcelProperty(value = "*生效日期",index =4)
    private Date activeDate;

    /**
     * 失效时间
     */
    @ExcelProperty(value = "失效时间",index =5)
    private Date inactiveDate;

    /**
     *
     */
    @ExcelProperty( value = "错误信息提示",index =6)
    private String errorMsg;
}

