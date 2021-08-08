package com.midea.cloud.common.enums.contract;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/20
 *  修改内容:
 * </pre>
 */
public enum CalculatingSigns {
    /**
     *         calculatingSigns = new HashMap<>();
     *         calculatingSigns.put("MORE_THAN",">");
     *         calculatingSigns.put("MORE_THAN_EQUAL",">=");
     *         calculatingSigns.put("LESS_THAN","<");
     *         calculatingSigns.put("LESS_THAN_EQUAL","<=");
     *         calculatingSigns.put("EQUAL","==");
     */
    MORE_THAN("MORE_THAN",">"),
    MORE_THAN_EQUAL("MORE_THAN_EQUAL",">="),
    LESS_THAN("LESS_THAN","<"),
    LESS_THAN_EQUAL("LESS_THAN_EQUAL","<="),
    EQUAL("EQUAL","==");


    private String code;
    private String value;

    CalculatingSigns(String code, String value) {
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
