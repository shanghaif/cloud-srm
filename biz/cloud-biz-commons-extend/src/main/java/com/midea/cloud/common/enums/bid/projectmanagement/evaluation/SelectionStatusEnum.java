package com.midea.cloud.common.enums.bid.projectmanagement.evaluation;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * <pre>
 * 评选状态
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年4月7日 上午9:15:51  
 *  修改内容:
 *          </pre>
 */
public enum SelectionStatusEnum {
	WIN("中标", "WIN"),
	FIRST_WIN("第一中标","FIRST_WIN"),
	SECOND_WIN("第二中标","SECOND_WIN"),
	FAIL("落标", "FAIL"),
	ABORT("流标", "ABORT"),
	WAIT("待跟标", "WAIT"),
	FOLLOW("跟标", "FOLLOW"),
	INVALID("作废", "INVALID");

	private String name;
	private String value;

	SelectionStatusEnum(String name, String value) {
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

	public static SelectionStatusEnum get(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (SelectionStatusEnum e : SelectionStatusEnum.values()) {
				if (e.getValue().equals(value)) {
					return e;
				}
			}
		}
		return null;
	}
}
