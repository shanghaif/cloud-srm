
package com.midea.cloud.srm.model.pm.soap.order;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;


/**
 * <p>APPS.CUX_PO_ORDERS_MOX1362358X4X8 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_ORDERS_MOX1362358X4X8">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OPERATIONUNITID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="OPERATIONNAME" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="DOCUMENTTYPE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="PONUMBER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string40" minOccurs="0"/>
 *         &lt;element name="LINEDETAILS" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}APPS.CUX_PO_ORDERS_MX1362358X4X13" minOccurs="0"/>
 *         &lt;element name="SOURCESYSCODE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="SOURCEHEADERID" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string80" minOccurs="0"/>
 *         &lt;element name="IFACECODE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string120" minOccurs="0"/>
 *         &lt;element name="IFACEMEAN" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR1" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR2" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR3" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR4" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR5" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_ORDERS_MOX1362358X4X8", propOrder = {
    "operationunitid",
    "operationname",
    "documenttype",
    "ponumber",
    "linedetails",
    "sourcesyscode",
    "sourceheaderid",
    "ifacecode",
    "ifacemean",
    "attr1",
    "attr2",
    "attr3",
    "attr4",
    "attr5"
})
public class APPSCUXPOORDERSMOX1362358X4X8 {

    @XmlElement(name = "OPERATIONUNITID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected BigDecimal operationunitid;
    @XmlElement(name = "OPERATIONNAME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String operationname;
    @XmlElement(name = "DOCUMENTTYPE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String documenttype;
    @XmlElement(name = "PONUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String ponumber;
    @XmlElement(name = "LINEDETAILS", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected APPSCUXPOORDERSMX1362358X4X13 linedetails;
    @XmlElement(name = "SOURCESYSCODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String sourcesyscode;
    @XmlElement(name = "SOURCEHEADERID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String sourceheaderid;
    @XmlElement(name = "IFACECODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String ifacecode;
    @XmlElement(name = "IFACEMEAN", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String ifacemean;
    @XmlElement(name = "ATTR1", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String attr1;
    @XmlElement(name = "ATTR2", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String attr2;
    @XmlElement(name = "ATTR3", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String attr3;
    @XmlElement(name = "ATTR4", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String attr4;
    @XmlElement(name = "ATTR5", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String attr5;

    /**
     * 获取operationunitid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public BigDecimal getOPERATIONUNITID() {
        return operationunitid;
    }

    /**
     * 设置operationunitid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setOPERATIONUNITID(BigDecimal value) {
        this.operationunitid = value;
    }

    /**
     * 获取operationname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getOPERATIONNAME() {
        return operationname;
    }

    /**
     * 设置operationname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOPERATIONNAME(String value) {
        this.operationname = value;
    }

    /**
     * 获取documenttype属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getDOCUMENTTYPE() {
        return documenttype;
    }

    /**
     * 设置documenttype属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDOCUMENTTYPE(String value) {
        this.documenttype = value;
    }

    /**
     * 获取ponumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getPONUMBER() {
        return ponumber;
    }

    /**
     * 设置ponumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPONUMBER(String value) {
        this.ponumber = value;
    }

    /**
     * 获取linedetails属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link APPSCUXPOORDERSMX1362358X4X13 }{@code >}
     *     
     */
    public APPSCUXPOORDERSMX1362358X4X13 getLINEDETAILS() {
        return linedetails;
    }

    /**
     * 设置linedetails属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link APPSCUXPOORDERSMX1362358X4X13 }{@code >}
     *     
     */
    public void setLINEDETAILS(APPSCUXPOORDERSMX1362358X4X13 value) {
        this.linedetails = value;
    }

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
     * 获取ifacecode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getIFACECODE() {
        return ifacecode;
    }

    /**
     * 设置ifacecode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIFACECODE(String value) {
        this.ifacecode = value;
    }

    /**
     * 获取ifacemean属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getIFACEMEAN() {
        return ifacemean;
    }

    /**
     * 设置ifacemean属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIFACEMEAN(String value) {
        this.ifacemean = value;
    }

    /**
     * 获取attr1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getATTR1() {
        return attr1;
    }

    /**
     * 设置attr1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setATTR1(String value) {
        this.attr1 = value;
    }

    /**
     * 获取attr2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getATTR2() {
        return attr2;
    }

    /**
     * 设置attr2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setATTR2(String value) {
        this.attr2 = value;
    }

    /**
     * 获取attr3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getATTR3() {
        return attr3;
    }

    /**
     * 设置attr3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setATTR3(String value) {
        this.attr3 = value;
    }

    /**
     * 获取attr4属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getATTR4() {
        return attr4;
    }

    /**
     * 设置attr4属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setATTR4(String value) {
        this.attr4 = value;
    }

    /**
     * 获取attr5属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getATTR5() {
        return attr5;
    }

    /**
     * 设置attr5属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setATTR5(String value) {
        this.attr5 = value;
    }

}
