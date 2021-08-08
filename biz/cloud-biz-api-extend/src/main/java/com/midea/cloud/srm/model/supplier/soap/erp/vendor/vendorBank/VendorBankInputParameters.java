
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
 *         &lt;element name="P_ESB_INFO_REC" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}APPS.CUX_PO_VENDOR_BAX1137211X1X1" minOccurs="0"/>
 *         &lt;element name="P_VENDOR_BANK_TBL" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}APPS.CUX_PO_VENDOR_BAX1137211X1X7" minOccurs="0"/>
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
    "pvendorbanktbl"
})
@XmlRootElement(name = "InputParameters")
public class VendorBankInputParameters {

    @XmlElement(name = "P_ESB_INFO_REC", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected APPSCUXPOVENDORBAX1137211X1X1 pesbinforec;
    @XmlElement(name = "P_VENDOR_BANK_TBL", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz", required = false)
    protected APPSCUXPOVENDORBAX1137211X1X7 pvendorbanktbl;

    public APPSCUXPOVENDORBAX1137211X1X1 getPesbinforec() {
        return pesbinforec;
    }

    public void setPesbinforec(APPSCUXPOVENDORBAX1137211X1X1 value) {
        this.pesbinforec = value;
    }

    public APPSCUXPOVENDORBAX1137211X1X7 getPvendorbanktbl() {
        return pvendorbanktbl;
    }

    public void setPvendorbanktbl(APPSCUXPOVENDORBAX1137211X1X7 value) {
        this.pvendorbanktbl = value;
    }

}
