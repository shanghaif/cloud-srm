package com.midea.cloud.common.enums.bid.projectmanagement.bidinitiating;

/**
 * <pre>
 * 招标-招标立项-招标类型 字典码:BID_TYPE
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 19:09:20
 *  修改内容:
 * </pre>
 */
public enum BidType {

    BUSINESS("商务", "BUSINESS"),
    TECHNOLOGY_BUSINESS("技术+商务", "TECHNOLOGY_BUSINESS"),
    LOGISTICS("物流招标","Logistics");

    private String name;
    private String value;

    private BidType(String name, String value) {
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
    public static BidType get(String value) {
        for (BidType o : BidType.values()) {
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
