
package com.midea.cloud.srm.model.suppliercooperate.soap.erp.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

;


/**
 * <p>APPS.CUX_PO_RECEIVE_X1139141X1X12 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_RECEIVE_X1139141X1X12">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RECEIPTNUM" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string40" minOccurs="0"/>
 *         &lt;element name="RECEIPTLINENUM" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="INVOICEQTY" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="SOURCELINEID" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string80" minOccurs="0"/>
 *         &lt;element name="ATTR1" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR2" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR3" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR4" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR5" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string240" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_RECEIVE_X1139141X1X12", propOrder = {
    "receiptnum",
    "receiptlinenum",
    "invoiceqty",
    "sourcelineid",
    "attr1",
    "attr2",
    "attr3",
    "attr4",
    "attr5"
})
public class APPSCUXPORECEIVEX1139141X1X12 {

    @XmlElement(name = "RECEIPTNUM", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String receiptnum;
    @XmlElement(name = "RECEIPTLINENUM", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String receiptlinenum;
    @XmlElement(name = "INVOICEQTY", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected BigDecimal invoiceqty;
    @XmlElement(name = "SOURCELINEID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String sourcelineid;
    @XmlElement(name = "ATTR1", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String attr1;
    @XmlElement(name = "ATTR2", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String attr2;
    @XmlElement(name = "ATTR3", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String attr3;
    @XmlElement(name = "ATTR4", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String attr4;
    @XmlElement(name = "ATTR5", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String attr5;

    /**
     * 获取receiptnum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link  }{@code <}{@link String }{@code >}
     *     
     */
    public String getRECEIPTNUM() {
        return receiptnum;
    }

    /**
     * 设置receiptnum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link  }{@code <}{@link String }{@code >}
     *     
     */
    public void setRECEIPTNUM(String value) {
        this.receiptnum = value;
    }

    /**
     * 获取receiptlinenum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link  }{@code <}{@link String }{@code >}
     *     
     */
    public String getRECEIPTLINENUM() {
        return receiptlinenum;
    }

    /**
     * 设置receiptlinenum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link  }{@code <}{@link String }{@code >}
     *     
     */
    public void setRECEIPTLINENUM(String value) {
        this.receiptlinenum = value;
    }

    /**
     * 获取invoiceqty属性的值。
     * 
     * @return
     *     possible object is
     *     {@link  }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public BigDecimal getINVOICEQTY() {
        return invoiceqty;
    }

    /**
     * 设置invoiceqty属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link  }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setINVOICEQTY(BigDecimal value) {
        this.invoiceqty = value;
    }

    /**
     * 获取sourcelineid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link  }{@code <}{@link String }{@code >}
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
     *     {@link  }{@code <}{@link String }{@code >}
     *     
     */
    public void setSOURCELINEID(String value) {
        this.sourcelineid = value;
    }

    /**
     * 获取attr1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link  }{@code <}{@link String }{@code >}
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
     *     {@link  }{@code <}{@link String }{@code >}
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
     *     {@link  }{@code <}{@link String }{@code >}
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
     *     {@link  }{@code <}{@link String }{@code >}
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
     *     {@link  }{@code <}{@link String }{@code >}
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
     *     {@link  }{@code <}{@link String }{@code >}
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
     *     {@link  }{@code <}{@link String }{@code >}
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
     *     {@link  }{@code <}{@link String }{@code >}
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
     *     {@link  }{@code <}{@link String }{@code >}
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
     *     {@link  }{@code <}{@link String }{@code >}
     *     
     */
    public void setATTR5(String value) {
        this.attr5 = value;
    }

}
