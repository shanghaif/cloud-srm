package com.midea.cloud.srm.model.pm.pr.requirement.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
*  <pre>
 *  采购需求头表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-27 15:56:14
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_pr_requirement_head")
public class RequirementHead extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID(CEEA)
     */
    @TableId("REQUIREMENT_HEAD_ID")
    private Long requirementHeadId;

    /**
     * 采购需求编号(申请编号)(CEEA)
     */
    @TableField("REQUIREMENT_HEAD_NUM")
    private String requirementHeadNum;

    /**
     * 处理状态
     */
    @TableField("HANDLE_STATUS")
    private String handleStatus;

    /**
     * 购类型采购类型(CEEA, 数据字典PURCHASE_TYPE)
     */
    @TableField("CEEA_PURCHASE_TYPE")
    private String ceeaPurchaseType;

    /**
     * 业务实体ID(CEEA)
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 业务实体编码(CEEA)
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 业务实体名称(CEEA)
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 库存组织ID(CEEA)
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 库存组织名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 组织编码(ceea:库存组织编码)
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 来源系统（如：erp系统）
     */
    @TableField("SOURCE_SYSTEM")
    private String sourceSystem;

    /**
     * 部门ID
     */
    @TableField("CEEA_DEPARTMENT_ID")
    private String ceeaDepartmentId;

    /**
     * 部门编码(弃用)
     */
    @TableField("CEEA_DEPARTMENT_CODE")
    private String ceeaDepartmentCode;

    /**
     * 部门名称
     */
    @TableField("CEEA_DEPARTMENT_NAME")
    private String ceeaDepartmentName;

    /**
     * 申请类型
     */
    @TableField("CEEA_PR_TYPE")
    private String ceeaPrType;

    /**
     * 物料大类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 物料大类编码
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 物料大类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 项目名称
     */
    @TableField("CEEA_PROJECT_NAME")
    private String ceeaProjectName;

    /**
     * 项目编号
     */
    @TableField("CEEA_PROJECT_NUM")
    private String ceeaProjectNum;

    /**
     * 项目负责人用户ID
     */
    @TableField("CEEA_PROJECT_USER_ID")
    private Long ceeaProjectUserId;

    /**
     * 项目负责人用户名称
     */
    @TableField("CEEA_PROJECT_USER_NICKNAME")
    private String ceeaProjectUserNickname;

    /**
     * 立项流水号
     */
    @TableField("CEEA_PROJECT_APPROVAL_NUM")
    private String ceeaProjectApprovalNum;

    /**
     * 是否募投
     */
    @TableField("CEEA_IF_VOTE")
    private String ceeaIfVote;

    /**
     * 募投项目名称
     */
    @TableField("CEEA_VOTE_PROJECT_NAME")
    private String ceeaVoteProjectName;

    /**
     * 资产类别
     */
    @TableField("CEEA_ASSET_TYPE")
    private String ceeaAssetType;

    /**
     * 预算总金额
     */
    @TableField("CEEA_TOTAL_BUDGET")
    private BigDecimal ceeaTotalBudget;

    /**
     * 业务小类
     */
    @TableField("CEEA_BUSINESS_SMALL")
    private String ceeaBusinessSmall;

    /**
     * 业务小类编码
     */
    @TableField("CEEA_BUSINESS_SMALL_CODE")
    private String ceeaBusinessSmallCode;

    /**
     * 是否总部
     */
    @TableField("CEEA_IF_HQ")
    private String ceeaIfHq;

    /**
     * 是否使用LOGO
     */
    @TableField("CEEA_IF_USE_LOGO")
    private String ceeaIfUseLogo;

    /**
     * 紧急情况说明
     */
    @TableField("CEEA_URGENCY_EXPLAIN")
    private String ceeaUrgencyExplain;

    /**
     * 指定原因
     */
    @TableField("CEEA_APPOINT_REASON")
    private String ceeaAppointReason;

    /**
     * 起草人意见
     */
    @TableField("CEEA_DRAFTER_OPINION")
    private String ceeaDrafterOpinion;

    /**
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;

    /**
     * 审核状态(数据字典APPROVAL_STATUS)
     */
    @TableField("AUDIT_STATUS")
    private String auditStatus;

    /**
     * 采购需求创建方式 CREATE_NEW:手动新建  MERGE_NEW:合并需求行新建
     */
    @TableField("CREATE_TYPE")
    private String createType;

    /**
     * 创建人姓名
     */
    @TableField(value = "CREATED_FULL_NAME", fill = FieldFill.INSERT)
    private String createdFullName;

    /**
     * 申请日期
     */
    @TableField("APPLY_DATE")
    private LocalDate applyDate;

    /**
     * 企业编码
     */
    @TableField("COMPANY_CODE")
    private String companyCode;

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
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.UPDATE)
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

    /**
     * 审批意见
     */
    @TableField("APPROVE_OPINION")
    private String approveOpinion;

    /**
     * 预算告警忽略标识(CEEA)
     */
    @TableField("BUDGET_IGNORE")
    private String budgetIgnore;

    /**
     * ERP采购申请单号(CEEA)
     */
    @TableField("ES_REQUIREMENT_HEAD_NUM")
    private String esRequirementHeadNum;

    /**
     * 本年预算占用金额(CEEA)
     */
    @TableField("THIS_YEAR_BUDGET_AMOUNT")
    private BigDecimal thisYearBudgetAmount;

    /**
     * 次年预算占用金额(CEEA)
     */
    @TableField("NEXT_YEAR_BUDGET_AMOUNT")
    private BigDecimal nextYearBudgetAmount;

    /**
     * 需求部门ID
     */
    @TableField(value = "DEMAND_DEPARTMENT_ID",updateStrategy = FieldStrategy.IGNORED)
    private String demandDepartmentId;

    /**
     * 需求部门编码
     */
    @TableField(value = "DEMAND_DEPARTMENT_CODE",updateStrategy = FieldStrategy.IGNORED)
    private String demandDepartmentCode;

    /**
     * 需求部门名称
     */
    @TableField(value = "DEMAND_DEPARTMENT_NAME",updateStrategy = FieldStrategy.IGNORED)
    private String demandDepartmentName;

    /**
     * 需求人ID
     */
    @TableField(value = "DEMAND_USER_ID",updateStrategy = FieldStrategy.IGNORED)
    private String demandUserId;

    /**
     * 需求人工号
     */
    @TableField(value = "DEMAND_USER_NAME",updateStrategy = FieldStrategy.IGNORED)
    private String demandUserName;

    /**
     * 需求人名称
     */
    @TableField(value = "DEMAND_USER_NICKNAME",updateStrategy = FieldStrategy.IGNORED)
    private String demandUserNickname;

    /**
     * 物质类别
     */
    @TableField("CATEGORY_TYPE")
    private String categoryType;

    /**
     * 是否已分配供应商
     * @enum com.midea.cloud.common.enums.pm.pr.requirement.IfDistributionVendor
     */
    @TableField("IF_DISTRIBUTION_VENDOR")
    private String ifDistributionVendor;

    /**
     * 失败原因
     */
    @TableField("ERROR_MSG")
    private String errorMsg;
}
