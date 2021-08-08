package com.midea.cloud.srm.base.organization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.organization.dto.OrgTypeDto;
import com.midea.cloud.srm.model.base.organization.dto.OrganizationDTO;
import com.midea.cloud.srm.model.base.organization.dto.OrganizationParamDto;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 组织信息 Mapper 接口
 * </p>
 *
 * @author chensl26@meicloud.com
 * @since 2020-02-15
 */
public interface OrganizationMapper extends BaseMapper<Organization> {

    List<OrganizationDTO> selectOrganizationDTO(String organizationTypeName, String organizationName, String parentOrganizationName, String enabled);

    List<Organization> getOrganizationByOrgCode(OrganizationParamDto organizationParamDto);

    List<OrgTypeDto> getOrgTypeDtoList();

    List<Organization> getFatherByChild(@Param("orgId") Long orgId);

    List<Long> queryOuIdByBuId(@Param("buId") Long buId);

    /**
     * 根据业务实体Id查询库存组织
     */
    List<Organization> getInvOrganizationsByOrgId(Long orgId);

    /**
     * 查询所有库存组织业务实体(价格库导入使用)
     * @return
     */
    List<Organization> listAllOrganizationForImport();

    List<Organization> listOrganizationByOrgCode(OrganizationParamDto organizationParamDto);
}
