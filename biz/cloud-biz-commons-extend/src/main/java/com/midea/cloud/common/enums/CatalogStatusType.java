package com.midea.cloud.common.enums;

public enum CatalogStatusType {
    VALID("生效","VALID"),

    INVALID("失效","INVALID");


    private String name;
    private String value;

    CatalogStatusType(String name, String value) {
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
