package com.midea.cloud.common.enums.contract;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/6/28
 *  修改内容:
 * </pre>
 */
public enum ElementEnum {
    // 是否激活
    IS_ACT_Y("Y","是"),
    IS_ACT_N("N","否"),
    // 是否固定
    IS_FIXED_Y("Y","是"),
    IS_FIXED_N("N","否");

    ElementEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
