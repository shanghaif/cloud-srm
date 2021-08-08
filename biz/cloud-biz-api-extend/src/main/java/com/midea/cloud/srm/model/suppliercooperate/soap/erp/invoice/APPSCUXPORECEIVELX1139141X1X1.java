
package com.midea.cloud.srm.model.suppliercooperate.soap.erp.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

;


/**
 * <p>APPS.CUX_PO_RECEIVE_LX1139141X1X1 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_RECEIVE_LX1139141X1X1">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="INSTID" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string100" minOccurs="0"/>
 *         &lt;element name="REQUESTTIME" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="ATTR1" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR2" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR3" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string240" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_RECEIVE_LX1139141X1X1", propOrder = {
    "instid",
    "requesttime",
    "attr1",
    "attr2",
    "attr3"
})
public class APPSCUXPORECEIVELX1139141X1X1 {

    @XmlElement(name = "INSTID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String instid;
    @XmlElement(name = "REQUESTTIME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String requesttime;
    @XmlElement(name = "ATTR1", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String attr1;
    @XmlElement(name = "ATTR2", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String attr2;
    @XmlElement(name = "ATTR3", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String attr3;

    /**
     * 获取instid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link  }{@code <}{@link String }{@code >}
     *     
     */
    public String getINSTID() {
        return instid;
    }

    /**
     * 设置instid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link  }{@code <}{@link String }{@code >}
     *     
     */
    public void setINSTID(String value) {
        this.instid = value;
    }

    /**
     * 获取requesttime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link  }{@code <}{@link String }{@code >}
     *     
     */
    public String getREQUESTTIME() {
        return requesttime;
    }

    /**
     * 设置requesttime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link  }{@code <}{@link String }{@code >}
     *     
     */
    public void setREQUESTTIME(String value) {
        this.requesttime = value;
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

}
