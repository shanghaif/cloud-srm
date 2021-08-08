package com.midea.cloud.srm.model.inq.quote.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <pre>
 *  报价评选导出
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-24 16:16
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class QuoteSelectionExcelVO {

    /**
     * 询价单号
     */
    @ExcelProperty(value = "询价单号")
    private String inquiryNo;

    /**
     * 询价单标题
     */
    @ExcelProperty(value = "询价单标题")
    private String inquiryTitle;

    /**
     * 供应商编码
     */
    @ExcelProperty(value = "供应商编码")
    private String vendorCode;

    /**
     * 供应商名称
     */
    @ExcelProperty(value = "供应商名称")
    private String vendorName;

    /**
     * 品类名称
     */
    @ExcelProperty(value = "品类名称")
    private String categoryName;

    /**
     * 物料编码
     */
    @ExcelProperty(value = "物料编码")
    private String itemCode;

    /**
     * 物料描述
     */
    @ExcelProperty(value = "物料描述")
    private String itemDesc;

    /**
     * 单位
     */
    @ExcelProperty(value = "单位")
    private String unit;

    /**
     * 预计采购量
     */
    @ExcelProperty(value = "预计采购量")
    private BigDecimal demandQuantity;

    /**
     * 报价差价
     */
    @ExcelProperty(value = "报价差价")
    private BigDecimal quotePriceDifference;

    /**
     * 税率
     */
    @ExcelProperty(value = "税率")
    private String taxRate;

    /**
     * 未税目标价
     */
    @ExcelProperty(value = "未税目标价")
    private BigDecimal notaxTargrtPrice;

    /**
     * 目标总价（未税）
     */
    @ExcelProperty(value = "目标总价（未税）")
    private BigDecimal notaxTargrtPriceTotal;

    /**
     * 现价差价
     */
    @ExcelProperty(value = "现价差价")
    private BigDecimal currentPriceDifference;

    /**
     * 未税单价
     */
    @ExcelProperty(value = "未税单价")
    private BigDecimal notaxPrice;

    /**
     * 总价（未税）
     */
    @ExcelProperty(value = "总价（未税）")
    private BigDecimal notaxPriceTotal;

    /**
     * 币种
     */
    @ExcelProperty(value = "币种")
    private String currency;

    /**
     * 综合得分
     */
    @ExcelProperty(value = "综合得分")
    private BigDecimal compositeScore;

    /**
     * 价格得分
     */
    @ExcelProperty(value = "价格得分")
    private BigDecimal priceScore;

    /**
     * 品质得分
     */
    @ExcelProperty(value = "品质得分")
    private BigDecimal qualityScore;

    /**
     * 是否选定
     */
    @ExcelProperty(value = "是否选定")
    private String isSelected;

    /**
     * 评选排名
     */
    @ExcelProperty(value = "评选排名")
    private Integer ranking;

    /**
     * 定价开始时间
     */
    @ExcelProperty(value = "定价开始时间")
    private String fixedPriceBegin;

    /**
     * 定价结束时间
     */
    @ExcelProperty(value = "定价结束时间")
    private String fixedPriceEnd;

}