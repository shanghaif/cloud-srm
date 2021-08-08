package com.midea.cloud.common.enums.pm.pr.requirement;

import lombok.Data;

public enum RelatedDocumentsEnum {
    TENDER("TENDER","招投标"),
    ENQUIRY("ENQUIRY","询价"),
    BIDDING("BIDDING","竞价"),
    PURCHASE("PURCHASE","采购订单");
    private String name;
    private String value;
    RelatedDocumentsEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
