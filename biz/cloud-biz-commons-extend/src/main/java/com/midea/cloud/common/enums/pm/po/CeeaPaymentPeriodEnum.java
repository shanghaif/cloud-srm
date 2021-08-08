package com.midea.cloud.common.enums.pm.po;

import org.apache.commons.lang3.StringUtils;

/**
 * 付款账期
 */
public enum CeeaPaymentPeriodEnum {
    A("90天付款","A");

    private String name;
    private String value;

    CeeaPaymentPeriodEnum(String name, String value) {
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

    public static CeeaPaymentPeriodEnum get(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (CeeaPaymentPeriodEnum e : CeeaPaymentPeriodEnum.values()) {
                if (e.getValue().equals(value)) {
                    return e;
                }
            }
        }
        return null;
    }
}
