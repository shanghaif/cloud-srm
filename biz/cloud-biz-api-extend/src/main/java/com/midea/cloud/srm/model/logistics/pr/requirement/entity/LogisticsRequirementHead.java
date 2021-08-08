package com.midea.cloud.srm.model.logistics.pr.requirement.entity;


import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.time.LocalDate;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  物流采购需求头表 模型
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-27 10:59:47
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_lgt_requirement_head")
public class LogisticsRequirementHead extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("REQUIREMENT_HEAD_ID")
    private Long requirementHeadId;

    /**
     * 采购需求编号(申请编号)
     */
    @TableField("REQUIREMENT_HEAD_NUM")
    private String requirementHeadNum;

    /**
     * 物流申请单状态
     * 字典编码：LOGISTICS_APPLY_STATUS
     * {{@link com.midea.cloud.common.enums.logistics.pr.requirement.LogisticsApproveStatus}}
     */
    @TableField("REQUIREMENT_STATUS")
    private String requirementStatus;

    /**
     * 需求池处理状态(数据字典LOGISTICS_APPLY_PROCESS_STATUS)
     * 字典编码：LOGISTICS_APPLY_PROCESS_STATUS
     * {{@link com.midea.cloud.common.enums.logistics.pr.requirement.LogisticsApplyProcessStatus}}
     */
    @TableField("APPLY_PROCESS_STATUS")
    private String applyProcessStatus;

    /**
     * 需求池分配状态
     * 字典编码：LOGISTICS_APPLY_ASSIGN_STYLE
     * {{@link com.midea.cloud.common.enums.logistics.pr.requirement.LogisticsApplyAssignStyle}}
     */
    @TableField("APPLY_ASSIGN_STATUS")
    private String applyAssignStatus;


    /**
     * 采购员ID
     */
    @TableField(value = "CEEA_APPLY_USER_ID",updateStrategy= FieldStrategy.IGNORED)
    private Long ceeaApplyUserId;

    /**
     * 采购员名称
     */
    @TableField(value="CEEA_APPLY_USER_NICKNAME",updateStrategy= FieldStrategy.IGNORED)
    private String ceeaApplyUserNickname;

    /**
     * 采购员账号
     */
    @TableField(value = "CEEA_APPLY_USER_NAME",updateStrategy= FieldStrategy.IGNORED)
    private String ceeaApplyUserName;

    /**
     * 模板ID(longi)
     */
    @TableField("TEMPLATE_HEAD_ID")
    private Long templateHeadId;

    /**
     * 模板编码
     */
    @TableField("TEMPLATE_CODE")
    private String templateCode;

    /**
     * 模板名称
     */
    @TableField("TEMPLATE_NAME")
    private String templateName;

    /**
     * 业务模式(字典编码BUSINESS_MODE)
     */
    @TableField("BUSINESS_MODE_CODE")
    private String businessModeCode;

    /**
     * 运输方式(字典编码TRANSPORT_MODE)
     */
    @TableField("TRANSPORT_MODE_CODE")
    private String transportModeCode;

    /**
     * 业务类型(longi, 数据字典BUSINESS_TYPE)
     */
    @TableField("BUSINESS_TYPE")
    private String businessType;

    /**
     * 服务项目名称
     */
    @TableField(value = "SERVICE_PROJECT_NAME", updateStrategy= FieldStrategy.IGNORED)
    private String serviceProjectName;

    /**
     * 服务项目编码
     */
    @TableField(value = "SERVICE_PROJECT_CODE", updateStrategy= FieldStrategy.IGNORED)
    private String serviceProjectCode;

    /**
     * 单位
     */
    @TableField("UNIT")
    private String unit;

    /**
     * 项目总量
     */
    @TableField("PROJECT_TOTAL")
    private BigDecimal projectTotal;

    /**
     * 申请主题
     */
    @TableField("REQUIREMENT_TITLE")
    private String requirementTitle;

    /**
     * 需求日期
     */
    @TableField("DEMAND_DATE")
    private LocalDate demandDate;

    /**
     * 预算金额(longi)
     */
    @TableField("BUDGET_AMOUNT")
    private BigDecimal budgetAmount;

    /**
     * 币种ID
     */
    @TableField("CURRENCY_ID")
    private Long currencyId;

    /**
     * 币种名称
     */
    @TableField("CURRENCY_NAME")
    private String currencyName;

    /**
     * 币种编码
     */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /**
     * 开始日期
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 结束日期
     */
    @TableField("END_DATE")
    private LocalDate endDate;

    /**
     * 价格有效开始日期
     */
    @TableField("PRICE_START_DATE")
    private LocalDate priceStartDate;

    /**
     * 价格有效结束日期
     */
    @TableField("PRICE_END_DATE")
    private LocalDate priceEndDate;

    /**
     * 项目地可进最大车型
     */
    @TableField("ALLOWED_VEHICLE")
    private String allowedVehicle;

    /**
     * 装载量
     */
    @TableField("LOAD_NUMBER")
    private BigDecimal loadNumber;

    /**
     * 指定供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 指定供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 指定供应商编码
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 指定供应商原因
     */
    @TableField("VENDOR_REASON")
    private String vendorReason;

    /**
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;

    /**
     * 供应商是否提交船期
     */
    @TableField("IF_VENDOR_SUBMIT_SHIP_DATE")
    private String ifVendorSubmitShipDate;

    /**
     * 申请人工号
     */
    @TableField("APPLY_CODE")
    private String applyCode;

    /**
     * 申请人用户名
     */
    @TableField("APPLY_BY")
    private String applyBy;

    /**
     * 申请人ID
     */
    @TableField("APPLY_ID")
    private Long applyId;

    /**
     * 部门ID(longi)
     */
    @TableField("APPLY_DEPARTMENT_ID")
    private Long applyDepartmentId;

    /**
     * 部门编码(longi)
     */
    @TableField("APPLY_DEPARTMENT_CODE")
    private String applyDepartmentCode;

    /**
     * 部门名称(longi)
     */
    @TableField("APPLY_DEPARTMENT_NAME")
    private String applyDepartmentName;

    /**
     * 申请日期
     */
    @TableField("APPLY_DATE")
    private LocalDate applyDate;

    /**
     * 联系电话
     */
    @TableField("PHONE")
    private String phone;

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
     * 备用字段6
     */
    @TableField("ATTR6")
    private String attr6;

    /**
     * 备用字段7
     */
    @TableField("ATTR7")
    private String attr7;

    /**
     * 备用字段8
     */
    @TableField("ATTR8")
    private String attr8;

    /**
     * 备用字段9
     */
    @TableField("ATTR9")
    private String attr9;

    /**
     * 备用字段10
     */
    @TableField("ATTR10")
    private String attr10;

    /**
     * 备用字段11
     */
    @TableField("ATTR11")
    private String attr11;

    /**
     * 备用字段12
     */
    @TableField("ATTR12")
    private String attr12;

    /**
     * 备用字段13
     */
    @TableField("ATTR13")
    private String attr13;

    /**
     * 备用字段14
     */
    @TableField("ATTR14")
    private String attr14;

    /**
     * 备用字段15
     */
    @TableField("ATTR15")
    private String attr15;

    /**
     * 来源系统（如：erp系统）
     */
    @TableField("SOURCE_SYSTEM")
    private String sourceSystem;

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
     * 更新人
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
     * 招标ID(longi)
     */
    @TableField(value = "BIDING_ID", updateStrategy = FieldStrategy.IGNORED)
    private Long bidingId;

    /**
     * 招标编码
     */
    @TableField(value = "BIDING_CODE", updateStrategy = FieldStrategy.IGNORED)
    private String bidingCode;

    /**
     * 招标名称
     */
    @TableField(value = "BIDING_NAME", updateStrategy = FieldStrategy.IGNORED)
    private String bidingName;


    /**
     * 采购订单编号
     * (不使用，物流申请与物流订单是一对多关系)
     */
    @TableField(value = "ORDER_HEAD_NUM", updateStrategy = FieldStrategy.IGNORED)
    private String orderHeadNum;

    /**
     * 合同类型
     */
    @TableField("CONTRACT_TYPE")
    private String contractType;

    /**
     * 附件模板id
     */
    @TableField("TEMPLATE_FILE_ID")
    private Long templateFileId;

    /**
     * 创建人中文名
     */
    @TableField("CREATED_BY_NAME")
    private String createdByName;

    /**
     * 商务开标时间
     */
    @TableField(exist = false)
    private Date businessOpenBidTime;

}
