package com.midea.cloud.common.enums.bargaining.projectmanagement.signupmanagement;

/**
 * <pre>
 * 招标报名状态 字典码:BIDDING_SIGNUP_STATES
 * </pre>
 *
 * @author zhuomb1@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-03 14:53:20
 *  修改内容:
 * </pre>
 */
public enum SignUpStatus {

    NO_SIGNUP("未报名", "NO_SIGNUP"),
    SIGNUPED("已报名", "SIGNUPED"),
    REJECTED("已驳回", "REJECTED");

    private String name;
    private String value;

    private SignUpStatus(String name, String value) {
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
    public static SignUpStatus get(String value) {
        for (SignUpStatus o : SignUpStatus.values()) {
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
