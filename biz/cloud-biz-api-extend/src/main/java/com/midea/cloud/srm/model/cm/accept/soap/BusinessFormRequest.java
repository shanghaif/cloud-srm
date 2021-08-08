
package com.midea.cloud.srm.model.cm.accept.soap;

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
 *         &lt;element name="BUSINESS_FORMS">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="BUSINESS_FORM" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="sourceSystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="oaNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="createdate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="employeeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="leCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="leName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="operationTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="paperAccessories" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="boeAbstract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="linkAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "businessforms"
})
@XmlRootElement(name = "businessFormRequest")
public class BusinessFormRequest {

    @XmlElement(name = "BUSINESS_FORMS", required = true)
    protected BUSINESSFORMS businessforms;

    /**
     * 获取businessforms属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BUSINESSFORMS }
     *     
     */
    public BUSINESSFORMS getBUSINESSFORMS() {
        return businessforms;
    }

    /**
     * 设置businessforms属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BUSINESSFORMS }
     *     
     */
    public void setBUSINESSFORMS(BUSINESSFORMS value) {
        this.businessforms = value;
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
     *         &lt;element name="BUSINESS_FORM" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="sourceSystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="oaNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="createdate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="employeeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="leCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="leName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="operationTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="paperAccessories" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="boeAbstract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="linkAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "businessform"
    })
    public static class BUSINESSFORMS {

        @XmlElement(name = "BUSINESS_FORM")
        protected List<BUSINESSFORM> businessform;

        /**
         * Gets the value of the businessform property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the businessform property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBUSINESSFORM().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BUSINESSFORM }
         * 
         * 
         */
        public List<BUSINESSFORM> getBUSINESSFORM() {
            if (businessform == null) {
                businessform = new ArrayList<BUSINESSFORM>();
            }
            return this.businessform;
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
         *         &lt;element name="sourceSystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="oaNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="createdate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="employeeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="leCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="leName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="operationTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="paperAccessories" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="boeAbstract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="linkAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "sourceSystem",
            "oaNo",
            "createdate",
            "employeeId",
            "leCode",
            "leName",
            "operationTypeName",
            "paperAccessories",
            "boeAbstract",
            "linkAddress"
        })
        public static class BUSINESSFORM {
            @Override
            public String toString() {
                    return "BUSINESSFORM{" +
                            "\"sourceSystem\"=\"" + sourceSystem + '\"' +
                            ", \"oaNo\"=\"" + oaNo + '\"' +
                            ", \"createdate\"=\"" + createdate + '\"' +
                            ", \"employeeId\"=\"" + employeeId + '\"' +
                            ", \"leCod\"e=\"" + leCode + '\"' +
                            ", \"leName\"=\"" + leName + '\"' +
                            ", \"operationTypeName\"=\"" + operationTypeName + '\"' +
                            ", \"paperAccessories\"=\"" + paperAccessories + '\"' +
                            ", \"boeAbstract\"=\"" + boeAbstract + '\"' +
                            ", \"linkAddress\"=\"" + linkAddress + '\"' +
                            '}';
            }

            protected String sourceSystem;
            protected String oaNo;
            protected String createdate;
            protected String employeeId;
            protected String leCode;
            protected String leName;
            protected String operationTypeName;
            protected String paperAccessories;
            protected String boeAbstract;
            protected String linkAddress;

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
             * 获取oaNo属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOaNo() {
                return oaNo;
            }

            /**
             * 设置oaNo属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOaNo(String value) {
                this.oaNo = value;
            }

            /**
             * 获取createdate属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCreatedate() {
                return createdate;
            }

            /**
             * 设置createdate属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCreatedate(String value) {
                this.createdate = value;
            }

            /**
             * 获取employeeId属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEmployeeId() {
                return employeeId;
            }

            /**
             * 设置employeeId属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEmployeeId(String value) {
                this.employeeId = value;
            }

            /**
             * 获取leCode属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLeCode() {
                return leCode;
            }

            /**
             * 设置leCode属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLeCode(String value) {
                this.leCode = value;
            }

            /**
             * 获取leName属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLeName() {
                return leName;
            }

            /**
             * 设置leName属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLeName(String value) {
                this.leName = value;
            }

            /**
             * 获取operationTypeName属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOperationTypeName() {
                return operationTypeName;
            }

            /**
             * 设置operationTypeName属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOperationTypeName(String value) {
                this.operationTypeName = value;
            }

            /**
             * 获取paperAccessories属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPaperAccessories() {
                return paperAccessories;
            }

            /**
             * 设置paperAccessories属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPaperAccessories(String value) {
                this.paperAccessories = value;
            }

            /**
             * 获取boeAbstract属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBoeAbstract() {
                return boeAbstract;
            }

            /**
             * 设置boeAbstract属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBoeAbstract(String value) {
                this.boeAbstract = value;
            }

            /**
             * 获取linkAddress属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLinkAddress() {
                return linkAddress;
            }

            /**
             * 设置linkAddress属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLinkAddress(String value) {
                this.linkAddress = value;
            }

        }

    }

}
