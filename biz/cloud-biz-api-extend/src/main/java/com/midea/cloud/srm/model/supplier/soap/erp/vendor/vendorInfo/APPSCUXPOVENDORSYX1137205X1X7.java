
package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_VENDOR_SYX1137205X1X7", propOrder = {
    "pvendortblitem"
})
public class APPSCUXPOVENDORSYX1137205X1X7 {

    @XmlElement(name = "P_VENDOR_TBL_ITEM", nillable = true)
    protected List<APPSCUXPOVENDORSYX1137205X1X8> pvendortblitem;

    public List<APPSCUXPOVENDORSYX1137205X1X8> getPVENDORTBLITEM() {
        if (pvendortblitem == null) {
            pvendortblitem = new ArrayList<APPSCUXPOVENDORSYX1137205X1X8>();
        }
        return this.pvendortblitem;
    }

    public void setPVENDORTBLITEM(List<APPSCUXPOVENDORSYX1137205X1X8> pvendortblitem) {
        this.pvendortblitem = pvendortblitem;
    }

}
