package com.midea.cloud.common.enums.pm.po;

import org.apache.commons.lang3.StringUtils;

public enum CeeaWarehousingReturnDetailEnum {
    RECEIVE("采购接收","RECEIVE"),RETURN("采购退货","RETURN");

    private String name;
    private String value;

    CeeaWarehousingReturnDetailEnum(String name, String value) {
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

    public static CeeaWarehousingReturnDetailEnum get(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (CeeaWarehousingReturnDetailEnum e : CeeaWarehousingReturnDetailEnum.values()) {
                if (e.getValue().equals(value)) {
                    return e;
                }
            }
        }
        return null;
    }
}
