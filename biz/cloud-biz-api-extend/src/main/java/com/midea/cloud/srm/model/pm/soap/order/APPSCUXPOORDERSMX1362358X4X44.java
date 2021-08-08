
package com.midea.cloud.srm.model.pm.soap.order;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>APPS.CUX_PO_ORDERS_MX1362358X4X44 complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_ORDERS_MX1362358X4X44">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="X_RESULT_INFO_ITEM" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz}APPS.CUX_PO_ORDERS_MX1362358X4X45" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_ORDERS_MX1362358X4X44", propOrder = {
    "xresultinfoitem"
})
public class APPSCUXPOORDERSMX1362358X4X44 {

    @XmlElement(name = "X_RESULT_INFO_ITEM", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpCancelPurchaseOrderSoapBiz",  required = false ,nillable = true)
    protected List<APPSCUXPOORDERSMX1362358X4X45> xresultinfoitem;

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
     * {@link APPSCUXPOORDERSMX1362358X4X45 }
     * 
     * 
     */
    public List<APPSCUXPOORDERSMX1362358X4X45> getXRESULTINFOITEM() {
        if (xresultinfoitem == null) {
            xresultinfoitem = new ArrayList<APPSCUXPOORDERSMX1362358X4X45>();
        }
        return this.xresultinfoitem;
    }

    public void setXRESULTINFOITEM(List<APPSCUXPOORDERSMX1362358X4X45> xresultinfoitem){
        this.xresultinfoitem = xresultinfoitem;
    }

}
