package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pre>
 *  erp单位请求实体类 用于接收单位数据的实体
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/12/22 15:01
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {
        "uomName",
        "uomCode",
        "uomCodeDescr",
        "baseUomFlag",
        "uomClassName",
        "disableDate",
        "languageCode",
        "attr1",
        "attr2",
        "attr3",
        "attr4",
        "attr5",
        "sourceSystem"
})
public class UnitOfMeasureEntity {

    /**
     * 单位名称
     */
    @XmlElement(name = "UOM_NAME", required = false)
    private String uomName;

    /**
     * 单位编码
     */
    @XmlElement(name = "UOM_CODE", required = false)
    private String uomCode;

    /**
     * 单位说明
     */
    @XmlElement(name = "UOM_CODE_DESCR", required = false)
    private String uomCodeDescr;

    /**
     * 基本单位标记
     */
    @XmlElement(name = "BASE_UOM_FLAG", required = false)
    private String baseUomFlag;

    /**
     * 单位分类名称
     */
    @XmlElement(name = "UOM_CLASS_NAME", required = false)
    private String uomClassName;

    /**
     * 失效日期
     */
    @XmlElement(name = "DISABLE_DATE", required = false)
    private String disableDate;

    /**
     * 语言代码
     */
    @XmlElement(name = "LANGUAGE_CODE", required = false)
    private String languageCode;

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
     * 来源系统
     */
    @XmlElement(name = "SOURCE_SYSTEM", required = false)
    private String sourceSystem;

}
