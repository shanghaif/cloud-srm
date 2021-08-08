package com.midea.cloud.common.enums.flow;

/**
 * <pre>
 *  cbpm流程业务系统模块(对应cbpm流程的fdModuleId业务系统模块id)
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/20
 *  修改内容:
 * </pre>
 * BASE
 * 中文
 *
 * 2020-03-23
 * 2
 * SUP
 * 中文
 * 供应商模块
 * 2020-03-23
 * 3

 *
 */
public enum CbpmModuleIdEnum {
    BASE("BASE", "基础模块"),
    SUP("SUP","供应商模块"),
    SUPAUTH("SUPAUTH","供应商准入模块"),
    SUPCOOPERATE("SUPCOOPERATE", "供应商协同模块"),
    BID("BID","招投标模块"),
    INQ("INQ","询比价模块"),
    FLOW("FLOW","流程中心模块"),
    LOG("LOG", "日志中心模块"),
    FILE("FILE","文件中心模块"),
    OAUTH("OAUTH","认证中心模块"),
    RBAC("RBAC","用户角色中心模块OAUTH");

    private String key;
    private String value;
    CbpmModuleIdEnum(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
