
package com.midea.cloud.srm.model.suppliercooperate.soap.erp.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>APPS.CUX_PO_RECEIVE_X1139141X1X11 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_RECEIVE_X1139141X1X11">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RCVDETAILS_ITEM" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}APPS.CUX_PO_RECEIVE_X1139141X1X12" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_RECEIVE_X1139141X1X11", propOrder = {
    "rcvdetailsitem"
})
public class APPSCUXPORECEIVEX1139141X1X11 {

    @XmlElement(name = "RCVDETAILS_ITEM", nillable = true)
    protected List<APPSCUXPORECEIVEX1139141X1X12> rcvdetailsitem;

    /**
     * Gets the value of the rcvdetailsitem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rcvdetailsitem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRCVDETAILSITEM().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link APPSCUXPORECEIVEX1139141X1X12 }
     * 
     * 
     */
    public List<APPSCUXPORECEIVEX1139141X1X12> getRCVDETAILSITEM() {
        if (rcvdetailsitem == null) {
            rcvdetailsitem = new ArrayList<APPSCUXPORECEIVEX1139141X1X12>();
        }
        return this.rcvdetailsitem;
    }

    /**
     * todo
     * @param rcvdetailsitem
     */
    public void setRcvdetailsitem(List<APPSCUXPORECEIVEX1139141X1X12> rcvdetailsitem) {
        this.rcvdetailsitem = rcvdetailsitem;
    }
}
