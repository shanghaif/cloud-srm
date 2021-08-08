package com.midea.cloud.common.enums;

/**
 * 寻源方式
 */
public enum SourceType {

    BIDDINGBi("招投标", "BIDDINGBi"),
    INQUIRY("询比价", "INQUIRY");

    private final String name;
    private final String value;

    SourceType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
