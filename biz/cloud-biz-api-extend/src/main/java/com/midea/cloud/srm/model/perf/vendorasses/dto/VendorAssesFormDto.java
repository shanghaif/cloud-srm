package com.midea.cloud.srm.model.perf.vendorasses.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *  供应商改善单表 前端控制器
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class VendorAssesFormDto implements Serializable {

    private static final long serialVersionUID = 1072492329480559657L;
    /**
     * 考核时间
     */
    @ExcelProperty( value = "考核时间",index = 0)
    private String assessmentDate;

    /**
     * 指标名称
     */
    @ExcelProperty( value = "指标名称",index = 1)
    private String indicatorName;

    /**
     * 评价结果
     */
    @ExcelProperty( value = "评价结果",index = 2)
    private String indicatorLineDes;

    /**
     * 实际考核金额(含税)
     */
    @ExcelProperty( value = "实际考核金额(含税)",index = 3)
    private String actualAssessmentAmountY;

    /**
     * 组织全路径
     */
    @ExcelProperty( value = "组织全路径",index = 4)
    private String organizationName;

    /**
     * 采购分类名称
     */
    @ExcelProperty( value = "采购分类名称",index = 5)
    private String categoryName;

    /**
     * 供应商名称
     */
    @ExcelProperty( value = "供应商名称",index = 6)
    private String vendorName;

    /**
     * 物料编码
     */
    @ExcelProperty( value = "物料编码",index = 7)
    private String materialCode;

    /**
     * 考核人账号
     */
    @ExcelProperty( value = "考核人账号",index = 8)
    private String respFullName;

    /**
     * 考核说明
     */
    @ExcelProperty( value = "考核说明",index = 9)
    private String explanation;

    /**
     * 导入错误信息反馈
     */
    @ExcelProperty( value = "导入错误反馈信息",index = 10)
    private String errorMsg;

}
