package com.midea.cloud.srm.model.inq.price.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  报价评选查询结果
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-8 17:03
 *  修改内容:
 * </pre>
 */
@Data
public class QuoteSelectedResult {

    /**
     * 报价行id
     */
    private Long quoteItemId;

    /**
     * 询价类型
     */
    private String inquiryType;

    /**
     * 询价单id
     */
    private Long inquiryId;

    /**
     * 询价单号
     */
    private String inquiryNo;

    /**
     * 询价标题
     */
    private String inquiryTitle;

    /**
     * 供应商id
     */
    private Long vendorId;

    /**
     * 供应商编码
     */
    private String vendorCode;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 组织名称
     */
    private String organizationName;

    /**
     * 物料id
     */
    private Long itemId;

    /**
     * 物料编码
     */
    private String itemCode;

    /**
     * 物料描述
     */
    private String itemDesc;

    /**
     * 行类型（价格类型）
     */
    private String itemType;

    /**
     * 品类id
     */
    private Long categoryId;

    /**
     * 品类名称
     */
    private String categoryName;

    /**
     * 单位
     */
    private String unit;

    /**
     * 未税单价
     */
    private BigDecimal notaxPrice;

    /**
     * 含税单价
     */
    private BigDecimal taxPrice;

    /**
     * 现价
     */
    private BigDecimal currentPrice;

    /**
     * 税率编码
     */
    private String taxKey;

    /**
     * 税率
     */
    private String taxRate;

    /**
     * 币种
     */
    private String currency;

    /**
     * 定价开始时间
     */
    private Date fixedPriceBegin;

    /**
     * 定价结束时间
     */
    private Date fixedPriceEnd;

    /**
     * 报价精度
     */
    private String priceNum;

    /**
     * 是否阶梯价
     */
    private String isLadder;

    /**
     * 阶梯价类型
     */
    private String ladderType;

}