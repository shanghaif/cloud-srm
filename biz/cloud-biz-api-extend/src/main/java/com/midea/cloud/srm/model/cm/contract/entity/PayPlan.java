package com.midea.cloud.srm.model.cm.contract.entity;

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
 *  合同付款计划 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 10:18:17
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_contract_pay_plan")
public class PayPlan extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 付款阶段: paymentStage
     * 付款方式: payMethod
     * 付款条件: payExplain
     * 付款比例: paymentRatio
     * 阶段付款金额: stagePaymentAmount
     * 计划付款日期: plannedPaymentDate
     * 天数: dateNum
     */

    /**
     * 主键ID,付款计划ID
     */
    @TableId("PAY_PLAN_ID")
    private Long payPlanId;

    /**
     * 合同头信息ID
     */
    @TableField("CONTRACT_HEAD_ID")
    private Long contractHeadId;

    /**
     * 里程碑阶段
     */
    @TableField("MILESTONE_STAGE")
    private Long milestoneStage;

    /**
     * 里程碑
     */
    @TableField("MILESTONE")
    private String milestone;

    /**
     * 里程碑说明
     */
    @TableField("MILESTONE_EXPLAIN")
    private String milestoneExplain;

    /**
     * 里程碑日期
     */
    @TableField("MILESTONE_DATE")
    private LocalDate milestoneDate;

    /**
     * 里程碑状态
     */
    @TableField("MILESTONE_STATUS")
    private String milestoneStatus;

    /**
     * 付款类型ID
     */
    @TableField("PAY_TYPE_ID")
    private Long payTypeId;

    /**
     * 付款类型
     */
    @TableField("PAY_TYPE")
    private String payType;

    /**
     * 付款阶段
     */
    @TableField("PAY_STAGE")
    private Long payStage;

    /**
     * 付款说明(付款条件)
     */
    @TableField("PAY_EXPLAIN")
    private String payExplain;

    /**
     * 付款比例
     */
    @TableField("PAY_RATIO")
    private Long payRatio;

    /**
     * 扣罚比例
     */
    @TableField("DEDUCTION_RATIO")
    private Long deductionRatio;

    /**
     * 支付方式（付款方式）
     */
    @TableField("PAY_METHOD")
    private String payMethod;

    /**
     * 付款延迟天数
     */
    @TableField("DELAYED_DAYS")
    private Long delayedDays;

    /**
     * 不含税付款金额
     */
    @TableField("EXCLUDE_TAX_PAY_AMOUNT")
    private BigDecimal excludeTaxPayAmount;

    /**
     * 付款税率编码
     */
    @TableField("TAX_KEY")
    private String taxKey;

    /**
     * 付款税率
     */
    @TableField("PAY_TAX")
    private BigDecimal payTax;

    /**
     * 币种编码
     */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /**
     * 付款日期
     */
    @TableField("PAY_DATE")
    private LocalDate payDate;

    /**
     * 逻辑说明
     */
    @TableField("LOGICAL_EXPLAIN")
    private String logicalExplain;

    /**
     * 付款状态
     */
    @TableField("PAY_STATUS")
    private String payStatus;

    /**
     * 已付金额
     */
    @TableField("PAID_AMOUNT")
    private BigDecimal paidAmount;

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
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 付款期数（合同期数）
     */
    @TableField("PAYMENT_PERIOD")
    private String paymentPeriod;

    /**
     * 付款阶段(字典)（付款条件）
     */
    @TableField("PAYMENT_STAGE")
    private String paymentStage;

    /**
     * 天数
     */
    @TableField("DATE_NUM")
    private String dateNum;

    /**
     * 阶段付款金额
     */
    @TableField("STAGE_PAYMENT_AMOUNT")
    private BigDecimal stagePaymentAmount;

    /**
     * 付款比例
     */
    @TableField("PAYMENT_RATIO")
    private BigDecimal paymentRatio;

    /**
     * 计划付款日期
     */
    @TableField("PLANNED_PAYMENT_DATE")
    private LocalDate plannedPaymentDate;

    /**
     * 原合同合同付款计划ID
     */
    @TableField("SOURCE_ID")
    private Long sourceId;

    /**
     * 是否能编辑
     */
    @TableField(exist = false)
    private String isEdit;

    /**
     * 变更字段
     */
    @TableField("CHANGE_FIELD")
    private String changeField;

    /**
     * 已付款金额(todo 待删除)
     */
    @TableField("PAID_MOUNT")
    private BigDecimal paidMount;


}
