
package com.midea.cloud.srm.model.suppliercooperate.deliverynote.soap.erpacceptvendorshipmentsoapbiz;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>APPS.CUX_RCV_ASN_IFAX1362720X1X35 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_RCV_ASN_IFAX1362720X1X35">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LOTLIST_ITEM" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}APPS.CUX_RCV_ASN_IFAX1362720X1X36" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_RCV_ASN_IFAX1362720X1X35", propOrder = {
    "lotlistitem"
})
@Data
public class APPSCUXRCVASNIFAX1362720X1X35 {

    @XmlElement(name = "LOTLIST_ITEM", nillable = true)
    protected List<APPSCUXRCVASNIFAX1362720X1X36> lotlistitem;

}
