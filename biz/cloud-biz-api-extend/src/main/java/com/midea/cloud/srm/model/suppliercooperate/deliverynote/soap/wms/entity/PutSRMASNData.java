
package com.midea.cloud.srm.model.suppliercooperate.deliverynote.soap.wms.entity;

import lombok.Data;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;putSRMASNData complex type�� Java �ࡣ
 * 
 * &lt;p&gt;����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="putSRMASNData"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="esbInfo" type="{http://data.ws.datahub/}esbInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="requestInfo" type="{http://data.ws.datahub/}requestData" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "putSRMASNData", propOrder = {
    "esbInfo",
    "requestInfo"
}, namespace = "http://data.ws.datahub1/")
@Data
public class PutSRMASNData {
    @XmlElement(name = "esbInfo")
    protected EsbInfo esbInfo;
    @XmlElement(name = "requestInfo")
    protected List<RequestData> requestInfo;

}
