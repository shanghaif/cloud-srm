package com.midea.cloud.srm.model.cm.contract.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  合同头表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 10:10:46
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_contract_head")
public class ContractHead extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 合同头信息ID
     */
    @TableId("CONTRACT_HEAD_ID")
    private Long contractHeadId;

    /**
     * 合同模板头ID
     */
    @TableId("MODEL_HEAD_ID")
    private Long modelHeadId;

    /**
     * 模板名称
     */
    @TableField("MODEL_NAME")
    private String modelName;

    /**
     * 合同类型,对应字典:CONTRACT_TYPE
     */
    @TableField("CONTRACT_TYPE")
    private String contractType;

    /**
     * 合同分类
     */
    @TableField("CONTRACT_CLASS")
    private String contractClass;

    /**
     * 模板头信息ID
     */
    @TableField("TEMPL_HEAD_ID")
    private Long templHeadId;

    /**
     * 模板名称
     */
    @TableField("TEMPL_NAME")
    private String templName;

    /**
     * 合同序号
     */
    @TableField("CONTRACT_NO")
    private String contractNo;

    /**
     * 合同名称
     */
    @TableField("CONTRACT_NAME")
    private String contractName;

    /**
     * 合同内容
     */
    @TableField("CONTENT")
    private String content;

    /**
     * 是否可修改全部内容(Y-是,N-否)
     */
    @TableField("ENABLE")
    private String enable;

    /**
     * 价格审批单号
     */
    @TableField("SOURCE_NUMBER")
    private String sourceNumber;

    /**
     * 主/原合同编号
     */
    @TableField("MAIN_CONTRACT_NO")
    private String mainContractNo;

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
     * 币种编码
     */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /**
     * 币种ID
     */
    @TableField("CURRENCY_ID")
    private Long currencyId;

    /**
     * 币种名称
     */
    @TableField("CURRENCY_NAME")
    private String currencyName;

    /**
     * 税率值
     */
    @TableField("TAX_RATE")
    private BigDecimal taxRate;

    /**
     * 税率ID
     */
    @TableField("TAX_ID")
    private Long taxId;

    /**
     * 税率名称
     */
    @TableField("TAX_NAME")
    private String taxName;

    /**
     * 税率编码
     */
    @TableField("TAX_KEY")
    private String taxKey;

    /**
     * 不含税总金额
     */
    @TableField("EXCLUDE_TAX_AMOUNT")
    private BigDecimal excludeTaxAmount;

    /**
     * 采购组织ID
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
     * 开工日期
     */
    @TableField("ENGINEER_START_DATE")
    private LocalDate engineerStartDate;

    /**
     * 完工日期
     */
    @TableField("ENGINEER_END_DATE")
    private LocalDate engineerEndDate;

    /**
     * 施工天数
     */
    @TableField("ENGINEER_DAYS")
    private Long engineerDays;

    /**
     * 验收时间
     */
    @TableField("ACCEPTANCE_TIME")
    private String acceptanceTime;

    /**
     * 保固期
     */
    @TableField("WARRANTY_PERIOD")
    private LocalDate warrantyPeriod;

    /**
     * 合同备注
     */
    @TableField("CONTRACT_REMARK")
    private String contractRemark;

    /**
     * 审批意见
     */
    @TableField("APPROVAL_ADVICE")
    private String approvalAdvice;

    /**
     * 采购员ID
     */
    @TableField("BUYER_ID")
    private Long buyerId;

    /**
     * 采购员名称
     */
    @TableField("BUYER_NAME")
    private String buyerName;

    /**
     * 合同状态
     */
    @TableField("CONTRACT_STATUS")
    private String contractStatus;


    /**
     * 供应商确认时间
     */
    @TableField("VENDOR_CONFIRM_DATE")
    private LocalDate vendorConfirmDate;

    /**
     * 供应商确认人
     */
    @TableField("VENDOR_CONFIRM_BY")
    private String vendorConfirmBy;

    /**
     * 已付总金额
     */
    @TableField("PAID_SUM_AMOUNT")
    private BigDecimal paidSumAmount;

    /**
     * 未付金额
     */
    @TableField("UNPAID_AMOUNT")
    private BigDecimal unpaidAmount;

    /**
     * 供应商备注
     */
    @TableField("VENDOR_REMARK")
    private String vendorRemark;

    /**
     * 供应商驳回原因
     */
    @TableField("VENDOR_REJECT_REASON")
    private String vendorRejectReason;

    /**
     * 合同寄出方式
     */
    @TableField("CONTRACT_SEND_TYPE")
    private String contractSendType;

    /**
     * 物流单号
     */
    @TableField("LOGISTICS_NO")
    private String logisticsNo;

    /**
     * 预计到达时间
     */
    @TableField("ARRIVAL_TIME")
    private LocalDate arrivalTime;

    /**
     * 合同签收人
     */
    @TableField("RECEIVED_BY")
    private String receivedBy;

    /**
     * 起始保修日期
     */
    @TableField("WARRANTY_START_DATE")
    private LocalDate warrantyStartDate;

    /**
     * 截止保修日期
     */
    @TableField("WARRANTY_END_DATE")
    private LocalDate warrantyEndDate;

    /**
     * 供应商附件ID
     */
    @TableField("VENDOR_FILE_ID")
    private Long vendorFileId;

    /**
     * 供应商附件名称
     */
    @TableField("VENDOR_FILE_NAME")
    private String vendorFileName;

    /**
     * 甲方
     */
    @TableField("OWNER")
    private String owner;

    /**
     * 传真
     */
    @TableField("FAX")
    private String fax;

    /**
     * 电话
     */
    @TableField("PHONE")
    private String phone;

    /**
     * 签约地点
     */
    @TableField("SIGNING_SITE")
    private String signingSite;

    /**
     * 邮编
     */
    @TableField("POSTCODE")
    private String postcode;

    /**
     * 开户行
     */
    @TableField("OPENING_BANK")
    private String openingBank;

    /**
     * 银行账号
     */
    @TableField("BANK_ACCOUNT")
    private String bankAccount;

    /**
     * 法定代表人
     */
    @TableField("LEGAL_PERSON")
    private String legalPerson;

    /**
     * 委托代理人
     */
    @TableField("ENTRUSTED_AGENT")
    private String entrustedAgent;

    /**
     * 乙方
     */
    @TableField("SECOND_PARTY")
    private String secondParty;

    /**
     * 乙方传真
     */
    @TableField("SECOND_FAX")
    private String secondFax;

    /**
     * 乙方电话
     */
    @TableField("SECOND_PHONE")
    private String secondPhone;

    /**
     * 乙方地点
     */
    @TableField("SECOND_SITE")
    private String secondSite;

    /**
     * 乙方邮编
     */
    @TableField("SECOND_POSTCODE")
    private String secondPostcode;

    /**
     * 乙方开户行
     */
    @TableField("SECOND_OPENING_BANK")
    private String secondOpeningBank;

    /**
     * 乙方银行账号
     */
    @TableField("SECOND_BANK_ACCOUNT")
    private String secondBankAccount;

    /**
     * 乙方银行账户名称
     */
    @TableField("SECOND_BANK_ACCOUNT_NAME")
    private String secondBankAccountName;

    /**
     * 乙方法定代表人
     */
    @TableField("SECOND_LEGAL_PERSON")
    private String secondLegalPerson;

    /**
     * 乙方委托代理人
     */
    @TableField("SECOND_ENTRUSTED_AGENT")
    private String secondEntrustedAgent;

    /**
     * 乙方签订日期
     */
    @TableField("SECOND_SIGN_DATE")
    private LocalDate secondSignDate;

	/**
	 * 合同总金额（含税）：以物料明细行金额自动汇总
	 */
	@TableField("INCLUDE_TAX_AMOUNT")
	private BigDecimal includeTaxAmount;

	/**
	 * 合同有效日期从
	 */
	@TableField("EFFECTIVE_DATE_FROM")
	private LocalDate effectiveDateFrom;

	/**
	 * 合同有效日期至
	 */
	@TableField("EFFECTIVE_DATE_TO")
	private LocalDate effectiveDateTo;

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
     * 是否框架协议(Y-是,N-否;默认为否)
     */
    @TableField("IS_FRAMEWORK_AGREEMENT")
    private String isFrameworkAgreement;

    /**
     * 是否总部(Y-是,N-否;默认为否)
     */
    @TableField("IS_HEADQUARTERS")
    private String isHeadquarters;

    /**
     * 起草人意见
     */
    @TableField("DRAFTER_OPINION")
    private String drafterOpinion;

    /**
     * 合同级别(字典:CONTRACT_LEVEL)
     */
    @TableField("CONTRACT_LEVEL")
    private String contractLevel;

    /**
     * 业务实体id
     */
    @TableField("BU_ID")
    private Long buId;

    /**
     * 业务实体编码
     */
    @TableField("BU_CODE")
    private String buCode;

    /**
     * 业务实体名称
     */
    @TableField("BU_NAME")
    private String buName;

    /**
     * 业务实体全路径id
     */
    @TableField("BU_FULL_PATH_ID")
    private String buFullPathId;

    /**
     * 合同编号
     */
    @TableField("CONTRACT_CODE")
    private String contractCode;

    /**
     * 原合同编号
     */
    @TableField("CONTRACT_OLD_CODE")
    private String contractOldCode;

    /**
     * 是否电站业务(Y-是,N-否)
     */
    @TableField("IS_POWER_STATION")
    private String isPowerStation;

    /**
     * ERP供应商Id
     */
    @TableField("ERP_VENDOR_ID")
    private Long erpVendorId;

    /**
     * ERP供应商Code
     */
    @TableField("ERP_VENDOR_CODE")
    private String erpVendorCode;

    /**
     * 来源类型
     */
    @TableField("SOURCE_TYPE")
    private String sourceType;

    /**
     * 框架协议编号
     */
    @TableField("CEEA_FRAMEWORK_AGREEMENT_CODE")
    private String frameworkAgreementCode;

    /**
     * 框架协议名称
     */
    @TableField("CEEA_FRAMEWORK_AGREEMENT_NAME")
    private String frameworkAgreementName;

    /**
     * 框架协议ID
     */
    @TableField(value = "CEEA_FRAMEWORK_AGREEMENT_ID")
    private Long frameworkAgreementId;

    /**
     * 是否便携合同(Y-是,N-否)
     */
    @TableField("CEEA_IS_PORTABLE_CONTRACT")
    private String ceeaIsPortableContract;

    /**
     * 原合同ID
     */
    @TableField("CEEA_CONTRACT_OLD_ID")
    private Long ceeaContractOldId;

    /**
     * 是否虚拟合同（N/Y）
     */
    @TableField("CEEA_IF_VIRTUAL")
    private String ceeaIfVirtual;

    /**
     * 管控方式(MANAGEMENT_CONTROL_MODEL)
     * {@link com.midea.cloud.common.enums.contract.ManagementControlModel}
     */
    @TableField("CEEA_CONTROL_METHOD")
    @NotBlank(message = "控制类型不能为空")
    private String ceeaControlMethod;

    /**
     * 付款计划
     */
    @TableField(exist = false)
    private List<PayPlan> payPlans;

    /**
     * 原合同文件ID文件ID
     */
    @TableField("ORIGINAL_CONTRACT_FILEUPLOAD_ID")
    private Long originalContractFileuploadId;

    /**
     * 盖章后合同ID
     */
    @TableField("STAMP_CONTRACT_FILEUPLOAD_ID")
    private Long stampContractFileuploadId;

    /**
     * 盖章后合同文件名
     */
    @TableField("STAMP_CONTRACT_FILE_NAME")
    private String stampContractFileName;

    /**
     * 审批流ID
     */
    @TableField("APPROVAL_FLOW_ID")
    private Long approvalFlowID;

}
