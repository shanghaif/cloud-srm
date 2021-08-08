
package com.midea.cloud.srm.model.logistics.soap.order.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for getSrmTariffInfo complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="getSrmTariffInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esbInfo" type="{http://www.longi.com/TMSSB/Srm/LogisticsContractRate/Schemas/v1.0}esbInfo" minOccurs="0"/>
 *         &lt;element name="head" type="{http://www.longi.com/TMSSB/Srm/LogisticsContractRate/Schemas/v1.0}head" minOccurs="0"/>
 *         &lt;element name="requestInfo" type="{http://www.longi.com/TMSSB/Srm/LogisticsContractRate/Schemas/v1.0}customerinfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSrmTariffInfo", propOrder = {
    "esbInfo",
    "head",
    "requestInfo"
})
public class GetSrmTariffInfo {

    protected EsbInfo esbInfo;
    protected Head head;
    protected List<Customerinfo> requestInfo;

    /**
     * Gets the value of the esbInfo property.
     *
     * @return
     *     possible object is
     *     {@link EsbInfo }
     *
     */
    public EsbInfo getEsbInfo() {
        return esbInfo;
    }

    /**
     * Sets the value of the esbInfo property.
     *
     * @param value
     *     allowed object is
     *     {@link EsbInfo }
     *
     */
    public void setEsbInfo(EsbInfo value) {
        this.esbInfo = value;
    }

    /**
     * Gets the value of the head property.
     *
     * @return
     *     possible object is
     *     {@link Head }
     *
     */
    public Head getHead() {
        return head;
    }

    /**
     * Sets the value of the head property.
     *
     * @param value
     *     allowed object is
     *     {@link Head }
     *
     */
    public void setHead(Head value) {
        this.head = value;
    }

    /**
     * Gets the value of the requestInfo property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requestInfo property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequestInfo().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Customerinfo }
     *
     *
     */
    public List<Customerinfo> getRequestInfo() {
        if (requestInfo == null) {
            requestInfo = new ArrayList<Customerinfo>();
        }
        return this.requestInfo;
    }

    public void setRequestInfo(List<Customerinfo> requestInfo) {
        this.requestInfo = requestInfo;
    }
}
