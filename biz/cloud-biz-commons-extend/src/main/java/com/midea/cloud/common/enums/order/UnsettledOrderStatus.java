package com.midea.cloud.common.enums.order;

/**
 * <pre>
 *  未结算数量账单状态 UNSETTLED_ORDER_STATUS
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
public enum UnsettledOrderStatus {

	/**
	 * 拟态
	 */
	CREATE,
	/**
	 * 已提交
	 */
	SUBMIT,
	/**
	 * 已审核
	 */
	COMFIRM,
	/**
	 * 已驳回
	 */
	REFUSE,
	/**
	 * 已作废
	 */
	CANCAL,
	/**
	 * 供应商已确认
	 */
	SURE,
	/**
	 * 已完成
	 */
	FINISH
}
