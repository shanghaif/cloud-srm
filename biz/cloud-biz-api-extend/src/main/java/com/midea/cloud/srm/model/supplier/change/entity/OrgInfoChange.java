package com.midea.cloud.srm.model.supplier.change.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

/**
*  <pre>
 *  合作组织信息变更 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-28 13:59:38
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_org_info_change")
public class OrgInfoChange extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 组织变更表ID
     */
    @TableId("ORG_CHANGE_ID")
    private Long orgChangeId;

    /**
     * 变更ID
     */
    @TableField("CHANGE_ID")
    private Long changeId;

    /**
     * ID
     */
    @TableField("ORG_INFO_ID")
    private Long orgInfoId;

    /**
     * 供应商ID
     */
    @TableField("COMPANY_ID")
    private Long companyId;

    /**
     * 合作组织
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 组织服务状态
     */
    @TableField("SERVICE_STATUS")
    private String serviceStatus;

    /**
     * 生效时间
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 失效时间
     */
    @TableField("END_DATE")
    private LocalDate endDate;

    /**
     * 组织code
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 父级组织名称
     */
    @TableField("PARENT_ORG_NAME")
    private String parentOrgName;

    /**
     * 父级组织CODE
     */
    @TableField("PARENT_ORG_CODE")
    private String parentOrgCode;

    /**
     * 合作组织ID
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 合作组织ID
     */
    @TableField("PARENT_ORG_ID")
    private Long parentOrgId;

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
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    @TableField(exist = false)
    Map<String,Object> dimFieldContexts;

    @TableField("OP_TYPE")
    private String opType;
}
