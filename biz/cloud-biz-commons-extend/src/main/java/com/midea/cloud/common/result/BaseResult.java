package com.midea.cloud.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <pre>
 *  返参基类
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-2-11 16:05
 *  修改内容:
 * </pre>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult<T> implements Serializable {

    private String code;
    private String message;
    private T data;
    private String errorMsgTrace;

    public static<T> BaseResult buildSuccess() {
        ResultCode resultCode = ResultCode.SUCCESS;
        BaseResult result = new BaseResult(resultCode.getCode(), resultCode.getMessage(), "", "");
        return result;
    }

    public static<T> BaseResult buildSuccess(T t) {
        ResultCode resultCode = ResultCode.SUCCESS;
        BaseResult result = new BaseResult(resultCode.getCode(), resultCode.getMessage(), t, "");
        return result;
    }

    public static<T> BaseResult build(ResultCode code) {
        BaseResult result = new BaseResult(code.getCode(), code.getMessage(), null, "");
        return result;
    }

    public static<T> BaseResult build(ResultCode code, T t) {
        BaseResult result = new BaseResult(code.getCode(), code.getMessage(), t, "");
        return result;
    }

    public static<T> BaseResult build(ResultCode code, String message) {
        BaseResult result = new BaseResult(code.getCode(), message, null, "");
        return result;
    }

    public static<T> BaseResult build(ResultCode code, String message, T t) {
        BaseResult result = new BaseResult(code.getCode(), message, t, "");
        return result;
    }

    public static<T> BaseResult build(String code, String message, T t) {
        BaseResult result = new BaseResult(code, message, t, "");
        return result;
    }

    public static<T> BaseResult build(String code, String message) {
        BaseResult result = new BaseResult(code, message, null, "");
        return result;
    }

    public static<T> BaseResult build(String code, String message, String errorMsgTrace) {
        BaseResult result = new BaseResult(code, message, null, errorMsgTrace);
        return result;
    }

    public static<T> BaseResult build(String code, String message, T t, String errorMsgTrace) {
        BaseResult result = new BaseResult(code, message, t, errorMsgTrace);
        return result;
    }

}
