package com.midea.cloud.common.enums;

public enum PriceApprovalStatus {
    DRAFT("拟定","DRAFT"),
    RESULT_NOT_APPROVED("结果未审批", "RESULT_NOT_APPROVED"),
    RESULT_REJECTED("结果已驳回", "RESULT_REJECTED"),
    WITHDRAW("已撤回", "WITHDRAW"),
    ABANDONED("已废弃", "ABANDONED"),
    RESULT_PASSED("结果已审批", "RESULT_PASSED");

    private final String name;
    private final String value;

    private PriceApprovalStatus(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }
}
