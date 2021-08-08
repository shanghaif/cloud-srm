package com.midea.cloud.srm.model.base.dict.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@ColumnWidth(15) //列宽
public class ExportExcelDictDTOLeft {
    private static final long serialVersionUID = 543212L;
    /**
     * ID
     */
    @ExcelIgnore
    private Long dictId;

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
     * 字典语言
     */
    @ExcelProperty(value = "字典语言",index =3)
    private String language;

    /**
     * 字典语言名称
     */
    @ExcelProperty(value = "*字典语言名称",index =4)
    private String languageName;

    /**
     * 管控角色
     */
    @ExcelProperty(value = "管控角色",index =5)
    private String dictRole;

    /**
     * 管控角色名称
     */
    @ExcelProperty(value = "管控角色名称",index =6)
    private String dictRoleName;

    /**
     * 生效日期
     */
    @ExcelProperty(value = "*生效日期",index =7)
    private Date activeDate;

    /**
     * 失效时间
     */
    @ExcelProperty(value = "失效时间",index =8)
    private Date inactiveDate;


    /**
     *
     */
    @ExcelProperty( value = "错误信息提示",index =9)
    private String errorMsg;
}
