package com.midea.cloud.srm.supcooperate.deliver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.supcooperate.DeliverPlanLineStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.BigDecimalUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.DeliverPlanDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.DeliverPlanDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.OrderAppointDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlan;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlanDetail;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.OrderAppoint;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.OrderDeliveryDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.supcooperate.deliver.mapper.DeliverPlanDetailMapper;
import com.midea.cloud.srm.supcooperate.deliver.service.IDeliverPlanDetailService;
import com.midea.cloud.srm.supcooperate.deliver.service.IDeliverPlanService;
import com.midea.cloud.srm.supcooperate.deliver.service.IOrderAppointService;
import com.midea.cloud.srm.supcooperate.deliver.service.IOrderDeliveryDetailService;
import com.midea.cloud.srm.supcooperate.job.DeliverPlanJob;
import com.midea.cloud.srm.supcooperate.order.mapper.OrderDetailMapper;
import com.midea.cloud.srm.supcooperate.order.service.IOrderDetailService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 *  到货计划详情表 服务实现类
 * </pre>
 *
 * @author zhi1772778785@163.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-27 14:42:31
 *  修改内容:
 * </pre>
 */
@Slf4j
@Service
public class DeliverPlanDetailServiceImpl extends ServiceImpl<DeliverPlanDetailMapper, DeliverPlanDetail> implements IDeliverPlanDetailService {
    private BigDecimal suitedSurplus;
    private List<Long> ids;
    private List<BigDecimal> bigDecimals;
    private List<OrderDetailDTO> orderDetailDTOList;

    @Autowired
    private IDeliverPlanService iDeliverPlanService;
    @Autowired
    private IOrderAppointService iOrderAppointService;
    @Resource
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private IOrderDetailService iOrderDetailService;
    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private IOrderDeliveryDetailService iOrderDeliveryDetailService;
    @Autowired
    private BaseClient baseClient;


    /**
     * 锁定到货详情行
     *
     * @param ids
     */
    @Transactional
    @Override
    public void getDeliverPlanLock(List<Long> ids) {
        QueryWrapper<DeliverPlanDetail> wrapper = new QueryWrapper<>();
        wrapper.in("DELIVER_PLAN_DETAIL_ID", ids);
        List<DeliverPlanDetail> DeliverPlanDetailList = this.list(wrapper);
        DeliverPlan deliverPlan = iDeliverPlanService.getById(DeliverPlanDetailList.get(0).getDeliverPlanId());
        for (DeliverPlanDetail deliverPlanDetail : DeliverPlanDetailList) {
            Assert.isTrue(DeliverPlanLineStatus.COMFIRM.toString()
                    .equals(deliverPlanDetail.getDeliverPlanStatus()), "请勾选行状态为已确认的进行锁定，或保存后再锁定。");
            deliverPlanDetail.setDeliverPlanLock("1");
            this.updateById(deliverPlanDetail);
            //匹配订单--》组装数据
            ArrayList<DeliverPlanDetail> deliverPlanDetailCopy = new ArrayList<>();
            deliverPlanDetailCopy.add(deliverPlanDetail);
            DeliverPlanDTO deliverPlanDTO = new DeliverPlanDTO().setDeliverPlan(deliverPlan);
            deliverPlanDTO.setDeliverPlanDetailList(deliverPlanDetailCopy);
            //匹配订单--》匹配开始
            this.MatchingOrder(deliverPlanDTO);
        }


    }

