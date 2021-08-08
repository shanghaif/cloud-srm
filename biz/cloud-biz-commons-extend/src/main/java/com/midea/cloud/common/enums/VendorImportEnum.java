package com.midea.cloud.common.enums;

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
 *  修改日期: 2020/7/3
 *  修改内容:
 * </pre>
 */
public enum VendorImportEnum {
    RELATION_INSIDE("INSIDE","境内供应商"),
    RELATION_OUT("OUT","境外供应商"),
    COMPANY_NATURE_GETI("GETI","个体户"),
    COMPANY_NATURE_FEIYINGLI("FEIYINGLI","非营利机构"),
    COMPANY_NATURE_GUOYOU("GUOYOU","国有企业"),

    ;
    private String key;
    private String value;

    VendorImportEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
