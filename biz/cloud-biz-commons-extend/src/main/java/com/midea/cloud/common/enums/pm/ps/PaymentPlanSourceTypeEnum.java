package com.midea.cloud.common.enums.pm.ps;

/**
 *
 * <pre>
 * 对账单
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jun 16, 20209:39:24 AM
 *  修改内容:
 *          </pre>
 */
public enum PaymentPlanSourceTypeEnum {

	ACCOUNT_STATEMENT("对账单", "ACCOUNT_STATEMENT"), CONTRACT("合同", "CONTRACT");

	private String name;
	private String value;

	PaymentPlanSourceTypeEnum(String name, String value) {
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

	public static PaymentPlanSourceTypeEnum get(String value) {
		for (PaymentPlanSourceTypeEnum o : PaymentPlanSourceTypeEnum.values()) {
			if (o.value.equals(value)) {
				return o;
			}
		}
		return null;
	}
}
