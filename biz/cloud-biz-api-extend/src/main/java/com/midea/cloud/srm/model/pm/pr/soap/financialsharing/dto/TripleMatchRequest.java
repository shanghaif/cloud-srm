
package com.midea.cloud.srm.model.pm.pr.soap.financialsharing.dto;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BOE_INFO">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="BOE_HEADER" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="employeeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="boeEmployeeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="approvalDeptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="financeDeptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="boeDeptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="boeLeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="boeTypeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="vendorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="vendorSiteID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="projectNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="contractNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="boeDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="bpCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                             &lt;element name="srmNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="orgCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="invoiceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="exchangeRateType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="exchangeRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="payCurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="invoiceTotal" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="payConditionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="paymentCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="boeAbstract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="sourceSystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="advancePayDocNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="poAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="taxInvoice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="accountsPayableDueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="taskNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="projectName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="taskName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="BOE_EXPENSES" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="BOE_EXPENSE" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="invoiceLine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="lineType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="rateCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="taxRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="poNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="poLineNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="poShipmentNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="poDistributionNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reciptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reciptLineNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="quantityInvoiced" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="unitOfMeas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="unitPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="buyerNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="inventoryItemID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="materialDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="productType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="consignOrder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="consignLine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="contract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="poUnitPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="poPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="poUnitPriceChangeFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="buyerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="taskNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="taskName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="projectNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="projectName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="BOE_ATTACHMENTS" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="BOE_ATTACHMENT" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="attachFileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attachFilePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attachFileType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attachUploader" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attachUploadTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="CAV_EXPENSES" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="CAV_EXPENSE" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="prepayNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="cavAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="FAM_CONTRACTS" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="FAM_CONTRACT" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="contractNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="contractName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="contractAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="contractCurrency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="cumulativeInvoicedAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="accumulatedInvoicedPercentage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="amountOfOnTheWayInvoice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="uninvoicedAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="amountOfThisInvoice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="proportionOfThisInvoice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="cumulativeAmountPaid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="accumulatedPaymentRatio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="amountOfPaymentInTransit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="unpaidAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="amountOfPayment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="proportionOfPayment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "boeinfo"
})
@XmlRootElement(name = "tripleMatchRequest")
public class TripleMatchRequest {

    @XmlElement(name = "BOE_INFO", required = true)
    protected TripleMatchRequest.BOEINFO boeinfo;

    /**
     * 获取boeinfo属性的值。
     *
     * @return
     *     possible object is
     *     {@link TripleMatchRequest.BOEINFO }
     *
     */
    public TripleMatchRequest.BOEINFO getBOEINFO() {
        return boeinfo;
    }

    /**
     * 设置boeinfo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link TripleMatchRequest.BOEINFO }
     *
     */
    public void setBOEINFO(TripleMatchRequest.BOEINFO value) {
        this.boeinfo = value;
    }


    /**
     * <p>anonymous complex type的 Java 类。
     *
     * <p>以下模式片段指定包含在此类中的预期内容。
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="BOE_HEADER" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="employeeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="boeEmployeeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="approvalDeptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="financeDeptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="boeDeptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="boeLeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="boeTypeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="vendorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="vendorSiteID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="projectNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="contractNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="boeDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="bpCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                   &lt;element name="srmNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="orgCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="invoiceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="exchangeRateType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="exchangeRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="payCurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="invoiceTotal" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="payConditionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="paymentCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="boeAbstract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="sourceSystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="advancePayDocNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="poAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="taxInvoice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="accountsPayableDueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="taskNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="projectName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="taskName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="BOE_EXPENSES" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="BOE_EXPENSE" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="invoiceLine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="lineType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="rateCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="taxRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="poNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="poLineNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="poShipmentNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="poDistributionNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reciptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reciptLineNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="quantityInvoiced" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="unitOfMeas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="unitPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="buyerNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="inventoryItemID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="materialDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="productType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="consignOrder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="consignLine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="contract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="poUnitPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="poPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="poUnitPriceChangeFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="buyerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="taskNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="taskName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="projectNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="projectName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="BOE_ATTACHMENTS" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="BOE_ATTACHMENT" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="attachFileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="attachFilePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="attachFileType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="attachUploader" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="attachUploadTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="CAV_EXPENSES" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="CAV_EXPENSE" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="prepayNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="cavAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="FAM_CONTRACTS" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="FAM_CONTRACT" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="contractNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="contractName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="contractAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="contractCurrency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="cumulativeInvoicedAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="accumulatedInvoicedPercentage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="amountOfOnTheWayInvoice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="uninvoicedAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="amountOfThisInvoice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="proportionOfThisInvoice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="cumulativeAmountPaid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="accumulatedPaymentRatio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="amountOfPaymentInTransit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="unpaidAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="amountOfPayment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="proportionOfPayment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "boeheader",
        "boeexpenses",
        "boeattachments",
        "cavexpenses",
        "famcontracts"
    })
    public static class BOEINFO {

        @XmlElement(name = "BOE_HEADER")
        protected TripleMatchRequest.BOEINFO.BOEHEADER boeheader;
        @XmlElement(name = "BOE_EXPENSES")
        protected TripleMatchRequest.BOEINFO.BOEEXPENSES boeexpenses;
        @XmlElement(name = "BOE_ATTACHMENTS")
        protected TripleMatchRequest.BOEINFO.BOEATTACHMENTS boeattachments;
        @XmlElement(name = "CAV_EXPENSES")
        protected TripleMatchRequest.BOEINFO.CAVEXPENSES cavexpenses;
        @XmlElement(name = "FAM_CONTRACTS")
        protected TripleMatchRequest.BOEINFO.FAMCONTRACTS famcontracts;

        /**
         * 获取boeheader属性的值。
         *
         * @return
         *     possible object is
         *     {@link TripleMatchRequest.BOEINFO.BOEHEADER }
         *
         */
        public TripleMatchRequest.BOEINFO.BOEHEADER getBOEHEADER() {
            return boeheader;
        }

