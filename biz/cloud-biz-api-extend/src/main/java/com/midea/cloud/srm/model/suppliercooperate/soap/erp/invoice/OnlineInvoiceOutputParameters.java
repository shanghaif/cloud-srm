
package com.midea.cloud.srm.model.suppliercooperate.soap.erp.invoice;

import javax.xml.bind.annotation.*;

;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="X_ESB_RESULT_INFO_REC" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}APPS.CUX_PO_RECEIVE_X1139141X1X25" minOccurs="0"/>
 *         &lt;element name="X_RESULT_INFO" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}APPS.CUX_PO_RECEIVE_X1139141X1X35" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "xesbresultinforec",
    "xresultinfo"
})
@XmlRootElement(name = "OutputParameters")
public class OnlineInvoiceOutputParameters {

    @XmlElement(name = "X_ESB_RESULT_INFO_REC", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected APPSCUXPORECEIVEX1139141X1X25 xesbresultinforec;
    @XmlElement(name = "X_RESULT_INFO", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected APPSCUXPORECEIVEX1139141X1X35 xresultinfo;

    /**
     * 获取xesbresultinforec属性的值。
     * 
     * @return
     *     possible object is
     *     {@link  }{@code <}{@link APPSCUXPORECEIVEX1139141X1X25 }{@code >}
     *     
     */
    public APPSCUXPORECEIVEX1139141X1X25 getXESBRESULTINFOREC() {
        return xesbresultinforec;
    }

    /**
     * 设置xesbresultinforec属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link  }{@code <}{@link APPSCUXPORECEIVEX1139141X1X25 }{@code >}
     *     
     */
    public void setXESBRESULTINFOREC(APPSCUXPORECEIVEX1139141X1X25 value) {
        this.xesbresultinforec = value;
    }

    /**
     * 获取xresultinfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link  }{@code <}{@link APPSCUXPORECEIVEX1139141X1X35 }{@code >}
     *     
     */
    public APPSCUXPORECEIVEX1139141X1X35 getXRESULTINFO() {
        return xresultinfo;
    }

    /**
     * 设置xresultinfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link  }{@code <}{@link APPSCUXPORECEIVEX1139141X1X35 }{@code >}
     *     
     */
    public void setXRESULTINFO(APPSCUXPORECEIVEX1139141X1X35 value) {
        this.xresultinfo = value;
    }

}
