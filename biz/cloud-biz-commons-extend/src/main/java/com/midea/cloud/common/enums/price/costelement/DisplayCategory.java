package com.midea.cloud.common.enums.price.costelement;

/**
 * <pre>
 * 基价显示类别
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/27
 *  修改内容:
 * </pre>
 */
public enum DisplayCategory {
    SUB_TABLE("0","分表"),
    SUM_TABLE("1","总表"),
    SUB_SUM_TABLE("2","分表和总表"),
    ;

    private String key;
    private String value;

    DisplayCategory(String key, String value) {
        this.key = key;
        this.value = value;
    }

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
