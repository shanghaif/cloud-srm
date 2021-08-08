
package com.midea.cloud.srm.model.suppliercooperate.soap.erp.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

;


/**
 * <p>APPS.CUX_PO_RECEIVE_LX1139141X1X8 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_RECEIVE_LX1139141X1X8">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="INVOICENUM" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string60" minOccurs="0"/>
 *         &lt;element name="OPERTYPE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="RCVDETAILS" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}APPS.CUX_PO_RECEIVE_X1139141X1X11" minOccurs="0"/>
 *         &lt;element name="SOURCESYSCODE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="IFACECODE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string120" minOccurs="0"/>
 *         &lt;element name="IFACEMEAN" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}string240" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_RECEIVE_LX1139141X1X8", propOrder = {
    "invoicenum",
    "opertype",
    "rcvdetails",
    "sourcesyscode",
    "ifacecode",
    "ifacemean"
})
public class APPSCUXPORECEIVELX1139141X1X8 {

    @XmlElement(name = "INVOICENUM", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String invoicenum;
    @XmlElement(name = "OPERTYPE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String opertype;
    @XmlElement(name = "RCVDETAILS", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected APPSCUXPORECEIVEX1139141X1X11 rcvdetails;
    @XmlElement(name = "SOURCESYSCODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String sourcesyscode;
    @XmlElement(name = "IFACECODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String ifacecode;
    @XmlElement(name = "IFACEMEAN", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected String ifacemean;

    /**
     * 获取invoicenum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link  }{@code <}{@link String }{@code >}
     *     
     */
    public String getINVOICENUM() {
        return invoicenum;
    }

    /**
     * 设置invoicenum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link  }{@code <}{@link String }{@code >}
     *     
     */
    public void setINVOICENUM(String value) {
        this.invoicenum = value;
    }

    /**
     * 获取opertype属性的值。
     * 
     * @return
     *     possible object is
     *     {@link  }{@code <}{@link String }{@code >}
     *     
     */
    public String getOPERTYPE() {
        return opertype;
    }

    /**
     * 设置opertype属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link  }{@code <}{@link String }{@code >}
     *     
     */
    public void setOPERTYPE(String value) {
        this.opertype = value;
    }

    /**
     * 获取rcvdetails属性的值。
     * 
     * @return
     *     possible object is
     *     {@link  }{@code <}{@link APPSCUXPORECEIVEX1139141X1X11 }{@code >}
     *     
     */
    public APPSCUXPORECEIVEX1139141X1X11 getRCVDETAILS() {
        return rcvdetails;
    }

    /**
     * 设置rcvdetails属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link  }{@code <}{@link APPSCUXPORECEIVEX1139141X1X11 }{@code >}
     *     
     */
    public void setRCVDETAILS(APPSCUXPORECEIVEX1139141X1X11 value) {
        this.rcvdetails = value;
    }

    /**
     * 获取sourcesyscode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link  }{@code <}{@link String }{@code >}
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
     *     {@link  }{@code <}{@link String }{@code >}
     *     
     */
    public void setSOURCESYSCODE(String value) {
        this.sourcesyscode = value;
    }

    /**
     * 获取ifacecode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link  }{@code <}{@link String }{@code >}
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
     *     {@link  }{@code <}{@link String }{@code >}
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
     *     {@link  }{@code <}{@link String }{@code >}
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
     *     {@link  }{@code <}{@link String }{@code >}
     *     
     */
    public void setIFACEMEAN(String value) {
        this.ifacemean = value;
    }

}
