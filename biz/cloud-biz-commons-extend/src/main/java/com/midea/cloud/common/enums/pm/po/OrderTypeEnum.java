package com.midea.cloud.common.enums.pm.po;

import org.apache.commons.lang3.StringUtils;

/**
 *
 *
 *
 * <pre>
 * 订单类型
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年5月26日 下午19:33:38
 *  修改内容:
 *          </pre>
 */
public enum OrderTypeEnum {

	NORMAL("常规采购", "NORMAL"),
	URGENT("紧急采购", "URGENT"),
	DEVELOP("研发采购", "DEVELOP"),
	ZERO_PRICE("零价格采购", "ZERO_PRICE"),
	CONVENIENT("便捷采购","CONVENIENT"),
	CONSIGNMENT("寄售订单","CONSIGNMENT"),
	SERVICE("服务采购","SERVICE"),
	APPOINT("指定采购","APPOINT"),
	OUT("废旧物资处置","OUT");


	/*便捷采购*/
	/*寄售*/

	private String name;
	private String value;

	OrderTypeEnum(String name, String value) {
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

	public static OrderTypeEnum get(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (OrderTypeEnum e : OrderTypeEnum.values()) {
				if (e.getValue().equals(value)) {
					return e;
				}
			}
		}
		return null;
	}

	public static String getValueByname(String name){
		if(StringUtils.isNotBlank(name)){
			for (OrderTypeEnum e : OrderTypeEnum.values()) {
				if (e.getName().equals(name)) {
					return e.getValue();
				}
			}
		}
		return null;
	}
}
