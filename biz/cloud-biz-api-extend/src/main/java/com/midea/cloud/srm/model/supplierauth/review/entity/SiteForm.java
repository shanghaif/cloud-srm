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
 *  现场评审单据 模型
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-15 17:06:13
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_auth_site_form")
public class SiteForm extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("SITE_FORM_ID")
    private Long siteFormId;

    /**
     * 现场评审单号(ceea:供应商认证单号)
     */
    @TableField("SITE_FORM_NUMBER")
    private String siteFormNumber;

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
     * 资质审查类型,参考字典码QUA_REVIEW_TYPE
     */
    @TableField("QUA_REVIEW_TYPE")
    private String quaReviewType;

    /**
     * 资质审查类型名称,参考字典码QUA_REVIEW_TYPE
     */
    @TableField("QUA_REVIEW_TYPE_NAME")
    private String quaReviewTypeName;

    /**
     * 现场评审类型，参考字典ASSESSMENT_TYPE(ceea:供应商认证类型,参考字典码CEEA_ASSESSMENT_TYPE)
     */
    @TableField("ASSESSMENT_TYPE")
    private String assessmentType;


    /**
     * 现场评审类型名称
     */
    @TableField("ASSESSMENT_TYPE_NAME")
    private String assessmentTypeName;


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
     * 现场评审单据说明(ceea:供应商认证说明)
     */
    @TableField("SITE_FORM_EXPLAIN")
    private String siteFormExplain;

    /**
     * 评审结果,参考字典REVIEW_RESULT_TYPE(ceea:最终结论,参考字典CEEA_RESULT_TYPE)
     */
    @TableField("REVIEW_RESULT")
    private String reviewResult;

    /**
     * 结论说明
     */
    @TableField("CEEA_RESULT_EXPLAIN")
    private String ceeaResultExplain;

    /**
     * 评审得分
     */
    @TableField("TOTAL_SCORE")
    private Double totalScore;

    /**
     * 现场评审地址
     *
     */
    @TableField("SITE_ADRESS")
    private String siteAdress;

    /**
     * 现场评审城市
     *
     */
    @TableField("SITE_CITY")
    private String siteCity;

    /**
     * 现场评审省份
     *
     */
    @TableField("SITE_PROVINCE")
    private String siteProvince;

    /**
     * 现场评审日期
     */
    @TableField("SITE_DATE")
    private LocalDate siteDate;

    /**
     * 现场评审成员
     */
    @TableField("SITE_MEMBER")
    private String siteMember;

    /**
     * 供方陪审员
     */
    @TableField("VENDOR_ASSESSOR")
    private String vendorAssessor;

    /**
     * 审批状态(DRAFT拟定、SUBMITTED已提交、REJECTED已驳回、APPROVED已审批,参考字典码APPROVE_STATUS_TYPE
     */
    @TableField("APPROVE_STATUS")
    private String approveStatus;

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

    /**
     * 外部CBPM实例ID
     */
    @TableField("CBPM_INSTANCE_ID")
    private String fdId;

    /**
     * BPM起草人意见
     */
    @TableField("CEEA_DRAFTER_OPINION")
    private String ceeaDrafterOpinion;

    /**
     * 创建人部门
     */
    @TableField("CEEA_DEPT_NAME")
    private String ceeaDeptName;

    /**
     * 创建人昵称
     */
    @TableField(value = "CREATE_FULL_NAME", exist = false)
    private String createdFullName;

}
