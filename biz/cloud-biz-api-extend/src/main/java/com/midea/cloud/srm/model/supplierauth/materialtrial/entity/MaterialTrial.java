package com.midea.cloud.srm.model.supplierauth.materialtrial.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  物料试用表 模型
 * </pre>
*
* @author zhuwl7@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-20 10:59:47
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_auth_material_trial")
public class MaterialTrial extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表ID，主键，供其他表做外键
     */
    @TableId("MATERIAL_TRIAL_ID")
    private Long materialTrialId;

    /**
     * 试用申请单号
     */
    @TableField("TRIAL_NUMBER")
    private String trialNumber;

    /**
     * 试用单据状态
     */
    @TableField("APPROVE_STATUS")
    private String approveStatus;

    /**
     * 样品确认单号
     */
    @TableField("SAMPLE_ID")
    private Long sampleId;

    /**
     * 样品确认单号
     */
    @TableField("SAMPLE_NUMBER")
    private String sampleNumber;

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
     * 试用开始时间
     */
    @TableField("TRIAL_START_DATE")
    private LocalDate trialStartDate;

    /**
     * 试用结束时间
     */
    @TableField("TRIAL_END_DATE")
    private LocalDate trialEndDate;

    /**
     * 试用批次
     */
    @TableField("TRY_BATCH")
    private String tryBatch;

    /**
     * 试用数量
     */
    @TableField("TRIAL_QTY")
    private Integer trialQty;

    /**
     * 物料试用说明
     */
    @TableField("TRIAL_INSTRUCTION")
    private String trialInstruction;

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
     * 试用结果
     */
    @TableField("TRIAL_RESULT")
    private String trialResult;

    /**
     * 试用结果说明
     */
    @TableField("TRIAL_RESULT_REMARK")
    private String trialResultRemark;

    /**
     * 采购商确认说明
     */
    @TableField("BUYER_CONFIRM_REMARK")
    private String buyerConfirmRemark;

    /**
     * 送样方式
     */
    @TableField("EXPRESS_TYPE")
    private String expressType;

    /**
     * 物流单号
     */
    @TableField("EXPRESS_NUMBER")
    private String expressNumber;

    /**
     * 供应商确认说明
     */
    @TableField("VENDOR_CONFIRM_REMARK")
    private String vendorConfirmRemark;

    /**
     * 预计送达时间
     */
    @TableField("ESTIMATED_DELIVERY_TIME")
    private LocalDate estimatedDeliveryTime;

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
     * 物料需要时间
     */
    @TableField("TRIAL_REQUIRE_TIME")
    private LocalDate trialRequireTime;

    @TableField(exist = false)
    List<Fileupload> fileUploads;


    /**
     * 功能菜单ID
     */
    @TableField(exist = false)
    Long menuId;

    @TableField("CBPM_INSTANCE_ID")
    private String cbpmInstaceId;
}
