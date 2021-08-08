package com.midea.cloud.srm.po.logisticsOrder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderHead;

import java.util.List;

/**
 * <p>
 * 物流采购订单头表 Mapper 接口
 * </p>
 *
 * @author xiexh12@meicloud.com
 * @since 2020-12-05
 */
public interface LogisticsOrderHeadMapper extends BaseMapper<OrderHead> {

    List<OrderHead> list(OrderHead orderHead);
}
