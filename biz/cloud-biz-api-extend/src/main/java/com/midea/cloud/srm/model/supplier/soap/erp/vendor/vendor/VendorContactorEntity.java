package com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendor;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pre>
 *  供应商联系人 VendorContactors
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
        "contactPartyId","contractorName","phoneNumber","emailAddress","disableDate"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class VendorContactorEntity {

    /**
     * 联系人Id
     */
    @XmlElement(name = "CONTACT_PARTY_ID", required = false)
    private String contactPartyId;

    /**
     * 姓名
     */
    @XmlElement(name = "CONTRACTOR_NAME", required = false)
    private String contractorName;

    /**
     * 电话号码
     */
    @XmlElement(name = "PHONE_NUMBER", required = false)
    private String phoneNumber;

    /**
     * 邮箱
     */
    @XmlElement(name = "EMAIL_ADDRESS", required = false)
    private String emailAddress;

    /**
     * 失效日期
     */
    @XmlElement(name = "DISABLE_DATE", required = false)
    private String disableDate;

}