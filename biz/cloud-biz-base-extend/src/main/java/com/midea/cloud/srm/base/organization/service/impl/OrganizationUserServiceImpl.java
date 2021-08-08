package com.midea.cloud.srm.base.organization.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.base.organization.mapper.OrganizationUserMapper;
import com.midea.cloud.srm.base.organization.service.IOrganizationUserService;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;

/**
 * <pre>
 *  组织与用户关系 服务实现类
 * </pre>
 *
 * @author huanghb14@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-19 19:04:52
 *  修改内容:
 * </pre>
 */
@Service
public class OrganizationUserServiceImpl extends ServiceImpl<OrganizationUserMapper, OrganizationUser> implements IOrganizationUserService {

	@Autowired
    private OrganizationUserMapper organizationUserMapper;
	
    @Override
    public List<OrganizationUser> listByParam(OrganizationUser organizationUser) {
        return this.getBaseMapper().listByParam(organizationUser);
    }
    
    @Override
    public List<Organization> getFullPathIdByTypeCode(Long userId) {
        return organizationUserMapper.getFullPathIdByTypeCode(userId);
    }

    @Override
    public List<OrganizationUser> getUserByOrganizationId(List<Long> organizationIds) {
        QueryWrapper<OrganizationUser> wrapper = new QueryWrapper();
        wrapper.in(CollectionUtils.isNotEmpty(organizationIds),"ORGANIZATION_ID",organizationIds);
        return this.list(wrapper);
    }
}
