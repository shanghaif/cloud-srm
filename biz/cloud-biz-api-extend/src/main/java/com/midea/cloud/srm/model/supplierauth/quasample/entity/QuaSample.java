package com.midea.cloud.srm.model.supplierauth.quasample.entity;

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
 *  样品确认表 模型
 * </pre>
*
* @author zhuwl7
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-17 19:04:43
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_auth_qua_sample")
public class QuaSample extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 样品确认主键ID
     */
    @TableId("SAMPLE_ID")
    private Long sampleId;

    /**
     * 样品确认编号
     */
    @TableField("SAMPLE_NUMBER")
    private String sampleNumber;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 供应商CODE
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 公司名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 样品接收人
     */
    @TableField("RECEIVER")
    private String receiver;

    /**
     * 样品接收人电话
     */
    @TableField("RECEIVER_PHONE")
    private String receiverPhone;

    /**
     * 资质审查单号表ID
     */
    @TableField("REVIEW_FORM_ID")
    private Long reviewFormId;

    /**
     * 资质审查单号，对应SCC_SUP_QUA_REVIEW_FORM表
     */
    @TableField("REVIEW_FORM_NUMBER")
    private String reviewFormNumber;

    /**
     * 审批状态(DRAFT拟定、SUBMITTED已提交、REJECTED已驳回、APPROVED已审批,参考字典码APPROVE_STATUS_TYPE
     */
    @TableField("APPROVE_STATUS")
    private String approveStatus;

    /**
     * 要求送样时间
     */
    @TableField("REQUIRE_SEND_TIME")
    private LocalDate requireSendTime;

    /**
     * 是否需要物料试用(Y，N)
     */
    @TableField("IS_MATERIAL_TRIAL")
    private String isMaterialTrial;

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
    private Integer tenantId;

    /**
     * 试用数量
     */
    @TableField("SAMPLE_QTY")
    private Integer sampleQty;

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
     * 父类合作组织
     */
    @TableField("PARENT_ORG_NAME")
    private String parentOrgName;

    /**
     * 父类合作组织
     */
    @TableField("PARENT_ORG_ID")
    private Long parentOrgId;

    /**
     * 父类合作组织
     */
    @TableField("PARENT_ORG_CODE")
    private String parentOrgCode;

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
     *品类全路径名称
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
     * 测试结果
     */
    @TableField("TEST_RESULT")
    private String testResult;

    /**
     * 测试结果说明
     */
    @TableField("TEST_RESULT_REMARK")
    private String testResultRemark;
    
    /**流程ID*/
    @TableField("CBPM_INSTANCE_ID")
    private String cbpmInstaceId;

    @TableField(exist = false)
    List<Fileupload> fileUploads;
   
    @TableField(exist=false)
    private Long menuId;
}
