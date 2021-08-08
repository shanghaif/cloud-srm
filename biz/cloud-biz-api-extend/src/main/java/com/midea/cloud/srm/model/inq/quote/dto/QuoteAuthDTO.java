package com.midea.cloud.srm.model.inq.quote.dto;

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
 *  修改日期: 2020-4-20 17:58
 *  修改内容:
 * </pre>
 */
@Data
public class QuoteAuthDTO {

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
     * 品类名称
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
     * 是否禁止报价
     */
    private String quoteForbid;
}