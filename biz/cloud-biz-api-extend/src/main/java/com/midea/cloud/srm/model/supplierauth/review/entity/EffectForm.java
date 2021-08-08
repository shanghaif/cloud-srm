package com.midea.cloud.srm.model.supplierauth.review.entity;

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
 *  供方生效单据 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-19 21:09:09
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_auth_effect_form")
public class EffectForm extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 供方生效单据ID
     */
    @TableId("EFFECT_FORM_ID")
    private Long effectFormId;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 供应商编码
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 供方生效单号
     */
    @TableField("EFFECT_FORM_NUMBER")
    private String effectFormNumber;

    /**
     * 供方生效单据说明
     */
    @TableField("EFFECT_EXPLAIN")
    private String effectExplain;

    /**
     * 资质审查单ID
     */
    @TableField("REVIEW_FORM_ID")
    private Long reviewFormId;

    /**
     * 资质审查单号
     */
    @TableField("REVIEW_FORM_NUMBER")
    private String reviewFormNumber;

    /**
     * 资质审查单创建时间
     */
    @TableField("REVIEW_CREATION_DATE")
    private Date reviewCreationDate;

    /**
     * 资质审查单完成时间
     */
    @TableField("REVIEW_FINISH_DATE")
    private Date reviewFinishDate;

    /**
     * 资质审查类型,参考字典码QUA_REVIEW_TYPE
     */
    @TableField("QUA_REVIEW_TYPE")
    private String quaReviewType;

    /**
     * 现场评审单ID
     */
    @TableField("SITE_FORM_ID")
    private Long siteFormId;

    /**
     * 现场评审单号
     */
    @TableField("SITE_FORM_NUMBER")
    private String siteFormNumber;

    /**
     * 现场评审单创建时间
     */
    @TableField("SITE_CREATION_DATE")
    private Date siteCreationDate;

    /**
     * 现场评审单完成时间
     */
    @TableField("SITE_FINISH_DATE")
    private Date siteFinishDate;

    /**
     * 审批状态(DRAFT拟定、SUBMITTED已提交、REJECTED已驳回、APPROVED已审批,参考字典码APPROVE_STATUS_TYPE
     */
    @TableField("APPROVE_STATUS")
    private String approveStatus;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    @TableField("END_DATE")
    private LocalDate endDate;

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
     * 附件上传
     */
    @TableField(exist = false)
    List<Fileupload> fileUploads;
}
