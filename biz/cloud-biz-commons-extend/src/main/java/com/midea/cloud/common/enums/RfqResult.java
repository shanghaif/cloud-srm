package com.midea.cloud.common.enums;

/**
 * 询价结果
 */
public enum RfqResult {

    SELECTED("SELECTED", "已选中"),
    CONTINUE_NEGOTIATE("CONTINUE_NEGOTIATE", "继续议价"),
    PRICE_APPROVAL_FORM("PRICE_APPROVAL_FORM", "已生成价格审批");

    private final String key;
    private final String name;

    RfqResult(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
