
package com.midea.cloud.srm.model.cm.contract.dto;
import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * <pre>
 *  合同头信息DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-5-28 10:03
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class ContractHeadDTO extends BaseDTO{

    /**
     * 合同头信息ID
     */
    private Long contractHeadId;

    /**
     * 操作类型
     */
    private String contractType;

    /**
     * 合同类型
     */
    private String contractClass;

    /**
     * 业务实体id
     */
    private Long buId;

    /**
     * 模板头信息ID
     */
    private Long templHeadId;

    /**
     * 模板名称
     */
    private String templName;

    /**
     * 合同序号
     */
    private String contractNo;

    /**
     * 合同编号
     */
    private String contractCode;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 来源单号
     */
    private String sourceNumber;

    /**
     * 主/原合同编号
     */
    private String mainContractNo;

    /**
     * 供应商ID
     */
    private Long vendorId;

    /**
     * 供应商编码
     */
    private String vendorCode;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 币种编码
     */
    private String currencyCode;

    /**
     * 币种ID
     */
    private String currencyId;

    /**
     * 币种名称
     */
    private String currencyName;

    /**
     * 不含税总金额
     */
    private BigDecimal excludeTaxAmount;

    /**
     * 采购组织ID
     */
    private Long organizationId;

    /**
     * 采购组织编码
     */
    private String organizationCode;

    /**
     * 采购组织名称
     */
    private String organizationName;

    /**
     * 开工日期
     */
    private LocalDate engineerStartDate;

    /**
     * 完工日期
     */
    private LocalDate engineerEndDate;

    /**
     * 施工天数
     */
    private Long engineerDays;

    /**
     * 验收时间
     */
    private String acceptanceTime;

    /**
     * 保固期
     */
    private LocalDate warrantyPeriod;

    /**
     * 合同备注
     */
    private String contractRemark;

    /**
     * 审批意见
     */
    private String approvalAdvice;

    /**
     * 采购员ID
     */
    private Long buyerId;

    /**
     * 采购员名称
     */
    private String buyerName;

    /**
     * 合同状态
     */
    private String contractStatus;


    /**
     * 供应商确认时间
     */
    private LocalDate vendorConfirmDate;

    /**
     * 供应商确认人
     */
    private String vendorConfirmBy;

    /**
     * 已付总金额
     */
    private BigDecimal paidSumAmount;

    /**
     * 未付金额
     */
    private BigDecimal unpaidAmount;

    /**
     * 供应商备注
     */
    private String vendorRemark;

    /**
     * 供应商驳回原因
     */
    private String vendorRejectReason;

    /**
     * 合同寄出方式
     */
    private String contractSendType;

    /**
     * 物流单号
     */
    private String logisticsNo;

    /**
     * 预计到达时间
     */
    private LocalDate arrivalTime;

    /**
     * 合同签收人
     */
    private String receivedBy;

    /**
     * 起始保修日期
     */
    private LocalDate warrantyStartDate;

    /**
     * 截止保修日期
     */
    private LocalDate warrantyEndDate;

    /**
     * 供应商附件ID
     */
    private Long vendorFileId;

    /**
     * 供应商附件名称
     */
    private String vendorFileName;

    /**
     * 甲方
     */
    private String owner;

    /**
     * 传真
     */
    private String fax;

    /**
     * 电话
     */
    private String phone;

    /**
     * 签约地点
     */
    private String signingSite;

    /**
     * 邮编
     */
    private String postcode;

    /**
     * 开户行
     */
    private String openingBank;

    /**
     * 银行账号
     */
    private String bankAccount;

    /**
     * 法定代表人
     */
    private String legalPerson;

    /**
     * 委托代理人
     */
    private String entrustedAgent;

    /**
     * 乙方
     */
    private String secondParty;

    /**
     * 乙方传真
     */
    private String secondFax;

    /**
     * 乙方电话
     */
    private String secondPhone;

    /**
     * 乙方地点
     */
    private String secondSite;

    /**
     * 乙方邮编
     */
    private String secondPostcode;

    /**
     * 乙方开户行
     */
    private String secondOpeningBank;

    /**
     * 乙方银行账号
     */
    private String secondBankAccount;

    /**
     * 乙方法定代表人
     */
    private String secondLegalPerson;

    /**
     * 乙方委托代理人
     */
    private String secondEntrustedAgent;

    /**
     * 乙方签订日期
     */
    private LocalDate secondSignDate;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    private LocalDate startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    private LocalDate endDate;

    /**
     * 创建人ID
     */
    private Long createdId;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 创建人IP
     */
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    private String lastUpdatedByIp;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 模板类型
     */
    private String templType;

    /**
     * 用户身份类型
     */
    private String userType;

    /**
     * 起始创建日期
     */
    private Date startCreationDate;

    /**
     * 截止创建日期
     */
    private Date endCreationDate;

    /**
     * 查询条件供应商ID
     */
    private String vendorIdMan;

    /**
     * 是否虚拟合同（N/Y）
     */
    private String ceeaIfVirtual;

    /**
     * 框架协议编号
     */
    private String frameworkAgreementCode;

    /**
     * 框架协议名称
     */
    private String frameworkAgreementName;

    /**
     * 框架协议ID
     */
    private Long frameworkAgreementId;

    /**
     * 开始时间
     */
    private Date toDate;

    /**
     * 结束时间
     */
    private Date fromDate;


    /**
     * 是否框架协议(Y-是,N-否;默认为否)
     */
    private String isFrameworkAgreement;
}
