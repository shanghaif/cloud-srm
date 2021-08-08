package com.midea.cloud.common.enums.perf.template;

/**
 * <pre>
 *  绩效模型-模板状态枚举类
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
 */
public enum PerfTemplateStatusEnum {
    DRAFT("拟定","DRAFT"),
    VALID("有效","VALID"),
    INVALID("失效","INVALID");

    private String name;
    private String value;
    private PerfTemplateStatusEnum(String name, String value){
        this.name = name;
        this.value = value;
    }

    /**
     * Description 根据模板状态value获取枚举
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.28
     **/
    public static PerfTemplateStatusEnum get(String value){
        for(PerfTemplateStatusEnum v : PerfTemplateStatusEnum.values()){
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
