package com.midea.cloud.common.enums.pm.ps;

/**
 * <pre>
 *    预付申请状态枚举  字典码ADVANCE_APPLY_STATUS
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/20 14:06
 *  修改内容:
 * </pre>
 */
public enum AdvanceApplyStatus {

    DRAFT("新建", "DRAFT"),
    SUBMITTED("已提交", "SUBMITTED"),
    UNDER_APPROVAL("审批中", "UNDER_APPROVAL"),
    REJECTED("已驳回", "REJECTED"),
    APPROVAL("已审批", "APPROVAL"),
    ABANDONED("已废弃", "ABANDONED"),
    WITHDRAW("已撤回", "WITHDRAW"),
    DROP("作废", "DROP");


    private String name;
    private String value;

    private AdvanceApplyStatus(String name, String value) {
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
}
