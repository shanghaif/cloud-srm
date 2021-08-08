package com.midea.cloud.srm.bid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtBidShipPeriod;

import java.util.List;

/**
 * <p>
 * 物流招标船期表 Mapper 接口
 * </p>
 *
 * @author wangpr@meiCloud.com
 * @since 2021-01-06
 */
public interface LgtBidShipPeriodMapper extends BaseMapper<LgtBidShipPeriod> {

    /**
     * 获取当前轮次的船期
     * @param bidShipPeriod
     * @return
     */
    List<LgtBidShipPeriod> listCurrency(LgtBidShipPeriod bidShipPeriod);
}
