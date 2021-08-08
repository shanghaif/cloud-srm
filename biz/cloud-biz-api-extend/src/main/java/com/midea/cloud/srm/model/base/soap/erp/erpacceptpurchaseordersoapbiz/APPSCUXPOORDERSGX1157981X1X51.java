
package com.midea.cloud.srm.model.base.soap.erp.erpacceptpurchaseordersoapbiz;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>APPS.CUX_PO_ORDERS_GX1157981X1X51 complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_ORDERS_GX1157981X1X51">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LINENUMBER" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="LINETYPE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="ITEMNUMBER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string40" minOccurs="0"/>
 *         &lt;element name="ITEMDESCR" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="UNITOFMEASURE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="QUANTITY" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="PRICE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="TAXPRICE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="TAXRATECODE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="REQUIREDATE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="PROMISEDATE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="NOTAXFLAG" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string10" minOccurs="0"/>
 *         &lt;element name="CUSTREQUIRED" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="CONTRACTNUMBER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="LINEDESCR" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="REQUISITIONNUMBER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string40" minOccurs="0"/>
 *         &lt;element name="REQUISITIONLINENUM" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="SOURCESYSCODE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="SOURCEREFENCE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string80" minOccurs="0"/>
 *         &lt;element name="SOURCELINEID" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string80" minOccurs="0"/>
 *         &lt;element name="ORGANIZATIONID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="PROJECTID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="TASKID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="PROJECTNUMBER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="TASKNUMBER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR1" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR2" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR3" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR4" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR5" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@Data
@Accessors(chain = true)
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_ORDERS_GX1157981X1X51", propOrder = {
    "linenumber",
    "linetype",
    "itemnumber",
    "itemdescr",
    "unitofmeasure",
    "quantity",
    "price",
    "taxprice",
    "taxratecode",
    "requiredate",
    "promisedate",
    "notaxflag",
    "custrequired",
    "contractnumber",
    "linedescr",
    "requisitionnumber",
    "requisitionlinenum",
    "sourcesyscode",
    "sourcerefence",
    "sourcelineid",
    "organizationid",
    "projectid",
    "taskid",
    "projectnumber",
    "tasknumber",
    "attr1",
    "attr2",
    "attr3",
    "attr4",
    "attr5"
})
public class APPSCUXPOORDERSGX1157981X1X51 {

    @XmlElement(name = "LINENUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected BigDecimal linenumber;
    @XmlElement(name = "LINETYPE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String linetype;
    @XmlElement(name = "ITEMNUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String itemnumber;
    @XmlElement(name = "ITEMDESCR", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String itemdescr;
    @XmlElement(name = "UNITOFMEASURE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String unitofmeasure;
    @XmlElement(name = "QUANTITY", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected BigDecimal quantity;
    @XmlElement(name = "PRICE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected BigDecimal price;
    @XmlElement(name = "TAXPRICE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected BigDecimal taxprice;
    @XmlElement(name = "TAXRATECODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String taxratecode;
    @XmlElement(name = "REQUIREDATE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String requiredate;
    @XmlElement(name = "PROMISEDATE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String promisedate;
    @XmlElement(name = "NOTAXFLAG", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String notaxflag;
    @XmlElement(name = "CUSTREQUIRED", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String custrequired;
    @XmlElement(name = "CONTRACTNUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String contractnumber;
    @XmlElement(name = "LINEDESCR", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String linedescr;
    @XmlElement(name = "REQUISITIONNUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String requisitionnumber;
    @XmlElement(name = "REQUISITIONLINENUM", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected BigDecimal requisitionlinenum;
    @XmlElement(name = "SOURCESYSCODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String sourcesyscode;
    @XmlElement(name = "SOURCEREFENCE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String sourcerefence;
    @XmlElement(name = "SOURCELINEID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String sourcelineid;
    @XmlElement(name = "ORGANIZATIONID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected BigDecimal organizationid;
    @XmlElement(name = "PROJECTID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected BigDecimal projectid;
    @XmlElement(name = "TASKID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected BigDecimal taskid;
    @XmlElement(name = "PROJECTNUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String projectnumber;
    @XmlElement(name = "TASKNUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String tasknumber;
    @XmlElement(name = "ATTR1", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String attr1;
    @XmlElement(name = "ATTR2", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String attr2;
    @XmlElement(name = "ATTR3", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String attr3;
    @XmlElement(name = "ATTR4", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String attr4;
    @XmlElement(name = "ATTR5", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String attr5;

}
