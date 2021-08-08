package com.midea.cloud.common.enums.price.costelement;

/**
 * <pre>
 * 成本模块的枚举类
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/25
 *  修改内容:
 * </pre>
 */
public enum CostElementStatus {
    // DRAFT-拟定,VALID-生效,INVALID-失效
    DRAFT("DRAFT","拟定"),
    VALID("VALID","生效"),
    INVALID("INVALID","失效"),

    ;

    private String key;
    private String value;

    CostElementStatus(String key, String value) {
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
