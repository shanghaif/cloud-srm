package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorSite;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "xesbresultinforec"
})
@XmlRootElement(name = "OutputParameters")
public class VendorSiteOutputParameters {

    @XmlElement(name = "X_ESB_RESULT_INFO_REC", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected APPSCUXPOVENDORSX1137207X1X31 xesbresultinforec;
    
    public APPSCUXPOVENDORSX1137207X1X31 getXesbresultinforec() {
        return xesbresultinforec;
    }
    
    public void setXesbresultinforec(APPSCUXPOVENDORSX1137207X1X31 value) {
        this.xesbresultinforec = value;
    }

}
