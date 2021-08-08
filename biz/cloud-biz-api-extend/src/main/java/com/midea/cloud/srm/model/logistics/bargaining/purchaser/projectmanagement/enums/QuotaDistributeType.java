package com.midea.cloud.srm.model.logistics.bargaining.purchaser.projectmanagement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 配额分配类型
 */
@Getter
@AllArgsConstructor
public enum QuotaDistributeType {
    CATEGORY_ALLOCATION("CATEGORY_ALLOCATION", "按品类金额分配"),
    NUMBER_ALLOCATION("NUMBER_ALLOCATION", "按数量分配"),
    NULL_ALLOCATION("NULL_ALLOCATION", "无分配");

    private final String code;
    private final String name;
}
