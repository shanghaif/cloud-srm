package com.midea.cloud.srm.model.base.organization.dto;

import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationRelation;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 *
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chensl26@meicloud.com
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
public class OrganizationDTO extends BaseDTO{

    private Organization organization;

    private List<OrganizationRelation> organizationRelations;
}
