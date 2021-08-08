package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorSite;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_VENDOR_SX1137207X1X31", propOrder = {
    "instid",
    "returnstatus",
    "returncode",
    "returnmsg",
    "requesttime",
    "responsetime",
    "attr1",
    "attr2",
    "attr3"
})
@Data
public class APPSCUXPOVENDORSX1137207X1X31 {

    @XmlElement(name = "INSTID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String instid;
    @XmlElement(name = "RETURNSTATUS", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String returnstatus;
    @XmlElement(name = "RETURNCODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String returncode;
    @XmlElement(name = "RETURNMSG", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String returnmsg;
    @XmlElement(name = "REQUESTTIME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String requesttime;
    @XmlElement(name = "RESPONSETIME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String responsetime;
    @XmlElement(name = "ATTR1", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String attr1;
    @XmlElement(name = "ATTR2", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String attr2;
    @XmlElement(name = "ATTR3", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String attr3;

}
