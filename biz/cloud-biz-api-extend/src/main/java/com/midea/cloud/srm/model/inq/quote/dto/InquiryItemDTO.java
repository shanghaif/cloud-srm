package com.midea.cloud.srm.model.inq.quote.dto;

import com.baomidou.mybatisplus.annotation.TableField;
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
 *  修改日期: 2020-4-20 15:52
 *  修改内容:
 * </pre>
 */
@Data
public class InquiryItemDTO {

    /**
     * 询价行id
     */
    private Long inquiryItemId;

    /**
     * 询价单头id
     */
    private Long inquiryId;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 物料id
     */
    private Long itemId;

    /**
     * 物料编码
     */
    private String itemCode;

    /**
     * 物料名称
     */
    private String itemDesc;

    /**
     * 品类ID
     */
    private Long categoryId;

    /**
     * 品类名称
     */
    private String categoryName;

    /**
     * 需求数量
     */
    private BigDecimal demandQuantity;

    /**
     * 规格
     */
    private String specification;

    /**
     * 单位
     */
    private String unit;

    /**
     * 是否阶梯价
     */
    private String isLadder;

    /**
     * 阶梯价类型
     */
    private String ladderType;

    /**
     * 行类型
     */
    private String itemType;

    /**
     * 行类型
     */
    private String currency;

    /**
     * 需求计划日期
     */
    private BigDecimal planDay;

    /**
     * 订单数量
     */
    private BigDecimal orderQuantity;

    /**
     * 未税目标价
     */
    private String notaxTargrtPrice;

    /**
     * 未税单价
     */
    private BigDecimal notaxPrice;

    /**
     * 定价开始日期
     */
    private Date fixedPriceBegin;

    /**
     * 定价结束日期
     */
    private Date fixedPriceEnd;

    /**
     * 阶梯价
     */
    private List<InquiryLadderPriceDTO> ladderPrices;
}