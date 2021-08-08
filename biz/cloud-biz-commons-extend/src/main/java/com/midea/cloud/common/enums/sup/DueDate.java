package com.midea.cloud.common.enums.sup;

/**
 * <pre>
 *  供应商证件到期提醒到期时间枚举类
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/13 19:54
 *  修改内容:
 * </pre>
 */

public enum DueDate {
    SIX_MONTHS("6个月", "SIX_MONTHS"),
    THREE_MONTHS("3个月", "THREE_MONTHS"),
    ONE_MONTH("1个月", "ONE _MONTH");

    private String name;
    private String value;

    private DueDate(String name, String value) {
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
    public static DueDate get(String value ){
        for(DueDate o:DueDate.values()){
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
