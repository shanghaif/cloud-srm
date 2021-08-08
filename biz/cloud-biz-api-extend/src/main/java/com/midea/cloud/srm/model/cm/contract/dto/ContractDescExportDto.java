package com.midea.cloud.srm.model.cm.contract.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class ContractDescExportDto implements Serializable {
    @ExcelProperty( value = "合同序号",index = 0)
    private String contractNo;

    @ExcelProperty( value = "合同编号",index = 1)
    private String contractCode;

    @ExcelProperty( value = "状态",index = 2) //字典
    private String contractStatus;

    @ExcelProperty( value = "已下单数量",index = 3)
    private BigDecimal ceeaUsedNumber;

    @ExcelProperty( value = "创建日期",index = 4)
    private Date creationDate;

    @ExcelProperty( value = "采购员",index = 5)
    private String createdBy;

    @ExcelProperty( value = "合同名称",index = 6)
    private String contractName;

    @ExcelProperty( value = "合同类型",index = 7) // 字典
    private String contractClass;

    @ExcelProperty( value = "模板名称",index = 8)
    private String modelName;

    @ExcelProperty( value = "供应商名称",index = 9)
    private String vendorName;

    @ExcelProperty( value = "是否总部",index = 10)
    private String isHeadquarters;

    @ExcelProperty( value = "合同有效期从",index = 11)
    private String dateTo;

    @ExcelProperty( value = "合同有效期至",index = 12)
    private String dateEnd;

    @ExcelProperty( value = "币种名称",index = 13)
    private String currencyName;

    @ExcelProperty( value = "合同级别",index = 14) // 字典
    private String contractLevel;

    @ExcelProperty( value = "送审主体业务实体",index = 15)
    private String buName;

    @ExcelProperty( value = "合同总金额(含税)",index = 16)
    private BigDecimal includeTaxAmount;

    @ExcelProperty( value = "合同备注",index = 17)
    private String contractRemark;

    @ExcelProperty( value = "合同行号",index = 18)
    private Integer no;

    @ExcelProperty( value = "寻源类型",index = 19) //字典
    private String sourceType;

    @ExcelProperty( value = "来源单号",index = 20)
    private String sourceNumber;

    @ExcelProperty( value = "业务实体",index = 21)
    private String lineBuName;

    @ExcelProperty( value = "库存组织",index = 22)
    private String invName;

    @ExcelProperty( value = "物料编码",index = 23)
    private String materialCode;

    @ExcelProperty( value = "物料名称",index = 24)
    private String materialName;

    @ExcelProperty( value = "物资小类",index = 25)
    private String categoryName1;

    @ExcelProperty( value = "物资中类",index = 26)
    private String categoryName2;

    @ExcelProperty( value = "物资大类",index = 27)
    private String categoryName3;

    @ExcelProperty( value = "数量",index = 28)
    private BigDecimal contractQuantity;

    @ExcelProperty( value = "单位",index = 29)
    private String unitName;

    @ExcelProperty( value = "含税单价",index = 30)
    private BigDecimal taxedPrice;

    @ExcelProperty( value = "含税金额",index = 31)
    private BigDecimal amount;

    @ExcelProperty( value = "未税金额",index = 32)
    private BigDecimal unAmount;

    @ExcelProperty( value = "税率编码",index = 33)
    private String taxKey;

    @ExcelProperty( value = "价格执行有效期自",index = 34)
    private String lineDateTo;

    @ExcelProperty( value = "价格执行有效期至",index = 35)
    private String lineDateEnd;

    @ExcelProperty( value = "质保期(月)",index = 36)
    private BigDecimal shelfLife;

    @ExcelProperty( value = "行备注",index = 37)
    private String lineRemark;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    @ExcelIgnore
    private LocalDate startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    @ExcelIgnore
    private LocalDate endDate;

    /**
     * 采购分类ID
     */
    @ExcelIgnore
    private Long categoryId;

    /**
     * 合同有效日期从
     */
    @ExcelIgnore
    private LocalDate effectiveDateFrom;

    /**
     * 合同有效日期至
     */
    @ExcelIgnore
    private LocalDate effectiveDateTo;

    /**
     * 合同头ID
     */
    @ExcelIgnore
    private Long contractHeadId;

}
