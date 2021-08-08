package com.midea.cloud.common.enums.logistics;

/**
 * <pre>
 *  计费方式
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/25 0:48
 *  修改内容:
 * </pre>
 */
public enum ChargeLevel {

    CONSIGNMENT("托位", "LAND_TRANSPORT"),
    WEIGHT("毛重", "WEIGHT"),
    VOLUME("体积", "VOLUME"),
    VEHICLE("车", "VEHICLE"),
    TRAY("托盘", "TRAY"),
    CONTAINER("集装箱", "CONTAINER"),
    BIL("BIL", "BIL"),
    CTN("CTN", "CTN"),
    PLT("PLT", "PLT"),
    SET("SET", "SET"),
    VAL("VAL", "VAL"),
    VOL("VOL", "VOL"),
    WT("WT", "WT"),
    LO("LO", "LO"),
    TRK("TRK", "TRK"),
    CU("CU", "CU");

    private String name;
    private String value;

    private ChargeLevel(String name, String value) {
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
    public static ChargeLevel get(String value ){
        for(ChargeLevel o:ChargeLevel.values()){
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
