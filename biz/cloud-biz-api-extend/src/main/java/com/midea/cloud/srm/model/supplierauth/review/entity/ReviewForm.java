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

import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  资质审查单据 模型
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-10 16:34:39
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_auth_review_form")
public class ReviewForm extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("REVIEW_FORM_ID")
    private Long reviewFormId;

    /**
     * 资质审查单号
     */
    @TableField("REVIEW_FORM_NUMBER")
    private String reviewFormNumber;

    /**
     * 资质审查类型,参考字典码QUA_REVIEW_TYPE
     */
    @TableField("QUA_REVIEW_TYPE")
    private String quaReviewType;

    /**
     * 资质审查类型名称
     */
    @TableField("QUA_REVIEW_TYPE_NAME")
    private String quaReviewTypeName;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 是否研发部门发起(Y是 N否),参考字典码YES_OR_NO
     */
    @TableField("IF_DEVELOP")
    private String ifDevelop;

    /**
     * 是否现场审查(Y是 N否),参考字典码YES_OR_NO
     */
    @TableField("IF_SITE_FORM")
    private String ifSiteForm;

    /**
     * 评审准备期（天）
     */
    @TableField("REVIEW_CYCLE")
    private Long reviewCycle;

    /**
     * 审查单据说明
     */
    @TableField("REVIEW_EXPLAIN")
    private String reviewExplain;

    /**
     * 审批状态(DRAFT拟定、SUBMITTED已提交、REJECTED已驳回、APPROVED已审批,参考字典码APPROVE_STATUS_TYPE
     */
    @TableField("APPROVE_STATUS")
    private String approveStatus;

    /**
     * 部门ID
     */
    @TableField("CEEA_DEPT_ID")
    private Long ceeaDeptId;

    /**
     * 部门名称
     */
    @TableField("CEEA_DEPT_NAME")
    private String ceeaDeptName;

    /**
     * 是否一次供应商
     */
    @TableField("CEEA_IF_ONCE_VENDOR")
    private String ceeaIfOnceVendor;

    /**
     * 是否供应商认证
     */
    @TableField("CEEA_IF_VENDOR_AUTH")
    private String ceeaIfVendorAuth;

    /**
     * 需求分析
     */
    @TableField("CEEA_DEMAND_ANALYSIS")
    private String ceeaDemandAnalysis;

    /**
     * 市场供应分析
     */
    @TableField("CEEA_SUP_ANALYSIS")
    private String ceeaSupAnalysis;

    /**
     * 品类本期采购策略
     */
    @TableField("CEEA_CATEGORY_STRATEGY")
    private String ceeaCategoryStrategy;


    /**
     * 生效日期(YYYY-MM-DD)
     */
    @TableField("START_DATE")
    private Date startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    @TableField("END_DATE")
    private Date endDate;

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
     * 创建人姓名
     */
    @TableField(value = "CREATED_FULL_NAME", fill = FieldFill.INSERT)
    private String createdFullName;

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
     * 最后更新人姓名
     */
    @TableField(value = "LAST_UPDATED_FULL_NAME", fill = FieldFill.UPDATE)
    private String lastUpdatedFullName;

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
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 供应商编码
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**流程ID*/
    @TableField("CBPM_INSTANCE_ID")
    private String cbpmInstaceId;

    /**
     * 附件上传
     */
    @TableField(exist = false)
    List<Fileupload> fileUploads;
    /**
     * BPM起草人意见
     */
    @TableField("CEEA_DRAFTER_OPINION")
    private String ceeaDrafterOpinion;

}
