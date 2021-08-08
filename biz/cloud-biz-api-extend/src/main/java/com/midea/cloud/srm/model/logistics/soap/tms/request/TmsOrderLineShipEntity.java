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
 *  修改日期: 2021/1/5 20:20
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {
        "orderHeadNum",
        "rowNum",
        "fromPort",
        "toPort",
        "wholeArk",
        "mon",
        "tue",
        "wed",
        "thu",
        "fri",
        "sat",
        "sun",
        "transitTime",
        "company",
        "transferPort",
        "attr1",
        "attr2",
        "attr3",
        "attr4",
        "attr5",
        "attr6"
})
public class TmsOrderLineShipEntity {
    /**
     * 合同号
     */
    @XmlElement(name = "orderHeadNum", required = false)
    private String orderHeadNum;

    /**
     * 行号
     */
    @XmlElement(name = "rowNum", required = false)
    private String rowNum;

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
     * FCL/LCL
     */
    @XmlElement(name = "wholeArk", required = false)
    private String wholeArk;

    /**
     * Mon
     */
    @XmlElement(name = "mon", required = false)
    private String mon;

    /**
     * Tue
     */
    @XmlElement(name = "tue", required = false)
    private String tue;

    /**
     * Wed
     */
    @XmlElement(name = "wed", required = false)
    private String wed;

    /**
     * Thu
     */
    @XmlElement(name = "thu", required = false)
    private String thu;

    /**
     * Fri
     */
    @XmlElement(name = "fri", required = false)
    private String fri;

    /**
     * Sat
     */
    @XmlElement(name = "sat", required = false)
    private String sat;

    /**
     * Sun
     */
    @XmlElement(name = "sun", required = false)
    private String sun;

    /**
     * Transit Time
     */
    @XmlElement(name = "transitTime", required = false)
    private String transitTime;

    /**
     * 船公司
     */
    @XmlElement(name = "company", required = false)
    private String company;

    /**
     * 中转港
     */
    @XmlElement(name = "transferPort", required = false)
    private String transferPort;


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
