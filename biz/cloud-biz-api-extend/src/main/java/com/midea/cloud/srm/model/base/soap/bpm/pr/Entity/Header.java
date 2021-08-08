package com.midea.cloud.srm.model.base.soap.bpm.pr.Entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pre>
 *
 * </pre>
 *
 * @author chenwt24@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-09
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "formId","status","formInstanceId","opinion","documentNum","finalApproverPo"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class Header {
    /**
     * 流程ID
     */
    @XmlElement(name = "DOCUMENT_TYPE", required = false)
    private String formId;

    /**
     * 状态
     */
    @XmlElement(name = "ES_APRROVE_FLAG", required = false)
    private String status;

    /**
     * 单据id
     */
    @XmlElement(name = "ES_RETURN_ID", required = false)
    private String formInstanceId;

    /**
     * 审批意见
     */
    @XmlElement(name = "ES_APRROVE_MESSAGE", required = false)
    private String opinion;

    /**
     * 单据编号
     */
    @XmlElement(name = "DOCUMENT_NUM", required = false)
    private String documentNum;

    /**
     * 订单流程回传最后审批人
     */
    @XmlElement(name = "FINAL_APPROVER_PO", required = false)
    private String finalApproverPo;


}
