package com.midea.cloud.common.enums.pm.ps;

/**
 * <pre>
 *  费控系统返回状态码；费控系统说根据返回状态码进行判断
 * </pre>
 *
 * @author chenjj120@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/11 上午 11:07
 *  修改内容:
 * </pre>
 */
public enum FSSCResponseCode {

    SUCCESS("0" , "成功"),

    WARN("2000" ,"警告"),

    ERROR("500","错误")
    ;

    private String code;

    private String statusName;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }



    FSSCResponseCode(String code ,String statusName){
        this.code = code;
        this.statusName = statusName;
    }
}
