
package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorInfo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "xesbresultinforec"
})
@XmlRootElement(name = "OutputParameters")
public class VendorInfoOutputParameters {

    @XmlElement(name = "X_ESB_RESULT_INFO_REC", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", required = false)
    protected APPSCUXPOVENDORSX1137205X1X18 xesbresultinforec;


    public APPSCUXPOVENDORSX1137205X1X18 getXesbresultinforec() {
        return xesbresultinforec;
    }

    public void setXesbresultinforec(APPSCUXPOVENDORSX1137205X1X18 value) {
        this.xesbresultinforec = value;
    }

}
