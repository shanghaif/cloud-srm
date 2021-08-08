package com.midea.cloud.common.enums.logistics;

/**
 * 费项
 * <pre>
 * 引用功能模块：物流寻源
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 * <pre>
 * @update 2020-11-24 23:24
 * </pre>
 */

public enum ChargeName {

    TRANSPORT_EXPENSE("运输费用", "LAND_TRANSPORT"),
    SHORT_TRANSFER_EXPENSE("短驳费", "AIR_TRANSPORT"),
    PICK_UP_EXPENSE("提货费", "RAILWAY_TRANSPORT"),
    SEA_TRANSPORT_EXPENSE("海运费", "SEA_TRANSPORT"),
    UNPACKING_LOADING_EXPENSE("拆箱装车费", "MULTI_METHOD_TRANSPORT"),
    DELIVERY_EXPENSE("送货费", "EXPRESS_DELIVERY"),
    WHOLE_JOURNAL_EXPENSE("全程运费", "LAND_TRANSPORT"),
    DOC("DOC", "DOC"),
    THC("THC", "THC"),
    OFR("OFR", "OFR"),
    CCL("CCL", "CCL"),
    RFR("RFR", "RFR"),
    AFR("AFR", "AFR"),
    TFR("TFR", "TFR");

    private String name;
    private String value;

    private ChargeName(String name, String value) {
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
    public static ChargeName get(String value ){
        for(ChargeName o:ChargeName.values()){
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
