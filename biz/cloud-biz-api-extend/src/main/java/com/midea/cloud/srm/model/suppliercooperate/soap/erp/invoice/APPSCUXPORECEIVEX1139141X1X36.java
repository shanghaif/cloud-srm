
package com.midea.cloud.srm.model.suppliercooperate.soap.erp.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

;


/**
 * <p>APPS.CUX_PO_RECEIVE_X1139141X1X36 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_RECEIVE_X1139141X1X36">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RECEIPTNUM" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string40" minOccurs="0"/>
 *         &lt;element name="RECEIPTLINENUM" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="ERRORMSG" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string240" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_RECEIVE_X1139141X1X36", propOrder = {
    "receiptnum",
    "receiptlinenum",
    "errormsg"
})
public class APPSCUXPORECEIVEX1139141X1X36 {

    @XmlElement(name = "RECEIPTNUM", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String receiptnum;
    @XmlElement(name = "RECEIPTLINENUM", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String receiptlinenum;
    @XmlElement(name = "ERRORMSG", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String errormsg;

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
     * 获取errormsg属性的值。
     * 
     * @return
     *     possible object is
     *     {@link  }{@code <}{@link String }{@code >}
     *     
     */
    public String getERRORMSG() {
        return errormsg;
    }

    /**
     * 设置errormsg属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link  }{@code <}{@link String }{@code >}
     *     
     */
    public void setERRORMSG(String value) {
        this.errormsg = value;
    }

}
