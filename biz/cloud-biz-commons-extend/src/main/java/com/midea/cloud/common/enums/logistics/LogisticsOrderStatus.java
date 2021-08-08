package com.midea.cloud.common.enums.logistics;

/**
 * 物流采购订单状态 字典编码 LOGISTICS_PRICE_STATUS
 * <pre>
 * 引用功能模块：物流寻源
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 * <pre>
 * @update 2020-11-27 17:24
 * </pre>
 */

public enum LogisticsOrderStatus {

    DRAFT("拟定", "DRAFT"),
    WAITING_CONFIRM("待确认", "WAITING_CONFIRM"),
    WAITING_VENDOR_CONFIRM("待供方确认", "WAITING_VENDOR_CONFIRM"),
    COMPLETED("完成", "COMPLETED"),
//    REFUSE("拒绝", "REFUSE"),
//    ACCEPT("接受", "ACCEPT"),
    CANCEL("作废", "CANCEL");

    private String name;
    private String value;

    private LogisticsOrderStatus(String name, String value) {
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
     *
     * 通过指定value值获取枚举
     * @param value
     * @return
     */
    public static LogisticsOrderStatus get(String value ){
        for(LogisticsOrderStatus o: LogisticsOrderStatus.values()){
            if(o.value.equals(value)){
                return o;
            }
        }
        return null;
    }

    /**
     * 枚举值列表是否包含指定code
     * @param code
     * @return true or false
     */
    public static boolean isContain( String code ){
        return (get(code)!=null);
    }

}
