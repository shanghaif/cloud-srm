package com.midea.cloud.common.enums.logistics;

/**
 * 运输方式
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

public enum TransportMode {

    LAND_TRANSPORT("陆运", "LAND_TRANSPORT"),
    AIR_TRANSPORT("空运", "AIR_TRANSPORT"),
    RAILWAY_TRANSPORT("铁运", "RAILWAY_TRANSPORT"),
    SEA_TRANSPORT("海运", "SEA_TRANSPORT"),
    MULTI_METHOD_TRANSPORT("多式联运", "MULTI_METHOD_TRANSPORT"),
    EXPRESS_DELIVERY("快递", "EXPRESS_DELIVERY");

    private String name;
    private String value;

    private TransportMode(String name, String value) {
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
    public static TransportMode get(String value ){
        for(TransportMode o:TransportMode.values()){
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
