package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pre>
 *  物料接口表实体类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/20 15:29
 *  修改内容:
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "categorySetCode","categorySetNameZhs","categorySetNameUs","setValue","setValueDescZhs","setValueDescUs"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class CategorySetsEntity {
    /**
     * 类别集代码
     */
    @XmlElement(name = "CATEGORY_SET_CODE", required = false)
    private String categorySetCode;

    /**
     * 类别集名称（ZHS）
     */
    @XmlElement(name = "CATEGORY_SET_NAME_ZHS", required = false)
    private String categorySetNameZhs;

    /**
     * 类别集名称（US）
     */
    @XmlElement(name = "CATEGORY_SET_NAME_US", required = false)
    private String categorySetNameUs;

    /**
     * 类别值
     */
    @XmlElement(name = "SET_VALUE", required = false)
    private String setValue;

    /**
     * 类别说明-ZHS
     */
    @XmlElement(name = "SET_VALUE_DESC_ZHS", required = false)
    private String setValueDescZhs;

    /**
     * 类别说明（US）
     */
    @XmlElement(name = "SET_VALUE_DESC_US", required = false)
    private String setValueDescUs;
}