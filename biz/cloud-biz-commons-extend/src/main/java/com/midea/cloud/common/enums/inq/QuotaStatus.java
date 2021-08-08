package com.midea.cloud.common.enums.inq;

public enum QuotaStatus {
    DRAFT("新建", "DRAFT"),
    EFFECT("生效","EFFECT");
    private String name;
    private String value;

    private QuotaStatus(String name, String value) {
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
        for (QuotaStatus o : QuotaStatus.values()) {
            if (o.value.equals(value)) {
                return o.value;
            }
        }
        return null;
    }
}
