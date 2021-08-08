package com.midea.cloud.srm.model.supplier.info.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * <pre>
 *  功能名称 产品导入模型DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/21 17:26
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class ProductModelDTO {

    @ExcelProperty(value = "新SRM供应商ID", index = 0)
    @NotEmpty(message = "新SRM供应商ID不能为空")
    private String companyId;

    @ExcelProperty(value = "老SRM供应商编码（C开头的）", index = 1)
    private String companyCode;

    @ExcelProperty(value = "ERP供应商ID", index = 2)
    private String erpVendorId;

    @ExcelProperty(value = "供应商ERP编码", index = 3)
    private String erpVendorCode;

    @ExcelProperty(value = "企业名称", index = 4)
    private String companyName;

    @ExcelProperty(value = "生产基地", index = 5)
    private String proBase;

    @ExcelProperty(value = "产品名称/型号", index = 6)
    private String proName;

    @ExcelProperty(value = "产品品牌", index = 7)
    private String proBrand;

    @ExcelProperty(value = "主要工艺", index = 8)
    private String mainTechnics;

    @ExcelProperty(value = "年产量", index = 9)
    private String yearOutput;

    @ExcelProperty(value = "可提供给隆基的供应产能比例%", index = 10)
    private String supplyCapacityRate;

    @ExcelProperty(value = "产品合格率（%）", index = 11)
    private String proQualifiedRate;

    @ExcelProperty(value = "年营业额（万元）", index = 12)
    private String yearTurnover;

    @ExcelProperty( value = "错误提示信息",index = 13)
    private String errorMessage;
}
