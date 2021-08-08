
package com.midea.cloud.srm.model.logistics.soap.order.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rate complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="rate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orderHeadNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rowLineNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rowNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="expenseItem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="chargeMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="chargeUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="minCost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maxCost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="expense" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ifBack" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="leg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="taxRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "rate", propOrder = {
    "orderHeadNum",
    "rowLineNum",
    "rowNum",
    "expenseItem",
    "chargeMethod",
    "chargeUnit",
    "minCost",
    "maxCost",
    "expense",
    "currency",
    "ifBack",
    "leg",
    "taxRate",
    "attr1",
    "attr2",
    "attr3",
    "attr4",
    "attr5",
    "attr6"
})
public class Rate {

    protected String orderHeadNum;
    protected String rowLineNum;
    protected String rowNum;
    protected String expenseItem;
    protected String chargeMethod;
    protected String chargeUnit;
    protected String minCost;
    protected String maxCost;
    protected String expense;
    protected String currency;
    protected String ifBack;
    protected String leg;
    protected String taxRate;
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
     * Gets the value of the expenseItem property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getExpenseItem() {
        return expenseItem;
    }

    /**
     * Sets the value of the expenseItem property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setExpenseItem(String value) {
        this.expenseItem = value;
    }

    /**
     * Gets the value of the chargeMethod property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getChargeMethod() {
        return chargeMethod;
    }

    /**
     * Sets the value of the chargeMethod property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setChargeMethod(String value) {
        this.chargeMethod = value;
    }

    /**
     * Gets the value of the chargeUnit property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getChargeUnit() {
        return chargeUnit;
    }

    /**
     * Sets the value of the chargeUnit property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setChargeUnit(String value) {
        this.chargeUnit = value;
    }

    /**
     * Gets the value of the minCost property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMinCost() {
        return minCost;
    }

    /**
     * Sets the value of the minCost property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMinCost(String value) {
        this.minCost = value;
    }

    /**
     * Gets the value of the maxCost property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMaxCost() {
        return maxCost;
    }

    /**
     * Sets the value of the maxCost property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMaxCost(String value) {
        this.maxCost = value;
    }

    /**
     * Gets the value of the expense property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getExpense() {
        return expense;
    }

    /**
     * Sets the value of the expense property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setExpense(String value) {
        this.expense = value;
    }

    /**
     * Gets the value of the currency property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the ifBack property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIfBack() {
        return ifBack;
    }

    /**
     * Sets the value of the ifBack property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIfBack(String value) {
        this.ifBack = value;
    }

    /**
     * Gets the value of the leg property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLeg() {
        return leg;
    }

    /**
     * Sets the value of the leg property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLeg(String value) {
        this.leg = value;
    }

    /**
     * Gets the value of the taxRate property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTaxRate() {
        return taxRate;
    }

    /**
     * Sets the value of the taxRate property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTaxRate(String value) {
        this.taxRate = value;
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
