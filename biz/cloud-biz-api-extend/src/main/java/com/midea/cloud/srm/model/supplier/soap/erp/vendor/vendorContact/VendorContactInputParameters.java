
package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorContact;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pesbinforec",
    "pvendorcontacttbl"
})
@XmlRootElement(name = "InputParameters")
public class VendorContactInputParameters {

    @XmlElement(name = "P_ESB_INFO_REC", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected APPSCUXPOVENDORCOX1137209X1X1 pesbinforec;
    @XmlElement(name = "P_VENDOR_CONTACT_TBL", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected APPSCUXPOVENDORCOX1137209X1X7 pvendorcontacttbl;

    public APPSCUXPOVENDORCOX1137209X1X1 getPesbinforec() {
        return pesbinforec;
    }

    public void setPesbinforec(APPSCUXPOVENDORCOX1137209X1X1 value) {
        this.pesbinforec = value;
    }

    public APPSCUXPOVENDORCOX1137209X1X7 getPvendorcontacttbl() {
        return pvendorcontacttbl;
    }

    public void setPvendorcontacttbl(APPSCUXPOVENDORCOX1137209X1X7 value) {
        this.pvendorcontacttbl = value;
    }

}
