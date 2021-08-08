package com.midea.cloud.common.enums.perf.indicators;

/**
 * <pre>
 *  指标行类型 枚举类
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
public enum IndicatorsLineTypeEnum {
    TEXT("文本", "TEXT"),
    NUMBER("数字", "NUMBER"),
    PERCENTAGE("百分比", "PERCENTAGE");

    private String name;
    private String value;
    private IndicatorsLineTypeEnum(String name, String value){
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
     * Description 根据指标行类型value获取枚举
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.26
     **/
    public static IndicatorsLineTypeEnum get(String value){
        for(IndicatorsLineTypeEnum v : IndicatorsLineTypeEnum.values()){
            if(v.value.equals(value)){
                return v;
            }
        }
        return null;
    }

}
