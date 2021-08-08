package com.midea.cloud.signature.electronicsignature.dto;

public class MsignRequest<T> {
    private String appId;
    private String appKey;
    private T data;

    public MsignRequest() {

    }

    public MsignRequest(String appId, String appKey) {
        this.appId = appId;
        this.appKey = appKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
