package com.midea.cloud.common.enums.sup;

public enum VendorSiteCode {
    ASSET("固定资产", "ASSET"),
    MATERIAL("材料", "MATERIAL"),
    EXPENSE("费用", "EXPENSE"),
    CONSIGNMENT("寄售","CONSIGNMENT");

    private String name;
    private String value;

    private VendorSiteCode(String name, String value) {
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
    public static VendorSiteCode get(String value ){
        for(VendorSiteCode o:VendorSiteCode.values()){
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
