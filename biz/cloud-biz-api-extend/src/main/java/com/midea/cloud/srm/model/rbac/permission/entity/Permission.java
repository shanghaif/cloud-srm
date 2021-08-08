package com.midea.cloud.srm.model.rbac.permission.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  权限维护 模型
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-10 17:30:00
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_rbac_permission")
public class Permission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    @TableId("PERMISSION_ID")
    private Long permissionId;

    /**
     * 父权限ID
     */
    @TableField("PARENT_PERMISSION_ID")
    private Long parentPermissionId;

    /**
     * 功能ID
     */
    @TableField(value = "FUNCTION_ID", updateStrategy = FieldStrategy.IGNORED)
    private Long functionId;

    /**
     * 功能名称
     */
    @TableField(exist = false)
    private String functionName;

    /**
     * 功能编码
     */
    @TableField(exist = false)
    private String functionCode;

    /**
     * 功能地址
     */
    @TableField(exist = false)
    private String functionAddress;

    /**
     * 功能描述
     */
    @TableField(exist = false)
    private String functionDesc;

    /**
     * 功能图标
     */
    @TableField(exist = false)
    private String functionIcon;

    /**
     * 权限名称
     */
    @TableField("PERMISSION_NAME")
    private String permissionName;

    /**
     * 父级权限名称
     */
    @TableField(exist = false)
    private String parentPermissionName;

    /**
     * 权限类型: BUTTON, MENU
     */
    @TableField("PERMISSION_TYPE")
    private String permissionType;

    /**
     * 权限编码, 通过工具自动生成
     */
    @TableField("PERMISSION_CODE")
    private String permissionCode;

    /**
     * 权限标识
     */
    @TableField("PERMISSION")
    private String permission;

    /**
     * 排序
     */
    @TableField("SORT")
    private Long sort;

    /**
     * icon路径
     */
    @TableField("ICON_PATH")
    private String iconPath;

    /**
     * 组织管控维度
     */
    @TableField("ORG_CONTROL_DIM")
    private String orgControlDim;

    /**
     * 启用品类分工
     */
    @TableField("ENABLE_CATEGORY_DIVISION")
    private String enableCategoryDivision;

    /**
     * 启用附件管理
     */
    @TableField("ENABLE_ATTACH_MANAGE")
    private String enableAttachManage;

    /**
     * 启用业务状态控制
     */
    @TableField("ENABLE_BUSI_STATE_CONTROL")
    private String enableBusiStateControl;

    /**
     * 启用工作流
     */
    @TableField("ENABLE_WORK_FLOW")
    private String enableWorkFlow;

    /**
     * 生效日期
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 失效日期
     */
    @TableField(value = "END_DATE", updateStrategy = FieldStrategy.IGNORED)
    private LocalDate endDate;

    /**
     * 子权限节点
     */
    @TableField(exist = false)
    private List<Permission> childPermissions;

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
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
    private String lastUpdatedByIp;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;


}
