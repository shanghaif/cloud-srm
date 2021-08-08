
package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorInfo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_VENDOR_SYX1137205X1X8", propOrder = {
    "vendorname",
    "vendornumber",
    "sourcesyscode",
    "sourcelineid",
    "attr1",
    "attr2",
    "attr3",
    "attr4",
    "attr5"
})
@Data
public class APPSCUXPOVENDORSYX1137205X1X8 {

    @XmlElement(name = "VENDORNAME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", required = false)
    protected String vendorname;
    @XmlElement(name = "VENDORNUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", required = false)
    protected String vendornumber;
    @XmlElement(name = "SOURCESYSCODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", required = false)
    protected String sourcesyscode;
    @XmlElement(name = "SOURCELINEID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", required = false)
    protected String sourcelineid;
    @XmlElement(name = "ATTR1", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", required = false)
    protected String attr1;
    @XmlElement(name = "ATTR2", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", required = false)
    protected String attr2;
    @XmlElement(name = "ATTR3", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", required = false)
    protected String attr3;
    @XmlElement(name = "ATTR4", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", required = false)
    protected String attr4;
    @XmlElement(name = "ATTR5", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorsSoapBiz", required = false)
    protected String attr5;

}
