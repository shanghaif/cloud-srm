package com.midea.cloud.srm.supcooperate.deliver.service.impl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.order.OrderStatus;
import com.midea.cloud.common.enums.pm.po.PurchaseOrderEnum;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.Requisition;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.DeliverPlanDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.OrderAppointDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlan;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlanDetail;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.OrderAppoint;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import com.midea.cloud.srm.supcooperate.deliver.mapper.OrderAppointMapper;
import com.midea.cloud.srm.supcooperate.deliver.service.IDeliverPlanDetailService;
import com.midea.cloud.srm.supcooperate.deliver.service.IOrderAppointService;
import com.midea.cloud.srm.supcooperate.order.mapper.OrderDetailMapper;
import com.midea.cloud.srm.supcooperate.order.mapper.OrderMapper;
import com.midea.cloud.srm.supcooperate.order.service.IOrderDetailService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 *  指定采购订单表 服务实现类
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
public class OrderAppointServiceImpl extends ServiceImpl<OrderAppointMapper, OrderAppoint> implements IOrderAppointService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private IDeliverPlanDetailService iDeliverPlanDetailService;
    @Autowired
    private BaseClient baseClient;


    @Override
    public PageInfo<OrderAppoint> orderAppointListPage(DeliverPlanDetail deliverPlanDetail) {
        PageUtil.startPage(deliverPlanDetail.getPageNum(), deliverPlanDetail.getPageSize());
        QueryWrapper<OrderAppoint> wrapper = new QueryWrapper<>();
        //详情ID
        wrapper.eq("DELIVER_PLAN_DETAIL_ID", deliverPlanDetail.getDeliverPlanDetailId());
        //到货计划ID
        wrapper.eq("DELIVER_PLAN_ID", deliverPlanDetail.getDeliverPlanId());
        if (deliverPlanDetail.getDeliverPlanDetailId() == null || deliverPlanDetail.getDeliverPlanId() == null) {
            wrapper.eq("1", "0");
        }
        return new PageInfo<OrderAppoint>(this.list(wrapper));
    }



    /**
     * 批量添加
     * @param orderAppointDTO
     */
    @Transactional
    @Override
    public void addBatch(OrderAppointDTO orderAppointDTO) {
        //获取需要添加的数据
        List<OrderDetailDTO> orderDetailDTOList = orderAppointDTO.getOrderDetailDTOList();
        //获取添加的数据的绑定信息
        DeliverPlanDetail deliverPlanDetail = orderAppointDTO.getDeliverPlanDetail();
        QueryWrapper<OrderAppoint> wrapper = new QueryWrapper<>();
        wrapper.eq("DELIVER_PLAN_DETAIL_ID",deliverPlanDetail.getDeliverPlanDetailId());
        List<OrderAppoint> OrderAppointList = this.list(wrapper);
        Map<Long, OrderAppoint> collect = OrderAppointList.stream().collect(Collectors.toMap(OrderAppoint::getOrderDetailId, Function.identity()));
        for (OrderDetailDTO orderDetailDTO:orderDetailDTOList){
            if (collect.get(orderDetailDTO.getOrderDetailId())!=null){
                continue;
            }
            OrderAppoint orderAppoint = new OrderAppoint();
            //添加主键id
            orderAppoint.setOrderAppointId(IdGenrator.generate());
            //添加到货计划详情行id
            orderAppoint.setDeliverPlanDetailId(deliverPlanDetail.getDeliverPlanDetailId());
            //添加到货计划行id
            orderAppoint.setDeliverPlanId(deliverPlanDetail.getDeliverPlanId());
            //添加采购订单id
            orderAppoint.setOrderId(orderDetailDTO.getOrderId());
            //添加采购订单明细id
            orderAppoint.setOrderDetailId(orderDetailDTO.getOrderDetailId());
            //采购订单号
            String orderNumber = orderDetailDTO.getOrderNumber();
            orderAppoint.setOrderNumber(orderNumber);
            //订单日期
            Date ceeaPurchaseOrderDate = orderDetailDTO.getCeeaPurchaseOrderDate();
            if (ceeaPurchaseOrderDate!=null){
                orderAppoint.setORDER_DATE(ceeaPurchaseOrderDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }
            //订单总数
            BigDecimal orderNum = orderDetailDTO.getOrderNum();
            orderAppoint.setOrderNum(orderNum);
            //接收总数量
            BigDecimal receiveSum = orderDetailDTO.getReceiveSum();
            orderAppoint.setReceiveSum(receiveSum);
            //剩余交货数量
            BigDecimal unreceivedSum = orderDetailDTO.getUnreceivedSum();
            orderAppoint.setUnreceivedSum(unreceivedSum);
            //业务实体
            orderAppoint.setOrgId(orderDetailDTO.getCeeaOrgId())
                    .setOrgCode(orderDetailDTO.getCeeaOrgCode())
                    .setOrgName(orderDetailDTO.getCeeaOrgName());
            //添加数据
            this.save(orderAppoint);
        }
    }


    public void TotalNum(Long deliverPlanId,Long deliverPlanDetailId){
        QueryWrapper<OrderAppoint> wrapper = new QueryWrapper<>();
        //到货计划详情ID
        wrapper.eq("DELIVER_PLAN_DETAIL_ID", deliverPlanDetailId);
        //到货计划ID
        wrapper.eq("DELIVER_PLAN_ID", deliverPlanId);
        List<OrderAppoint> list = this.list(wrapper);
        BigDecimal i = new BigDecimal(0);
        for (OrderAppoint orderAppoint:list){
            i=i.add(orderAppoint.getUnreceivedSum());
        }
        DeliverPlanDetail byId = iDeliverPlanDetailService.getById(deliverPlanDetailId);
        BigDecimal requirementQuantity = byId.getRequirementQuantity();
        if (i.compareTo(requirementQuantity)<0){
            byId.setOrderQuantityMatched(i);
        }else {
            byId.setOrderQuantityMatched(requirementQuantity);
        }
        iDeliverPlanDetailService.updateById(byId);
    }


    @Override
    public PageInfo<OrderDetailDTO> OrderDetailListpage(DeliverPlanDTO deliverPlan) {
        PageUtil.startPage(deliverPlan.getPageNum(), deliverPlan.getPageSize());
        QueryWrapper<OrderRequestDTO> wrapper = new QueryWrapper<>();
        //采购订单号查询
        wrapper.like(StringUtils.isNotEmpty(deliverPlan.getOrderNumber()),"o.ORDER_NUMBER",deliverPlan.getOrderNumber());
        //业务实体进行筛选
        wrapper.eq(deliverPlan.getOrgId()!=null,"o.CEEA_ORG_ID",deliverPlan.getOrgId());
        //库存组织
        wrapper.eq(deliverPlan.getOrganizationId()!=null,"od.CEEA_ORGANIZATION_ID",deliverPlan.getOrganizationId());
        //交货地点
        wrapper.eq(StringUtils.isNotEmpty(deliverPlan.getDeliveryAddress()),"o.CEEA_RECEIVE_ADDRESS",deliverPlan.getDeliveryAddress());
        //供应商
        wrapper.eq(deliverPlan.getVendorId()!=null,"o.VENDOR_ID",deliverPlan.getVendorId());
        //物料
        wrapper.eq(deliverPlan.getMaterialId()!=null,"od.MATERIAL_ID",deliverPlan.getMaterialId());
        //订单状态
        wrapper.eq("ORDER_STATUS", PurchaseOrderEnum.ACCEPT.getValue());
        //待验收数量
        wrapper.gt("(IFNULL(od.RECEIVED_QUANTITY,0) - IFNULL(od.RECEIVE_NUM,0))",0);
        wrapper.isNotNull("RECEIVED_QUANTITY");
        //接收数量
        wrapper.gt("od.RECEIVED_QUANTITY",0);
        wrapper.isNotNull("RECEIVED_QUANTITY");
        return new PageInfo<OrderDetailDTO>(orderDetailMapper.findListCopy(wrapper));
    }

    public List<OrderDetailDTO> OrderDetailList(DeliverPlan deliverPlan) {
        //获取所有允许超计划发货的小类id
        List<MaterialItem> materialItems = baseClient.listMaterialByParam(new MaterialItem().setMaterialId(deliverPlan.getMaterialId()));
        Assert.isTrue(CollectionUtils.isNotEmpty(materialItems)&&materialItems.get(0).getCategoryId()!=null,"找不到对应的物料信息或物料信息缺少品类信息");
        PurchaseCategory purchaseCategory = baseClient.MinByIfDeliverPlan(materialItems.get(0).getCategoryId());
        Assert.notNull(purchaseCategory,"此到货计划的物料不符合规则，请删除该到货到货计划。");
        QueryWrapper<OrderRequestDTO> wrapper = new QueryWrapper<>();
        //采购订单号查询
        //wrapper.like(StringUtils.isNotEmpty(deliverPlan.getOrderNumber()),"o.ORDER_NUMBER",deliverPlan.getOrderNumber());
        //业务实体进行筛选
        wrapper.eq(deliverPlan.getOrgId()!=null,"o.CEEA_ORG_ID",deliverPlan.getOrgId());
        //库存组织
        wrapper.eq(deliverPlan.getOrganizationId()!=null,"od.CEEA_ORGANIZATION_ID",deliverPlan.getOrganizationId());
        //交货地点
        wrapper.eq(StringUtils.isNotEmpty(deliverPlan.getDeliveryAddress()),"o.CEEA_RECEIVE_ADDRESS",deliverPlan.getDeliveryAddress());
        //供应商
        wrapper.eq(deliverPlan.getVendorId()!=null,"o.VENDOR_ID",deliverPlan.getVendorId());
        //物料
        wrapper.eq(deliverPlan.getMaterialId()!=null,"od.MATERIAL_ID",deliverPlan.getMaterialId());
        //订单状态
        wrapper.eq("ORDER_STATUS", PurchaseOrderEnum.ACCEPT.getValue());
        //未接受数量
        wrapper.gt(" (IFNULL(od.ORDER_NUM,0)-IFNULL(od.RECEIVE_SUM,0)-IFNULL(od.CEEA_ASSIGNED_QUANTITY,0))",0);
        return orderDetailMapper.findListCopy(wrapper);
    }

    /**
     * 批量删除
     * @param ids
     */
    @Transactional
    @Override
    public void deleteBatch(List<Long> ids) {
        //QueryWrapper<DeliverPlanDetail> wrapper = new QueryWrapper<>();
        //wrapper.in("ACCEPT_ORDER_ID",ids);
        //iDeliverPlanDetailService.remove(wrapper);
        this.removeByIds(ids);
    }
}
