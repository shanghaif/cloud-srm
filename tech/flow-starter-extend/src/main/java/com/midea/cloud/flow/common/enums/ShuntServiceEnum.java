package com.midea.cloud.flow.common.enums;

/**
 * Description 分流服务枚举类
 * @Param
 * @return
 * @Author wuwl18@meicloud.com
 * @Date 2020.03.21
 * @throws
 **/
public enum ShuntServiceEnum {
    API_SERVIVE("api","api业务服务"),
    BASE_SERVICE("base","base业务服务"),
    BID_SERVICE("bid","业务服务"),
    COMMONS_SERVICE("commons","commons业务服务"),
    GATEWAY_SERVICE("gateway","gateway业务服务"),
    INQ_SERVIVE("inq","inq业务服务"),
    SUPPER_SERVICE("supplier","supplier业务服务"),
    SUPPER_AUTH_SERVICE("supplier-auth","supplier-auth业务服务"),
    SUPPER_COOPERATE_SERVICE("supplier-cooperate","supplier-cooperate业务服务");

    private String key;
    private String value;
    ShuntServiceEnum(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
