package com.midea.cloud.srm.model.supplierauth.soap.erp.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pre>
 *  供应商基础信息返回实体类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/2 11:05
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {
        "vendorName","vendorNumber","sourceSysCode","sourceLineId",
        "attr1","attr2","attr3","attr4","attr5"
})
public class VendorEntity {

    /**
     * 供应商名称
     */
    @XmlElement(name = "VENDOR_NAME", required = false)
    private String vendorName;

    /**
     * 供应商编码
     */
    @XmlElement(name = "VENDOR_NUMBER", required = false)
    private String vendorNumber;

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
