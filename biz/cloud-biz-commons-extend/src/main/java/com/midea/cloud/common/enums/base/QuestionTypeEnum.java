package com.midea.cloud.common.enums.base;

import lombok.Getter;

@Getter
public enum QuestionTypeEnum {
    S("单选题","S"),
    M("多选题","M"),
    Q("问答题","Q");

    private String flag;
    private String value;

    QuestionTypeEnum(String flag, String value) {
        this.flag = flag;
        this.value = value;
    }
}
