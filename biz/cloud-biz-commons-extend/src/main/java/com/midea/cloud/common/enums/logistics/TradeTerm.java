package com.midea.cloud.common.enums.logistics;

/**
 * 贸易术语
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

public enum TradeTerm {

    EXW("EXW", "EXW"),
    FCA("FCA", "FCA"),
    FOB("FOB", "FOB"),
    CIP("CIP", "CIP"),
    CIF("CIF", "CIF"),
    CPT("CPT", "CPT"),
    DAP("DAP", "DAP"),
    DDP("DDP", "DDP");

    private String name;
    private String value;

    private TradeTerm(String name, String value) {
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
    public static TradeTerm get(String value ){
        for(TradeTerm o:TradeTerm.values()){
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
