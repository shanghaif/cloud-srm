
package com.midea.cloud.srm.model.pm.soap.order;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pesbinforec",
    "ppoinfotbl"
})
@XmlRootElement(name = "OrderChangeInputParameters")
public class OrderChangeInputParameters {

    @XmlElement(name = "P_ESB_INFO_REC", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz", required = false)
    protected APPSCUXPOORDERSMOX1362358X4X1 pesbinforec;
    @XmlElement(name = "P_PO_INFO_TBL", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz", required = false)
    protected APPSCUXPOORDERSMOX1362358X4X7 ppoinfotbl;

    /**
     * 获取pesbinforec属性的值。
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link APPSCUXPOORDERSMOX1362358X4X1 }{@code >}
     *
     */
    public APPSCUXPOORDERSMOX1362358X4X1 getPESBINFOREC() {
        return pesbinforec;
    }

    /**
     * 设置pesbinforec属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link APPSCUXPOORDERSMOX1362358X4X1 }{@code >}
     *
     */
    public void setPESBINFOREC(APPSCUXPOORDERSMOX1362358X4X1 value) {
        this.pesbinforec = value;
    }

    /**
     * 获取ppoinfotbl属性的值。
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link APPSCUXPOORDERSMOX1362358X4X7 }{@code >}
     *
     */
    public APPSCUXPOORDERSMOX1362358X4X7 getPPOINFOTBL() {
        return ppoinfotbl;
    }

    /**
     * 设置ppoinfotbl属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link APPSCUXPOORDERSMOX1362358X4X7 }{@code >}
     *     
     */
    public void setPPOINFOTBL(APPSCUXPOORDERSMOX1362358X4X7 value) {
        this.ppoinfotbl = value;
    }

}
