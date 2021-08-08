package com.midea.cloud.srm.model.supplier.invite.enums;

public enum InviteVendorStatusEnum {
    DRAFT("拟定", "DRAFT"),
    INVITING("邀请中", "INVITING"),
    REGISTERED("已注册", "REGISTERED");

    private String name;
    private String value;

    private InviteVendorStatusEnum(String name, String value) {
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
    public static InviteVendorStatusEnum get(String value ){
        for(InviteVendorStatusEnum o: InviteVendorStatusEnum.values()){
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
