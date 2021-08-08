
package com.midea.cloud.srm.model.logistics.soap.logisticsorder.request;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;


/**
 * <p>getSrmTariffInfo complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="getSrmTariffInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esbInfo" type="{http://www.longi.com/TMSSB/Srm/LogisticsContractRate/Schemas/v1.0}esbInfo" minOccurs="0"/>
 *         &lt;element name="head" type="{http://www.longi.com/TMSSB/Srm/LogisticsContractRate/Schemas/v1.0}head" minOccurs="0"/>
 *         &lt;element name="requestInfo" type="{http://www.longi.com/TMSSB/Srm/LogisticsContractRate/Schemas/v1.0}customerinfo" maxOccurs="unbounded" minOccurs="0"/>
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
    "head",
    "requestInfo"
})
@XmlRootElement(name = "getSrmTariffInfo")
public class GetSrmTariffInfo {
    @XmlElement(name = "esbInfo", namespace = "http://www.longi.com/TMSSB/Srm/LogisticsContractRate/WSDLs/v1.0", required = false)
    protected EsbInfo esbInfo;
    @XmlElement(name = "head", namespace = "http://www.longi.com/TMSSB/Srm/LogisticsContractRate/WSDLs/v1.0", required = false)
    protected Head head;
    @XmlElement(name = "requestInfo", namespace = "http://www.longi.com/TMSSB/Srm/LogisticsContractRate/WSDLs/v1.0", required = false)
    protected List<Customerinfo> requestInfo;

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
     * 获取head属性的值。
     *
     * @return
     *     possible object is
     *     {@link Head }
     *
     */
    public Head getHead() {
        return head;
    }

    /**
     * 设置head属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link Head }
     *
     */
    public void setHead(Head value) {
        this.head = value;
    }

    /**
     * Gets the value of the requestInfo property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requestInfo property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequestInfo().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Customerinfo }
     *
     *
     */
    public List<Customerinfo> getRequestInfo() {
        if (requestInfo == null) {
            requestInfo = new ArrayList<Customerinfo>();
        }
        return this.requestInfo;
    }

    public void setRequestInfo(List<Customerinfo> requestInfo) {
        this.requestInfo = requestInfo;
    }
}
