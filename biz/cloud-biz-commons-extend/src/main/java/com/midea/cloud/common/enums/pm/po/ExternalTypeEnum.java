package com.midea.cloud.common.enums.pm.po;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * <pre>
 * 采购订单 - 外部类型
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jun 24, 20205:48:52 PM
 *  修改内容:
 *          </pre>
 */
public enum ExternalTypeEnum {

	CONTRACT("合同", "CONTRACT"), REQUIREMENT("需求", "REQUIREMENT");

	private String name;
	private String value;

	ExternalTypeEnum(String name, String value) {
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

	public static ExternalTypeEnum get(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (ExternalTypeEnum e : ExternalTypeEnum.values()) {
				if (e.getValue().equals(value)) {
					return e;
				}
			}
		}
		return null;
	}
}
