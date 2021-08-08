package com.midea.cloud.common.enums.inq;

import org.apache.commons.lang3.StringUtils;

/**
 * 配额分配类型
 */
public enum CeeaQuotaAllocationTypeEnum {
    AMOUNT_RATIO("按金额比例","AMOUNT_RATIO"),
    MATERIAL_RATIO("按物料比例","MATERIAL_RATIO");

    private String name;
    private String value;

    CeeaQuotaAllocationTypeEnum(String name, String value) {
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

    public static CeeaQuotaAllocationTypeEnum get(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (CeeaQuotaAllocationTypeEnum e : CeeaQuotaAllocationTypeEnum.values()) {
                if (e.getValue().equals(value)) {
                    return e;
                }
            }
        }
        return null;
    }
}
