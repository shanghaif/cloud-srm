package com.midea.cloud.common.enums.bargaining.projectmanagement.projectpublish;

/**
 * <pre>
 * 招投标审批状态 字典码:BIDDING_APPROVAL_STATUS
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-03 14:53:20
 *  修改内容:
 * </pre>
 */
public enum BiddingApprovalStatus {

    DRAFT("拟定", "DRAFT"),
    SUBMITTED("已提交", "SUBMITTED"),
    REJECTED("已驳回", "REJECTED"),
    WITHDRAW("已撤回", "WITHDRAW"),
    ABANDONED("已废弃", "ABANDONED"),
    APPROVED("已审批", "APPROVED");

    private String name;
    private String value;

    private BiddingApprovalStatus(String name, String value) {
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
     * 通过指定value值获取枚举
     *
     * @param value
     * @return
     */
    public static BiddingApprovalStatus get(String value) {
        for (BiddingApprovalStatus o : BiddingApprovalStatus.values()) {
            if (o.value.equals(value)) {
                return o;
            }
        }
        return null;
    }

    /**
     * 枚举值列表是否包含指定code
     *
     * @param code
     * @return true or false
     */
    public static boolean isContain(String code) {
        return (get(code) != null);
    }

}
