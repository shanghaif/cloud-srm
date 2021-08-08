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
        "countryCode",
        "countryNameZhs",
        "countryNameEn",
        "portCode",
        "portNameZhs",
        "portNameEn",
        "portType",
        "status",
        "sourceSystem",
        "attr1",
        "attr2",
        "attr3",
        "attr4",
        "attr5"
})
public class TmsPortEntity {

    /**
     * 国家编号
     */
    @XmlElement(name = "countryCode", required = false)
    private String countryCode;

    /**
     * 国家中文名
     */
    @XmlElement(name = "countryNameZhs", required = false)
    private String countryNameZhs;

    /**
     * 国家英文名
     */
    @XmlElement(name = "countryNameEn", required = false)
    private String countryNameEn;

    /**
     * 港口代码
     */
    @XmlElement(name = "portCode", required = false)
    private String portCode;

    /**
     * 港口中文名称
     */
    @XmlElement(name = "portNameZhs", required = false)
    private String portNameZhs;

    /**
     * 港口英文名称
     */
    @XmlElement(name = "portNameEn", required = false)
    private String portNameEn;

    /**
     * 港口类型(字典编码PORT_TYPE)
     */
    @XmlElement(name = "portType", required = false)
    private String portType;

    /**
     * 状态(字典编码LOGISTICS_STATUS)
     */
    @XmlElement(name = "status", required = false)
    private String status;

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
    
}
