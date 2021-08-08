package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidProcess;

/**
*  <pre>
 *  招标流程表 服务类
 * </pre>
*
* @author fengdc3@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-31 18:58:55
 *  修改内容:
 * </pre>
*/
public interface IBidProcessService extends IService<BidProcess> {

    void updateByBidingId(BidProcess bidProcess);
}
