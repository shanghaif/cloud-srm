
package com.midea.cloud.srm.model.logistics.soap.order.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for customerinfo complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="customerinfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orderHeadNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vendorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priceStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priceEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="settlementMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contractType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessModeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transportModeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sourceSystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="road" type="{http://www.longi.com/TMSSB/Srm/LogisticsContractRate/Schemas/v1.0}road" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="boat" type="{http://www.longi.com/TMSSB/Srm/LogisticsContractRate/Schemas/v1.0}boat" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customerinfo", propOrder = {
    "orderHeadNum",
    "vendorCode",
    "priceStartDate",
    "priceEndDate",
    "settlementMethod",
    "contractType",
    "businessModeCode",
    "transportModeCode",
    "status",
    "sourceSystem",
    "attr1",
    "attr2",
    "attr3",
    "attr4",
    "attr5",
    "attr6",
    "road",
    "boat"
})
public class Customerinfo {

    protected String orderHeadNum;
    protected String vendorCode;
    protected String priceStartDate;
    protected String priceEndDate;
    protected String settlementMethod;
    protected String contractType;
    protected String businessModeCode;
    protected String transportModeCode;
    protected String status;
    protected String sourceSystem;
    protected String attr1;
    protected String attr2;
    protected String attr3;
    protected String attr4;
    protected String attr5;
    protected String attr6;
    protected List<Road> road;
    protected List<Boat> boat;

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
     * Gets the value of the vendorCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getVendorCode() {
        return vendorCode;
    }

    /**
     * Sets the value of the vendorCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setVendorCode(String value) {
        this.vendorCode = value;
    }

    /**
     * Gets the value of the priceStartDate property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPriceStartDate() {
        return priceStartDate;
    }

    /**
     * Sets the value of the priceStartDate property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPriceStartDate(String value) {
        this.priceStartDate = value;
    }

    /**
     * Gets the value of the priceEndDate property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPriceEndDate() {
        return priceEndDate;
    }

    /**
     * Sets the value of the priceEndDate property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPriceEndDate(String value) {
        this.priceEndDate = value;
    }

    /**
     * Gets the value of the settlementMethod property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSettlementMethod() {
        return settlementMethod;
    }

    /**
     * Sets the value of the settlementMethod property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSettlementMethod(String value) {
        this.settlementMethod = value;
    }

    /**
     * Gets the value of the contractType property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getContractType() {
        return contractType;
    }

    /**
     * Sets the value of the contractType property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setContractType(String value) {
        this.contractType = value;
    }

    /**
     * Gets the value of the businessModeCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBusinessModeCode() {
        return businessModeCode;
    }

    /**
     * Sets the value of the businessModeCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBusinessModeCode(String value) {
        this.businessModeCode = value;
    }

    /**
     * Gets the value of the transportModeCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTransportModeCode() {
        return transportModeCode;
    }

    /**
     * Sets the value of the transportModeCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTransportModeCode(String value) {
        this.transportModeCode = value;
    }

    /**
     * Gets the value of the status property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the sourceSystem property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSourceSystem() {
        return sourceSystem;
    }

    /**
     * Sets the value of the sourceSystem property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSourceSystem(String value) {
        this.sourceSystem = value;
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

    /**
     * Gets the value of the road property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the road property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoad().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Road }
     *
     *
     */
    public List<Road> getRoad() {
        if (road == null) {
            road = new ArrayList<Road>();
        }
        return this.road;
    }

    /**
     * Gets the value of the boat property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the boat property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBoat().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Boat }
     *
     *
     */
    public List<Boat> getBoat() {
        if (boat == null) {
            boat = new ArrayList<Boat>();
        }
        return this.boat;
    }

    public void setRoad(List<Road> road) {
        this.road = road;
    }

    public void setBoat(List<Boat> boat) {
        this.boat = boat;
    }
}
