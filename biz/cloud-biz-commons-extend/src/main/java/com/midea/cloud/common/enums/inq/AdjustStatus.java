package com.midea.cloud.common.enums.inq;

import com.midea.cloud.common.enums.pm.pr.requirement.RequirementApproveStatus;

public enum AdjustStatus {

    DRAFT("新建", "DRAFT"),
    SUBMITTED("审批中", "SUBMITTED"),
    REJECTED("已驳回", "REJECTED"),
    APPROVED("已审批", "APPROVED");


    private String name;
    private String value;

    private AdjustStatus(String name, String value) {
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

    /**
     * 通过指定value值获取枚举
     *
     * @param value
     * @return
     */
    public static String get(String value) {
        for (AdjustStatus o : AdjustStatus.values()) {
            if (o.value.equals(value)) {
                return o.value;
            }
        }
        return null;
    }
}
