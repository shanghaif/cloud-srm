
package com.midea.cloud.srm.model.pm.soap.order;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;


/**
 * <p>APPS.CUX_PO_ORDERS_MX1362358X4X45 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_ORDERS_MX1362358X4X45">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SOURCESYSCODE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="SOURCEHEADERID" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="SOURCELINEID" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="QUANTITYRECEIVED" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_ORDERS_MX1362358X4X45", propOrder = {
    "sourcesyscode",
    "sourceheaderid",
    "sourcelineid",
    "quantityreceived"
})
public class APPSCUXPOORDERSMX1362358X4X45 {

     @XmlElement(name = "SOURCESYSCODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String sourcesyscode;
     @XmlElement(name = "SOURCEHEADERID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String sourceheaderid;
     @XmlElement(name = "SOURCELINEID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String sourcelineid;
     @XmlElement(name = "QUANTITYRECEIVED", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected BigDecimal quantityreceived;

    /**
     * 获取sourcesyscode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getSOURCESYSCODE() {
        return sourcesyscode;
    }

    /**
     * 设置sourcesyscode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSOURCESYSCODE(String value) {
        this.sourcesyscode = value;
    }

    /**
     * 获取sourceheaderid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getSOURCEHEADERID() {
        return sourceheaderid;
    }

    /**
     * 设置sourceheaderid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSOURCEHEADERID(String value) {
        this.sourceheaderid = value;
    }

    /**
     * 获取sourcelineid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getSOURCELINEID() {
        return sourcelineid;
    }

    /**
     * 设置sourcelineid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSOURCELINEID(String value) {
        this.sourcelineid = value;
    }

    /**
     * 获取quantityreceived属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public BigDecimal getQUANTITYRECEIVED() {
        return quantityreceived;
    }

    /**
     * 设置quantityreceived属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setQUANTITYRECEIVED(BigDecimal value) {
        this.quantityreceived = value;
    }

}
