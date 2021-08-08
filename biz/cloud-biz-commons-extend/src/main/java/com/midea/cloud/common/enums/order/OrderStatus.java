package com.midea.cloud.common.enums.order;

/**
 * <pre>
 *  订单状态
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
public enum OrderStatus {

	/**
	 * 拟定（采购商）
	 */
	CREATE,
	/**
	 * 已提交（采购商）
	 */
	SUBMIT,
	/**
	 * 已确认（供应商）
	 */
	COMFIRM,
	/**
	 * 已拒绝（供应商）
	 */
	REFUSE
}
