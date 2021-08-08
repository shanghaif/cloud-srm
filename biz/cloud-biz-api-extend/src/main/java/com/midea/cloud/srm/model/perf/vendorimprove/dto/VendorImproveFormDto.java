package com.midea.cloud.srm.model.perf.vendorimprove.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

/**
 *  <pre>
 *  供应商改善单表 dto
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class VendorImproveFormDto implements Serializable {

    private static final long serialVersionUID = 7202711704845453651L;
    /**
     * 改善开始日期
     */
    @ExcelProperty( value = "改善开始日期",index = 0)
    private String improveDateStart;

    /**
     * 改善结束日期
     */
    @ExcelProperty( value = "改善结束日期",index = 1)
    private String improveDateEnd;

    /**
     * 改善主题
     */
    @ExcelProperty( value = "改善主题",index = 2)
    private String improveTitle;

    /**
     * 改善主项目
     */
    @ExcelProperty( value = "改善项目",index = 3)
    private String improveProject;

    /**
     * 责任跟进人账号
     */
    @ExcelProperty( value = "责任跟进人账号",index = 4)
    private String respUserName;

    /**
     * 供应商编码
     */
    @ExcelProperty( value = "供应商编码",index = 5)
    private String vendorCode;

    /**
     * 采购组织名称
     */
    @ExcelProperty( value = "采购组织名称",index = 6)
    private String organizationName;

    /**
     * 采购分类名称
     */
    @ExcelProperty( value = "采购分类名称",index = 7)
    private String categoryName;

    /**
     * 改善说明
     */
    @ExcelProperty( value = "改善说明",index = 8)
    private String explanation;

    /**
     * 导入错误信息反馈
     */
    @ExcelProperty( value = "导入错误信息反馈",index = 9)
    private String errorMsg;

}
