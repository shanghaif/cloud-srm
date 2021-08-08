package com.midea.cloud.srm.cm.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.cm.model.service.IModelLineService;
import com.midea.cloud.srm.cm.order.mapper.OrderMapper;
import com.midea.cloud.srm.cm.order.service.IOrderService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.cm.model.entity.ModelLine;
import com.midea.cloud.srm.model.cm.order.dto.OrderDto;
import com.midea.cloud.srm.model.cm.order.entity.Order;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
*  <pre>
 *  合同单据 服务实现类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-06 09:37:25
 *  修改内容:
 * </pre>
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Resource
    private IModelLineService iModelLineService;
    @Resource
    private BaseClient baseClient;

    @Override
    @Transactional
    public Long add(OrderDto orderDto) {
        Order order = orderDto.getOrder();
        List<ModelLine> modelLineList = orderDto.getModelLines();
        Assert.notNull(order.getOrderName(),"合同名称不能为空");
        // 检查合同名字是否重复
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ORDER_NAME",order.getOrderName());
        List<Order> orderList = this.list(queryWrapper);
        if(CollectionUtils.isNotEmpty(orderList)){
            throw new BaseException("合同名称重复");
        }
        // 保存头信息
        // 获取单据编号
        order.setOrderCode(baseClient.seqGen(SequenceCodeConstant.SEQ_SCC_CM_COMPANY_MODEL));
        Long modelHeadId = order.getModelHeadId();
        Assert.notNull(modelHeadId,"modelHeadId不能为空");
        // 单据ID
        long orderId = IdGenrator.generate();
        order.setOrderId(orderId);
        // 默认激活状态
        if (StringUtil.isEmpty(order.getEnable())){
            order.setEnable("Y");
        }
        this.save(order);
        // 保存行信息
        if(CollectionUtils.isNotEmpty(modelLineList)){
            for(ModelLine modelLine: modelLineList){
//                modelLine.setOrderId(orderId);
                modelLine.setModelHeadId(modelHeadId);
                // 获取id
                long id = IdGenrator.generate();
                modelLine.setModelLineId(id);
                // 设置头id
                iModelLineService.save(modelLine);
            }
        }
        return orderId;
    }

    @Override
    public OrderDto queryById(Long orderId) {
        Assert.notNull(orderId,"orderId不能为空");
        Order order = this.getById(orderId);
        QueryWrapper<ModelLine> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ORDER_ID",orderId);
        List<ModelLine> modelLines = iModelLineService.list(queryWrapper);
        OrderDto orderDto = new OrderDto();
        orderDto.setOrder(order);
        orderDto.setModelLines(modelLines);
        return orderDto;
    }

    @Override
    @Transactional
    public void update(OrderDto orderDto) {
        Order order = orderDto.getOrder();
        List<ModelLine> modelLines = orderDto.getModelLines();
        Assert.notNull(order.getOrderId(),"orderId不能为空");
        this.updateById(order);
        if(CollectionUtils.isNotEmpty(modelLines)){
            modelLines.forEach(modelLine -> {
                if (StringUtil.notEmpty(modelLine.getModelLineId())) {
                    iModelLineService.updateById(modelLine);
                }else {
                    long id = IdGenrator.generate();
                    modelLine.setModelLineId(id);
                    iModelLineService.save(modelLine);
                }
            });
        }
    }
}
