package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendor;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pre>
 *  供应商地点 VendorSite
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/09/27 15:27
 *  修改内容:
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "vendorSiteId","addressName","operateUnit","operateUnitName","shipToLocation","billToLocation",
        "shipMethod","paymentMethod","invoiceMatchOption","paymentCurrency","liabilityAccounts","advanceAccount",
        "billAccount","disableDate","detailAddress","purchaseFlag","paymentFlag","rfqOnlyFlag","attr1","attr2","attr3"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class VendorSiteEntity {

    /**
     * 供应商地点Id
     */
    @XmlElement(name = "VENDOR_SITE_ID", required = false)
    private String vendorSiteId;

    /**
     * 地点名称
     */
    @XmlElement(name = "ADDRESS_NAME", required = false)
    private String addressName;

    /**
     * 所属公司编码
     */
    @XmlElement(name = "OPERATE_UNIT", required = false)
    private String operateUnit;

    /**
     * 所属公司名称
     */
    @XmlElement(name = "OPERATE_UNIT_NAME", required = false)
    private String operateUnitName;

    /**
     * 收货地点
     */
    @XmlElement(name = "SHIP_TO_LOCATION", required = false)
    private String shipToLocation;

    /**
     * 收单地点
     */
    @XmlElement(name = "BILL_TO_LOCATION", required = false)
    private String billToLocation;

    /**
     * 发运方式
     */
    @XmlElement(name = "SHIP_METHOD", required = false)
    private String shipMethod;

    /**
     * 付款方式
     */
    @XmlElement(name = "PAYMENT_METHOD", required = false)
    private String paymentMethod;

    /**
     * 发票匹配选项
     */
    @XmlElement(name = "INVOICE_MATCH_OPTION", required = false)
    private String invoiceMatchOption;

    /**
     * 付款币种
     */
    @XmlElement(name = "PAYMENT_CURRENCY", required = false)
    private String paymentCurrency;

    /**
     * 负债账户
     */
    @XmlElement(name = "LIABILITY_ACCOUNTS", required = false)
    private String liabilityAccounts;

    /**
     * 预付款账户
     */
    @XmlElement(name = "ADVANCE_ACCOUNT", required = false)
    private String advanceAccount;

    /**
     * 票据账户
     */
    @XmlElement(name = "BILL_ACCOUNT", required = false)
    private String billAccount;

    /**
     * 失效日期
     */
    @XmlElement(name = "DISABLE_DATE", required = false)
    private String disableDate;

    /**
     * 详细地址
     */
    @XmlElement(name = "DETAIL_ADDRESS", required = false)
    private String detailAddress;

    /**
     * 采购
     */
    @XmlElement(name = "PURCHASE_FLAG", required = false)
    private String purchaseFlag;

    /**
     * 付款
     */
    @XmlElement(name = "PAYMENT_FLAG", required = false)
    private String paymentFlag;

    /**
     * 仅限询价
     */
    @XmlElement(name = "RFQ_ONLY_FLAG", required = false)
    private String rfqOnlyFlag;

    /**
     * 备用字段1
     */
    @XmlElement(name = "ATTR1", required = false)
    private String attr1;

    /**
     * 备用字段2
     */
    @XmlElement(name = "ATTR2", required = false)
    private String attr2;

    /**
     * 备用字段3
     */
    @XmlElement(name = "ATTR3", required = false)
    private String attr3;

}