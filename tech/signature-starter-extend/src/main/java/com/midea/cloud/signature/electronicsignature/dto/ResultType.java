package com.midea.cloud.signature.electronicsignature.dto;

public enum ResultType {
    SUCCESS("SUCCESS", "1"),//执行成功（适用于调用流程操作）
    HADATA("HADATA", "2"),// 有数据（适用于查询操作）
    FAILURE("FAILURE", "0"),// 执行失败（适用于调用流程操作）
    NODATA("NODATA", "3"),// 无数据（适用于查询操作）
    EXCEPTION("EXCEPTION", "4"),// 异常
    UNDEFINED("UNDEFINED", "5");//未定义，表示由aop拦截器对已有结果进行判断
    private String type;
    private String value;

    ResultType(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
