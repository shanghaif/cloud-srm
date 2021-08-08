
package com.midea.cloud.srm.model.pm.soap.order;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>APPS.CUX_PO_ORDERS_MX1362358X4X13 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_ORDERS_MX1362358X4X13">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LINEDETAILS_ITEM" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}APPS.CUX_PO_ORDERS_MX1362358X4X14" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_ORDERS_MX1362358X4X13", propOrder = {
    "linedetailsitem"
})
public class APPSCUXPOORDERSMX1362358X4X13 {

    @XmlElement(name = "LINEDETAILS_ITEM", nillable = true)
    protected List<APPSCUXPOORDERSMX1362358X4X14> linedetailsitem;

    /**
     * Gets the value of the linedetailsitem property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the linedetailsitem property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLINEDETAILSITEM().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link APPSCUXPOORDERSMX1362358X4X14 }
     *
     *
     */
    public List<APPSCUXPOORDERSMX1362358X4X14> getLINEDETAILSITEM() {
        if (linedetailsitem == null) {
            linedetailsitem = new ArrayList<APPSCUXPOORDERSMX1362358X4X14>();
        }
        return this.linedetailsitem;
    }

    public void setLINEDETAILSITEM(List<APPSCUXPOORDERSMX1362358X4X14> linedetailsitem){
        this.linedetailsitem = linedetailsitem;
    }
}
