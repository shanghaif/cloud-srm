
package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorInfo;

import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pesbinforec",
    "pvendortbl"
})
@XmlRootElement(name = "InputParameters")
public class VendorInfoInputParameters {

    @XmlElement(name = "P_ESB_INFO_REC", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", required = false)
    protected APPSCUXPOVENDORSYX1137205X1X1 pesbinforec;
    @XmlElement(name = "P_VENDOR_TBL", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", required = false)
    protected APPSCUXPOVENDORSYX1137205X1X7 pvendortbl;


    public APPSCUXPOVENDORSYX1137205X1X1 getPesbinforec() {
        return pesbinforec;
    }

    public void setPesbinforec(APPSCUXPOVENDORSYX1137205X1X1 value) {
        this.pesbinforec = value;
    }

    public APPSCUXPOVENDORSYX1137205X1X7 getPvendortbl() {
        return pvendortbl;
    }

    public void setPvendortbl(APPSCUXPOVENDORSYX1137205X1X7 value) {
        this.pvendortbl = value;
    }

}
