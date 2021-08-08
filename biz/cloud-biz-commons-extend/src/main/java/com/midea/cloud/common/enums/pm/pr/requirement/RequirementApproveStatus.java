package com.midea.cloud.common.enums.pm.pr.requirement;

/**
 * 审批状态  字典编码:APPROVAL_STATUS
 *
 * @author fengdc3@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-15 9:15:20
 *  修改内容:
 * </pre>
 */
public enum RequirementApproveStatus {

    DRAFT("拟定", "DRAFT"),
    SUBMITTED("已提交", "SUBMITTED"),
    APPROVING("审批中", "APPROVING"),
    REJECTED("已驳回", "REJECTED"),
    APPROVED("已审批", "APPROVED"),
    ABANDONED("已废弃", "ABANDONED"),
    WITHDRAW("已撤回", "WITHDRAW");

    private String name;
    private String value;

    private RequirementApproveStatus(String name, String value) {
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
    public static RequirementApproveStatus get(String value) {
        for (RequirementApproveStatus o : RequirementApproveStatus.values()) {
            if (o.value.equals(value)) {
                return o;
            }
        }
        return null;
    }

    /**
     * 枚举值列表是否包含指定code
     *
     * @param code
     * @return true or false
     */
    public static boolean isContain(String code) {
        return (get(code) != null);
    }

}
