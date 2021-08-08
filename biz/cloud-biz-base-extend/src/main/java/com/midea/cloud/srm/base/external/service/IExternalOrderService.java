package com.midea.cloud.srm.base.external.service;

import com.midea.cloud.srm.model.base.external.entity.ExternalOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
*  <pre>
 *  外部订单表 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-23 16:40:11
 *  修改内容:
 * </pre>
*/
public interface IExternalOrderService extends IService<ExternalOrder> {

    void addOrUpdate(ExternalOrder sysExternalOrder);

    Integer getAccountNum();
}
