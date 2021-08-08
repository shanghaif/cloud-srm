package com.midea.cloud.srm.model.cm.contract.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录：
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class ContractListExportDto implements Serializable {

    @ExcelProperty( value = "合同序号",index = 0)
    private String contractNo;

    @ExcelProperty( value = "合同编号",index = 1)
    private String contractCode;

    @ExcelProperty( value = "价格审批单号",index = 2)
    private String sourceNumber;

    @ExcelProperty( value = "合同名称",index = 3)
    private String contractName;

    @ExcelProperty( value = "合同类型",index = 4)
    private String contractClass;

    @ExcelProperty( value = "操作类型",index = 5)
    private String contractType;

    @ExcelProperty( value = "供应商编码",index = 6)
    private String vendorCode;

    @ExcelProperty( value = "供应商名称",index = 7)
    private String vendorName;

    @ExcelProperty( value = "合同状态",index = 8)
    private String contractStatus;

    @ExcelProperty( value = "送审业务实体",index = 9)
    private String buName;

    @ExcelProperty( value = "合同级别",index = 10)
    private String contractLevel;

    @ExcelProperty( value = "总金额",index = 11)
    private BigDecimal includeTaxAmount;

    @ExcelProperty( value = "币种",index = 12)
    private String currencyName;

    // 转下格式
    @ExcelProperty( value = "审结日期",index = 13)
    private String startDate;

    // 转下格式
    @ExcelProperty( value = "有效日期从",index = 14)
    private String effectiveDateFrom;

    // 转下格式
    @ExcelProperty( value = "有效日期至",index = 15)
    private String effectiveDateTo;

    @ExcelProperty( value = "创建时间",index = 16)
    private Date creationDate;

    @ExcelProperty( value = "创建人",index = 17)
    private String createdBy;
}
