
package com.midea.cloud.srm.model.base.soap.erp.dto;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import java.util.List;


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
 *                   &lt;element name="HEADER">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="BUSINESS_GROUP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="SYSTEM_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="REQUEST_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="IF_CATE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="IF_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="USER_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="PASSWORD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="BATCH_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="TOTAL_SEG_COUNT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="SEG_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="CONTEXT">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="BUSINESS_UNIT">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="RECORD">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="ORGANIZATION_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="SHORT_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ORG_INFORMATION5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ENABLED_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="BUSINESS_GROUP_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="COST_ALLOCATION_KEYFLEX_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="LOCATION_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="LOCATION_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="SOFT_CODING_KEYFLEX_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="DATE_FROM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="DATE_TO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="INTERNAL_EXTERNAL_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="INTERNAL_ADDRESS_LINE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="COMPANY_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ORGANIZATION_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ORGANIZATION_TYPE_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="LANGUECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="REQUEST_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="PROGRAM_APPLICATION_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="PROGRAM_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="PROGRAM_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE_CATEGORY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE9" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE11" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE12" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE14" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE15" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE16" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE17" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE18" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE19" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE20" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ERP_LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ERP_LAST_UPDATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ERP_LAST_UPDATE_LOGIN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ERP_CREATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ERP_CREATION_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="OBJECT_VERSION_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="PARTY_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE21" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE22" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE23" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE24" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE25" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE26" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE27" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE28" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE29" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="ATTRIBUTE30" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="CREATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="CREATION_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="LAST_UPDATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="EXTENT_ATTRIBUTES" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
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
@XmlRootElement(name = "REQUEST")
public class REQUEST {

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
     *         &lt;element name="HEADER">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="BUSINESS_GROUP" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="SYSTEM_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="REQUEST_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="IF_CATE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="IF_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="USER_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="PASSWORD" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="BATCH_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="TOTAL_SEG_COUNT" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="SEG_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="CONTEXT">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="BUSINESS_UNIT">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="RECORD">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="ORGANIZATION_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="SHORT_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ORG_INFORMATION5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ENABLED_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="BUSINESS_GROUP_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="COST_ALLOCATION_KEYFLEX_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="LOCATION_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="LOCATION_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="SOFT_CODING_KEYFLEX_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="DATE_FROM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="DATE_TO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="INTERNAL_EXTERNAL_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="INTERNAL_ADDRESS_LINE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="COMPANY_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ORGANIZATION_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ORGANIZATION_TYPE_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="LANGUECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="REQUEST_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="PROGRAM_APPLICATION_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="PROGRAM_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="PROGRAM_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE_CATEGORY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE9" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE11" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE12" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE14" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE15" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE16" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE17" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE18" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE19" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE20" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ERP_LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ERP_LAST_UPDATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ERP_LAST_UPDATE_LOGIN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ERP_CREATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ERP_CREATION_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="OBJECT_VERSION_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="PARTY_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE21" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE22" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE23" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE24" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE25" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE26" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE27" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE28" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE29" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="ATTRIBUTE30" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="CREATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="CREATION_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="LAST_UPDATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="EXTENT_ATTRIBUTES" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "header",
        "context"
    })
    public static class RequestInfo {

        @XmlElement(name = "HEADER", required = true)
        protected HEADER header;
        @XmlElement(name = "CONTEXT", required = true)
        protected CONTEXT context;

        /**
         * 获取header属性的值。
         * 
         * @return
         *     possible object is
         *     {@link HEADER }
         *     
         */
        public HEADER getHEADER() {
            return header;
        }

        /**
         * 设置header属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link HEADER }
         *     
         */
        public void setHEADER(HEADER value) {
            this.header = value;
        }

        /**
         * 获取context属性的值。
         * 
         * @return
         *     possible object is
         *     {@link CONTEXT }
         *     
         */
        public CONTEXT getCONTEXT() {
            return context;
        }

        /**
         * 设置context属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link CONTEXT }
         *     
         */
        public void setCONTEXT(CONTEXT value) {
            this.context = value;
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
         *         &lt;element name="BUSINESS_UNIT">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="RECORD">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="ORGANIZATION_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="SHORT_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ORG_INFORMATION5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ENABLED_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="BUSINESS_GROUP_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="COST_ALLOCATION_KEYFLEX_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="LOCATION_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="LOCATION_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="SOFT_CODING_KEYFLEX_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="DATE_FROM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="DATE_TO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="INTERNAL_EXTERNAL_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="INTERNAL_ADDRESS_LINE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="COMPANY_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ORGANIZATION_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ORGANIZATION_TYPE_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="LANGUECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="REQUEST_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="PROGRAM_APPLICATION_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="PROGRAM_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="PROGRAM_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE_CATEGORY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE9" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE11" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE12" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE14" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE15" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE16" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE17" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE18" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE19" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE20" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ERP_LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ERP_LAST_UPDATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ERP_LAST_UPDATE_LOGIN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ERP_CREATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ERP_CREATION_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="OBJECT_VERSION_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="PARTY_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE21" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE22" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE23" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE24" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE25" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE26" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE27" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE28" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE29" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="ATTRIBUTE30" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="CREATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="CREATION_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="LAST_UPDATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="EXTENT_ATTRIBUTES" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "businessunit"
        })
        public static class CONTEXT {

            @XmlElement(name = "BUSINESS_UNIT", required = true)
            protected BUSINESSUNIT businessunit;

            /**
             * 获取businessunit属性的值。
             * 
             * @return
             *     possible object is
             *     {@link BUSINESSUNIT }
             *     
             */
            public BUSINESSUNIT getBUSINESSUNIT() {
                return businessunit;
            }

            /**
             * 设置businessunit属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link BUSINESSUNIT }
             *     
             */
            public void setBUSINESSUNIT(BUSINESSUNIT value) {
                this.businessunit = value;
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
             *         &lt;element name="RECORD">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="ORGANIZATION_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="SHORT_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ORG_INFORMATION5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ENABLED_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="BUSINESS_GROUP_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="COST_ALLOCATION_KEYFLEX_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="LOCATION_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="LOCATION_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="SOFT_CODING_KEYFLEX_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="DATE_FROM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="DATE_TO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="INTERNAL_EXTERNAL_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="INTERNAL_ADDRESS_LINE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="COMPANY_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ORGANIZATION_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ORGANIZATION_TYPE_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="LANGUECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="REQUEST_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="PROGRAM_APPLICATION_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="PROGRAM_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="PROGRAM_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE_CATEGORY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE9" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE11" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE12" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE14" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE15" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE16" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE17" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE18" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE19" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE20" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ERP_LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ERP_LAST_UPDATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ERP_LAST_UPDATE_LOGIN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ERP_CREATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ERP_CREATION_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="OBJECT_VERSION_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="PARTY_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE21" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE22" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE23" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE24" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE25" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE26" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE27" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE28" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE29" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="ATTRIBUTE30" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CREATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="CREATION_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="LAST_UPDATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="EXTENT_ATTRIBUTES" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                "record"
            })
            public static class BUSINESSUNIT {

                @XmlElement(name = "RECORD", required = true)
                protected List<RECORD> record;

                /**
                 * 获取record属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link RECORD }
                 *     
                 */
                public List<RECORD> getRECORD() {
                    return record;
                }

                /**
                 * 设置record属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RECORD }
                 *     
                 */
                public void setRECORD(List<RECORD>  value) {
                    this.record = value;
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
                 *         &lt;element name="ORGANIZATION_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="SHORT_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ORG_INFORMATION5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ENABLED_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="BUSINESS_GROUP_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="COST_ALLOCATION_KEYFLEX_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="LOCATION_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="LOCATION_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="SOFT_CODING_KEYFLEX_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="DATE_FROM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="DATE_TO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="INTERNAL_EXTERNAL_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="INTERNAL_ADDRESS_LINE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="COMPANY_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ORGANIZATION_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ORGANIZATION_TYPE_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="LANGUECODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="REQUEST_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="PROGRAM_APPLICATION_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="PROGRAM_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="PROGRAM_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE_CATEGORY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE9" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE11" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE12" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE14" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE15" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE16" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE17" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE18" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE19" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE20" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ERP_LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ERP_LAST_UPDATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ERP_LAST_UPDATE_LOGIN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ERP_CREATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ERP_CREATION_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="OBJECT_VERSION_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="PARTY_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE21" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE22" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE23" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE24" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE25" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE26" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE27" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE28" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE29" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="ATTRIBUTE30" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CREATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="CREATION_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="LAST_UPDATED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="EXTENT_ATTRIBUTES" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                    "organizationid",
                    "shortcode",
                    "name",
                    "orginformation5",
                    "enabledflag",
                    "businessgroupid",
                    "costallocationkeyflexid",
                    "locationid",
                    "locationname",
                    "softcodingkeyflexid",
                    "datefrom",
                    "dateto",
                    "internalexternalflag",
                    "internaladdressline",
                    "companyname",
                    "organizationtypecode",
                    "organizationtypename",
                    "languecode",
                    "requestid",
                    "programapplicationid",
                    "programid",
                    "programupdatedate",
                    "attributecategory",
                    "attribute1",
                    "attribute2",
                    "attribute3",
                    "attribute4",
                    "attribute5",
                    "attribute6",
                    "attribute7",
                    "attribute8",
                    "attribute9",
                    "attribute10",
                    "attribute11",
                    "attribute12",
                    "attribute13",
                    "attribute14",
                    "attribute15",
                    "attribute16",
                    "attribute17",
                    "attribute18",
                    "attribute19",
                    "attribute20",
                    "erplastupdatedate",
                    "erplastupdatedby",
                    "erplastupdatelogin",
                    "erpcreatedby",
                    "erpcreationdate",
                    "objectversionnumber",
                    "partyid",
                    "attribute21",
                    "attribute22",
                    "attribute23",
                    "attribute24",
                    "attribute25",
                    "attribute26",
                    "attribute27",
                    "attribute28",
                    "attribute29",
                    "attribute30",
                    "createdby",
                    "creationdate",
                    "lastupdatedby",
                    "lastupdatedate",
                    "extentattributes"
                })
                public static class RECORD {

                    @XmlElementRef(name = "ORGANIZATION_ID", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> organizationid;
                    @XmlElementRef(name = "SHORT_CODE", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> shortcode;
                    @XmlElementRef(name = "NAME", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> name;
                    @XmlElementRef(name = "ORG_INFORMATION5", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> orginformation5;
                    @XmlElementRef(name = "ENABLED_FLAG", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> enabledflag;
                    @XmlElementRef(name = "BUSINESS_GROUP_ID", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> businessgroupid;
                    @XmlElementRef(name = "COST_ALLOCATION_KEYFLEX_ID", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> costallocationkeyflexid;
                    @XmlElementRef(name = "LOCATION_ID", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> locationid;
                    @XmlElementRef(name = "LOCATION_NAME", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> locationname;
                    @XmlElementRef(name = "SOFT_CODING_KEYFLEX_ID", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> softcodingkeyflexid;
                    @XmlElementRef(name = "DATE_FROM", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> datefrom;
                    @XmlElementRef(name = "DATE_TO", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> dateto;
                    @XmlElementRef(name = "INTERNAL_EXTERNAL_FLAG", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> internalexternalflag;
                    @XmlElementRef(name = "INTERNAL_ADDRESS_LINE", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> internaladdressline;
                    @XmlElementRef(name = "COMPANY_NAME", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> companyname;
                    @XmlElementRef(name = "ORGANIZATION_TYPE_CODE", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> organizationtypecode;
                    @XmlElementRef(name = "ORGANIZATION_TYPE_NAME", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> organizationtypename;
                    @XmlElementRef(name = "LANGUECODE", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> languecode;
                    @XmlElementRef(name = "REQUEST_ID", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> requestid;
                    @XmlElementRef(name = "PROGRAM_APPLICATION_ID", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> programapplicationid;
                    @XmlElementRef(name = "PROGRAM_ID", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> programid;
                    @XmlElementRef(name = "PROGRAM_UPDATE_DATE", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> programupdatedate;
                    @XmlElementRef(name = "ATTRIBUTE_CATEGORY", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attributecategory;
                    @XmlElementRef(name = "ATTRIBUTE1", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute1;
                    @XmlElementRef(name = "ATTRIBUTE2", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute2;
                    @XmlElementRef(name = "ATTRIBUTE3", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute3;
                    @XmlElementRef(name = "ATTRIBUTE4", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute4;
                    @XmlElementRef(name = "ATTRIBUTE5", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute5;
                    @XmlElementRef(name = "ATTRIBUTE6", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute6;
                    @XmlElementRef(name = "ATTRIBUTE7", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute7;
                    @XmlElementRef(name = "ATTRIBUTE8", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute8;
                    @XmlElementRef(name = "ATTRIBUTE9", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute9;
                    @XmlElementRef(name = "ATTRIBUTE10", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute10;
                    @XmlElementRef(name = "ATTRIBUTE11", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute11;
                    @XmlElementRef(name = "ATTRIBUTE12", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute12;
                    @XmlElementRef(name = "ATTRIBUTE13", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute13;
                    @XmlElementRef(name = "ATTRIBUTE14", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute14;
                    @XmlElementRef(name = "ATTRIBUTE15", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute15;
                    @XmlElementRef(name = "ATTRIBUTE16", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute16;
                    @XmlElementRef(name = "ATTRIBUTE17", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute17;
                    @XmlElementRef(name = "ATTRIBUTE18", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute18;
                    @XmlElementRef(name = "ATTRIBUTE19", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute19;
                    @XmlElementRef(name = "ATTRIBUTE20", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute20;
                    @XmlElementRef(name = "ERP_LAST_UPDATE_DATE", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> erplastupdatedate;
                    @XmlElementRef(name = "ERP_LAST_UPDATED_BY", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> erplastupdatedby;
                    @XmlElementRef(name = "ERP_LAST_UPDATE_LOGIN", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> erplastupdatelogin;
                    @XmlElementRef(name = "ERP_CREATED_BY", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> erpcreatedby;
                    @XmlElementRef(name = "ERP_CREATION_DATE", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> erpcreationdate;
                    @XmlElementRef(name = "OBJECT_VERSION_NUMBER", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> objectversionnumber;
                    @XmlElementRef(name = "PARTY_ID", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> partyid;
                    @XmlElementRef(name = "ATTRIBUTE21", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute21;
                    @XmlElementRef(name = "ATTRIBUTE22", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute22;
                    @XmlElementRef(name = "ATTRIBUTE23", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute23;
                    @XmlElementRef(name = "ATTRIBUTE24", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute24;
                    @XmlElementRef(name = "ATTRIBUTE25", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute25;
                    @XmlElementRef(name = "ATTRIBUTE26", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute26;
                    @XmlElementRef(name = "ATTRIBUTE27", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute27;
                    @XmlElementRef(name = "ATTRIBUTE28", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute28;
                    @XmlElementRef(name = "ATTRIBUTE29", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute29;
                    @XmlElementRef(name = "ATTRIBUTE30", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> attribute30;
                    @XmlElementRef(name = "CREATED_BY", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> createdby;
                    @XmlElementRef(name = "CREATION_DATE", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> creationdate;
                    @XmlElementRef(name = "LAST_UPDATED_BY", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> lastupdatedby;
                    @XmlElementRef(name = "LAST_UPDATE_DATE", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> lastupdatedate;
                    @XmlElementRef(name = "EXTENT_ATTRIBUTES", namespace = "http://www.aurora-framework.org/schema", type = JAXBElement.class, required = false)
                    protected JAXBElement<String> extentattributes;

                    /**
                     * 获取organizationid属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getORGANIZATIONID() {
                        return organizationid;
                    }

                    /**
                     * 设置organizationid属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setORGANIZATIONID(JAXBElement<String> value) {
                        this.organizationid = value;
                    }

                    /**
                     * 获取shortcode属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getSHORTCODE() {
                        return shortcode;
                    }

                    /**
                     * 设置shortcode属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setSHORTCODE(JAXBElement<String> value) {
                        this.shortcode = value;
                    }

                    /**
                     * 获取name属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getNAME() {
                        return name;
                    }

                    /**
                     * 设置name属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setNAME(JAXBElement<String> value) {
                        this.name = value;
                    }

                    /**
                     * 获取orginformation5属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getORGINFORMATION5() {
                        return orginformation5;
                    }

                    /**
                     * 设置orginformation5属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setORGINFORMATION5(JAXBElement<String> value) {
                        this.orginformation5 = value;
                    }

                    /**
                     * 获取enabledflag属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getENABLEDFLAG() {
                        return enabledflag;
                    }

                    /**
                     * 设置enabledflag属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setENABLEDFLAG(JAXBElement<String> value) {
                        this.enabledflag = value;
                    }

                    /**
                     * 获取businessgroupid属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getBUSINESSGROUPID() {
                        return businessgroupid;
                    }

                    /**
                     * 设置businessgroupid属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setBUSINESSGROUPID(JAXBElement<String> value) {
                        this.businessgroupid = value;
                    }

                    /**
                     * 获取costallocationkeyflexid属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getCOSTALLOCATIONKEYFLEXID() {
                        return costallocationkeyflexid;
                    }

                    /**
                     * 设置costallocationkeyflexid属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setCOSTALLOCATIONKEYFLEXID(JAXBElement<String> value) {
                        this.costallocationkeyflexid = value;
                    }

                    /**
                     * 获取locationid属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getLOCATIONID() {
                        return locationid;
                    }

                    /**
                     * 设置locationid属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setLOCATIONID(JAXBElement<String> value) {
                        this.locationid = value;
                    }

                    /**
                     * 获取locationname属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getLOCATIONNAME() {
                        return locationname;
                    }

                    /**
                     * 设置locationname属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setLOCATIONNAME(JAXBElement<String> value) {
                        this.locationname = value;
                    }

                    /**
                     * 获取softcodingkeyflexid属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getSOFTCODINGKEYFLEXID() {
                        return softcodingkeyflexid;
                    }

                    /**
                     * 设置softcodingkeyflexid属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setSOFTCODINGKEYFLEXID(JAXBElement<String> value) {
                        this.softcodingkeyflexid = value;
                    }

                    /**
                     * 获取datefrom属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getDATEFROM() {
                        return datefrom;
                    }

                    /**
                     * 设置datefrom属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setDATEFROM(JAXBElement<String> value) {
                        this.datefrom = value;
                    }

                    /**
                     * 获取dateto属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getDATETO() {
                        return dateto;
                    }

                    /**
                     * 设置dateto属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setDATETO(JAXBElement<String> value) {
                        this.dateto = value;
                    }

                    /**
                     * 获取internalexternalflag属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getINTERNALEXTERNALFLAG() {
                        return internalexternalflag;
                    }

                    /**
                     * 设置internalexternalflag属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setINTERNALEXTERNALFLAG(JAXBElement<String> value) {
                        this.internalexternalflag = value;
                    }

                    /**
                     * 获取internaladdressline属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getINTERNALADDRESSLINE() {
                        return internaladdressline;
                    }

                    /**
                     * 设置internaladdressline属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setINTERNALADDRESSLINE(JAXBElement<String> value) {
                        this.internaladdressline = value;
                    }

                    /**
                     * 获取companyname属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getCOMPANYNAME() {
                        return companyname;
                    }

                    /**
                     * 设置companyname属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setCOMPANYNAME(JAXBElement<String> value) {
                        this.companyname = value;
                    }

                    /**
                     * 获取organizationtypecode属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getORGANIZATIONTYPECODE() {
                        return organizationtypecode;
                    }

                    /**
                     * 设置organizationtypecode属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setORGANIZATIONTYPECODE(JAXBElement<String> value) {
                        this.organizationtypecode = value;
                    }

                    /**
                     * 获取organizationtypename属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getORGANIZATIONTYPENAME() {
                        return organizationtypename;
                    }

                    /**
                     * 设置organizationtypename属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setORGANIZATIONTYPENAME(JAXBElement<String> value) {
                        this.organizationtypename = value;
                    }

                    /**
                     * 获取languecode属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getLANGUECODE() {
                        return languecode;
                    }

                    /**
                     * 设置languecode属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setLANGUECODE(JAXBElement<String> value) {
                        this.languecode = value;
                    }

                    /**
                     * 获取requestid属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getREQUESTID() {
                        return requestid;
                    }

                    /**
                     * 设置requestid属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setREQUESTID(JAXBElement<String> value) {
                        this.requestid = value;
                    }

                    /**
                     * 获取programapplicationid属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getPROGRAMAPPLICATIONID() {
                        return programapplicationid;
                    }

                    /**
                     * 设置programapplicationid属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setPROGRAMAPPLICATIONID(JAXBElement<String> value) {
                        this.programapplicationid = value;
                    }

                    /**
                     * 获取programid属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getPROGRAMID() {
                        return programid;
                    }

                    /**
                     * 设置programid属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setPROGRAMID(JAXBElement<String> value) {
                        this.programid = value;
                    }

                    /**
                     * 获取programupdatedate属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getPROGRAMUPDATEDATE() {
                        return programupdatedate;
                    }

                    /**
                     * 设置programupdatedate属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setPROGRAMUPDATEDATE(JAXBElement<String> value) {
                        this.programupdatedate = value;
                    }

                    /**
                     * 获取attributecategory属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTECATEGORY() {
                        return attributecategory;
                    }

                    /**
                     * 设置attributecategory属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTECATEGORY(JAXBElement<String> value) {
                        this.attributecategory = value;
                    }

                    /**
                     * 获取attribute1属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE1() {
                        return attribute1;
                    }

                    /**
                     * 设置attribute1属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE1(JAXBElement<String> value) {
                        this.attribute1 = value;
                    }

                    /**
                     * 获取attribute2属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE2() {
                        return attribute2;
                    }

                    /**
                     * 设置attribute2属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE2(JAXBElement<String> value) {
                        this.attribute2 = value;
                    }

                    /**
                     * 获取attribute3属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE3() {
                        return attribute3;
                    }

                    /**
                     * 设置attribute3属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE3(JAXBElement<String> value) {
                        this.attribute3 = value;
                    }

                    /**
                     * 获取attribute4属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE4() {
                        return attribute4;
                    }

                    /**
                     * 设置attribute4属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE4(JAXBElement<String> value) {
                        this.attribute4 = value;
                    }

                    /**
                     * 获取attribute5属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE5() {
                        return attribute5;
                    }

                    /**
                     * 设置attribute5属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE5(JAXBElement<String> value) {
                        this.attribute5 = value;
                    }

                    /**
                     * 获取attribute6属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE6() {
                        return attribute6;
                    }

                    /**
                     * 设置attribute6属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE6(JAXBElement<String> value) {
                        this.attribute6 = value;
                    }

                    /**
                     * 获取attribute7属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE7() {
                        return attribute7;
                    }

                    /**
                     * 设置attribute7属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE7(JAXBElement<String> value) {
                        this.attribute7 = value;
                    }

                    /**
                     * 获取attribute8属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE8() {
                        return attribute8;
                    }

                    /**
                     * 设置attribute8属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE8(JAXBElement<String> value) {
                        this.attribute8 = value;
                    }

                    /**
                     * 获取attribute9属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE9() {
                        return attribute9;
                    }

                    /**
                     * 设置attribute9属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE9(JAXBElement<String> value) {
                        this.attribute9 = value;
                    }

                    /**
                     * 获取attribute10属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE10() {
                        return attribute10;
                    }

                    /**
                     * 设置attribute10属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE10(JAXBElement<String> value) {
                        this.attribute10 = value;
                    }

                    /**
                     * 获取attribute11属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE11() {
                        return attribute11;
                    }

                    /**
                     * 设置attribute11属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE11(JAXBElement<String> value) {
                        this.attribute11 = value;
                    }

                    /**
                     * 获取attribute12属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE12() {
                        return attribute12;
                    }

                    /**
                     * 设置attribute12属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE12(JAXBElement<String> value) {
                        this.attribute12 = value;
                    }

                    /**
                     * 获取attribute13属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE13() {
                        return attribute13;
                    }

                    /**
                     * 设置attribute13属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE13(JAXBElement<String> value) {
                        this.attribute13 = value;
                    }

                    /**
                     * 获取attribute14属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE14() {
                        return attribute14;
                    }

                    /**
                     * 设置attribute14属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE14(JAXBElement<String> value) {
                        this.attribute14 = value;
                    }

                    /**
                     * 获取attribute15属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE15() {
                        return attribute15;
                    }

                    /**
                     * 设置attribute15属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE15(JAXBElement<String> value) {
                        this.attribute15 = value;
                    }

                    /**
                     * 获取attribute16属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE16() {
                        return attribute16;
                    }

                    /**
                     * 设置attribute16属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE16(JAXBElement<String> value) {
                        this.attribute16 = value;
                    }

                    /**
                     * 获取attribute17属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE17() {
                        return attribute17;
                    }

                    /**
                     * 设置attribute17属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE17(JAXBElement<String> value) {
                        this.attribute17 = value;
                    }

                    /**
                     * 获取attribute18属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE18() {
                        return attribute18;
                    }

                    /**
                     * 设置attribute18属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE18(JAXBElement<String> value) {
                        this.attribute18 = value;
                    }

                    /**
                     * 获取attribute19属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE19() {
                        return attribute19;
                    }

                    /**
                     * 设置attribute19属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE19(JAXBElement<String> value) {
                        this.attribute19 = value;
                    }

                    /**
                     * 获取attribute20属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE20() {
                        return attribute20;
                    }

                    /**
                     * 设置attribute20属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE20(JAXBElement<String> value) {
                        this.attribute20 = value;
                    }

                    /**
                     * 获取erplastupdatedate属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getERPLASTUPDATEDATE() {
                        return erplastupdatedate;
                    }

                    /**
                     * 设置erplastupdatedate属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setERPLASTUPDATEDATE(JAXBElement<String> value) {
                        this.erplastupdatedate = value;
                    }

                    /**
                     * 获取erplastupdatedby属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getERPLASTUPDATEDBY() {
                        return erplastupdatedby;
                    }

                    /**
                     * 设置erplastupdatedby属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setERPLASTUPDATEDBY(JAXBElement<String> value) {
                        this.erplastupdatedby = value;
                    }

                    /**
                     * 获取erplastupdatelogin属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getERPLASTUPDATELOGIN() {
                        return erplastupdatelogin;
                    }

                    /**
                     * 设置erplastupdatelogin属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setERPLASTUPDATELOGIN(JAXBElement<String> value) {
                        this.erplastupdatelogin = value;
                    }

                    /**
                     * 获取erpcreatedby属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getERPCREATEDBY() {
                        return erpcreatedby;
                    }

                    /**
                     * 设置erpcreatedby属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setERPCREATEDBY(JAXBElement<String> value) {
                        this.erpcreatedby = value;
                    }

                    /**
                     * 获取erpcreationdate属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getERPCREATIONDATE() {
                        return erpcreationdate;
                    }

                    /**
                     * 设置erpcreationdate属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setERPCREATIONDATE(JAXBElement<String> value) {
                        this.erpcreationdate = value;
                    }

                    /**
                     * 获取objectversionnumber属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getOBJECTVERSIONNUMBER() {
                        return objectversionnumber;
                    }

                    /**
                     * 设置objectversionnumber属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setOBJECTVERSIONNUMBER(JAXBElement<String> value) {
                        this.objectversionnumber = value;
                    }

                    /**
                     * 获取partyid属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getPARTYID() {
                        return partyid;
                    }

                    /**
                     * 设置partyid属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setPARTYID(JAXBElement<String> value) {
                        this.partyid = value;
                    }

                    /**
                     * 获取attribute21属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE21() {
                        return attribute21;
                    }

                    /**
                     * 设置attribute21属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE21(JAXBElement<String> value) {
                        this.attribute21 = value;
                    }

                    /**
                     * 获取attribute22属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE22() {
                        return attribute22;
                    }

                    /**
                     * 设置attribute22属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE22(JAXBElement<String> value) {
                        this.attribute22 = value;
                    }

                    /**
                     * 获取attribute23属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE23() {
                        return attribute23;
                    }

                    /**
                     * 设置attribute23属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE23(JAXBElement<String> value) {
                        this.attribute23 = value;
                    }

                    /**
                     * 获取attribute24属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE24() {
                        return attribute24;
                    }

                    /**
                     * 设置attribute24属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE24(JAXBElement<String> value) {
                        this.attribute24 = value;
                    }

                    /**
                     * 获取attribute25属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE25() {
                        return attribute25;
                    }

                    /**
                     * 设置attribute25属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE25(JAXBElement<String> value) {
                        this.attribute25 = value;
                    }

                    /**
                     * 获取attribute26属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE26() {
                        return attribute26;
                    }

                    /**
                     * 设置attribute26属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE26(JAXBElement<String> value) {
                        this.attribute26 = value;
                    }

                    /**
                     * 获取attribute27属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE27() {
                        return attribute27;
                    }

                    /**
                     * 设置attribute27属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE27(JAXBElement<String> value) {
                        this.attribute27 = value;
                    }

                    /**
                     * 获取attribute28属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE28() {
                        return attribute28;
                    }

                    /**
                     * 设置attribute28属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE28(JAXBElement<String> value) {
                        this.attribute28 = value;
                    }

                    /**
                     * 获取attribute29属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE29() {
                        return attribute29;
                    }

                    /**
                     * 设置attribute29属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE29(JAXBElement<String> value) {
                        this.attribute29 = value;
                    }

                    /**
                     * 获取attribute30属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getATTRIBUTE30() {
                        return attribute30;
                    }

                    /**
                     * 设置attribute30属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setATTRIBUTE30(JAXBElement<String> value) {
                        this.attribute30 = value;
                    }

                    /**
                     * 获取createdby属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getCREATEDBY() {
                        return createdby;
                    }

                    /**
                     * 设置createdby属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setCREATEDBY(JAXBElement<String> value) {
                        this.createdby = value;
                    }

                    /**
                     * 获取creationdate属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getCREATIONDATE() {
                        return creationdate;
                    }

                    /**
                     * 设置creationdate属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setCREATIONDATE(JAXBElement<String> value) {
                        this.creationdate = value;
                    }

                    /**
                     * 获取lastupdatedby属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getLASTUPDATEDBY() {
                        return lastupdatedby;
                    }

                    /**
                     * 设置lastupdatedby属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setLASTUPDATEDBY(JAXBElement<String> value) {
                        this.lastupdatedby = value;
                    }

                    /**
                     * 获取lastupdatedate属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getLASTUPDATEDATE() {
                        return lastupdatedate;
                    }

                    /**
                     * 设置lastupdatedate属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setLASTUPDATEDATE(JAXBElement<String> value) {
                        this.lastupdatedate = value;
                    }

                    /**
                     * 获取extentattributes属性的值。
                     * 
                     * @return
                     *     possible object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public JAXBElement<String> getEXTENTATTRIBUTES() {
                        return extentattributes;
                    }

                    /**
                     * 设置extentattributes属性的值。
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link JAXBElement }{@code <}{@link String }{@code >}
                     *     
                     */
                    public void setEXTENTATTRIBUTES(JAXBElement<String> value) {
                        this.extentattributes = value;
                    }

                }

            }

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
         *         &lt;element name="BUSINESS_GROUP" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="SYSTEM_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="REQUEST_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="IF_CATE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="IF_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="USER_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="PASSWORD" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="BATCH_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="TOTAL_SEG_COUNT" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="SEG_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
            "businessgroup",
            "systemcode",
            "requestid",
            "ifcatecode",
            "ifcode",
            "username",
            "password",
            "batchnum",
            "totalsegcount",
            "segnum"
        })
        public static class HEADER {

            @XmlElement(name = "BUSINESS_GROUP", required = true)
            protected String businessgroup;
            @XmlElement(name = "SYSTEM_CODE", required = true)
            protected String systemcode;
            @XmlElement(name = "REQUEST_ID", required = true)
            protected String requestid;
            @XmlElement(name = "IF_CATE_CODE", required = true)
            protected String ifcatecode;
            @XmlElement(name = "IF_CODE", required = true)
            protected String ifcode;
            @XmlElement(name = "USER_NAME", required = true)
            protected String username;
            @XmlElement(name = "PASSWORD", required = true)
            protected String password;
            @XmlElement(name = "BATCH_NUM", required = true)
            protected String batchnum;
            @XmlElement(name = "TOTAL_SEG_COUNT", required = true)
            protected String totalsegcount;
            @XmlElement(name = "SEG_NUM", required = true)
            protected String segnum;

            /**
             * 获取businessgroup属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBUSINESSGROUP() {
                return businessgroup;
            }

            /**
             * 设置businessgroup属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBUSINESSGROUP(String value) {
                this.businessgroup = value;
            }

            /**
             * 获取systemcode属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSYSTEMCODE() {
                return systemcode;
            }

            /**
             * 设置systemcode属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSYSTEMCODE(String value) {
                this.systemcode = value;
            }

            /**
             * 获取requestid属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getREQUESTID() {
                return requestid;
            }

            /**
             * 设置requestid属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setREQUESTID(String value) {
                this.requestid = value;
            }

            /**
             * 获取ifcatecode属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIFCATECODE() {
                return ifcatecode;
            }

            /**
             * 设置ifcatecode属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIFCATECODE(String value) {
                this.ifcatecode = value;
            }

            /**
             * 获取ifcode属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIFCODE() {
                return ifcode;
            }

            /**
             * 设置ifcode属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIFCODE(String value) {
                this.ifcode = value;
            }

            /**
             * 获取username属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUSERNAME() {
                return username;
            }

            /**
             * 设置username属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUSERNAME(String value) {
                this.username = value;
            }

            /**
             * 获取password属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPASSWORD() {
                return password;
            }

            /**
             * 设置password属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPASSWORD(String value) {
                this.password = value;
            }

            /**
             * 获取batchnum属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBATCHNUM() {
                return batchnum;
            }

            /**
             * 设置batchnum属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBATCHNUM(String value) {
                this.batchnum = value;
            }

            /**
             * 获取totalsegcount属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTOTALSEGCOUNT() {
                return totalsegcount;
            }

            /**
             * 设置totalsegcount属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTOTALSEGCOUNT(String value) {
                this.totalsegcount = value;
            }

            /**
             * 获取segnum属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSEGNUM() {
                return segnum;
            }

            /**
             * 设置segnum属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSEGNUM(String value) {
                this.segnum = value;
            }

        }

    }

}
