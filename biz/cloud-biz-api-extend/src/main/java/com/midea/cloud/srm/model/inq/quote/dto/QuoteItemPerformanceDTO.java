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
 *  修改日期: 2020-4-7 9:11
 *  修改内容:
 * </pre>
 */
@Data
public class QuoteItemPerformanceDTO {

    /**
     * 评分行id
     */
    private Long itemPerformanceId;

    /**
     * 组织id
     */
    private Long ForganizationId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

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
     * 评分项目编码
     */
    private String scoreItemCode;

    /**
     * 评分项目名称
     */
    private String scoreItemName;

    /**
     * 评分规则
     */
    private String scoreRule;

    /**
     * 满分值
     */
    private BigDecimal fullScore;

    /**
     * 得分
     */
    private BigDecimal score;
}