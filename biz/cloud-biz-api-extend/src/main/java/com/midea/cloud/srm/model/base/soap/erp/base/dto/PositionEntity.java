package com.midea.cloud.srm.model.base.soap.erp.base.dto;

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
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/17 16:23
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "id","positionNbr","effdt","effStatus","descr","descrshort",
        "deptid","jobcode","reportsTo","lastupddttm","lgiIntDt","resourceStatus"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class PositionEntity {

    /**
     * 主键ID
     */
    @XmlElement(name = "ID", required = false)
    private String id;

    /**
     * 职位编号
     */
    @XmlElement(name = "POSITION_NBR", required = false)
    private String positionNbr;

    /**
     * 生效日期
     */
    @XmlElement(name = "EFFDT", required = false)
    private String effdt;

    /**
     * 生效状态（A–有效，I-无效）
     */
    @XmlElement(name = "EFF_STATUS", required = false)
    private String effStatus;

    /**
     * 描述
     */
    @XmlElement(name = "DESCR", required = false)
    private String descr;

    /**
     * 简短描述
     */
    @XmlElement(name = "DESCRSHORT", required = false)
    private String descrshort;

    /**
     * 部门ID
     */
    @XmlElement(name = "DEPTID", required = false)
    private String deptid;

    /**
     * 岗位代码
     */
    @XmlElement(name = "JOBCODE", required = false)
    private String jobcode;

    /**
     * 直接上级编号
     */
    @XmlElement(name = "REPORTS_TO", required = false)
    private String reportsTo;

    /**
     * 上次更新日期时间
     */
    @XmlElement(name = "LASTUPDDTTM", required = false)
    private String lastupddttm;

    /**
     * 时间戳
     */
    @XmlElement(name = "LGI_INT_DT", required = false)
    private String lgiIntDt;

    /**
     * 来源状态(NEW-数据在接口表里,没同步到业务表,SUCCESS-同步到业务表,ERROR-数据同步到业务表出错)
     */
    @XmlElement(name = "RESOURCE_STATUS", required = false)
    private String resourceStatus;
}
