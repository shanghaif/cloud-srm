package com.midea.cloud.srm.model.inq.quote.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <pre>
 *  报价行绩效查询
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-3 15:27
 *  修改内容:
 * </pre>
 */
@Data
public class QuoteItemPerformanceQueryResult {

    /**
     * 评分行id
     */
    private Long itemPerformanceId;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 询价单号
     */
    private String inquiryNo;

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
     * 评分项
     */
    private String scoreItem;

    /**
     * 评分规则
     */
    private String scoreRule;

    /**
     * 满分
     */
    private BigDecimal fullScore;

    /**
     * 评分
     */
    private BigDecimal score;
}