package com.midea.cloud.common.enums.price.costelement;

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
 *  修改日期: 2020/7/29
 *  修改内容:
 * </pre>
 */
public enum CalculationBasis {

    /**
     * 计算基准(MATERIAL-材质成本,CRAFT-工艺成本,MATERIAL_CRAFT-材质+工艺成本; 字典编码-RATE_CALCULATION_BASIS )
     */
    MATERIAL("MATERIAL","材质成本"),
    MATERIAL_CRAFT("MATERIAL_CRAFT","材质+工艺成本"),
    CRAFT("CRAFT","工艺成本"),
    ;

    private String key;
    private String value;

    CalculationBasis(String key, String value) {
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
