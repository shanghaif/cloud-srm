
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
 *                             &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="bpCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                             &lt;element name="boeDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="boeAbstract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="srmNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="applyAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="relationCompanyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="relationEmployeeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
 *                                       &lt;element name="boeTypeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="boeDeptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="expenseAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="rateValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="stdCurrencyAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="expenseDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
 *                   &lt;element name="BOE_EXPENSE_CAVS" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="BOE_EXPENSE_CAV" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="boeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="boeTypeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="boeEmployeeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="approvalDeptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="srmNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="creditAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="paidAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="appliedPayAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="unpaidAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="appliedPayLineAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                       &lt;element name="invoiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="sourceSystemCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlRootElement(name = "payApplyRequest")
public class PayApplyRequest {

    @XmlElement(name = "BOE_INFO", required = true)
    protected PayApplyRequest.BOEINFO boeinfo;

    /**
     * 获取boeinfo属性的值。
     *
     * @return
     *     possible object is
     *     {@link PayApplyRequest.BOEINFO }
     *
     */
    public PayApplyRequest.BOEINFO getBOEINFO() {
        return boeinfo;
    }

    /**
     * 设置boeinfo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link PayApplyRequest.BOEINFO }
     *
     */
    public void setBOEINFO(PayApplyRequest.BOEINFO value) {
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
     *                   &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="bpCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                   &lt;element name="boeDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="boeAbstract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="srmNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="applyAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="relationCompanyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="relationEmployeeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
     *                             &lt;element name="boeTypeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="boeDeptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="expenseAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="rateValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="stdCurrencyAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="expenseDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
     *         &lt;element name="BOE_EXPENSE_CAVS" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="BOE_EXPENSE_CAV" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="boeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="boeTypeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="boeEmployeeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="approvalDeptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="srmNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="creditAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="paidAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="appliedPayAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="unpaidAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="appliedPayLineAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                             &lt;element name="invoiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="sourceSystemCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "boeexpensecavs"
    })
    public static class BOEINFO {

        @XmlElement(name = "BOE_HEADER")
        protected PayApplyRequest.BOEINFO.BOEHEADER boeheader;
        @XmlElement(name = "BOE_EXPENSES")
        protected PayApplyRequest.BOEINFO.BOEEXPENSES boeexpenses;
        @XmlElement(name = "BOE_PAYMENTS")
        protected PayApplyRequest.BOEINFO.BOEPAYMENTS boepayments;
        @XmlElement(name = "BOE_ATTACHMENTS")
        protected PayApplyRequest.BOEINFO.BOEATTACHMENTS boeattachments;
        @XmlElement(name = "BOE_EXPENSE_CAVS")
        protected PayApplyRequest.BOEINFO.BOEEXPENSECAVS boeexpensecavs;

        /**
         * 获取boeheader属性的值。
         *
         * @return
         *     possible object is
         *     {@link PayApplyRequest.BOEINFO.BOEHEADER }
         *
         */
        public PayApplyRequest.BOEINFO.BOEHEADER getBOEHEADER() {
            return boeheader;
        }

        /**
         * 设置boeheader属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link PayApplyRequest.BOEINFO.BOEHEADER }
         *
         */
        public void setBOEHEADER(PayApplyRequest.BOEINFO.BOEHEADER value) {
            this.boeheader = value;
        }

        /**
         * 获取boeexpenses属性的值。
         *
         * @return
         *     possible object is
         *     {@link PayApplyRequest.BOEINFO.BOEEXPENSES }
         *
         */
        public PayApplyRequest.BOEINFO.BOEEXPENSES getBOEEXPENSES() {
            return boeexpenses;
        }

        /**
         * 设置boeexpenses属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link PayApplyRequest.BOEINFO.BOEEXPENSES }
         *
         */
        public void setBOEEXPENSES(PayApplyRequest.BOEINFO.BOEEXPENSES value) {
            this.boeexpenses = value;
        }

        /**
         * 获取boepayments属性的值。
         *
         * @return
         *     possible object is
         *     {@link PayApplyRequest.BOEINFO.BOEPAYMENTS }
         *
         */
        public PayApplyRequest.BOEINFO.BOEPAYMENTS getBOEPAYMENTS() {
            return boepayments;
        }

        /**
         * 设置boepayments属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link PayApplyRequest.BOEINFO.BOEPAYMENTS }
         *
         */
        public void setBOEPAYMENTS(PayApplyRequest.BOEINFO.BOEPAYMENTS value) {
            this.boepayments = value;
        }

        /**
         * 获取boeattachments属性的值。
         *
         * @return
         *     possible object is
         *     {@link PayApplyRequest.BOEINFO.BOEATTACHMENTS }
         *
         */
        public PayApplyRequest.BOEINFO.BOEATTACHMENTS getBOEATTACHMENTS() {
            return boeattachments;
        }

        /**
         * 设置boeattachments属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link PayApplyRequest.BOEINFO.BOEATTACHMENTS }
         *
         */
        public void setBOEATTACHMENTS(PayApplyRequest.BOEINFO.BOEATTACHMENTS value) {
            this.boeattachments = value;
        }

        /**
         * 获取boeexpensecavs属性的值。
         *
         * @return
         *     possible object is
         *     {@link PayApplyRequest.BOEINFO.BOEEXPENSECAVS }
         *
         */
        public PayApplyRequest.BOEINFO.BOEEXPENSECAVS getBOEEXPENSECAVS() {
            return boeexpensecavs;
        }

        /**
         * 设置boeexpensecavs属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link PayApplyRequest.BOEINFO.BOEEXPENSECAVS }
         *
         */
        public void setBOEEXPENSECAVS(PayApplyRequest.BOEINFO.BOEEXPENSECAVS value) {
            this.boeexpensecavs = value;
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
             * {@link PayApplyRequest.BOEINFO.BOEATTACHMENTS.BOEATTACHMENT }
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
         *         &lt;element name="BOE_EXPENSE_CAV" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="boeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="boeTypeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="boeEmployeeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="approvalDeptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="srmNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="creditAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="paidAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="appliedPayAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="unpaidAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="appliedPayLineAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="invoiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="sourceSystemCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "boeexpensecav"
        })
        public static class BOEEXPENSECAVS {

            @XmlElement(name = "BOE_EXPENSE_CAV")
            protected List<BOEEXPENSECAV> boeexpensecav;

            /**
             * Gets the value of the boeexpensecav property.
             *
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the boeexpensecav property.
             *
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getBOEEXPENSECAV().add(newItem);
             * </pre>
             *
             *
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link PayApplyRequest.BOEINFO.BOEEXPENSECAVS.BOEEXPENSECAV }
             *
             *
             */
            public List<BOEEXPENSECAV> getBOEEXPENSECAV() {
                if (boeexpensecav == null) {
                    boeexpensecav = new ArrayList<BOEEXPENSECAV>();
                }
                return this.boeexpensecav;
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
             *         &lt;element name="boeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="boeTypeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="boeEmployeeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="approvalDeptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="srmNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="creditAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="paidAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="appliedPayAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="unpaidAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="appliedPayLineAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="invoiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="sourceSystemCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                "boeNum",
                "boeTypeNum",
                "boeEmployeeNum",
                "approvalDeptNum",
                "srmNum",
                "currencyCode",
                "creditAmount",
                "paidAmount",
                "appliedPayAmount",
                "unpaidAmount",
                "appliedPayLineAmount",
                "invoiceID",
                "sourceSystemCode",
                "reservedField1",
                "reservedField2",
                "reservedField3",
                "reservedField4",
                "reservedField5"
            })
            public static class BOEEXPENSECAV {

                protected String boeNum;
                protected String boeTypeNum;
                protected String boeEmployeeNum;
                protected String approvalDeptNum;
                protected String srmNum;
                protected String currencyCode;
                protected BigDecimal creditAmount;
                protected BigDecimal paidAmount;
                protected BigDecimal appliedPayAmount;
                protected BigDecimal unpaidAmount;
                protected BigDecimal appliedPayLineAmount;
                protected String invoiceID;
                protected String sourceSystemCode;
                protected String reservedField1;
                protected String reservedField2;
                protected String reservedField3;
                protected String reservedField4;
                protected String reservedField5;

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
         *                   &lt;element name="boeTypeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="boeDeptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="expenseAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="rateValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="stdCurrencyAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="expenseDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
             * {@link PayApplyRequest.BOEINFO.BOEEXPENSES.BOEEXPENSE }
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
             *         &lt;element name="boeTypeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="boeDeptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="expenseAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="rateValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="stdCurrencyAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="expenseDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                "boeTypeNum",
                "boeDeptNum",
                "currencyCode",
                "expenseAmount",
                "rateValue",
                "stdCurrencyAmount",
                "description",
                "expenseDate",
                "reservedField1",
                "reservedField2",
                "reservedField3",
                "reservedField4",
                "reservedField5"
            })
            public static class BOEEXPENSE {

                protected String boeTypeNum;
                protected String boeDeptNum;
                protected String currencyCode;
                protected BigDecimal expenseAmount;
                protected BigDecimal rateValue;
                protected BigDecimal stdCurrencyAmount;
                protected String description;
                protected String expenseDate;
                protected String reservedField1;
                protected String reservedField2;
                protected String reservedField3;
                protected String reservedField4;
                protected String reservedField5;

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
         *         &lt;element name="employeeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="boeEmployeeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="approvalDeptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="financeDeptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="boeDeptNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="boeLeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="boeTypeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="boeVendorNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="bpCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *         &lt;element name="boeDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="boeAbstract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="reservedField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="srmNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="applyAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="reservedField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="reservedField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="reservedField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="reservedField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="relationCompanyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="relationEmployeeNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "currencyCode",
            "bpCount",
            "boeDate",
            "boeAbstract",
            "reservedField1",
            "srmNum",
            "applyAmount",
            "reservedField2",
            "reservedField3",
            "reservedField4",
            "reservedField5",
            "relationCompanyCode",
            "relationEmployeeNum"
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
            protected String currencyCode;
            protected Integer bpCount;
            protected String boeDate;
            protected String boeAbstract;
            protected String reservedField1;
            protected String srmNum;
            protected BigDecimal applyAmount;
            protected String reservedField2;
            protected String reservedField3;
            protected String reservedField4;
            protected String reservedField5;
            protected String relationCompanyCode;
            protected String relationEmployeeNum;

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
             * 获取applyAmount属性的值。
             *
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *
             */
            public BigDecimal getApplyAmount() {
                return applyAmount;
            }

            /**
             * 设置applyAmount属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *
             */
            public void setApplyAmount(BigDecimal value) {
                this.applyAmount = value;
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
             * 获取relationCompanyCode属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getRelationCompanyCode() {
                return relationCompanyCode;
            }

            /**
             * 设置relationCompanyCode属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setRelationCompanyCode(String value) {
                this.relationCompanyCode = value;
            }

            /**
             * 获取relationEmployeeNum属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getRelationEmployeeNum() {
                return relationEmployeeNum;
            }

            /**
             * 设置relationEmployeeNum属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setRelationEmployeeNum(String value) {
                this.relationEmployeeNum = value;
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
             * {@link PayApplyRequest.BOEINFO.BOEPAYMENTS.BOEPAYMENT }
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

    }

}
