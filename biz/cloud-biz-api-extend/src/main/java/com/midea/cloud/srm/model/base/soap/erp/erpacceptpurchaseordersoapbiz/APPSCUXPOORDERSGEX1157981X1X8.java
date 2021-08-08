
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
 * <p>APPS.CUX_PO_ORDERS_GEX1157981X1X8 complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_ORDERS_GEX1157981X1X8">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OPERATIONUNITID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="OPERATIONNAME" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="DOCUMENTTYPE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="PONUMBER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string40" minOccurs="0"/>
 *         &lt;element name="CURRENCY" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string10" minOccurs="0"/>
 *         &lt;element name="EXCHANGETYPE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="EXCHANGEDATE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="EXCHANGERATE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="AGENTID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="AGENTNAME" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string100" minOccurs="0"/>
 *         &lt;element name="VENDORID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="VENDORNUMBER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string60" minOccurs="0"/>
 *         &lt;element name="VENDORSITEID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="VENDORSITECODE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="CONTRACTORID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="CONTRACTORNAME" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string100" minOccurs="0"/>
 *         &lt;element name="BILLTOLOCATION" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="SHIPTOLOCATION" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="POORDERTYPE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="POCONTRACTNUMBER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="CONTRACTOR" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="CONTRACTORPHONE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="CONTRACTORFAX" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="QACAGRNUMBER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="TECAGRNUMBER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="NEGNUMBER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="DESCROTHER" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ABSTRACT" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="TAXRATE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="PAYMENTMETHOD" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="PAYMENTTERM" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string80" minOccurs="0"/>
 *         &lt;element name="SOURCESYSCODE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="SOURCEREFENCE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string80" minOccurs="0"/>
 *         &lt;element name="SOURCELINEID" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string80" minOccurs="0"/>
 *         &lt;element name="IFACECODE" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string120" minOccurs="0"/>
 *         &lt;element name="IFACEMEAN" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR1" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR2" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR3" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR4" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR5" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="LINEDETAILS" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}APPS.CUX_PO_ORDERS_GX1157981X1X50" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */

/**
 * 入参头表
 */

@Data
@Accessors(chain = true)
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_PO_ORDERS_GEX1157981X1X8", propOrder = {
    "operationunitid",
    "operationname",
    "documenttype",
    "ponumber",
    "currency",
    "exchangetype",
    "exchangedate",
    "exchangerate",
    "agentid",
    "agentname",
    "vendorid",
    "vendornumber",
    "vendorsiteid",
    "vendorsitecode",
    "contractorid",
    "contractorname",
    "billtolocation",
    "shiptolocation",
    "poordertype",
    "pocontractnumber",
    "contractor",
    "contractorphone",
    "contractorfax",
    "qacagrnumber",
    "tecagrnumber",
    "negnumber",
    "descrother",
    "_abstract",
    "taxrate",
    "paymentmethod",
    "paymentterm",
    "sourcesyscode",
    "sourcerefence",
    "sourcelineid",
    "ifacecode",
    "ifacemean",
    "attr1",
    "attr2",
    "attr3",
    "attr4",
    "attr5",
    "linedetails"
})
public class APPSCUXPOORDERSGEX1157981X1X8 {

    @XmlElement(name = "OPERATIONUNITID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected BigDecimal operationunitid;
    @XmlElement(name = "OPERATIONNAME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String operationname;
    @XmlElement(name = "DOCUMENTTYPE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String documenttype;
    @XmlElement(name = "PONUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String ponumber;
    @XmlElement(name = "CURRENCY", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String currency;
    @XmlElement(name = "EXCHANGETYPE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String exchangetype;
    @XmlElement(name = "EXCHANGEDATE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String exchangedate;
    @XmlElement(name = "EXCHANGERATE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected BigDecimal exchangerate;
    @XmlElement(name = "AGENTID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected BigDecimal agentid;
    @XmlElement(name = "AGENTNAME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String agentname;
    @XmlElement(name = "VENDORID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected BigDecimal vendorid;
    @XmlElement(name = "VENDORNUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String vendornumber;
    @XmlElement(name = "VENDORSITEID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected BigDecimal vendorsiteid;
    @XmlElement(name = "VENDORSITECODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String vendorsitecode;
    @XmlElement(name = "CONTRACTORID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected BigDecimal contractorid;
    @XmlElement(name = "CONTRACTORNAME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String contractorname;
    @XmlElement(name = "BILLTOLOCATION", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String billtolocation;
    @XmlElement(name = "SHIPTOLOCATION", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String shiptolocation;
    @XmlElement(name = "POORDERTYPE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String poordertype;
    @XmlElement(name = "POCONTRACTNUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String pocontractnumber;
    @XmlElement(name = "CONTRACTOR", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String contractor;
    @XmlElement(name = "CONTRACTORPHONE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String contractorphone;
    @XmlElement(name = "CONTRACTORFAX", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String contractorfax;
    @XmlElement(name = "QACAGRNUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String qacagrnumber;
    @XmlElement(name = "TECAGRNUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String tecagrnumber;
    @XmlElement(name = "NEGNUMBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String negnumber;
    @XmlElement(name = "DESCROTHER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String descrother;
    @XmlElement(name = "ABSTRACT", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String _abstract;
    @XmlElement(name = "TAXRATE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected BigDecimal taxrate;
    @XmlElement(name = "PAYMENTMETHOD", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String paymentmethod;
    @XmlElement(name = "PAYMENTTERM", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String paymentterm;
    @XmlElement(name = "SOURCESYSCODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String sourcesyscode;
    @XmlElement(name = "SOURCEREFENCE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String sourcerefence;
    @XmlElement(name = "SOURCELINEID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String sourcelineid;
    @XmlElement(name = "IFACECODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String ifacecode;
    @XmlElement(name = "IFACEMEAN", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String ifacemean;
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
    @XmlElement(name = "LINEDETAILS", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected APPSCUXPOORDERSGX1157981X1X50 linedetails;


}