        /**
         * 设置boeheader属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link TripleMatchRequest.BOEINFO.BOEHEADER }
         *
         */
        public void setBOEHEADER(TripleMatchRequest.BOEINFO.BOEHEADER value) {
            this.boeheader = value;
        }

        /**
         * 获取boeexpenses属性的值。
         *
         * @return
         *     possible object is
         *     {@link TripleMatchRequest.BOEINFO.BOEEXPENSES }
         *
         */
        public TripleMatchRequest.BOEINFO.BOEEXPENSES getBOEEXPENSES() {
            return boeexpenses;
        }

        /**
         * 设置boeexpenses属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link TripleMatchRequest.BOEINFO.BOEEXPENSES }
         *
         */
        public void setBOEEXPENSES(TripleMatchRequest.BOEINFO.BOEEXPENSES value) {
            this.boeexpenses = value;
        }

        /**
         * 获取boeattachments属性的值。
         *
         * @return
         *     possible object is
         *     {@link TripleMatchRequest.BOEINFO.BOEATTACHMENTS }
         *
         */
        public TripleMatchRequest.BOEINFO.BOEATTACHMENTS getBOEATTACHMENTS() {
            return boeattachments;
        }

        /**
         * 设置boeattachments属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link TripleMatchRequest.BOEINFO.BOEATTACHMENTS }
         *
         */
        public void setBOEATTACHMENTS(TripleMatchRequest.BOEINFO.BOEATTACHMENTS value) {
            this.boeattachments = value;
        }

        /**
         * 获取cavexpenses属性的值。
         *
         * @return
         *     possible object is
         *     {@link TripleMatchRequest.BOEINFO.CAVEXPENSES }
         *
         */
        public TripleMatchRequest.BOEINFO.CAVEXPENSES getCAVEXPENSES() {
            return cavexpenses;
        }

        /**
         * 设置cavexpenses属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link TripleMatchRequest.BOEINFO.CAVEXPENSES }
         *
         */
        public void setCAVEXPENSES(TripleMatchRequest.BOEINFO.CAVEXPENSES value) {
            this.cavexpenses = value;
        }

        /**
         * 获取famcontracts属性的值。
         *
         * @return
         *     possible object is
         *     {@link TripleMatchRequest.BOEINFO.FAMCONTRACTS }
         *
         */
        public TripleMatchRequest.BOEINFO.FAMCONTRACTS getFAMCONTRACTS() {
            return famcontracts;
        }

        /**
         * 设置famcontracts属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link TripleMatchRequest.BOEINFO.FAMCONTRACTS }
         *
         */
        public void setFAMCONTRACTS(TripleMatchRequest.BOEINFO.FAMCONTRACTS value) {
            this.famcontracts = value;
        }


        /**
         * <p>anonymous complex type的 Java 类。
         *
         * <p>以下模式片段指定包含在此类中的预期内容。
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="BOE_ATTACHMENT" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="attachFileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="attachFilePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="attachFileType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="attachUploader" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="attachUploadTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "boeattachment"
        })
        public static class BOEATTACHMENTS {

            @XmlElement(name = "BOE_ATTACHMENT")
            protected List<BOEATTACHMENT> boeattachment;

            /**
             * Gets the value of the boeattachment property.
             *
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the boeattachment property.
             *
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getBOEATTACHMENT().add(newItem);
             * </pre>
             *
             *
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link TripleMatchRequest.BOEINFO.BOEATTACHMENTS.BOEATTACHMENT }
             *
             *
             */
            public List<BOEATTACHMENT> getBOEATTACHMENT() {
                if (boeattachment == null) {
                    boeattachment = new ArrayList<BOEATTACHMENT>();
                }
                return this.boeattachment;
            }