    /**
     * 确认行状态
     *
     * @param ids
     */
    @Transactional
    @Override
    public void getDeliverPlanStatus(List<Long> ids) {
        QueryWrapper<DeliverPlanDetail> wrapper = new QueryWrapper<>();
        wrapper.in("DELIVER_PLAN_DETAIL_ID", ids);
        List<DeliverPlanDetail> DeliverPlanDetailList = this.list(wrapper);
        DeliverPlan deliverPlan = iDeliverPlanService.getById(DeliverPlanDetailList.get(0).getDeliverPlanId());
        //获取所有允许超计划发货的小类id
        PurchaseCategory purchaseCategory = baseClient.MinByIfBeyondDeliver(deliverPlan.getCategoryId());
        for (DeliverPlanDetail deliverPlanDetail : DeliverPlanDetailList) {
            int i = deliverPlanDetail.getRequirementQuantity().compareTo(deliverPlanDetail.getQuantityPromised());
            //如果物料的品类是允许超计划发货类则不进行判断
            if (purchaseCategory == null) {
                Assert.isTrue(i >= 0, "非允许超计划发货物料承诺供应数量必须小于或等于需求数量，请修改后保存再确认。");
            }
            deliverPlanDetail.setDeliverPlanStatus(DeliverPlanLineStatus.COMFIRM.toString());
            this.updateById(deliverPlanDetail);
        }
        iDeliverPlanService.getAffirmByMrp(true);
    }

    /**
     * 查看指定采购订单详情
     *
     * @return
     */
    @Override
    public DeliverPlanDetailDTO getDeliverPlanDetail(DeliverPlanDetail deliverPlanDetail) {
        Long deliverPlanId = deliverPlanDetail.getDeliverPlanId();
        Long deliverPlanDetailId = deliverPlanDetail.getDeliverPlanDetailId();
        Assert.notNull(deliverPlanId, "到货计划单不存在");
        Assert.notNull(deliverPlanDetailId, "对应的到货计划单详情行不存在");
        //获取到货计划单
        DeliverPlanDetailDTO deliverPlanDetailDTO = new DeliverPlanDetailDTO();
        deliverPlanDetailDTO.setDeliverPlanDetailId(deliverPlanDetailId);
        deliverPlanDetailDTO.setDeliverPlan(iDeliverPlanService.getById(deliverPlanId));
        QueryWrapper<OrderAppoint> wrapper = new QueryWrapper<>();
        wrapper.eq("DELIVER_PLAN_DETAIL_ID", deliverPlanDetail.getDeliverPlanDetailId());
        wrapper.eq("DELIVER_PLAN_ID", deliverPlanDetail.getDeliverPlanId());
        List<OrderAppoint> list = iOrderAppointService.list(wrapper);
        deliverPlanDetailDTO.setOrderAppointList(list);
        DeliverPlanDetail byId = this.getById(deliverPlanDetailId);
        deliverPlanDetailDTO.setRequirementQuantity(byId.getRequirementQuantity());
        deliverPlanDetailDTO.setSchMonthlyDate(byId.getSchMonthlyDate());
        return deliverPlanDetailDTO;
    }

