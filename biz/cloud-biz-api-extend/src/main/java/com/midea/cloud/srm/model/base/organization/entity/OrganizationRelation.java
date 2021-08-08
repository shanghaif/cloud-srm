package com.midea.cloud.srm.model.base.organization.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  组织关系 模型
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-15 12:48:06
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_organization_rel")
public class OrganizationRelation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键关系ID
     */
    @TableId(value = "REL_ID", type = IdType.AUTO)
    private Long relId;

    /**
     * 组织名称
     */
    @TableField(exist = false)
    private String organizationName;

    /**
     * 组织编码
     */
    @TableField(exist = false)
    private String organizationCode;

    /**
     * 组织类型
     */
    @TableField(exist = false)
    private String organizationTypeCode;

    /**
     * 组织ID
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;


    /**
     * 父组织名称
     */
    @TableField(exist = false)
    private String parentOrganizationName;

    /**
     * 父组织编码
     */
    @TableField(exist = false)
    private String parentOrganizationCode;

    /**
     * 父组织ID
     */
    @TableField("PARENT_ORGANIZATION_ID")
    private Long parentOrganizationId;

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
     * 最后更新人
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
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 子节点信息
     */
    @TableField(exist = false)
    private List<OrganizationRelation> childOrganRelation;

    /**
     * 全路径唯一ID(MD加密)
     */
    @TableField(exist = false)
    private String fullPathId;

    /**
     * 用户ID
     */
    @TableField(exist = false)
    private Long userId;

    /**
     * 公司名称
     */
    @TableField(exist = false)
    private String ceeaCompanyName;

    /**
     * 公司简称
     */
    @TableField(exist = false)
    private String ceeaCompanyShort;

    /**
     * 公司代码
     */
    @TableField(exist = false)
    private String ceeaCompanyCode;

    /**
     * 是否有效
     */
    @TableField(exist = false)
    private String enabled;

    /**
     * 是否项目公司
     */
    @TableField(exist = false)
    private String ifProjectCompany;

}
