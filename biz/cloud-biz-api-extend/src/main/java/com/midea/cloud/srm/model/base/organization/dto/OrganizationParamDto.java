package com.midea.cloud.srm.model.base.organization.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

@Data
public class OrganizationParamDto extends BaseDTO {
    /**
     * 组织类型编码
     */
    private String organizationTypeCode;
    /**
     * 父组织Id
     */
    private String parentOrganizationId;
    /**
     * 用户id
     */
    private Long userId;
}
