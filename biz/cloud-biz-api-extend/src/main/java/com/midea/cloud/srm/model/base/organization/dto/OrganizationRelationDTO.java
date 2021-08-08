package com.midea.cloud.srm.model.base.organization.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: ${DATE} ${TIME}
 *  修改内容:
 * </pre>
 */
@Data
public class OrganizationRelationDTO extends BaseDTO {

    //组织关系
    private Long id;

    private Long parentRelId;

    private String organizationName;

    private String organizationCode;

    private Long organizationId;

    private String fullPathId;

    private String parentOrganizationName;

    private String parentOrganizationCode;

    private Long parentOrganizationId;

    private String enabled;

    //组织信息
    private Long organizationTypeId;

    private String organizationTypeCode;

    private String organizationTypeName;

    private LocalDate startDate;

    private LocalDate endDate;

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
     * 更新时间
     */
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    private String lastUpdatedByIp;
}
