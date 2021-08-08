
package com.midea.cloud.srm.model.logistics.soap.order.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getSrmTariffInfoResponse complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="getSrmTariffInfoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="getSrmTariffInfo" type="{http://www.longi.com/TMSSB/Srm/LogisticsContractRate/Schemas/v1.0}responseBody" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSrmTariffInfoResponse", propOrder = {
    "getSrmTariffInfo"
})
public class GetSrmTariffInfoResponse {

    protected ResponseBody getSrmTariffInfo;

    /**
     * Gets the value of the getSrmTariffInfo property.
     *
     * @return
     *     possible object is
     *     {@link ResponseBody }
     *
     */
    public ResponseBody getGetSrmTariffInfo() {
        return getSrmTariffInfo;
    }

    /**
     * Sets the value of the getSrmTariffInfo property.
     *
     * @param value
     *     allowed object is
     *     {@link ResponseBody }
     *
     */
    public void setGetSrmTariffInfo(ResponseBody value) {
        this.getSrmTariffInfo = value;
    }

}
