package com.midea.cloud.common.enums.bid.projectmanagement.clearquestion;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * <pre>
 * 投标状态
 * </pre>
 * 
 * @author zhuomb1@midea.com
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
public enum BidingAnswerEnum {
	/**
	 * 拟定：澄清已保存未发布
	 * 已完成：所有供应商都已确认澄清内容
	 */
	DRAFT("拟定", "DRAFT"), ISSUED("已发布", "ISSUED"), FINISHED("已完成", "FINISHED");

	private String name;
	private String value;

	BidingAnswerEnum(String name, String value) {
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

	public static BidingAnswerEnum get(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (BidingAnswerEnum e : BidingAnswerEnum.values()) {
				if (e.getValue().equals(value)) {
					return e;
				}
			}
		}
		return null;
	}
}
