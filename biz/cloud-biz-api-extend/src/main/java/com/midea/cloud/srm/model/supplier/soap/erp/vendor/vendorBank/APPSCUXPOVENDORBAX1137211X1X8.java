
package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorBank;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;


/**
 * <p>APPS.CUX_PO_VENDOR_BAX1137211X1X8 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_VENDOR_BAX1137211X1X8">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ERPVENDORID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="VENDORNUMBER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string40" minOccurs="0"/>
 *         &lt;element name="BANKCODE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string40" minOccurs="0"/>
 *         &lt;element name="BANKNAME" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="BRANCHBANKCODE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string40" minOccurs="0"/>
 *         &lt;element name="BRANCHBANKNAME" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="BANKACCOUNTNAME" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string120" minOccurs="0"/>
 *         &lt;element name="BANKACCOUNTNUMBER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string40" minOccurs="0"/>
 *         &lt;element name="CURRENCYCODE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string10" minOccurs="0"/>
 *         &lt;element name="PRIMARYBANKACCOUNT" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string10" minOccurs="0"/>
 *         &lt;element name="ENABLEDFLAG" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string10" minOccurs="0"/>
 *         &lt;element name="COMMENTS" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="SOURCESYSCODE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="SOURCELINEID" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string80" minOccurs="0"/>
 *         &lt;element name="ATTR1" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR2" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR3" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR4" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR5" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string240" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_VENDOR_BAX1137211X1X8", propOrder = {
    "erpvendorid",
    "vendornumber",
    "bankcode",
    "bankname",
    "branchbankcode",
    "branchbankname",
    "bankaccountname",
    "bankaccountnumber",
    "currencycode",
    "primarybankaccount",
    "enabledflag",
    "comments",
    "sourcesyscode",
    "sourcelineid",
    "attr1",
    "attr2",
    "attr3",
    "attr4",
    "attr5"
})
@Data
public class APPSCUXPOVENDORBAX1137211X1X8 {

    @XmlElement(name = "ERPVENDORID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected BigDecimal erpvendorid;
    @XmlElement(name = "VENDORNUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  vendornumber;
    @XmlElement(name = "BANKCODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  bankcode;
    @XmlElement(name = "BANKNAME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  bankname;
    @XmlElement(name = "BRANCHBANKCODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  branchbankcode;
    @XmlElement(name = "BRANCHBANKNAME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  branchbankname;
    @XmlElement(name = "BANKACCOUNTNAME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  bankaccountname;
    @XmlElement(name = "BANKACCOUNTNUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  bankaccountnumber;
    @XmlElement(name = "CURRENCYCODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  currencycode;
    @XmlElement(name = "PRIMARYBANKACCOUNT", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  primarybankaccount;
    @XmlElement(name = "ENABLEDFLAG", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  enabledflag;
    @XmlElement(name = "COMMENTS", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  comments;
    @XmlElement(name = "SOURCESYSCODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  sourcesyscode;
    @XmlElement(name = "SOURCELINEID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  sourcelineid;
    @XmlElement(name = "ATTR1", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  attr1;
    @XmlElement(name = "ATTR2", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  attr2;
    @XmlElement(name = "ATTR3", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  attr3;
    @XmlElement(name = "ATTR4", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  attr4;
    @XmlElement(name = "ATTR5", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  attr5;

}
