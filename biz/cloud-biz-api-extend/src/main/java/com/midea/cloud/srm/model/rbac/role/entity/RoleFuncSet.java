package com.midea.cloud.srm.model.rbac.role.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;

import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
*  <pre>
 *  角色-功能表 模型
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-25
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_rbac_role_func_set")
public class RoleFuncSet extends BaseEntity implements Serializable  {

    private static final long serialVersionUID = 1L;

    /**
     * 角色-功能表ID
     */
    @TableId("ROLE_FUNC_SET_ID")
    private Long roleFuncSetId;

    /**
     * 功能表ID
     */
    @TableField("FUNCTION_ID")
    @NotEmpty(message = "功能表id不能为空")
    private Long functionId;

    /**
     * 功能编码
     */
    @TableField("FUNCTION_CODE")
    @NotEmpty(message = "功能编码不能为空")
    private String functionCode;

    /**
     * 功能名称
     */
    @TableField("FUNCTION_NAME")
    @NotEmpty(message = "功能名称不能为空")
    private String functionName;

    /**
     * 功能地址
     */
    @TableField("FUNCTION_ADDRESS")
    private String functionAddress;

    /**
     * 角色ID
     */
    @TableField("ROLE_ID")
    @NotEmpty(message = "角色id不能为空")
    private Long roleId;

    /**
     * 角色编码
     */
    @TableField("ROLE_CODE")
    @NotEmpty(message = "角色编码不能为空")
    private String roleCode;

    /**
     * BUYER(采购商角色), BUYER_INIT(采购商初始化角色), SUPPLIER(供应商角色), SUPPLIER_INIT(供应商初始化角色), REG_DEFAULT(注册初始化角色)
     */
    @TableField("ROLE_TYPE")
    @NotEmpty(message = "角色类型不能为空")
    private String roleType;

    /**角色名称*/
    @TableField("ROLE_NAME")
    @NotEmpty(message = "角色名称不能为空")
    private String roleName;

    /**查询权限类型(查询权限类型(OU-按业务实体,BU-按事业部,INVENTORY-按库存组织,CATEGORY-按品类分工,OU_CATEGORY-按OU+品类,ALL-全部))*/
    @TableField("ROLE_FUNC_SET_TYPE")
    @NotEmpty(message = "查询权限类型不能为空")
    private String roleFuncSetType;

    /**
     * 是否启动(是-Y,否-N,默认为N)
     */
    @TableField("ENABLE_FLAG")
    private String enableFlag;

    /**
     * 创建人ID
     */
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;

    /**
     * 创建人
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;

    /**
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedByIp;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}
