
package com.midea.cloud.srm.model.pm.soap.order;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>APPS.CUX_PO_ORDERS_MOX1362358X4X7 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_ORDERS_MOX1362358X4X7">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="P_PO_INFO_TBL_ITEM" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}APPS.CUX_PO_ORDERS_MOX1362358X4X8" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_ORDERS_MOX1362358X4X7", propOrder = {
    "ppoinfotblitem"
})
public class APPSCUXPOORDERSMOX1362358X4X7 {

    @XmlElement(name = "P_PO_INFO_TBL_ITEM", nillable = true)
    protected List<APPSCUXPOORDERSMOX1362358X4X8> ppoinfotblitem;

    /**
     * Gets the value of the ppoinfotblitem property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ppoinfotblitem property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPPOINFOTBLITEM().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link APPSCUXPOORDERSMOX1362358X4X8 }
     *
     *
     */
    public List<APPSCUXPOORDERSMOX1362358X4X8> getPPOINFOTBLITEM() {
        if (ppoinfotblitem == null) {
            ppoinfotblitem = new ArrayList<APPSCUXPOORDERSMOX1362358X4X8>();
        }
        return this.ppoinfotblitem;
    }


    public void setPPOINFOTBLITEM(List<APPSCUXPOORDERSMOX1362358X4X8> ppoinfotblitem){
        this.ppoinfotblitem = ppoinfotblitem;
    }


}
