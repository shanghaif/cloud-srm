
package com.midea.cloud.srm.model.logistics.soap.logisticsorder.response;

import com.midea.cloud.srm.model.logistics.soap.logisticsorder.request.EsbInfoRes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>responseBody complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="responseBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esbInfo" type="{http://www.longi.com/TMSSB/Srm/LogisticsContractRate/Schemas/v1.0}esbInfoRes" minOccurs="0"/>
 *         &lt;element name="resultInfo" type="{http://www.longi.com/TMSSB/Srm/LogisticsContractRate/Schemas/v1.0}resultInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "responseBody", propOrder = {
    "esbInfo",
    "resultInfo"
})
public class ResponseBody {
    @XmlElement(namespace = "http://www.longi.com/TMSSB/Srm/LogisticsContractRate/WSDLs/v1.0", required = false)
    protected EsbInfoRes esbInfo;
    @XmlElement(namespace = "http://www.longi.com/TMSSB/Srm/LogisticsContractRate/WSDLs/v1.0", required = false)
    protected ResultInfo resultInfo;

    /**
     * 获取esbInfo属性的值。
     *
     * @return
     *     possible object is
     *     {@link EsbInfoRes }
     *
     */
    public EsbInfoRes getEsbInfo() {
        return esbInfo;
    }

    /**
     * 设置esbInfo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link EsbInfoRes }
     *
     */
    public void setEsbInfo(EsbInfoRes value) {
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

}
