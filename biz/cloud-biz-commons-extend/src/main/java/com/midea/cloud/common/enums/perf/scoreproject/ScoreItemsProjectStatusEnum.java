package com.midea.cloud.common.enums.perf.scoreproject;

/**
 * <pre>
 *  绩效评分项目项目状态枚举类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/6/5 17:59
 *  修改内容:
 * </pre>
 */
public enum ScoreItemsProjectStatusEnum {
    SCORE_DRAFT("拟定","SCORE_DRAFT"),
    SCORE_NOTIFIED("已通知评分","SCORE_NOTIFIED"),
    SCORE_CALCULATED("已计算评分","SCORE_CALCULATED"),
    RESULT_NO_PUBLISHED("结果未发布","RESULT_NO_PUBLISHED"),
    RESULT_PUBLISHED("结果已发布","RESULT_PUBLISHED"),
    OBSOLETE("已废弃","OBSOLETE");

    private String name;
    private String value;
    ScoreItemsProjectStatusEnum(String name, String value){
        this.name = name;
        this.value = value;
    }

    /**
     * Description 根据项目状态value获取枚举类
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.05
     **/
    public static ScoreItemsProjectStatusEnum get(String value){
        for(ScoreItemsProjectStatusEnum v : ScoreItemsProjectStatusEnum.values()){
            if(v.value.equals(value)){
                return v;
            }
        }
        return null;
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
}
