package com.midea.cloud.common.enums.pm.po;

import org.apache.commons.lang3.StringUtils;

/**
 * 付款方式
 */
public enum CeeaPaymentWayEnum {
    WIRE_TRANSFER("电汇","WIRE_TRANSFER"),
    ACCEPTANCE("承兑","ACCEPTANCE");

    private String name;
    private String value;

    CeeaPaymentWayEnum(String name, String value) {
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

    public static CeeaPaymentWayEnum get(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (CeeaPaymentWayEnum e : CeeaPaymentWayEnum.values()) {
                if (e.getValue().equals(value)) {
                    return e;
                }
            }
        }
        return null;
    }

}
