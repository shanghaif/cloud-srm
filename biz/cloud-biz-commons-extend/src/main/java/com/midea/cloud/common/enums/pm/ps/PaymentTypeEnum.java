package com.midea.cloud.common.enums.pm.ps;

/**
 * <pre>
 *  付款类型 PAYMENT_TYPE
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/6/4 9:13
 *  修改内容:
 * </pre>
 */
public enum PaymentTypeEnum {

    CASH("现金支付", "CASH"),
    ADVANCE("预付款", "ADVANCE");

    private String name;
    private String value;

    PaymentTypeEnum(String name, String value) {
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
