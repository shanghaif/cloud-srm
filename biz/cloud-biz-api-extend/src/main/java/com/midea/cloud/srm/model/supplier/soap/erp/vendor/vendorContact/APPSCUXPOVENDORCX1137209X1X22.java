
package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorContact;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>APPS.CUX_PO_VENDOR_CX1137209X1X22 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_VENDOR_CX1137209X1X22">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="INSTID" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string100" minOccurs="0"/>
 *         &lt;element name="RETURNSTATUS" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="RETURNCODE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="RETURNMSG" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string2400" minOccurs="0"/>
 *         &lt;element name="REQUESTTIME" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string60" minOccurs="0"/>
 *         &lt;element name="RESPONSETIME" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string60" minOccurs="0"/>
 *         &lt;element name="ATTR1" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR2" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR3" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string240" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_VENDOR_CX1137209X1X22", propOrder = {
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
public class APPSCUXPOVENDORCX1137209X1X22 {

    @XmlElement(name = "INSTID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  instid;
    @XmlElement(name = "RETURNSTATUS", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  returnstatus;
    @XmlElement(name = "RETURNCODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  returncode;
    @XmlElement(name = "RETURNMSG", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  returnmsg;
    @XmlElement(name = "REQUESTTIME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  requesttime;
    @XmlElement(name = "RESPONSETIME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  responsetime;
    @XmlElement(name = "ATTR1", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  attr1;
    @XmlElement(name = "ATTR2", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  attr2;
    @XmlElement(name = "ATTR3", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  attr3;

}
