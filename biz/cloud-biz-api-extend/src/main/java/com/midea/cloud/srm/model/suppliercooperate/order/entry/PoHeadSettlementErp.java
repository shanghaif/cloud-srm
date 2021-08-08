package com.midea.cloud.srm.model.suppliercooperate.order.entry;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/11/2 11:17
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_po_head_settlement_erp")
public class PoHeadSettlementErp extends BaseErrorCell{
    @TableId("PO_HEAD_SETTLEMENT_ERP_ID")
    private Long poHeadSettlementErpId;

    @TableField("PO_HEADER_ID")
    protected String poHeaderId;
    @TableField("PO_NUMBER")
    protected String poNumber;
    @TableField("SETTLEMENT_PO_NUMBER")
    protected String settlementPoNumber;

    @TableField("AGENT_ID")
    protected String agentId;

    @TableField("AGENT_CODE")
    protected String agentCode;

    @TableField("AGENT_NAME")
    protected String agentName;

    @TableField("BUSINESS_UNIT_ID")
    protected String businessUnitId;

    @TableField("BUSINESS_UNIT_CODE")
    protected String businessUnitCode;

    @TableField("PO_TYPE")
    protected String poType;

    @TableField("PO_ORDER_TYPE")
    protected String poOrderType;

    @TableField("VENDOR_ID")
    protected String vendorId;

    @TableField("VENDOR_CODE")
    protected String vendorCode;

    @TableField("VENDOR_SITE_ID")
    protected String vendorSiteId;

    @TableField("VENDOR_SITE_CODE")
    protected String vendorSiteCode;

    @TableField("VENDOR_CONTACT_ID")
    protected String vendorContactId;

    @TableField("VENDOR_CONTACT_CODE")
    protected String vendorContactCode;

    @TableField("VENDOR_CONTACT_NAME")
    protected String vendorContactName;

    @TableField("SHIP_TO_LOCATION_ID")
    protected String shipToLocationId;

    @TableField("SHIP_TO_LOCATION_CODE")
    protected String shipToLocationCode;

    @TableField("SHIP_TO_LOCATION_ADDRESS")
    protected String shipToLocationAddress;

    @TableField("BILL_TO_LOCATION_ID")
    protected String billToLocationId;

    @TableField("BILL_TO_LOCATION_CODE")
    protected String billToLocationCode;

    @TableField("CONTRACT_NUMBER")
    protected String contractNumber;

    @TableField("SHIP_VIA_LOOKUP_CODE")
    protected String shipViaLookupCode;

    @TableField("FOB_LOOKUP_CODE")
    protected String fobLookupCode;

    @TableField("FREIGHT_TERMS_LOOKUP_CODE")
    protected String freightTermsLookupCode;

    @TableField("PO_STATUS")
    protected String poStatus;

    @TableField("CURRENCY_CODE")
    protected String currencyCode;

    @TableField("RATE_TYPE")
    protected String rateType;

    @TableField("RATE_DATE")
    protected String rateDate;

    @TableField("RATE")
    protected String rate;

    @TableField("START_DATE")
    protected String startDate;

    @TableField("END_DATE")
    protected String endDate;

    @TableField("BLANKET_TOTAL_AMOUNT")
    protected String blanketTotalAmount;

    @TableField("REVISION_NUM")
    protected String revisionNum;

    @TableField("APPROVED_DATE")
    protected String approvedDate;

    @TableField("MIN_RELEASE_AMOUNT")
    protected String minReleaseAmount;

    @TableField("NOTE_TO_AUTHORIZER")
    protected String noteToAuthorizer;

    @TableField("NOTE_TO_VENDOR")
    protected String noteToVendor;

    @TableField("NOTE_TO_RECEIVER")
    protected String noteToReceiver;

    @TableField("PRINT_COUNT")
    protected String printCount;

    @TableField("PRINTED_DATE")
    protected String printedDate;

    @TableField("CONTRACTOR")
    protected String contractor;

    @TableField("CONTRACTOR_PHONE")
    protected String contractorPhone;

    @TableField("CONTRACTOR_FAX")
    protected String contractorFax;

    @TableField("QUALITY_AGREEMENT_NUMBER")
    protected String qualityAgreementNumber;

