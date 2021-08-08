package com.midea.cloud.srm.model.logistics.soap.tms.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2021/1/5 19:57
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {
        "orderHeadNum",
        "vendorCode",
        "priceStartDate",
        "priceEndDate",
        "settlementMethod",
        "contractType",
        "businessModeCode",
        "transportModeCode",
        "status",
        "sourceSystem",
        "attr1",
        "attr2",
        "attr3",
        "attr4",
        "attr5",
        "attr6"
})
public class TmsOrderHeadEntity {
    /**
     * 合同号
     */
    @XmlElement(name = "orderHeadNum", required = false)
    private String orderHeadNum;

    /**
     * 承运商
     */
    @XmlElement(name = "vendorCode", required = false)
    private String vendorCode;

    /**
     * 生效期
     */
    @XmlElement(name = "priceStartDate", required = false)
    private String priceStartDate;

    /**
     * 失效期
     */
    @XmlElement(name = "priceEndDate", required = false)
    private String priceEndDate;

    /**
     * 结算方式
     */
    @XmlElement(name = "settlementMethod", required = false)
    private String settlementMethod;

    /**
     * 合同类型
     */
    @XmlElement(name = "contractType", required = false)
    private String contractType;

    /**
     * 业务模式
     */
    @XmlElement(name = "businessModeCode", required = false)
    private String businessModeCode;

    /**
     * 运输方式
     */
    @XmlElement(name = "transportModeCode", required = false)
    private String transportModeCode;

    /**
     * 有效性
     */
    @XmlElement(name = "status", required = false)
    private String status;

    /**
     * 来源系统
     */
    @XmlElement(name = "sourceSystem", required = false)
    private String sourceSystem;


    @XmlElement(name = "attr1", required = false)
    private String attr1;


    @XmlElement(name = "attr2", required = false)
    private String attr2;


    @XmlElement(name = "attr3", required = false)
    private String attr3;


    @XmlElement(name = "attr4", required = false)
    private String attr4;

    @XmlElement(name = "attr5", required = false)
    private String attr5;

    @XmlElement(name = "attr6", required = false)
    private String attr6;

}
