package com.midea.cloud.flow.model.dto;

/**
 * <pre>
 * http请求返回结果。
 * </pre>
 *
 * @author liangtf2 liangtf2@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class Result<T> {
	
	/**
	 * 请求成功
	 */
	public final static int HTTP_RESULT_CODE_SUCCESS = 200;

	/**
	 * 请求失败
	 */
	public final static int HTTP_RESULT_CODE_FAIL = 500;
	
    private int code;
    private String msg;
    private T data;

    public Result() {
    }


    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(Object data) {
        return new Result(HTTP_RESULT_CODE_SUCCESS, "请求成功", data);
    }

    public static Result fail(String msg) {
        return new Result(HTTP_RESULT_CODE_FAIL, msg,null);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

