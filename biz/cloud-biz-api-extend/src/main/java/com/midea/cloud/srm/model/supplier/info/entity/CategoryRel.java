package com.midea.cloud.srm.model.supplier.info.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
*  <pre>
 *  可供品类 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-05 14:51:06
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_category_rel")
public class CategoryRel extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("CATEGORY_REL_ID")
    private Long categoryRelId;

    /**
     * 供应商ID
     */
    @TableField("COMPANY_ID")
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
     * 品类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 品类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 品列CODE
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 品类全名称
     */
    @TableField("CATEGORY_FULL_NAME")
    private String categoryFullName;

    /**
     * 品类全ID
     */
    @TableField("CATEGORY_FULL_ID")
    private String categoryFullId;

    /**
     * 业务实体ID
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 业务实体编码
     */
    @TableField("ORG_CODE")
    private Long orgCode;

    /**
     * 业务实体名称
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 品类服务状态
     */
    @TableField("SERVICE_STATUS")
    private String serviceStatus;

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


}
