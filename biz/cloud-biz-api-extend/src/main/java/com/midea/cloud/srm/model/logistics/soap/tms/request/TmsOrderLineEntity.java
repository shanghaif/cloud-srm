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
 *  修改日期: 2021/1/5 19:59
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {
        "orderHeadNum",
        "rowLineNum",
        "fromCountry",
        "fromProvince",
        "fromCity",
        "fromCounty",
        "fromPlace",
        "toCountry",
        "toProvinceCode",
        "toCityCode",
        "toCountyCode",
        "toPlace",
        "vendorOrderNum",
        "transportDistance",
        "transportModeCode",
        "serviceProjectCode",
        "status",
        "realTime",
        "priceStartDate",
        "priceEndDate",
        "wholeArk",
        "fromPort",
        "toPort",
        "unProjectFlag",
        "attr1",
        "attr2",
        "attr3",
        "attr4",
        "attr5",
        "attr6",
        "importExportMethod"
})
public class TmsOrderLineEntity {
    /**
     * 合同号
     */
    @XmlElement(name = "orderHeadNum", required = false)
    private String orderHeadNum;

    /**
     * 行号
     */
    @XmlElement(name = "rowLineNum", required = false)
    private String rowLineNum;

    /**
     * 发货国家
     */
    @XmlElement(name = "fromCountry", required = false)
    private String fromCountry;

    /**
     * 发货省
     */
    @XmlElement(name = "fromProvince", required = false)
    private String fromProvince;

    /**
     * 发货市
     */
    @XmlElement(name = "fromCity", required = false)
    private String fromCity;

    /**
     * 发货区县
     */
    @XmlElement(name = "fromCounty", required = false)
    private String fromCounty;

    /**
     * 发货地址
     */
    @XmlElement(name = "fromPlace", required = false)
    private String fromPlace;

    /**
     * 收货国家
     */
    @XmlElement(name = "toCountry", required = false)
    private String toCountry;

    /**
     * 收货省
     */
    @XmlElement(name = "toProvinceCode", required = false)
    private String toProvinceCode;

    /**
     * 收货市
     */
    @XmlElement(name = "toCityCode", required = false)
    private String toCityCode;

    /**
     * 收货区县
     */
    @XmlElement(name = "toCountyCode", required = false)
    private String toCountyCode;

    /**
     * 收货地址
     */
    @XmlElement(name = "toPlace", required = false)
    private String toPlace;

    /**
     * 供方顺序
     */
    @XmlElement(name = "vendorOrderNum", required = false)
    private String vendorOrderNum;

    /**
     * 公里数
     */
    @XmlElement(name = "transportDistance", required = false)
    private String transportDistance;

    /**
     * 运输方式
     */
    @XmlElement(name = "transportModeCode", required = false)
    private String transportModeCode;

    /**
     * 项目
     */
    @XmlElement(name = "serviceProjectCode", required = false)
    private String serviceProjectCode;

    /**
     * 有效性(固定Y）
     */
    @XmlElement(name = "status", required = false)
    private String status;

    /**
     * 运输周期
     */
    @XmlElement(name = "realTime", required = false)
    private String realTime;

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
     * FCL/LCL
     */
    @XmlElement(name = "wholeArk", required = false)
    private String wholeArk;

    /**
     * 始发港
     */
    @XmlElement(name = "fromPort", required = false)
    private String fromPort;

    /**
     * 目的港
     */
    @XmlElement(name = "toPort", required = false)
    private String toPort;

    /**
     * 非项目可使用(Y)
     */
    @XmlElement(name = "unProjectFlag", required = false)
    private String unProjectFlag;


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


    /**
     * 进出口方式
     */
    @XmlElement(name = "importExportMethod", required = false)
    private String importExportMethod;
}
