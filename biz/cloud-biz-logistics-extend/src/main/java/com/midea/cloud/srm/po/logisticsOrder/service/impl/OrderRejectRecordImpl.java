package com.midea.cloud.srm.po.logisticsOrder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderRejectRecord;
import com.midea.cloud.srm.po.logisticsOrder.mapper.OrderRejectRecordMapper;
import com.midea.cloud.srm.po.logisticsOrder.service.IOrderRejectRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2021/2/24 11:48
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class OrderRejectRecordImpl extends ServiceImpl<OrderRejectRecordMapper, OrderRejectRecord> implements IOrderRejectRecordService {
}
