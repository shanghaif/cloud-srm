package com.midea.cloud.srm.model.perf.vendorasses;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
*  <pre>
 *  供应商考核单表 模型
 * </pre>
*
* @author wangpr@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:55:47
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_perf_vendor_asses_form")
public class VendorAssesForm extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 供应商考核单ID
     */
    @TableId("VENDOR_ASSES_ID")
    private Long vendorAssesId;

    /**
     * 考核单号
     */
    @TableField("ASSESSMENT_NO")
    private String assessmentNo;

    /**
     * 考核时间
     */
    @TableField("ASSESSMENT_DATE")
    private LocalDate assessmentDate;

    /**
     * 指标维度头表id
     */
    @TableField("INDICATOR_HEAD_ID")
    private String indicatorHeadId;

    /**
     * 指标维度(1-品质，2-服务，3-交付，4-成本，5-技术)
     */
    @TableField("INDICATOR_DIMENSION")
    private String indicatorDimension;

    /**
     * 指标名称
     */
    @TableField("INDICATOR_NAME")
    private String indicatorName;

    /**
     * 指标行信息
     */
    @TableField("INDICATOR_LINE_DES")
    private String indicatorLineDes;

    /**
     * 建议考核金额
     */
    @TableField("ASSESSMENT_PENALTY")
    private BigDecimal assessmentPenalty;

    /**
     * 考核单状态(DRAFT-拟定,IN_FEEDBACK-反馈中,WITHDRAWN-已撤回,OBSOLETE-已废弃,ASSESSED-已考核)
     */
    @TableField("STATUS")
    private String status;

    /**
     * 税率编码
     */
    @TableField("TAX_KEY")
    private String taxKey;

    /**
     * 税率
     */
    @TableField("TAX_CODE")
    private BigDecimal taxCode;

    /**
     * 税率名称
     */
    @TableField("TAX_NAME")
    private String taxName;

    /**
     * 实际考核金额(含税)
     */
    @TableField("ACTUAL_ASSESSMENT_AMOUNT_Y")
    private BigDecimal actualAssessmentAmountY;

    /**
     * 实际考核金额(不含税)
     */
    @TableField("ACTUAL_ASSESSMENT_AMOUNT_N")
    private BigDecimal actualAssessmentAmountN;

    /**
     * 采购组织id
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 采购组织编码
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 采购组织名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 供应商编码
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 币种编码
     */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /**
     * 币种名称
     */
    @TableField("CURRENCY_NAME")
    private String currencyName;

    /**
     * 采购分类id
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 采购分类编码
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 采购分类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 物料编码
     */
    @TableField("ITEM_CODE")
    private String materialCode;

    /**
     * 物料名称
     */
    @TableField("ITEM_NAME")
    private String materialName;

    /**
     * 责任人账号
     */
    @TableField("RESP_USER_NAME")
    private String respUserName;

    /**
     * 责任人名字
     */
    @TableField("RESP_FULL_NAME")
    private String respFullName;

    /**
     * 考核附件文件id
     */
    @TableField("FILE_UPLOAD_ID")
    private String fileUploadId;

    /**
     * 考核附件文件名
     */
    @TableField("FILE_SOURCE_NAME")
    private String fileSourceName;

    /**
     * 考核说明
     */
    @TableField("EXPLANATION")
    private String explanation;

    /**
     * 供应商反馈时间
     */
    @TableField("V_FEEDBACK_TIME")
    private Date vFeedbackTime;

    /**
     * 供应商反馈附件文件id
     */
    @TableField("V_FEEDBACK_FILE_UPLOAD_ID")
    private String vFeedbackFileUploadId;

    /**
     * 供应商反馈附件文件名
     */
    @TableField("V_FEEDBACK_FILE_SOURCE_NAME")
    private String vFeedbackFileSourceName;

    /**
     * 供应商反馈说明
     */
    @TableField("V_FEEDBACK_EXPLANATION")
    private String vFeedbackExplanation;

    /**
     * 供应商供应商是否已反馈,Y:是,N:否
     */
    @TableField("V_IS_FEEDBACK")
    private String vIsFeedback;

    /**
     * 处理供应商反馈时间
     */
    @TableField("M_FEEDBACK_TIME")
    private Date mFeedbackTime;

    /**
     * 处理供应商反馈附件文件id
     */
    @TableField("M_FEEDBACK_FILE_UPLOAD_ID")
    private String mFeedbackFileUploadId;

    /**
     * 处理供应商反馈附件文件名
     */
    @TableField("M_FEEDBACK_FILE_SOURCE_NAME")
    private String mFeedbackFileSourceName;

    /**
     * 处理供应商反馈说明
     */
    @TableField("M_FEEDBACK_EXPLANATION")
    private String mFeedbackExplanation;

    /**
     * 处理供应商供应商是否已反馈,Y:是,N:否
     */
    @TableField("M_IS_FEEDBACK")
    private String mIsFeedback;

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
     * 更新人
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
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    @TableField(exist = false)
    private LocalDate assessmentDateStart;

    @TableField(exist = false)
    private LocalDate assessmentDateEnd;

    /**
     * 外部CBPM实例ID
     */
    @TableField("CBPM_INSTANCE_ID")
    private String cbpmInstaceId;
    @TableField(exist = false)
    private Long menuId;
    @TableField("APPROVE_STATUS")
    private String approveStatus;
    /**
     * 评选结果
     */
    @TableField("CEEA_INDICATOR_LINE_DES")
    private String ceeaIndicatorLineDes;
    /**
     * 考核类型
     */
    @TableField("CEEA_ASSESSMENT_TYPE")
    private String ceeaAssessmentType;
    /**
     * 标题
     */
    @TableField("CEEA_ASSESSMENT_TITLE")
    private String ceeaAssessmentTitle;
    /**
     * 关联状态
     */
    @TableField("CEEA_ASSOCIATED_STATES")
    private String ceeaAssociatedStates;
    /**
     * 建议降级至（品类状态）
     */
    @TableField("CEEA_CATEGORY_STATUS")
    private String ceeaCategoryStatus;

    /**
     * 供应商ID
     */
    @TableField("CEEA_VENDOR_ID")
    private String ceeaVendorId;

    /**
     * 是否引用(是Y,否N)
     */
    @TableField("IF_QUOTE")
    private String ifQuote;
    /**
     * 起草人意见
     */
    @TableField("CEEA_DRAFTER_OPINION")
    private String ceeaDrafterOpinion;

    @TableField(exist = false)
    private String processType;//提交审批Y，废弃N
}
