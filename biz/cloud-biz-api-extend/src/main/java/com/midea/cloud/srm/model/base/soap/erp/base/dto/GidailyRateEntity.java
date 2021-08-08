package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

/**
 * <pre>
 *  汇率接口表实体类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/25 11:07
 *  修改内容:
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "fromCurrency","toCurrency","conversionDate","conversionType","conversionRate","creationDate","createdBy","lastUpdateDate","lastUpdatedBy",
        "attr1","attr2","attr3","attr4","attr5"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class GidailyRateEntity {

    /**
     * 源币种
     */
    @XmlElement(name = "FROM_CURRENCY", required = false)
    private String fromCurrency;

    /**
     * 目的币种
     */
    @XmlElement(name = "TO_CURRENCY", required = false)
    private String toCurrency;

    /**
     * 日期
     */
    @XmlElement(name = "CONVERSION_DATE", required = false)
    private String conversionDate;

    /**
     * 汇率类型
     */
    @XmlElement(name = "CONVERSION_TYPE", required = false)
    private String conversionType;

    /**
     * 汇率
     */
    @XmlElement(name = "CONVERSION_RATE", required = false)
    private BigDecimal conversionRate;

    /**
     * 创建时间
     */
    @XmlElement(name = "CREATION_DATE", required = false)
    private String creationDate;

    /**
     * 创建人
     */
    @XmlElement(name = "CREATED_BY", required = false)
    private BigDecimal createdBy;

    /**
     * 最后更新时间
     */
    @XmlElement(name = "LAST_UPDATE_DATE", required = false)
    private String lastUpdateDate;

    /**
     * 最后更新人
     */
    @XmlElement(name = "LAST_UPDATED_BY", required = false)
    private BigDecimal lastUpdatedBy;

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
