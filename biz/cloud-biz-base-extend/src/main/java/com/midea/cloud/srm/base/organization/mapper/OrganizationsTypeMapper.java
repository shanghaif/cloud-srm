package com.midea.cloud.srm.base.organization.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationsType;

/**
 * <p>
 * 组织类型 Mapper 接口
 * </p>
 *
 * @author chensl26@meicloud.com
 * @since 2020-02-13
 */
public interface OrganizationsTypeMapper extends BaseMapper<OrganizationsType> {
	List<OrganizationsType>  getOrgTypeByUser(Long userId);
}
