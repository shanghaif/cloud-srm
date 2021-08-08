
package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorContact;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>APPS.CUX_PO_VENDOR_COX1137209X1X7 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_VENDOR_COX1137209X1X7">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="P_VENDOR_CONTACT_TBL_ITEM" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz}APPS.CUX_PO_VENDOR_COX1137209X1X8" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_VENDOR_COX1137209X1X7", propOrder = {
    "pvendorcontacttblitem"
})
public class APPSCUXPOVENDORCOX1137209X1X7 {

    @XmlElement(name = "P_VENDOR_CONTACT_TBL_ITEM", nillable = true)
    protected List<APPSCUXPOVENDORCOX1137209X1X8> pvendorcontacttblitem;

    public List<APPSCUXPOVENDORCOX1137209X1X8> getPVENDORCONTACTTBLITEM() {
        if (pvendorcontacttblitem == null) {
            pvendorcontacttblitem = new ArrayList<APPSCUXPOVENDORCOX1137209X1X8>();
        }
        return this.pvendorcontacttblitem;
    }

    public void setPVENDORCONTACTTBLITEM(List<APPSCUXPOVENDORCOX1137209X1X8> pvendorcontacttblitem) {
       this.pvendorcontacttblitem = pvendorcontacttblitem;
    }

}
