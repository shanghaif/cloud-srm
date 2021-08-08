
package com.midea.cloud.srm.model.pm.soap.order;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;


/**
 * <p>APPS.CUX_PO_ORDERS_MX1362358X4X14 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_ORDERS_MX1362358X4X14">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="POLINENUMBER" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="LINESTATUS" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="QUANTITYRECEIVED" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="SOURCELINEID" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string80" minOccurs="0"/>
 *         &lt;element name="PROMISEDDATE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="LINEATTR1" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="LINEATTR2" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="LINEATTR3" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="LINEATTR4" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="LINEATTR5" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_ORDERS_MX1362358X4X14", propOrder = {
    "polinenumber",
    "linestatus",
    "quantityreceived",
    "sourcelineid",
    "promiseddate",
    "lineattr1",
    "lineattr2",
    "lineattr3",
    "lineattr4",
    "lineattr5"
})
public class APPSCUXPOORDERSMX1362358X4X14 {

    @XmlElement(name = "POLINENUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected BigDecimal polinenumber;
    @XmlElement(name = "LINESTATUS", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String linestatus;
    @XmlElement(name = "QUANTITYRECEIVED", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected BigDecimal quantityreceived;
    @XmlElement(name = "SOURCELINEID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String sourcelineid;
    @XmlElement(name = "PROMISEDDATE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String promiseddate;
    @XmlElement(name = "LINEATTR1", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String lineattr1;
    @XmlElement(name = "LINEATTR2", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String lineattr2;
    @XmlElement(name = "LINEATTR3", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String lineattr3;
    @XmlElement(name = "LINEATTR4", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String lineattr4;
    @XmlElement(name = "LINEATTR5", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected String lineattr5;

    /**
     * 获取polinenumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public BigDecimal getPOLINENUMBER() {
        return polinenumber;
    }

    /**
     * 设置polinenumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setPOLINENUMBER(BigDecimal value) {
        this.polinenumber = value;
    }

    /**
     * 获取linestatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getLINESTATUS() {
        return linestatus;
    }

    /**
     * 设置linestatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLINESTATUS(String value) {
        this.linestatus = value;
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
     * 获取promiseddate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getPROMISEDDATE() {
        return promiseddate;
    }

    /**
     * 设置promiseddate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPROMISEDDATE(String value) {
        this.promiseddate = value;
    }

    /**
     * 获取lineattr1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getLINEATTR1() {
        return lineattr1;
    }

    /**
     * 设置lineattr1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLINEATTR1(String value) {
        this.lineattr1 = value;
    }

    /**
     * 获取lineattr2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getLINEATTR2() {
        return lineattr2;
    }

    /**
     * 设置lineattr2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLINEATTR2(String value) {
        this.lineattr2 = value;
    }

    /**
     * 获取lineattr3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getLINEATTR3() {
        return lineattr3;
    }

    /**
     * 设置lineattr3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLINEATTR3(String value) {
        this.lineattr3 = value;
    }

    /**
     * 获取lineattr4属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getLINEATTR4() {
        return lineattr4;
    }

    /**
     * 设置lineattr4属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLINEATTR4(String value) {
        this.lineattr4 = value;
    }

    /**
     * 获取lineattr5属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getLINEATTR5() {
        return lineattr5;
    }

    /**
     * 设置lineattr5属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLINEATTR5(String value) {
        this.lineattr5 = value;
    }

}
