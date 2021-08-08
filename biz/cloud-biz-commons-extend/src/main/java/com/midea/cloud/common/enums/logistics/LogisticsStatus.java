package com.midea.cloud.common.enums.logistics;

/**
 * 物流寻源模块 公用状态
 * <pre>
 * 引用功能模块：物流寻源
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 * <pre>
 * @update 2020-11-27 17:24
 * </pre>
 */

public enum LogisticsStatus {

    DRAFT("拟定", "DRAFT"),
    EFFECTIVE("生效", "EFFECTIVE"),
    INEFFECTIVE("失效", "INEFFECTIVE");

    private String name;
    private String value;

    private LogisticsStatus(String name, String value) {
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
    public static LogisticsStatus get(String value ){
        for(LogisticsStatus o:LogisticsStatus.values()){
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
