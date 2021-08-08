package com.midea.cloud.common.enums.bid.projectmanagement.evaluation;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * <pre>
 *  评选方式
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 * <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年4月17日 下午1:41:32  
 *  修改内容: 
 * </pre>
 */
public enum EvaluateMethodEnum {
	LOWER_PRICE("合理低价法", "LOWER_PRICE"), HIGH_PRICE("合理高价法", "HIGH_PRICE"), COMPOSITE_SCORE("综合评分法", "COMPOSITE_SCORE");

	private String name;
	private String value;

	EvaluateMethodEnum(String name, String value) {
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

	public static EvaluateMethodEnum get(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (EvaluateMethodEnum e : EvaluateMethodEnum.values()) {
				if (e.getValue().equals(value)) {
					return e;
				}
			}
		}
		return null;
	}
}
