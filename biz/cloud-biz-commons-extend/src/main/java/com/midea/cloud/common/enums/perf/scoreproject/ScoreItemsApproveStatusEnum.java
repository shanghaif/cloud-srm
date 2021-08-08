package com.midea.cloud.common.enums.perf.scoreproject;

/**
 * <pre>
 *  绩效评分项目-审批状态枚举类
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
public enum ScoreItemsApproveStatusEnum {
    DRAFT("拟定","DRAFT"),
    SUBMITTED("已提交","SUBMITTED"),
    REJECTED("已驳回","REJECTED"),
    APPROVED("已批准","APPROVED"),
    ABANDONED("已废弃", "ABANDONED");

    private String name;
    private String value;
    ScoreItemsApproveStatusEnum(String name, String value){
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
    public static ScoreItemsApproveStatusEnum get(String value){
        for(ScoreItemsApproveStatusEnum v : ScoreItemsApproveStatusEnum.values()){
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
