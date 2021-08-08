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
 *  修改日期: 2021/2/19 10:07
 *  修改内容:
 * </pre>
 */
public enum WarehouseReceiptStatus {
    DRAFT("拟定","DRAFT"),
    WAITING_CONFIRM("待确认","WAITING_CONFIRM"),
    CONFIRM("已确认","CONFIRM"),
    WRITEOFF("冲销","WRITEOFF");

    private String name;
    private String value;

    WarehouseReceiptStatus(String name, String value) {
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
