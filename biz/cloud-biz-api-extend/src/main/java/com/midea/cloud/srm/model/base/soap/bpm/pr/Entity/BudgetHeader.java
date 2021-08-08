package com.midea.cloud.srm.model.base.soap.bpm.pr.Entity;

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
 * @author yourname@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/12/10 16:03
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "instId","instName","projectNum","projectName","projectType","projectClass","amount","typeCode","attr1","attr2","attr3","attr4","attr5"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class BudgetHeader {

    /**
     * 流程实例ID
     */
    @XmlElement(name = "INST_ID", required = false)
    private String instId;

    /**
     * 流程实例名称
     */
    @XmlElement(name = "INST_NAME", required = false)
    private String instName;

    /**
     * 项目编号
     */
    @XmlElement(name = "PROJECT_NUM", required = false)
    private String projectNum;

    /**
     * 项目名称
     */
    @XmlElement(name = "PROJECT_NAME", required = false)
    private String projectName;

    /**
     * 项目类型
     */
    @XmlElement(name = "PROJECT_TYPE", required = false)
    private String projectType;

    /**
     * 项目级别
     */
    @XmlElement(name = "PROJECT_CLASS", required = false)
    private String projectClass;

    /**
     * 项目预算金额
     */
    @XmlElement(name = "AMOUNT", required = false)
    private String amount;

    /**
     * 操作类型
     */
    @XmlElement(name = "TYPE_CODE", required = false)
    private String typeCode;

    /**
     * 弹性字段1
     */
    @XmlElement(name = "attr1", required = false)
    private String attr1;

    /**
     * 弹性字段2
     */
    @XmlElement(name = "attr2", required = false)
    private String attr2;

    /**
     * 弹性字段3
     */
    @XmlElement(name = "attr3", required = false)
    private String attr3;

    /**
     * 弹性字段4
     */
    @XmlElement(name = "attr4", required = false)
    private String attr4;

    /**
     * 弹性字段5
     */
    @XmlElement(name = "attr5", required = false)
    private String attr5;
 }
