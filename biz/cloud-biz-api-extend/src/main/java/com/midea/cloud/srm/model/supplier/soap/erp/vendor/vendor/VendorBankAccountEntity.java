package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendor;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pre>
 *  供应商银行账户 VendorBankAccounts
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
        "bankCode","bankName","branchBankCode","branchBankName","bankAccountName","bankAccountNumber",
        "currencyCode","primaryBankAccount","enabledFlag","comments","attr1","attr2","attr3"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class VendorBankAccountEntity {

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
     * 账户名称
     */
    @XmlElement(name = "BANK_ACCOUNT_NAME", required = false)
    private String bankAccountName;

    /**
     * 账户编码
     */
    @XmlElement(name = "BANK_ACCOUNT_NUMBER", required = false)
    private String bankAccountNumber;

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
     * 发启用状态
     */
    @XmlElement(name = "ENABLED_FLAG", required = false)
    private String enabledFlag;

    /**
     * 备注
     */
    @XmlElement(name = "COMMENTS", required = false)
    private String comments;

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