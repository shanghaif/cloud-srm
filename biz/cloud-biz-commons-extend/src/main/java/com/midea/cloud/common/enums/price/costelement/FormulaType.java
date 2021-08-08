package com.midea.cloud.common.enums.price.costelement;

/**
 * <pre>
 * 公式类型
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
public enum FormulaType {
    FORMULA_TYPE_0("0","用量公式"),
    FORMULA_TYPE_1("1","价格公式"),

    ;

    private String key;
    private String value;

    FormulaType(String key, String value) {
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
