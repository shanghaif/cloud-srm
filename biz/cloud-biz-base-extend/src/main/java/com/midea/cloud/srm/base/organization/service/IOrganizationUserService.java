package com.midea.cloud.srm.base.organization.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;

/**
 * <pre>
 *  组织与用户关系 服务类
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
public interface IOrganizationUserService extends IService<OrganizationUser> {

    /**
     * 通过参数查询组织与用户关系列表
     *
     * @param organizationUser
     * @return
     */
    List<OrganizationUser> listByParam(OrganizationUser organizationUser);

    List<Organization> getFullPathIdByTypeCode(Long userId);

    /**
     * 通过组织ID获取组织用户关系对象
     * @param organizationIds
     * @return
     */
    List<OrganizationUser> getUserByOrganizationId(List<Long> organizationIds);
}
