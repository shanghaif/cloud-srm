package com.midea.cloud.common.enums.bargaining.projectmanagement.evaluation;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * <pre>
 * 决标方式
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年4月17日 下午1:41:54  
 *  修改内容:
 *          </pre>
 */
public enum BidingAwardWayEnum {
	INDIVIDUAL_DECISION("单项决标", "INDIVIDUAL_DECISION"), COMBINED_DECISION("组合决标", "COMBINED_DECISION");

	private String name;
	private String value;

	BidingAwardWayEnum(String name, String value) {
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

	public static BidingAwardWayEnum get(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (BidingAwardWayEnum e : BidingAwardWayEnum.values()) {
				if (e.getValue().equals(value)) {
					return e;
				}
			}
		}
		return null;
	}
}
