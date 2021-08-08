
package com.midea.cloud.srm.model.logistics.soap.order.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for road complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="road">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orderHeadNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rowLineNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fromCountry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fromProvince" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fromCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fromCounty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fromPlace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toCountry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toCityCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toCountyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toPlace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vendorOrderNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transportDistance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transportModeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serviceProjectCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="realTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priceStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priceEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="wholeArk" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fromPort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toPort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unProjectFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rate" type="{http://www.longi.com/TMSSB/Srm/LogisticsContractRate/Schemas/v1.0}rate" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "road", propOrder = {
    "orderHeadNum",
    "rowLineNum",
    "fromCountry",
    "fromProvince",
    "fromCity",
    "fromCounty",
    "fromPlace",
    "toCountry",
    "toProvinceCode",
    "toCityCode",
    "toCountyCode",
    "toPlace",
    "vendorOrderNum",
    "transportDistance",
    "transportModeCode",
    "serviceProjectCode",
    "status",
    "realTime",
    "priceStartDate",
    "priceEndDate",
    "wholeArk",
    "fromPort",
    "toPort",
    "unProjectFlag",
    "attr1",
    "attr2",
    "attr3",
    "attr4",
    "attr5",
    "attr6",
    "rate"
})
public class Road {

    protected String orderHeadNum;
    protected String rowLineNum;
    protected String fromCountry;
    protected String fromProvince;
    protected String fromCity;
    protected String fromCounty;
    protected String fromPlace;
    protected String toCountry;
    protected String toProvinceCode;
    protected String toCityCode;
    protected String toCountyCode;
    protected String toPlace;
    protected String vendorOrderNum;
    protected String transportDistance;
    protected String transportModeCode;
    protected String serviceProjectCode;
    protected String status;
    protected String realTime;
    protected String priceStartDate;
    protected String priceEndDate;
    protected String wholeArk;
    protected String fromPort;
    protected String toPort;
    protected String unProjectFlag;
    protected String attr1;
    protected String attr2;
    protected String attr3;
    protected String attr4;
    protected String attr5;
    protected String attr6;
    protected List<Rate> rate;

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
     * Gets the value of the rowLineNum property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRowLineNum() {
        return rowLineNum;
    }

    /**
     * Sets the value of the rowLineNum property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRowLineNum(String value) {
        this.rowLineNum = value;
    }

    /**
     * Gets the value of the fromCountry property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFromCountry() {
        return fromCountry;
    }

    /**
     * Sets the value of the fromCountry property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFromCountry(String value) {
        this.fromCountry = value;
    }

    /**
     * Gets the value of the fromProvince property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFromProvince() {
        return fromProvince;
    }

    /**
     * Sets the value of the fromProvince property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFromProvince(String value) {
        this.fromProvince = value;
    }

    /**
     * Gets the value of the fromCity property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFromCity() {
        return fromCity;
    }

    /**
     * Sets the value of the fromCity property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFromCity(String value) {
        this.fromCity = value;
    }

    /**
     * Gets the value of the fromCounty property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFromCounty() {
        return fromCounty;
    }

    /**
     * Sets the value of the fromCounty property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFromCounty(String value) {
        this.fromCounty = value;
    }

    /**
     * Gets the value of the fromPlace property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFromPlace() {
        return fromPlace;
    }

    /**
     * Sets the value of the fromPlace property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFromPlace(String value) {
        this.fromPlace = value;
    }

    /**
     * Gets the value of the toCountry property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getToCountry() {
        return toCountry;
    }

    /**
     * Sets the value of the toCountry property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setToCountry(String value) {
        this.toCountry = value;
    }

    /**
     * Gets the value of the toProvinceCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getToProvinceCode() {
        return toProvinceCode;
    }

    /**
     * Sets the value of the toProvinceCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setToProvinceCode(String value) {
        this.toProvinceCode = value;
    }

    /**
     * Gets the value of the toCityCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getToCityCode() {
        return toCityCode;
    }

    /**
     * Sets the value of the toCityCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setToCityCode(String value) {
        this.toCityCode = value;
    }

    /**
     * Gets the value of the toCountyCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getToCountyCode() {
        return toCountyCode;
    }

    /**
     * Sets the value of the toCountyCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setToCountyCode(String value) {
        this.toCountyCode = value;
    }

    /**
     * Gets the value of the toPlace property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getToPlace() {
        return toPlace;
    }

    /**
     * Sets the value of the toPlace property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setToPlace(String value) {
        this.toPlace = value;
    }

    /**
     * Gets the value of the vendorOrderNum property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getVendorOrderNum() {
        return vendorOrderNum;
    }

    /**
     * Sets the value of the vendorOrderNum property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setVendorOrderNum(String value) {
        this.vendorOrderNum = value;
    }

    /**
     * Gets the value of the transportDistance property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTransportDistance() {
        return transportDistance;
    }

    /**
     * Sets the value of the transportDistance property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTransportDistance(String value) {
        this.transportDistance = value;
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
     * Gets the value of the serviceProjectCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getServiceProjectCode() {
        return serviceProjectCode;
    }

    /**
     * Sets the value of the serviceProjectCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setServiceProjectCode(String value) {
        this.serviceProjectCode = value;
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
     * Gets the value of the realTime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRealTime() {
        return realTime;
    }

    /**
     * Sets the value of the realTime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRealTime(String value) {
        this.realTime = value;
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
     * Gets the value of the unProjectFlag property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUnProjectFlag() {
        return unProjectFlag;
    }

    /**
     * Sets the value of the unProjectFlag property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUnProjectFlag(String value) {
        this.unProjectFlag = value;
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
     * Gets the value of the rate property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rate property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRate().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Rate }
     *
     *
     */
    public List<Rate> getRate() {
        if (rate == null) {
            rate = new ArrayList<Rate>();
        }
        return this.rate;
    }

    public void setRate(List<Rate> rate) {
        this.rate = rate;
    }
}
