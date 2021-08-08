package com.midea.cloud.common.enums;

/**
 * 流程节点
 * <pre>
 * 招标：流程节点标记
 * </pre>
 *
 * @author zhuomb1@midea.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public enum ProcessNodeName {

    technologyExchange("技术交流", "technologyExchange"),
    projectInformation("项目信息", "projectInformation"),
    projectRequirement("项目需求", "projectRequirement"),
    inviteSupplier("邀请供应商", "inviteSupplier"),
    scoringRule("评分规则", "scoringRule"),
    processApproval("流程审批", "processApproval"),
    supplierPerformance("供应商绩效 ", "supplierPerformance"),
    targetPrice("拦标价", "targetPrice"),
    projectPublish("项目发布", "projectPublish"),
    entryManagement("报名管理", "entryManagement"),
    questionClarification("质疑澄清", "questionClarification"),
    bidingControl("投标控制", "bidingControl"),
    technicalScore("技术评分", "technicalScore"),
    technicalManagement("技术标管理", "technicalManagement"),
    commercialManagement("商务标管理", "commercialManagement"),
    bidEvaluation("评选", "bidEvaluation"),
    bidingResult("招标结果", "bidingResult"),
    projectReport("结项报告", "projectReport"),
    projectApproval("结项审批", "projectApproval");

    private String name;
    private String value;

    private ProcessNodeName(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     *
     * 通过指定value值获取枚举
     * @param value
     * @return
     */
    public static ProcessNodeName get(String value ){
        for(ProcessNodeName o: ProcessNodeName.values()){
            if(o.value.equals(value)){
                return o;
            }
        }
        return null;
    }

    /**
     * 枚举值列表是否包含指定code
     * @param code
     * @return true or false
     */
    public static boolean isContain( String code ){
        return (get(code)!=null);
    }
}
