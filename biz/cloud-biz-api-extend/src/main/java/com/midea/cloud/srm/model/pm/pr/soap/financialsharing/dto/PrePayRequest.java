
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
 *                             &lt;element name="srmSlNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="taskNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="taskName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="projectName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
 *                                       &lt;element name="boeTypeSubNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="expenseAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="rateValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="convertedAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="expenseDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="invoiceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="invoiceNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="taxType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="taxValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="taxAmout" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="amountExcluedTax" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="convertedTaxAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="convertedAmountExcluedTax" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="payAwtGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="payAwtTaxAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="expenseDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="projectNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="taskNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="taskName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
 *                   &lt;element name="BOE_PAYMENTS" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="BOE_PAYMENT" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="paymentModeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="accountName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="vendorSitesID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *                                       &lt;element name="bankBranchName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="bankAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="payeeBankCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="paymentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="stdCurrencyAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="paymentAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="rateValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="payCurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlRootElement(name = "prePayRequest")
public class PrePayRequest {

    @XmlElement(name = "BOE_INFO", required = true)
    protected PrePayRequest.BOEINFO boeinfo;

    /**
     * 获取boeinfo属性的值。
     *
     * @return
     *     possible object is
     *     {@link PrePayRequest.BOEINFO }
     *
     */
    public PrePayRequest.BOEINFO getBOEINFO() {
        return boeinfo;
    }

