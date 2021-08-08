package com.midea.cloud.common.enums.contract;

import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 * 合同等级枚举，传bpm使用
 * </pre>
 *
 * @author chenwt24@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-15
 *  修改内容:
 * </pre>
 */
public enum ContractLevelForBpmEnum {

    /** */
    LEVEL_01("LEVEL_01","SA"),
    LEVEL_02("LEVEL_02","A"),
    LEVEL_03("LEVEL_03","B"),
    LEVEL_04("LEVEL_04","C");

    private String code;
    private String value;

    ContractLevelForBpmEnum(String code, String value) {
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

    public static String getValueByCode(String code){
        if(StringUtils.isBlank(code)){
            return null;
        }
        for (ContractLevelForBpmEnum level: ContractLevelForBpmEnum.values()){
            if(level.getCode().equals(code)){
                return level.getValue();
            }
        }
        return null;
    }
}
