package com.midea.cloud.common.enums.price.costelement;

/**
 * <pre>
 * 计算方式
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
public enum Calculation {
    /**
     * DIRECT_CALCULATION-直接计算,CALCULATED_BY_RATE-按费率计算; 字典编码-COST_ELEMENT_CALCULATION
     */
    DIRECT_CALCULATION("DIRECT_CALCULATION","直接计算"),
    CALCULATED_BY_RATE("CALCULATED_BY_RATE","按费率计算");

    private String key;
    private String value;

    Calculation(String key, String value) {
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
