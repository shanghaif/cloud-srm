package com.midea.cloud.srm.model.suppliercooperate.order.entry;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
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
 *  修改日期: 2020/11/2 11:18
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_po_line_settlement_erp")
public class PoLineSettlementErp extends BaseErrorCell{

    @TableId("PO_LINE_SETTLEMENT_ERP_ID")
    private Long poLineSettlementErpId;

    @TableField("PO_HEAD_SETTLEMENT_ERP_ID")
    private Long poHeadSettlemenErpId;

    @TableField("PO_LINE_ID")
    protected String poLineId;

    @TableField("PO_HEADER_ID")
    protected String poHeaderId;

    @TableField("LINE_NUM")
    protected String lineNum;

    @TableField("UNIT_PRICE")
    protected BigDecimal unitPrice;

    @TableField("TAX_PRICE")
    protected BigDecimal taxPrice;

    @TableField("USER_HOLD_FLAG")
    protected String userHoldFlag;

    @TableField("UN_NUMBER_ID")
    protected String unNumberId;

    @TableField("UNORDERED_FLAG")
    protected String unorderedFlag;

    @TableField("UNIT_MEAS_LOOKUP_CODE")
    protected String unitMeasLookupCode;

    @TableField("TRANSACTION_REASON_CODE")
    protected String transactionReasonCode;

    @TableField("TAX_NAME")
    protected String taxName;

    @TableField("TAXABLE_FLAG")
    protected String taxableFlag;

    @TableField("QUANTITY_COMMITTED")
    protected String quantityCommitted;

    @TableField("QUANTITY")
    protected BigDecimal quantity;

    @TableField("QTY_RCV_TOLERANCE")
    protected String qtyRcvTolerance;

    @TableField("OVER_TOLERANCE_ERROR_FLAG")
    protected String overToleranceErrorFlag;

    @TableField("NOT_TO_EXCEED_PRICE")
    protected String notToExceedPrice;

    @TableField("NOTE_TO_VENDOR")
    protected String noteToVendor;

    @TableField("NEGOTIATED_BY_PREPARER_FLAG")
    protected String negotiatedByPreparerFlag;

    @TableField("MIN_ORDER_QUANTITY")
    protected String minOrderQuantity;

    @TableField("MAX_ORDER_QUANTITY")
    protected String maxOrderQuantity;

    @TableField("MARKET_PRICE")
    protected String marketPrice;

    @TableField("LIST_PRICE_PER_UNIT")
    protected String listPricePerUnit;

    @TableField("ITEM_REVISION")
    protected String itemRevision;

    @TableField("ITEM_ID")
    protected String itemId;

    @TableField("ITEM_CODE")
    protected String itemCode;

    @TableField("ITEM_TYPE")
    protected String itemType;

    @TableField("ITEM_DESCRIPTION")
    protected String itemDescription;

    @TableField("ITEM_CATEGORY")
    protected String itemCategory;

    @TableField("AGENCY_CODE")
    protected String agencyCode;

    @TableField("AGENCY_NAME")
    protected String agencyName;

    @TableField("LOTS_NUM")
    protected String lotsNum;

    @TableField("LOTS_VALIDITY_DATE")
    protected String lotsValidityDate;

    @TableField("HAZARD_CLASS_ID")
    protected String hazardClassId;

    @TableField("FROM_LINE_ID")
    protected String fromLineId;

    @TableField("FROM_HEADER_ID")
    protected String fromHeaderId;

    @TableField("FIRM_STATUS_LOOKUP_CODE")
    protected String FIRM_STATUS_LOOKUP_CODE;

    @TableField("FIRM_DATE")
    protected String FIRM_DATE;

    @TableField("COMMITTED_AMOUNT")
    protected String COMMITTED_AMOUNT;

    @TableField("CLOSED_REASON")
    protected String CLOSED_REASON;

    @TableField("CLOSED_FLAG")
    protected String CLOSED_FLAG;

