package com.midea.cloud.common.enums.base;

import com.midea.cloud.common.enums.order.ReturnOrderStatusEnum;

public enum StatusCodeEnum {

    DRAFT("拟定", "DRAFT"),
    PUBLISHED("已发布", "PUBLISHED"),
    COMPLETED("已完成", "COMPLETED");

    private String name;
    private String value;

    private StatusCodeEnum(String name, String value) {
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
    public static StatusCodeEnum get(String value) {
        for (StatusCodeEnum o : StatusCodeEnum.values()) {
            if (o.value.equals(value)) {
                return o;
            }
        }
        return null;
    }

    /**
     * 枚举值列表是否包含指定code
     *
     * @param code
     * @return true or false
     */
    public static boolean isContain(String code) {
        return (get(code) != null);
    }

}
