package com.midea.cloud.srm.model.inq.inquiry.dto;

import lombok.Data;

import java.math.BigDecimal;

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
 *  修改日期: 2020-4-7 17:10
 *  修改内容:
 * </pre>
 */
@Data
public class ItemTargetPriceResponseDTO {

    /**
     * 寻价行id
     */
    private Long inquiryItemId;

    /**
     * 物料编码
     */
    private String itemCode;

    /**
     * 物料名称
     */
    private String itemDesc;

    /**
     * 品类名称
     */
    private String categoryName;

    /**
     * 行类型
     */
    private String itemType;

    /**
     * 需求数量
     */
    private BigDecimal demandQuantity;

    /**
     * 单位
     */
    private String unit;

    /**
     * 税率编码
     */
    private String taxKey;

    /**
     * 税率
     */
    private String taxRate;

    /**
     * 未税目标价
     */
    private String notaxTargrtPrice;

    /**
     * 币种
     */
    private String currency;
}