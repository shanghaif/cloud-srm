package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  （隆基）税率接口表请求实体类（Esb总线）
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/12 18:24
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "taxRateId","taxRegimeCode","taxRateCode","percentageRate","effectiveFrom","effectiveTo","sourceSystem",
        "attr1", "attr2","attr3","attr4","attr5","attr6","attr7","attr8","attr9","attr10"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class PurchaseTaxEntity {

    /**
     * 税率ID（隆基erp字段）
     */
    @XmlElement(name = "TAX_RATE_ID", required = false)
    private String taxRateId;

    /**
     * 税种编码（隆基erp字段）
     */
    @XmlElement(name = "TAX_REGIME_CODE", required = false)
    private String taxRegimeCode;

    /**
     * 税码（隆基erp字段）
     */
    @XmlElement(name = "TAX_RATE_CODE", required = false)
    private String taxRateCode;

    /**
     * 税率额（隆基erp字段）
     */
    @XmlElement(name = "PERCENTAGE_RATE", required = false)
    private BigDecimal percentageRate;

    /**
     * 有效日期从（隆基erp字段）
     */
    @XmlElement(name = "EFFECTIVE_FROM", required = false)
    private String effectiveFrom;

    /**
     * 有效日期至（隆基erp字段）
     */
    @XmlElement(name = "EFFECTIVE_TO", required = false)
    private String effectiveTo;

    /**
     * 来源系统
     */
    @XmlElement(name = "SOURCE_SYSTEM_", required = false)
    private String sourceSystem;

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

    /**
     * 备用字段6
     */
    @XmlElement(name = "ATTR6", required = false)
    private String attr6;

    /**
     * 备用字段7
     */
    @XmlElement(name = "ATTR7", required = false)
    private String attr7;

    /**
     * 备用字段8
     */
    @XmlElement(name = "ATTR8", required = false)
    private String attr8;

    /**
     * 备用字段9
     */
    @XmlElement(name = "ATTR9", required = false)
    private String attr9;

    /**
     * 备用字段10
     */
    @XmlElement(name = "ATTR10", required = false)
    private String attr10;
}
