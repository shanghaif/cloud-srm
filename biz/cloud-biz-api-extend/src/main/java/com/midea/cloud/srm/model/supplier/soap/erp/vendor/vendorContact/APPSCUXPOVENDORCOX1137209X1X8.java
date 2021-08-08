
package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorContact;

import lombok.Data;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


/**
 * <p>APPS.CUX_PO_VENDOR_COX1137209X1X8 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_VENDOR_COX1137209X1X8">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ERPVENDORID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="VENDORNUMBER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string40" minOccurs="0"/>
 *         &lt;element name="CONTRACTORNAME" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string100" minOccurs="0"/>
 *         &lt;element name="PHONNUMBER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="EMAILADDRESS" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string40" minOccurs="0"/>
 *         &lt;element name="DISABLEDATE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="SOURCESYSCODE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="SOURCELINEID" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string80" minOccurs="0"/>
 *         &lt;element name="ATTR1" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR2" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR3" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR4" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR5" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}string240" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_VENDOR_COX1137209X1X8", propOrder = {
    "erpvendorid",
    "vendornumber",
    "contractorname",
    "phonnumber",
    "emailaddress",
    "disabledate",
    "sourcesyscode",
    "sourcelineid",
    "attr1",
    "attr2",
    "attr3",
    "attr4",
    "attr5"
})
@Data
public class APPSCUXPOVENDORCOX1137209X1X8 {

    @XmlElement(name = "ERPVENDORID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected BigDecimal erpvendorid;
    @XmlElement(name = "VENDORNUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  vendornumber;
    @XmlElement(name = "CONTRACTORNAME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  contractorname;
    @XmlElement(name = "PHONNUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  phonnumber;
    @XmlElement(name = "EMAILADDRESS", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  emailaddress;
    @XmlElement(name = "DISABLEDATE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  disabledate;
    @XmlElement(name = "SOURCESYSCODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  sourcesyscode;
    @XmlElement(name = "SOURCELINEID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  sourcelineid;
    @XmlElement(name = "ATTR1", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  attr1;
    @XmlElement(name = "ATTR2", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  attr2;
    @XmlElement(name = "ATTR3", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  attr3;
    @XmlElement(name = "ATTR4", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  attr4;
    @XmlElement(name = "ATTR5", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected String  attr5;

}
