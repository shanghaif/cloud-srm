package com.midea.cloud.srm.model.inq.quote.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  可用报价行查询
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-24 17:08
 *  修改内容:
 * </pre>
 */
@Data
public class EffectiveQuoteItemsResult {

    /**
     * 报价行id
     */
    private Long quoteItemId;

    /**
     * 询价单号
     */
    private String inquiryNo;

    /**
     * 询价单标题
     */
    private String inquiryTitle;

    /**
     * 采购组织id
     */
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 邀请的供应商id
     */
    private Long inquiryVendorId;

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
     * 询价物料行id
     */
    private Long inquiryItemId;

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
     * 未税目标价
     */
    private String notaxTargrtPrice;

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
     * 未税单价
     */
    private BigDecimal notaxPrice;

    /**
     * 含税单价
     */
    private BigDecimal taxPrice;

    /**
     * 单位
     */
    private String unit;

    /**
     * 预计采购量
     */
    private BigDecimal demandQuantity;

    /**
     * 是否阶梯价
     */
    private String isLadder;

    /**
     * 阶梯价类型
     */
    private String ladderType;

    /**
     * 品类名称
     */
    private String categoryName;

    /**
     * 发布时间
     */
    private Date publishDate;

    /**
     * 截止时间
     */
    private Date deadline;

    /**
     * 定价开始时间
     */
    private Date fixedPriceBegin;

    /**
     * 定价结束时间
     */
    private Date fixedPriceEnd;

    /**
     * 备注
     */
    private String remark;
}