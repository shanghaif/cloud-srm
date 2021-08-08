package com.midea.cloud.srm.model.suppliercooperate.invoice.dto;

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
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/11
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class InvoiceDetailDto implements Serializable {
    /**
     * 库存组织名称
     */
    @ExcelProperty( value = "库存组织名称",index = 0)
    private String organizationName;

    /**
     * 组织地点(交货地点)
     */
    @ExcelProperty( value = "交货地点",index = 1)
    private String organizationSite;

    /**
     * 接收编号
     */
    @ExcelProperty( value = "接收编号",index = 2)
    private String receiveOrderNo;

    /**
     * 接收行号
     */
    @ExcelProperty( value = "接收行号",index = 3)
    private Integer receiveOrderLineNo;

    /**
     * 接收日期
     */
    @ExcelProperty( value = "接收日期",index = 4)
    private Date receiveDate;

    /**
     * 事务处理类型
     */
    @ExcelProperty( value = "事务类型",index = 5)
    private String type;

    /**
     * 采购订单号
     */
    @ExcelProperty( value = "采购订单号",index = 6)
    private String orderNumber;

    /**
     * 接收数量
     */
    @ExcelProperty( value = "接收数量",index = 7)
    private BigDecimal receiveNum;

    /**
     * 未开票数量
     */
    @ExcelProperty( value = "未开票数量",index = 8)
    private BigDecimal notInvoiceQuantity;

    /**
     * 本次开票数量
     */
    @ExcelProperty( value = "本次开票数量",index = 9)
    private BigDecimal invoiceQuantity;

    /**
     * 物料小类名称
     */
    @ExcelProperty( value = "物料小类名称",index = 10)
    private String categoryName;

    /**
     * 物料编码
     */
    @ExcelProperty( value = "物料编码",index = 11)
    private String itemCode;

    /**
     * 物料名称
     */
    @ExcelProperty( value = "物料名称",index = 12)
    private String itemName;

    /**
     * 单位
     */
    @ExcelProperty( value = "单位",index = 13)
    private String unit;

    /**
     * 单价（不含税）
     */
    @ExcelProperty( value = "未税单价",index = 14)
    private BigDecimal unitPriceExcludingTax;

    /**
     * 净价金额
     */
    @ExcelProperty( value = "净价金额",index = 15)
    private BigDecimal noTaxAmount;

    /**
     * 税率
     */
    @ExcelProperty( value = "税率",index = 16)
    private BigDecimal taxRate;

    /**
     * 税额
     */
    @ExcelProperty( value = "税额",index = 17)
    private BigDecimal tax;

    /**
     * 单价（含税）
     */
    @ExcelProperty( value = "含税单价",index = 18)
    private BigDecimal unitPriceContainingTax;

    /**
     * 含税金额
     */
    @ExcelProperty( value = "含税金额",index = 19)
    private BigDecimal taxAmount;

    /**
     * 合同编号
     */
    @ExcelProperty( value = "合同编号",index = 20)
    private String contractNo;

    /**
     * 项目编号
     */
    @ExcelProperty( value = "项目编号",index = 21)
    private String projectNum;

    /**
     * 项目名称
     */
    @ExcelProperty( value = "项目名称",index = 22)
    private String projectName;

    /**
     * 任务编号
     */
    @ExcelProperty( value = "任务编号",index = 23)
    private String taskNum;

    /**
     * 任务名称
     */
    @ExcelProperty( value = "任务名称",index = 24)
    private String taskName;
}
