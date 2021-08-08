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
 *  付款申请-行表（隆基新增） 模型
 * </pre>
*
* @author xiexh12@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-25 21:12:58
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_ps_payment_apply_line")
public class CeeaPaymentApplyLine extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 付款申请行表主键ID
     */
    @TableId("PAYMENT_APPLY_LINE_ID")
    private Long paymentApplyLineId;

    /**
     * 付款申请头ID
     */
    @TableField("PAYMENT_APPLY_HEAD_ID")
    private Long paymentApplyHeadId;

    /**
     * 行号
     */
    @TableField("ROW_NUM")
    private Integer rowNum;

    /**
     * 联行号
     */
    @TableField("UNION_CODE")
    private String unionCode;

    /**
     * 网上发票号
     */
    @TableField("INVOICE_NUM")
    private String invoiceNum;

    /**
     * 合同编号
     */
    @TableField("CONTRACT_NUM")
    private String contractNum;

    /**
     * 项目名称
     */
    @TableField("PROJECT_NAME")
    private String projectName;

    /**
     * 项目编号
     */
    @TableField("PROJECT_NUM")
    private String projectNum;

    /**
     * 银行名称
     */
    @TableField("BANK_NAME")
    private String bankName;

    /**
     * 账户名称
     */
    @TableField("BANK_ACCOUNT_NAME")
    private String bankAccountName;

    /**
     * 银行账号
     */
    @TableField("BANK_ACCOUNT")
    private String bankAccount;

    /**
     * 开户银行(银行分行)
     */
    @TableField("OPENING_BANK")
    private String openingBank;

    /**
     * 应付款到账日
     */
    @TableField("DUE_DATE")
    private Date dueDate;

    /**
     * 付款方式
     */
    @TableField("PAY_METHOD")
    private String payMethod;

    /**
     * 发票总额
     */
    @TableField("INVOICE_TOTAL_AMOUNT")
    private BigDecimal invoiceTotalAmount;

    /**
     * 申请付款金额
     */
    @TableField("APPLY_PAY_AMOUNT")
    private BigDecimal applyPayAmount;

    /**
     * 明细备注
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


}
