package com.midea.cloud.common.enums.price.costelement;

/**
 * <pre>
 * 公式符号
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
public enum FormulaSymbol {
    FORMULA_SYMBOL_GT(">","大于"),
    FORMULA_SYMBOL_GE(">=","大于等于"),
    FORMULA_SYMBOL_LT("<","小于"),
    FORMULA_SYMBOL_LE("<=","小于等于"),
    FORMULA_SYMBOL_EQ("==","等于"),
    FORMULA_SYMBOL_NE("!=","不等于"),
    FORMULA_SYMBOL_AND("&&","并且"),
    FORMULA_SYMBOL_OR("||","或者"),

    ;

    private String key;
    private String value;

    FormulaSymbol(String key, String value) {
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
