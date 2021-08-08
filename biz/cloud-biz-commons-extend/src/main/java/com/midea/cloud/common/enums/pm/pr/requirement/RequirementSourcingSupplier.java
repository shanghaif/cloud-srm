package com.midea.cloud.common.enums.pm.pr.requirement;

/**
 * <pre>
 *  采购需求-货源供应商枚举类
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
public enum RequirementSourcingSupplier {
    HAVE("有","Y"),
    NOT("无","N");

    public String name;
    public String value;
    private RequirementSourcingSupplier(String name, String value){
        this.name = name;
        this.value = value;
    }

    /**根据value获取枚举值**/
    public static RequirementSourcingSupplier get(String value){
        for(RequirementSourcingSupplier rss : RequirementSourcingSupplier.values()){
            if(rss.value.equals(value)){
                return rss;
            }
        }
        return null;
    }
}
