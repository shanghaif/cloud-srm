package com.midea.cloud.common.enums.order;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: May 29, 20203:18:16 PM 
 *  修改内容:
 *          </pre>
 */
public enum ReturnOrderStatusEnum {

	EDIT("拟定", "EDIT"),
	CONFIRM("已确认", "CONFIRM");

    private String name;
    private String value;

    private ReturnOrderStatusEnum(String name, String value) {
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

    /**
     * 通过指定value值获取枚举
     *
     * @param value
     * @return
     */
    public static ReturnOrderStatusEnum get(String value) {
        for (ReturnOrderStatusEnum o : ReturnOrderStatusEnum.values()) {
            if (o.value.equals(value)) {
                return o;
            }
        }
        return null;
    }

    /**
     * 枚举值列表是否包含指定code
     *
     * @param code
     * @return true or false
     */
    public static boolean isContain(String code) {
        return (get(code) != null);
    }

}
