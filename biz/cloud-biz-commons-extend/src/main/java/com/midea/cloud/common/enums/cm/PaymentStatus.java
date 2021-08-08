package com.midea.cloud.common.enums.cm;

/**
 * <pre>
 * 付款申请状态
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/26
 *  修改内容:
 * </pre>
 */
public enum PaymentStatus {
    DRAFT("DRAFT","新建"),
    UNDER_APPROVAL("UNDER_APPROVAL","审批中"),
    APPROVAL("APPROVAL","已审批"),
    DROP("DROP","已作废"),
    REJECT("REJECTED", "已驳回");

    private String key;
    private String value;

    PaymentStatus(String key, String value) {
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
