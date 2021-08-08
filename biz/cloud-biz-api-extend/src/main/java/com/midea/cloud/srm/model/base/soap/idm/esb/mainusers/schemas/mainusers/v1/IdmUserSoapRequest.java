
package com.midea.cloud.srm.model.base.soap.idm.esb.mainusers.schemas.mainusers.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
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
 *                   &lt;element name="mainUsers">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="SysCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="mainUser" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="USERID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="USER_NAME_C" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="USER_TEL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="USER_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="USER_EMAIL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="COMPANY_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="PASSWORD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="EDI_BPM_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="EDI_BPM_TIME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="STATUS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="Attr1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="Attr2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="Attr3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="Attr4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="Attr5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlRootElement(name = "request", namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0")
public class IdmUserSoapRequest {

    @XmlElement(required = true, namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0")
    protected EsbInfo esbInfo;
    @XmlElement(required = true, namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0")
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


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "instId",
        "requestTime",
        "attr1",
        "attr2",
        "attr3"
    })
    public static class EsbInfo {

        @XmlElement(required = true, namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0")
        protected String instId;
        @XmlElement(required = true, namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0")
        protected String requestTime;
        @XmlElement(required = true, namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0")
        protected String attr1;
        @XmlElement(required = true, namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0")
        protected String attr2;
        @XmlElement(required = true, namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0")
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
        "mainUsers"
    })
    public static class RequestInfo {

        @XmlElement(required = true)
        protected MainUsers mainUsers;

        /**
         * 获取mainUsers属性的值。
         * 
         * @return
         *     possible object is
         *     {@link MainUsers }
         *     
         */
        public MainUsers getMainUsers() {
            return mainUsers;
        }

        /**
         * 设置mainUsers属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link MainUsers }
         *     
         */
        public void setMainUsers(MainUsers value) {
            this.mainUsers = value;
        }


        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "sysCode",
            "mainUser"
        })
        public static class MainUsers {

            @XmlElement(name = "SysCode", namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", required = false)
            protected String sysCode;
            protected List<MainUser> mainUser;

            /**
             * 获取sysCode属性的值。
             * 
             * @return
             *     possible object is
             *     {@link JAXBElement }{@code <}{@link String }{@code >}
             *     
             */
            public String getSysCode() {
                return sysCode;
            }

            /**
             * 设置sysCode属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link JAXBElement }{@code <}{@link String }{@code >}
             *     
             */
            public void setSysCode(String value) {
                this.sysCode = value;
            }


            public List<MainUser> getMainUser() {
                if (mainUser == null) {
                    mainUser = new ArrayList<MainUser>();
                }
                return this.mainUser;
            }

            public void setMainUser(List<MainUser> mainUser) {
                this.mainUser = mainUser;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "userid",
                "usernamec",
                "usertel",
                "usertype",
                "useremail",
                "companyname",
                "password",
                "edibpmflag",
                "edibpmtime",
                "status",
                "attr1",
                "attr2",
                "attr3",
                "attr4",
                "attr5"
            })
            public static class MainUser {

                @XmlElement(name = "USERID", namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", required = false)
                protected String userid;
                @XmlElement(name = "USER_NAME_C", namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", required = false)
                protected String usernamec;
                @XmlElement(name = "USER_TEL", namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", required = false)
                protected String usertel;
                @XmlElement(name = "USER_TYPE", namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", required = false)
                protected String usertype;
                @XmlElement(name = "USER_EMAIL", namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", required = false)
                protected String useremail;
                @XmlElement(name = "COMPANY_NAME", namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", required = false)
                protected String companyname;
                @XmlElement(name = "PASSWORD", namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", required = false)
                protected String password;
                @XmlElement(name = "EDI_BPM_FLAG", namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", required = false)
                protected String edibpmflag;
                @XmlElement(name = "EDI_BPM_TIME", namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", required = false)
                protected String edibpmtime;
                @XmlElement(name = "STATUS", namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", required = false)
                protected String status;
                @XmlElement(name = "Attr1", namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", required = false)
                protected String attr1;
                @XmlElement(name = "Attr2", namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", required = false)
                protected String attr2;
                @XmlElement(name = "Attr3", namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", required = false)
                protected String attr3;
                @XmlElement(name = "Attr4", namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", required = false)
                protected String attr4;
                @XmlElement(name = "Attr5", namespace = "http://www.longi.com/IDMSB/Idm/Esb/mainUsers/Schemas/mainusers/v1.0", required = false)
                protected String attr5;

                /**
                 * 获取userid属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public String getUSERID() {
                    return userid;
                }

                /**
                 * 设置userid属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public void setUSERID(String value) {
                    this.userid = value;
                }

                /**
                 * 获取usernamec属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public String getUSERNAMEC() {
                    return usernamec;
                }

                /**
                 * 设置usernamec属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public void setUSERNAMEC(String value) {
                    this.usernamec = value;
                }

                /**
                 * 获取usertel属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public String getUSERTEL() {
                    return usertel;
                }

                /**
                 * 设置usertel属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public void setUSERTEL(String value) {
                    this.usertel = value;
                }

                /**
                 * 获取usertype属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public String getUSERTYPE() {
                    return usertype;
                }

                /**
                 * 设置usertype属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public void setUSERTYPE(String value) {
                    this.usertype = value;
                }

                /**
                 * 获取useremail属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public String getUSEREMAIL() {
                    return useremail;
                }

                /**
                 * 设置useremail属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public void setUSEREMAIL(String value) {
                    this.useremail = value;
                }

                /**
                 * 获取companyname属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public String getCOMPANYNAME() {
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
                public void setCOMPANYNAME(String value) {
                    this.companyname = value;
                }

                /**
                 * 获取password属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
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
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public void setPASSWORD(String value) {
                    this.password = value;
                }

                /**
                 * 获取edibpmflag属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public String getEDIBPMFLAG() {
                    return edibpmflag;
                }

                /**
                 * 设置edibpmflag属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public void setEDIBPMFLAG(String value) {
                    this.edibpmflag = value;
                }

                /**
                 * 获取edibpmtime属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public String getEDIBPMTIME() {
                    return edibpmtime;
                }

                /**
                 * 设置edibpmtime属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public void setEDIBPMTIME(String value) {
                    this.edibpmtime = value;
                }

                /**
                 * 获取status属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public String getSTATUS() {
                    return status;
                }

                /**
                 * 设置status属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public void setSTATUS(String value) {
                    this.status = value;
                }

                /**
                 * 获取attr1属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
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
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
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
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
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
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
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
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
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
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
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
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
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
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
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
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
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
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public void setAttr5(String value) {
                    this.attr5 = value;
                }

            }

        }

    }

}
