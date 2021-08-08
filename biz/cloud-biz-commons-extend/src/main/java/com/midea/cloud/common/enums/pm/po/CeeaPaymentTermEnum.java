package com.midea.cloud.common.enums.pm.po;

import org.apache.commons.lang3.StringUtils;

/**
 * 付款条件
 */
public enum CeeaPaymentTermEnum {
    A("货物到货后并收到乙方发票后","A");

    private String name;
    private String value;

    CeeaPaymentTermEnum(String name, String value) {
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

    public static CeeaPaymentTermEnum get(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (CeeaPaymentTermEnum e : CeeaPaymentTermEnum.values()) {
                if (e.getValue().equals(value)) {
                    return e;
                }
            }
        }
        return null;
    }

}
