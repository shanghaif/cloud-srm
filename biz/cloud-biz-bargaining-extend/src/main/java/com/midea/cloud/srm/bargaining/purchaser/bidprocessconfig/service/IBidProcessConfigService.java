package com.midea.cloud.srm.bargaining.purchaser.bidprocessconfig.service;

import com.midea.cloud.srm.model.bargaining.purchaser.bidprocessconfig.entity.BidProcessConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
*  <pre>
 *  招标流程配置表 服务类
 * </pre>
*
* @author fengdc3@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-16 15:01:13
 *  修改内容:
 * </pre>
*/
public interface IBidProcessConfigService extends IService<BidProcessConfig> {

    void saveOrUpdateConfig(BidProcessConfig bidProcessConfig);
}
