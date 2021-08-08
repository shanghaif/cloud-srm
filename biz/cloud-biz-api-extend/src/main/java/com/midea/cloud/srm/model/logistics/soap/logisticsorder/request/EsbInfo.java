
package com.midea.cloud.srm.model.logistics.soap.logisticsorder.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>esbInfo complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="esbInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attr1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="instId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="requestTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esbInfo", propOrder = {
    "attr1",
    "attr2",
    "attr3",
    "instId",
    "requestTime"
})
public class EsbInfo {
    @XmlElement(name = "attr1", namespace = "http://www.longi.com/TMSSB/Srm/LogisticsContractRate/WSDLs/v1.0", required = false)
    protected String attr1;
    @XmlElement(name = "attr2", namespace = "http://www.longi.com/TMSSB/Srm/LogisticsContractRate/WSDLs/v1.0", required = false)
    protected String attr2;
    @XmlElement(name = "attr3", namespace = "http://www.longi.com/TMSSB/Srm/LogisticsContractRate/WSDLs/v1.0", required = false)
    protected String attr3;
    @XmlElement(name = "instId", namespace = "http://www.longi.com/TMSSB/Srm/LogisticsContractRate/WSDLs/v1.0", required = false)
    protected String instId;
    @XmlElement(name = "requestTime", namespace = "http://www.longi.com/TMSSB/Srm/LogisticsContractRate/WSDLs/v1.0", required = false)
    protected String requestTime;

    /**
     * 获取attr1属性的值。
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
     * 设置attr1属性的值。
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
     * 获取attr2属性的值。
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
     * 设置attr2属性的值。
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
     * 获取attr3属性的值。
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
     * 设置attr3属性的值。
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
     * 获取instId属性的值。
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
     * 设置instId属性的值。
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
     * 获取requestTime属性的值。
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
     * 设置requestTime属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRequestTime(String value) {
        this.requestTime = value;
    }



}
