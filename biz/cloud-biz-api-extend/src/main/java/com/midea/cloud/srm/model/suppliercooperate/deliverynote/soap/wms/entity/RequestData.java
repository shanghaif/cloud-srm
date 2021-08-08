
package com.midea.cloud.srm.model.suppliercooperate.deliverynote.soap.wms.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;requestData complex type�� Java �ࡣ
 * 
 * &lt;p&gt;����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="requestData"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CUSTOMERID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="WAREHOUSEID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ASNNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ASNTYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ASNSTATUS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ASNCREATIONTIME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EXPECTEDARRIVETIME1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SUPPLIERID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SUPPLIERNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ASNREFERENCE1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ASNREFERENCE2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ASNREFERENCE3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ASNREFERENCE4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ASNREFERENCE5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="NOTETEXT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="HEDI01" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="HEDI02" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="HEDI03" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="HEDI04" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="HEDI05" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="HEDI06" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="HEDI07" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="HEDI08" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ITEMS" type="{http://data.ws.datahub/}ItemData" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "requestData", propOrder = {
    "customerid",
    "warehouseid",
    "asnno",
    "asntype",
    "asnstatus",
    "asncreationtime",
    "expectedarrivetime1",
    "supplierid",
    "suppliername",
    "asnreference1",
    "asnreference2",
    "asnreference3",
    "asnreference4",
    "asnreference5",
    "notetext",
    "hedi01",
    "hedi02",
    "hedi03",
    "hedi04",
    "hedi05",
    "hedi06",
    "hedi07",
    "hedi08",
    "items"
})
@Accessors(chain = true)
@Data
public class RequestData {

    @XmlElement(name = "CUSTOMERID")
    protected String customerid;
    @XmlElement(name = "WAREHOUSEID")
    protected String warehouseid;
    @XmlElement(name = "ASNNO")
    protected String asnno;
    @XmlElement(name = "ASNTYPE")
    protected String asntype;
    @XmlElement(name = "ASNSTATUS")
    protected String asnstatus;
    @XmlElement(name = "ASNCREATIONTIME")
    protected String asncreationtime;
    @XmlElement(name = "EXPECTEDARRIVETIME1")
    protected String expectedarrivetime1;
    @XmlElement(name = "SUPPLIERID")
    protected String supplierid;
    @XmlElement(name = "SUPPLIERNAME")
    protected String suppliername;
    @XmlElement(name = "ASNREFERENCE1")
    protected String asnreference1;
    @XmlElement(name = "ASNREFERENCE2")
    protected String asnreference2;
    @XmlElement(name = "ASNREFERENCE3")
    protected String asnreference3;
    @XmlElement(name = "ASNREFERENCE4")
    protected String asnreference4;
    @XmlElement(name = "ASNREFERENCE5")
    protected String asnreference5;
    @XmlElement(name = "NOTETEXT")
    protected String notetext;
    @XmlElement(name = "HEDI01")
    protected String hedi01;
    @XmlElement(name = "HEDI02")
    protected String hedi02;
    @XmlElement(name = "HEDI03")
    protected String hedi03;
    @XmlElement(name = "HEDI04")
    protected String hedi04;
    @XmlElement(name = "HEDI05")
    protected String hedi05;
    @XmlElement(name = "HEDI06")
    protected String hedi06;
    @XmlElement(name = "HEDI07")
    protected String hedi07;
    @XmlElement(name = "HEDI08")
    protected String hedi08;
    @XmlElement(name = "ITEMS")
    protected ArrayList<ItemData> items;
}
