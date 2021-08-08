
package com.midea.cloud.srm.model.base.soap.erp.erpacceptpurchaseordersoapbiz;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>APPS.CUX_PO_ORDERS_GEX1157981X1X7 complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="APPS.CUX_PO_ORDERS_GEX1157981X1X7">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="P_PO_INFO_TBL_ITEM" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/ErpAcceptPurchaseOrderSoapBiz}APPS.CUX_PO_ORDERS_GEX1157981X1X8" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "APPS.CUX_PO_ORDERS_GEX1157981X1X7", propOrder = {
    "ppoinfotblitem"
})
public class APPSCUXPOORDERSGEX1157981X1X7 {

    @XmlElement(name = "P_PO_INFO_TBL_ITEM", nillable = true)
    protected List<APPSCUXPOORDERSGEX1157981X1X8> ppoinfotblitem;

}
