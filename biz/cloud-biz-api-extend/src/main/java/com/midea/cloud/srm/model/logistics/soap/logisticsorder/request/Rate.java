
package com.midea.cloud.srm.model.logistics.soap.logisticsorder.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>rate complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取expenseItem属性的值。
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
     * 设置expenseItem属性的值。
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
     * 获取chargeMethod属性的值。
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
     * 设置chargeMethod属性的值。
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
     * 获取chargeUnit属性的值。
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
     * 设置chargeUnit属性的值。
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
     * 获取minCost属性的值。
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
     * 设置minCost属性的值。
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
     * 获取maxCost属性的值。
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
     * 设置maxCost属性的值。
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
     * 获取expense属性的值。
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
     * 设置expense属性的值。
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
     * 获取currency属性的值。
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
     * 设置currency属性的值。
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
     * 获取ifBack属性的值。
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
     * 设置ifBack属性的值。
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
     * 获取leg属性的值。
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
     * 设置leg属性的值。
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
     * 获取taxRate属性的值。
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
     * 设置taxRate属性的值。
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
