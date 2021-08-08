package com.midea.cloud.srm.model.pm.pr.soap.requisition.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

/**
 * <pre>
 *  采购申请明细接口表实体类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/1 10:32
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "requisitionLineId","poRequisitionId","lineNumber","lineType","itemNumber","itemDescr","setValue","setValueDescr",
        "currencyCode","price","quantity","unitOfMeasure","source","departName","requestorNumber","requestorName",
        "requireOrgCode","locationId","locationName","noteToAgent","noteToReceiver","cancelFlag","cancelDate",
        "cancelReason","closeCode","closeDate","closeReason","modifiedByAgentFlag","suggestedVendor",
        "agentNumber","agentName","justification","needByDate",
        "attr1", "attr2","attr3","attr4","attr5","ProjectId"
        ,"ProjectNumber"
        ,"ProjectName"
        ,"TaskId"
        ,"TaskNumber"
        ,"TaskName"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class RequisitionDetailEntity {

    /**
     * 申请ID
     */
    @XmlElement(name = "PREQUISITION_LINE_ID", required = false)
    private Long requisitionLineId;

    /**
     * 采购申请ID（采购申请头ID）
     */
     @XmlElement(name = "PO_REQUISITION_ID", required = false)
    private Long poRequisitionId;

    /**
     * 行编号
     */
     @XmlElement(name = "LINE_NUMBER", required = false)
    private Long lineNumber;

    /**
     * 行类型
     */
     @XmlElement(name = "LINE_TYPE", required = false)
    private String lineType;

    /**
     * 物料编码
     */
     @XmlElement(name = "ITEM_NUMBER", required = false)
    private String itemNumber;

    /**
     * 物料说明
     */
     @XmlElement(name = "ITEM_DESCR", required = false)
    private String itemDescr;

    /**
     * 类别编码
     */
     @XmlElement(name = "SET_VALUE", required = false)
    private String setValue;

    /**
     * 类别说明
     */
     @XmlElement(name = "SET_VALUE_DESCR", required = false)
    private String setValueDescr;

    /**
     * 币种（为空时取头上币种）
     */
     @XmlElement(name = "CURRENCY_CODE", required = false)
    private String currencyCode;

    /**
     * 单价
     */
     @XmlElement(name = "PRICE", required = false)
    private String price;

    /**
     * 数量
     */
     @XmlElement(name = "QUANTITY", required = false)
    private String quantity;

    /**
     * 计量单位
     */
     @XmlElement(name = "UNIT_OF_MEASURE", required = false)
    private String unitOfMeasure;

    /**
     * 来源
     */
     @XmlElement(name = "SOURCE", required = false)
    private String source;

    /**
     * 部门名称
     */
     @XmlElement(name = "DEPART_NAME", required = false)
    private String departName;

    /**
     * 申请人编号
     */
     @XmlElement(name = "REQUESTOR_NUMBER", required = false)
    private String requestorNumber;

    /**
     * 申请人姓名
     */
     @XmlElement(name = "REQUESTOR_NAME", required = false)
    private String requestorName;

    /**
     * 需求组织编码
     */
     @XmlElement(name = "REQUIRE_ORG_CODE", required = false)
    private String requireOrgCode;

    /**
     * 地点ID
     */
     @XmlElement(name = "LOCATION_ID", required = false)
    private Long locationId;

    /**
     * 地点名称
     */
     @XmlElement(name = "LOCATION_NAME", required = false)
    private String locationName;

    /**
     * 通知采购员
     */
     @XmlElement(name = "NOTE_TO_AGENT", required = false)
    private String noteToAgent;

    /**
     * 通知接收人
     */
     @XmlElement(name = "NOTE_TO_RECEIVER", required = false)
    private String noteToReceiver;

    /**
     * 取消标记
     */
     @XmlElement(name = "CANCEL_FLAG", required = false)
    private String cancelFlag;

    /**
     * 取消日期
     */
     @XmlElement(name = "CANCEL_DATE", required = false)
    private String cancelDate;

    /**
     * 取消原因
     */
     @XmlElement(name = "CANCEL_REASON", required = false)
    private String cancelReason;

    /**
     * 关闭代码
     */
     @XmlElement(name = "CLOSE_CODE", required = false)
    private String closeCode;

    /**
     * 关闭时间
     */
     @XmlElement(name = "CLOSE_DATE", required = false)
    private String closeDate;

    /**
     * 关闭原因
     */
     @XmlElement(name = "CLOSE_REASON", required = false)
    private String closeReason;

    /**
     * 执行标记
     */
     @XmlElement(name = "MODIFIED_BY_AGENT_FLAG", required = false)
    private String modifiedByAgentFlag;

    /**
     * 建议供应商名称
     */
     @XmlElement(name = "SUGGESTED_VENDOR", required = false)
    private String suggestedVendor;

    /**
     * 采购员编码
     */
     @XmlElement(name = "AGENT_NUMBER", required = false)
    private String agentNumber;

    /**
     * 采购员姓名
     */
     @XmlElement(name = "AGENT_NAME", required = false)
    private String agentName;

    /**
     * 理由（详细信息页签-理由）
     */
     @XmlElement(name = "JUSTIFICATION", required = false)
    private String justification;

    /**
     * 需求日期
     */
     @XmlElement(name = "NEED_BY_DATE", required = false)
    private String needByDate;

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
     * 项目id、code、name
     */
    @XmlElement(name ="PROJECT_ID", required = false)
    private String ProjectId;
    @XmlElement(name ="PROJECT_NUMBER", required = false)
    private String ProjectNumber;
    @XmlElement(name ="PROJECT_NAME", required = false)
    private String ProjectName;
    /**
     * 任务id、code、name
     */
    @XmlElement(name ="TASK_ID", required = false)
    private String TaskId;
    @XmlElement(name ="TASK_NUMBER", required = false)
    private String TaskNumber;
    @XmlElement(name ="TASK_NAME", required = false)
    private String TaskName;
}
