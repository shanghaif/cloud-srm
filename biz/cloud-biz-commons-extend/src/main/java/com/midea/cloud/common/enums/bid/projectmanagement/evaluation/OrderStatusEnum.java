package com.midea.cloud.common.enums.bid.projectmanagement.evaluation;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * <pre>
 * 投标状态
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年4月17日 下午1:42:32  
 *  修改内容:
 *          </pre>
 */
public enum OrderStatusEnum {
	DRAFT("暂存", "DRAFT"), SUBMISSION("提交", "SUBMISSION"), WITHDRAW("撤回", "WITHDRAW"), INVALID("作废", "INVALID");

	private String name;
	private String value;

	OrderStatusEnum(String name, String value) {
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

	public static OrderStatusEnum get(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (OrderStatusEnum e : OrderStatusEnum.values()) {
				if (e.getValue().equals(value)) {
					return e;
				}
			}
		}
		return null;
	}
}