            /**
             * <p>anonymous complex type的 Java 类。
             *
             * <p>以下模式片段指定包含在此类中的预期内容。
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="attachFileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="attachFilePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="attachFileType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="attachUploader" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="attachUploadTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "attachFileName",
                "attachFilePath",
                "attachFileType",
                "attachUploader",
                "attachUploadTime",
                "reservedField1",
                "reservedField2",
                "reservedField3",
                "reservedField4",
                "reservedField5"
            })
            public static class BOEATTACHMENT {

                protected String attachFileName;
                protected String attachFilePath;
                protected String attachFileType;
                protected String attachUploader;
                protected String attachUploadTime;
                protected String reservedField1;
                protected String reservedField2;
                protected String reservedField3;
                protected String reservedField4;
                protected String reservedField5;

                /**
                 * 获取attachFileName属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getAttachFileName() {
                    return attachFileName;
                }

                /**
                 * 设置attachFileName属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setAttachFileName(String value) {
                    this.attachFileName = value;
                }

                /**
                 * 获取attachFilePath属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getAttachFilePath() {
                    return attachFilePath;
                }

                /**
                 * 设置attachFilePath属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setAttachFilePath(String value) {
                    this.attachFilePath = value;
                }

                /**
                 * 获取attachFileType属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getAttachFileType() {
                    return attachFileType;
                }

                /**
                 * 设置attachFileType属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setAttachFileType(String value) {
                    this.attachFileType = value;
                }

                /**
                 * 获取attachUploader属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getAttachUploader() {
                    return attachUploader;
                }

                /**
                 * 设置attachUploader属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setAttachUploader(String value) {
                    this.attachUploader = value;
                }

                /**
                 * 获取attachUploadTime属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getAttachUploadTime() {
                    return attachUploadTime;
                }

                /**
                 * 设置attachUploadTime属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setAttachUploadTime(String value) {
                    this.attachUploadTime = value;
                }

                /**
                 * 获取reservedField1属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getReservedField1() {
                    return reservedField1;
                }

                /**
                 * 设置reservedField1属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setReservedField1(String value) {
                    this.reservedField1 = value;
                }

                /**
                 * 获取reservedField2属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getReservedField2() {
                    return reservedField2;
                }

                /**
                 * 设置reservedField2属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setReservedField2(String value) {
                    this.reservedField2 = value;
                }

                /**
                 * 获取reservedField3属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getReservedField3() {
                    return reservedField3;
                }

                /**
                 * 设置reservedField3属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setReservedField3(String value) {
                    this.reservedField3 = value;
                }

                /**
                 * 获取reservedField4属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getReservedField4() {
                    return reservedField4;
                }

                /**
                 * 设置reservedField4属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setReservedField4(String value) {
                    this.reservedField4 = value;
                }

                /**
                 * 获取reservedField5属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getReservedField5() {
                    return reservedField5;
                }

                /**
                 * 设置reservedField5属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setReservedField5(String value) {
                    this.reservedField5 = value;
                }

            }

        }


        /**
         * <p>anonymous complex type的 Java 类。
         *
         * <p>以下模式片段指定包含在此类中的预期内容。
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="BOE_EXPENSE" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="invoiceLine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="lineType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="rateCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="taxRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="poNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="poLineNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="poShipmentNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="poDistributionNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reciptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reciptLineNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="quantityInvoiced" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="unitOfMeas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="unitPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="buyerNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="inventoryItemID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="materialDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="productType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="consignOrder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="consignLine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="contract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="poUnitPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="poPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="poUnitPriceChangeFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="buyerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="taskNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="taskName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="projectNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="projectName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "boeexpense"
        })
        public static class BOEEXPENSES {

            @XmlElement(name = "BOE_EXPENSE")
            protected List<BOEEXPENSE> boeexpense;

            /**
             * Gets the value of the boeexpense property.
             *
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the boeexpense property.
             *
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getBOEEXPENSE().add(newItem);
             * </pre>
             *
             *
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link TripleMatchRequest.BOEINFO.BOEEXPENSES.BOEEXPENSE }
             *
             *
             */
            public List<BOEEXPENSE> getBOEEXPENSE() {
                if (boeexpense == null) {
                    boeexpense = new ArrayList<BOEEXPENSE>();
                }
                return this.boeexpense;
            }