    /**
     * 订单匹配
     * --->2020/10/11:修改成承诺供应数量匹配
     * --->2020/12/1:修改成需求数量去匹配
     * --->2020/12/1:修改成承诺数量去匹配
     *
     * @param deliverPlanDTO
     */
    @Transactional
    @Override
    public void MatchingOrder(DeliverPlanDTO deliverPlanDTO) {
        this.ids = new ArrayList<>();
        this.orderDetailDTOList = new ArrayList<>();
        this.bigDecimals = new ArrayList<>();
        String str = "";
        DeliverPlanDetail deliverPlanDetail = deliverPlanDTO.getDeliverPlanDetailList().get(0);
        //获取需求数量
        //this.suitedSurplus = deliverPlanDetail.getRequirementQuantity();
        //获取承诺供应商数量
        this.suitedSurplus = deliverPlanDetail.getQuantityPromised();
        //获取对应id的所有指定采购明细列表
        QueryWrapper<OrderAppoint> wrapper = new QueryWrapper<>();
        //到货计划详情ID
        wrapper.eq("DELIVER_PLAN_DETAIL_ID", deliverPlanDetail.getDeliverPlanDetailId());
        //到货计划ID
        wrapper.eq("DELIVER_PLAN_ID", deliverPlanDetail.getDeliverPlanId());
        wrapper.orderByAsc("ORDER_DATE", "CREATION_DATE");
        List<OrderAppoint> orderAppointList = iOrderAppointService.list(wrapper);
        DeliverPlan deliverPlan = deliverPlanDTO.getDeliverPlan();
        if (this.suitedSurplus.compareTo(new BigDecimal(0)) != 0
                || this.suitedSurplus.compareTo(new BigDecimal(0E-8)) != 0) {
            if (CollectionUtils.isNotEmpty(orderAppointList)) {
                str = this.isGetMatchingOrder(deliverPlan, orderAppointList);
            } else {
                str = this.notGetMatchingOrder(deliverPlan);
            }
        } else {
            str = "匹配成功";
        }
        Assert.isTrue(!StringUtils.isEmpty(str), "依到货计划的需求数量，符合条件的采购订单无法满足当前匹配数量，请确认后重试1。");
        //设置匹配数量
        deliverPlanDetail.setOrderQuantityMatched(deliverPlanDetail.getQuantityPromised());
        this.updateById(deliverPlanDetail);
        List<DeliverPlanDetail> deliverPlanDetailList = this.deliverPList(deliverPlanDetail);
        int size = 0;
        if (!CollectionUtils.isEmpty(deliverPlanDetailList)) {
            size = deliverPlanDetailList.size();
        }
        BigDecimal matchDegree = deliverPlan.getMatchDegree();
        if (matchDegree == null) {
            matchDegree = new BigDecimal(0);
        }
        matchDegree = matchDegree.multiply(new BigDecimal(size + 0));
        deliverPlan.setMatchDegree(matchDegree.divide(new BigDecimal(size + 1), RoundingMode.HALF_UP));
        iDeliverPlanService.updateById(deliverPlan);
        boolean falg = this.getUpdateDeliverPlan(orderAppointList, deliverPlanDetail, deliverPlanDTO.getDeliverPlan());
        Assert.isTrue(falg, "入库失败，请重试。");

    }

    /**
     * 指定匹配原则
     *
     * @param deliverPlan      到货计划详情
     * @param orderAppointList 存在的指定采购订单
     */
    public String isGetMatchingOrder(DeliverPlan deliverPlan, List<OrderAppoint> orderAppointList) {
        for (OrderAppoint orderAppoint : orderAppointList) {
            OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
            //获取采购订单明细id
            Long orderDetailId = orderAppoint.getOrderDetailId();
            if (orderDetailId == null) {
                continue;
            }
            orderRequestDTO.setOrderDetailId(orderDetailId);
            List<OrderDetailDTO> list = orderDetailMapper.findList(orderRequestDTO);
            //如果此物料已给其他计划匹配了则跳过本次循环
            if (CollectionUtils.isEmpty(list)) {
                continue;
            }
            this.orderDetailDTOList.addAll(list);
            OrderDetailDTO orderDetailDTO = list.get(0);
            boolean dispose = this.getDispose(orderDetailDTO);
            this.ids.add(orderDetailDTO.getOrderDetailId());
            if (dispose) {
                return "匹配成功";
            }
        }
        //如果指定的采购订单无法满足匹配数量，则继续执行“无指定匹配原则”
        return this.notGetMatchingOrder(deliverPlan);
    }


    /**
     * 无指定匹配原则
     *
     * @param deliverPlan 到货计划详情
     */
    public String notGetMatchingOrder(DeliverPlan deliverPlan) {
        //获取符合条件的采购订单
        List<OrderDetailDTO> list = iOrderAppointService.OrderDetailList(deliverPlan);
        Assert.notEmpty(list, "依到货计划的需求数量，符合条件的采购订单无法满足当前匹配数量，请确认后重试2。");
        this.orderDetailDTOList.addAll(list);
        String str = "";
        //开始消耗匹配数量
        for (OrderDetailDTO orderDetailDTO : list) {
            try {
                boolean dispose = this.getDispose(orderDetailDTO);
                this.ids.add(orderDetailDTO.getOrderDetailId());
                if (dispose) {
                    str = "匹配成功";
                    break;
                }
            } catch (Exception e) {
                log.error("操作失败", e);
            }

        }
        return str;
    }

