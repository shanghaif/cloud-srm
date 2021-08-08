package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendor;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pre>
 *  供应商地址 VendorAddress
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/09/27 15:31
 *  修改内容:
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "partySiteId","addressName","country","province","city","county",
        "detailAddress","purchaseFlag","paymentFlag","rfqOnlyFlag","enableFlag","attr1","attr2","attr3"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class VendorAddressEntity {

    /**
     * 地址Id
     */
    @XmlElement(name = "PARTY_SITE_ID", required = false)
    private String partySiteId;

    /**
     * 地址名称
     */
    @XmlElement(name = "ADDRESS_NAME", required = false)
    private String addressName;

    /**
     * 国家
     */
    @XmlElement(name = "COUNTRY", required = false)
    private String country;

    /**
     * 省
     */
    @XmlElement(name = "PROVINCE", required = false)
    private String province;

    /**
     * 市
     */
    @XmlElement(name = "CITY", required = false)
    private String city;

    /**
     * 县
     */
    @XmlElement(name = "COUNTY", required = false)
    private String county;

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
     * 启用状态
     */
    @XmlElement(name = "ENABLE_FLAG", required = false)
    private String enableFlag;

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