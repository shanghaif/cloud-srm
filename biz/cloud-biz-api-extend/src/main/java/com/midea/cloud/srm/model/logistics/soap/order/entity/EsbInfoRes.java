
package com.midea.cloud.srm.model.logistics.soap.order.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for esbInfoRes complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="esbInfoRes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attr1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="instId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="requestTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="responseTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="returnCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="returnMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="returnStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esbInfoRes", propOrder = {
    "attr1",
    "attr2",
    "attr3",
    "instId",
    "requestTime",
    "responseTime",
    "returnCode",
    "returnMsg",
    "returnStatus"
})
public class EsbInfoRes {

    protected String attr1;
    protected String attr2;
    protected String attr3;
    protected String instId;
    protected String requestTime;
    protected String responseTime;
    protected String returnCode;
    protected String returnMsg;
    protected String returnStatus;

    /**
     * Gets the value of the attr1 property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAttr1() {
        return attr1;
    }

    /**
     * Sets the value of the attr1 property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAttr1(String value) {
        this.attr1 = value;
    }

    /**
     * Gets the value of the attr2 property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAttr2() {
        return attr2;
    }

    /**
     * Sets the value of the attr2 property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAttr2(String value) {
        this.attr2 = value;
    }

    /**
     * Gets the value of the attr3 property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAttr3() {
        return attr3;
    }

    /**
     * Sets the value of the attr3 property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAttr3(String value) {
        this.attr3 = value;
    }

    /**
     * Gets the value of the instId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getInstId() {
        return instId;
    }

    /**
     * Sets the value of the instId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setInstId(String value) {
        this.instId = value;
    }

    /**
     * Gets the value of the requestTime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRequestTime() {
        return requestTime;
    }

    /**
     * Sets the value of the requestTime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRequestTime(String value) {
        this.requestTime = value;
    }

    /**
     * Gets the value of the responseTime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getResponseTime() {
        return responseTime;
    }

    /**
     * Sets the value of the responseTime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setResponseTime(String value) {
        this.responseTime = value;
    }

    /**
     * Gets the value of the returnCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getReturnCode() {
        return returnCode;
    }

    /**
     * Sets the value of the returnCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setReturnCode(String value) {
        this.returnCode = value;
    }

    /**
     * Gets the value of the returnMsg property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getReturnMsg() {
        return returnMsg;
    }

    /**
     * Sets the value of the returnMsg property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setReturnMsg(String value) {
        this.returnMsg = value;
    }

    /**
     * Gets the value of the returnStatus property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getReturnStatus() {
        return returnStatus;
    }

    /**
     * Sets the value of the returnStatus property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setReturnStatus(String value) {
        this.returnStatus = value;
    }

}
