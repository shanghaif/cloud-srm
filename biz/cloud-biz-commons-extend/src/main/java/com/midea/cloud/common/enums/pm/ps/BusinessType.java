package com.midea.cloud.common.enums.pm.ps;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/27 17:21
 *  修改内容:
 * </pre>
 */
public enum BusinessType {

    BUSINESS_TYPE_40("服务类", "40");

    private String name;
    private String value;

    private BusinessType(String name, String value) {
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
