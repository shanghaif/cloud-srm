package com.midea.cloud.common.enums.perf.template;

/**
 * <pre>
 *  绩效模型-评价区间枚举类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/5/28 17:19
 *  修改内容:
 * </pre>
 * TEMPLATE_STATUS	模板状态	varChar(80)	N	DRAFT-拟定、VALID-有效、INVALID-失效
 */
public enum PerfTemplateEvaluationPeroidEnum {
    MONTHLY("月度","MONTHLY"),
    QUARTER("季度","QUARTER"),
    HALF_YEAR("半年","HALF_YEAR"),
    YEAR("YEAR","年度");

    private String name;
    private String value;
    private PerfTemplateEvaluationPeroidEnum(String name, String value){
        this.name = name;
        this.value = value;
    }

    /**
     * Description 根据评价区间value获取枚举
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.28
     **/
    public static PerfTemplateEvaluationPeroidEnum get(String value){
        for(PerfTemplateEvaluationPeroidEnum v : PerfTemplateEvaluationPeroidEnum.values()){
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
