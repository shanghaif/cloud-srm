
package com.midea.cloud.srm.model.suppliercooperate.deliverynote.soap.wms.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>esbInfo complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="esbInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attr1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attr3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="instId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="requestTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esbInfo", propOrder = {
    "attr1",
    "attr2",
    "attr3",
    "instId",
    "requestTime"
})
@Accessors(chain = true)
@Data
public class EsbInfo {
    @XmlElement(name = "ATTR1")
    protected String attr1;
    @XmlElement(name = "ATTR2")
    protected String attr2;
    @XmlElement(name = "ATTR3")
    protected String attr3;
    @XmlElement(name = "INSTID")
    protected String instId;
    @XmlElement(name = "REQUESTTIME")
    protected String requestTime;
}
