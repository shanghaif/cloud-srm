package com.midea.cloud.srm.supcooperate.deliver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.BigDecimalUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.OrderDeliveryDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.OrderDeliveryDetail;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteDetail;
import com.midea.cloud.srm.supcooperate.deliver.mapper.OrderDeliveryDetailMapper;
import com.midea.cloud.srm.supcooperate.deliver.service.IDeliverPlanDetailService;
import com.midea.cloud.srm.supcooperate.deliver.service.IOrderDeliveryDetailService;
import com.midea.cloud.srm.supcooperate.order.service.IDeliveryNoteDetailService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  到货计划明细表 服务实现类
 * </pre>
 *
 * @author zhi1772778785@163.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-28 13:59:09
 *  修改内容:
 * </pre>
 */
@Service
public class OrderDeliveryDetailServiceImpl extends ServiceImpl<OrderDeliveryDetailMapper, OrderDeliveryDetail> implements IOrderDeliveryDetailService {

    @Autowired
    private OrderDeliveryDetailMapper orderDeliveryDetailMapper;
    @Autowired
    private IDeliveryNoteDetailService iDeliveryNoteDetailService;
    @Autowired
    private IDeliverPlanDetailService iDeliverPlanDetailService;
    @Override
    public PageInfo<OrderDeliveryDetail> orderDeliveryDetailListPage(OrderDeliveryDetailDTO orderDeliveryDetail) {
        PageUtil.startPage(orderDeliveryDetail.getPageNum(), orderDeliveryDetail.getPageSize());
        QueryWrapper<OrderDeliveryDetailDTO> wrapper = new QueryWrapper<>();
        //到货计划详情ID
        wrapper.eq(orderDeliveryDetail.getDeliverPlanDetailId()!=null,"a.DELIVER_PLAN_DETAIL_ID", orderDeliveryDetail.getDeliverPlanDetailId());
        //采购订单号
        wrapper.in(CollectionUtils.isNotEmpty(orderDeliveryDetail.getOrderIdList()), "a.ORDER_NUMBER", orderDeliveryDetail.getOrderIdList());
        //订单日期开始
        wrapper.ge(orderDeliveryDetail.getStartDate() != null, "b.CEEA_PURCHASE_ORDER_DATE", orderDeliveryDetail.getStartDate());
        //订单末尾日期
        wrapper.le(orderDeliveryDetail.getEndDate() != null, "b.CEEA_PURCHASE_ORDER_DATE", orderDeliveryDetail.getEndDate());
        //采购员
        wrapper.like(StringUtils.isNotEmpty(orderDeliveryDetail.getBuyerName()), "b.BUYER_NAME", orderDeliveryDetail.getBuyerName());
        //到货计划表ID
        wrapper.eq(orderDeliveryDetail.getDeliverPlanId() != null, "a.DELIVER_PLAN_ID", orderDeliveryDetail.getDeliverPlanId());
        //收货地址
        wrapper.eq(StringUtils.isNotEmpty(orderDeliveryDetail.getCeeaReceiveAddress()), "b.CEEA_RECEIVE_ADDRESS", orderDeliveryDetail.getCeeaReceiveAddress());
        //业务实体条件查询
        wrapper.eq(orderDeliveryDetail.getOrgId() != null, "b.CEEA_ORG_ID", orderDeliveryDetail.getOrgId());
        //库存组织条件查询
        wrapper.eq(orderDeliveryDetail.getOrganizationId() != null, "c.CEEA_ORGANIZATION_ID", orderDeliveryDetail.getOrganizationId());
        return new PageInfo<OrderDeliveryDetail>(orderDeliveryDetailMapper.orderDeliveryDetailListPage(wrapper));
        //return new PageInfo<OrderDeliveryDetail>(this.list(wrapper));
    }

