package com.midea.cloud.common.enums.pm.ps;

/**
 *
 * <pre>
 * 付款计划_单据状态
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jun 16, 20201:56:16 PM
 *  修改内容:
 *          </pre>
 */
public enum PaymentSchedulesStatusEnum {

	DRAFT("拟定", "DRAFT"), UNDER_REVIEW("审核中", "UNDER_REVIEW"), REJECTED("已驳回", "REJECTED"), APPROVED("已通过", "APPROVED");

	private String name;
	private String value;

	PaymentSchedulesStatusEnum(String name, String value) {
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

	public static PaymentSchedulesStatusEnum get(String value) {
		for (PaymentSchedulesStatusEnum o : PaymentSchedulesStatusEnum.values()) {
			if (o.value.equals(value)) {
				return o;
			}
		}
		return null;
	}
}
