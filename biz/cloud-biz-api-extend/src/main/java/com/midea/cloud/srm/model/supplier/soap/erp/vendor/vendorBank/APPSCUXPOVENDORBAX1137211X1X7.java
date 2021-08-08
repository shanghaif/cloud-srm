
package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorBank;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>APPS.CUX_PO_VENDOR_BAX1137211X1X7 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_VENDOR_BAX1137211X1X7">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="P_VENDOR_BANK_TBL_ITEM" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorBankSoapBiz}APPS.CUX_PO_VENDOR_BAX1137211X1X8" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_VENDOR_BAX1137211X1X7", propOrder = {
    "pvendorbanktblitem"
})
public class APPSCUXPOVENDORBAX1137211X1X7 {

    @XmlElement(name = "P_VENDOR_BANK_TBL_ITEM", nillable = true)
    protected List<APPSCUXPOVENDORBAX1137211X1X8> pvendorbanktblitem;

    /**
     * Gets the value of the pvendorbanktblitem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pvendorbanktblitem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPVENDORBANKTBLITEM().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link APPSCUXPOVENDORBAX1137211X1X8 }
     * 
     * 
     */
    public List<APPSCUXPOVENDORBAX1137211X1X8> getPVENDORBANKTBLITEM() {
        if (pvendorbanktblitem == null) {
            pvendorbanktblitem = new ArrayList<APPSCUXPOVENDORBAX1137211X1X8>();
        }
        return this.pvendorbanktblitem;
    }
    public void setPVENDORBANKTBLITEM(List<APPSCUXPOVENDORBAX1137211X1X8> pvendorbanktblitem) {
        this.pvendorbanktblitem = pvendorbanktblitem;
    }


}
