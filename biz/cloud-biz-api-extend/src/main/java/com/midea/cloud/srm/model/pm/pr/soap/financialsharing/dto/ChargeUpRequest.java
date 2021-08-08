
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
 *                             &lt;element name="boeVendorNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="project" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="bpCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                             &lt;element name="boeDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="boeAbstract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="appliedPayTotalAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="payTotalAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="letterOfCreditNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="srmPaymentOrder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="projectNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="contractCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
 *                                       &lt;element name="lineNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="boeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="boeTypeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="srmNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="invoiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="creditAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="paidAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="appliedPayAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="unpaidAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="appliedPayLineAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="sourceSystemCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="accountsPayableDueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
 *                                       &lt;element name="payDetailsLineNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlRootElement(name = "chargeUpRequest")
public class ChargeUpRequest {

    @XmlElement(name = "BOE_INFO", required = true)
    protected ChargeUpRequest.BOEINFO boeinfo;

    /**
     * 获取boeinfo属性的值。
     *
     * @return
     *     possible object is
     *     {@link ChargeUpRequest.BOEINFO }
     *
     */
    public ChargeUpRequest.BOEINFO getBOEINFO() {
        return boeinfo;
    }

    /**
     * 设置boeinfo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link ChargeUpRequest.BOEINFO }
     *
     */
    public void setBOEINFO(ChargeUpRequest.BOEINFO value) {
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
     *                   &lt;element name="boeVendorNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="project" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="bpCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                   &lt;element name="boeDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="boeAbstract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="appliedPayTotalAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="payTotalAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="letterOfCreditNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="srmPaymentOrder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="projectNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="contractCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
     *                             &lt;element name="lineNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="boeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="boeTypeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="srmNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="invoiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="creditAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="paidAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="appliedPayAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="unpaidAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="appliedPayLineAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="sourceSystemCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="accountsPayableDueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
     *                             &lt;element name="payDetailsLineNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        protected ChargeUpRequest.BOEINFO.BOEHEADER boeheader;
        @XmlElement(name = "BOE_EXPENSES")
        protected ChargeUpRequest.BOEINFO.BOEEXPENSES boeexpenses;
        @XmlElement(name = "BOE_PAYMENTS")
        protected ChargeUpRequest.BOEINFO.BOEPAYMENTS boepayments;
        @XmlElement(name = "BOE_ATTACHMENTS")
        protected ChargeUpRequest.BOEINFO.BOEATTACHMENTS boeattachments;
        @XmlElement(name = "FAM_CONTRACTS")
        protected ChargeUpRequest.BOEINFO.FAMCONTRACTS famcontracts;

        /**
         * 获取boeheader属性的值。
         *
         * @return
         *     possible object is
         *     {@link ChargeUpRequest.BOEINFO.BOEHEADER }
         *
         */
        public ChargeUpRequest.BOEINFO.BOEHEADER getBOEHEADER() {
            return boeheader;
        }

        /**
         * 设置boeheader属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link ChargeUpRequest.BOEINFO.BOEHEADER }
         *
         */
        public void setBOEHEADER(ChargeUpRequest.BOEINFO.BOEHEADER value) {
            this.boeheader = value;
        }

        /**
         * 获取boeexpenses属性的值。
         *
         * @return
         *     possible object is
         *     {@link ChargeUpRequest.BOEINFO.BOEEXPENSES }
         *
         */
        public ChargeUpRequest.BOEINFO.BOEEXPENSES getBOEEXPENSES() {
            return boeexpenses;
        }

        /**
         * 设置boeexpenses属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link ChargeUpRequest.BOEINFO.BOEEXPENSES }
         *
         */
        public void setBOEEXPENSES(ChargeUpRequest.BOEINFO.BOEEXPENSES value) {
            this.boeexpenses = value;
        }

        /**
         * 获取boepayments属性的值。
         *
         * @return
         *     possible object is
         *     {@link ChargeUpRequest.BOEINFO.BOEPAYMENTS }
         *
         */
        public ChargeUpRequest.BOEINFO.BOEPAYMENTS getBOEPAYMENTS() {
            return boepayments;
        }

        /**
         * 设置boepayments属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link ChargeUpRequest.BOEINFO.BOEPAYMENTS }
         *
         */
        public void setBOEPAYMENTS(ChargeUpRequest.BOEINFO.BOEPAYMENTS value) {
            this.boepayments = value;
        }

        /**
         * 获取boeattachments属性的值。
         *
         * @return
         *     possible object is
         *     {@link ChargeUpRequest.BOEINFO.BOEATTACHMENTS }
         *
         */
        public ChargeUpRequest.BOEINFO.BOEATTACHMENTS getBOEATTACHMENTS() {
            return boeattachments;
        }

        /**
         * 设置boeattachments属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link ChargeUpRequest.BOEINFO.BOEATTACHMENTS }
         *
         */
        public void setBOEATTACHMENTS(ChargeUpRequest.BOEINFO.BOEATTACHMENTS value) {
            this.boeattachments = value;
        }

        /**
         * 获取famcontracts属性的值。
         *
         * @return
         *     possible object is
         *     {@link ChargeUpRequest.BOEINFO.FAMCONTRACTS }
         *
         */
        public ChargeUpRequest.BOEINFO.FAMCONTRACTS getFAMCONTRACTS() {
            return famcontracts;
        }

        /**
         * 设置famcontracts属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link ChargeUpRequest.BOEINFO.FAMCONTRACTS }
         *
         */
        public void setFAMCONTRACTS(ChargeUpRequest.BOEINFO.FAMCONTRACTS value) {
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
             * {@link ChargeUpRequest.BOEINFO.BOEATTACHMENTS.BOEATTACHMENT }
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
         *                   &lt;element name="lineNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="boeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="boeTypeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="srmNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="invoiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="creditAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="paidAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="appliedPayAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="unpaidAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="appliedPayLineAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="sourceSystemCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="accountsPayableDueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
             * {@link ChargeUpRequest.BOEINFO.BOEEXPENSES.BOEEXPENSE }
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
             *         &lt;element name="lineNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="boeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="boeTypeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="srmNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="invoiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="creditAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="paidAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="appliedPayAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="unpaidAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="appliedPayLineAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="sourceSystemCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="accountsPayableDueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                "lineNum",
                "boeNum",
                "boeTypeNum",
                "srmNum",
                "invoiceID",
                "currencyCode",
                "creditAmount",
                "paidAmount",
                "appliedPayAmount",
                "unpaidAmount",
                "appliedPayLineAmount",
                "description",
                "sourceSystemCode",
                "reservedField1",
                "reservedField2",
                "reservedField3",
                "reservedField4",
                "reservedField5",
                "accountsPayableDueDate"
            })
            public static class BOEEXPENSE {