            /**
             * <p>anonymous complex type的 Java 类。
             *
             * <p>以下模式片段指定包含在此类中的预期内容。
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="invoiceLine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="lineType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="rateCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="taxRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="poNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="poLineNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="poShipmentNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="poDistributionNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reciptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reciptLineNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="quantityInvoiced" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="unitOfMeas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="unitPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="buyerNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="inventoryItemID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="materialDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="productType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="consignOrder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="consignLine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="contract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="poUnitPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="poPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="poUnitPriceChangeFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="buyerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="taskNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="taskName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="projectNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="projectName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "invoiceLine",
                "lineType",
                "amount",
                "rateCode",
                "taxRate",
                "poNum",
                "poLineNum",
                "poShipmentNum",
                "poDistributionNum",
                "reciptNum",
                "reciptLineNum",
                "quantityInvoiced",
                "unitOfMeas",
                "unitPrice",
                "description",
                "buyerNum",
                "inventoryItemID",
                "materialDescription",
                "productType",
                "reservedField1",
                "reservedField2",
                "reservedField3",
                "reservedField4",
                "reservedField5",
                "consignOrder",
                "consignLine",
                "contract",
                "poUnitPrice",
                "poPrice",
                "poUnitPriceChangeFlag",
                "buyerName",
                "taskNumber",
                "taskName",
                "projectNumber",
                "projectName"
            })
            public static class BOEEXPENSE {

                protected String invoiceLine;
                protected String lineType;
                protected BigDecimal amount;
                protected String rateCode;
                protected BigDecimal taxRate;
                protected String poNum;
                protected String poLineNum;
                protected String poShipmentNum;
                protected String poDistributionNum;
                protected String reciptNum;
                protected String reciptLineNum;
                protected String quantityInvoiced;
                protected String unitOfMeas;
                protected BigDecimal unitPrice;
                protected String description;
                protected String buyerNum;
                protected String inventoryItemID;
                protected String materialDescription;
                protected String productType;
                protected String reservedField1;
                protected String reservedField2;
                protected String reservedField3;
                protected String reservedField4;
                protected String reservedField5;
                protected String consignOrder;
                protected String consignLine;
                protected String contract;
                protected BigDecimal poUnitPrice;
                protected BigDecimal poPrice;
                protected String poUnitPriceChangeFlag;
                protected String buyerName;
                protected String taskNumber;
                protected String taskName;
                protected String projectNumber;
                protected String projectName;

                /**
                 * 获取invoiceLine属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getInvoiceLine() {
                    return invoiceLine;
                }

                /**
                 * 设置invoiceLine属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setInvoiceLine(String value) {
                    this.invoiceLine = value;
                }

                /**
                 * 获取lineType属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getLineType() {
                    return lineType;
                }

                /**
                 * 设置lineType属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setLineType(String value) {
                    this.lineType = value;
                }

                /**
                 * 获取amount属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getAmount() {
                    return amount;
                }

                /**
                 * 设置amount属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setAmount(BigDecimal value) {
                    this.amount = value;
                }

                /**
                 * 获取rateCode属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getRateCode() {
                    return rateCode;
                }

                /**
                 * 设置rateCode属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setRateCode(String value) {
                    this.rateCode = value;
                }

                /**
                 * 获取taxRate属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getTaxRate() {
                    return taxRate;
                }

                /**
                 * 设置taxRate属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setTaxRate(BigDecimal value) {
                    this.taxRate = value;
                }

                /**
                 * 获取poNum属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getPoNum() {
                    return poNum;
                }

                /**
                 * 设置poNum属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setPoNum(String value) {
                    this.poNum = value;
                }

                /**
                 * 获取poLineNum属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getPoLineNum() {
                    return poLineNum;
                }

                /**
                 * 设置poLineNum属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setPoLineNum(String value) {
                    this.poLineNum = value;
                }

                /**
                 * 获取poShipmentNum属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getPoShipmentNum() {
                    return poShipmentNum;
                }

                /**
                 * 设置poShipmentNum属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setPoShipmentNum(String value) {
                    this.poShipmentNum = value;
                }

                /**
                 * 获取poDistributionNum属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getPoDistributionNum() {
                    return poDistributionNum;
                }

                /**
                 * 设置poDistributionNum属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setPoDistributionNum(String value) {
                    this.poDistributionNum = value;
                }

                /**
                 * 获取reciptNum属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getReciptNum() {
                    return reciptNum;
                }

                /**
                 * 设置reciptNum属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setReciptNum(String value) {
                    this.reciptNum = value;
                }

                /**
                 * 获取reciptLineNum属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getReciptLineNum() {
                    return reciptLineNum;
                }

                /**
                 * 设置reciptLineNum属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setReciptLineNum(String value) {
                    this.reciptLineNum = value;
                }

                /**
                 * 获取quantityInvoiced属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getQuantityInvoiced() {
                    return quantityInvoiced;
                }

                /**
                 * 设置quantityInvoiced属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setQuantityInvoiced(String value) {
                    this.quantityInvoiced = value;
                }

                /**
                 * 获取unitOfMeas属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getUnitOfMeas() {
                    return unitOfMeas;
                }

                /**
                 * 设置unitOfMeas属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setUnitOfMeas(String value) {
                    this.unitOfMeas = value;
                }

                /**
                 * 获取unitPrice属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getUnitPrice() {
                    return unitPrice;
                }

                /**
                 * 设置unitPrice属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setUnitPrice(BigDecimal value) {
                    this.unitPrice = value;
                }

                /**
                 * 获取description属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getDescription() {
                    return description;
                }

                /**
                 * 设置description属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setDescription(String value) {
                    this.description = value;
                }

                /**
                 * 获取buyerNum属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getBuyerNum() {
                    return buyerNum;
                }

                /**
                 * 设置buyerNum属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setBuyerNum(String value) {
                    this.buyerNum = value;
                }

                /**
                 * 获取inventoryItemID属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getInventoryItemID() {
                    return inventoryItemID;
                }

                /**
                 * 设置inventoryItemID属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setInventoryItemID(String value) {
                    this.inventoryItemID = value;
                }

                /**
                 * 获取materialDescription属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getMaterialDescription() {
                    return materialDescription;
                }

                /**
                 * 设置materialDescription属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setMaterialDescription(String value) {
                    this.materialDescription = value;
                }

                /**
                 * 获取productType属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getProductType() {
                    return productType;
                }

                /**
                 * 设置productType属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setProductType(String value) {
                    this.productType = value;
                }

                /**
                 * 获取reservedField1属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getReservedField1() {
                    return reservedField1;
                }

                /**
                 * 设置reservedField1属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setReservedField1(String value) {
                    this.reservedField1 = value;
                }

                /**
                 * 获取reservedField2属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getReservedField2() {
                    return reservedField2;
                }

                /**
                 * 设置reservedField2属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setReservedField2(String value) {
                    this.reservedField2 = value;
                }

                /**
                 * 获取reservedField3属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getReservedField3() {
                    return reservedField3;
                }

                /**
                 * 设置reservedField3属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setReservedField3(String value) {
                    this.reservedField3 = value;
                }

                /**
                 * 获取reservedField4属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getReservedField4() {
                    return reservedField4;
                }

                /**
                 * 设置reservedField4属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setReservedField4(String value) {
                    this.reservedField4 = value;
                }

                /**
                 * 获取reservedField5属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getReservedField5() {
                    return reservedField5;
                }

                /**
                 * 设置reservedField5属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setReservedField5(String value) {
                    this.reservedField5 = value;
                }

                /**
                 * 获取consignOrder属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getConsignOrder() {
                    return consignOrder;
                }

                /**
                 * 设置consignOrder属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setConsignOrder(String value) {
                    this.consignOrder = value;
                }

                /**
                 * 获取consignLine属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getConsignLine() {
                    return consignLine;
                }

                /**
                 * 设置consignLine属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setConsignLine(String value) {
                    this.consignLine = value;
                }

                /**
                 * 获取contract属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getContract() {
                    return contract;
                }

                /**
                 * 设置contract属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setContract(String value) {
                    this.contract = value;
                }

                /**
                 * 获取poUnitPrice属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getPoUnitPrice() {
                    return poUnitPrice;
                }

                /**
                 * 设置poUnitPrice属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setPoUnitPrice(BigDecimal value) {
                    this.poUnitPrice = value;
                }

                /**
                 * 获取poPrice属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getPoPrice() {
                    return poPrice;
                }

                /**
                 * 设置poPrice属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setPoPrice(BigDecimal value) {
                    this.poPrice = value;
                }

                /**
                 * 获取poUnitPriceChangeFlag属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getPoUnitPriceChangeFlag() {
                    return poUnitPriceChangeFlag;
                }

                /**
                 * 设置poUnitPriceChangeFlag属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setPoUnitPriceChangeFlag(String value) {
                    this.poUnitPriceChangeFlag = value;
                }

                /**
                 * 获取buyerName属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getBuyerName() {
                    return buyerName;
                }

                /**
                 * 设置buyerName属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setBuyerName(String value) {
                    this.buyerName = value;
                }

                /**
                 * 获取taskNumber属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getTaskNumber() {
                    return taskNumber;
                }

                /**
                 * 设置taskNumber属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setTaskNumber(String value) {
                    this.taskNumber = value;
                }

                /**
                 * 获取taskName属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getTaskName() {
                    return taskName;
                }

                /**
                 * 设置taskName属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setTaskName(String value) {
                    this.taskName = value;
                }

                /**
                 * 获取projectNumber属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getProjectNumber() {
                    return projectNumber;
                }

                /**
                 * 设置projectNumber属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setProjectNumber(String value) {
                    this.projectNumber = value;
                }

                /**
                 * 获取projectName属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getProjectName() {
                    return projectName;
                }

                /**
                 * 设置projectName属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setProjectName(String value) {
                    this.projectName = value;
                }

            }

        }


        /**
         * <p>anonymous complex type的 Java 类。
         *
         * <p>以下模式片段指定包含在此类中的预期内容。
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="employeeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="boeEmployeeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="approvalDeptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="financeDeptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="boeDeptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="boeLeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="boeTypeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="vendorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="vendorSiteID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="projectNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="contractNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="boeDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="bpCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *         &lt;element name="srmNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="orgCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="invoiceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="exchangeRateType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="exchangeRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="payCurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="invoiceTotal" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="payConditionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="paymentCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="boeAbstract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="sourceSystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="advancePayDocNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="poAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="taxInvoice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="accountsPayableDueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="taskNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="projectName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="taskName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "employeeNum",
            "boeEmployeeNum",
            "approvalDeptNum",
            "financeDeptNum",
            "boeDeptNum",
            "boeLeNum",
            "boeTypeNum",
            "vendorCode",
            "vendorSiteID",
            "projectNum",
            "contractNum",
            "boeDate",
            "bpCount",
            "srmNum",
            "orgCode",
            "invoiceType",
            "currencyCode",
            "exchangeRateType",
            "exchangeRate",
            "payCurrencyCode",
            "invoiceTotal",
            "payConditionCode",
            "paymentCode",
            "boeAbstract",
            "sourceSystem",
            "advancePayDocNum",
            "reservedField1",
            "reservedField2",
            "reservedField3",
            "reservedField4",
            "reservedField5",
            "poAmount",
            "taxInvoice",
            "accountsPayableDueDate",
            "taskNumber",
            "projectName",
            "taskName"
        })
        public static class BOEHEADER {

            protected String employeeNum;
            protected String boeEmployeeNum;
            protected String approvalDeptNum;
            protected String financeDeptNum;
            protected String boeDeptNum;
            protected String boeLeNum;
            protected String boeTypeNum;
            protected String vendorCode;
            protected String vendorSiteID;
            protected String projectNum;
            protected String contractNum;
            protected String boeDate;
            protected Integer bpCount;
            protected String srmNum;
            protected String orgCode;
            protected String invoiceType;
            protected String currencyCode;
            protected String exchangeRateType;
            protected BigDecimal exchangeRate;
            protected String payCurrencyCode;
            protected BigDecimal invoiceTotal;
            protected String payConditionCode;
            protected String paymentCode;
            protected String boeAbstract;
            protected String sourceSystem;
            protected String advancePayDocNum;
            protected String reservedField1;
            protected String reservedField2;
            protected String reservedField3;
            protected String reservedField4;
            protected String reservedField5;
            protected BigDecimal poAmount;
            protected String taxInvoice;
            protected String accountsPayableDueDate;
            protected String taskNumber;
            protected String projectName;
            protected String taskName;

            /**
             * 获取employeeNum属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getEmployeeNum() {
                return employeeNum;
            }

            /**
             * 设置employeeNum属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setEmployeeNum(String value) {
                this.employeeNum = value;
            }

            /**
             * 获取boeEmployeeNum属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getBoeEmployeeNum() {
                return boeEmployeeNum;
            }

            /**
             * 设置boeEmployeeNum属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setBoeEmployeeNum(String value) {
                this.boeEmployeeNum = value;
            }

            /**
             * 获取approvalDeptNum属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getApprovalDeptNum() {
                return approvalDeptNum;
            }

            /**
             * 设置approvalDeptNum属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setApprovalDeptNum(String value) {
                this.approvalDeptNum = value;
            }

            /**
             * 获取financeDeptNum属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getFinanceDeptNum() {
                return financeDeptNum;
            }

            /**
             * 设置financeDeptNum属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setFinanceDeptNum(String value) {
                this.financeDeptNum = value;
            }

            /**
             * 获取boeDeptNum属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getBoeDeptNum() {
                return boeDeptNum;
            }

            /**
             * 设置boeDeptNum属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setBoeDeptNum(String value) {
                this.boeDeptNum = value;
            }

            /**
             * 获取boeLeNum属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getBoeLeNum() {
                return boeLeNum;
            }

            /**
             * 设置boeLeNum属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setBoeLeNum(String value) {
                this.boeLeNum = value;
            }

            /**
             * 获取boeTypeNum属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getBoeTypeNum() {
                return boeTypeNum;
            }

            /**
             * 设置boeTypeNum属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setBoeTypeNum(String value) {
                this.boeTypeNum = value;
            }

            /**
             * 获取vendorCode属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getVendorCode() {
                return vendorCode;
            }

            /**
             * 设置vendorCode属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setVendorCode(String value) {
                this.vendorCode = value;
            }

            /**
             * 获取vendorSiteID属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getVendorSiteID() {
                return vendorSiteID;
            }

            /**
             * 设置vendorSiteID属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setVendorSiteID(String value) {
                this.vendorSiteID = value;
            }

            /**
             * 获取projectNum属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getProjectNum() {
                return projectNum;
            }

            /**
             * 设置projectNum属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setProjectNum(String value) {
                this.projectNum = value;
            }

            /**
             * 获取contractNum属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getContractNum() {
                return contractNum;
            }

            /**
             * 设置contractNum属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setContractNum(String value) {
                this.contractNum = value;
            }

            /**
             * 获取boeDate属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getBoeDate() {
                return boeDate;
            }

            /**
             * 设置boeDate属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setBoeDate(String value) {
                this.boeDate = value;
            }

            /**
             * 获取bpCount属性的值。
             *
             * @return
             *     possible object is
             *     {@link Integer }
             *
             */
            public Integer getBpCount() {
                return bpCount;
            }

