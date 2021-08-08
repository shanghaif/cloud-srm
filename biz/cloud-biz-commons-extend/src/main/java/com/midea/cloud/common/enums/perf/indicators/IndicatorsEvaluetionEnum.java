package com.midea.cloud.common.enums.perf.indicators;

/**
 * <pre>
 *  评价方式枚举类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/5/26 14:21
 *  修改内容:
 * </pre>
 */
public enum IndicatorsEvaluetionEnum {
    SCORING_SYSTEM_VALUE("评分-系统取值", "SCORING_SYSTEM_VALUE"),
    DEDUCTION_SYSTEM_VALUE("扣分-系统取值", "DEDUCTION_SYSTEM_VALUE"),
    SCORING_MANUAL("评分-手工	", "SCORING_MANUAL"),
    DEDUCTION_MANUAL("扣分-手工", "DEDUCTION_MANUAL");

    private String name;
    private String value;
    private IndicatorsEvaluetionEnum(String name, String value){
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
     * Description 根据评价方式value获取枚举
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.26
     **/
    public static IndicatorsEvaluetionEnum get(String value){
        for(IndicatorsEvaluetionEnum v : IndicatorsEvaluetionEnum.values()){
            if(v.value.equals(value)){
                return v;
            }
        }
        return null;
    }

}
