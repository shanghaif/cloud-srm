
package com.midea.cloud.srm.model.suppliercooperate.deliverynote.soap.erpacceptvendorshipmentsoapbiz;

import lombok.Data;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "xesbresultinforec",
        "xasnresulttbltype"
})
@XmlRootElement(name = "OutputParameters")
@Data
public class OutputAcceptVendor {

    @XmlElement(name = "X_ESB_RESULT_INFO_REC", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected APPSCUXRCVASNIFAX1362720X1X39 xesbresultinforec;
    @XmlElement(name = "X_ASN_RESULT_TBL_TYPE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected APPSCUXRCVASNIFAX1362720X1X49 xasnresulttbltype;

}