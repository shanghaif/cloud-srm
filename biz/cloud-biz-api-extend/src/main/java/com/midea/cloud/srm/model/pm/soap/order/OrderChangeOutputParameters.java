
package com.midea.cloud.srm.model.pm.soap.order;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


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
 *         &lt;element name="X_ESB_RESULT_INFO_REC" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}APPS.CUX_PO_ORDERS_MX1362358X4X34" minOccurs="0"/>
 *         &lt;element name="X_RESULT_INFO" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}APPS.CUX_PO_ORDERS_MX1362358X4X44" minOccurs="0"/>
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
public class OrderChangeOutputParameters {

    @XmlElement(name = "X_ESB_RESULT_INFO_REC", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false)
    protected APPSCUXPOORDERSMX1362358X4X34 xesbresultinforec;
    @XmlElement(name = "X_RESULT_INFO", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",required = false)
    protected APPSCUXPOORDERSMX1362358X4X44 xresultinfo;

    /**
     * 获取xesbresultinforec属性的值。
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link APPSCUXPOORDERSMX1362358X4X34 }{@code >}
     *
     */
    public APPSCUXPOORDERSMX1362358X4X34 getXESBRESULTINFOREC() {
        return xesbresultinforec;
    }

    /**
     * 设置xesbresultinforec属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link APPSCUXPOORDERSMX1362358X4X34 }{@code >}
     *
     */
    public void setXESBRESULTINFOREC(APPSCUXPOORDERSMX1362358X4X34 value) {
        this.xesbresultinforec = value;
    }

    /**
     * 获取xresultinfo属性的值。
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link APPSCUXPOORDERSMX1362358X4X44 }{@code >}
     *
     */
    public APPSCUXPOORDERSMX1362358X4X44 getXRESULTINFO() {
        return xresultinfo;
    }

    /**
     * 设置xresultinfo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link APPSCUXPOORDERSMX1362358X4X44 }{@code >}
     *     
     */
    public void setXRESULTINFO(APPSCUXPOORDERSMX1362358X4X44 value) {
        this.xresultinfo = value;
    }

}
