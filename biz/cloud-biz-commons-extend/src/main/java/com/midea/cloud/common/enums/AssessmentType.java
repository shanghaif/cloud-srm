package com.midea.cloud.common.enums;

/**
 * 评审类型
 * <pre>
 * 引用功能模块：现场评审
 * </pre>
 *
 * @author yanling.fang@meicloud.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public enum AssessmentType {

    ACCESS_ASSESSMENT("准入评审", "ACCESS_ASSESSMENT"),
    ANNUAL_REVIEW("日常评审", "ANNUAL_REVIEW");

    private String name;
    private String value;

    private AssessmentType(String name, String value) {
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
    public static AssessmentType get( String value ){
        for(AssessmentType o:AssessmentType.values()){
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