            /**
             * 设置bpCount属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link Integer }
             *
             */
            public void setBpCount(Integer value) {
                this.bpCount = value;
            }

            /**
             * 获取srmNum属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getSrmNum() {
                return srmNum;
            }

            /**
             * 设置srmNum属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setSrmNum(String value) {
                this.srmNum = value;
            }

            /**
             * 获取orgCode属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getOrgCode() {
                return orgCode;
            }

            /**
             * 设置orgCode属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setOrgCode(String value) {
                this.orgCode = value;
            }

            /**
             * 获取invoiceType属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getInvoiceType() {
                return invoiceType;
            }

            /**
             * 设置invoiceType属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setInvoiceType(String value) {
                this.invoiceType = value;
            }

            /**
             * 获取currencyCode属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getCurrencyCode() {
                return currencyCode;
            }

            /**
             * 设置currencyCode属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setCurrencyCode(String value) {
                this.currencyCode = value;
            }

            /**
             * 获取exchangeRateType属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getExchangeRateType() {
                return exchangeRateType;
            }

            /**
             * 设置exchangeRateType属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setExchangeRateType(String value) {
                this.exchangeRateType = value;
            }

            /**
             * 获取exchangeRate属性的值。
             *
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *
             */
            public BigDecimal getExchangeRate() {
                return exchangeRate;
            }

