package com.midea.cloud.common.enums.sup;

public enum DemotionType {
    DEMOTION_TO_REGISTERED("终止合作", "DEMOTION_TO_REGISTERED"),
    DEMOTION_TO_YELLOW("降级至黄牌", "DEMOTION_TO_YELLOW"),
    DEMOTION_TO_RED("降级至红牌", "DEMOTION_TO_RED"),
    DEMOTION_TO_BLACK("降级至黑牌","DEMOTION_TO_BLACK"),
    YELLOW_IMPROVE("黄牌改善升级", "YELLOW_IMPROVE");

    private String name;
    private String value;

    private DemotionType(String name, String value) {
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
    public static DemotionType get(String value ){
        for(DemotionType o: DemotionType.values()){
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
