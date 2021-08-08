package com.midea.cloud.srm.model.pm.pr.requirement.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import lombok.Data;
import lombok.experimental.Accessors;

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
public class RequirementHeadQueryDTO extends BaseDTO {

    /**采购需求行表集合*/
    private List<RequirementLine> requirementLineList;

    /** 主键ID */
    private Long requirementHeadId;

    /** 采购需求编号(申请编号) */
    private String requirementHeadNum;

    /** 处理状态 */
    private String handleStatus;

    /** 驳回原因 */
    private String rejectReason;

    /** 备注 */
    private String comments;

    /** 审核状态 */
    private String auditStatus;

    /** 创建人姓名 */
    private String createdFullName;

    /**申请日期 */
    private LocalDate applyDate;

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

}
