package com.midea.cloud.srm.base.organization.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;

/**
 * <p>
 * 组织与用户关系 Mapper 接口
 * </p>
 *
 * @author huanghb14@meiCloud.com
 * @since 2020-03-19
 */
public interface OrganizationUserMapper extends BaseMapper<OrganizationUser> {

    List<OrganizationUser> listByParam(@Param("organizationUser") OrganizationUser organizationUser);
    
    List<Organization> getFullPathIdByTypeCode(Long userId);
}