                protected String lineNum;
                protected String boeNum;
                protected String boeTypeNum;
                protected String srmNum;
                protected String invoiceID;
                protected String currencyCode;
                protected BigDecimal creditAmount;
                protected BigDecimal paidAmount;
                protected BigDecimal appliedPayAmount;
                protected BigDecimal unpaidAmount;
                protected BigDecimal appliedPayLineAmount;
                protected String description;
                protected String sourceSystemCode;
                protected String reservedField1;
                protected String reservedField2;
                protected String reservedField3;
                protected String reservedField4;
                protected String reservedField5;
                protected String accountsPayableDueDate;

                /**
                 * 获取lineNum属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getLineNum() {
                    return lineNum;
                }

                /**
                 * 设置lineNum属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setLineNum(String value) {
                    this.lineNum = value;
                }

                /**
                 * 获取boeNum属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getBoeNum() {
                    return boeNum;
                }

                /**
                 * 设置boeNum属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setBoeNum(String value) {
                    this.boeNum = value;
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
                 * 获取invoiceID属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getInvoiceID() {
                    return invoiceID;
                }

                /**
                 * 设置invoiceID属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setInvoiceID(String value) {
                    this.invoiceID = value;
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
                 * 获取creditAmount属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getCreditAmount() {
                    return creditAmount;
                }

                /**
                 * 设置creditAmount属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setCreditAmount(BigDecimal value) {
                    this.creditAmount = value;
                }

                /**
                 * 获取paidAmount属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getPaidAmount() {
                    return paidAmount;
                }

                /**
                 * 设置paidAmount属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setPaidAmount(BigDecimal value) {
                    this.paidAmount = value;
                }

                /**
                 * 获取appliedPayAmount属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getAppliedPayAmount() {
                    return appliedPayAmount;
                }

                /**
                 * 设置appliedPayAmount属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setAppliedPayAmount(BigDecimal value) {
                    this.appliedPayAmount = value;
                }

                /**
                 * 获取unpaidAmount属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getUnpaidAmount() {
                    return unpaidAmount;
                }

                /**
                 * 设置unpaidAmount属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setUnpaidAmount(BigDecimal value) {
                    this.unpaidAmount = value;
                }

                /**
                 * 获取appliedPayLineAmount属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *
                 */
                public BigDecimal getAppliedPayLineAmount() {
                    return appliedPayLineAmount;
                }

