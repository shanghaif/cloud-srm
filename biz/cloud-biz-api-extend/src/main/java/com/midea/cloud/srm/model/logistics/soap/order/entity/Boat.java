
package com.midea.cloud.srm.model.logistics.soap.order.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for boat complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="boat">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orderHeadNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rowNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fromPort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toPort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="wholeArk" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="wed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="thu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sun" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transitTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="company" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transferPort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "boat", propOrder = {
    "orderHeadNum",
    "rowNum",
    "fromPort",
    "toPort",
    "wholeArk",
    "mon",
    "tue",
    "wed",
    "thu",
    "fri",
    "sat",
    "sun",
    "transitTime",
    "company",
    "transferPort",
    "attr1",
    "attr2",
    "attr3",
    "attr4",
    "attr5",
    "attr6"
})
public class Boat {

    protected String orderHeadNum;
    protected String rowNum;
    protected String fromPort;
    protected String toPort;
    protected String wholeArk;
    protected String mon;
    protected String tue;
    protected String wed;
    protected String thu;
    protected String fri;
    protected String sat;
    protected String sun;
    protected String transitTime;
    protected String company;
    protected String transferPort;
    protected String attr1;
    protected String attr2;
    protected String attr3;
    protected String attr4;
    protected String attr5;
    protected String attr6;

    /**
     * Gets the value of the orderHeadNum property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOrderHeadNum() {
        return orderHeadNum;
    }

    /**
     * Sets the value of the orderHeadNum property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOrderHeadNum(String value) {
        this.orderHeadNum = value;
    }

    /**
     * Gets the value of the rowNum property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRowNum() {
        return rowNum;
    }

    /**
     * Sets the value of the rowNum property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRowNum(String value) {
        this.rowNum = value;
    }

    /**
     * Gets the value of the fromPort property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFromPort() {
        return fromPort;
    }

    /**
     * Sets the value of the fromPort property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFromPort(String value) {
        this.fromPort = value;
    }

    /**
     * Gets the value of the toPort property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getToPort() {
        return toPort;
    }

    /**
     * Sets the value of the toPort property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setToPort(String value) {
        this.toPort = value;
    }

    /**
     * Gets the value of the wholeArk property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getWholeArk() {
        return wholeArk;
    }

    /**
     * Sets the value of the wholeArk property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setWholeArk(String value) {
        this.wholeArk = value;
    }

    /**
     * Gets the value of the mon property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMon() {
        return mon;
    }

    /**
     * Sets the value of the mon property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMon(String value) {
        this.mon = value;
    }

    /**
     * Gets the value of the tue property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTue() {
        return tue;
    }

    /**
     * Sets the value of the tue property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTue(String value) {
        this.tue = value;
    }

    /**
     * Gets the value of the wed property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getWed() {
        return wed;
    }

    /**
     * Sets the value of the wed property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setWed(String value) {
        this.wed = value;
    }

    /**
     * Gets the value of the thu property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getThu() {
        return thu;
    }

    /**
     * Sets the value of the thu property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setThu(String value) {
        this.thu = value;
    }

    /**
     * Gets the value of the fri property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFri() {
        return fri;
    }

    /**
     * Sets the value of the fri property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFri(String value) {
        this.fri = value;
    }

    /**
     * Gets the value of the sat property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSat() {
        return sat;
    }

    /**
     * Sets the value of the sat property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSat(String value) {
        this.sat = value;
    }

    /**
     * Gets the value of the sun property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSun() {
        return sun;
    }

    /**
     * Sets the value of the sun property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSun(String value) {
        this.sun = value;
    }

    /**
     * Gets the value of the transitTime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTransitTime() {
        return transitTime;
    }

    /**
     * Sets the value of the transitTime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTransitTime(String value) {
        this.transitTime = value;
    }

    /**
     * Gets the value of the company property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCompany() {
        return company;
    }

    /**
     * Sets the value of the company property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCompany(String value) {
        this.company = value;
    }

    /**
     * Gets the value of the transferPort property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTransferPort() {
        return transferPort;
    }

    /**
     * Sets the value of the transferPort property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTransferPort(String value) {
        this.transferPort = value;
    }

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
     * Gets the value of the attr4 property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAttr4() {
        return attr4;
    }

    /**
     * Sets the value of the attr4 property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAttr4(String value) {
        this.attr4 = value;
    }

    /**
     * Gets the value of the attr5 property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAttr5() {
        return attr5;
    }

    /**
     * Sets the value of the attr5 property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAttr5(String value) {
        this.attr5 = value;
    }

    /**
     * Gets the value of the attr6 property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAttr6() {
        return attr6;
    }

    /**
     * Sets the value of the attr6 property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAttr6(String value) {
        this.attr6 = value;
    }

}
