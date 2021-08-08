package com.midea.cloud.common.exception;

import com.midea.cloud.common.result.ResultCode;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/4 15:42
 *  修改内容:
 * </pre>
 */
public class BidException extends BaseException {
    public BidException() {
        super();
    }

    public BidException(String message) {
        super(message);
    }

    public BidException(String resultCode, String message) {
        super(message);
    }

    public BidException(String resultCode, String message, Throwable throwable) {
        super(resultCode,message, throwable);
    }

    public BidException(ResultCode resultCode) {
        super(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public BidException(ResultCode resultCode, String message) {
        super(resultCode.getCode(), message, null);
    }

    public BidException(ResultCode resultCode, Throwable throwable) {
        super(resultCode.getCode(), resultCode.getMessage(), throwable);
    }

    public BidException(ResultCode resultCode, String message, Throwable throwable) {
        super(resultCode.getCode(), message, throwable);
    }

}
