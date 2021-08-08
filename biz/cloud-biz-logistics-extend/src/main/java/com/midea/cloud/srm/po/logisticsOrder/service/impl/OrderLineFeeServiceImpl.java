package com.midea.cloud.srm.po.logisticsOrder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderHead;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderLine;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderLineFee;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderLineFee;
import com.midea.cloud.srm.po.logisticsOrder.mapper.OrderLineFeeMapper;
import com.midea.cloud.srm.po.logisticsOrder.service.IOrderLineFeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
*  <pre>
 *  物流采购订单费用项表 服务实现类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-05 18:52:09
 *  修改内容:
 * </pre>
*/
@Service
@Slf4j
public class OrderLineFeeServiceImpl extends ServiceImpl<OrderLineFeeMapper, OrderLineFee> implements IOrderLineFeeService {
    @Override
    public void addOrderLineFeeBatch(OrderLine orderLine, List<OrderLineFee> orderLineFeeList) {
        log.info("批量新增物流采购订单行费用项addOrderLineFeeBatch，参数：{},{}", orderLine, orderLineFeeList);
        Objects.requireNonNull(orderLine);
        if (CollectionUtils.isEmpty(orderLineFeeList)) return;

        int rowNum = 0;
        Iterator<OrderLineFee> iterator = orderLineFeeList.iterator();
        while (iterator.hasNext()) {
            long id = IdGenrator.generate();
            OrderLineFee lineFee = iterator.next();
            lineFee.setOrderLineFeeId(id)
                    .setOrderHeadId(orderLine.getOrderHeadId())
                    .setOrderHeadNum(orderLine.getOrderHeadNum())
                    .setOrderLineId(orderLine.getOrderLineId())
                    .setRowNum(++rowNum);
        }

        this.saveBatch(orderLineFeeList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatch(OrderLine orderLine, List<OrderLineFee> orderLineFeeList) {
        OrderLineFee orderLineFee = new OrderLineFee();
        orderLineFee.setOrderLineId(orderLine.getOrderLineId());
        QueryWrapper<OrderLineFee> queryWrapper = new QueryWrapper<>(orderLineFee);
        List<OrderLineFee> oldLineFeeList = this.list(queryWrapper);
        List<Long> oldLineFeeIdList = oldLineFeeList.stream().map(OrderLineFee::getOrderLineFeeId).collect(Collectors.toList());
        List<Long> newLineFeeIdList = new ArrayList<>();

        List<OrderLineFee> orderLineFeeListAdd = new ArrayList<>();
        List<OrderLineFee> orderLineFeeListUpdate = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(orderLineFeeList)){
            for (int i = 0; i < orderLineFeeList.size(); i++) {
                OrderLineFee line = orderLineFeeList.get(i);
                Long orderLineFeeId = line.getOrderLineFeeId();
                line.setRowNum(i + 1);

                if (orderLineFeeId == null) {
                    // 新增
                    line.setOrderLineFeeId(IdGenrator.generate()).setOrderHeadId(orderLine.getOrderHeadId()).setOrderHeadNum(orderLine.getOrderHeadNum()).setOrderLineId(orderLine.getOrderLineId());
                    orderLineFeeListAdd.add(line);
                } else {
                    // 更新
                    newLineFeeIdList.add(orderLineFeeId);
                    orderLineFeeListUpdate.add(line);
                }
            }
        }

        //批量增加
        if(CollectionUtils.isNotEmpty(orderLineFeeListAdd)){
            this.saveBatch(orderLineFeeListAdd);
        }
        //批量修改
        if(CollectionUtils.isNotEmpty(orderLineFeeListUpdate)){
            this.updateBatchById(orderLineFeeListUpdate);
        }
        // 删除
        List<Long> removeIds = new LinkedList<>();
        if(CollectionUtils.isNotEmpty(oldLineFeeIdList)){
            for (Long id : oldLineFeeIdList) {
                if (!newLineFeeIdList.contains(id)) {
                    removeIds.add(id);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(removeIds)){
            this.removeByIds(removeIds);
        }

    }
}
