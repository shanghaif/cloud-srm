package com.midea.cloud.common.enums.contract;

/**
 * <pre>
 * 合同级别
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
public enum ContractLevelEnum {
    LEVEL_01("LEVEL_01","A级关联方"),
    LEVEL_02("LEVEL_02","A级"),
    LEVEL_03("LEVEL_03","B级"),
    LEVEL_04("LEVEL_04","C级");

    private String code;
    private String value;

    ContractLevelEnum(String code, String value) {
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
