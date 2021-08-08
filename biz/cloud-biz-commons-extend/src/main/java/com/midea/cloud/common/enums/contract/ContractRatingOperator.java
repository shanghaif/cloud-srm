package com.midea.cloud.common.enums.contract;

/**
 * <pre>
 * 合同
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/6
 *  修改内容:
 * </pre>
 */
public enum ContractRatingOperator {
    MORE_THAN("MORE_THAN","大于"),
    MORE_THAN_EQUAL("MORE_THAN_EQUAL","大于等于"),
    LESS_THAN("LESS_THAN","小于"),
    LESS_THAN_EQUAL("LESS_THAN_EQUAL","小于等于"),
    EQUAL("EQUAL","等于");

    private String code;
    private String value;

    ContractRatingOperator(String code, String value) {
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
