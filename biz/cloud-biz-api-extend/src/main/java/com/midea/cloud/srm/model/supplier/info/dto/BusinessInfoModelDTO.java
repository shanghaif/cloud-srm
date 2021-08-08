 package com.midea.cloud.srm.model.supplier.info.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * <pre>
 *  功能名称 客户情况导入模型DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/21 17:35
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class BusinessInfoModelDTO {

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

    @ExcelProperty(value = "客户名称", index = 5)
    private String customer;

    @ExcelProperty(value = "合作频率", index = 6)
    private String ceeaWorkFrequency;

    @ExcelProperty(value = "所属区域", index = 7)
    private String ceeaArea;

    @ExcelProperty(value = "销售数量", index = 8)
    private String preSalesVol;

    @ExcelProperty(value = "销售额（万元）", index = 9)
    private String preSalesAmount;

    @ExcelProperty(value = "备注", index = 10)
    private String ceeaComment;

    @ExcelProperty( value = "错误提示信息",index = 11)
    private String errorMessage;
}
