package com.midea.cloud.common.enums.logistics;

/**
 * <pre>
 *  计费单位
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

public enum SubLevel {

    SEVENTEEN_FIVE_METER("17.5米", "SEVENTEEN_FIVE_METER"),
    THIRTEEN_METER("13米", "THIRTEEN_METER"),
    NINE_SIX_METER("9.6米", "NINE_SIX_METER"),
    SIX_EIGHT_METER("6.8米", "SIX_EIGHT_METER"),
    FOUR_TWO_METER("4.2米", "FOUR_TWO_METER"),
    TWENTY_GP("20GP", "TWENTY_GP"),
    FORTY_GP("40GP", "FORTY_GP"),
    FORTY_HQ("40HQ", "FORTY_HQ"),
    FORTYFIVE_HQ("45HQ", "FORTYFIVE_HQ"),
    FORTY_OT("40OT", "FORTY_OT"),
    TWENTY_FR("20FR", "TWENTY_FR"),
    FORTY_FR("40FR", "FORTY_FR"),
    TWENTY_OT("20OT", "TWENTY_OT"),
    EIGHT_SIX_METER("8.6米", "EIGHT_SIX_METER"),
    NINE_FOUR_METER("9.4米", "NINE_FOUR_METER"),
    BULK_LOAD("散货", "BULK_LOAD"),
    THREE_T("3T", "THREE_T"),
    FIVE_T("5T", "FIVE_T"),
    EIGHT_T("8T", "EIGHT_T"),
    TEN_T("10T", "TEN_T"),
    LCL("LCL", "LCL"),
    AIR("AIR", "AIR"),
    L1("L1", "L1"),
    L2("L2", "L2"),
    L3("L3", "L3"),
    L4("L4", "L4"),
    L5("L5", "L5"),
    L6("L6", "L6");

    private String name;
    private String value;

    private SubLevel(String name, String value) {
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
    public static SubLevel get(String value ){
        for(SubLevel o:SubLevel.values()){
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
