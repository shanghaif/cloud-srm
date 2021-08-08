package com.midea.cloud.common.enums.pm.pr.requirement;

/**
 * <pre>
 * 采购需求申请状态 字典码:APPLICATION_STATUS
 * </pre>
 *
 * @author fengdc3@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-13 19:15:20
 *  修改内容:
 * </pre>
 */
public enum RequirementApplyStatus {

    EDIT("拟定", "EDIT"),
    UNASSIGNED("未分配", "UNASSIGNED"),
    REJECTED("已驳回", "REJECTED"),
    ASSIGNED("已分配", "ASSIGNED"),
    MERGED("已合并","MERGED"),
    CREATED("已创建寻源单据", "CREATED"),
    COMPLETED("已完成", "COMPLETED"),
    TRANSFERRED_ORDER("已创建订单","TRANSFERRED_ORDER"),
    CANCELED("已取消", "CANCELED"),//ceea:已取消
    RETURNING("待退回", "RETURNING");

    private String name;
    private String value;

    private RequirementApplyStatus(String name, String value) {
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
    public static RequirementApplyStatus get(String value) {
        for (RequirementApplyStatus o : RequirementApplyStatus.values()) {
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
