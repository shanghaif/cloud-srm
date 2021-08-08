package com.midea.cloud.signature.electronicsignature.dto;

import com.midea.cloud.signature.electronicsignature.utils.MsignUtils;

public class MsignResponse<T> {
    private String message;
    private String resultType;
    private String serialNum;
    private T data;

    public static MsignResponse<?> exception() {
        MsignResponse<MsignResponseData> msignResponse = new MsignResponse();
        MsignResponseData msignResponseData = new MsignResponseData();
        msignResponse.setResultType(ResultType.EXCEPTION.getType());
        msignResponse.setSerialNum(MsignUtils.getSerialNum());
        msignResponse.setMessage("异常");
        msignResponse.setData(msignResponseData);
        msignResponseData.setCode(ResultType.EXCEPTION.getValue());
        msignResponseData.setMessage(msignResponse.getMessage());
        return msignResponse;
    }

    public static MsignResponse<?> exception(String message) {
        MsignResponse<MsignResponseData> msignResponse = (MsignResponse<MsignResponseData>) exception();
        msignResponse.setMessage(message);
        msignResponse.getData().setMessage(message);
        return msignResponse;
    }

    public static <T> MsignResponse<?> success(T data) {
        MsignResponse msignResponse = new MsignResponse();
        msignResponse.setResultType(ResultType.SUCCESS.getType());
        msignResponse.setSerialNum(MsignUtils.getSerialNum());
        msignResponse.setMessage("成功");
        msignResponse.setData(data);
        return msignResponse;
    }

    public static MsignResponse<?> success() {
        MsignResponse<MsignResponseData> msignResponse = new MsignResponse();
        MsignResponseData msignResponseData = new MsignResponseData();
        msignResponse.setResultType(ResultType.SUCCESS.getType());
        msignResponse.setSerialNum(MsignUtils.getSerialNum());
        msignResponse.setMessage("成功");
        msignResponse.setData(msignResponseData);
        msignResponseData.setCode(ResultType.SUCCESS.getValue());
        msignResponseData.setMessage(msignResponse.getMessage());
        return msignResponse;
    }

    public static MsignResponse<?> success(String message) {
        MsignResponse<MsignResponseData> msignResponse = (MsignResponse<MsignResponseData>) success();
        msignResponse.setMessage(message);
        msignResponse.getData().setMessage(message);
        return msignResponse;
    }

    public static MsignResponse<?> success(String message, String serialNum) {
        MsignResponse<MsignResponseData> msignResponse = (MsignResponse<MsignResponseData>) success();
        msignResponse.setMessage(message);
        msignResponse.setSerialNum(serialNum);
        msignResponse.getData().setMessage(message);
        return msignResponse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
