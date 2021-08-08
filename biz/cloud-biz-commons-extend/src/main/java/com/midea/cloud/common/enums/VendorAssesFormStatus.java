package com.midea.cloud.common.enums;

/**
 * <pre>
 *  供应商考核单状态
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-11 9:52
 *  修改内容:
 * </pre>
 */
public enum VendorAssesFormStatus {
    DRAFT("DRAFT", "拟定"),
    IN_FEEDBACK("IN_FEEDBACK", "反馈中"),
    //    WITHDRAWN("WITHDRAWN","已撤回"),
    VENDOR_FEEDBACK("VENDOR_FEEDBACK", "供应商已反馈"),
    ASSESSED("ASSESSED", "已考核"),
    REVIEWED("REVIEWED", "已审核"),
    SETTLED("SETTLED", "已结算"),
    SUBMITTED("SUBMITTED", "已提交"),
    AFFIRM("AFFIRM", "供应商已确认"),
    UNDER_APPROVAL("UNDER_APPROVAL", "审批中"),
    REJECTED("REJECTED", "已驳回"),
    WITHDRAW("WITHDRAW", "审核撤回"),
    OBSOLETE("OBSOLETE", "已废弃");
    private String Key;
    private String value;

    VendorAssesFormStatus(String key, String value) {
        Key = key;
        this.value = value;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
