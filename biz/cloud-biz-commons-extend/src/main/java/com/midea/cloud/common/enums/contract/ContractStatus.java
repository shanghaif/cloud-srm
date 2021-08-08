package com.midea.cloud.common.enums.contract;

/**
 * <pre>
 *  合同状态
 * </pre>
 *
 * @author ex_lizp6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-5-27 13:39
 *  修改内容:
 * </pre>
 */
public enum ContractStatus {

    /**
     * 拟定
     */
    DRAFT,

    /**
     * 待审核
     */
    UNDER_REVIEW,

    /**
     * 未发布
     */
    UNPUBLISHED,

    /**
     * 已驳回
     */
    REJECTED,

    /**
     * 待供应商确认
     */
    SUPPLIER_CONFIRMING,

    /**
     * 供应商已确认
     */
    SUPPLIER_CONFIRMED,

    /**
     * 已归档
     */
    ARCHIVED,

    /**
     * 审核拒绝
     */
    REFUSED,
    /**
     * 已废弃
     */
    ABANDONED,

    /**
     * 关闭
     */
    CLOSE,

    /**
     *已撤回
     */
    WITHDRAW;
}
