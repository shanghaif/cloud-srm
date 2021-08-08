package com.midea.cloud.common.enums.inq;

import com.midea.cloud.common.enums.pm.po.CeeaOrderAttachTypeEnum;

public enum SymbolTypeEnum{
    EQ("EQ","="),
    GT("GT",">"),
    GE("GE",">="),
    LT("LT","<"),
    LE("LE","<=");
    private String key;
    private String value;

    SymbolTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    /**根据value获取枚举值**/
    public static String get(String value){
        for(SymbolTypeEnum symbolType: SymbolTypeEnum.values()){
            if(symbolType.value.equals(value)){
                return symbolType.value;
            }
        }
        return null;
    }
}
