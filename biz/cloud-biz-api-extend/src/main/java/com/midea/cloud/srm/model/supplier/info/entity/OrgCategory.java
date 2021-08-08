package com.midea.cloud.srm.model.supplier.info.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

/**
*  <pre>
 *  组织与品类 模型
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 16:21:46
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_org_category")
public class OrgCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("ORG_CATEGORY_ID")
    private Long orgCategoryId;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 供应商ID
     */
    @TableId("COMPANY_ID")
    private Long companyId;

    /**
     * 供应商编码
     */
    @TableField(exist = false)
    private String companyCode;

    /**
     * 供应商名称
     */
    @TableField(exist = false)
    private String companyName;

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
     * 合作组织code
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 合作组织
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 父类合作组织
     */
    @TableField("PARENT_ORG_ID")
    private Long parentOrgId;

    /**
     * 父类合作组织
     */
    @TableField("PARENT_ORG_NAME")
    private String parentOrgName;

    /**
     * 父类合作组织
     */
    @TableField("PARENT_ORG_CODE")
    private String parentOrgCode;

    /**
     * 合作品类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 合作品类code
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 合作品类
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 合作品类全路径ID
     */
    @TableField("CATEGORY_FULL_ID")
    private String categoryFullId;

    /**
     * 合作品类全称
     */
    @TableField("CATEGORY_FULL_NAME")
    private String categoryFullName;


    /**
     * 品类服务状态
     */
    @TableField("SERVICE_STATUS")
    private String serviceStatus;

    /**
     * 是否单一供方（Y：是，N：否）
     */
    @TableField("IF_SINGLE_SUPPLIER")
    private String ifSingleSupplier;


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
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

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
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
    private String lastUpdatedByIp;

    @TableField(exist = false)
    Map<String,Object> dimFieldContexts;

    @TableField(exist = false)
    private String Username;

    /**
     * 组织类型
     */
    @TableField(exist = false)
    private String orgType;

    /**
     * 父组织ID
     */
    @TableField(exist = false)
    private Long fatherId;

    /**
     * 限期使用,Y:是,N:否
     */
    @TableField("LIMIT_TIME_USE")
    private String limitTimeUse;
}
