package com.midea.cloud.common.enums.perf.indicators;

public enum EvaluationResults {
    BAD("差","BAD"),
    NOT_SO_BAD("中","BAD");

    private String name;
    private String value;

    private EvaluationResults(String name, String value){
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
