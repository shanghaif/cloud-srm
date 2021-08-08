package com.midea.cloud.srm.model.logistics.pr.requirement.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 *  <pre>
 *  采购需求头表 模型
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-12 18:46:40
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class LogisticsRequirementHeadQueryDTO extends BaseDTO {

    /**采购需求行表集合*/
    private List<RequirementLine> requirementLineList;

    /** 主键ID */
    private Long requirementHeadId;

    /** 采购需求编号(申请编号) */
    private String requirementHeadNum;

    /**
     * 物流申请单状态
     */
    private String requirementStatus;

    /**
     * 模板ID(longi)
     */
    private Long templateHeadId;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 业务模式(字典编码BUSINESS_MODE)
     */
    private String businessModeCode;

    /**
     * 运输方式(字典编码TRANSPORT_MODE)
     */
    private String transportModeCode;

    /**
     * 业务类型(longi, 数据字典BUSINESS_TYPE)
     */
    private String businessType;

    /**
     * 服务项目名称
     */
    private String serviceProjectName;

    /**
     * 服务项目编码
     */
    private String serviceProjectCode;

    /**
     * 单位
     */
    private String unit;

    /**
     * 项目总量
     */
    private BigDecimal projectTotal;

    /**
     * 申请主题
     */
    private String requirementTitle;

    /**
     * 需求日期
     */
    private LocalDate demandDate;

    /**
     * 预算金额(longi)
     */
    private BigDecimal ceeaTotalBudget;

    /**
     * 币种ID
     */
    private Long currencyId;

    /**
     * 币种名称
     */
    private String currencyName;

    /**
     * 币种编码
     */
    private String currencyCode;

    /**
     * 开始日期
     */
    private LocalDate startDate;

    /**
     * 结束日期
     */
    private LocalDate endDate;

    /**
     * 价格有效开始日期
     */
    private LocalDate priceStartDate;

    /**
     * 价格有效结束日期
     */
    private LocalDate priceEndDate;

    /**
     * 项目地可进最大车型
     */
    private String allowedVehicle;

    /**
     * 装载量
     */
    private BigDecimal loadNumber;

    /**
     * 指定供应商ID
     */
    private Long vendorId;

    /**
     * 指定供应商名称
     */
    private String vendorName;

    /**
     * 指定供应商编码
     */
    private String vendorCode;

    /**
     * 指定供应商原因
     */
    private String vendorReason;

    /**
     * 备注
     */
    private String comments;

    /**
     * 供应商是否提交船期
     */
    private String ifVendorSubmitShipDate;

    /**
     * 申请人工号
     */
    private String applyCode;

    /**
     * 申请人用户名
     */
    private String applyBy;

    /**
     * 申请人ID
     */
    private Long applyId;

    /**
     * 部门ID(longi)
     */
    private String applyDepartmentId;

    /**
     * 部门编码(longi)
     */
    private String applyDepartmentCode;

    /**
     * 部门名称(longi)
     */
    private String applyDepartmentName;

    /**
     * 申请日期
     */
    private LocalDate applyDate;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 备用字段1
     */
    private String attr1;

    /**
     * 备用字段2
     */
    private String attr2;

    /**
     * 备用字段3
     */
    private String attr3;

    /**
     * 备用字段4
     */
    private String attr4;

    /**
     * 备用字段5
     */
    private String attr5;

    /**
     * 备用字段6
     */
    private String attr6;

    /**
     * 备用字段7
     */
    private String attr7;

    /**
     * 备用字段8
     */
    private String attr8;

    /**
     * 备用字段9
     */
    private String attr9;

    /**
     * 备用字段10
     */
    private String attr10;

    /**
     * 备用字段11
     */
    private String attr11;

    /**
     * 备用字段12
     */
    private String attr12;

    /**
     * 备用字段13
     */
    private String attr13;

    /**
     * 备用字段14
     */
    private String attr14;

    /**
     * 备用字段15
     */
    private String attr15;

    /**
     * 来源系统（如：erp系统）
     */
    private String sourceSystem;

    /** 处理状态 */
    private String handleStatus;

    /** 驳回原因 */
    private String rejectReason;


    /** 审核状态 */
    private String auditStatus;

    /** 创建人姓名 */
    private String createdFullName;


    /** 企业编码 */
    private String companyCode;

    /** 组织编码 */
    private String organizationCode;

    /** 创建人ID */
    private Long createdId;

    /** 创建人 */
    private String createdBy;

    /** 创建时间 */
    private Date creationDate;

    /** 创建人IP */
    private String createdByIp;

    /** 最后更新人ID */
    private Long lastUpdatedId;

    /** 更新人 */
    private String lastUpdatedBy;

    /** 最后更新时间 */
    private Date lastUpdateDate;

    /** 最后更新人IP */
    private String lastUpdatedByIp;

    /** 租户ID */
    private String tenantId;

    /** 版本号 */
    private Long version;

    /**
     * 采购类型
     */
    private String ceeaPurchaseType;

    /**
     * 申请类型
     */
    private String ceeaPrType;

    /**
     * 项目编号
     */
    private String ceeaProjectNum;

    /**
     * 项目名称
     */
    private String ceeaProjectName;

    /**
     * 项目负责人用户ID
     */
    private Long ceeaProjectUserId;

    /**
     * 项目负责人用户名称
     */
    private String ceeaProjectUserNickname;

    /**
     * 资产类别
     */
    private String ceeaAssetType;

    /*业务主体ID集*/
    private List<Long> orgIds;

    /*需求部门ID集*/
    private List<String> ceeaDepartmentIds;

    /*需求部门ID*/
    private  String ceeaDepartmentId;

    /*申请人ID集(创建人ID集)*/
    private List<Long> createdIds;

    /*申请起始日期*/
    private LocalDate startApplyDate;

    /*申请截止日期*/
    private LocalDate endApplyDate;

    private Long categoryId;//物料大类ID

    /**
     * 采购申请单号(老系统)
     */
    private String esRequirementHeadNum;

    /**
     * 采购员ID
     */
    private Long ceeaApplyUserId;

    /**
     * 采购员名称
     */
    private String ceeaApplyUserNickname;

    /**
     * 采购员账号
     */
    private String ceeaApplyUserName;

    /**
     * 需求池处理状态
     */
    private String applyProcessStatus;

    /**
     * 需求池分配状态
     */
    private String applyAssignStatus;


    /**
     * 招标ID(longi)
     */
    private Long biddingId;

    /**
     * 招标编码
     */
    private String biddingCode;

    /**
     * 招标名称
     */
    private String biddingeName;

}
