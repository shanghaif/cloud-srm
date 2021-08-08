package com.midea.cloud.common.enums.pm.po;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * 
 * <pre>
 * 订单行状态
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
public enum PurchaseOrderRowStatusEnum {

	PUBLISHED("已发布", "PUBLISHED"), UNPUBLISHED("未发布", "UNPUBLISHED"), RETURN("已驳回", "RETURN"), CLOSED("已关闭", "CLOSED"), ACCEPTED("已接受", "ACCEPTED"), DECLINE("已拒绝", "DECLINE");

	private String name;
	private String value;

	PurchaseOrderRowStatusEnum(String name, String value) {
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

	public static PurchaseOrderRowStatusEnum get(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (PurchaseOrderRowStatusEnum e : PurchaseOrderRowStatusEnum.values()) {
				if (e.getValue().equals(value)) {
					return e;
				}
			}
		}
		return null;
	}
}
