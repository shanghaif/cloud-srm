package com.midea.cloud.srm.model.inq.price.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  价格审批单头表 模型
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-08 15:28:50
 *  修改内容:
 * </pre>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_price_approval_header")
public class ApprovalHeader extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 价格审批单头主键
     */
    @TableId("APPROVAL_HEADER_ID")
    private Long approvalHeaderId;

    /**
     * 寻源方式
     */
    @TableField("SOURCE_TYPE")
    private String sourceType;

    /**
     * 审批状态，1未审批；2已驳回；3已审批；
     */
    @TableField("STATUS")
    private String status;


    /**
     * 价格审批单号
     */
    @TableField("APPROVAL_NO")
    private String approvalNo;


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
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
    private Long lastUpdatedId;

    /**
     * 更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
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


    /*新增的字段*/
    /**
     * 标题
     */
    @TableField("CEEA_TITLE")
    private String ceeaTitle;

    /**
     * 寻源单号
     */
    @TableField("CEEA_SOURCE_NO")
    private String ceeaSourceNo;

    /**
     * 决标方式
     */
    @TableField("CEEA_AWARE_WAY")
    private String ceeaAwareWay;

    /**
     * 配额分配类型
     */
    @TableField("CEEA_ALLOCATION_TYPE")
    private String ceeaAllocationType;

    /**
     * 是否更新价格库
     */
    @TableField("CEEA_IF_UPDATE_PRICE_LIBRARY")
    private String ceeaIfUpdatePriceLibrary;

    /**
     * 需求概述
     */
    @TableField("CEEA_REQUIREMENT_OVERVIEW")
    private String ceeaRequirementOverview;

    /**
     * 说明
     */
    @TableField("CEEA_DESCRIPTION")
    private String ceeaDescription;


    /*不使用的字段*/
    /**
     * 组织id(不使用)
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 组织全路径虚拟ID(不使用)
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 组织名称(不使用)
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 业务单据id(不使用)
     */
    @TableField("BUSINESS_ID")
    private Long businessId;

    /**
     * 业务单据号(不使用)
     */
    @TableField("BUSINESS_NO")
    private String businessNo;

    /**
     * 业务单据标题(不使用)
     */
    @TableField("BUSINESS_TITLE")
    private String businessTitle;


    /**
     * 报价精度(不使用)
     */
    @TableField("PRICE_NUM")
    private String priceNum;

    /**
     * 流程ID(不使用)
     */
    @TableField("CBPM_INSTANCE_ID")
    private String cbpmInstaceId;
    /**
     * 转换合同的状态
     */
    @TableField("CEEA_TRANSFER_STATUS")
    private String transferStatus;
    /**
     * 中标总金额
     */
    @TableField("CEEA_BID_AMOUNT")
    private BigDecimal bidAmount;
    /**
     * 币种
     */
    @TableField("CEEA_STANDARD_CURRENCY")
    private String standardCurrency;
    /**
     * 报表转换状态
     */
    @TableField("CEEA_REPORT_TRANSFER_STATUS")
    private String reportTransferStatus;
	     //起草人意见
    @TableField("CEEA_DRAFTER_OPINION")
    private String ceeaDrafterOpinion;
}
