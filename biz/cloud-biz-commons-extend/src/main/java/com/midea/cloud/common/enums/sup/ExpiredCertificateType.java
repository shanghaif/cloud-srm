package com.midea.cloud.common.enums.sup;

public enum ExpiredCertificateType {
    REVIEW_FORM("资质审查", "REVIEW_FORM"),
    SAMPLE_FORM("样品确认", "SAMPLE_FORM"),
    AUTH_FORM("供应商评审","AHTU_FORM"),
    MATERIAL_FORM("物料试用", "MATERIAL_FORM"),
    COMPANY_INFO("公司营业证件","COMPANY_INFO"),
    MANAGEMENT_ATTACH("认证附件","MANAGEMENT_ATTACH");

    private String name;
    private String value;

    private ExpiredCertificateType(String name, String value) {
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
    public static ExpiredCertificateType get(String value ){
        for(ExpiredCertificateType o:ExpiredCertificateType.values()){
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