    @TableField("CLOSED_DATE")
    protected String CLOSED_DATE;

    @TableField("CATEGORY_ID")
    protected String CATEGORY_ID;

    @TableField("CAPITAL_EXPENSE_FLAG")
    protected String CAPITAL_EXPENSE_FLAG;

    @TableField("CANCEL_REASON")
    protected String CANCEL_REASON;

    @TableField("CANCEL_FLAG")
    protected String CANCEL_FLAG;

    @TableField("CANCEL_DATE")
    protected String CANCEL_DATE;

    @TableField("CANCELLED_BY")
    protected String CANCELLED_BY;

    @TableField("ATTRIBUTE11")
    protected String ATTRIBUTE11;

    @TableField("ATTRIBUTE10")
    protected String ATTRIBUTE10;

    @TableField("ALLOW_PRICE_OVERRIDE_FLAG")
    protected String ALLOW_PRICE_OVERRIDE_FLAG;

    @TableField("ERP_PO_LINE_TYPE")
    protected String ERP_PO_LINE_TYPE;

    @TableField("ERP_PO_LINE_TYPE_DESC")
    protected String ERP_PO_LINE_TYPE_DESC;

    @TableField("APPROVED_DATE")
    protected String APPROVED_DATE;

    @TableField("ERP_CREATION_DATE")
    protected String ERP_CREATION_DATE;

    @TableField("ERP_LAST_UPDATE_DATE")
    protected String ERP_LAST_UPDATE_DATE;

    @TableField("LINE_TYPE_CODE")
    protected String LINE_TYPE_CODE;

    @TableField("INV_ORGANIZATION_ID")
    protected String invOrganizationId;

    @TableField("INV_ORGANIZATION_CODE")
    protected String invOrganizationCode;

    @TableField("IS_RETURNED_FLAG")
    protected String IS_RETURNED_FLAG;

    @TableField("IS_FREE_FLAG")
    protected String IS_FREE_FLAG;

    @TableField("IS_IMMED_SHIPPED_FLAG")
    protected String IS_IMMED_SHIPPED_FLAG;

    @TableField("SHIP_TO_LOCATION_ADDRESS")
    protected String shipToLocationAddress;

    @TableField("CONTACTS_INFO")
    protected String CONTACTS_INFO;

    @TableField("ACTUAL_RECEIVER_CODE")
    protected String ACTUAL_RECEIVER_CODE;

    @TableField("ACTUAL_RECEIVER_NAME")
    protected String ACTUAL_RECEIVER_NAME;

    @TableField("CURRENCY_CODE")
    protected String CURRENCY_CODE;

    @TableField("RATE_TYPE")
    protected String RATE_TYPE;

    @TableField("RATE_DATE")
    protected String RATE_DATE;

    @TableField("RATE")
    protected String RATE;

    @TableField("NEED_BY_DATE")
    protected String NEED_BY_DATE;

    @TableField("PROMISE_DATE")
    protected Date promiseDate;

    @TableField("CUSTOMER_REQUIREMENT")
    protected String customerRequirement;

    @TableField("CONTRACT_NUMBER")
    protected String contractNumber;

    @TableField("SOURCE_PO_NUM")
    protected String sourcePoNum;

    @TableField("SOURCE_PO_LINE_NUM")
    protected String sourcePoLineNum;

    @TableField("PRIMARY_UNIT")
    protected String primaryUnit;

    @TableField("PRIMARY_QUANTITY")
    protected BigDecimal primaryQuantity;

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

    @TableField("PO_LINE_LOCATION")
    protected String PO_LINE_LOCATION;
    /**
     * 结算单号（报文中头表的po_number）
     */
    @TableField("SETTLEMENT_PO_NUMBER")
    protected String settlementPoNumber;

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
     * 所有含税金额
     */
    @TableField(exist = false)
    private BigDecimal totalTaxPrice;
    /**
     * 所有未含税金额
     */
    @TableField(exist = false)
    private BigDecimal totalUnitPrice;

}
