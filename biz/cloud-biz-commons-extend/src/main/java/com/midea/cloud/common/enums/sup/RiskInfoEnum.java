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
public enum RiskInfoEnum {
    /**
     * 企业搜索:
     * 入参：userName+keywords
     * */
    R1("R1","https://api.riskraider.com/v3.1/enterprise/enterpriseSearch"),
    /**
     * 企业风险扫描结果:
     * 入参：userName+enterpriseName+accountId
     * */
    R2("R2","https://api.riskraider.com/v3/businessInfo/getEnterpriseInfoScan"),
    /**
     * 企业工商信息:
     * 入参：userName+enterpriseName
     * */
    R3("R3","https://api.riskraider.com/v3.1/business/getEnterpriseBusinessInformation"),
    /**
     * 企业司法全景:
     * 入参：userName+enterpriseName
     * */
    R4("R4","https://api.riskraider.com/v3/justice/getEnterpriseJudicialPanorama"),
    /**
     * 企业司法信息:
     * 入参：userName+enterpriseName
     * */
    R5("R5","https://api.riskraider.com/v3/justice/getEnterpriseJusticeInfo"),
    /**
     * 企业司法裁判文书:
     * 入参：userName+enterpriseName+currentPageNo
     * */
    R6("R6","https://api.riskraider.com/v3/justice/getEnterpriseJudgmentPage"),

    /**
     * 企业经营分析:
     * 入参：userName+enterpriseName
     * */
    R7("R7","https://api.riskraider.com/v3/finance/getEnterpriseFinanceSituation"),
    /**
     * 企业失信信息:
     * 入参：userName+enterpriseName
     * */
    R8("R8","https://api.riskraider.com/v3/blacklist/getEnterpriseDiscreditInformation"),
    /**
     * 企业舆情信息（原版）:
     * 入参：userName+enterpriseName+currentPageNo+pageSize
     * */
    R9("R9","https://api.riskraider.com/v3/newsInfo/getEnterpriseNewsInfo"),
    /**
     * 企业舆情信息:
     * 入参：userName+enterpriseName+newsTags+currentPageNo+pageSize
     * */
    R10("R10","https://api.riskraider.com/v3/newsInfo/getEnterpriseNewsInfo"),
    /**
     * 监控企业风险信息:
     * 入参：userName+enterpriseName
     * */
    R11("R11","https://api.riskraider.com/v3/monitor/getMonitorInfoOfEnterprise"),
    /**
     * 更新事件详情:
     * 入参：userName+apiKey+accountId+monitorId+eventSubType+currentPageNo+pageSize+eventScopeFlag+endDate+monitorCycle
     * 注：在调用此接口前需调用（标准监控企业更新事件企业列表接口）
     * */
    R12("R12","https://api.riskraider.com/v3/updateEventDetail/getEventDetail"),
    /**
     * 更新事件企业列表:
     * 入参：userName+apiKey+accountId+accountType+currentPageNo+pageSize+enterpriseName+endDate+monitorCycle+riskSituation+eventLevel+associatedEnterprises+riskChange+tagList
     * */
    R13("R13","https://api.riskraider.com/v3/monitorEnterpriseUpdateEvent/getMonitorEnterpriseEventUpdateInfo"),
    /**
     * 监控企业关系挖掘:
     * 入参：userName+apiKey+accountId+companyNames[]
     * */
    R14("R14","https://api.riskraider.com/v3/relationDiagram/getNewRelationDiagram"),
    /**
     * SaaS账户下监控企业列表:
     * 入参：userName+apiKey+accountId+currentPageNo+pageSize
     * 注：在调用此接口前需调用（目标客户 SaaS 账号接口）
     * */
    F1("F1","https://api.riskraider.com/v3/relationDiagram/getEnterpriseList"),
    /**
     * 是否为监控企业:
     * 入参：userName+enterpriseName
     * */
    F2("F2","https://api.riskraider.com/v3/monitor/isMonitorCompanyOfClientCompany"),
    /**
     * 目标客户SaaS账号:
     * 入参：userName+apiKey
     * */
    F3("F3","https://api.riskraider.com/v3/monitorEnterpriseUpdateEvent/getSaaSAccount"),
    /**
     * 事件字典表:
     * 入参：userName+apiKey
     * */
    F4("F4","https://api.riskraider.com/v3/dictionary/getUpdateEventDic"),
    /**
     * 企业添加监控规范:
     * 入参：userName+enterpriseName+accountId
     * */
    F5("F5","https://api.riskraider.com/v3/monitorEnterprise/addMonitorEnterprise"),
    /**
     * 企业取消监控规范:
     * 入参：userName+enterpriseName+accountId
     * */
    F6("F6","https://api.riskraider.com/v3/monitorEnterprise/cancelMonitorEnterprise");

    private String code;
    private String value;

    public static String getValueByCode(String code){
        if(StringUtils.isBlank(code)){
            return null;
        }
        for (RiskInfoEnum bpmEnum: RiskInfoEnum.values()){
            if(code.equals(bpmEnum.getCode())){
                return bpmEnum.getValue();
            }
        }
        return null;
    }
}
