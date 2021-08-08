package com.midea.cloud.common.enums.soap;

/**
 * <pre>
 *  外部系统接口来源
 * </pre>
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/13 19:05
 *  修改内容: financailShare
 * </pre>
 */
public enum InterfaceResourceEnum {
    ERP("ERP", "ERP系统"),
    HR("HR","人力系统"),
    FINANCAIL_SHAREING("FINANCAIL_SHAREING", "财务共享系统");

    private String name;
    private String value;

    private InterfaceResourceEnum(String name, String value) {
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

    /**
     * 通过指定value值获取枚举
     *
     * @param value
     * @return
     */
    public static InterfaceResourceEnum get(String value) {
        for (InterfaceResourceEnum o : InterfaceResourceEnum.values()) {
            if (o.value.equals(value)) {
                return o;
            }
        }
        return null;
    }


}
