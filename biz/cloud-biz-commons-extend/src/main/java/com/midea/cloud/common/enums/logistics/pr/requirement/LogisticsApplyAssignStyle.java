package com.midea.cloud.common.enums.logistics.pr.requirement;

/**
 * <pre>
 * 采购需求申请状态 字典码:LOGISTICS_APPLY_ASSIGN_STYLE
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
public enum LogisticsApplyAssignStyle {

    ASSIGNED("已分配", "ASSIGNED"),
    UNASSIGNED("未分配", "UNASSIGNED");

    private String name;
    private String value;

    private LogisticsApplyAssignStyle(String name, String value) {
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
    public static LogisticsApplyAssignStyle get(String value) {
        for (LogisticsApplyAssignStyle o : LogisticsApplyAssignStyle.values()) {
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
