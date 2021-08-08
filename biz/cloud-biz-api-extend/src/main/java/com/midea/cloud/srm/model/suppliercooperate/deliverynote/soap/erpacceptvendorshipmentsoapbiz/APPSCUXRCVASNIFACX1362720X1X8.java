
package com.midea.cloud.srm.model.suppliercooperate.deliverynote.soap.erpacceptvendorshipmentsoapbiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS." +
        "", propOrder = {
    "sourcesyscode",
    "sourceheaderid",
    "ifacecode",
    "ifacemean",
    "operationunitid",
    "operationname",
    "shipmentnum",
    "vendorid",
    "vendorname",
    "shippeddate",
    "expectedreceiptdate",
    "lines"
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class APPSCUXRCVASNIFACX1362720X1X8 {

    @XmlElement(name = "SOURCESYSCODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected String sourcesyscode;
    @XmlElement(name = "SOURCEHEADERID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected String sourceheaderid;
    @XmlElement(name = "IFACECODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected String ifacecode;
    @XmlElement(name = "IFACEMEAN", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected String ifacemean;
    @XmlElement(name = "OPERATIONUNITID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected BigDecimal operationunitid;
    @XmlElement(name = "OPERATIONNAME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected String operationname;
    @XmlElement(name = "SHIPMENTNUM", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected String shipmentnum;
    @XmlElement(name = "VENDORID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected BigDecimal vendorid;
    @XmlElement(name = "VENDORNAME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected String vendorname;
    @XmlElement(name = "SHIPPEDDATE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected String shippeddate;
    @XmlElement(name = "EXPECTEDRECEIPTDATE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected String expectedreceiptdate;
    @XmlElement(name = "LINES", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected APPSCUXRCVASNIFAX1362720X1X20 lines;

}
