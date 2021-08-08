package com.midea.cloud.common.enums.pm.pr.requirement;

/**
 * <pre>
 *  采购需求-成本类型举类  字典编码:BUSINESS_GEN_TYPE
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
public enum BusinessGenType {
    BID("招标","BID"),
    INQ("询比价","INQ");

    public String name;
    public String value;
    private BusinessGenType(String name, String value){
        this.name = name;
        this.value = value;
    }

    /**根据value获取枚举值**/
    public static BusinessGenType get(String value){
        for(BusinessGenType rss : BusinessGenType.values()){
            if(rss.value.equals(value)){
                return rss;
            }
        }
        return null;
    }
}
