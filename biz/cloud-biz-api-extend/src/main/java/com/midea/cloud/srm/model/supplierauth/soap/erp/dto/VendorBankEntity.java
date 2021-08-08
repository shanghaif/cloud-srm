package com.midea.cloud.srm.model.supplierauth.soap.erp.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pre>
 *  供应商银行信息返回实体类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/2 21:10
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {
        "erpVendorId","vendorNumber","bankCode","bankName","branchBankCode","branchBankName","bankAccountNumber","bankAccountName",
        "currencyCode","primaryBankAccount","enabledFlag","comments","sourceSysCode","sourceLineId",
        "attr1","attr2","attr3","attr4","attr5"
})
public class VendorBankEntity {

    /**
     * ERP供应商Id
     */
    @XmlElement(name = "ERP_VENDOR_ID", required = false)
    private Long erpVendorId;

    /**
     * 供应商编码
     */
    @XmlElement(name = "VENDOR_NUMBER", required = false)
    private String vendorNumber;

    /**
     * 银行编码
     */
    @XmlElement(name = "BANK_CODE", required = false)
    private String bankCode;

    /**
     * 银行名称
     */
    @XmlElement(name = "BANK_NAME", required = false)
    private String bankName;

    /**
     * 分行编码
     */
    @XmlElement(name = "BRANCH_BANK_CODE", required = false)
    private String branchBankCode;

    /**
     * 分行名称
     */
    @XmlElement(name = "BRANCH_BANK_NAME", required = false)
    private String branchBankName;

    /**
     * 账户编码
     */
    @XmlElement(name = "BANK_ACCOUNT_NUMBER", required = false)
    private String bankAccountNumber;

    /**
     * 账户名称
     */
    @XmlElement(name = "BANK_ACCOUNT_NAME", required = false)
    private String bankAccountName;

    /**
     * 币种
     */
    @XmlElement(name = "CURRENCY_CODE", required = false)
    private String currencyCode;

    /**
     * 是否主账户
     */
    @XmlElement(name = "PRIMARY_BANK_ACCOUNT", required = false)
    private String primaryBankAccount;

    /**
     * 启用状态
     */
    @XmlElement(name = "ENABLED_FLAG", required = false)
    private String enabledFlag;

    /**
     * 备注
     */
    @XmlElement(name = "COMMENTS", required = false)
    private String comments;

    /**
     * 来源系统编码
     */
    @XmlElement(name = "SOURCE_SYS_CODE", required = false)
    private String sourceSysCode;

    /**
     * 来源系行Id
     */
    @XmlElement(name = "SOURCE_LINE_ID", required = false)
    private String sourceLineId;

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
}