            /**
             * 设置exchangeRate属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *
             */
            public void setExchangeRate(BigDecimal value) {
                this.exchangeRate = value;
            }

            /**
             * 获取payCurrencyCode属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getPayCurrencyCode() {
                return payCurrencyCode;
            }

            /**
             * 设置payCurrencyCode属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setPayCurrencyCode(String value) {
                this.payCurrencyCode = value;
            }

            /**
             * 获取invoiceTotal属性的值。
             *
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *
             */
            public BigDecimal getInvoiceTotal() {
                return invoiceTotal;
            }

            /**
             * 设置invoiceTotal属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *
             */
            public void setInvoiceTotal(BigDecimal value) {
                this.invoiceTotal = value;
            }

            /**
             * 获取payConditionCode属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getPayConditionCode() {
                return payConditionCode;
            }

            /**
             * 设置payConditionCode属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setPayConditionCode(String value) {
                this.payConditionCode = value;
            }

            /**
             * 获取paymentCode属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getPaymentCode() {
                return paymentCode;
            }

            /**
             * 设置paymentCode属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setPaymentCode(String value) {
                this.paymentCode = value;
            }

            /**
             * 获取boeAbstract属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getBoeAbstract() {
                return boeAbstract;
            }

            /**
             * 设置boeAbstract属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setBoeAbstract(String value) {
                this.boeAbstract = value;
            }

            /**
             * 获取sourceSystem属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getSourceSystem() {
                return sourceSystem;
            }

            /**
             * 设置sourceSystem属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setSourceSystem(String value) {
                this.sourceSystem = value;
            }

            /**
             * 获取advancePayDocNum属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getAdvancePayDocNum() {
                return advancePayDocNum;
            }

            /**
             * 设置advancePayDocNum属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setAdvancePayDocNum(String value) {
                this.advancePayDocNum = value;
            }

            /**
             * 获取reservedField1属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getReservedField1() {
                return reservedField1;
            }

            /**
             * 设置reservedField1属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setReservedField1(String value) {
                this.reservedField1 = value;
            }

            /**
             * 获取reservedField2属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getReservedField2() {
                return reservedField2;
            }

            /**
             * 设置reservedField2属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setReservedField2(String value) {
                this.reservedField2 = value;
            }

            /**
             * 获取reservedField3属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getReservedField3() {
                return reservedField3;
            }

            /**
             * 设置reservedField3属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setReservedField3(String value) {
                this.reservedField3 = value;
            }

            /**
             * 获取reservedField4属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getReservedField4() {
                return reservedField4;
            }

            /**
             * 设置reservedField4属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setReservedField4(String value) {
                this.reservedField4 = value;
            }

            /**
             * 获取reservedField5属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getReservedField5() {
                return reservedField5;
            }

            /**
             * 设置reservedField5属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setReservedField5(String value) {
                this.reservedField5 = value;
            }

            /**
             * 获取poAmount属性的值。
             *
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *
             */
            public BigDecimal getPoAmount() {
                return poAmount;
            }

            /**
             * 设置poAmount属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *
             */
            public void setPoAmount(BigDecimal value) {
                this.poAmount = value;
            }

            /**
             * 获取taxInvoice属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getTaxInvoice() {
                return taxInvoice;
            }

            /**
             * 设置taxInvoice属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setTaxInvoice(String value) {
                this.taxInvoice = value;
            }

            /**
             * 获取accountsPayableDueDate属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getAccountsPayableDueDate() {
                return accountsPayableDueDate;
            }

            /**
             * 设置accountsPayableDueDate属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setAccountsPayableDueDate(String value) {
                this.accountsPayableDueDate = value;
            }

            /**
             * 获取taskNumber属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getTaskNumber() {
                return taskNumber;
            }

            /**
             * 设置taskNumber属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setTaskNumber(String value) {
                this.taskNumber = value;
            }

            /**
             * 获取projectName属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getProjectName() {
                return projectName;
            }

            /**
             * 设置projectName属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setProjectName(String value) {
                this.projectName = value;
            }

            /**
             * 获取taskName属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getTaskName() {
                return taskName;
            }

            /**
             * 设置taskName属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setTaskName(String value) {
                this.taskName = value;
            }

        }


        /**
         * <p>anonymous complex type的 Java 类。
         *
         * <p>以下模式片段指定包含在此类中的预期内容。
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="CAV_EXPENSE" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="prepayNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="cavAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "cavexpense"
        })
        public static class CAVEXPENSES {

            @XmlElement(name = "CAV_EXPENSE")
            protected List<CAVEXPENSE> cavexpense;

            /**
             * Gets the value of the cavexpense property.
             *
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the cavexpense property.
             *
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getCAVEXPENSE().add(newItem);
             * </pre>
             *
             *
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link TripleMatchRequest.BOEINFO.CAVEXPENSES.CAVEXPENSE }
             *
             *
             */
            public List<CAVEXPENSE> getCAVEXPENSE() {
                if (cavexpense == null) {
                    cavexpense = new ArrayList<CAVEXPENSE>();
                }
                return this.cavexpense;
            }


