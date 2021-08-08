
package com.midea.cloud.srm.model.suppliercooperate.deliverynote.soap.erpacceptvendorshipmentsoapbiz;

import lombok.Data;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>APPS.CUX_RCV_ASN_IFAX1362720X1X50 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_RCV_ASN_IFAX1362720X1X50">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SOURCESYSCODE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="SOURCEHEADERID" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}string80" minOccurs="0"/>
 *         &lt;element name="SOURCELINEID" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}string80" minOccurs="0"/>
 *         &lt;element name="SHIPMENTNUM" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="PONUMBER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}string40" minOccurs="0"/>
 *         &lt;element name="POLINENUMBER" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="PROCESSSTATUS" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}string3" minOccurs="0"/>
 *         &lt;element name="PROCESSMESSAGE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}string400" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_RCV_ASN_IFAX1362720X1X50", propOrder = {
    "sourcesyscode",
    "sourceheaderid",
    "sourcelineid",
    "shipmentnum",
    "ponumber",
    "polinenumber",
    "processstatus",
    "processmessage"
})
@Data
public class APPSCUXRCVASNIFAX1362720X1X50 {

    @XmlElement(name = "SOURCESYSCODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected String sourcesyscode;
    @XmlElement(name = "SOURCEHEADERID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected String sourceheaderid;
    @XmlElement(name = "SOURCELINEID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected String sourcelineid;
    @XmlElement(name = "SHIPMENTNUM", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected String shipmentnum;
    @XmlElement(name = "PONUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected String ponumber;
    @XmlElement(name = "POLINENUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected BigDecimal polinenumber;
    @XmlElement(name = "PROCESSSTATUS", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected String processstatus;
    @XmlElement(name = "PROCESSMESSAGE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected String processmessage;


}
