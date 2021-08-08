package com.midea.cloud.srm.bid.purchaser.bidexpert.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.bid.purchaser.bidexpert.entity.BidExpert;

import java.util.List;

/**
*  <pre>
 *  专家库表 服务类
 * </pre>
*
* @author fengdc3@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-19 20:42:21
 *  修改内容:
 * </pre>
*/
public interface IBidExpertService extends IService<BidExpert> {

    PageInfo<BidExpert> listPage(BidExpert bidExpert);

    void saveOrUpdateBidExpertBatch(List<BidExpert> bidExpertList);

    void invalid(List<Long> idList);
}
