package com.midea.cloud.common.enums;
/**
 * <pre>
 *  供应商改善单状态
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-11 9:52
 *  修改内容:
 * </pre>
 */
public enum VendorImproveFormStatus {
    DRAFT("DRAFT","拟定"),
    IMPROVING("IMPROVING","改善中"),
    UNDER_EVALUATION("UNDER_EVALUATION","评价中"),
    EVALUATED("EVALUATED","已评价");

    private String code;
    private String value;

    VendorImproveFormStatus(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
