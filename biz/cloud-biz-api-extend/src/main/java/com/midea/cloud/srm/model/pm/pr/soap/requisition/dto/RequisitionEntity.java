package com.midea.cloud.srm.model.pm.pr.soap.requisition.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * <pre>
 *  采购申请接口表实体类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/1 8:52
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "operationUnitId","operationName","requestHeaderId","requestNumber","enableFlag","startDate","endDate",
        "authStatus","documentType","projectNumber","projectName","approveDate","description",
        "attr1", "attr2","attr3","attr4","attr5","requisitionLines"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class RequisitionEntity {
    
    /**
     * 业务实体ID
     */
    @XmlElement(name = "OPERATION_UNIT_ID", required = false)
    private Long operationUnitId;

    /**
     * 业务实体名称
     */
    @XmlElement(name = "OPERATION_NAME", required = false)
    private String operationName;

    /**
     * 申请ID
     */
    @XmlElement(name = "REQUEST_HEADER_ID", required = false)
    private Long requestHeaderId;

    /**
     * 申请编号
     */
    @XmlElement(name = "REQUEST_NUMBER", required = false)
    private String requestNumber;

    /**
     * 启用标志
     */
    @XmlElement(name = "ENABLE_FLAG", required = false)
    private String enableFlag;

    /**
     * 起始日期
     */
    @XmlElement(name = "START_DATE", required = false)
    private String startDate;

    /**
     * 截止日期
     */
    @XmlElement(name = "END_DATE", required = false)
    private String endDate;

    /**
     * 状态（传送已审批、已取消）
     */
    @XmlElement(name = "AUTH_STATUS", required = false)
    private String authStatus;

    /**
     * 申请类型
     */
    @XmlElement(name = "DOCUMENT_TYPE", required = false)
    private String documentType;

    /**
     * 项目编号
     */
    @XmlElement(name = "PROJECT_NUMBER", required = false)
    private String projectNumber;

    /**
     * 项目名称
     */
    @XmlElement(name = "PROJECT_NAME", required = false)
    private String projectName;

    /**
     * 审批日期
     */
    @XmlElement(name = "APPROVE_DATE", required = false)
    private String approveDate;

    /**
     * 说明
     */
    @XmlElement(name = "DESCRIPTION", required = false)
    private String description;

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
     * 采购申请明细
     */
    @XmlElement(name = "REQUISITION_LINE", required = false)
    private List<RequisitionDetailEntity> requisitionLines;
    
}
