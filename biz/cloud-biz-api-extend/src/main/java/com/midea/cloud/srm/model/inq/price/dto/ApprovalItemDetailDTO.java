package com.midea.cloud.srm.model.inq.price.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
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
 *  修改日期: 2020-4-9 17:44
 *  修改内容:
 * </pre>
 */
@Data
public class ApprovalItemDetailDTO {

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
     * 单位
     */
    private String unit;

    /**
     * 未税现价
     */
    private BigDecimal notaxCurrentPrice;

    /**
     * 未税选定单价
     */
    private BigDecimal notaxSelectedPrice;

    /**
     * 含税单价
     */
    private BigDecimal taxPrice;

    /**
     * 税率编码
     */
    private String taxKey;

    /**
     * 税率
     */
    private String taxRate;

    /**
     * 定价开始时间
     */
    private Date fixedPriceBegin;

    /**
     * 定价结束时间
     */
    private Date fixedPriceEnd;

    /**
     * 是否阶梯价
     */
    private String isLadder;

    /**
     * 阶梯价类型
     */
    private String ladderType;

    /**
     * 品类id
     */
    private Long categoryId;

    /**
     * 品类名称
     */
    private String categoryName;

    /**
     * 币种
     */
    private String currency;

    /**
     * 行类型（价格类型）
     */
    private String itemType;

    /**
     * 审批行阶梯价
     */
    private List<ApprovalLadderPriceDTO> ladderPrices;
}