
package com.midea.cloud.srm.model.logistics.soap.tms.request;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esbInfo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="instId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="requestTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="attr1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="attr2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="attr3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="requestInfo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="logisticsOrders" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="orderHead" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="orderHeadNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="vendorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="priceStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="priceEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="settlementMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="contractType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="businessModeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="transportModeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="sourceSystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="orderLine" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="orderHeadNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="rowLineNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="fromCountry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="fromProvince" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="fromCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="fromCounty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="fromPlace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="toCountry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="toProvinceCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="toCityCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="toCountyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="toPlace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="vendorOrderNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="transportDistance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="transportModeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="serviceProjectCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="realTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="priceStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="priceEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="wholeArk" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="fromPort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="toPort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="unProjectFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="importExportMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="orderLineFee" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="orderHeadNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="rowLineNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="rowNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="expenseItem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="chargeMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="chargeUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="minCost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="maxCost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="expense" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="ifBack" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="leg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="taxRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="orderLineShip" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="orderHeadNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="rowNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="fromPort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="toPort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="wholeArk" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="mon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="tue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="wed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="thu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="fri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="sat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="sun" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="transitTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="company" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="transferPort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="attr6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "esbInfo",
    "requestInfo"
})
@XmlRootElement(name = "logisticsOrderRequest")
public class LogisticsOrderRequest {

    @XmlElement(required = true)
    protected EsbInfo esbInfo;
    @XmlElement(required = true)
    protected RequestInfo requestInfo;

    /**
     * 获取esbInfo属性的值。
     *
     * @return
     *     possible object is
     *     {@link EsbInfo }
     *
     */
    public EsbInfo getEsbInfo() {
        return esbInfo;
    }

    /**
     * 设置esbInfo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link EsbInfo }
     *
     */
    public void setEsbInfo(EsbInfo value) {
        this.esbInfo = value;
    }

    /**
     * 获取requestInfo属性的值。
     *
     * @return
     *     possible object is
     *     {@link RequestInfo }
     *
     */
    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    /**
     * 设置requestInfo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link RequestInfo }
     *
     */
    public void setRequestInfo(RequestInfo value) {
        this.requestInfo = value;
    }


    /**
     * <p>anonymous complex type的 Java 类。
     *
     * <p>以下模式片段指定包含在此类中的预期内容。
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="instId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="requestTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="attr1" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="attr2" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="attr3" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "instId",
        "requestTime",
        "attr1",
        "attr2",
        "attr3"
    })
    public static class EsbInfo {

        @XmlElement(required = true)
        protected String instId;
        @XmlElement(required = true)
        protected String requestTime;
        @XmlElement(required = true)
        protected String attr1;
        @XmlElement(required = true)
        protected String attr2;
        @XmlElement(required = true)
        protected String attr3;

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

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "logisticsOrders"
    })
    public static class RequestInfo {

        protected List<LogisticsOrders> logisticsOrders;

        public List<LogisticsOrders> getLogisticsOrders() {
            if (logisticsOrders == null) {
                logisticsOrders = new ArrayList<LogisticsOrders>();
            }
            return this.logisticsOrders;
        }

        public void setLogisticsOrders(List<LogisticsOrders> logisticsOrders) {
            this.logisticsOrders = logisticsOrders;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "orderHead",
            "orderLine",
            "orderLineFee",
            "orderLineShip"
        })
        public static class LogisticsOrders {

            protected OrderHead orderHead;
            protected List<OrderLine> orderLine;
            protected List<OrderLineFee> orderLineFee;
            protected List<OrderLineShip> orderLineShip;

            public OrderHead getOrderHead() {
                return orderHead;
            }

            public void setOrderHead(OrderHead value) {
                this.orderHead = value;
            }

            public List<OrderLine> getOrderLine() {
                if (orderLine == null) {
                    orderLine = new ArrayList<OrderLine>();
                }
                return this.orderLine;
            }

            public List<OrderLineFee> getOrderLineFee() {
                if (orderLineFee == null) {
                    orderLineFee = new ArrayList<OrderLineFee>();
                }
                return this.orderLineFee;
            }

            public List<OrderLineShip> getOrderLineShip() {
                if (orderLineShip == null) {
                    orderLineShip = new ArrayList<OrderLineShip>();
                }
                return this.orderLineShip;
            }

            public void setOrderLine(List<OrderLine> orderLine) {
                this.orderLine = orderLine;
            }

            public void setOrderLineFee(List<OrderLineFee> orderLineFee) {
                this.orderLineFee = orderLineFee;
            }

            public void setOrderLineShip(List<OrderLineShip> orderLineShip) {
                this.orderLineShip = orderLineShip;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
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
                "attr6"
            })
            public static class OrderHead {

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

            }


            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
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
                "importExportMethod"
            })
            public static class OrderLine {

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
                protected String importExportMethod;

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
                 * 获取importExportMethod属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getImportExportMethod() {
                    return importExportMethod;
                }

                /**
                 * 设置importExportMethod属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setImportExportMethod(String value) {
                    this.importExportMethod = value;
                }

            }


            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
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
            public static class OrderLineFee {

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


            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
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
            public static class OrderLineShip {

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

        }

    }

}
