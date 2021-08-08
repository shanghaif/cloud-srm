package com.midea.cloud.srm.base.serviceconfig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.serviceconfig.entity.SupplierConfig;

/**
*  <pre>
 *   服务类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-26 16:18:14
 *  修改内容:
 * </pre>
*/
public interface ISupplierConfigService extends IService<SupplierConfig> {

     void saveForOne(SupplierConfig supplierConfig);

}
