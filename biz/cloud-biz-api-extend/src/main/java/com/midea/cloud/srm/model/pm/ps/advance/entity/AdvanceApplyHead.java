package com.midea.cloud.srm.model.pm.ps.advance.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.time.LocalDate;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  预付款申请头表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-20 13:40:24
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_ps_advance_apply_head")
public class AdvanceApplyHead extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,预付款申请头ID
     */
    @TableId("ADVANCE_APPLY_HEAD_ID")
    private Long advanceApplyHeadId;

    /**
     * 业务实体ID
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 业务实体编码
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 业务实体名称
     */

    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

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
     * erp供应商编码
     */
    @TableField("ERP_VENDOR_CODE")
    private String erpVendorCode;

    /**
     * 成本类型名称
     */
    @TableField("COST_TYPE_NAME")
    private String costTypeName;

    /**
     * 成本类型编码
     */
    @TableField("COST_TYPE_CODE")
    private String costTypeCode;

    /**
     * 预付款申请单号
     */
    @TableField("ADVANCE_APPLY_NUM")
    private String advanceApplyNum;

    /**
     * 合同序号
     */
    @TableField("CONTRACT_NUM")
    private String contractNum;

    /**
     * 合同编号
     */
    @TableField("CONTRACT_CODE")
    private String contractCode;

    /**
     * 合同头信息ID
     */
    @TableId("CONTRACT_HEAD_ID")
    private Long contractHeadId;

    /**
     * 是否代付
     */
    @TableField("IF_PAY_AGENT")
    private String ifPayAgent;

    /**
     * 代付方业务实体ID
     */
    @TableField("PAY_AGENT_ORG_ID")
    private Long payAgentOrgId;

    /**
     * 代付方业务实体编码
     */
    @TableField("PAY_AGENT_ORG_CODE")
    private String payAgentOrgCode;

    /**
     * 代付方业务实体名称
     */
    @TableField("PAY_AGENT_ORG_NAME")
    private String payAgentOrgName;

    /**
     * 代付方对接人(名称)
     */
    @TableField("PAY_AGENT_NICKNAME")
    private String payAgentNickname;

    /**
     * 代付方对接人(登录账号)
     */
    @TableField("PAY_AGENT_USERNAME")
    private String payAgentUsername;

    /**
     * 代付方部门ID
     */
    @TableField("PAY_AGENT_DEPT_ID")
    private String payAgentDeptId;

    /**
     * 代付方部门编码
     */
    @TableField("PAY_AGENT_DEPT_CODE")
    private String payAgentDeptCode;

    /**
     * 代付方部门名称
     */
    @TableField("PAY_AGENT_DEPT_NAME")
    private String payAgentDeptName;


    /**
     * 业务类型
     */
    @TableField("BUSINESS_TYPE")
    private String businessType;

    /**
     * 是否电站业务
     */
    @TableField("IF_POWER_STATION")
    private String ifPowerStation;

    /**
     * 货币ID
     */
    @TableField("CURRENCY_ID")
    private Long currencyId;

    /**
     * 货币编码
     */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /**
     * 货币编码
     */
    @TableField("CURRENCY_NAME")
    private String currencyName;

    /**
     * 汇率
     */
    @TableField("EXCHANGE_RATE")
    private BigDecimal exchangeRate;

    /**
     * 项目编号
     */
    @TableField("PROJECT_NUM")
    private String projectNum;

    /**
     * 项目名称
     */
    @TableField("PROJECT_NAME")
    private String projectName;

    /**
     * 申请人用户ID
     */
    @TableField("APPLY_USER_ID")
    private Long applyUserId;

    /**
     * 申请人用户名称
     */
    @TableField("APPLY_USER_NICKNAME")
    private String applyUserNickname;

    /**
     * 申请人用户工号
     */
    @TableField("APPLY_USER_NAME")
    private String applyUserName;


    /**
     * 申请部门ID
     */
    @TableField("APPLY_DEPT_ID")
    private String applyDeptId;

    /**
     * 申请部门名称
     */
    @TableField("APPLY_DEPT_NAME")
    private String applyDeptName;

    /**
     * 是否手动推计划外
     */
    @TableField("IF_OUT_PLAN")
    private String ifOutPlan;

    /**
     * 是否纸质附件
     */
    @TableField("IF_PAPER_ATTACH")
    private String ifPaperAttach;

    /**
     * 预付款单据状态
     */
    @TableField("ADVANCE_APPLY_STATUS")
    private String advanceApplyStatus;

    /**
     * 事由
     */
    @TableField("COMMENT")
    private String comment;

    /**
     * 费用说明
     */
    @TableField("COST_EXPLAIN")
    private String costExplain;

    /**
     * 申请日期
     */
    @TableField("APPLY_DATE")
    private LocalDate applyDate;

    /**
     * 申请付款金额
     */
    @TableField("APPLY_PAY_AMOUNT")
    private BigDecimal applyPayAmount;

    /**
     * 可用金额
     */
    @TableField("USABLE_AMOUNT")
    private BigDecimal usableAmount;

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
     * 费控回写单号
     */
    @TableField("BOE_NO")
    private String boeNo;

    /**
     * 费控来源系统单号
     */
    @TableField("SOURCE_BOE_NO")
    private String sourceBoeNo;

    /**
     * 打印路径
     */
    @TableField("PRINT_URL")
    private String printUrl;

    /**
     * 是否被网上开票引用
     */
    @TableField("IF_QUOTE")
    private String ifQuote;

    /**
     * 附件张数（费控对接需要字段）
     */
    @TableField("BPCOUNT")
    private Integer bpCount;

    /**
     * 资金计划告警忽略标识
     */
    @TableField("FUNDS_PLAN_IGNORE")
    private String fundsPlanIgnore;

    /**
     * 任务编号
     */
    @TableField("TASK_NUM")
    private String taskNum;

    /**
     * 任务名称
     */
    @TableField("TASK_NAME")
    private String taskName;

    /**
     * 合同总金额（含税）：以物料明细行金额自动汇总
     */
    @TableField("INCLUDE_TAX_AMOUNT")
    private BigDecimal includeTaxAmount;

    /**
     * 合同付款阶段
     */
    @TableField("PAYMENT_STAGE")
    private String paymentStage;

    /**
     * 虚拟发票号
     */
    @TableField("ONLINE_INVOICE_NUM")
    private String onlineInvoiceNum;

    /**
     * 虚拟发票ID
     */
    @TableId("ONLINE_INVOICE_ID")
    private Long onlineInvoiceId;

}
