
package com.midea.cloud.srm.model.logistics.soap.order.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for responseBody complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="responseBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esbInfo" type="{http://www.longi.com/TMSSB/Srm/LogisticsContractRate/Schemas/v1.0}esbInfoRes" minOccurs="0"/>
 *         &lt;element name="resultInfo" type="{http://www.longi.com/TMSSB/Srm/LogisticsContractRate/Schemas/v1.0}resultInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "responseBody", propOrder = {
    "esbInfo",
    "resultInfo"
})
public class ResponseBody {

    protected EsbInfoRes esbInfo;
    protected ResultInfo resultInfo;

    /**
     * Gets the value of the esbInfo property.
     *
     * @return
     *     possible object is
     *     {@link EsbInfoRes }
     *
     */
    public EsbInfoRes getEsbInfo() {
        return esbInfo;
    }

    /**
     * Sets the value of the esbInfo property.
     *
     * @param value
     *     allowed object is
     *     {@link EsbInfoRes }
     *
     */
    public void setEsbInfo(EsbInfoRes value) {
        this.esbInfo = value;
    }

    /**
     * Gets the value of the resultInfo property.
     *
     * @return
     *     possible object is
     *     {@link ResultInfo }
     *
     */
    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    /**
     * Sets the value of the resultInfo property.
     *
     * @param value
     *     allowed object is
     *     {@link ResultInfo }
     *
     */
    public void setResultInfo(ResultInfo value) {
        this.resultInfo = value;
    }

}
