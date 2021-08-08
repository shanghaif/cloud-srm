package com.midea.cloud.srm.inq.inquiry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.inq.inquiry.entity.LadderPrice;

import java.util.List;

/**
*  <pre>
 *  询价--物料阶梯价表 服务类
 * </pre>
*
* @author zhongbh
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 18:46:55
 *  修改内容:
 * </pre>
*/
public interface ILadderPriceService extends IService<LadderPrice> {
    void savePrices(List<LadderPrice> ladderPrices);

    /**
     * 根据询价单物料批量获取阶梯价
     */
    List<LadderPrice> getLadderPrice(List<Long> inquiryItemIds);
}
