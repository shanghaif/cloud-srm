package com.midea.cloud.common.enums.pm.pr.requirement;

/**
 * <pre>
 *  采购需求-有效价格举类
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
public enum RequirementEffectivePrice {
    HAVE("有","Y"),
    NOT("无","N");

    public String name;
    public String value;
    private RequirementEffectivePrice(String name, String value){
        this.name = name;
        this.value = value;
    }

    /**根据value获取枚举值**/
    public static RequirementEffectivePrice get(String value){
        for(RequirementEffectivePrice rss : RequirementEffectivePrice.values()){
            if(rss.value.equals(value)){
                return rss;
            }
        }
        return null;
    }
}
