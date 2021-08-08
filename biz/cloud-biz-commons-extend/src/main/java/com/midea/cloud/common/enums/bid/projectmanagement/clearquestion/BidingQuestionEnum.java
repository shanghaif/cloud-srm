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
public enum BidingQuestionEnum {
	/**
	 * 拟定：创建质疑，未提交
	 * 未澄清：提交质疑，采购商未澄清
	 * 已澄清：采购商针对此质疑已经发布澄清
	 * 已驳回：采购商驳回质疑
	 */
	DRAFT("拟定", "DRAFT"), SUBMITTED("已提交", "SUBMITTED"),
	CLARIFIED("已澄清", "CLARIFIED"), REJECTED("已驳回", "REJECTED");

	private String name;
	private String value;

	BidingQuestionEnum(String name, String value) {
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

	public static BidingQuestionEnum get(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (BidingQuestionEnum e : BidingQuestionEnum.values()) {
				if (e.getValue().equals(value)) {
					return e;
				}
			}
		}
		return null;
	}
}
