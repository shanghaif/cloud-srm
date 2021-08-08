
package com.midea.cloud.srm.model.suppliercooperate.deliverynote.soap.erpacceptvendorshipmentsoapbiz;

import lombok.Data;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


/**
 * <p>APPS.CUX_RCV_ASN_IFAX1362720X1X36 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_RCV_ASN_IFAX1362720X1X36">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LOTNUMBER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}string80" minOccurs="0"/>
 *         &lt;element name="LOTQUANTITY" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_RCV_ASN_IFAX1362720X1X36", propOrder = {
    "lotnumber",
    "lotquantity"
})
@Data
public class APPSCUXRCVASNIFAX1362720X1X36 {

    @XmlElement(name = "LOTNUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected JAXBElement<String> lotnumber;
    @XmlElement(name = "LOTQUANTITY", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false)
    protected JAXBElement<BigDecimal> lotquantity;
}
