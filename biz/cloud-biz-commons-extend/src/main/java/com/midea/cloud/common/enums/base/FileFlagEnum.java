package com.midea.cloud.common.enums.base;

import lombok.Getter;

@Getter
public enum FileFlagEnum {
    Y("是","Y"),
    N("否","N");
    private String flag;
    private String value;

    FileFlagEnum(String flag, String value) {
        this.flag = flag;
        this.value = value;
    }
}
