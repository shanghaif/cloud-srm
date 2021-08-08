package com.midea.cloud.srm.bid.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.bid.mapper.LgtBidShipPeriodMapper;
import com.midea.cloud.srm.bid.service.ILgtBidShipPeriodService;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtBidShipPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  <pre>
 *  物流招标船期表 服务实现类
 * </pre>
*
* @author wangpr@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-06 09:08:34
 *  修改内容:
 * </pre>
*/
@Service
public class LgtBidShipPeriodServiceImpl extends ServiceImpl<LgtBidShipPeriodMapper, LgtBidShipPeriod> implements ILgtBidShipPeriodService {

    @Autowired
    private LgtBidShipPeriodMapper bidShipPeriodMapper;

    /**
     * 获取当前轮次的船期明细
     * @param bidShipPeriod
     * @return
     */
    @Override
    public List<LgtBidShipPeriod> listCurrency(LgtBidShipPeriod bidShipPeriod) {
        return bidShipPeriodMapper.listCurrency(bidShipPeriod);
    }
}
