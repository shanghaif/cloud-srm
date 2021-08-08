package com.midea.cloud.common.enums.order;

/**
 * <pre>
 *  送货单状态 DELIVERY_NOTE_STATUS
 * </pre>
 *
 * @author huanghb3@meicloud.com
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
public enum DeliveryNoteStatus {

    /**
     * 拟定
     */
    CREATE,
    /**
     * 已提交
     */
    SUBMITTED,
    /**
     * 已确认发货
     */
    DELIVERED,
    /**
     * 已驳回
     */
    REJECTED,
    /**
     * 待取消
     */
    CANCELLING,
    /**
     * 已取消
     */
    CANCELLED

}
