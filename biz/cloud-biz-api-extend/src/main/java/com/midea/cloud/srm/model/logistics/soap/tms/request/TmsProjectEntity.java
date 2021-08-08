package com.midea.cloud.srm.model.logistics.soap.tms.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pre>
 *  tms港口请求实体类 用于接收港口数据的实体
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/26 15:01
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {
        "projectCode",
        "projectName",
        "projectStatus",
        "address",
        "unit",
        "projectTotal",
        "businessMode",
        "country",
        "sourceSystem",
        "attr1",
        "attr2",
        "attr3",
        "attr4",
        "attr5",
        "attr6",
        "attr7",
        "attr8",
        "attr9"
})
public class TmsProjectEntity {

    /**
     * 项目编码
     */
    @XmlElement(name = "projectCode", required = false)
    private String projectCode;

    /**
     * 项目名称
     */
    @XmlElement(name = "projectName", required = false)
    private String projectName;

    /**
     * 项目状态
     */
    @XmlElement(name = "projectStatus", required = false)
    private String projectStatus;

    /**
     * 地址
     */
    @XmlElement(name = "address", required = false)
    private String address;

    /**
     * 单位
     */
    @XmlElement(name = "unit", required = false)
    private String unit;

    /**
     * 项目总量
     */
    @XmlElement(name = "projectTotal", required = false)
    private String projectTotal;

    /**
     * 业务模式(字典码BUSINESS_MODE)
     */
    @XmlElement(name = "businessMode", required = false)
    private String businessMode;

    /**
     * 国家
     */
    @XmlElement(name = "country", required = false)
    private String country;

    /**
     * 来源系统
     */
    @XmlElement(name = "sourceSystem", required = false)
    private String sourceSystem;

    /**
     * 备用字段1
     */
    @XmlElement(name = "attr1", required = false)
    private String attr1;

    /**
     * 备用字段2
     */
    @XmlElement(name = "attr2", required = false)
    private String attr2;

    /**
     * 备用字段3
     */
    @XmlElement(name = "attr3", required = false)
    private String attr3;

    /**
     * 备用字段4
     */
    @XmlElement(name = "attr4", required = false)
    private String attr4;

    /**
     * 备用字段5
     */
    @XmlElement(name = "attr5", required = false)
    private String attr5;

    /**
     * 备用字段6
     */
    @XmlElement(name = "attr6", required = false)
    private String attr6;

    /**
     * 备用字段7
     */
    @XmlElement(name = "attr7", required = false)
    private String attr7;

    /**
     * 备用字段8
     */
    @XmlElement(name = "attr8", required = false)
    private String attr8;

    /**
     * 备用字段9
     */
    @XmlElement(name = "attr9", required = false)
    private String attr9;

}
