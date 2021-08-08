
package com.midea.cloud.srm.model.cm.accept.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="RESPONSE_BODY" type="{http://soap.f10.vispractice.com}responseBody" minOccurs="0"/>
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
    "responsebody"
})
public class BusinessFormResponse {

    @XmlElement(name = "RESPONSE_BODY")
    protected ResponseBody responsebody;

    /**
     * 获取responsebody属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ResponseBody }
     *     
     */
    public ResponseBody getRESPONSEBODY() {
        return responsebody;
    }

    /**
     * 设置responsebody属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseBody }
     *     
     */
    public void setRESPONSEBODY(ResponseBody value) {
        this.responsebody = value;
    }

}
