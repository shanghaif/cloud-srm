package com.midea.cloud.common.enums.pm.po;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/11/29 14:30
 *  修改内容:
 * </pre>
 */
public enum PriceSourceTypeEnum {
    PRICE_LIBRARY("价格库","PRICE_LIBRARY"),
    CONTRACT("合同","CONTRACT"),
    MANUAL("手工","MANUAL");

    private String name;
    private String value;

    PriceSourceTypeEnum(String name, String value) {
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

}
