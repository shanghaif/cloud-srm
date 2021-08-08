package com.midea.cloud.srm.model.supplierauth.soap.erp.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pre>
 *  供应商联系人信息返回实体类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/2 20:56
 *  修改内容:
 * </pre>
 */

@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {
        "erpVendorId","vendorNumber","contractorName","phoneNumber","emailAddress","disableDate",
        "sourceSysCode","sourceLineId",
        "attr1","attr2","attr3","attr4","attr5"
})
public class VendorContactEntity {

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

    /**
     * 来源系统编码
     */
    @XmlElement(name = "SOURCE_SYS_CODE", required = false)
    private String sourceSysCode;

    /**
     * 来源系统Id
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
