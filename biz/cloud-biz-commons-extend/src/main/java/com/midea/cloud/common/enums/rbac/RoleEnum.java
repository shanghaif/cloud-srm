package com.midea.cloud.common.enums.rbac;

/**
 * 角色枚举;缺失的自行添加
 */
public enum RoleEnum {

    LONGI_DG("需求组" , "LONGI_DG"),
    LONGI_IT("物控组-IT","LONGI_IT"),
    LONGI_DD("物控组(目录化）","LONGI_DD"),
    SYSTEM_ADMIN("系统管理","SystemAdmin"),
    LONGI_CH("财务查询组" , "LONGI_CH"),
    LONGI_PPA("采购策略组" , "LONGI_PPA"),
    LONGI_PPDM("采购履行部经理（三级部门负责人）" , "LONGI_PPDM"),
    LONGI_PSDM("采购策略部经理（三级部门负责人）","LONGI_PSDM"),
    LONGI_PFGP("采购履行组" , "LONGI_PFGP"),
    LONGI_SM("供应商管理" , "LONGI_SM"),
    LONGI_OM("流程优化组" , "LONGI_OM"),
    LONGI_CR("查询组" , "LONGI_CR"),
    LONGI_LG("法务组" , "LONGI_LG"),
    ;

    private String roleName;

    private String roleCode;

    RoleEnum(String roleName , String roleCode){
        this.roleCode = roleCode;
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
