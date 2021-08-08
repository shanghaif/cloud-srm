package com.midea.cloud.srm.model.perf.template.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
 *  <pre>
 *  绩效模型头表和采购分类表DTO
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-28 16:41:07
 *  修改内容:
 * </pre>
 */
@Data
public class PerfTemplateHeaderQueryDTO extends BaseDTO {

    private static final long serialVersionUID = 738589275952332305L;

    /**
     * 主键ID
     */
    private Long templateHeadId;

    /**
     * 模型名称
     */
    private String templateName;

    /**
     * 评价区间(MONTHLY-月度，QUARTER季度，HALF_YEAR-半年度，YEAR-年度)
     */
    private String evaluationPeriod;

    /**
     * 是否删除(Y-是/N-否)
     */
    private String deleteFlag;

    /**
     * 模板状态(DRAFT-拟定、VALID-有效、INVALID-失效)
     */
    private String templateStatus;

    /**
     * 采购组织ID
     */
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 采购组织CODE
     */
    private String organizationCode;

    /**
     * 采购组织名称
     */
    private String organizationName;

    /**
     * 生效开始日期
     */
    private Date startDate;

    /**
     * 生效日期
     */
    private Date endDate;

    /**
     * 生效日期
     */
    private Date effectiveDate;

    /**
     * 创建人ID
     */
    private Long createdId;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 创建人IP
     */
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    private Long lastUpdatedId;

    /**
     * 更新人
     */
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    private String lastUpdatedByIp;

    /**
     * 创建人姓名
     */
    private String createdFullName;

    /**
     * 最后更新人姓名
     */
    private String lastUpdatedFullName;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 冗余采购分类ID
     */
    private Long categoryId;

    /**
     * 冗余采购分类CODE
     */
    private String categoryCode;

    /**
     * 冗余采购分类名称
     */
    private String categoryName;


}
