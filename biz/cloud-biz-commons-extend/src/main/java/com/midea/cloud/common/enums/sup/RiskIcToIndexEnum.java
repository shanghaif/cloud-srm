package com.midea.cloud.common.enums.sup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 *  风险雷达接口编码和接口地址
 * </pre>
 *
 * @author chenwt24@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-20
 *  修改内容:
 * </pre>
 */
@AllArgsConstructor
@Getter
public enum RiskIcToIndexEnum {
    /**
     * 企业搜索:
     * 入参：userName+keywords
     * */
    R1("R1","raider_info_r1"),
    /**
     * 企业风险扫描结果:
     * 入参：userName+enterpriseName+accountId
     * */
    R2("R2","raider_info_r2"),
    /**
     * 企业工商信息:
     * 入参：userName+enterpriseName
     * */
    R3("R3","raider_info_r3"),
    /**
     * 企业司法全景:
     * 入参：userName+enterpriseName
     * */
    R4("R4","raider_info_r4"),
    /**
     * 企业司法信息:
     * 入参：userName+enterpriseName
     * */
    R5("R5","raider_info_r5"),
    /**
     * 企业司法裁判文书:
     * 入参：userName+enterpriseName+currentPageNo
     * */
    R6("R6","raider_info_r6"),

    /**
     * 企业经营分析:
     * 入参：userName+enterpriseName
     * */
    R7("R7","raider_info_r7"),
    /**
     * 企业失信信息:
     * 入参：userName+enterpriseName
     * */
    R8("R8","raider_info_r8"),
    /**
     * 企业舆情信息（原版）:
     * 入参：userName+enterpriseName+currentPageNo+pageSize
     * */
    R9("R9","raider_info_r9"),
    /**
     * 企业舆情信息:
     * 入参：userName+enterpriseName+newsTags+currentPageNo+pageSize
     * */
    R10("R10","raider_info_r10"),
    R11("R11","raider_info_r11"),
    R12("R12","raider_info_r12"),
    R13("R13","raider_info_r13"),
    R14("R14","raider_info_r14"),
    F1("F1","raider_info_f1"),
    F2("F2","raider_info_f2"),
    F3("F3","raider_info_f3"),
    F4("F4","raider_info_f4"),
    F5("F5","raider_info_f5"),
    F6("F6","raider_info_f6");

    private String code;
    private String value;

    public static String getValueByCode(String code){
        if(StringUtils.isBlank(code)){
            return null;
        }
        for (RiskIcToIndexEnum bpmEnum: RiskIcToIndexEnum.values()){
            if(code.equals(bpmEnum.getCode())){
                return bpmEnum.getValue();
            }
        }
        return null;
    }
}
