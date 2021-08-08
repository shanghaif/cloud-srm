package com.midea.cloud.common.enums;

/**
 * 币种
 * <pre>
 * 引用功能模块：ALL
 * </pre>
 *
 * @author zhuwl7@meicloud.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public enum OcrCurrencyType {

    USD("美元", "USD"),
    CNY("人民币", "CNY"),
    HKD("港元", "HKD");

    private String name;
    private String value;

    private OcrCurrencyType(String name, String value) {
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
    public static OcrCurrencyType get(String value ){
        for(OcrCurrencyType o: OcrCurrencyType.values()){
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
