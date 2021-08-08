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
 *  币种接口表实体类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/25 14:37
 *  修改内容:
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "currencyCode","currencyNameZhs","currencyNameUs","descriptionZhs","descriptionUs","enabledFlag","issuingTerritoryCode",
        "symbol","precision","extendedPrecision","minimumAccountableUnit","startDate","endDate",
        "attr1","attr2","attr3","attr4","attr5"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class CurrencyEntity {
    
    /**
     * 币种代码
     */
    @XmlElement(name = "CURRENCY_CODE", required = false)
    private String currencyCode;

    /**
     * 币种名称中文
     */
    @XmlElement(name = "CURRENCY_NAME_ZHS", required = false)
    private String currencyNameZhs;

    /**
     * 币种名称英文
     */
    @XmlElement(name = "CURRENCY_NAME_US", required = false)
    private String currencyNameUs;

    /**
     * 中文说明
     */
    @XmlElement(name = "DESCRIPTION_ZHS", required = false)
    private String descriptionZhs;

    /**
     * 英文说明
     */
    @XmlElement(name = "DESCRIPTION_US", required = false)
    private String descriptionUs;

    /**
     * 启用标记
     */
    @XmlElement(name = "ENABLED_FLAG", required = false)
    private String enabledFlag;

    /**
     * 发行地区代码
     */
    @XmlElement(name = "ISSUING_TERRITORY_CODE", required = false)
    private String issuingTerritoryCode;

    /**
     * 符号
     */
    @XmlElement(name = "SYMBOL", required = false)
    private String symbol;

    /**
     * 精确度
     */
    @XmlElement(name = "PRECISION", required = false)
    private String precision;

    /**
     * 扩展精确度
     */
    @XmlElement(name = "EXTENDED_PRECISION", required = false)
    private String extendedPrecision;

    /**
     * 最小可记账单位
     */
    @XmlElement(name = "MINIMUM_ACCOUNTABLE_UNIT", required = false)
    private String minimumAccountableUnit;

    /**
     * 起始日期
     */
    @XmlElement(name = "START_DATE", required = false)
    private String startDate;

    /**
     * 终止日期
     */
    @XmlElement(name = "END_DATE", required = false)
    private String endDate;

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
