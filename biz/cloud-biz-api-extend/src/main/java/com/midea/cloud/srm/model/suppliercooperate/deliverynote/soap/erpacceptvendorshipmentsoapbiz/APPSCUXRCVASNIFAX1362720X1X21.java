
package com.midea.cloud.srm.model.suppliercooperate.deliverynote.soap.erpacceptvendorshipmentsoapbiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>APPS.CUX_RCV_ASN_IFAX1362720X1X21 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_RCV_ASN_IFAX1362720X1X21">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SOURCELINEID" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}string80" minOccurs="0"/>
 *         &lt;element name="PONUMBER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}string40" minOccurs="0"/>
 *         &lt;element name="POLINENUMBER" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="SHIPTOORGANIZATIONID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="LINESTATUS" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="ITEMNUMBER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}string60" minOccurs="0"/>
 *         &lt;element name="QUANTITY" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="UNITOFMEASURE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="ATTR1" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR2" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR3" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR4" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR5" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="LOTLIST" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}APPS.CUX_RCV_ASN_IFAX1362720X1X35" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_RCV_ASN_IFAX1362720X1X21", propOrder = {
    "sourcelineid",
    "ponumber",
    "polinenumber",
    "shiptoorganizationid",
    "linestatus",
    "itemnumber",
    "quantity",
    "unitofmeasure",
    "attr1",
    "attr2",
    "attr3",
    "attr4",
    "attr5",
    "lotlist"
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class APPSCUXRCVASNIFAX1362720X1X21 {

    @XmlElement(name = "SOURCELINEID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected String sourcelineid;
    @XmlElement(name = "PONUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected String ponumber;
    @XmlElement(name = "POLINENUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected BigDecimal polinenumber;
    @XmlElement(name = "SHIPTOORGANIZATIONID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected BigDecimal shiptoorganizationid;
    @XmlElement(name = "LINESTATUS", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected String linestatus;
    @XmlElement(name = "ITEMNUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected  String itemnumber;
    @XmlElement(name = "QUANTITY", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected  BigDecimal quantity;
    @XmlElement(name = "UNITOFMEASURE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected  String unitofmeasure;
    @XmlElement(name = "ATTR1", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected  String attr1;
    @XmlElement(name = "ATTR2", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected  String attr2;
    @XmlElement(name = "ATTR3", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected  String attr3;
    @XmlElement(name = "ATTR4", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected  String attr4;
    @XmlElement(name = "ATTR5", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected  String attr5;
    @XmlElement(name = "LOTLIST", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz",  required = false)
    protected  APPSCUXRCVASNIFAX1362720X1X35 lotlist;


}
