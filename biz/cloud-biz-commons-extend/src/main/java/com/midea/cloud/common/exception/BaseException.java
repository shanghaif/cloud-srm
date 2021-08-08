package com.midea.cloud.common.exception;

import com.midea.cloud.common.result.ResultCode;

/**
 * <pre>
 *  全局异常基类
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-2-11 15:49
 *  修改内容:
 * </pre>
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -4782274738616376976L;

    public static final ResultCode DEFAULT_ERROR_CODE = ResultCode.UNKNOWN_ERROR;
    private String resultCode = DEFAULT_ERROR_CODE.getCode();

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    public BaseException(String resultCode, String message, Throwable throwable) {
        super(message, throwable);
        this.resultCode = resultCode;
    }

    public BaseException(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public BaseException(ResultCode resultCode, String message) {
        this(resultCode.getCode(), message, null);
    }

    public BaseException(ResultCode resultCode, Throwable throwable) {
        this(resultCode.getCode(), resultCode.getMessage(), throwable);
    }

    public BaseException(ResultCode resultCode, String message, Throwable throwable) {
        this(resultCode.getCode(), message, throwable);
    }

    public String getResultCode() {
        return resultCode;
    }

}