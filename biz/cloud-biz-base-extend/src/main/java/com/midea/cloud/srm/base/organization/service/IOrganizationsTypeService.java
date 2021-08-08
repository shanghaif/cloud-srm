package com.midea.cloud.srm.base.organization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationsType;

import java.util.List;

/**
*  <pre>
 *  组织类型 服务类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-13 23:53:35
 *  修改内容:
 * </pre>
*/
public interface IOrganizationsTypeService extends IService<OrganizationsType> {

    void batchSaveOrUpdateOrganizationsType(List<OrganizationsType> organizationsTypes);

    List<OrganizationsType> getOrgTypeByUser(Long userId);
}
