package com.midea.cloud.srm.model.inq.quote.dto;

import lombok.Data;

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
 *  修改日期: 2020-4-13 11:04
 *  修改内容:
 * </pre>
 */
@Data
public class QuoteTrackingQueryResponseDTO {

    /**
     * 询价单号
     */
    private String inquiryNo;

    /**
     * 询价标题
     */
    private String inquiryTitle;

    /**
     * 报价方式
     */
    private String quoteRule;

    /**
     * 评分方式
     */
    private String inquiryRule;

    /**
     * 发布时间
     */
    private Date publishDate;

    /**
     * 报价开始时间
     */
    private Date beginQuote;

    /**
     * 截止时间
     */
    private Date deadline;

    /**
     * 状态
     */
    private String status;

    /**
     * 审批状态
     */
    private String auditStatus;

    /**
     * 报价保留位数
     */
    private String priceNum;

    /**
     * 跟踪列表
     */
    private List<QuoteTrackingItemDTO> trackingItems;

    /**
     * 目标价是否加密
     */
    Boolean targetPriceEncrypt;

    /**
     * 是否有绩效评分
     */
    Boolean hasPerformanceScore;

    /**
     * 是否有绩效评分
     */
    Boolean hasVendorN;
}