    /**
     * 设置boeinfo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link PrePayRequest.BOEINFO }
     *
     */
    public void setBOEINFO(PrePayRequest.BOEINFO value) {
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
     *                   &lt;element name="srmSlNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="taskNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="taskName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="projectName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
     *                             &lt;element name="boeTypeSubNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="expenseAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="rateValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="convertedAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="expenseDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="invoiceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="invoiceNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="taxType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="taxValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="taxAmout" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="amountExcluedTax" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="convertedTaxAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="convertedAmountExcluedTax" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="payAwtGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="payAwtTaxAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="expenseDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="projectNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="taskNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="taskName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
     *         &lt;element name="BOE_PAYMENTS" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="BOE_PAYMENT" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="paymentModeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="accountName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="vendorSitesID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
     *                             &lt;element name="bankBranchName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="bankAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="payeeBankCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="paymentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="stdCurrencyAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="paymentAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="rateValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="payCurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "boepayments",
        "boeattachments",
        "famcontracts"
    })
    public static class BOEINFO {

        @XmlElement(name = "BOE_HEADER")
        protected PrePayRequest.BOEINFO.BOEHEADER boeheader;
        @XmlElement(name = "BOE_EXPENSES")
        protected PrePayRequest.BOEINFO.BOEEXPENSES boeexpenses;
        @XmlElement(name = "BOE_PAYMENTS")
        protected PrePayRequest.BOEINFO.BOEPAYMENTS boepayments;
        @XmlElement(name = "BOE_ATTACHMENTS")
        protected PrePayRequest.BOEINFO.BOEATTACHMENTS boeattachments;
        @XmlElement(name = "FAM_CONTRACTS")
        protected PrePayRequest.BOEINFO.FAMCONTRACTS famcontracts;

        /**
         * 获取boeheader属性的值。
         *
         * @return
         *     possible object is
         *     {@link PrePayRequest.BOEINFO.BOEHEADER }
         *
         */
        public PrePayRequest.BOEINFO.BOEHEADER getBOEHEADER() {
            return boeheader;
        }

        /**
         * 设置boeheader属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link PrePayRequest.BOEINFO.BOEHEADER }
         *
         */
        public void setBOEHEADER(PrePayRequest.BOEINFO.BOEHEADER value) {
            this.boeheader = value;
        }

        /**
         * 获取boeexpenses属性的值。
         *
         * @return
         *     possible object is
         *     {@link PrePayRequest.BOEINFO.BOEEXPENSES }
         *
         */
        public PrePayRequest.BOEINFO.BOEEXPENSES getBOEEXPENSES() {
            return boeexpenses;
        }

        /**
         * 设置boeexpenses属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link PrePayRequest.BOEINFO.BOEEXPENSES }
         *
         */
        public void setBOEEXPENSES(PrePayRequest.BOEINFO.BOEEXPENSES value) {
            this.boeexpenses = value;
        }

        /**
         * 获取boepayments属性的值。
         *
         * @return
         *     possible object is
         *     {@link PrePayRequest.BOEINFO.BOEPAYMENTS }
         *
         */
        public PrePayRequest.BOEINFO.BOEPAYMENTS getBOEPAYMENTS() {
            return boepayments;
        }

        /**
         * 设置boepayments属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link PrePayRequest.BOEINFO.BOEPAYMENTS }
         *
         */
        public void setBOEPAYMENTS(PrePayRequest.BOEINFO.BOEPAYMENTS value) {
            this.boepayments = value;
        }

        /**
         * 获取boeattachments属性的值。
         *
         * @return
         *     possible object is
         *     {@link PrePayRequest.BOEINFO.BOEATTACHMENTS }
         *
         */
        public PrePayRequest.BOEINFO.BOEATTACHMENTS getBOEATTACHMENTS() {
            return boeattachments;
        }

        /**
         * 设置boeattachments属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link PrePayRequest.BOEINFO.BOEATTACHMENTS }
         *
         */
        public void setBOEATTACHMENTS(PrePayRequest.BOEINFO.BOEATTACHMENTS value) {
            this.boeattachments = value;
        }

        /**
         * 获取famcontracts属性的值。
         *
         * @return
         *     possible object is
         *     {@link PrePayRequest.BOEINFO.FAMCONTRACTS }
         *
         */
        public PrePayRequest.BOEINFO.FAMCONTRACTS getFAMCONTRACTS() {
            return famcontracts;
        }

        /**
         * 设置famcontracts属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link PrePayRequest.BOEINFO.FAMCONTRACTS }
         *
         */
        public void setFAMCONTRACTS(PrePayRequest.BOEINFO.FAMCONTRACTS value) {
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
             * {@link PrePayRequest.BOEINFO.BOEATTACHMENTS.BOEATTACHMENT }
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
         *                   &lt;element name="boeTypeSubNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="expenseAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="rateValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="convertedAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="expenseDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="invoiceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="invoiceNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="taxType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="taxValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="taxAmout" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="amountExcluedTax" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="convertedTaxAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="convertedAmountExcluedTax" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="payAwtGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="payAwtTaxAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="expenseDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="projectNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="taskNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="taskName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
             * {@link PrePayRequest.BOEINFO.BOEEXPENSES.BOEEXPENSE }
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
             *         &lt;element name="boeTypeSubNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="expenseAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="rateValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="convertedAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="expenseDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="invoiceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="invoiceNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="taxType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="taxValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="taxAmout" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="amountExcluedTax" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="convertedTaxAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="convertedAmountExcluedTax" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="payAwtGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="payAwtTaxAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="expenseDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="projectNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="taskNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="taskName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                "boeTypeSubNum",
                "expenseAmount",
                "currencyCode",
                "rateValue",
                "convertedAmount",
                "expenseDate",
                "invoiceType",
                "invoiceNumber",
                "taxType",
                "taxValue",
                "taxAmout",
                "amountExcluedTax",
                "convertedTaxAmount",
                "convertedAmountExcluedTax",
                "payAwtGroup",
                "payAwtTaxAmount",
                "expenseDesc",
                "reservedField1",
                "reservedField2",
                "reservedField3",
                "reservedField4",
                "reservedField5",
                "projectNum",
                "taskNumber",
                "taskName",
                "projectName"
            })
            public static class BOEEXPENSE {

                protected String boeTypeSubNum;
                protected BigDecimal expenseAmount;
                protected String currencyCode;
                protected BigDecimal rateValue;
                protected BigDecimal convertedAmount;
                protected String expenseDate;
                protected String invoiceType;
                protected String invoiceNumber;
                protected String taxType;
                protected BigDecimal taxValue;
                protected BigDecimal taxAmout;
                protected BigDecimal amountExcluedTax;
                protected BigDecimal convertedTaxAmount;
                protected BigDecimal convertedAmountExcluedTax;
                protected String payAwtGroup;
                protected BigDecimal payAwtTaxAmount;
                protected String expenseDesc;
                protected String reservedField1;
                protected String reservedField2;
                protected String reservedField3;
                protected String reservedField4;
                protected String reservedField5;
                protected String projectNum;
                protected String taskNumber;
                protected String taskName;
                protected String projectName;

                /**
                 * 获取boeTypeSubNum属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getBoeTypeSubNum() {
                    return boeTypeSubNum;
                }

                /**
                 * 设置boeTypeSubNum属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setBoeTypeSubNum(String value) {
                    this.boeTypeSubNum = value;
                }

                /**
                 * 获取expenseAmount属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getExpenseAmount() {
                    return expenseAmount;
                }

                /**
                 * 设置expenseAmount属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setExpenseAmount(BigDecimal value) {
                    this.expenseAmount = value;
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
                 * 获取rateValue属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getRateValue() {
                    return rateValue;
                }

                /**
                 * 设置rateValue属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setRateValue(BigDecimal value) {
                    this.rateValue = value;
                }

                /**
                 * 获取convertedAmount属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getConvertedAmount() {
                    return convertedAmount;
                }

                /**
                 * 设置convertedAmount属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setConvertedAmount(BigDecimal value) {
                    this.convertedAmount = value;
                }

                /**
                 * 获取expenseDate属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getExpenseDate() {
                    return expenseDate;
                }

                /**
                 * 设置expenseDate属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setExpenseDate(String value) {
                    this.expenseDate = value;
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
                 * 获取invoiceNumber属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getInvoiceNumber() {
                    return invoiceNumber;
                }

                /**
                 * 设置invoiceNumber属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setInvoiceNumber(String value) {
                    this.invoiceNumber = value;
                }

                /**
                 * 获取taxType属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getTaxType() {
                    return taxType;
                }

                /**
                 * 设置taxType属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setTaxType(String value) {
                    this.taxType = value;
                }

                /**
                 * 获取taxValue属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getTaxValue() {
                    return taxValue;
                }

                /**
                 * 设置taxValue属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setTaxValue(BigDecimal value) {
                    this.taxValue = value;
                }

                /**
                 * 获取taxAmout属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getTaxAmout() {
                    return taxAmout;
                }

                /**
                 * 设置taxAmout属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setTaxAmout(BigDecimal value) {
                    this.taxAmout = value;
                }

                /**
                 * 获取amountExcluedTax属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getAmountExcluedTax() {
                    return amountExcluedTax;
                }

                /**
                 * 设置amountExcluedTax属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setAmountExcluedTax(BigDecimal value) {
                    this.amountExcluedTax = value;
                }

                /**
                 * 获取convertedTaxAmount属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getConvertedTaxAmount() {
                    return convertedTaxAmount;
                }

                /**
                 * 设置convertedTaxAmount属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setConvertedTaxAmount(BigDecimal value) {
                    this.convertedTaxAmount = value;
                }

                /**
                 * 获取convertedAmountExcluedTax属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getConvertedAmountExcluedTax() {
                    return convertedAmountExcluedTax;
                }

                /**
                 * 设置convertedAmountExcluedTax属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setConvertedAmountExcluedTax(BigDecimal value) {
                    this.convertedAmountExcluedTax = value;
                }

                /**
                 * 获取payAwtGroup属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getPayAwtGroup() {
                    return payAwtGroup;
                }

                /**
                 * 设置payAwtGroup属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setPayAwtGroup(String value) {
                    this.payAwtGroup = value;
                }

                /**
                 * 获取payAwtTaxAmount属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getPayAwtTaxAmount() {
                    return payAwtTaxAmount;
                }

                /**
                 * 设置payAwtTaxAmount属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setPayAwtTaxAmount(BigDecimal value) {
                    this.payAwtTaxAmount = value;
                }

                /**
                 * 获取expenseDesc属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getExpenseDesc() {
                    return expenseDesc;
                }

                /**
                 * 设置expenseDesc属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setExpenseDesc(String value) {
                    this.expenseDesc = value;
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
         *         &lt;element name="srmSlNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="taskNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="taskName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "srmSlNum",
            "reservedField1",
            "reservedField2",
            "reservedField3",
            "reservedField4",
            "reservedField5",
            "taskNumber",
            "taskName",
            "projectName"
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
            protected String srmSlNum;
            protected String reservedField1;
            protected String reservedField2;
            protected String reservedField3;
            protected String reservedField4;
            protected String reservedField5;
            protected String taskNumber;
            protected String taskName;
            protected String projectName;

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
             * 获取srmSlNum属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getSrmSlNum() {
                return srmSlNum;
            }

            /**
             * 设置srmSlNum属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setSrmSlNum(String value) {
                this.srmSlNum = value;
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
         *         &lt;element name="BOE_PAYMENT" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="paymentModeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="accountName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="vendorSitesID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
         *                   &lt;element name="bankBranchName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="bankAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="payeeBankCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="paymentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="stdCurrencyAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="paymentAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="rateValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="payCurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "boepayment"
        })
        public static class BOEPAYMENTS {

            @XmlElement(name = "BOE_PAYMENT")
            protected List<BOEPAYMENT> boepayment;

            /**
             * Gets the value of the boepayment property.
             *
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the boepayment property.
             *
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getBOEPAYMENT().add(newItem);
             * </pre>
             *
             *
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link PrePayRequest.BOEINFO.BOEPAYMENTS.BOEPAYMENT }
             *
             *
             */
            public List<BOEPAYMENT> getBOEPAYMENT() {
                if (boepayment == null) {
                    boepayment = new ArrayList<BOEPAYMENT>();
                }
                return this.boepayment;
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
             *         &lt;element name="paymentModeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="accountName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="vendorSitesID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
             *         &lt;element name="bankBranchName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="bankAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="payeeBankCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="paymentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="stdCurrencyAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="paymentAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="rateValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="payCurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                "paymentModeCode",
                "accountName",
                "vendorSitesID",
                "bankBranchName",
                "bankAccount",
                "payeeBankCode",
                "paymentType",
                "stdCurrencyAmount",
                "paymentAmount",
                "rateValue",
                "payCurrencyCode",
                "reservedField1",
                "reservedField2",
                "reservedField3",
                "reservedField4",
                "reservedField5"
            })
            public static class BOEPAYMENT {

                protected String paymentModeCode;
                protected String accountName;
                protected Long vendorSitesID;
                protected String bankBranchName;
                protected String bankAccount;
                protected String payeeBankCode;
                protected String paymentType;
                protected BigDecimal stdCurrencyAmount;
                protected BigDecimal paymentAmount;
                protected BigDecimal rateValue;
                protected String payCurrencyCode;
                protected String reservedField1;
                protected String reservedField2;
                protected String reservedField3;
                protected String reservedField4;
                protected String reservedField5;

                /**
                 * 获取paymentModeCode属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getPaymentModeCode() {
                    return paymentModeCode;
                }

                /**
                 * 设置paymentModeCode属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setPaymentModeCode(String value) {
                    this.paymentModeCode = value;
                }

                /**
                 * 获取accountName属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getAccountName() {
                    return accountName;
                }

                /**
                 * 设置accountName属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setAccountName(String value) {
                    this.accountName = value;
                }

                /**
                 * 获取vendorSitesID属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *
                 */
                public Long getVendorSitesID() {
                    return vendorSitesID;
                }

                /**
                 * 设置vendorSitesID属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *
                 */
                public void setVendorSitesID(Long value) {
                    this.vendorSitesID = value;
                }

                /**
                 * 获取bankBranchName属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getBankBranchName() {
                    return bankBranchName;
                }

                /**
                 * 设置bankBranchName属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setBankBranchName(String value) {
                    this.bankBranchName = value;
                }

                /**
                 * 获取bankAccount属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getBankAccount() {
                    return bankAccount;
                }

                /**
                 * 设置bankAccount属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setBankAccount(String value) {
                    this.bankAccount = value;
                }

                /**
                 * 获取payeeBankCode属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getPayeeBankCode() {
                    return payeeBankCode;
                }

                /**
                 * 设置payeeBankCode属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setPayeeBankCode(String value) {
                    this.payeeBankCode = value;
                }

                /**
                 * 获取paymentType属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getPaymentType() {
                    return paymentType;
                }

                /**
                 * 设置paymentType属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setPaymentType(String value) {
                    this.paymentType = value;
                }

                /**
                 * 获取stdCurrencyAmount属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getStdCurrencyAmount() {
                    return stdCurrencyAmount;
                }

                /**
                 * 设置stdCurrencyAmount属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setStdCurrencyAmount(BigDecimal value) {
                    this.stdCurrencyAmount = value;
                }

                /**
                 * 获取paymentAmount属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getPaymentAmount() {
                    return paymentAmount;
                }

                /**
                 * 设置paymentAmount属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setPaymentAmount(BigDecimal value) {
                    this.paymentAmount = value;
                }

                /**
                 * 获取rateValue属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getRateValue() {
                    return rateValue;
                }

                /**
                 * 设置rateValue属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setRateValue(BigDecimal value) {
                    this.rateValue = value;
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
             * {@link PrePayRequest.BOEINFO.FAMCONTRACTS.FAMCONTRACT }
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
