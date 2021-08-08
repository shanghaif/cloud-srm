
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
 *         &lt;element name="P_ESB_INFO_REC" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}APPS.CUX_PO_RECEIVE_LX1139141X1X1" minOccurs="0"/>
 *         &lt;element name="P_PO_INFO_TBL" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}APPS.CUX_PO_RECEIVE_LX1139141X1X7" minOccurs="0"/>
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
    "pesbinforec",
    "ppoinfotbl"
})
@XmlRootElement(name = "InputParameters")
public class OnlineInvoiceInputParameters {

    @XmlElement(name = "P_ESB_INFO_REC", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected APPSCUXPORECEIVELX1139141X1X1 pesbinforec;
    @XmlElement(name = "P_PO_INFO_TBL", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false)
    protected APPSCUXPORECEIVELX1139141X1X7 ppoinfotbl;

    /**
     * 获取pesbinforec属性的值。
     * 
     * @return
     *     possible object is
     *     {@link  }{@code <}{@link APPSCUXPORECEIVELX1139141X1X1 }{@code >}
     *     
     */
    public APPSCUXPORECEIVELX1139141X1X1 getPESBINFOREC() {
        return pesbinforec;
    }

    /**
     * 设置pesbinforec属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link  }{@code <}{@link APPSCUXPORECEIVELX1139141X1X1 }{@code >}
     *     
     */
    public void setPESBINFOREC(APPSCUXPORECEIVELX1139141X1X1 value) {
        this.pesbinforec = value;
    }

    /**
     * 获取ppoinfotbl属性的值。
     * 
     * @return
     *     possible object is
     *     {@link  }{@code <}{@link APPSCUXPORECEIVELX1139141X1X7 }{@code >}
     *     
     */
    public APPSCUXPORECEIVELX1139141X1X7 getPPOINFOTBL() {
        return ppoinfotbl;
    }

    /**
     * 设置ppoinfotbl属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link  }{@code <}{@link APPSCUXPORECEIVELX1139141X1X7 }{@code >}
     *     
     */
    public void setPPOINFOTBL(APPSCUXPORECEIVELX1139141X1X7 value) {
        this.ppoinfotbl = value;
    }

}
