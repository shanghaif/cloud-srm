package com.midea.cloud.common.enums.bargaining.projectmanagement.evaluation;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * <pre>
 * 评分维度
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年4月7日 下午4:33:58  
 *  修改内容:
 *          </pre>
 */
public enum ScoreDimensionEnum {
	PRICE("价格", "PRICE"), TECHNOLOGY("技术", "TECHNOLOGY"), ACHIEVEMENT("绩效", "ACHIEVEMENT");

	private String name;
	private String value;

	ScoreDimensionEnum(String name, String value) {
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

	public static ScoreDimensionEnum get(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (ScoreDimensionEnum e : ScoreDimensionEnum.values()) {
				if (e.getValue().equals(value)) {
					return e;
				}
			}
		}
		return null;
	}
}
