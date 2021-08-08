
package com.midea.cloud.srm.model.base.soap.erp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;


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
 *         &lt;element name="RESPONSE">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="esbInfo">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="instId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="returnStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="returnCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="returnMsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="requestTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="responseTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="attr1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="attr2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="attr3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="resultInfo">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="RESPONSE_STATUS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="RESPONSE_MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
 *       &lt;attribute name="success" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "response"
})
@XmlRootElement(name = "soapResponse")
public class SoapResponse {

    @XmlElement(name = "RESPONSE", required = true)
    protected RESPONSE response;
    @XmlAttribute(name = "success")
    protected String success;

    /**
     * 获取response属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RESPONSE }
     *     
     */
    public RESPONSE getResponse() {
        return response;
    }

    /**
     * 设置response属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RESPONSE }
     *     
     */
    public void setResponse(RESPONSE value) {
        this.response = value;
    }

    /**
     * 获取success属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuccess() {
        return success;
    }

    /**
     * 设置success属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuccess(String value) {
        this.success = value;
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
     *         &lt;element name="esbInfo">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="instId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="returnStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="returnCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="returnMsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="requestTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="responseTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="attr1" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="attr2" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="attr3" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="resultInfo">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="RESPONSE_STATUS" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="RESPONSE_MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "resultInfo"
    })

    public static class RESPONSE {

        @XmlElement(required = true)
        protected EsbInfo esbInfo;
        @XmlElement(required = true)
        protected ResultInfo resultInfo;

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
         * 获取resultInfo属性的值。
         * 
         * @return
         *     possible object is
         *     {@link ResultInfo }
         *     
         */
        public ResultInfo getResultInfo() {
            return resultInfo;
        }

        /**
         * 设置resultInfo属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link ResultInfo }
         *     
         */
        public void setResultInfo(ResultInfo value) {
            this.resultInfo = value;
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
         *         &lt;element name="returnStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="returnCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="returnMsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="requestTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="responseTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
            "returnStatus",
            "returnCode",
            "returnMsg",
            "requestTime",
            "responseTime",
            "attr1",
            "attr2",
            "attr3"
        })
        public static class EsbInfo {

            @XmlElement(required = true)
            protected String instId;
            @XmlElement(required = true)
            protected String returnStatus;
            @XmlElement(required = true)
            protected String returnCode;
            @XmlElement(required = true)
            protected String returnMsg;
            @XmlElement(required = true)
            protected String requestTime;
            @XmlElement(required = true)
            protected String responseTime;
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
             * 获取returnStatus属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getReturnStatus() {
                return returnStatus;
            }

            /**
             * 设置returnStatus属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setReturnStatus(String value) {
                this.returnStatus = value;
            }

            /**
             * 获取returnCode属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getReturnCode() {
                return returnCode;
            }

            /**
             * 设置returnCode属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setReturnCode(String value) {
                this.returnCode = value;
            }

            /**
             * 获取returnMsg属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getReturnMsg() {
                return returnMsg;
            }

            /**
             * 设置returnMsg属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setReturnMsg(String value) {
                this.returnMsg = value;
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
             * 获取responseTime属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getResponseTime() {
                return responseTime;
            }

            /**
             * 设置responseTime属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setResponseTime(String value) {
                this.responseTime = value;
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
         *         &lt;element name="RESPONSE_STATUS" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="RESPONSE_MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
            "responsestatus",
            "responsemessage"
        })
        public static class ResultInfo {

            @XmlElement(name = "RESPONSE_STATUS", required = true)
            protected String responsestatus;
            @XmlElement(name = "RESPONSE_MESSAGE", required = true)
            protected String responsemessage;

            /**
             * 获取responsestatus属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRESPONSESTATUS() {
                return responsestatus;
            }

            /**
             * 设置responsestatus属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRESPONSESTATUS(String value) {
                this.responsestatus = value;
            }

            /**
             * 获取responsemessage属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRESPONSEMESSAGE() {
                return responsemessage;
            }

            /**
             * 设置responsemessage属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRESPONSEMESSAGE(String value) {
                this.responsemessage = value;
            }

        }

    }

}
