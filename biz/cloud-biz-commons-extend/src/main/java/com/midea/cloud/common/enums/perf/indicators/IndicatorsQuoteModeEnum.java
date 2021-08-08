package com.midea.cloud.common.enums.perf.indicators;

/**
 * <pre>
 *  取值方式 枚举类
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
public enum IndicatorsQuoteModeEnum {
    DIRECT_QUOTE("直接取值", "DIRECT_QUOTE"),
    TEXT_CONVERSION("按文本折算", "TEXT_CONVERSION"),
    INTERVAL_CONVERSION("按区间折算","INTERVAL_CONVERSION");

    private String name;
    private String value;
    private IndicatorsQuoteModeEnum(String name, String value){
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
     * Description 根据折算类型value获取枚举
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.26
     **/
    public static IndicatorsQuoteModeEnum get(String value){
        for(IndicatorsQuoteModeEnum v : IndicatorsQuoteModeEnum.values()){
            if(v.value.equals(value)){
                return v;
            }
        }
        return null;
    }

}
