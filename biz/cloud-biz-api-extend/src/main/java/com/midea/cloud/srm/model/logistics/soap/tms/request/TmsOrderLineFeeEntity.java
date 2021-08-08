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
 *  修改日期: 2021/1/5 20:26
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {
        "orderHeadNum",
        "rowLineNum",
        "rowNum",
        "expenseItem",
        "chargeMethod",
        "chargeUnit",
        "minCost",
        "maxCost",
        "expense",
        "currency",
        "ifBack",
        "leg",
        "taxRate",
        "attr1",
        "attr2",
        "attr3",
        "attr4",
        "attr5",
        "attr6"
})
public class TmsOrderLineFeeEntity {
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
     * 费用行号
     */
    @XmlElement(name = "rowNum", required = false)
    private String rowNum;

    /**
     * chargetype
     */
    @XmlElement(name = "expenseItem", required = false)
    private String expenseItem;

    /**
     * 计费方式
     */
    @XmlElement(name = "chargeMethod", required = false)
    private String chargeMethod;

    /**
     * sublevel
     */
    @XmlElement(name = "chargeUnit", required = false)
    private String chargeUnit;

    /**
     * 最小收费
     */
    @XmlElement(name = "minCost", required = false)
    private String minCost;

    /**
     * 最大收费
     */
    @XmlElement(name = "maxCost", required = false)
    private String maxCost;

    /**
     * 费率
     */
    @XmlElement(name = "expense", required = false)
    private String expense;

    /**
     * 币制
     */
    @XmlElement(name = "currency", required = false)
    private String currency;

    /**
     * 是否往返
     */
    @XmlElement(name = "ifBack", required = false)
    private String ifBack;

    /**
     * LEG
     */
    @XmlElement(name = "leg", required = false)
    private String leg;

    /**
     * 税率
     */
    @XmlElement(name = "taxRate", required = false)
    private String taxRate;


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

