package com.midea.cloud.common.enums.bargaining.projectmanagement.techscore;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * <pre>
 * 评分状态
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年4月17日 下午1:44:46  
 *  修改内容:
 *          </pre>
 */
public enum ScoreStatusEnum {
	
	UNFINISHED("未完成", "UNFINISHED"), FINISHED("已完成", "FINISHED");
	
	private String name;
	private String value;

	ScoreStatusEnum(String name, String value) {
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

	public static ScoreStatusEnum get(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (ScoreStatusEnum e : ScoreStatusEnum.values()) {
				if (e.getValue().equals(value)) {
					return e;
				}
			}
		}
		return null;
	}
}
