package com.midea.cloud.srm.model.supplier.change.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-23 13:58
 *  修改内容:
 * </pre>
 */
@Data
public class QuoteTrackingItemsResponseDTO {

    /**
     * 物料编码
     */
    private String itemCode;

    /**
     * 物料描述
     */
    private String itemDesc;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 单位
     */
    private String unit;

    /**
     * 采购分类
     */
    private String categoryName;

    /**
     * 未税单价
     */
    private BigDecimal notaxPrice;

    /**
     * 含税单价
     */
    private BigDecimal taxPrice;

    /**
     * 总金额
     */
    private BigDecimal total;

    /**
     * 预计采购数量
     */
    private BigDecimal demandQuantity;

    /**
     * 税率编码
     */
    private String taxKey;

    /**
     * 税率
     */
    private String taxRate;

    /**
     * 是否阶梯价
     */
    private String isLadder;

    /**
     * 阶梯价类型
     */
    private String ladderType;

    /**
     * 价格类型
     */
    private String itemType;

    /**
     * 总价
     */
    private BigDecimal totalAmount;

    /**
     * 报价结果
     */
    private String quoteResult;

    /**
     * 阶梯价
     */
    private List<QuoteLadderPriceDTO> quoteLadderPrices;
}