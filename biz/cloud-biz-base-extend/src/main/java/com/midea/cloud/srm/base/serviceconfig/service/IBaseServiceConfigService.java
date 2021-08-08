package com.midea.cloud.srm.base.serviceconfig.service;

import com.midea.cloud.srm.model.base.serviceconfig.entity.ServiceConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
*  <pre>
 *  产品与服务以及供方管理配置 服务类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-21 19:02:27
 *  修改内容:
 * </pre>
*/
public interface IBaseServiceConfigService extends IService<ServiceConfig> {

    void saveForOne(ServiceConfig serviceConfig);

}
