
package com.midea.cloud.srm.model.suppliercooperate.deliverynote.soap.wms.entity;

import lombok.Data;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;esbInfoRes complex type�� Java �ࡣ
 * 
 * &lt;p&gt;����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="esbInfoRes"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="resultInfo" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="returnCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="returnDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="returnFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esbInfoRes", propOrder = {
    "resultInfo",
    "returnCode",
    "returnDesc",
    "returnFlag"
})
@Data
public class EsbInfoRes {
    @XmlElement(nillable = true)
    protected ArrayList<String> resultInfo;
    @XmlElement(name = "RETURMCODE",namespace = "http://www.w3.org/2001/XMLSchema")
    protected String returnCode;
    @XmlElement(name = "RETURNDESC",namespace = "http://www.w3.org/2001/XMLSchema")
    protected String returnDesc;
    @XmlElement(name = "RETURNFLAG",namespace = "http://www.w3.org/2001/XMLSchema")
    protected String returnFlag;

}