    @TableField("TECHNOLOGY_AGREEMENT_NUMBER")
    protected String technologyAgreementNumber;

    @TableField("NEGOTIATION_NUMBER")
    protected String negotiationNumber;

    @TableField("VENDOR_ORDER_NUM")
    protected String vendorOrderNum;

    @TableField("CONFIRMING_ORDER_FLAG")
    protected String confirmingOrderFlag;

    @TableField("REPLY_DATE")
    protected String replyDate;

    @TableField("REPLY_METHOD_LOOKUP_CODE")
    protected String replyMethodLookupCode;

    @TableField("RFQ_CLOSE_DATE")
    protected String rfqCloseDate;

    @TableField("QUOTE_TYPE_LOOKUP_CODE")
    protected String quoteTypeLookupCode;

    @TableField("QUOTATION_CLASS_CODE")
    protected String quotationClassCode;

    @TableField("QUOTE_WARNING_DELAY_UNIT")
    protected String quoteWarningDelayUnit;

    @TableField("QUOTE_WARNING_DELAY")
    protected String quoteWarningDelay;

    @TableField("QUOTE_VENDOR_QUOTE_NUMBER")
    protected String quoteVendorQuoteNumber;

    @TableField("ACCEPTANCE_REQUIRED_FLAG")
    protected String acceptanceRequiredFlag;

    @TableField("ACCEPTANCE_DUE_DATE")
    protected String acceptanceDueDate;

    @TableField("CLOSED_DATE")
    protected String closedDate;

    @TableField("USER_HOLD_FLAG")
    protected String userHoldFlag;

    @TableField("APPROVAL_REQUIRED_FLAG")
    protected String approvalRequiredFlag;

    @TableField("CANCEL_FLAG")
    protected String cancelFlag;

    @TableField("FIRM_STATUS_LOOKUP_CODE")
    protected String firmStatusLookupCode;

    @TableField("FIRM_DATE")
    protected String firmDate;

    @TableField("FROZEN_FLAG")
    protected String frozenFlag;

    @TableField("CLOSED_CODE")
    protected String closedCode;

    @TableField("USSGL_TRANSACTION_CODE")
    protected String ussglTransactionCode;

    @TableField("SUPPLY_AGREEMENT_FLAG")
    protected String supplyAgreementFlag;

    @TableField("EDI_PROCESSED_FLAG")
    protected String ediProcessedFlag;

    @TableField("EDI_PROCESSED_STATUS")
    protected String ediProcessedStatus;

    @TableField("PO_RELEASE_ID")
    protected String poReleaseId;

    @TableField("PO_RELEASE_NUM")
    protected String poReleaseNum;

    @TableField("ERP_PO_TYPE")
    protected String erpPoType;

    @TableField("ERP_PO_TYPE_DESC")
    protected String erpPoTypeDesc;

    @TableField("ERP_CREATION_DATE")
    protected String erpCreationDate;

    @TableField("ERP_LAST_UPDATE_DATE")
    protected String erpLastUpdateDate;

    @TableField("COMMENTS")
    protected String comments;

    @TableField("OTHER_COMMENTS")
    protected String otherComments;

    @TableField("TAX_RATE")
    protected String taxRate;

    @TableField("PAYMENT_METHOD")
    protected String paymentMethod;

    @TableField("PAYMENT_TERM")
    protected String paymentTerm;

    @TableField("MANUFACTURER_CODE")
    protected String manufacturerCode;

    @TableField("MANUFACTURER_NAME")
    protected String manufacturerName;

    @TableField("C_ATTRIBUTE1")
    protected String C_ATTRIBUTE1;

    @TableField("C_ATTRIBUTE2")
    protected String C_ATTRIBUTE2;

    @TableField("C_ATTRIBUTE3")
    protected String C_ATTRIBUTE3;
    @TableField("C_ATTRIBUTE4")
    protected String C_ATTRIBUTE4;
    @TableField("C_ATTRIBUTE5")
    protected String C_ATTRIBUTE5;
    @TableField("C_ATTRIBUTE6")
    protected String C_ATTRIBUTE6;
    @TableField("C_ATTRIBUTE7")
    protected String C_ATTRIBUTE7;
    @TableField("C_ATTRIBUTE8")
    protected String C_ATTRIBUTE8;
    @TableField("C_ATTRIBUTE9")
    protected String C_ATTRIBUTE9;
    @TableField("C_ATTRIBUTE10")
    protected String C_ATTRIBUTE10;

