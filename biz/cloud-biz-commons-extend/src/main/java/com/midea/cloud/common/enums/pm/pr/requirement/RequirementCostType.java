package com.midea.cloud.common.enums.pm.pr.requirement;

/**
 * <pre>
 *  采购需求-成本类型举类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/5/15 11:24
 *  修改内容:
 * </pre>
 */
public enum RequirementCostType {
    COST_CENTER("成本中心","COST_CENTER"),
    FEE("项目","FEE"),
    CAPITAL("资产","CAPITAL");

    public String name;
    public String value;
    private RequirementCostType(String name, String value){
        this.name = name;
        this.value = value;
    }

    /**根据value获取枚举值**/
    public static RequirementCostType get(String value){
        for(RequirementCostType rss : RequirementCostType.values()){
            if(rss.value.equals(value)){
                return rss;
            }
        }
        return null;
    }
}
