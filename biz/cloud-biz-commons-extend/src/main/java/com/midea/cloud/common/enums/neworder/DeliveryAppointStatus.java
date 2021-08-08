package com.midea.cloud.common.enums.neworder;

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
 *  修改日期: 2021/2/4 10:07
 *  修改内容:
 * </pre>
 */
public enum DeliveryAppointStatus {
    DRAFT("拟定","DRAFT"),
    WAITING_CONFIRM("待确认","WAITING_CONFIRM"),
    ACCEPT("接受","ACCEPT"),
    REJECT("拒绝","REJECT");

    private String name;
    private String value;

    DeliveryAppointStatus(String name, String value) {
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
