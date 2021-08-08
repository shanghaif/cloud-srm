
package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorBank;

import lombok.Data;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


/**
 * <p>APPS.CUX_PO_VENDOR_BX1137211X1X28 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_VENDOR_BX1137211X1X28">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="INSTID" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string100" minOccurs="0"/>
 *         &lt;element name="RETURNSTATUS" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="RETURNCODE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="RETURNMSG" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string2400" minOccurs="0"/>
 *         &lt;element name="REQUESTTIME" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string60" minOccurs="0"/>
 *         &lt;element name="RESPONSETIME" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string60" minOccurs="0"/>
 *         &lt;element name="ATTR1" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR2" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR3" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}string240" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_VENDOR_BX1137211X1X28", propOrder = {
    "instid",
    "returnstatus",
    "returncode",
    "returnmsg",
    "requesttime",
    "responsetime",
    "attr1",
    "attr2",
    "attr3"
})
@Data
public class APPSCUXPOVENDORBX1137211X1X28 {

    @XmlElement(name = "INSTID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  instid;
    @XmlElement(name = "RETURNSTATUS", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  returnstatus;
    @XmlElement(name = "RETURNCODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  returncode;
    @XmlElement(name = "RETURNMSG", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  returnmsg;
    @XmlElement(name = "REQUESTTIME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  requesttime;
    @XmlElement(name = "RESPONSETIME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  responsetime;
    @XmlElement(name = "ATTR1", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  attr1;
    @XmlElement(name = "ATTR2", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  attr2;
    @XmlElement(name = "ATTR3", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected String  attr3;

}
