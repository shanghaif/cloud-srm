package com.midea.cloud.srm.model.pm.ps.payment.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  付款申请-头表（隆基新增） 模型
 * </pre>
*
* @author xiexh12@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-25 21:04:34
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_ps_payment_apply_head")
public class CeeaPaymentApplyHead extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 付款申请主键ID
     */
    @TableId("PAYMENT_APPLY_HEAD_ID")
    private Long paymentApplyHeadId;

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
     * 供应商编号
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

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
     * 付款申请单号
     */
    @TableField("PAYMENT_APPLY_NUMBER")
    private String paymentApplyNumber;

    /**
     * 合同编号
     */
    @TableField("CONTRACT_NUM")
    private String contractNum;


    /**
     * 合同编码
     */
    @TableField("CONTRACT_CODE")
    private String contractCode;

    /**
     * 是否纸质附件
     */
    @TableField("IF_PAPER_ATTACH")
    private String ifPaperAttach;

    /**
     * 申请日期
     */
    @TableField("APPLY_DATE")
    private Date applyDate;

    /**
     * 单据状态
     */
    @TableField("RECEIPT_STATUS")
    private String receiptStatus;

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
     * 代付方对接人
     */
    @TableField("PAY_AGENT_LINKMAN")
    private String payAgentLinkman;

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
     * 是否手动推计划外
     */
    @TableField("IF_MANUAL_OUT_PLAN")
    private String ifManualOutPlan;

    /**
     * 币种id
     */
    @TableField("CURRENCY_ID")
    private Long currencyId;

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
     * 汇率
     */
    @TableField("GIDAILY_RATE")
    private BigDecimal gidailyRate;

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
     * 申请人用户
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
     * 申请部门编码
     */
    @TableField("APPLY_DEPT_CODE")
    private String applyDeptCode;

    /**
     * 发票总额
     */
    @TableField("INVOICE_TOTAL_AMOUNT")
    private BigDecimal invoiceTotalAmount;

    /**
     * 申请付款总额
     */
    @TableField("APPLY_PAYMENT_TOTAL_AMOUNT")
    private BigDecimal applyPaymentTotalAmount;

    /**
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;

    /**
     * 摘要
     */
    @TableField("SUMMARY")
    private String summary;

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
     * 是否电站业务
     */
    @TableField("IF_POWER_STATION_BUSINESS")
    private String ifPowerStationBusiness;

    /**
     * 任务名称
     */
    @TableField("TASK_NAME")
    private String taskName;

    /**
     * 任务编号
     */
    @TableField("TASK_CODE")
    private String taskCode;

    /**
     * 项目编号
     */
    @TableField("PROJECT_CODE")
    private String projectCode;

    /**
     * 项目名称
     */
    @TableField("PROJECT_NAME")
    private String projectName;

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
     * 附件张数
     */
    @TableField("BPCOUNT")
    private Integer bpCount;


    /**
     * 代付对接人员工工号
     */
    @TableField("PAY_AGENT_CODE")
    private String payAgentCode;

    /**
     * 代付对接人用户名
     */
    @TableField("PAY_AGENT_NAME")
    private String payAgentName;

    /**
     * 资金计划告警忽略标识
     */
    @TableField("FUNDS_PLAN_IGNORE")
    private String fundsPlanIgnore;

}
