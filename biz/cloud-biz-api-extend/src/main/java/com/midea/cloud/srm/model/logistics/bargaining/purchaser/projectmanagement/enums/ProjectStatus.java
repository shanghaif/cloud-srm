package com.midea.cloud.srm.model.logistics.bargaining.purchaser.projectmanagement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tanjl11
 * @date 2020/12/07 13:21
 */
@Getter
@AllArgsConstructor
public enum ProjectStatus {
    DRAFT("DRAFT", "拟定"),
    ACCEPT_BID("ACCEPT_BID", "接收报价中"),
    BID_CLOSED("BID_CLOSED", "已截止报价"),
    SELECTION("SELECTION", "评选中"),
    MUTI_ROUND_QUOTATION("MUTI_ROUND_QUOTATION", "多轮报价中"),
    AWARD_SUBMIT("AWARD_APPROVAL", "决标审批中"),
    AWARD_APPROVAL("AWARD_APPROVAL", "决标已审批"),
    END("END", "结束"),
    CANCEL("CANCEL", "已取消"),
    WITHDRAW("WITHDRAW", "已撤回");


    private final String code;
    private final String value;

}
