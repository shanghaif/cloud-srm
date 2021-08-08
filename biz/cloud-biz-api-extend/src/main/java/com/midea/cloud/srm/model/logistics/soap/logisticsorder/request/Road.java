
package com.midea.cloud.srm.model.logistics.soap.logisticsorder.request;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>road complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取orderHeadNum属性的值。
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
     * 设置orderHeadNum属性的值。
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
     * 获取rowLineNum属性的值。
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
     * 设置rowLineNum属性的值。
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
     * 获取fromCountry属性的值。
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
     * 设置fromCountry属性的值。
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
     * 获取fromProvince属性的值。
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
     * 设置fromProvince属性的值。
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
     * 获取fromCity属性的值。
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
     * 设置fromCity属性的值。
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
     * 获取fromCounty属性的值。
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
     * 设置fromCounty属性的值。
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
     * 获取fromPlace属性的值。
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
     * 设置fromPlace属性的值。
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
     * 获取toCountry属性的值。
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
     * 设置toCountry属性的值。
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
     * 获取toProvinceCode属性的值。
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
     * 设置toProvinceCode属性的值。
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
     * 获取toCityCode属性的值。
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
     * 设置toCityCode属性的值。
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
     * 获取toCountyCode属性的值。
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
     * 设置toCountyCode属性的值。
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
     * 获取toPlace属性的值。
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
     * 设置toPlace属性的值。
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
     * 获取vendorOrderNum属性的值。
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
     * 设置vendorOrderNum属性的值。
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
     * 获取transportDistance属性的值。
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
     * 设置transportDistance属性的值。
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
     * 获取transportModeCode属性的值。
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
     * 设置transportModeCode属性的值。
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
     * 获取serviceProjectCode属性的值。
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
     * 设置serviceProjectCode属性的值。
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
     * 获取status属性的值。
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
     * 设置status属性的值。
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
     * 获取realTime属性的值。
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
     * 设置realTime属性的值。
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
     * 获取priceStartDate属性的值。
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
     * 设置priceStartDate属性的值。
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
     * 获取priceEndDate属性的值。
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
     * 设置priceEndDate属性的值。
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
     * 获取wholeArk属性的值。
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
     * 设置wholeArk属性的值。
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
     * 获取fromPort属性的值。
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
     * 设置fromPort属性的值。
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
     * 获取toPort属性的值。
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
     * 设置toPort属性的值。
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
     * 获取unProjectFlag属性的值。
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
     * 设置unProjectFlag属性的值。
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
     * 获取attr4属性的值。
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
     * 设置attr4属性的值。
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
     * 获取attr5属性的值。
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
     * 设置attr5属性的值。
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
     * 获取attr6属性的值。
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
     * 设置attr6属性的值。
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
