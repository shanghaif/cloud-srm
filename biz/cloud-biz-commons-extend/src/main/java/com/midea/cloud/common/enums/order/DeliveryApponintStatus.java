package com.midea.cloud.common.enums.order;

/**
 * <pre>
 *  送货预约单状态 DELIVERY_APPONINT_STATUS
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
public enum DeliveryApponintStatus {

	/**
	 * 待提交(供应商)
	 */
	CREATE,
	/**
	 * 待审核(供应商)
	 */
	SUBMIT,
	/**
	 * 已审核（采购商）
	 */
	COMFIRM,
	/**
	 * 驳回（采购商）
	 */
	REFUSE
}
