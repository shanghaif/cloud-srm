package com.midea.cloud.common.enums.pm.ps;

/**
 *
 * <pre>
 *
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jun 11, 202011:11:03 AM
 *  修改内容:
 *          </pre>
 */
public enum StatementLineTypeEnum {

	RECEIPT("入库", "RECEIPT"), RETURN("退货", "RETURN");

	private String name;
	private String value;

	StatementLineTypeEnum(String name, String value) {
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

	public static StatementLineTypeEnum get(String value) {
		for (StatementLineTypeEnum o : StatementLineTypeEnum.values()) {
			if (o.value.equals(value)) {
				return o;
			}
		}
		return null;
	}
}
