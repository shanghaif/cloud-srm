package com.midea.cloud.common.enums.pm.pr.requirement;

/**
 * <pre>
 *  采购需求-创建方式枚举类
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/5/22 16:24
 *  修改内容:
 * </pre>
 */
public enum RequirementCreateType {
    CREATE_NEW("手动新建","CREATE_NEW"),
    MERGE_NEW("合并需求行时新建","MERGE_NEW");

    public String name;
    public String value;

    private RequirementCreateType(String name, String value){
        this.name = name;
        this.value = value;
    }

    /**根据value获取枚举值**/
    public static RequirementCreateType get(String value){
        for(RequirementCreateType rss : RequirementCreateType.values()){
            if(rss.value.equals(value)){
                return rss;
            }
        }
        return null;
    }
}
