
package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorContact;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "xesbresultinforec"
})
@XmlRootElement(name = "OutputParameters")
public class VendorContactOutputParameters {

    @XmlElement(name = "X_ESB_RESULT_INFO_REC", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorContactSoapBiz", required = false)
    protected APPSCUXPOVENDORCX1137209X1X22 xesbresultinforec;

    public APPSCUXPOVENDORCX1137209X1X22 getXesbresultinforec() {
        return xesbresultinforec;
    }

    public void setXesbresultinforec(APPSCUXPOVENDORCX1137209X1X22 value) {
        this.xesbresultinforec = value;
    }

}
