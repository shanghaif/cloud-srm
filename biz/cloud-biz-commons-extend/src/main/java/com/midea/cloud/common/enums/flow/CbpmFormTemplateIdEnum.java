package com.midea.cloud.common.enums.flow;

/**
 * <pre>
 *  cbpm流程业务系统模版(对应cbpm流程的formTemplateId业务系统模版id)
 *  注意：后续需要接入的流程，需要在这里配置
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/20
 *  修改内容:
 * </pre>
 */
public enum CbpmFormTemplateIdEnum {
    QUA_OF_REVIEW("quaOfReview", "资质审查"),
    INIT_PROJECT_APPROVAL("initProjectApproval","招标立项审批"),
    END_PROJECT_APPROVAL("endProjectApproval","招标结项审批"),
    INQUIRY_APPROVAL_FLOW("inquiryApprovalFlow","价格审批单"),
    INQUIRY_FLOW("inquiryFlow","询价单审批"),
    QUA_OF_SAMPLE_FLOW_CHECK("quaOfSampleFlowCheck","样品确认结果审批"),
    QUA_OF_MATERIAL_TRIAL("quaOfMaterialTrial","物料试用"),
    CHANGE_SUP_INFO("changeSupInfo","供应商信息变更"),
    VENDOR_ASSES_FORM("vendorAssesForm","绩效考核"),
    PERF_SCORE_ITEMS("perfScoreItems","绩效评分项目"),
    END_COOPERATE("endCooperate","合作终止"),
    REQUIRE_HEADER("requireHeader","采购需求"),
    REQUIRE_ORDER("requireOrder","采购订单");

    private String key;
    private String value;
    CbpmFormTemplateIdEnum(String key, String value){
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
