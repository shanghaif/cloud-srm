package com.midea.cloud.common.enums.pm.ps;

/**
 *
 * <pre>
 * 对账单状态
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jun 22, 20203:34:19 PM
 *  修改内容:
 *          </pre>
 */
public enum StatementStatusEnum {

	CREATE("拟态", "CREATE"), SUBMITTED("已提交", "SUBMITTED"), CANCAL("已作废", "CANCAL"), REJECTED("已驳回", "REJECTED"), PASS("已通过", "PASS");

	private String name;
	private String value;

	StatementStatusEnum(String name, String value) {
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

	public static StatementStatusEnum get(String value) {
		for (StatementStatusEnum o : StatementStatusEnum.values()) {
			if (o.value.equals(value)) {
				return o;
			}
		}
		return null;
	}
}
