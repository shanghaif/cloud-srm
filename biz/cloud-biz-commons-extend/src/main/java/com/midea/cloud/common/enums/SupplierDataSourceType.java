package com.midea.cloud.common.enums;

/**
 * 数据来源
 * <pre>
 * 引用功能模块： Supplier
 * </pre>
 *
 * @author zhuwl7@meicloud.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public enum SupplierDataSourceType {

    ONESELF_REGISTER("自主注册", "ONESELF_REGISTER"),//供应商注册
    MANUALLY_CREATE("手工创建", "MANUALLY_CREATE"),//绿色通道
    INITIALIZE("初始化", "INITIALIZE"); //发票单据号

    private String name;
    private String value;

    private SupplierDataSourceType(String name, String value) {
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
