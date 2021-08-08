package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity;

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
 *  招标流程表 模型
 * </pre>
*
* @author fengdc3@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-31 18:58:55
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_bid_process")
public class BidProcess extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 招标流程ID
     */
    @TableId("PROCESS_ID")
    private Long processId;

    /**
     * 招标流程配置ID
     */
    @TableField("PROCESS_CONFIG_ID")
    private Long processConfigId;

    /**
     * 招标ID
     */
    @TableField("BIDING_ID")
    private Long bidingId;

    /**
     * 技术交流 Y:启用,N:不启用
     */
    @TableField("TECHNOLOGY_EXCHANGE")
    private String technologyExchange;

    /**
     * 项目信息 Y:启用,N:不启用
     */
    @TableField("PROJECT_INFORMATION")
    private String projectInformation;

    /**
     * 项目需求 Y:启用,N:不启用
     */
    @TableField("PROJECT_REQUIREMENT")
    private String projectRequirement;

    /**
     * 邀请供应商 Y:启用,N:不启用
     */
    @TableField("INVITE_SUPPLIER")
    private String inviteSupplier;

    /**
     * 评分规则 Y:启用,N:不启用
     */
    @TableField("SCORING_RULE")
    private String scoringRule;

    /**
     * 流程审批 Y:启用,N:不启用
     */
    @TableField("PROCESS_APPROVAL")
    private String processApproval;

    /**
     * 供应商绩效 Y:启用,N:不启用
     */
    @TableField("SUPPLIER_PERFORMANCE")
    private String supplierPerformance;

    /**
     * 拦标价 Y:启用,N:不启用
     */
    @TableField("TARGET_PRICE")
    private String targetPrice;

    /**
     * 项目发布 Y:启用,N:不启用
     */
    @TableField("PROJECT_PUBLISH")
    private String projectPublish;

    /**
     * 报名管理 Y:启用,N:不启用
     */
    @TableField("ENTRY_MANAGEMENT")
    private String entryManagement;

    /**
     * 质疑澄清 Y:启用,N:不启用
     */
    @TableField("QUESTION_CLARIFICATION")
    private String questionClarification;

    /**
     * 投标控制 Y:启用,N:不启用
     */
    @TableField("BIDING_CONTROL")
    private String bidingControl;

    /**
     * 技术评分 Y:启用,N:不启用
     */
    @TableField("TECHNICAL_SCORE")
    private String technicalScore;

    /**
     * 技术标管理 Y:启用,N:不启用
     */
    @TableField("TECHNICAL_MANAGEMENT")
    private String technicalManagement;

    /**
     * 商务标管理 Y:启用,N:不启用
     */
    @TableField("COMMERCIAL_MANAGEMENT")
    private String commercialManagement;

    /**
     * 评选 Y:启用,N:不启用
     */
    @TableField("BID_EVALUATION")
    private String bidEvaluation;

    /**
     * 结项报告 Y:启用,N:不启用
     */
    @TableField("PROJECT_REPORT")
    private String projectReport;

    /**
     * 结项审批 Y:启用,N:不启用
     */
    @TableField("PROJECT_APPROVAL")
    private String projectApproval;

    /**
     * 是否有效  Y:有效  N:无效
     */
    @TableField("ENABLE_FLAG")
    private String enableFlag;

    /**
     * 企业编码
     */
    @TableField("COMPANY_CODE")
    private String companyCode;

    /**
     * 组织编码
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 招标流程配置简述/备注
     */
    @TableField("COMMENTS")
    private String comments;

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
     * 更新人
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
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}