    /**
     * 修改操作
     *
     * @param orderDetailDTO
     * @param
     * @return
     */
    @Transactional
    public boolean getDispose(OrderDetailDTO orderDetailDTO) {
        //剩余数量
        BigDecimal unreceivedSum = orderDetailDTO.getUnreceivedSum();
        //获取已匹配计划数量
        BigDecimal ceeaAssignedQuantity = orderDetailDTO.getCeeaAssignedQuantity() == null ? new BigDecimal(0) : orderDetailDTO.getCeeaAssignedQuantity();
        //获取订单可支配数量
        BigDecimal subtract = unreceivedSum.subtract(ceeaAssignedQuantity);
        //大于0继续，小于零结束
        BigDecimal subtract1 = this.suitedSurplus.subtract(subtract);
        boolean falg = subtract1.compareTo(new BigDecimal(0)) > 0;
        if (falg) {
            this.bigDecimals.add(subtract);
            this.suitedSurplus = suitedSurplus.subtract(subtract);
            orderDetailDTO.setCeeaAssignedQuantity(unreceivedSum);
        } else {
            this.bigDecimals.add(this.suitedSurplus);
            orderDetailDTO.setCeeaAssignedQuantity(ceeaAssignedQuantity.add(this.suitedSurplus));
        }
        iOrderDetailService.updateById(orderDetailDTO);
        List<OrderDetailDTO> orderDetailDTOS = orderDetailMapper.falgList(orderDetailDTO);
        //当已分配计划数量大于数量，说明已经给操作，立刻回滚
        if (CollectionUtils.isEmpty(orderDetailDTOS)) {
            this.bigDecimals.remove(bigDecimals.size() - 1);
            throw new BaseException("已分配计划数量大于数量");
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动回滚
        }
        return !falg;
    }

