package com.midea.cloud.srm.model.inq.quote.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.supplier.change.dto.QuoteLadderPriceDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-24 16:58
 *  修改内容:
 * </pre>
 */
@Data
public class QuoteSelectionQueryResponseDTO {

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
    private BigDecimal notaxTargrtPrice;

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
     * 目标总价（未税）
     */
    private BigDecimal notaxTargrtPriceTotal;

    /**
     * 未税单价
     */
    private BigDecimal notaxPrice;

    /**
     * 含税单价
     */
    private BigDecimal taxPrice;

    /**
     * 总价（未税）
     */
    private BigDecimal notaxPriceTotal;

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

    /**
     * 现价
     */
    private BigDecimal currentPrice;

    /**
     * 报价差价
     */
    private BigDecimal quotePriceDifference;

    /**
     * 现价差价
     */
    private BigDecimal currentPriceDifference;

    /**
     * 价格得分
     */
    private BigDecimal priceScore;

    /**
     * 综合得分
     */
    private BigDecimal compositeScore;

    /**
     * 品质得分
     */
    private BigDecimal qualityScore;

    /**
     * 评选排名
     */
    private Integer ranking;

    /**
     * 是否选定
     */
    private String isSelected;

    /**
     * 阶梯价
     */
    List<QuoteLadderPriceDTO> quoteLadderPrices;
}