package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorSite;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_VENDOR_SIX1137207X1X7", propOrder = {
    "pvendorsitetblitem"
})
@Data
public class APPSCUXPOVENDORSIX1137207X1X7 {

    @XmlElement(name = "P_VENDOR_SITE_TBL_ITEM", nillable = true)
    protected List<APPSCUXPOVENDORSIX1137207X1X8> pvendorsitetblitem;

}
