package com.midea.cloud.srm.model.inq.inquiry.dto;

import lombok.Data;

@Data
public class QuoteQueryDto {
    /**
     * 询价单号
     */
    private String inquiryNo;

    /**
     * 询价标题
     */
    private String inquiryTitle;
    /**
     * 询价标题
     */
    private String itemCode;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 组织id
     */
    private String organizationName;

    /**
     * 状态
     */
    private String status;

    /**
     * 状态
     */
    private String inquiryStatus;

    /**
     * 审批状态
     */
    private String auditStatus;

    /**
     * 供应商id
     */
    private Long vendorId;

    private Integer pageNum; // 页码
    private Integer pageSize; // 页数
    private String searchUrl; // 记录跳转url，如果有必要
    private Boolean isNeedTotal; // 是否需要统计总数
}
