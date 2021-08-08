package com.midea.cloud.srm.po.logisticsOrder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderHead;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderLineShip;
import com.midea.cloud.srm.po.logisticsOrder.mapper.OrderLineShipMapper;
import com.midea.cloud.srm.po.logisticsOrder.service.IOrderLineShipService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
*  <pre>
 *  物流采购订单行船期表 服务实现类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-11 11:21:42
 *  修改内容:
 * </pre>
*/
@Service
@Slf4j
public class OrderLineShipServiceImpl extends ServiceImpl<OrderLineShipMapper, OrderLineShip> implements IOrderLineShipService {

    /**
     * 批量新增订单船期行程
     * @param orderHead
     * @param orderLineShipList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrderShipBatch(OrderHead orderHead, List<OrderLineShip> orderLineShipList) {
        log.info("批量新增物流订单行船期addOrderShipBatch，参数：{},{}", orderHead, orderLineShipList);
        Objects.requireNonNull(orderHead);
        if (CollectionUtils.isEmpty(orderLineShipList)) {
            return;
        }

        int rowNum = 0;
        Iterator<OrderLineShip> iterator = orderLineShipList.iterator();
        while (iterator.hasNext()) {
            long id = IdGenrator.generate();
            OrderLineShip line = iterator.next();
            line.setOrderLineShipId(id)
                    .setOrderHeadId(orderHead.getOrderHeadId())
                    .setOrderHeadNum(orderHead.getOrderHeadNum())
                    .setRowNum(++rowNum);
        }

        this.saveBatch(orderLineShipList);
    }

    /**
     * 批量修改订单船期行程
     * @param orderHead
     * @param orderLineShipList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderLineShipBatch(OrderHead orderHead, List<OrderLineShip> orderLineShipList) {
        if(CollectionUtils.isEmpty(orderLineShipList)){
            return;
        }
        QueryWrapper<OrderLineShip> queryWrapper = new QueryWrapper<>(new OrderLineShip().setOrderHeadId(orderHead.getOrderHeadId()));
        List<OrderLineShip> oldLineShipList = this.list(queryWrapper);
        List<Long> oldLineShipIds = oldLineShipList.stream().map(OrderLineShip::getOrderLineShipId).collect(Collectors.toList());
        List<Long> newLineShipIds = new LinkedList<>();

        //新增的list
        List<OrderLineShip> orderLineShipListAdd = new LinkedList<>();
        List<OrderLineShip> orderLineShipListUpdate = new LinkedList<>();
        //修改的list
        for(int i=0;i<orderLineShipList.size();i++){
            OrderLineShip line = orderLineShipList.get(i);
            Long lineId = line.getOrderLineShipId();
            line.setRowNum(i + 1);

            if(Objects.isNull(lineId)){
                //新增
                line.setOrderHeadId(orderHead.getOrderHeadId());
                orderLineShipListAdd.add(line);
            }else{
                //更新
                newLineShipIds.add(lineId);
                orderLineShipListUpdate.add(line);
            }
        }
        //批量添加
        if(CollectionUtils.isNotEmpty(orderLineShipListAdd)){
            this.saveBatch(orderLineShipListAdd);
        }
        //批量修改
        if(CollectionUtils.isNotEmpty(orderLineShipListUpdate)){
            this.updateBatchById(orderLineShipListUpdate);
        }

        //删除
        List<Long> removeIds = new LinkedList<>();
        if(CollectionUtils.isNotEmpty(oldLineShipIds)){
            for (Long id : oldLineShipIds) {
                if (!newLineShipIds.contains(id)) {
                    removeIds.add(id);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(removeIds)){
            this.removeByIds(removeIds);
        }

    }

}
