package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidFileConfig;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;

import java.util.List;

/**
*  <pre>
 *  供方必须上传附件配置表 服务类
 * </pre>
*
* @author fengdc3@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-20 20:13:34
 *  修改内容:
 * </pre>
*/
public interface IBidFileConfigService extends IService<BidFileConfig> {
    void saveBatchBidFileConfig(List<BidFileConfig> bidFileConfigList, Long bidingId);

    void updateBatchBidFileConfig(List<BidFileConfig> bidFileConfigList, Biding biding);
}
