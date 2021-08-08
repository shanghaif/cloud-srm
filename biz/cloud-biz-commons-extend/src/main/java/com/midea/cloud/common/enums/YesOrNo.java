package com.midea.cloud.common.enums;

/**
 * 是或者否编码（Y:是 N:否）
 * <pre>
 * 引用功能模块：ALL
 * </pre>
 *
 * @author zouwei zouwei@midea.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public enum YesOrNo {

    YES("是", "Y"),
    NO("否", "N");

    private String name;
    private String value;

    private YesOrNo(String name, String value) {
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
    public static YesOrNo get( String value ){
        for(YesOrNo o:YesOrNo.values()){
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
