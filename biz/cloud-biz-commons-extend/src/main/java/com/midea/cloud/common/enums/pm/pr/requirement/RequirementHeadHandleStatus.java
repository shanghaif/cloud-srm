package com.midea.cloud.common.enums.pm.pr.requirement;

/**
 * <pre>
 * 采购需求处理状态 字典码:PROCESSING_STATUS
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
public enum RequirementHeadHandleStatus {

    EDIT("拟定", "EDIT"),
    PARTIAL("已部分处理", "PARTIAL"),
    ALL("已全部处理", "ALL");

    private String name;
    private String value;

    private RequirementHeadHandleStatus(String name, String value) {
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
    public static RequirementHeadHandleStatus get(String value) {
        for (RequirementHeadHandleStatus o : RequirementHeadHandleStatus.values()) {
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