            /**
             * <p>anonymous complex type的 Java 类。
             *
             * <p>以下模式片段指定包含在此类中的预期内容。
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="prepayNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="cavAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "prepayNum",
                "cavAmount",
                "reservedField1",
                "reservedField2",
                "reservedField3",
                "reservedField4",
                "reservedField5"
            })
            public static class CAVEXPENSE {

                protected String prepayNum;
                protected BigDecimal cavAmount;
                protected String reservedField1;
                protected String reservedField2;
                protected String reservedField3;
                protected String reservedField4;
                protected String reservedField5;

                /**
                 * 获取prepayNum属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getPrepayNum() {
                    return prepayNum;
                }

                /**
                 * 设置prepayNum属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setPrepayNum(String value) {
                    this.prepayNum = value;
                }

                /**
                 * 获取cavAmount属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getCavAmount() {
                    return cavAmount;
                }

                /**
                 * 设置cavAmount属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setCavAmount(BigDecimal value) {
                    this.cavAmount = value;
                }

                /**
                 * 获取reservedField1属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getReservedField1() {
                    return reservedField1;
                }

                /**
                 * 设置reservedField1属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setReservedField1(String value) {
                    this.reservedField1 = value;
                }

                /**
                 * 获取reservedField2属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getReservedField2() {
                    return reservedField2;
                }

                /**
                 * 设置reservedField2属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setReservedField2(String value) {
                    this.reservedField2 = value;
                }

                /**
                 * 获取reservedField3属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getReservedField3() {
                    return reservedField3;
                }

                /**
                 * 设置reservedField3属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setReservedField3(String value) {
                    this.reservedField3 = value;
                }

                /**
                 * 获取reservedField4属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getReservedField4() {
                    return reservedField4;
                }

                /**
                 * 设置reservedField4属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setReservedField4(String value) {
                    this.reservedField4 = value;
                }

                /**
                 * 获取reservedField5属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getReservedField5() {
                    return reservedField5;
                }

                /**
                 * 设置reservedField5属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setReservedField5(String value) {
                    this.reservedField5 = value;
                }

            }

        }


        /**
         * <p>anonymous complex type的 Java 类。
         *
         * <p>以下模式片段指定包含在此类中的预期内容。
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="FAM_CONTRACT" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="contractNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="contractName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="contractAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="contractCurrency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="cumulativeInvoicedAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="accumulatedInvoicedPercentage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="amountOfOnTheWayInvoice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="uninvoicedAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="amountOfThisInvoice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="proportionOfThisInvoice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="cumulativeAmountPaid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="accumulatedPaymentRatio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="amountOfPaymentInTransit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="unpaidAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="amountOfPayment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="proportionOfPayment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "famcontract"
        })
        public static class FAMCONTRACTS {

            @XmlElement(name = "FAM_CONTRACT")
            protected List<FAMCONTRACT> famcontract;

            /**
             * Gets the value of the famcontract property.
             *
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the famcontract property.
             *
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getFAMCONTRACT().add(newItem);
             * </pre>
             *
             *
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link TripleMatchRequest.BOEINFO.FAMCONTRACTS.FAMCONTRACT }
             *
             *
             */
            public List<FAMCONTRACT> getFAMCONTRACT() {
                if (famcontract == null) {
                    famcontract = new ArrayList<FAMCONTRACT>();
                }
                return this.famcontract;
            }


