package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorSite;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_VENDOR_SIX1137207X1X8", propOrder = {
    "erpvendorid",
    "vendornumber",
    "addressname",
    "country",
    "province",
    "city",
    "county",
    "addressdetail",
    "purchaseflag",
    "paymentflag",
    "rfqonlyflag",
    "enabledflag",
    "belongoprid",
    "vendorsitecode",
    "disabledate",
    "sourcesyscode",
    "sourcelineid",
    "attr1",
    "attr2",
    "attr3",
    "attr4",
    "attr5"
})
@Data
public class APPSCUXPOVENDORSIX1137207X1X8 {

    @XmlElement(name = "ERPVENDORID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected BigDecimal erpvendorid;
    @XmlElement(name = "VENDORNUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String vendornumber;
    @XmlElement(name = "ADDRESSNAME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String addressname;
    @XmlElement(name = "COUNTRY", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String country;
    @XmlElement(name = "PROVINCE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String province;
    @XmlElement(name = "CITY", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String city;
    @XmlElement(name = "COUNTY", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String county;
    @XmlElement(name = "ADDRESSDETAIL", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String addressdetail;
    @XmlElement(name = "PURCHASEFLAG", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String purchaseflag;
    @XmlElement(name = "PAYMENTFLAG", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String paymentflag;
    @XmlElement(name = "RFQONLYFLAG", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String rfqonlyflag;
    @XmlElement(name = "ENABLEDFLAG", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String enabledflag;
    @XmlElement(name = "BELONGOPRID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected BigDecimal belongoprid;
    @XmlElement(name = "VENDORSITECODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String vendorsitecode;
    @XmlElement(name = "DISABLEDATE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String disabledate;
    @XmlElement(name = "SOURCESYSCODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String sourcesyscode;
    @XmlElement(name = "SOURCELINEID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String sourcelineid;
    @XmlElement(name = "ATTR1", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String attr1;
    @XmlElement(name = "ATTR2", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String attr2;
    @XmlElement(name = "ATTR3", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String attr3;
    @XmlElement(name = "ATTR4", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String attr4;
    @XmlElement(name = "ATTR5", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorSiteSoapBiz", required = false)
    protected String attr5;
    
}
