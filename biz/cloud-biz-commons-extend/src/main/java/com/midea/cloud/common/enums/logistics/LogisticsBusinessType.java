package com.midea.cloud.common.enums.logistics;

/**
 * 业务模式
 * <pre>
 * 物流寻源业务类型：物流寻源
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 * <pre>
 * @update 2020-12-07 17:00
 * </pre>
 */

public enum LogisticsBusinessType {

    PROJECT("项目类", "PROJECT"),
    NOT_PROJECT("非项目类", "NOT_PROJECT");

    private String name;
    private String value;

    private LogisticsBusinessType(String name, String value) {
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
    public static LogisticsBusinessType get(String value ){
        for(LogisticsBusinessType o:LogisticsBusinessType.values()){
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
