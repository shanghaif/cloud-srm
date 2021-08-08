
package com.midea.cloud.srm.model.logistics.soap.logisticsorder.request;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>customerinfo complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取vendorCode属性的值。
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
     * 设置vendorCode属性的值。
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
     * 获取settlementMethod属性的值。
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
     * 设置settlementMethod属性的值。
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
     * 获取contractType属性的值。
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
     * 设置contractType属性的值。
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
     * 获取businessModeCode属性的值。
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
     * 设置businessModeCode属性的值。
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
     * 获取sourceSystem属性的值。
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
     * 设置sourceSystem属性的值。
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
