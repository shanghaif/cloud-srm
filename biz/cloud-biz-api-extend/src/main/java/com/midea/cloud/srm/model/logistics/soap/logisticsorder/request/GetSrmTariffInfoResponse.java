
package com.midea.cloud.srm.model.logistics.soap.logisticsorder.request;

import com.midea.cloud.srm.model.logistics.soap.logisticsorder.response.ResponseBody;

import javax.xml.bind.annotation.*;


/**
 * <p>getSrmTariffInfoResponse complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="getSrmTariffInfoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="getSrmTariffInfo" type="{http://www.longi.com/TMSSB/Srm/LogisticsContractRate/Schemas/v1.0}responseBody" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSrmTariffInfoResponse", propOrder = {
    "getSrmTariffInfo"
})
@XmlRootElement(name = "getSrmTariffInfo")
public class GetSrmTariffInfoResponse {

    @XmlElement(namespace = "http://www.longi.com/TMSSB/Srm/LogisticsContractRate/WSDLs/v1.0", required = false)
    protected ResponseBody getSrmTariffInfo;

    /**
     * 获取getSrmTariffInfo属性的值。
     *
     * @return
     *     possible object is
     *     {@link ResponseBody }
     *
     */
    public ResponseBody getGetSrmTariffInfo() {
        return getSrmTariffInfo;
    }

    /**
     * 设置getSrmTariffInfo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link ResponseBody }
     *
     */
    public void setGetSrmTariffInfo(ResponseBody value) {
        this.getSrmTariffInfo = value;
    }


}
