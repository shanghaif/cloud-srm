package com.midea.cloud.srm.model.base.material.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  采购物料维护状态
 * </pre>
 *
 * @author haiping2.li@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/22 11:40
 *  修改内容:
 * </pre>
 */
@AllArgsConstructor
@Getter
public enum CeeaMaterialStatus {
	NOT_NOTIFIED("NOT_NOTIFIED", "未通知"),
	NOTIFIED("NOTIFIED", "已通知"),
	MAINTAINED("MAINTAINED", "已维护");

	private final String    code;
	private final String    type;
}
