
package com.midea.cloud.srm.model.suppliercooperate.deliverynote.soap.wms.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;ItemData complex type�� Java �ࡣ
 * 
 * &lt;p&gt;����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ItemData"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="WAREHOUSEID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ASNNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ASNLINENO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CUSTOMERID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SKU" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SKUDESCR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PONO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="POLINENO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LINESTATUS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EXPECTEDQTY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="NOTETEXT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT01" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT02" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT03" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT04" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT05" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT06" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT07" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT08" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT09" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT11" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT12" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT14" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT15" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT16" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT17" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT18" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT19" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT20" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT21" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT22" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT23" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOTATT24" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TOTALCUBIC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TOTALGROSSWEIGHT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TOTALNETWEIGHT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEDI01" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEDI02" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEDI03" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEDI04" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEDI05" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEDI11" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEDI12" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEDI13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEDI14" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEDI15" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEDI16" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEDI17" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEDI18" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemData", propOrder = {
    "warehouseid",
    "asnno",
    "asnlineno",
    "customerid",
    "sku",
    "skudescr",
    "pono",
    "polineno",
    "linestatus",
    "expectedqty",
    "notetext",
    "lotatt01",
    "lotatt02",
    "lotatt03",
    "lotatt04",
    "lotatt05",
    "lotatt06",
    "lotatt07",
    "lotatt08",
    "lotatt09",
    "lotatt10",
    "lotatt11",
    "lotatt12",
    "lotatt13",
    "lotatt14",
    "lotatt15",
    "lotatt16",
    "lotatt17",
    "lotatt18",
    "lotatt19",
    "lotatt20",
    "lotatt21",
    "lotatt22",
    "lotatt23",
    "lotatt24",
    "totalcubic",
    "totalgrossweight",
    "totalnetweight",
    "dedi01",
    "dedi02",
    "dedi03",
    "dedi04",
    "dedi05",
    "dedi11",
    "dedi12",
    "dedi13",
    "dedi14",
    "dedi15",
    "dedi16",
    "dedi17",
    "dedi18"
})
@Accessors(chain = true)
@Data
public class ItemData {

    @XmlElement(name = "WAREHOUSEID")
    protected String warehouseid;
    @XmlElement(name = "ASNNO")
    protected String asnno;
    @XmlElement(name = "ASNLINENO")
    protected String asnlineno;
    @XmlElement(name = "CUSTOMERID")
    protected String customerid;
    @XmlElement(name = "SKU")
    protected String sku;
    @XmlElement(name = "SKUDESCR")
    protected String skudescr;
    @XmlElement(name = "PONO")
    protected String pono;
    @XmlElement(name = "POLINENO")
    protected String polineno;
    @XmlElement(name = "LINESTATUS")
    protected String linestatus;
    @XmlElement(name = "EXPECTEDQTY")
    protected String expectedqty;
    @XmlElement(name = "NOTETEXT")
    protected String notetext;
    @XmlElement(name = "LOTATT01")
    protected String lotatt01;
    @XmlElement(name = "LOTATT02")
    protected String lotatt02;
    @XmlElement(name = "LOTATT03")
    protected String lotatt03;
    @XmlElement(name = "LOTATT04")
    protected String lotatt04;
    @XmlElement(name = "LOTATT05")
    protected String lotatt05;
    @XmlElement(name = "LOTATT06")
    protected String lotatt06;
    @XmlElement(name = "LOTATT07")
    protected String lotatt07;
    @XmlElement(name = "LOTATT08")
    protected String lotatt08;
    @XmlElement(name = "LOTATT09")
    protected String lotatt09;
    @XmlElement(name = "LOTATT10")
    protected String lotatt10;
    @XmlElement(name = "LOTATT11")
    protected String lotatt11;
    @XmlElement(name = "LOTATT12")
    protected String lotatt12;
    @XmlElement(name = "LOTATT13")
    protected String lotatt13;
    @XmlElement(name = "LOTATT14")
    protected String lotatt14;
    @XmlElement(name = "LOTATT15")
    protected String lotatt15;
    @XmlElement(name = "LOTATT16")
    protected String lotatt16;
    @XmlElement(name = "LOTATT17")
    protected String lotatt17;
    @XmlElement(name = "LOTATT18")
    protected String lotatt18;
    @XmlElement(name = "LOTATT19")
    protected String lotatt19;
    @XmlElement(name = "LOTATT20")
    protected String lotatt20;
    @XmlElement(name = "LOTATT21")
    protected String lotatt21;
    @XmlElement(name = "LOTATT22")
    protected String lotatt22;
    @XmlElement(name = "LOTATT23")
    protected String lotatt23;
    @XmlElement(name = "LOTATT24")
    protected String lotatt24;
    @XmlElement(name = "TOTALCUBIC")
    protected String totalcubic;
    @XmlElement(name = "TOTALGROSSWEIGHT")
    protected String totalgrossweight;
    @XmlElement(name = "TOTALNETWEIGHT")
    protected String totalnetweight;
    @XmlElement(name = "DEDI01")
    protected String dedi01;
    @XmlElement(name = "DEDI02")
    protected String dedi02;
    @XmlElement(name = "DEDI03")
    protected String dedi03;
    @XmlElement(name = "DEDI04")
    protected String dedi04;
    @XmlElement(name = "DEDI05")
    protected String dedi05;
    @XmlElement(name = "DEDI11")
    protected String dedi11;
    @XmlElement(name = "DEDI12")
    protected String dedi12;
    @XmlElement(name = "DEDI13")
    protected String dedi13;
    @XmlElement(name = "DEDI14")
    protected String dedi14;
    @XmlElement(name = "DEDI15")
    protected String dedi15;
    @XmlElement(name = "DEDI16")
    protected String dedi16;
    @XmlElement(name = "DEDI17")
    protected String dedi17;
    @XmlElement(name = "DEDI18")
    protected String dedi18;
}
