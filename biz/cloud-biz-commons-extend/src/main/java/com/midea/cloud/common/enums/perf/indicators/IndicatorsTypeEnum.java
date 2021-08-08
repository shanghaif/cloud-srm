package com.midea.cloud.common.enums.perf.indicators;

/**
 * <pre>
 *  指标类型枚举类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/5/26 14:04
 *  修改内容:
 * </pre>
 */
public enum IndicatorsTypeEnum {
    INDICATOR("绩效", "PERFORMANCE"),
    ASSESSMENT("考核", "ASSESSMENT");

    private String name;
    private String value;
    private IndicatorsTypeEnum(String name, String value){
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
     * Description 根据指标类型value获取枚举
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.26
     **/
    public static IndicatorsTypeEnum get(String value){
        for(IndicatorsTypeEnum v : IndicatorsTypeEnum.values()){
            if(v.value.equals(value)){
                return v;
            }
        }
        return null;
    }

    /**
     * Description 根据指标类型value获取中文
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.26
     **/
    public static String getName(String value){
        for(IndicatorsTypeEnum v : IndicatorsTypeEnum.values()){
            if(v.value.equals(value)){
                return v.name;
            }
        }
        return null;
    }

}
