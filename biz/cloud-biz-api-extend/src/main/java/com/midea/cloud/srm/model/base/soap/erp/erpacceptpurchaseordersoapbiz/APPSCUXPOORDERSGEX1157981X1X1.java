
package com.midea.cloud.srm.model.base.soap.erp.erpacceptpurchaseordersoapbiz;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


/**
 * <p>APPS.CUX_PO_ORDERS_GEX1157981X1X1 complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_ORDERS_GEX1157981X1X1">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="INSTID" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string100" minOccurs="0"/>
 *         &lt;element name="REQUESTTIME" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string30" minOccurs="0"/>
 *         &lt;element name="ATTR1" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR2" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
 *         &lt;element name="ATTR3" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}string240" minOccurs="0"/>
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
@XmlType(name = "APPS.CUX_PO_ORDERS_GEX1157981X1X1", propOrder = {
    "instid",
    "requesttime",
    "attr1",
    "attr2",
    "attr3"
})
public class APPSCUXPOORDERSGEX1157981X1X1 {

    @XmlElement(name = "INSTID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String instid;
    @XmlElement(name = "REQUESTTIME", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String requesttime;
    @XmlElement(name = "ATTR1", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String attr1;
    @XmlElement(name = "ATTR2", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String attr2;
    @XmlElement(name = "ATTR3", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected String attr3;

}
