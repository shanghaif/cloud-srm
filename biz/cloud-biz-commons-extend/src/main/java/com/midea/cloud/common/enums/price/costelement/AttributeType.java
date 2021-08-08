package com.midea.cloud.common.enums.price.costelement;

/**
 * <pre>
 * 属性类型
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
public enum AttributeType {
    /**
     * 属性类型, 字典编码: FEATURE_ATTRIBUTE_TYPE
     * 数值,字符串,枚举,公式
     */
    NUMBER("NUMBER","数值"),
    STRING("STRING","字符串"),
    ENUM("ENUM","枚举"),
    FORMULA("FORMULA","公式")
    ;

    private String key;
    private String value;

    AttributeType(String key, String value) {
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
