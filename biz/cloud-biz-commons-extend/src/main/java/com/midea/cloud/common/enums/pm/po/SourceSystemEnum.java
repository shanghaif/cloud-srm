package com.midea.cloud.common.enums.pm.po;

import org.apache.commons.lang3.StringUtils;

/**
 *
 *
 *
 * <pre>
 * 来源系统
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年4月27日 下午2:48:38
 *  修改内容:
 *          </pre>
 */
public enum SourceSystemEnum {

	MANUAL("手工创建", "MANUAL"),
	PURCHASE_REQUIREMENT("采购需求", "PURCHASE_REQUIREMENT"),
	DEMAND("需求转订单","DEMAND");

	private String name;
	private String value;

	SourceSystemEnum(String name, String value) {
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

	public static SourceSystemEnum get(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (SourceSystemEnum e : SourceSystemEnum.values()) {
				if (e.getValue().equals(value)) {
					return e;
				}
			}
		}
		return null;
	}
}
