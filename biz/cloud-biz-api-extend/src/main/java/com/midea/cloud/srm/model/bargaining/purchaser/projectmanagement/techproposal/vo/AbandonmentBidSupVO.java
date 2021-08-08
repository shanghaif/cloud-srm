package com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 弃标供应商信息
 */
@Getter
@Setter
public class AbandonmentBidSupVO {

    /**
     * 采购组织
     */
    private String orgName;
    /**
     * 物料描述
     */
    private String targetDesc;
    /**
     * 物料编码
     */
    private String targetNum;
    /**
     * 弃标供应商
     */
    private String supplierName;
    /**
     *物料组（组合）
     */
    private String itemGroup;
}