    /**
     * 匹配完后修改指定采购订单的数据，添加交货明细
     *
     * @return
     */
    @Transactional
    public boolean getUpdateDeliverPlan(List<OrderAppoint> orderAppointList, DeliverPlanDetail deliverPlanDetail, DeliverPlan deliverPlan) {
        //如果存在旧信息先删除
        if (!CollectionUtils.isEmpty(orderAppointList)) {
            ArrayList<Long> orderAppointId = new ArrayList<>();
            for (OrderAppoint orderAppoint : orderAppointList) {
                orderAppointId.add(orderAppoint.getOrderAppointId());
            }
            iOrderAppointService.deleteBatch(orderAppointId);
        }
        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
        try {
            for (int i = 0; i < this.ids.size(); i++) {
                for (OrderDetailDTO orderDetailDTO : this.orderDetailDTOList) {
                    if (orderDetailDTO.getOrderDetailId() == ids.get(i)) {
                        //修改采购订单的到货计划号
                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.setOrderDetailId(orderDetailDTO.getOrderDetailId());
                        orderDetail.setCeeaPlanReceiveNum(deliverPlan.getDeliverPlanNum());
                        if (deliverPlanDetail.getSchMonthlyDate() != null) {
                            orderDetail.setCeeaPromiseReceiveDate(Date.from(deliverPlanDetail.getSchMonthlyDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                        }
                        iOrderDetailService.updateById(orderDetail);
                        orderDetailDTOList.add(orderDetailDTO);
                        Order orderById = iOrderService.getById(orderDetailDTO.getOrderId());
                        OrderDeliveryDetail orderDeliveryDetail = new OrderDeliveryDetail();
                        //采购订单号
                        String orderNumber = orderDetailDTO.getOrderNumber();
                        orderDeliveryDetail.setOrderNumber(orderNumber);
                        //添加到货计划详情行id
                        orderDeliveryDetail.setDeliverPlanDetailId(deliverPlanDetail.getDeliverPlanDetailId());
                        //添加到货计划行id
                        orderDeliveryDetail.setDeliverPlanId(deliverPlanDetail.getDeliverPlanId());
                        //添加采购订单id
                        orderDeliveryDetail.setOrderId(orderDetailDTO.getOrderId());
                        //添加采购订单明细id
                        orderDeliveryDetail.setOrderDetailId(orderDetailDTO.getOrderDetailId());
                        //订单日期
                        Date ceeaPurchaseOrderDate = orderDetailDTO.getCeeaPurchaseOrderDate();
                        if (ceeaPurchaseOrderDate != null) {
                            orderDeliveryDetail.setOrderDate(ceeaPurchaseOrderDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        }
                        //订单总数
                        BigDecimal orderNum = orderDetailDTO.getOrderNum();
                        orderDeliveryDetail.setOrderQuantity(orderNum);
                        //业务实体
                        Long ceeaOrgId = orderById.getCeeaOrgId();
                        orderDeliveryDetail.setOrgId(ceeaOrgId);
                        orderDeliveryDetail.setOrgName(orderById.getCeeaOrgName());
                        //供应商名称
                        orderDeliveryDetail.setVendorId(orderById.getVendorId());
                        orderDeliveryDetail.setVendorCode(orderById.getVendorCode());
                        //采购员
                        orderDeliveryDetail.setBuyerName(orderById.getBuyerName());
                        //物料小类
                        orderDeliveryDetail.setCategoryId(orderDetailDTO.getCategoryId());
                        orderDeliveryDetail.setCategoryName(orderDetailDTO.getCategoryName());
                        //物料编码
                        orderDeliveryDetail.setCategoryName(orderDetailDTO.getMaterialName());
                        orderDeliveryDetail.setCategoryCode(orderDetailDTO.getMaterialCode());
                        //计划交货数量
                        orderDeliveryDetail.setPlanReceiveNum(bigDecimals.get(i));
                        //计划交货日期
                        orderDeliveryDetail.setPlanReceiveDate(deliverPlanDetail.getSchMonthlyDate());
                        //到货计划号
                        orderDeliveryDetail.setDeliverPlanNum(deliverPlan.getDeliverPlanNum());
                        Long id = IdGenrator.generate();
                        orderDeliveryDetail.setOrderDeliveryDetailId(id);
                        iOrderDeliveryDetailService.save(orderDeliveryDetail);
                        continue;
                    }
                }
            }
            OrderAppointDTO orderAppointDTO = new OrderAppointDTO();
            orderAppointDTO.setOrderDetailDTOList(orderDetailDTOList);
            orderAppointDTO.setDeliverPlanDetail(deliverPlanDetail);
            iOrderAppointService.addBatch(orderAppointDTO);
            return true;
        } catch (Exception e) {
            log.info("操作失败", e);
            return false;
        }
    }

    public List<DeliverPlanDetail> deliverPList(DeliverPlanDetail deliverPlanDetail) {
        Assert.notNull(deliverPlanDetail.getDeliverPlanId(), "到货计划单不存在");
        QueryWrapper<DeliverPlanDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("DELIVER_PLAN_ID", deliverPlanDetail.getDeliverPlanId());
        wrapper.gt("ORDER_QUANTITY_MATCHED", 0);
        wrapper.ne("DELIVER_PLAN_DETAIL_ID", deliverPlanDetail.getDeliverPlanDetailId());
        return this.list(wrapper);
    }

    /**
     * 订单交货明细废弃
     */
    @Transactional
    public void getAuditNote(Long id) {
        //获取到货计划详情行
        DeliverPlanDetail byId = this.getById(id);
        Assert.notNull(byId, "找不到对应的到货计划行");
        //获取到货计划明细原匹配数量
        BigDecimal orderQuantityMatched = BigDecimalUtil.isEmpty(byId.getOrderQuantityMatched());
        //只有已锁定已匹配的明细行才可以废弃
        Assert.isTrue("2".equals(byId.getDeliverPlanLock()), "只有已锁定已匹配的明细行才可以废弃。");
        if (new BigDecimal(0).compareTo(orderQuantityMatched) == 0) {
            return;
        }
        //开始废弃
        log.info("===========================================开始废弃到货计划明细===================================================================");
        //获取所有正常的到货计划明细行
        OrderDeliveryDetail orderDeliveryDetail = new OrderDeliveryDetail();
        orderDeliveryDetail.setOrderDetailId(id).setOrderDeliveryDetailState("Y");
        List<OrderDeliveryDetail> orderDeliveryDetailList = iOrderDeliveryDetailService.orderDeliveryDetailList(orderDeliveryDetail);

        List<Long> collect = orderDeliveryDetailList.stream().map(OrderDeliveryDetail::getOrderDetailId).collect(Collectors.toList());
        QueryWrapper<OrderDetail> orderDetailQueryWrapper = new QueryWrapper<>();
        orderDetailQueryWrapper.in("ORDER_DETAIL_ID", collect);
        List<OrderDetail> list = iOrderDetailService.list(orderDetailQueryWrapper);
        Map<Long, OrderDetail> orderDetailMap = list.stream().collect(Collectors.toMap(OrderDetail::getOrderDetailId, Function.identity()));
        //获取可以回写的匹配数量
        BigDecimal deliveryQuantity = getDeliveryQuantity(orderDeliveryDetailList, orderDetailMap);
        //回写到货计划详情
        byId.setDeliverPlanLock("3").setOrderQuantityMatched(orderQuantityMatched.subtract(deliveryQuantity));
        this.updateById(byId);
        //回写采购订单
        iOrderDetailService.updateBatchById(list);
        //回写到货计划明细行
        iOrderDeliveryDetailService.updateBatchById(orderDeliveryDetailList);
    }

    /**
     * 获取可以回写的匹配数量
     *
     * @param orderDeliveryDetailList
     * @return
     */
    public BigDecimal getDeliveryQuantity(List<OrderDeliveryDetail> orderDeliveryDetailList, Map<Long, OrderDetail> orderDetailMap) {
        //获取所有到货计划明细行能回写的数量
        BigDecimal bigDecimal = new BigDecimal(0);
        if (CollectionUtils.isNotEmpty(orderDeliveryDetailList)) {
            for (OrderDeliveryDetail orderDeliveryDetail : orderDeliveryDetailList) {
                BigDecimal deliveryQuantity = iOrderDeliveryDetailService.getDeliveryQuantity(orderDeliveryDetail);
                bigDecimal.add(deliveryQuantity);
                //设置状态为失效
                orderDeliveryDetail.setOrderDeliveryDetailState("N");
                Long orderDetailId = orderDeliveryDetail.getOrderDetailId();
                //获取对应的采购订单明细行
                OrderDetail orderDetail = orderDetailMap.get(orderDetailId);
                BigDecimal ceeaAssignedQuantity = BigDecimalUtil.isEmpty(orderDetail.getCeeaAssignedQuantity());
                //填入修改后的采购订单行信息
                BigDecimal subtract = ceeaAssignedQuantity.subtract(deliveryQuantity);
                Assert.isTrue(subtract.compareTo(new BigDecimal(0)) >= 0, "采购订单已分配数量有误，请联系管理员");
            }

        }
        Assert.isTrue(bigDecimal.compareTo(new BigDecimal(0)) > 0, "可废弃的数量为0。");
        //使用已分配数量-已送货货数量=可回写操作数量
        return bigDecimal;
    }

}
