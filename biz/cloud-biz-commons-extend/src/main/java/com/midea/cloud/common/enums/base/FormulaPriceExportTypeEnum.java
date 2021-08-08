package com.midea.cloud.common.enums.base;

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
 *  修改日期: 2020/12/8 13:35
 *  修改内容:
 * </pre>
 */
public enum FormulaPriceExportTypeEnum {
    TYPE1("价格公式头","TYPE1"),
    TYPE2("价格公式内容","TYPE2"),
    TYPE3("价格公式基材","TYPE3");

    private String name;
    private String value;

    private FormulaPriceExportTypeEnum(String name,String value){
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
