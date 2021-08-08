package com.midea.cloud.srm.model.pm.pr.requirement.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  采购申请明细表（隆基采购申请明细同步） 模型
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-01 10:27:30
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_pr_requisition_detail")
public class RequisitionDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 业务实体ID
     */
    @TableField(value = "OPERATION_UNIT_ID",exist = false)
    private Long operationUnitId;
    /**
     * 业务实体CODE
     */
    @TableField(value = "OPERATION_CODE",exist = false)
    private String operationUnitCode;

    /**
     * 业务实体名称
     */
    @TableField(value = "OPERATION_NAME",exist = false)
    private String operationName;
    /**
     * 物料小类ID(longi)
     */
    @TableField(value = "CATEGORY_ID",exist = false)
    private Long categoryId;
    /**
     * 物料小类名称(longi)
     */
    @TableField(value = "CATEGORY_NAME",exist = false)
    private String categoryName;

    /**
     * 物料小类编码(longi)
     */
    @TableField(value = "CATEGORY_CODE",exist = false)
    private String categoryCode;
    /**
     * 物料大类ID
     */
    @TableField(value = "CATEGORYMIX_ID",exist = false)
    private Long categoryMixId;
    /**
     * 物料大类名称(longi)
     */
    @TableField(value = "CATEGORYMIX_NAME",exist = false)
    private String categoryMixName;

    /**
     * 物料大类编码(longi)
     */
    @TableField(value = "CATEGORYMIX_CODE",exist = false)
    private String categoryMixCode;



    /**
     * 申请行ID
     */
    @TableId("REQUISITION_LINE_ID")
    private Long requisitionLineId;

    /**
     * 采购申请ID（采购申请头ID）
     */
    @TableField("REQUEST_HEADER_ID")
    private Long requestHeaderId;

    /**
     * 行编号
     */
    @TableField("LINE_NUMBER")
    private Long lineNumber;

    /**
     * 行类型
     */
    @TableField("LINE_TYPE")
    private String lineType;

    /**
     * 物料编码
     */
    @TableField("ITEM_NUMBER")
    private String itemNumber;

    /**
     * 物料说明
     */
    @TableField("ITEM_DESCR")
    private String itemDescr;

    /**
     * 类别编码
     */
    @TableField("SET_VALUE")
    private String setValue;

    /**
     * 类别说明
     */
    @TableField("SET_VALUE_DESCR")
    private String setValueDescr;

    /**
     * 币种（为空时取头上币种）
     */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /**
     * 单价
     */
    @TableField("PRICE")
    private BigDecimal price;

    /**
     * 数量
     */
    @TableField("QUANTITY")
    private BigDecimal quantity;

    /**
     * 计量单位
     */
    @TableField("UNIT_OF_MEASURE")
    private String unitOfMeasure;

    /**
     * 来源
     */
    @TableField("SOURCE")
    private String source;

    /**
     * 部门名称
     */
    @TableField("DEPART_NAME")
    private String departName;

    /**
     * 申请人编号
     */
    @TableField("REQUESTOR_NUMBER")
    private String requestorNumber;

    /**
     * 申请人姓名
     */
    @TableField("REQUESTOR_NAME")
    private String requestorName;

    /**
     * 需求组织编码
     */
    @TableField("REQUIRE_ORG_CODE")
    private String requireOrgCode;

    /**
     * 地点ID
     */
    @TableField("LOCATION_ID")
    private Long locationId;

    /**
     * 地点名称
     */
    @TableField("LOCATION_NAME")
    private String locationName;

    /**
     * 通知采购员
     */
    @TableField("NOTE_TO_AGENT")
    private String noteToAgent;

    /**
     * 通知接收人
     */
    @TableField("NOTE_TO_RECEIVER")
    private String noteToReceiver;

    /**
     * 取消标记
     */
    @TableField("CANCEL_FLAG")
    private String cancelFlag;

    /**
     * 取消日期
     */
    @TableField("CANCEL_DATE")
    private String cancelDate;

    /**
     * 取消原因
     */
    @TableField("CANCEL_REASON")
    private String cancelReason;

    /**
     * 关闭代码
     */
    @TableField("CLOSE_CODE")
    private String closeCode;

    /**
     * 关闭时间
     */
    @TableField("CLOSE_DATE")
    private String closeDate;

    /**
     * 关闭原因
     */
    @TableField("CLOSE_REASON")
    private String closeReason;

    /**
     * 执行标记
     */
    @TableField("MODIFIED_BY_AGENT_FLAG")
    private String modifiedByAgentFlag;

    /**
     * 建议供应商名称
     */
    @TableField("SUGGESTED_VENDOR")
    private String suggestedVendor;

    /**
     * 采购员编码
     */
    @TableField("AGENT_NUMBER")
    private String agentNumber;

    /**
     * 采购员姓名
     */
    @TableField("AGENT_NAME")
    private String agentName;

    /**
     * 理由（详细信息页签-理由）
     */
    @TableField("JUSTIFICATION")
    private String justification;

    /**
     * 需求日期
     */
    @TableField("NEED_BY_DATE")
    private String needByDate;

    /**
     * 接口状态(NEW-新增，UPDATE-更新)
     */
    @TableField("ITF_STATUS")
    private String itfStatus;

    /**
     * 备用字段1
     */
    @TableField("ATTR1")
    private String attr1;

    /**
     * 备用字段2
     */
    @TableField("ATTR2")
    private String attr2;

    /**
     * 备用字段3
     */
    @TableField("ATTR3")
    private String attr3;

    /**
     * 备用字段4
     */
    @TableField("ATTR4")
    private String attr4;

    /**
     * 备用字段5
     */
    @TableField("ATTR5")
    private String attr5;

    /**
     * 创建人ID
     */
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;

    /**
     * 创建人
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;

    /**
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedByIp;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;
    /**
     * srm采购申请id
     */
    @TableField("REQUIREMENT_HEAD_ID")
    private Long requirementHeadId;
    /**
     * srm采购明细id
     */
    @TableField("REQUIREMENT_LINE_ID")
    private Long requirementLineId;
    /**
     * 采购需求编号(申请编号)
     */
    @TableField("REQUIREMENT_HEAD_NUM")
    private String requirementHeadNum;

    /**
     * 采购申请行导入状态(1:已导入业务表,0:未导入业务表)
     */
    @TableField("LINE_IMPORT_STATUS")
    private int lineImportStatus;

    /**
     * 项目id、code、name
     */
    @TableField("PROJECT_ID")
    private String ProjectId;
    @TableField("PROJECT_NUMBER")
    private String ProjectNumber;
    @TableField("PROJECT_NAME")
    private String ProjectName;
    /**
     * 任务id、code、name
     */
    @TableField("TASK_ID")
    private String TaskId;
    @TableField("TASK_NUMBER")
    private String TaskNumber;
    @TableField("TASK_NAME")
    private String TaskName;
}
