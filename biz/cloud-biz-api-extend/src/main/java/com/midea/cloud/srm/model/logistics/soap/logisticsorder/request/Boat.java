
package com.midea.cloud.srm.model.logistics.soap.logisticsorder.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>boat complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取rowNum属性的值。
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
     * 设置rowNum属性的值。
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
     * 获取mon属性的值。
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
     * 设置mon属性的值。
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
     * 获取tue属性的值。
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
     * 设置tue属性的值。
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
     * 获取wed属性的值。
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
     * 设置wed属性的值。
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
     * 获取thu属性的值。
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
     * 设置thu属性的值。
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
     * 获取fri属性的值。
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
     * 设置fri属性的值。
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
     * 获取sat属性的值。
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
     * 设置sat属性的值。
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
     * 获取sun属性的值。
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
     * 设置sun属性的值。
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
     * 获取transitTime属性的值。
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
     * 设置transitTime属性的值。
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
     * 获取company属性的值。
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
     * 设置company属性的值。
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
     * 获取transferPort属性的值。
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
     * 设置transferPort属性的值。
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



}
