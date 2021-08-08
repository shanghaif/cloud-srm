package com.midea.cloud.common.enums.rbac;

/**
 * <pre>
 * 角色-功能配置类型枚举类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/26
 *  修改内容:
 * </pre>
 */
public enum RoleFuncSetTypeEnum {
    OU("OU","按业务实体"),
    BU("BU","按事业部"),
    INVENTORY("INVENTORY","按库存组织"),
    CATEGORY("CATEGORY","按品类分工"),
    OU_CATEGORY("OU_CATEGORY","按OU+品类"),
    ALL("ALL","全部");

    private String key;
    private String name;
    RoleFuncSetTypeEnum(String key, String name){
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
