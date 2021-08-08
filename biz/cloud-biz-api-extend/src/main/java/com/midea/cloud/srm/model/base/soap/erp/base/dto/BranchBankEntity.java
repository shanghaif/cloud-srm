package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pre>
 *  （隆基）银行分行接口表请求实体类（Esb总线）
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/14 12:38
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "countryCode","bankNum","bankName","branchBankNum","branchBankName","altBranchBankName","branchBankType",
        "ediSite","eftNumber","descr","addrDetail","disableDate","contractorName","phoneNumber","emailAddr",
        "attr1", "attr2","attr3","attr4","attr5"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class BranchBankEntity {

    /**
     * 国家编码（隆基erp字段）
     */
    @XmlElement(name = "COUNTRY_CODE", required = false)
    private String countryCode;

    /**
     * 银行编号（隆基erp字段）
     */
    @XmlElement(name = "BANK_NUM", required = false)
    private String bankNum;

    /**
     * 银行名称（隆基erp字段）
     */
    @XmlElement(name = "BANK_NAME", required = false)
    private String bankName;

    /**
     * 分行编号（隆基erp字段）
     */
    @XmlElement(name = "BRANCH_BANK_NUM", required = false)
    private String branchBankNum;

    /**
     * 分行名称（隆基erp字段）
     */
    @XmlElement(name = "BRANCH_BANK_NAME", required = false)
    private String branchBankName;

    /**
     * 银行分行名称（隆基erp字段）
     */
    @XmlElement(name = "ALT_BRANCH_BANK_NAME", required = false)
    private String altBranchBankName;

    /**
     * 分行类型（隆基erp字段）
     */
    @XmlElement(name = "BRANCH_BANK_TYPE", required = false)
    private String branchBankType;

    /**
     * EDI地点（隆基erp字段）
     */
    @XmlElement(name = "EDI_SITE", required = false)
    private String ediSite;

    /**
     * EFT编号（隆基erp字段）
     */
    @XmlElement(name = "EFT_NUMBER", required = false)
    private String eftNumber;

    /**
     * 说明（隆基erp字段）
     */
    @XmlElement(name = "DESCR", required = false)
    private String descr;

    /**
     * 分行详细地址（隆基erp字段）
     */
    @XmlElement(name = "ADDR_DETAIL", required = false)
    private String addrDetail;

    /**
     * 无效日期（隆基erp字段）
     */
    @XmlElement(name = "DISABLE_DATE", required = false)
    private String disableDate;

    /**
     * 联系人姓名（隆基erp字段）
     */
    @XmlElement(name = "CONTRACTOR_NAME", required = false)
    private String contractorName;

    /**
     * 联系电话（隆基erp字段）
     */
    @XmlElement(name = "PHONE_NUMBER", required = false)
    private String phoneNumber;

    /**
     * 电子邮箱（隆基erp字段）
     */
    @XmlElement(name = "EMAIL_ADDR", required = false)
    private String emailAddr;

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
