package com.midea.cloud.common.enums.supcooperate;

public enum MaterialItemType {
    HASNEW("HASNEW","已创建"),
    ISSUED("ISSUED","已发布");

    private String name;
    private String value;

    MaterialItemType(String name, String value) {
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
    public static String get(String value) {
        for (MaterialItemType o : MaterialItemType.values()) {
            if (o.value.equals(value)) {
                return o.name;
            }
        }
        return null;
    }

}
