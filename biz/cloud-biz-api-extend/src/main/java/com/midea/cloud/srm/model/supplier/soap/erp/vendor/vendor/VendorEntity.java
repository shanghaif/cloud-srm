package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendor;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <pre>
 *  供应商
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/09/27 15:29
 *  修改内容:
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "vendorId","vendorClass","vendorCode","vendorName","vendorAltName","vendorAddress","lawPerson","payTaxNum","paymentTerm",
        "paymentMethod","enableFlag","internalCommComCode","internalCommCompany","originVendorCode","originVendorName","attr1", "attr2","attr3","attr4","attr5",
        "vendorBankAccounts","vendorAddresses","vendorSites","vendorContactors"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class VendorEntity {

    /**
     * 供应商Id
     */
    @XmlElement(name = "VENDOR_ID", required = false)
    private String vendorId;

    /**
     * 供应商分类
     */
    @XmlElement(name = "VENDOR_CLASS", required = false)
    private String vendorClass;

    /**
     * 供应商编码
     */
    @XmlElement(name = "VENDOR_CODE", required = false)
    private String vendorCode;

    /**
     * 供应商名称
     */
    @XmlElement(name = "VENDOR_NAME", required = false)
    private String vendorName;

    /**
     * 供应商别名
     */
    @XmlElement(name = "VENDOR_ALT_NAME", required = false)
    private String vendorAltName;

    /**
     * 地址
     */
    @XmlElement(name = "VENDOR_ADDRESS", required = false)
    private String vendorAddress;

    /**
     * 法人代表
     */
    @XmlElement(name = "LAW_PERSON", required = false)
    private String lawPerson;

    /**
     * 纳税登记号
     */
    @XmlElement(name = "PAY_TAX_NUM", required = false)
    private String payTaxNum;

    /**
     * 付款条件
     */
    @XmlElement(name = "PAYMENT_TREM", required = false)
    private String paymentTerm;

    /**
     * 付款方式
     */
    @XmlElement(name = "PAYMENT_METHOD", required = false)
    private String paymentMethod;

    /**
     * 启用状态
     */
    @XmlElement(name = "ENABLE_FLAG", required = false)
    private String enableFlag;

    /**
     * 内部往来公司编码
     */
    @XmlElement(name = "INTERNAL_COMM_COM_CODE", required = false)
    private String internalCommComCode;

    /**
     * 内部往来公司名称
     */
    @XmlElement(name = "INTERNAL_COMM_COMPANY", required = false)
    private String internalCommCompany;

    /**
     * 原厂商编码
     */
    @XmlElement(name = "ORIGIN_VENDOR_CODE", required = false)
    private String originVendorCode;

    /**
     * 原厂商名称
     */
    @XmlElement(name = "ORIGIN_VENDOR_NAME", required = false)
    private String originVendorName;

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

    /**
     * 备用字段4
     */
    @XmlElement(name = "ATTR4", required = false)
    private String attr4;

    /**
     * 备用字段5
     */
    @XmlElement(name = "ATTR5", required = false)
    private String attr5;

    /**
     * 供应商银行账户
     */
    @XmlElement(name = "VENDOR_BANK_ACCOUNTS", required = false)
    private VendorBankAccounts vendorBankAccounts;

    /**
     * 供应商地址
     */
    @XmlElement(name = "VENDOR_ADDRESSES", required = false)
    private VendorAddresses vendorAddresses;

    /**
     * 供应商地点
     */
    @XmlElement(name = "VENDOR_SITES", required = false)
    private VendorSites vendorSites;

    /**
     * 联系人
     */
    @XmlElement(name = "VENDOR_CONTACTORS", required = false)
    private VendorContactors vendorContactors;
}