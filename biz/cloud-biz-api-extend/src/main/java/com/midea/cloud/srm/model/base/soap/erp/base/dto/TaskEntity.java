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
        "taskId",
        "taskNumber",
        "taskName",
        "attr1",
        "attr2",
        "attr3",
        "attr4",
        "attr5"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class TaskEntity {

    /**
     * 任务ID
     */
    @XmlElement(name = "taskId", required = false)
    private String taskId;

    /**
     * 任务编号
     */
    @XmlElement(name = "taskNumber", required = false)
    private String taskNumber;

    /**
     * 任务名称
     */
    @XmlElement(name = "taskName", required = false)
    private String taskName;

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
