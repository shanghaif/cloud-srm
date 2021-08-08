package com.midea.cloud.srm.model.rbac.soap.erp.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pre>
 *  采购员接口表实体类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/28 14:34
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "agentId","agentNumber","agentName","startDate","endDate","categorySetCode","categorySetName",
        "purchaseCategoryCode","purchaseCategoryName","shipTolocationId","shipTolocationName",
        "attr1", "attr2","attr3","attr4","attr5"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class PoAgentEntity {

    /**
     * 采购员ID
     */
   @XmlElement(name = "AGENT_ID", required = false)
    private String agentId;

    /**
     * 采购员编码
     */
   @XmlElement(name = "AGENT_NUMBER", required = false)
    private String agentNumber;

    /**
     * 采购员姓名
     */
   @XmlElement(name = "AGENT_NAME", required = false)
    private String agentName;

    /**
     * 起始日期
     */
   @XmlElement(name = "START_DATE", required = false)
    private String startDate;

    /**
     * 截止日期
     */
   @XmlElement(name = "END_DATE", required = false)
    private String endDate;


    /**
     * 类别集代码
     */
   @XmlElement(name = "CATEGORY_SET_CODE", required = false)
    private String categorySetCode;

    /**
     * 类别集名称
     */
   @XmlElement(name = "CATEGORY_SET_NAME", required = false)
    private String categorySetName;

    /**
     * 采购类别代码
     */
   @XmlElement(name = "PURCHASE_CATEGORY_CODE", required = false)
    private String purchaseCategoryCode;

    /**
     * 采购类别名称
     */
   @XmlElement(name = "PURCHASE_CATEGORY_NAME", required = false)
    private String purchaseCategoryName;

    /**
     * 收货方ID
     */
   @XmlElement(name = "SHIP_TOLOCATION_ID", required = false)
    private String shipTolocationId;

    /**
     * 收货方名称
     */
   @XmlElement(name = "SHIP_TOLOCATION_NAME", required = false)
    private String shipTolocationName;

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
