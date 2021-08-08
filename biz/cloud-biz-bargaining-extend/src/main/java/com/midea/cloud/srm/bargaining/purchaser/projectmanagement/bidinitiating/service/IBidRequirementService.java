package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirement;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;

import java.util.List;

/**
*  <pre>
 *  招标需求表 服务类
 * </pre>
*
* @author fengdc3@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:  tanjl11@meicloud.com
 *  修改日期: 2020-09-03 17:04:28
 *  修改内容:
 * </pre>
*/
public interface IBidRequirementService extends IService<BidRequirement> {

    void saveBidRequirement(BidRequirement bidRequirement, List<BidRequirementLine> bidRequirementLineList);

    void updateBidRequirement(BidRequirement bidRequirement, List<BidRequirementLine> bidRequirementLineList);

    BidRequirement getBidRequirement(Long bidingId);
}
