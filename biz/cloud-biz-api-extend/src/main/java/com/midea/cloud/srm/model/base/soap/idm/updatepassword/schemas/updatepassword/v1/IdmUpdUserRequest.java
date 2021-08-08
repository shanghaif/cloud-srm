
package com.midea.cloud.srm.model.base.soap.idm.updatepassword.schemas.updatepassword.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "esbInfo",
    "requestInfo"
})
@XmlRootElement(name = "request", namespace = "http://www.longi.com/IDMSB/Idm/UpdatePassWord/Schemas/updatePassWord/v1.0")
/**
 * Description 修改密码同步到IDM总线Request实体类
 * @Author wuwl18@meicloud.com
 * @Date 2020.08.29
 **/
public class IdmUpdUserRequest {

    @XmlElement(required = true, namespace = "http://www.longi.com/IDMSB/Idm/UpdatePassWord/Schemas/updatePassWord/v1.0")
    protected EsbInfo esbInfo;
    @XmlElement(required = true, namespace = "http://www.longi.com/IDMSB/Idm/UpdatePassWord/Schemas/updatePassWord/v1.0")
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

        @XmlElement(required = true, namespace = "http://www.longi.com/IDMSB/Idm/UpdatePassWord/Schemas/updatePassWord/v1.0")
        protected String instId;
        @XmlElement(required = true, namespace = "http://www.longi.com/IDMSB/Idm/UpdatePassWord/Schemas/updatePassWord/v1.0")
        protected String requestTime;
        @XmlElement(required = true, namespace = "http://www.longi.com/IDMSB/Idm/UpdatePassWord/Schemas/updatePassWord/v1.0")
        protected String attr1;
        @XmlElement(required = true, namespace = "http://www.longi.com/IDMSB/Idm/UpdatePassWord/Schemas/updatePassWord/v1.0")
        protected String attr2;
        @XmlElement(required = true, namespace = "http://www.longi.com/IDMSB/Idm/UpdatePassWord/Schemas/updatePassWord/v1.0")
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
        "employees"
    })
    public static class RequestInfo {

        @XmlElement(name = "Employees", required = true)
        protected Employees employees;

        /**
         * 获取employees属性的值。
         * 
         * @return
         *     possible object is
         *     {@link Employees }
         *     
         */
        public Employees getEmployees() {
            return employees;
        }

        /**
         * 设置employees属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link Employees }
         *     
         */
        public void setEmployees(Employees value) {
            this.employees = value;
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
         *         &lt;element name="Employee" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="EMPLID" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="NewPassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="OldPassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="Attr1" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="Attr2" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="Attr3" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="Attr4" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="Attr5" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
            "employee"
        })
        public static class Employees {

            @XmlElement(name = "Employee", required = true)
            protected List<Employee> employee;

            /**
             * Gets the value of the employee property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the employee property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getEmployee().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Employee }
             * 
             * 
             */
            public List<Employee> getEmployee() {
                if (employee == null) {
                    employee = new ArrayList<Employee>();
                }
                return this.employee;
            }

            public void getEmployee(List<Employee> employee) {
                this.employee = employee;
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
             *         &lt;element name="EMPLID" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="NewPassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="OldPassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="Attr1" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="Attr2" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="Attr3" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="Attr4" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="Attr5" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
                "emplid",
                "newPassword",
                "oldPassword",
                "attr1",
                "attr2",
                "attr3",
                "attr4",
                "attr5"
            })
            public static class Employee {

                @XmlElement(name = "EMPLID", required = true)
                protected String emplid;
                @XmlElement(name = "NewPassword", required = true)
                protected String newPassword;
                @XmlElement(name = "OldPassword", required = true)
                protected String oldPassword;
                @XmlElement(name = "Attr1", required = true)
                protected String attr1;
                @XmlElement(name = "Attr2", required = true)
                protected String attr2;
                @XmlElement(name = "Attr3", required = true)
                protected String attr3;
                @XmlElement(name = "Attr4", required = true)
                protected String attr4;
                @XmlElement(name = "Attr5", required = true)
                protected String attr5;

                /**
                 * 获取emplid属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getEmplid() {
                    return emplid;
                }

                /**
                 * 设置emplid属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setEmplid(String value) {
                    this.emplid = value;
                }

                /**
                 * 获取newPassword属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getNewPassword() {
                    return newPassword;
                }

                /**
                 * 设置newPassword属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setNewPassword(String value) {
                    this.newPassword = value;
                }

                /**
                 * 获取oldPassword属性的值。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOldPassword() {
                    return oldPassword;
                }

                /**
                 * 设置oldPassword属性的值。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOldPassword(String value) {
                    this.oldPassword = value;
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

            }

        }

    }

}
