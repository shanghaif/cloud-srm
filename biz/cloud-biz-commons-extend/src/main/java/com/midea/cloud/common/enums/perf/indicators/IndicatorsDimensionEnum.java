package com.midea.cloud.common.enums.perf.indicators;

/**
 * <pre>
 * 指标维护枚举类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/5/26 14:14
 *  修改内容:
 * </pre>
 */
public enum IndicatorsDimensionEnum {
    QUALITY("品质","QUALITY"),
    SERVICE("服务","SERVICE"),
    DELIVER("交付","DELIVER"),
    COST("成本","COST"),
    COMPREHENSIVE("综合","COMPREHENSIVE"),
    TECHNOLOGY("技术","TECHNOLOGY");

    private String name;
    private String value;

    private IndicatorsDimensionEnum(String name, String value){
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
     * Description 根据指标维护value获取枚举
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.26
     **/
    public static IndicatorsDimensionEnum get(String value){
        for(IndicatorsDimensionEnum v : IndicatorsDimensionEnum.values()){
            if(v.value.equals(value)){
                return v;
            }
        }
        return null;
    }

    /**
     * Description 根据指标维护value获取枚举
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.26
     **/
    public static String getName(String value){
        for(IndicatorsDimensionEnum v : IndicatorsDimensionEnum.values()){
            if(v.value.equals(value)){
                return v.getName();
            }
        }
        return null;
    }
}
