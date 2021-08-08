
package com.midea.cloud.srm.model.suppliercooperate.soap.erp.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>APPS.CUX_PO_RECEIVE_X1139141X1X35 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_RECEIVE_X1139141X1X35">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="X_RESULT_INFO_ITEM" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz}APPS.CUX_PO_RECEIVE_X1139141X1X36" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_RECEIVE_X1139141X1X35", propOrder = {
    "xresultinfoitem"
})
public class APPSCUXPORECEIVEX1139141X1X35 {

    @XmlElement(name = "X_RESULT_INFO_ITEM",namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPoRcvRtnLockSoapBiz",  required = false, nillable = true)
    protected List<APPSCUXPORECEIVEX1139141X1X36> xresultinfoitem;

    /**
     * Gets the value of the xresultinfoitem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the xresultinfoitem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getXRESULTINFOITEM().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link APPSCUXPORECEIVEX1139141X1X36 }
     * 
     * 
     */
    public List<APPSCUXPORECEIVEX1139141X1X36> getXRESULTINFOITEM() {
        if (xresultinfoitem == null) {
            xresultinfoitem = new ArrayList<APPSCUXPORECEIVEX1139141X1X36>();
        }
        return this.xresultinfoitem;
    }

}
