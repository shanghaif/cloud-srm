package com.midea.cloud.srm.po.logisticsOrder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderHead;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderLineContract;
import com.midea.cloud.srm.po.logisticsOrder.mapper.OrderLineContractMapper;
import com.midea.cloud.srm.po.logisticsOrder.service.IOrderLineContractService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
*  <pre>
 *  物流采购订单行合同表 服务实现类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-05 18:51:30
 *  修改内容:
 * </pre>
*/
@Service
@Slf4j
public class OrderLineContractServiceImpl extends ServiceImpl<OrderLineContractMapper, OrderLineContract> implements IOrderLineContractService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrderContractBatch(OrderHead orderHead, List<OrderLineContract> orderLineContractList) {
        log.info("批量新增物流采购订单行合同addOrderContractBatch，参数：{},{}", orderHead, orderLineContractList);
        Objects.requireNonNull(orderHead);
        if (CollectionUtils.isEmpty(orderLineContractList)) return;

        int rowNum = 0;
        Iterator<OrderLineContract> iterator = orderLineContractList.iterator();
        while (iterator.hasNext()) {
            long id = IdGenrator.generate();
            OrderLineContract line = iterator.next();
            line.setOrderLineContractId(id)
                    .setOrderHeadId(orderHead.getOrderHeadId())
                    .setOrderHeadNum(orderHead.getOrderHeadNum())
                    .setRowNum(++rowNum);
        }

        this.saveBatch(orderLineContractList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatch(OrderHead orderHead, List<OrderLineContract> orderLineList) {
        OrderLineContract orderLine = new OrderLineContract();
        orderLine.setOrderHeadId(orderHead.getOrderHeadId());
        QueryWrapper<OrderLineContract> queryWrapper = new QueryWrapper<>(orderLine);
        List<OrderLineContract> oldLineList = this.list(queryWrapper);
        List<Long> oldLineIdList = oldLineList.stream().map(OrderLineContract::getOrderLineContractId).collect(Collectors.toList());
        List<Long> newLineIdList = new ArrayList<>();

        List<OrderLineContract> orderLineContractListAdd = new LinkedList<>();
        List<OrderLineContract> orderLineContractListUpdate = new LinkedList<>();
        for (int i = 0; i < orderLineList.size(); i++) {
            OrderLineContract line = orderLineList.get(i);
            Long orderLineId = line.getOrderLineContractId();
            line.setRowNum(i + 1);

            if (orderLineId == null) {
                // 新增
                line.setOrderLineContractId(IdGenrator.generate()).setOrderHeadId(orderHead.getOrderHeadId()).setOrderHeadNum(orderHead.getOrderHeadNum());
                orderLineContractListAdd.add(line);
            } else {
                // 更新
                newLineIdList.add(orderLineId);
                orderLineContractListUpdate.add(line);
            }
        }

        //批量增加行程明细行
        if(CollectionUtils.isNotEmpty(orderLineContractListAdd)){
            this.saveBatch(orderLineContractListAdd);
        }
        //批量更新行程明细行
        if(CollectionUtils.isNotEmpty(orderLineContractListUpdate)){
            this.updateBatchById(orderLineContractListUpdate);
        }
        // 删除明细行
        List<Long> removeIds = new LinkedList<>();
        for (Long oldId : oldLineIdList) {
            if (!newLineIdList.contains(oldId)) {
                removeIds.add(oldId);
            }
        }
        if(CollectionUtils.isNotEmpty(removeIds)){
            this.removeByIds(removeIds);
        }
    }
}
