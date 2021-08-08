package com.midea.cloud.common.enums.supcooperate;

/**
 * <pre>
 *  功能名称描述: 发票通知单据状态枚举  INVOICE_NOTICE_STATUS
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-6-27 15:51
 *  修改内容:
 * </pre>
 */
public enum InvoiceNoticeStatus {

    /**
     * 拟定
     */
    DRAFT,

    /**
     * 已提交
     */
    SUBMITTED,
    /**
     * 供应商已确认(ceea)
     */
    CONFIRMED,
    /**
     * 供应商已确认(ceea)
     */
    REJECTED,

    /**
     * 审批中,发起了审批
     */
    UNDER_APPROVAL,

    /**
     * 初审通过
     */
    FIRST_REVIEW_APPROVED,

    /**
     * 终审通过
     */
    FINAL_REVIEW_APPROVED,

    /**
     * 已驳回
     */
    VENDOR_REJECTED,

    /**
     * 已废弃
     */
    ABANDONED,
    /**
     * 已撤回
     */
    WITHDRAW;
}
