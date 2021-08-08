package com.midea.cloud.common.enums.soap;

/**
 * <pre>
 * 接口状态(NEW-数据在接口表里,没同步到业务表,SUCCESS-同步到业务表,ERROR-数据同步到业务表出错)
 * </pre>
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/13 19:05
 *  修改内容:
 * </pre>
 */
public enum InterfaceStatusEnum {
    NEW("NEW", "新增"),
    UPDATE("UPDATE", "更新"),
    SUCCESS("SUCCESS","同步成功"),
    ERROR("ERROR", "同步失败");

    private String name;
    private String value;

    private InterfaceStatusEnum(String name, String value) {
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
    public static InterfaceStatusEnum get(String value) {
        for (InterfaceStatusEnum o : InterfaceStatusEnum.values()) {
            if (o.value.equals(value)) {
                return o;
            }
        }
        return null;
    }


}
