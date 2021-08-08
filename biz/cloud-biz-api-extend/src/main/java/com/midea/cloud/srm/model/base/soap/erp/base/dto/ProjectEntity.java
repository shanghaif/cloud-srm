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
 *  修改日期: 2020/9/3 19:01
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "projectId",
        "projectNumber",
        "projectName",
        "orgId",
        "orgName",
        "projectTypeName",
        "projectLongName",
        "projectStatusCode",
        "projectStatusName",
        "projectOaNumber",
        "attr1",
        "attr2",
        "attr3",
        "attr4",
        "attr5",
        "tasks"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class ProjectEntity {

    /**
     * 项目ID
     */
    @XmlElement(name = "projectId", required = false)
    private String projectId;

    /**
     * 项目编号
     */
    @XmlElement(name = "projectNumber", required = false)
    private String projectNumber;

    /**
     * 项目名称
     */
    @XmlElement(name = "projectName", required = false)
    private String projectName;

    /**
     * 业务实体ID
     */
    @XmlElement(name = "orgId", required = false)
    private String orgId;

    /**
     * 业务实体名称
     */
    @XmlElement(name = "orgName", required = false)
    private String orgName;

    /**
     * 项目类型名称
     */
    @XmlElement(name = "projectTypeName", required = false)
    private String projectTypeName;

    /**
     * 项目全称
     */
    @XmlElement(name = "projectLongName", required = false)
    private String projectLongName;

    /**
     * 项目状态编码
     */
    @XmlElement(name = "projectStatusCode", required = false)
    private String projectStatusCode;

    /**
     * 项目状态名称
     */
    @XmlElement(name = "projectStatusName", required = false)
    private String projectStatusName;

    /**
     * OA项目编号
     */
    @XmlElement(name = "projectOaNumber", required = false)
    private String projectOaNumber;

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
     * 任务集合
     */
    @XmlElement(name = "tasks", required = false)
    private TasksEntity tasks;

}
