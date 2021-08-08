
package com.midea.cloud.srm.model.suppliercooperate.deliverynote.soap.erpacceptvendorshipmentsoapbiz;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>APPS.CUX_RCV_ASN_IFAX1362720X1X49 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_RCV_ASN_IFAX1362720X1X49">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="X_ASN_RESULT_TBL_TYPE_ITEM" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz}APPS.CUX_RCV_ASN_IFAX1362720X1X50" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_RCV_ASN_IFAX1362720X1X49", propOrder = {
    "xasnresulttbltypeitem"
})
public class APPSCUXRCVASNIFAX1362720X1X49 {
    @XmlElement(name = "X_ASN_RESULT_TBL_TYPE_ITEM", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptVendorShipmentSoapBiz", required = false, nillable = true)
    protected List<APPSCUXRCVASNIFAX1362720X1X50> xasnresulttbltypeitem;

    /**
     * Gets the value of the xasnresulttbltypeitem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the xasnresulttbltypeitem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getXASNRESULTTBLTYPEITEM().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link APPSCUXRCVASNIFAX1362720X1X50 }
     * 
     * 
     */
    public List<APPSCUXRCVASNIFAX1362720X1X50> getXASNRESULTTBLTYPEITEM() {
        if (xasnresulttbltypeitem == null) {
            xasnresulttbltypeitem = new ArrayList<APPSCUXRCVASNIFAX1362720X1X50>();
        }
        return this.xasnresulttbltypeitem;
    }

}
