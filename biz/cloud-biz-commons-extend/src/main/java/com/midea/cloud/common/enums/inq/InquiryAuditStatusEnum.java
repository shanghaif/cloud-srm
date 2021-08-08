package com.midea.cloud.common.enums.inq;

/**
 * <pre>
 * 流程节点类型枚举
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/20 19:17
 *  修改内容:
 * </pre>
 */
public enum InquiryAuditStatusEnum {
    APPLY_NOT_APPROVED("APPLY_NOT_APPROVED","申请未审批"),
    APPLY_REJECTED("APPLY_REJECTED","申请已驳回"),
    APPLY_PASSED("APPLY_PASSED","申请已审批"),
    RESULT_NOT_APPROVED("RESULT_NOT_APPROVED","结果未审批"),
    RESULT_REJECTED("RESULT_REJECTED","结果已驳回"),
    RESULT_PASSED("RESULT_PASSED","结果已审批");
    private String key;
    private String value;
    InquiryAuditStatusEnum(String key, String value){
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
