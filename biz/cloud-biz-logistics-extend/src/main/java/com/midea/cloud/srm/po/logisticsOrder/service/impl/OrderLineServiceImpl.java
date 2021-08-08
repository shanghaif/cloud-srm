package com.midea.cloud.srm.po.logisticsOrder.service.impl;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.pm.pr.requirement.RequirementApproveStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.model.logistics.po.order.entity.*;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderLine;
import com.midea.cloud.srm.po.logisticsOrder.service.IOrderHeadService;
import com.midea.cloud.srm.po.logisticsOrder.service.IOrderLineFeeService;
import com.midea.cloud.srm.po.logisticsOrder.mapper.LogisticsOrderLineMapper;
import com.midea.cloud.srm.po.logisticsOrder.service.IOrderLineService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
*  <pre>
 *  物流采购订单行表 服务实现类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-05 18:51:00
 *  修改内容:
 * </pre>
*/
@Service(value = "LogisticsOrderLineServiceImpl")
@Slf4j
public class OrderLineServiceImpl extends ServiceImpl<LogisticsOrderLineMapper, OrderLine> implements IOrderLineService {


    @Autowired
    private IOrderLineFeeService iOrderLineFeeService;
    @Autowired
    private IOrderHeadService iOrderHeadService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrderLineBatch(OrderHead orderHead, List<OrderLine> orderLineList) {
        log.info("批量新增物流采购订单行addOrderLineBatch，参数：{},{}", orderHead, orderLineList);
        Objects.requireNonNull(orderHead);
        if (CollectionUtils.isEmpty(orderLineList)) return;

        int rowNum = 0;
        Iterator<OrderLine> iterator = orderLineList.iterator();
        while (iterator.hasNext()) {
            long id = IdGenrator.generate();
            OrderLine line = iterator.next();
            line.setOrderLineId(id)
                    .setOrderHeadId(orderHead.getOrderHeadId())
                    .setOrderHeadNum(orderHead.getOrderHeadNum())
                    .setRowNum(++rowNum);
            iOrderLineFeeService.addOrderLineFeeBatch(line,line.getOrderLineFeeList());
        }

        this.saveBatch(orderLineList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatch(OrderHead orderHead, List<OrderLine> orderLineList) {
        OrderLine orderLine = new OrderLine();
        orderLine.setOrderHeadId(orderHead.getOrderHeadId());
        QueryWrapper<OrderLine> queryWrapper = new QueryWrapper<>(orderLine);
        List<OrderLine> oldLineList = this.list(queryWrapper);
        List<Long> oldLineIdList = oldLineList.stream().map(OrderLine::getOrderLineId).collect(Collectors.toList());
        List<Long> newLineIdList = new ArrayList<>();

        List<OrderLine> orderLineListAdd = new LinkedList<>();
        List<OrderLine> orderLineListUpdate = new LinkedList<>();
        for (int i = 0; i < orderLineList.size(); i++) {
            OrderLine line = orderLineList.get(i);
            Long orderLineId = line.getOrderLineId();
            line.setRowNum(i + 1);

            if (orderLineId == null) {
                //新增
                line.setOrderLineId(IdGenrator.generate()).setOrderHeadId(orderHead.getOrderHeadId()).setOrderHeadNum(orderHead.getOrderHeadNum());
                orderLineListAdd.add(line);
            } else {
                // 更新
                newLineIdList.add(orderLineId);
                orderLineListUpdate.add(line);
            }
            iOrderLineFeeService.updateBatch(line,line.getOrderLineFeeList());
        }
        //批量增加行程明细行
        if(CollectionUtils.isNotEmpty(orderLineListAdd)){
            this.saveBatch(orderLineListAdd);
        }
        //批量更新行程明细行
        if(CollectionUtils.isNotEmpty(orderLineListUpdate)){
            this.updateBatchById(orderLineListUpdate);
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

    @Override
    public void removeBatch(Long orderHeadId) {
        log.info("批量删除采购订单行：参数{}",orderHeadId);
        Objects.requireNonNull(orderHeadId);
        List<OrderLine> lines = this.list(Wrappers.lambdaQuery(OrderLine.class).eq(OrderLine::getOrderHeadId, orderHeadId));
        if(CollectionUtils.isEmpty(lines)){
            return;
        }
        lines.parallelStream().forEach(x->{
            iOrderLineFeeService.remove(Wrappers.lambdaQuery(OrderLineFee.class).eq(OrderLineFee::getOrderLineId,x.getOrderLineId()));
        });
        this.remove(Wrappers.lambdaQuery(OrderLine.class).eq(OrderLine::getOrderHeadId,orderHeadId));
    }

    @Override
    public List<OrderLine> listBatch(Long orderHeadId) {
        log.info("批量查询采购订单行：参数{}",orderHeadId);
        Objects.requireNonNull(orderHeadId);
        List<OrderLine> lines = this.list(Wrappers.lambdaQuery(OrderLine.class).eq(OrderLine::getOrderHeadId, orderHeadId));
        if(CollectionUtils.isEmpty(lines)){
            return null;
        }
        lines.parallelStream().forEach(x->{
            List<OrderLineFee> orderLineFees = iOrderLineFeeService.list(Wrappers.lambdaQuery(OrderLineFee.class).eq(OrderLineFee::getOrderLineId, x.getOrderLineId()));
            x.setOrderLineFeeList(orderLineFees);
        });
        return lines;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByLineId(Long lineId) {
        log.info("删除物流采购订单，参数：{}",lineId);
        OrderLine orderLine = this.getById(lineId);
        if(Objects.isNull(orderLine)){
            throw new BaseException(String.format("未找到物流采购订单行，单据id：%s",orderLine));
        }
        OrderHead orderHead = iOrderHeadService.getById(orderLine.getOrderHeadId());

        //拟定状态下才可删除
        if (RequirementApproveStatus.DRAFT.getValue().equals(orderHead.getOrderStatus())) {
            this.removeById(lineId);
            iOrderLineFeeService.remove(Wrappers.lambdaQuery(new OrderLineFee().setOrderLineId(lineId)));
        } else {
            throw new BaseException(LocaleHandler.getLocaleMsg("只有拟定状态下才可以删除"));
        }
    }
}