    @TableField("C_ATTRIBUTE11")
    protected String C_ATTRIBUTE11;
    @TableField("C_ATTRIBUTE12")
    protected String C_ATTRIBUTE12;
    @TableField("C_ATTRIBUTE13")
    protected String C_ATTRIBUTE13;
    @TableField("C_ATTRIBUTE14")
    protected String C_ATTRIBUTE14;
    @TableField("C_ATTRIBUTE15")
    protected String C_ATTRIBUTE15;
    @TableField("C_ATTRIBUTE16")
    protected String C_ATTRIBUTE16;
    @TableField("C_ATTRIBUTE17")
    protected String C_ATTRIBUTE17;
    @TableField("C_ATTRIBUTE18")
    protected String C_ATTRIBUTE18;
    @TableField("C_ATTRIBUTE19")
    protected String C_ATTRIBUTE19;
    @TableField("C_ATTRIBUTE20")
    protected String C_ATTRIBUTE20;

    @TableField("C_ATTRIBUTE21")
    protected String C_ATTRIBUTE21;
    @TableField("C_ATTRIBUTE22")
    protected String C_ATTRIBUTE22;
    @TableField("C_ATTRIBUTE23")
    protected String C_ATTRIBUTE23;
    @TableField("C_ATTRIBUTE24")
    protected String C_ATTRIBUTE24;
    @TableField("C_ATTRIBUTE25")
    protected String C_ATTRIBUTE25;
    @TableField("C_ATTRIBUTE26")
    protected String C_ATTRIBUTE26;
    @TableField("C_ATTRIBUTE27")
    protected String C_ATTRIBUTE27;
    @TableField("C_ATTRIBUTE28")
    protected String C_ATTRIBUTE28;
    @TableField("C_ATTRIBUTE29")
    protected String C_ATTRIBUTE29;
    @TableField("C_ATTRIBUTE30")
    protected String C_ATTRIBUTE30;
    @TableField("C_ATTRIBUTE31")
    protected String C_ATTRIBUTE31;
    @TableField("C_ATTRIBUTE32")
    protected String C_ATTRIBUTE32;
    @TableField("C_ATTRIBUTE33")
    protected String C_ATTRIBUTE33;
    @TableField("C_ATTRIBUTE34")
    protected String C_ATTRIBUTE34;
    @TableField("C_ATTRIBUTE35")
    protected String C_ATTRIBUTE35;
    @TableField("C_ATTRIBUTE36")
    protected String C_ATTRIBUTE36;
    @TableField("C_ATTRIBUTE37")
    protected String C_ATTRIBUTE37;
    @TableField("C_ATTRIBUTE38")
    protected String C_ATTRIBUTE38;
    @TableField("C_ATTRIBUTE39")
    protected String C_ATTRIBUTE39;
    @TableField("C_ATTRIBUTE40")
    protected String C_ATTRIBUTE40;

    @TableField("C_ATTRIBUTE41")
    protected String C_ATTRIBUTE41;
    @TableField("C_ATTRIBUTE42")
    protected String C_ATTRIBUTE42;
    @TableField("C_ATTRIBUTE43")
    protected String C_ATTRIBUTE43;
    @TableField("C_ATTRIBUTE44")
    protected String C_ATTRIBUTE44;
    @TableField("C_ATTRIBUTE45")
    protected String C_ATTRIBUTE45;
    @TableField("C_ATTRIBUTE46")
    protected String C_ATTRIBUTE46;
    @TableField("C_ATTRIBUTE47")
    protected String C_ATTRIBUTE47;
    @TableField("C_ATTRIBUTE48")
    protected String C_ATTRIBUTE48;
    @TableField("C_ATTRIBUTE49")
    protected String C_ATTRIBUTE49;
    @TableField("C_ATTRIBUTE50")
    protected String C_ATTRIBUTE50;

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
}
