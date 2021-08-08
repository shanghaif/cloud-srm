package com.midea.cloud.common.enums.pm.po;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * 
 * <pre>
 * 收货状态
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
public enum ReceivedStatusEnum {

	/**
	 * 不要删掉[UNCONFIRMED]
	 */
	PROTOCOL("拟定", "PROTOCOL"), CONFIRM("确认", "CONFIRM"),UNCONFIRMED("待确认", "UNCONFIRMED");

	private String name;
	private String value;

	ReceivedStatusEnum(String name, String value) {
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

	public static ReceivedStatusEnum get(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (ReceivedStatusEnum e : ReceivedStatusEnum.values()) {
				if (e.getValue().equals(value)) {
					return e;
				}
			}
		}
		return null;
	}
}