    /**
     * 到货计划转送货单分页查询
     * @param orderDeliveryDetail
     * @return
     */
    @Override
    public List<OrderDeliveryDetailDTO> orderDeliveryDetailListPageCopy(OrderDeliveryDetailDTO orderDeliveryDetail) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (ObjectUtils.isEmpty(loginAppUser)||ObjectUtils.isEmpty(loginAppUser.getCompanyId())){
            return new ArrayList<>();
        }
        QueryWrapper<OrderDeliveryDetailDTO> wrapper = new QueryWrapper<>();
        //到货计划详情ID
        wrapper.eq(orderDeliveryDetail.getDeliverPlanDetailId()!=null,"a.DELIVER_PLAN_DETAIL_ID", orderDeliveryDetail.getDeliverPlanDetailId());
        //采购订单号
        wrapper.in(CollectionUtils.isNotEmpty(orderDeliveryDetail.getOrderIdList()), "a.ORDER_NUMBER", orderDeliveryDetail.getOrderIdList());
        //订单日期开始
        wrapper.ge(orderDeliveryDetail.getStartDate() != null, "b.CEEA_PURCHASE_ORDER_DATE", orderDeliveryDetail.getStartDate());
        //订单末尾日期
        wrapper.le(orderDeliveryDetail.getEndDate() != null, "b.CEEA_PURCHASE_ORDER_DATE", orderDeliveryDetail.getEndDate());
        //采购员
        wrapper.like(StringUtils.isNotEmpty(orderDeliveryDetail.getBuyerName()), "b.BUYER_NAME", orderDeliveryDetail.getBuyerName());
        //到货计划表ID
        wrapper.eq(orderDeliveryDetail.getDeliverPlanId() != null, "a.DELIVER_PLAN_ID", orderDeliveryDetail.getDeliverPlanId());
        //收货地址
        wrapper.eq(StringUtils.isNotEmpty(orderDeliveryDetail.getCeeaReceiveAddress()), "b.CEEA_RECEIVE_ADDRESS", orderDeliveryDetail.getCeeaReceiveAddress());
        //业务实体条件查询
        wrapper.eq(orderDeliveryDetail.getOrgId() != null, "b.CEEA_ORG_ID", orderDeliveryDetail.getOrgId());
        //库存组织条件查询
        wrapper.eq(orderDeliveryDetail.getOrganizationId() != null, "c.CEEA_ORGANIZATION_ID", orderDeliveryDetail.getOrganizationId());
        //到货计划号
        wrapper.like(StringUtils.isNotEmpty(orderDeliveryDetail.getDeliverPlanNum()),"a.DELIVER_PLAN_NUM",orderDeliveryDetail.getDeliverPlanNum());
        wrapper.gt("IFNULL(a.PLAN_RECEIVE_NUM,0)-IFNULL(a.DELIVERY_QUANTITY,0)",0);
        return orderDeliveryDetailMapper.orderDeliveryDetailListPageCopy(wrapper);
    }




    @Override
    public List<OrderDeliveryDetail> orderDeliveryDetailList(OrderDeliveryDetail orderDeliveryDetail) {
        QueryWrapper<OrderDeliveryDetail> wrapper = new QueryWrapper<>();
        //到货计划详情ID
        wrapper.eq("DELIVER_PLAN_DETAIL_ID", orderDeliveryDetail.getDeliverPlanDetailId());
        //采购订单号
        //wrapper.in(CollectionUtils.isNotEmpty(orderDeliveryDetail.getOrderIdList()), "ORDER_NUMBER", orderDeliveryDetail.getOrderIdList());
        //订单日期开始
        //wrapper.ge(orderDeliveryDetail.getStartDate() != null, "ORDER_DATE", orderDeliveryDetail.getStartDate());
        //订单末尾日期
        //wrapper.le(orderDeliveryDetail.getEndDate() != null, "ORDER_DATE", orderDeliveryDetail.getEndDate());
        //采购员
        wrapper.like(StringUtils.isNotEmpty(orderDeliveryDetail.getBuyerName()), "BUYER_NAME", orderDeliveryDetail.getBuyerName());
        //到货计划id
        wrapper.eq(orderDeliveryDetail.getDeliverPlanId() != null, "DELIVER_PLAN_ID", orderDeliveryDetail.getDeliverPlanId());
        //采购订单明细id
        wrapper.eq(orderDeliveryDetail.getOrderDetailId()!=null,"ORDER_DETAIL_ID",orderDeliveryDetail.getOrderDetailId());
        //状态
        wrapper.eq(StringUtils.isNotEmpty(orderDeliveryDetail.getOrderDeliveryDetailState()),"ORDER_DELIVERY_DETAIL_STATE",orderDeliveryDetail.getOrderDeliveryDetailState());
        return this.list(wrapper);
    }

    /**
     * 订单交货明细废弃
     */
    public void getAuditNote(Long id){
        //1、获取对应的订单交货明细
        OrderDeliveryDetail orderDeliveryDetail = this.getById(id);
        //2、判断单据状态，是否启动废弃
        if (!"Y".equals(orderDeliveryDetail.getOrderDeliveryDetailState())){
            return;
        }
        //3、获取回写数量
        BigDecimal deliveryQuantity = getDeliveryQuantity(orderDeliveryDetail);
        // 回写到货计划行
        // 修改状态
        // 删除指定订单明细
        // 回写采购订单明细已匹配数量

    }

    /**
     * 获取可以回写的匹配数量
     * @param orderDeliveryDetail
     * @return
     */
    @Override
    public BigDecimal getDeliveryQuantity(OrderDeliveryDetail orderDeliveryDetail){
        BigDecimal planReceiveNum = BigDecimalUtil.isEmpty(orderDeliveryDetail.getPlanReceiveNum());
        //获取订单交货明细已送货数量
        BigDecimal deliveryQuantity = BigDecimalUtil.isEmpty(orderDeliveryDetail.getDeliveryQuantity());
        //获取关联订单交货明细的已送货的送货单数量之和
        DeliveryNoteDetail deliveryNoteDetail = new DeliveryNoteDetail();
        deliveryNoteDetail.setCeeaArrivalDetailId(orderDeliveryDetail.getDeliverPlanDetailId());
        List<DeliveryNoteDetail> deliveryNoteDetailList = iDeliveryNoteDetailService.DeliveryNoteDetailList(deliveryNoteDetail);
        BigDecimal finalBigDecimal = new BigDecimal(0);
        //关联订单交货明细的已送货的送货单存在
        if (CollectionUtils.isNotEmpty(deliveryNoteDetailList)){
            deliveryNoteDetailList.forEach(x->{
                BigDecimal empty = BigDecimalUtil.isEmpty(x.getDeliveryQuantity());
                finalBigDecimal.add(empty);

            });
        }
        Assert.isTrue(finalBigDecimal.compareTo(deliveryQuantity)==0,"废弃过程出现数量冲突，请重试或重试无效联系管理员");
        //使用已分配数量-已送货货数量=可回写操作数量
        return planReceiveNum.subtract(finalBigDecimal);
    }
}
