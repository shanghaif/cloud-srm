package com.midea.cloud.common.enums.pm.po;

import org.apache.commons.lang3.StringUtils;

/**
 * 付款阶段枚举
 */
public enum CeeaPaymentStageEnum {
    ADVANCE_CHARGE("预付款","ADVANCE_CHARGE"),
    ARRIVAL_PAYMENT("到货款","ARRIVAL_PAYMENT"),
    ACCEPTANCE_PAYMENT("验收款","ACCEPTANCE_PAYMENT"),
    QUALITY_DEPOSIT("质保金","QUALITY_DEPOSIT");

    private String name;
    private String value;

    CeeaPaymentStageEnum(String name, String value) {
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

    public static CeeaPaymentStageEnum get(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (CeeaPaymentStageEnum e : CeeaPaymentStageEnum.values()) {
                if (e.getValue().equals(value)) {
                    return e;
                }
            }
        }
        return null;
    }

}