            /**
             * <p>anonymous complex type的 Java 类。
             * 
             * <p>以下模式片段指定包含在此类中的预期内容。
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="contractNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="contractName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="contractAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="contractCurrency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="cumulativeInvoicedAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="accumulatedInvoicedPercentage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="amountOfOnTheWayInvoice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="uninvoicedAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="amountOfThisInvoice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="proportionOfThisInvoice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="cumulativeAmountPaid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="accumulatedPaymentRatio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="amountOfPaymentInTransit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="unpaidAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="amountOfPayment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="proportionOfPayment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "contractNumber",
                "contractName",
                "contractAmount",
                "contractCurrency",
                "cumulativeInvoicedAmount",
                "accumulatedInvoicedPercentage",
                "amountOfOnTheWayInvoice",
                "uninvoicedAmount",
                "amountOfThisInvoice",
                "proportionOfThisInvoice",
                "cumulativeAmountPaid",
                "accumulatedPaymentRatio",
                "amountOfPaymentInTransit",
                "unpaidAmount",
                "amountOfPayment",
                "proportionOfPayment",
                "reservedField1",
                "reservedField2",
                "reservedField3",
                "reservedField4",
                "reservedField5"
            })
            public static class FAMCONTRACT {

                protected String contractNumber;
                protected String contractName;
                protected String contractAmount;
                protected String contractCurrency;
                protected String cumulativeInvoicedAmount;
                protected String accumulatedInvoicedPercentage;
                protected String amountOfOnTheWayInvoice;
                protected String uninvoicedAmount;
                protected String amountOfThisInvoice;
                protected String proportionOfThisInvoice;
                protected String cumulativeAmountPaid;
                protected String accumulatedPaymentRatio;
                protected String amountOfPaymentInTransit;
                protected String unpaidAmount;
                protected String amountOfPayment;
                protected String proportionOfPayment;
                protected String reservedField1;
                protected String reservedField2;
                protected String reservedField3;
                protected String reservedField4;
                protected String reservedField5;

                /**
                 * 获取contractNumber属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getContractNumber() {
                    return contractNumber;
                }

                /**
                 * 设置contractNumber属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setContractNumber(String value) {
                    this.contractNumber = value;
                }

                /**
                 * 获取contractName属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getContractName() {
                    return contractName;
                }

                /**
                 * 设置contractName属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setContractName(String value) {
                    this.contractName = value;
                }

                /**
                 * 获取contractAmount属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getContractAmount() {
                    return contractAmount;
                }

                /**
                 * 设置contractAmount属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setContractAmount(String value) {
                    this.contractAmount = value;
                }

                /**
                 * 获取contractCurrency属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getContractCurrency() {
                    return contractCurrency;
                }

                /**
                 * 设置contractCurrency属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setContractCurrency(String value) {
                    this.contractCurrency = value;
                }

                /**
                 * 获取cumulativeInvoicedAmount属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCumulativeInvoicedAmount() {
                    return cumulativeInvoicedAmount;
                }

                /**
                 * 设置cumulativeInvoicedAmount属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCumulativeInvoicedAmount(String value) {
                    this.cumulativeInvoicedAmount = value;
                }

                /**
                 * 获取accumulatedInvoicedPercentage属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAccumulatedInvoicedPercentage() {
                    return accumulatedInvoicedPercentage;
                }

                /**
                 * 设置accumulatedInvoicedPercentage属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAccumulatedInvoicedPercentage(String value) {
                    this.accumulatedInvoicedPercentage = value;
                }

                /**
                 * 获取amountOfOnTheWayInvoice属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAmountOfOnTheWayInvoice() {
                    return amountOfOnTheWayInvoice;
                }

                /**
                 * 设置amountOfOnTheWayInvoice属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAmountOfOnTheWayInvoice(String value) {
                    this.amountOfOnTheWayInvoice = value;
                }

                /**
                 * 获取uninvoicedAmount属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getUninvoicedAmount() {
                    return uninvoicedAmount;
                }

                /**
                 * 设置uninvoicedAmount属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setUninvoicedAmount(String value) {
                    this.uninvoicedAmount = value;
                }

                /**
                 * 获取amountOfThisInvoice属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAmountOfThisInvoice() {
                    return amountOfThisInvoice;
                }

                /**
                 * 设置amountOfThisInvoice属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAmountOfThisInvoice(String value) {
                    this.amountOfThisInvoice = value;
                }

                /**
                 * 获取proportionOfThisInvoice属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getProportionOfThisInvoice() {
                    return proportionOfThisInvoice;
                }

                /**
                 * 设置proportionOfThisInvoice属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setProportionOfThisInvoice(String value) {
                    this.proportionOfThisInvoice = value;
                }

                /**
                 * 获取cumulativeAmountPaid属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCumulativeAmountPaid() {
                    return cumulativeAmountPaid;
                }

                /**
                 * 设置cumulativeAmountPaid属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCumulativeAmountPaid(String value) {
                    this.cumulativeAmountPaid = value;
                }

                /**
                 * 获取accumulatedPaymentRatio属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAccumulatedPaymentRatio() {
                    return accumulatedPaymentRatio;
                }

                /**
                 * 设置accumulatedPaymentRatio属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAccumulatedPaymentRatio(String value) {
                    this.accumulatedPaymentRatio = value;
                }

                /**
                 * 获取amountOfPaymentInTransit属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAmountOfPaymentInTransit() {
                    return amountOfPaymentInTransit;
                }

                /**
                 * 设置amountOfPaymentInTransit属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAmountOfPaymentInTransit(String value) {
                    this.amountOfPaymentInTransit = value;
                }

                /**
                 * 获取unpaidAmount属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getUnpaidAmount() {
                    return unpaidAmount;
                }

                /**
                 * 设置unpaidAmount属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setUnpaidAmount(String value) {
                    this.unpaidAmount = value;
                }

                /**
                 * 获取amountOfPayment属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAmountOfPayment() {
                    return amountOfPayment;
                }

                /**
                 * 设置amountOfPayment属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAmountOfPayment(String value) {
                    this.amountOfPayment = value;
                }

                /**
                 * 获取proportionOfPayment属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getProportionOfPayment() {
                    return proportionOfPayment;
                }

                /**
                 * 设置proportionOfPayment属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setProportionOfPayment(String value) {
                    this.proportionOfPayment = value;
                }

                /**
                 * 获取reservedField1属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getReservedField1() {
                    return reservedField1;
                }

                /**
                 * 设置reservedField1属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setReservedField1(String value) {
                    this.reservedField1 = value;
                }

                /**
                 * 获取reservedField2属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getReservedField2() {
                    return reservedField2;
                }

                /**
                 * 设置reservedField2属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setReservedField2(String value) {
                    this.reservedField2 = value;
                }

                /**
                 * 获取reservedField3属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getReservedField3() {
                    return reservedField3;
                }

                /**
                 * 设置reservedField3属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setReservedField3(String value) {
                    this.reservedField3 = value;
                }

                /**
                 * 获取reservedField4属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getReservedField4() {
                    return reservedField4;
                }

                /**
                 * 设置reservedField4属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setReservedField4(String value) {
                    this.reservedField4 = value;
                }

                /**
                 * 获取reservedField5属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getReservedField5() {
                    return reservedField5;
                }

                /**
                 * 设置reservedField5属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setReservedField5(String value) {
                    this.reservedField5 = value;
                }

            }

        }

    }

}
