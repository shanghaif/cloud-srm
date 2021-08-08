package com.midea.cloud.srm.model.supplierauth.purchasecatalog.entity;

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
 *  采购目录 模型
 * </pre>
*
* @author zhuwl7@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-23 11:36:03
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_auth_purchase_catalog")
public class PurchaseCatalog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表ID，主键
     */
    @TableId("CATALOG_ID")
    private Long catalogId;

    /**
     * 供应商公司ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 供应商CODE
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 供应商公司名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 品类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 品类CODE
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 品类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 品类全路径名称
     */
    @TableField("CATEGORY_FULL_NAME")
    private String categoryFullName;

    /**
     * 物料ID
     */
    @TableField("MATERIAL_ID")
    private Long materialId;

    /**
     * 物料CODE
     */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /**
     * 物料名称
     */
    @TableField("MATERIAL_NAME")
    private String materialName;

    /**
     * 状态
     */
    @TableField("CATALOG_STATUS")
    private String catalogStatus;

    /**
     * 采购组织ID
     */
    @TableField("PURCHASE_ORG_ID")
    private Long purchaseOrgId;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 采购组织CODE
     */
    @TableField("PURCHASE_ORG_CODE")
    private String purchaseOrgCode;

    /**
     * 采购组织名称
     */
    @TableField("PURCHASE_ORG_NAME")
    private String purchaseOrgName;

    /**
     * 父采购组织ID
     */
    @TableField("PARENT_ORG_ID")
    private Long parentOrgId;

    /**
     * 父采购组织CODE
     */
    @TableField("PARENT_ORG_CODE")
    private String parentOrgCode;

    /**
     * 父采购组织名称
     */
    @TableField("PARENT_ORG_NAME")
    private String parentOrgName;

    /**
     * 生效日期
     */
    @TableField("START_DATE")
    private Date startDate;

    /**
     * 失效日期
     */
    @TableField("END_DATE")
    private Date endDate;

    /**
     * 更新原因
     */
    @TableField(value = "UPDATED_REASON")
    private String updatedReason;

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
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;


}
