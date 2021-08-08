
package com.midea.cloud.srm.model.suppliercooperate.deliverynote.soap.erpacceptvendorshipmentsoapbiz;

import lombok.Data;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_RCV_ASN_IFACX1362720X1X1", propOrder = {
    "instid",
    "requesttime",
    "attr1",
    "attr2",
    "attr3"
})
@Data
public class APPSCUXRCVASNIFACX1362720X1X1 {

    @XmlElement(name = "INSTID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected String  instid;
    @XmlElement(name = "REQUESTTIME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected String  requesttime;
    @XmlElement(name = "ATTR1", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected String  attr1;
    @XmlElement(name = "ATTR2", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected String  attr2;
    @XmlElement(name = "ATTR3", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected String  attr3;

}
