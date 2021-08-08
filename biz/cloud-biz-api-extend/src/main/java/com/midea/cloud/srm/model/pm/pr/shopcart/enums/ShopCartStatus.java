package com.midea.cloud.srm.model.pm.pr.shopcart.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  购物车行状态
 * </pre>
 *
 * @author haiping2.li@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/18 17:19
 *  修改内容:
 * </pre>
 */
@AllArgsConstructor
@Getter
public enum ShopCartStatus {
	DRAFT("DRAFT", "未提交"),
	SUBMITTED("SUBMITTED", "已提交"),
	APPLIED("APPLIED", "已生成申请");

	private final String    code;
	private final String    type;
}
