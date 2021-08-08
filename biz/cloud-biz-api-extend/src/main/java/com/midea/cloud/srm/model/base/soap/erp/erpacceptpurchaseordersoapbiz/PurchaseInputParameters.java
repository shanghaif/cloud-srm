
package com.midea.cloud.srm.model.base.soap.erp.erpacceptpurchaseordersoapbiz;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


/**
 * <p>anonymous complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="P_ESB_INFO_REC" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}APPS.CUX_PO_ORDERS_GEX1157981X1X1" minOccurs="0"/>
 *         &lt;element name="P_PO_INFO_TBL" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}APPS.CUX_PO_ORDERS_GEX1157981X1X7" minOccurs="0"/>
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
@XmlType(name = "", propOrder = {
    "pesbinforec",
    "ppoinfotbl"
})
@XmlRootElement(name = "InputParameters")
public class PurchaseInputParameters {

    @XmlElement(name = "P_ESB_INFO_REC", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected APPSCUXPOORDERSGEX1157981X1X1 pesbinforec;
    @XmlElement(name = "P_PO_INFO_TBL", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz", required = false)
    protected APPSCUXPOORDERSGEX1157981X1X7 ppoinfotbl;

}
