package com.midea.cloud.common.enums.bid.projectmanagement.techproposal;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * <pre>
 * 评分进度，评标进度
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年4月8日 下午3:06:27  
 *  修改内容:
 *          </pre>
 */
public enum ProgressEnum {
	FINISHED("已完成", "FINISHED"), UNFINISHED("未完成", "UNFINISHED");

	private String name;
	private String value;

	ProgressEnum(String name, String value) {
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

	public static ProgressEnum get(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (ProgressEnum e : ProgressEnum.values()) {
				if (e.getValue().equals(value)) {
					return e;
				}
			}
		}
		return null;
	}
}
