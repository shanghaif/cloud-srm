package com.midea.cloud.common.enums;

/**
 * 准入流程
 * <pre>
 * 引用功能模块：供方准入流程配置
 * </pre>
 *
 * @author yanling.fang@meicloud.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public enum AccessProcessType {

    STRICT("严格流程", "STRICT"),
    ELASTIC("弹性流程", "ELASTIC"),
    SIMPLIFY("精简流程", "SIMPLIFY"),
    ULRA_SIMPLIFY("超精简流程", "ULRA_SIMPLIFY");

    private String name;
    private String value;

    private AccessProcessType(String name, String value) {
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
    public static AccessProcessType get( String value ){
        for(AccessProcessType o:AccessProcessType.values()){
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
