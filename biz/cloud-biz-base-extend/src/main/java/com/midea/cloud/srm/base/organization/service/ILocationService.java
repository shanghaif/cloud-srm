package com.midea.cloud.srm.base.organization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.organization.entity.Location;
import com.midea.cloud.srm.model.base.organization.entity.Organization;

import java.util.List;

/**
*  <pre>
 *  地址接口表 服务类
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-17 19:44:45
 *  修改内容:
 * </pre>
*/
public interface ILocationService extends IService<Location> {

    /**
     * 根据库存组织查询地点
     * @param organization
     * @return
     */
    List<Location> getLocationsByOrganization(Organization organization);

}
