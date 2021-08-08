package com.midea.cloud.srm.model.logistics.po.order.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.enums.SourceFrom;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 *  <pre>
 *  物流采购订单头表 模型
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-05 18:50:05
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_lgt_order_head")
public class OrderHead extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("ORDER_HEAD_ID")
    private Long orderHeadId;

    /**
     * 采购订单编号(订单编号)
     */
    @TableField("ORDER_HEAD_NUM")
    private String orderHeadNum;

    /**
     * 物流订单单状态
     * 字典编码：LOGISTICS_PRICE_STATUS
     * {{@link com.midea.cloud.common.enums.logistics.LogisticsOrderStatus}}
     */
    @TableField("ORDER_STATUS")
    private String orderStatus;


    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 供应商编码
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

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
     * {{@link com.midea.cloud.common.enums.logistics.TransportModeEnum}}
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
    @TableField("SERVICE_PROJECT_NAME")
    private String serviceProjectName;

    /**
     * 服务项目编码
     */
    @TableField("SERVICE_PROJECT_CODE")
    private String serviceProjectCode;

    /**
     * 是否供应商确认
     */
    @TableField("IF_NEED_VENDOR_COMFIRM")
    private String ifNeedVendorComfirm;

    /**
     * 是否需要船期
     */
    @TableField("IF_NEED_SHIP_DATE")
    private String ifNeedShipDate;

    /**
     * 是否供方提交船期
     */
    @TableField("IF_VENDOR_SUBMIT_SHIP_DATE")
    private String ifVendorSubmitShipDate;

    /**
     * 订单主题
     */
    @TableField("ORDER_TITLE")
    private String orderTitle;

    /**
     * 中标顺序
     */
    @TableField("BIDDING_SEQUENCE")
    private String biddingSequence;

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
     * 采购员工号
     */
    @TableField("CEEA_APPLY_USER_NAME")
    private String ceeaApplyUserName;

    /**
     * 采购员用户名
     */
    @TableField("CEEA_APPLY_USER_NICKNAME")
    private String ceeaApplyUserNickname;

    /**
     * 采购员ID
     */
    @TableField("CEEA_APPLY_USER_ID")
    private Long ceeaApplyUserId;

    /**
     * 订单日期
     */
    @TableField("ORDER_DATE")
    private LocalDate orderDate;

    /**
     * 采购部门id
     */
    @TableField("PURCHASE_DEPARTMENT_ID")
    private String purchaseDepartmentId;

    /**
     * 采购部门编码
     */
    @TableField("PURCHASE_DEPARTMENT_CODE")
    private String purchaseDepartmentCode;

    /**
     * 采购部门名称
     */
    @TableField("PURCHASE_DEPARTMENT_NAME")
    private String purchaseDepartmentName;

    /**
     * 申请部门ID
     */
    @TableField("APPLY_DEPARTMENT_ID")
    private String applyDepartmentId;

    /**
     * 申请部门编码
     */
    @TableField("APPLY_DEPARTMENT_CODE")
    private String applyDepartmentCode;

    /**
     * 申请部门名称
     */
    @TableField("APPLY_DEPARTMENT_NAME")
    private String applyDepartmentName;

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
     * 订单申请人ID
     */
    @TableField("APPLY_ID")
    private Long applyId;

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
     * 税率id
     */
    @TableField("TAX_ID")
    private Long taxId;

    /**
     * 税率编码
     */
    @TableField("TAX_CODE")
    private String taxCode;

    /**
     * 税率值
     */
    @TableField("TAX_RATE")
    private BigDecimal taxRate;

    /**
     * 采购需求编号(申请编号)
     */
    @TableField("REQUIREMENT_HEAD_NUM")
    private String requirementHeadNum;

    /**
     * 采购申请Id
     */
    @TableField("REQUIREMENT_HEAD_ID")
    private String requirementHeadId;

    /**
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;

    /**
     * 起草人意见
     */
    @TableField("DRAFTER_OPINION")
    private String drafterOpinion;

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
     * 同步tms状态
     * {{@link com.midea.cloud.common.enums.logistics.LogisticsOrderTmsStatus}}
     */
    @TableField("TMS_STATUS")
    private String tmsStatus;

    /**
     * 同步tms返回消息
     */
    @TableField("TMS_INFO")
    private String tmsInfo;

    /**
     * 物流采购订单 来源单据类型
     * 字典编码：SOURCE_DATA
     * {{@link com.midea.cloud.common.enums.logistics.OrderSourceFrom}}
     */
    @TableField("ORDER_SOURCE_FROM")
    private String orderSourceFrom;

    /**
     * 是否拒绝过 Y/N
     */
    @TableField("IF_REJECTED")
    private String ifRejected;

    /**
     * 招标单 来源单据类型 采购申请/手工创建
     * SourceFrom
     * {{@link SourceFrom}}
     */
    @TableField(exist = false)
    private String sourceFrom;

    /**
     * 起始时间
     */
    @TableField(exist = false)
    private Date fromDate;

    /**
     * 结束时间
     */
    @TableField(exist = false)
    private Date toDate;

    /**
     * 登录供应商id
     */
    @TableField(exist = false)
    private Long loginCompanyId;

    /**
     * 合同编码
     */
    @TableField(exist = false)
    private String contractCode;

    /**
     * 拒绝原因
     */
    @TableField(exist = false)
    private String rejectReason;
    /**
     * 数量说明
     */
    @TableField("NUMBER_COMMENT")
    private String numberComment;
}
