
package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorBank;

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
 *         &lt;element name="X_ESB_RESULT_INFO_REC" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}APPS.CUX_PO_VENDOR_BX1137211X1X28" minOccurs="0"/>
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
    "xesbresultinforec"
})
@XmlRootElement(name = "OutputParameters")
public class VendorBankOutputParameters {

    @XmlElement(name = "X_ESB_RESULT_INFO_REC", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected APPSCUXPOVENDORBX1137211X1X28 xesbresultinforec;

    /**
     * 获取xesbresultinforec属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link APPSCUXPOVENDORBX1137211X1X28 }{@code >}
     *     
     */
    public APPSCUXPOVENDORBX1137211X1X28 getXesbresultinforec() {
        return xesbresultinforec;
    }

    /**
     * 设置xesbresultinforec属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link APPSCUXPOVENDORBX1137211X1X28 }{@code >}
     *     
     */
    public void setXesbresultinforec(APPSCUXPOVENDORBX1137211X1X28 value) {
        this.xesbresultinforec = value;
    }

}
