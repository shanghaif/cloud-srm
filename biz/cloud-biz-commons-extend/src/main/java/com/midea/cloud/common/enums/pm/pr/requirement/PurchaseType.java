package com.midea.cloud.common.enums.pm.pr.requirement;

/**
 * <pre>
 *   采购类型枚举(longi)  字典码:PURCHASE_TYPE
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/27 11:53
 *  修改内容:
 * </pre>
 */
public enum PurchaseType {

    NORMAL("常规采购", "NORMAL"),
    URGENT("紧急采购", "URGENT"),
    DEVELOP("研发采购", "DEVELOP"),
    SERVICE("服务采购", "SERVICE"),
    CONVENIENT("便捷采购", "CONVENIENT"),
    APPOINT("指定采购", "APPOINT"),
    OUT("废旧物处理", "OUT");

    private String name;
    private String value;

    PurchaseType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * 通过指定value值获取枚举
     *
     * @param value
     * @return
     */
    public static PurchaseType get(String value) {
        for (PurchaseType o : PurchaseType.values()) {
            if (o.value.equals(value)) {
                return o;
            }
        }
        return null;
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
