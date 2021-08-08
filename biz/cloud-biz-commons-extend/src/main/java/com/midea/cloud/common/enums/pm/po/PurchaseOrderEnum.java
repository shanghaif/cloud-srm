package com.midea.cloud.common.enums.pm.po;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * 
 * <pre>
 * 订单状态
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
public enum PurchaseOrderEnum {

	DRAFT("新建", "DRAFT"),
	UNDER_APPROVAL("审批中", "UNDER_APPROVAL"),
	APPROVED("已审批", "APPROVED"),
	REJECT("已驳回","REJECT"),
	ACCEPT("供方已确认", "ACCEPT"),
	REFUSED("已拒绝", "REFUSED"),
	ABANDONED("已废弃", "ABANDONED"),
	WITHDRAW("已撤回", "WITHDRAW"),
	SUBMITTED("已提交","SUBMITTED");

	private String name;
	private String value;

	PurchaseOrderEnum(String name, String value) {
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

	public static PurchaseOrderEnum get(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (PurchaseOrderEnum e : PurchaseOrderEnum.values()) {
				if (e.getValue().equals(value)) {
					return e;
				}
			}
		}
		return null;
	}
}