                /**
                 * 设置appliedPayLineAmount属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *
                 */
                public void setAppliedPayLineAmount(BigDecimal value) {
                    this.appliedPayLineAmount = value;
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
                 * 获取sourceSystemCode属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getSourceSystemCode() {
                    return sourceSystemCode;
                }

                /**
                 * 设置sourceSystemCode属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setSourceSystemCode(String value) {
                    this.sourceSystemCode = value;
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
         *         &lt;element name="boeVendorNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="project" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="bpCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *         &lt;element name="boeDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="boeAbstract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="appliedPayTotalAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="payTotalAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="letterOfCreditNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="srmPaymentOrder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="projectNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="contractCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "boeVendorNum",
            "project",
            "bpCount",
            "boeDate",
            "boeAbstract",
            "currencyCode",
            "appliedPayTotalAmount",
            "payTotalAmount",
            "letterOfCreditNum",
            "srmPaymentOrder",
            "reservedField1",
            "reservedField2",
            "reservedField3",
            "reservedField4",
            "reservedField5",
            "projectNumber",
            "contractCode",
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
            protected String boeVendorNum;
            protected String project;
            protected Integer bpCount;
            protected String boeDate;
            protected String boeAbstract;
            protected String currencyCode;
            protected BigDecimal appliedPayTotalAmount;
            protected BigDecimal payTotalAmount;
            protected String letterOfCreditNum;
            protected String srmPaymentOrder;
            protected String reservedField1;
            protected String reservedField2;
            protected String reservedField3;
            protected String reservedField4;
            protected String reservedField5;
            protected String projectNumber;
            protected String contractCode;
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
             * 获取boeVendorNum属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getBoeVendorNum() {
                return boeVendorNum;
            }

            /**
             * 设置boeVendorNum属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setBoeVendorNum(String value) {
                this.boeVendorNum = value;
            }

            /**
             * 获取project属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getProject() {
                return project;
            }

            /**
             * 设置project属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setProject(String value) {
                this.project = value;
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
             * 获取appliedPayTotalAmount属性的值。
             *
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *
             */
            public BigDecimal getAppliedPayTotalAmount() {
                return appliedPayTotalAmount;
            }

            /**
             * 设置appliedPayTotalAmount属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *
             */
            public void setAppliedPayTotalAmount(BigDecimal value) {
                this.appliedPayTotalAmount = value;
            }

            /**
             * 获取payTotalAmount属性的值。
             *
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *
             */
            public BigDecimal getPayTotalAmount() {
                return payTotalAmount;
            }

            /**
             * 设置payTotalAmount属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *
             */
            public void setPayTotalAmount(BigDecimal value) {
                this.payTotalAmount = value;
            }

            /**
             * 获取letterOfCreditNum属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getLetterOfCreditNum() {
                return letterOfCreditNum;
            }

            /**
             * 设置letterOfCreditNum属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setLetterOfCreditNum(String value) {
                this.letterOfCreditNum = value;
            }

            /**
             * 获取srmPaymentOrder属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getSrmPaymentOrder() {
                return srmPaymentOrder;
            }

            /**
             * 设置srmPaymentOrder属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setSrmPaymentOrder(String value) {
                this.srmPaymentOrder = value;
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
             * 获取contractCode属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getContractCode() {
                return contractCode;
            }

            /**
             * 设置contractCode属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setContractCode(String value) {
                this.contractCode = value;
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
         *                   &lt;element name="payDetailsLineNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
             * {@link ChargeUpRequest.BOEINFO.BOEPAYMENTS.BOEPAYMENT }
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
             *         &lt;element name="payDetailsLineNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                "payDetailsLineNum",
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

                protected String payDetailsLineNum;
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
                 * 获取payDetailsLineNum属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getPayDetailsLineNum() {
                    return payDetailsLineNum;
                }

                /**
                 * 设置payDetailsLineNum属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setPayDetailsLineNum(String value) {
                    this.payDetailsLineNum = value;
                }

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
             * {@link ChargeUpRequest.BOEINFO.FAMCONTRACTS.FAMCONTRACT }
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
