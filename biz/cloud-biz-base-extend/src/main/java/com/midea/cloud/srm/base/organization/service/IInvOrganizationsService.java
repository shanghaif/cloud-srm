package com.midea.cloud.srm.base.organization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.organization.entity.InvOrganization;
import com.midea.cloud.srm.model.base.organization.entity.Organization;

import java.util.List;

/**
*  <pre>
 *  库存组织接口表 服务类
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-04 15:39:50
 *  修改内容:
 * </pre>
*/
public interface IInvOrganizationsService extends IService<InvOrganization> {

    List<Organization> queryIvnByParent(Long parentId);

    InvOrganization queryInvByInvCode(String invCode);
}
