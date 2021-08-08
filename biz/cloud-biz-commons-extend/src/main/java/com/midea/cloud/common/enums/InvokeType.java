package com.midea.cloud.common.enums;

/**
 * <pre>
 *  调用类型
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-2-14 9:25
 *  修改内容:
 * </pre>
 */
public enum InvokeType {

    INTERAL_INVOKE("INTERAL_INVOKE", "内部调用"),
    EXTERNAL_INVOKE("EXTERNAL_INVOKE", "外部调用");

    private String code;
    private String name;

    InvokeType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
