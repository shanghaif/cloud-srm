package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorSite;

import lombok.Data;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pesbinforec",
    "pvendorsitetbl"
})
@XmlRootElement(name = "InputParameters")
public class VendorSiteInputParameters {

    @XmlElement(name = "P_ESB_INFO_REC", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected APPSCUXPOVENDORSIX1137207X1X1 pesbinforec;
    @XmlElement(name = "P_VENDOR_SITE_TBL", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected APPSCUXPOVENDORSIX1137207X1X7 pvendorsitetbl;
    
    
    public APPSCUXPOVENDORSIX1137207X1X1 getPesbinforec() {
        return pesbinforec;
    }
    
    public void setPesbinforec(APPSCUXPOVENDORSIX1137207X1X1 value) {
        this.pesbinforec = value;
    }
    
    public APPSCUXPOVENDORSIX1137207X1X7 getPvendorsitetbl() {
        return pvendorsitetbl;
    }
    
    public void setPvendorsitetbl(APPSCUXPOVENDORSIX1137207X1X7 value) {
        this.pvendorsitetbl = value;
    }

}
