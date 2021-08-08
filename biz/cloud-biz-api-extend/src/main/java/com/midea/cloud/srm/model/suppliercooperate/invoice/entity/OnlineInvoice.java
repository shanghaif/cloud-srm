package com.midea.cloud.srm.model.suppliercooperate.invoice.entity;

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
 *  网上开票表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-31 16:47:48
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_sc_online_invoice")
public class OnlineInvoice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,网上开票ID
     */
    @TableId("ONLINE_INVOICE_ID")
    private Long onlineInvoiceId;

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
     * 网上发票号
     */
    @TableField("ONLINE_INVOICE_NUM")
    private String onlineInvoiceNum;

    /**
     * 税务发票号
     */
    @TableField("TAX_INVOICE_NUM")
    private String taxInvoiceNum;

    /**
     * 费控发票号(备用,暂不使用)
     */
    @TableField("FSSC_NO")
    private String fsscNo;

    /**
     * 币种ID
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
    @TableField("EXCHANGE_RATE")
    private BigDecimal exchangeRate;

    /**
     * 发票日期
     */
    @TableField("INVOICE_DATE")
    private LocalDate invoiceDate;

    /**
     * 实际发票金额(含税)
     */
    @TableField("ACTUAL_INVOICE_AMOUNT_Y")
    private BigDecimal actualInvoiceAmountY;

    /**
     * 发票税额
     */
    @TableField("INVOICE_TAX")
    private BigDecimal invoiceTax;

    /**
     * 发票净额(不含税)
     */
    @TableField("ACTUAL_INVOICE_AMOUNT_N")
    private BigDecimal actualInvoiceAmountN;

    /**
     * 含税总额(系统)
     */
    @TableField("TAX_TOTAL_AMOUNT")
    private BigDecimal taxTotalAmount;

    /**
     * 税额(系统)
     */
    @TableField("TOTAL_TAX")
    private BigDecimal totalTax;

    /**
     * 付款账期编码
     */
    @TableField("PAY_ACCOUNT_PERIOD_CODE")
    private String payAccountPeriodCode;

    /**
     * 付款账期名称(费控需要传)
     */
    @TableField("PAY_ACCOUNT_PERIOD_NAME")
    private String payAccountPeriodName;

    /**
     * 付款方式
     */
    @TableField("PAY_METHOD")
    private String payMethod;

    /**
     * 应付账款到期日
     */
    @TableField("ACCOUNT_PAYABLE_DEALINE")
    private LocalDate accountPayableDealine;

    /**
     * 业务类型
     */
    @TableField("BUSINESS_TYPE")
    private String businessType;

    /**
     * 是否纸质附件
     */
    @TableField("IF_PAPER_ATTACH")
    private String ifPaperAttach;

    /**
     * 审批人ID
     */
    @TableField("APPROVER_ID")
    private Long approverId;

    /**
     * 审批人名称
     */
    @TableField("APPROVER_NICKNAME")
    private String approverNickname;

    /**
     * 审批人登录账号
     */
    @TableField("APPROVER_USERNAME")
    private String approverUsername;

    /**
     * 审批人员工工号
     */
    @TableField("APPROVER_EMP_NO")
    private String approverEmpNo;

    /**
     * 审批部门编码
     */
    @TableField("APPROVER_DEPTID")
    private String approverDeptid;

    /**
     * 审批部门
     */
    @TableField("APPROVER_DEPT")
    private String approverDept;

    /**
     * 合同编号
     */
    @TableField("CONTRACT_CODE")
    private String contractCode;

    /**
     * 发票状态
     */
    @TableField("INVOICE_STATUS")
    private String invoiceStatus;

    /**
     * 摘要
     */
    @TableField("COMMENT")
    private String comment;

    /**
     * 起草人意见
     */
    @TableField("DRAFTER_VIEW")
    private String drafterView;

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
     * 网上开票类型:VENDOR_INVOICE,//供应商端开具  BUYER_INVOICE;//采购商端开具
     */
    @TableField("ONLINE_INVOICE_TYPE")
    private String onlineInvoiceType;

    /**
     * 已付款金额
     */
    @TableField("PAID_AMOUNT")
    private BigDecimal paidAmount;

    /**
     * 未付款金额
     */
    @TableField("UN_PAID_AMOUNT")
    private BigDecimal unPaidAmount;

    /**
     * 本次付款金额
     */
    @TableField("PAYING_AMOUNT")
    private BigDecimal payingAmount;

    /**
     * 导入状态
     */
    @TableField("IMPORT_STATUS")
    private String importStatus;

    /**
     * 项目编号
     */
    @TableField(exist = false)
    private String projectCode;

    /**
     * 项目名称
     */
    @TableField(exist = false)
    private String projectName;

    /**
     * 费控单号
     */
    @TableField("BOE_NO")
    private String boeNo;

    /**
     * 费控打印URL
     */
    @TableField("PRINT_URL")
    private String printUrl;

    /**
     * 是否核销预付的虚拟发票,是传Y,否传N
     */
    @TableField("VIRTUAL_INVOICE")
    private String virtualInvoice;

    /**
     * 是否服务类
     */
    @TableField("IS_SERVICE")
    private String isService;

    /**
     * 纸质张数
     */
    @TableField("BPCOUNT")
    private Integer bpcount;

    /**
     * erp instid
     */
    @TableField("INSTID")
    private String instid;

    /**
     * 预算告警忽略标识
     */
    @TableField("BUDGET_IGNORE")
    private String budgetIgnore;
}
