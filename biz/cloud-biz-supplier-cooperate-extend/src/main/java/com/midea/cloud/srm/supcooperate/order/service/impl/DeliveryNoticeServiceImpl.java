package com.midea.cloud.srm.supcooperate.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.neworder.DeliveryNoticeStatus;
import com.midea.cloud.common.enums.order.NoteStatus;
import com.midea.cloud.common.enums.supcooperate.DeliveryNoteSource;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.DeliveryNoticeDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.DeliveryNoticeRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.DeliveryNotice;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.supcooperate.order.mapper.DeliveryNoticeMapper;
import com.midea.cloud.srm.supcooperate.order.service.IDeliveryNoticeService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 *  送货通知单表 服务实现类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/19 19:13
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class DeliveryNoticeServiceImpl extends ServiceImpl<DeliveryNoticeMapper, DeliveryNotice> implements IDeliveryNoticeService {
    @Autowired
    private IOrderDetailService iOrderDetailService;

    @Autowired
    private DeliveryNoticeMapper deliveryNoticeMapper;

    @Autowired
    private BaseClient baseClient;


    @Override
    public List<DeliveryNoticeDTO> findList(DeliveryNoticeRequestDTO requestDTO) {
//        List<DeliveryNoticeDTO> list = this.getBaseMapper().findList(requestDTO);
//        list.forEach(item->{
//            BigDecimal warehouseReceiptQuantity = this.getBaseMapper().getWarehouseReceiptQuantity(item.getDeliveryNoticeId());
//            item.setReceiveSum(warehouseReceiptQuantity);
//        });
        return list(requestDTO);
    }

    public List<DeliveryNoticeDTO> list(DeliveryNoticeRequestDTO requestDTO){
        List<DeliveryNoticeDTO> deliveryNoticeList = deliveryNoticeMapper.list(requestDTO);
        return deliveryNoticeList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void releasedBatch(List<DeliveryNotice> deliveryNotices) {
        //校验
        if(CollectionUtils.isEmpty(deliveryNotices)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请传递必传参数"));
        }
        List<Long> ids = deliveryNotices.stream().map(item -> item.getDeliveryNoticeId()).collect(Collectors.toList());
        List<DeliveryNotice> deliveryNoticeList = this.listByIds(ids);

        //修改送货通知
        for(DeliveryNotice deliveryNotice : deliveryNoticeList){
            deliveryNotice.setDeliveryNoticeStatus(DeliveryNoticeStatus.WAITING_VENDOR_CONFIRM.getValue());
        }
        this.updateBatchById(deliveryNoticeList);
    }

    @Override
    public List<DeliveryNoticeDTO> listCreateDeliveryNoteDetail(List<Long> deliveryNoticeIds) {
        List<DeliveryNoticeDTO> list = this.getBaseMapper().listCreateDeliveryNoteDetail(deliveryNoticeIds);
        Set<Long> organizationIdSet = new HashSet();
        Set<String> receivedFactorySet = new HashSet();
        list.forEach(item->{
            Assert.isTrue(StringUtils.equals(item.getDeliveryNoticeStatus(),NoteStatus.RELEASED.name()),"只能选择已发布送货通知单");
            organizationIdSet.add(item.getOrganizationId());
            receivedFactorySet.add(item.getReceivedFactory());
        });
        Assert.isTrue(organizationIdSet.size()==1,"不是同一个采购组织的订单");
        Assert.isTrue(receivedFactorySet.size()==1,"同一个送货单的收货工厂必须一致");
        return list;
    }

    @Override
    public BigDecimal getWarehouseReceiptQuantityByDeliveryNoticeId(Long deliveryNoticeId) {
        return this.getBaseMapper().getWarehouseReceiptQuantity(deliveryNoticeId);
    }

    /**
     * 批量添加送货通知
     * @param deliveryNoticeList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAdd(List<DeliveryNotice> deliveryNoticeList) {
        //校验
        if(CollectionUtils.isEmpty(deliveryNoticeList)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请传递必传参数"));
        }
        //校验数量是否足够
        checkIfQuantityEnough(deliveryNoticeList,TYPE.ADD.value);

        List<Long> orderDetailIds = deliveryNoticeList.stream().map(item -> item.getOrderDetailId()).collect(Collectors.toList());
        List<OrderDetail> orderDetailList = iOrderDetailService.listByIds(orderDetailIds);
        Map<Long, OrderDetail> orderDetailMap = orderDetailList.stream().collect(Collectors.toMap(item -> item.getOrderDetailId(), item -> item));
        List<OrderDetail> orderDetails = new ArrayList<>();
        for(DeliveryNotice deliveryNotice : deliveryNoticeList){
            OrderDetail orderDetail = orderDetailMap.get(deliveryNotice.getOrderDetailId());
            if(Objects.isNull(orderDetail)){
                throw new BaseException(LocaleHandler.getLocaleMsg(String.format("查询不到采购订单明细[orderDetailId=%s]",deliveryNotice.getOrderDetailId())));
            }
            if(Objects.isNull(orderDetail.getDeliveryNoticeQuantity())){
                orderDetail.setDeliveryNoticeQuantity(BigDecimal.ZERO);
            }
            if(Objects.isNull(orderDetail.getOrderNum())){
                orderDetail.setOrderNum(BigDecimal.ZERO);
            }
            if(orderDetail.getDeliveryNoticeQuantity().add(deliveryNotice.getNoticeSum())
                    .compareTo(orderDetail.getOrderNum()) > 0
            ) {
                throw new BaseException(LocaleHandler.getLocaleMsg("【本次填写的数量】大于【剩余可添加的数量】,请刷新页面重试"));
            }
            // 回写订单已下通知数量
            OrderDetail detail = new OrderDetail().
                    setOrderDetailId(orderDetail.getOrderDetailId()).
                    setDeliveryNoticeQuantity(orderDetail.getDeliveryNoticeQuantity().add(deliveryNotice.getNoticeSum()));
            orderDetails.add(detail);
        }
        //保存参数
        for(DeliveryNotice deliveryNotice : deliveryNoticeList){
            deliveryNotice.setDeliveryNoticeId(IdGenrator.generate());
            deliveryNotice.setDeliveryNoticeNum(baseClient.seqGen(SequenceCodeConstant.SEQ_SSC_DELIVERY_NOTICE_NUM));
            deliveryNotice.setDeliveryNoticeStatus(DeliveryNoticeStatus.DRAFT.getValue());
            if(Objects.isNull(deliveryNotice.getNoticeSum())){
                deliveryNotice.setNoticeSum(BigDecimal.ZERO);
            }
        }
        this.saveBatch(deliveryNoticeList);
        //回写采购订单明细
        if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(orderDetails)){
            iOrderDetailService.updateBatchById(orderDetails);
        }
    }

    /**
     * 批量更新送货通知
     * @param deliveryNoticeList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdate(List<DeliveryNotice> deliveryNoticeList) {
        //校验
        if(CollectionUtils.isEmpty(deliveryNoticeList)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请传递必传参数"));
        }
        //校验数量是否足够
        checkIfQuantityEnough(deliveryNoticeList,TYPE.UPDATE.value);

        //查询订单明细
        List<Long> orderDetailIds = deliveryNoticeList.stream().map(item -> item.getOrderDetailId()).collect(Collectors.toList());
        List<OrderDetail> orderDetailList = iOrderDetailService.listByIds(orderDetailIds);
        Map<Long, OrderDetail> orderDetailMap = orderDetailList.stream().collect(Collectors.toMap(item -> item.getOrderDetailId(), item -> item));
        //查询送货通知
        List<Long> deliveryNoticeIds = deliveryNoticeList.stream().map(item -> item.getDeliveryNoticeId()).collect(Collectors.toList());
        List<DeliveryNotice> deliveryNoticeList2 = this.listByIds(deliveryNoticeIds);
        Map<Long, DeliveryNotice> deliveryNoticeMap = deliveryNoticeList2.stream().collect(Collectors.toMap(item -> item.getDeliveryNoticeId(), item -> item));

        List<OrderDetail> orderDetails = new ArrayList<>();
        for(DeliveryNotice deliveryNotice : deliveryNoticeList){
            OrderDetail orderDetail = orderDetailMap.get(deliveryNotice.getOrderDetailId());
            if(Objects.isNull(orderDetail)){
                throw new BaseException(LocaleHandler.getLocaleMsg(String.format("查询不到采购订单明细[orderDetailId=%s]",deliveryNotice.getOrderDetailId())));
            }
            if(Objects.isNull(orderDetail.getDeliveryNoticeQuantity())){
                orderDetail.setDeliveryNoticeQuantity(BigDecimal.ZERO);
            }
            if(Objects.isNull(orderDetail.getOrderNum())){
                orderDetail.setOrderNum(BigDecimal.ZERO);
            }
            // 送货通知单
            DeliveryNotice oldDeliveryNotice = deliveryNoticeMap.get(deliveryNotice.getDeliveryNoticeId());
            BigDecimal quantity = deliveryNotice.getNoticeSum().subtract(oldDeliveryNotice.getNoticeSum());
            if(quantity.compareTo(BigDecimal.ZERO) > 0){
                if(orderDetail.getDeliveryNoticeQuantity().add(quantity)
                        .compareTo(orderDetail.getOrderNum()) > 0
                ) {
                    throw new BaseException(LocaleHandler.getLocaleMsg("【本次填写的数量】大于【剩余可添加的数量】,请刷新页面"));
                }
            }
            // 更新采购订单回写数据
            OrderDetail detail = new OrderDetail().setOrderDetailId(orderDetail.getOrderDetailId()).
                    setDeliveryNoticeQuantity(orderDetail.getDeliveryNoticeQuantity().add(quantity));
            orderDetails.add(detail);
        }

        //更新送货通知
        this.updateBatchById(deliveryNoticeList);

        //回写采购订单明细
        iOrderDetailService.updateBatchById(orderDetails);

    }

    /**
     * 批量删除送货通知单
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteDeliveryNotice(List<Long> ids) {
        //校验
        if(CollectionUtils.isEmpty(ids)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请传递必传参数"));
        }
        //获取送货通知
        List<DeliveryNotice> deliveryNoticeList = this.listByIds(ids);
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(deliveryNoticeList)) {
            List<OrderDetail> orderDetails = new ArrayList<>();
            //查询订单明细
            List<Long> orderDetailIds = deliveryNoticeList.stream().map(item -> item.getOrderDetailId()).collect(Collectors.toList());
            List<OrderDetail> orderDetailList = iOrderDetailService.listByIds(orderDetailIds);
            Map<Long, OrderDetail> orderDetailMap = orderDetailList.stream().collect(Collectors.toMap(OrderDetail::getOrderDetailId, Function.identity()));

            //校验
            for(DeliveryNotice deliveryNotice : deliveryNoticeList){
                if(!DeliveryNoticeStatus.DRAFT.getValue().equals(deliveryNotice.getDeliveryNoticeStatus())){
                    throw new BaseException(LocaleHandler.getLocaleMsg(String.format("[deliveryNoticeNum=%s]不为拟定状态,请检查",deliveryNotice.getDeliveryNoticeNum())));
                }
                OrderDetail orderDetail = orderDetailMap.get(deliveryNotice.getOrderDetailId());
                if (null != orderDetail) {
                    // 原通知累计数量
                    BigDecimal deliveryNoticeQuantity = null != orderDetail.getDeliveryNoticeQuantity() ? orderDetail.getDeliveryNoticeQuantity() : BigDecimal.ZERO;
                    // 扣减数量
                    BigDecimal noticeSum = null != deliveryNotice.getNoticeSum()?deliveryNotice.getNoticeSum():BigDecimal.ZERO;
                    OrderDetail detail = new OrderDetail().setOrderDetailId(orderDetail.getOrderDetailId()).
                            setDeliveryNoticeQuantity(deliveryNoticeQuantity.add(noticeSum));
                    orderDetails.add(detail);
                }
            }
            //批量删除送货通知
            this.removeByIds(ids);

            // 回写采购订单, 扣减通货通知累计数量
            if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(orderDetails)) {
                iOrderDetailService.updateBatchById(orderDetails);
            }
        }

    }

    /**
     * 供应商确认
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void supplierConfirm(List<Long> ids) {
        //校验
        if(CollectionUtils.isEmpty(ids)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请传递必填参数"));
        }
        List<DeliveryNotice> deliveryNoticeList = this.listByIds(ids);
        for(DeliveryNotice deliveryNotice : deliveryNoticeList){
            if(!DeliveryNoticeStatus.WAITING_VENDOR_CONFIRM.getValue().equals(deliveryNotice.getDeliveryNoticeStatus())){
                throw new BaseException(LocaleHandler.getLocaleMsg(String.format("[deliveryNoticeNum=%s]不为待供应商确认状态",deliveryNotice.getDeliveryNoticeNum())));
            }
        }
        //批量更新数据
        for(DeliveryNotice deliveryNotice : deliveryNoticeList){
            deliveryNotice.setDeliveryNoticeStatus(DeliveryNoticeStatus.ACCEPT.getValue());
        }
        this.updateBatchById(deliveryNoticeList);
    }

    /**
     * 供应商拒绝
     * @param deliveryNoticeRequestDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void supplierReject(DeliveryNoticeRequestDTO deliveryNoticeRequestDTO) {
        List<Long> ids = deliveryNoticeRequestDTO.getIds();
        String refusedReason = deliveryNoticeRequestDTO.getRefusedReason();
        //校验
        if(CollectionUtils.isEmpty(ids)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请传递必填参数"));
        }
        List<DeliveryNotice> deliveryNoticeList = this.listByIds(ids);
        for(DeliveryNotice deliveryNotice : deliveryNoticeList){
            if(!DeliveryNoticeStatus.WAITING_VENDOR_CONFIRM.getValue().equals(deliveryNotice.getDeliveryNoticeStatus())){
                throw new BaseException(LocaleHandler.getLocaleMsg(String.format("[deliveryNoticeNum=%s]不为待供应商确认状态",deliveryNotice.getDeliveryNoticeNum())));
            }
        }
        //批量更新数据
        for(DeliveryNotice deliveryNotice : deliveryNoticeList){
            deliveryNotice.setDeliveryNoticeStatus(DeliveryNoticeStatus.REJECT.getValue());
            deliveryNotice.setRefusedReason(refusedReason);
        }
        this.updateBatchById(deliveryNoticeList);

        //回写订单明细数量 todo
    }

    /**
     * 创建送货单-查询送货通知
     * @param orderRequestDTO
     * @return
     */
    @Override
    public PageInfo<OrderDetailDTO> listInDeliveryNote(OrderRequestDTO orderRequestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (ObjectUtils.isEmpty(loginAppUser)){
            return new PageInfo<>(Collections.EMPTY_LIST);
        }
        if (!org.apache.commons.lang3.StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                && !org.apache.commons.lang3.StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())) {
            Assert.isTrue(false, "用户类型不存在");
        }
        if (org.apache.commons.lang3.StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())) {
            orderRequestDTO.setVendorId(loginAppUser.getCompanyId());
        }
//        List<PurchaseCategory> purchaseCategorieList = baseClient.listMinByIfDeliverPlan();
//        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(purchaseCategorieList)){
//            List<Long> purchaseCategoryIds = purchaseCategorieList.stream().map(PurchaseCategory::getCategoryId).collect(Collectors.toList());
//            orderRequestDTO.setPurchaseCategoryIds(purchaseCategoryIds);
//        }
        PageUtil.startPage(orderRequestDTO.getPageNum(), orderRequestDTO.getPageSize());
        List<OrderDetailDTO> orderDetailDTOS = deliveryNoticeMapper.listInDeliveryNote(orderRequestDTO);
        if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(orderDetailDTOS)){
            orderDetailDTOS.forEach(orderDetailDTO -> orderDetailDTO.setOrderSource(DeliveryNoteSource.DELIVERY_NOTICE.name()));
        }
        return new PageInfo<OrderDetailDTO>(orderDetailDTOS);
    }

    public static enum TYPE {
        ADD(0),   //新增
        UPDATE(1),  //修改
        RELEASE(2); //释放

        private final Integer value;

        TYPE(Integer value) {
            this.value = value;
        }
    }


    /**
     * 校验数量是否足够
     * @param deliveryNoticeList
     * @param type ADD/UPDATE
     */
    private void checkIfQuantityEnough(List<DeliveryNotice> deliveryNoticeList, Integer type){
        if(CollectionUtils.isEmpty(deliveryNoticeList)){
            return;
        }

        //获取校验所需数据
        List<Long> orderDetailIdList = deliveryNoticeList.stream().map(item -> item.getDeliveryNoticeId()).collect(Collectors.toList());
        List<OrderDetail> orderDetailList = iOrderDetailService.listByIds(orderDetailIdList);

        List<Long> deliveryNoticeIdList = deliveryNoticeList.stream().map(item -> item.getDeliveryNoticeId()).collect(Collectors.toList());
        List<DeliveryNotice> oldDeliveryNoticeList = this.listByIds(deliveryNoticeIdList);
        Map<Long, DeliveryNotice> oldDeliveryNoticeMap = oldDeliveryNoticeList.stream().collect(Collectors.toMap(item -> item.getDeliveryNoticeId(), item -> item));

        Map<Long, List<DeliveryNotice>> deliveryNoticeMap = getDeliveryNoticeMap(deliveryNoticeList);

        //将null值转换为0.0
        for(OrderDetail orderDetail : orderDetailList){
            if(Objects.isNull(orderDetail.getDeliveryNoticeQuantity())){
                orderDetail.setDeliveryNoticeQuantity(BigDecimal.ZERO);
            }
        }
        for(DeliveryNotice deliveryNotice : deliveryNoticeList){
            if(Objects.isNull(deliveryNotice.getNoticeSum())){
                deliveryNotice.setNoticeSum(BigDecimal.ZERO);
            }
        }
        for(DeliveryNotice deliveryNotice : oldDeliveryNoticeList){
            if(Objects.isNull(deliveryNotice.getNoticeSum())){
                deliveryNotice.setNoticeSum(BigDecimal.ZERO);
            }
        }

        if(TYPE.ADD.value.equals(type)){
            //新增的情况
            for(OrderDetail orderDetail : orderDetailList){
                List<DeliveryNotice> list = deliveryNoticeMap.get(orderDetail.getOrderDetailId());
                //本次创建数量
                BigDecimal quantity1 = list.stream().map(item -> item.getNoticeSum()).reduce(BigDecimal::add).get();
                //剩余可创建数量
                BigDecimal quantity2 = orderDetail.getOrderNum().subtract(orderDetail.getDeliveryNoticeQuantity());

                if(quantity1.compareTo(quantity2) > 0){
                    throw new BaseException(LocaleHandler.getLocaleMsg(String.format("订单明细[id=%s]数量不足,请检查",orderDetail.getOrderDetailId())));
                }
            }

        }else{
            //修改的情况
            for(OrderDetail orderDetail : orderDetailList){
                List<DeliveryNotice> list = deliveryNoticeMap.get(orderDetail.getOrderDetailId());
                //quantity1 本次创建数量
                BigDecimal quantity1 = list.stream().map(item -> item.getNoticeSum()).reduce(BigDecimal::add).get();
                //quantity2 数据库持有数量
                BigDecimal quantity2 = BigDecimal.ZERO;
                for(DeliveryNotice deliveryNotice : list) {
                    quantity2 = quantity2.add(oldDeliveryNoticeMap.get(deliveryNotice.getDeliveryNoticeId()).getNoticeSum());
                }
                BigDecimal quantity3 = quantity1.subtract(quantity2);
                //剩余可创建数量
                BigDecimal quantity4 = orderDetail.getOrderNum().subtract(orderDetail.getDeliveryNoticeQuantity());
                if(quantity3.compareTo(quantity4) > 0){
                    log.info("");
                    throw new BaseException(LocaleHandler.getLocaleMsg(String.format("订单明细[id=%s]数量不足,请检查",orderDetail.getOrderDetailId())));
                }
            }

        }

    }

    private Map<Long, List<DeliveryNotice>> getDeliveryNoticeMap(List<DeliveryNotice> deliveryNoticeList){
        Map<Long, List<DeliveryNotice>> deliveryNoticeMap = new HashMap<>();
        for(DeliveryNotice deliveryNotice : deliveryNoticeList){
            if(CollectionUtils.isEmpty(deliveryNoticeMap.get(deliveryNotice.getOrderDetailId()))){
                deliveryNoticeMap.put(deliveryNotice.getOrderDetailId(),new LinkedList<DeliveryNotice>(){{
                    add(deliveryNotice);
                }});
            }else{
                deliveryNoticeMap.get(deliveryNotice.getOrderDetailId()).add(deliveryNotice);
            }
        }
        return deliveryNoticeMap;
    }

    /**
     * 更新订单明细 送货通知数量
     * @param deliveryNoticeList
     * @param type 新增/修改/释放
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderDetailQuantity(List<DeliveryNotice> deliveryNoticeList, Integer type){
        //获取所需数据
        Map<Long, List<DeliveryNotice>> deliveryNoticeMap = getDeliveryNoticeMap(deliveryNoticeList);

        List<Long> orderDetailIdList = deliveryNoticeList.stream().map(item -> item.getOrderDetailId()).collect(Collectors.toList());
        List<OrderDetail> orderDetailList = iOrderDetailService.listByIds(orderDetailIdList);

        List<Long> deliveryNoticeIdList = deliveryNoticeList.stream().map(item -> item.getDeliveryNoticeId()).collect(Collectors.toList());
        List<DeliveryNotice> oldDeliveryNoticeList = this.listByIds(deliveryNoticeIdList);
        Map<Long, DeliveryNotice> oldDeliveryNoticeMap = oldDeliveryNoticeList.stream().collect(Collectors.toMap(item -> item.getDeliveryNoticeId(), item -> item));

        //将null值转换为0.0
        for(OrderDetail orderDetail : orderDetailList){
            if(Objects.isNull(orderDetail.getDeliveryNoticeQuantity())){
                orderDetail.setDeliveryNoticeQuantity(BigDecimal.ZERO);
            }
        }
        for(DeliveryNotice deliveryNotice : deliveryNoticeList){
            if(Objects.isNull(deliveryNotice.getNoticeSum())){
                deliveryNotice.setNoticeSum(BigDecimal.ZERO);
            }
        }
        for(DeliveryNotice deliveryNotice : oldDeliveryNoticeList){
            if(Objects.isNull(deliveryNotice.getNoticeSum())){
                deliveryNotice.setNoticeSum(BigDecimal.ZERO);
            }
        }

        if(TYPE.ADD.value.equals(type)){
            //新增
            updateOrderDetailQuantityAdd(deliveryNoticeMap,oldDeliveryNoticeMap,orderDetailList);
        }else if(TYPE.UPDATE.value.equals(type)){
            //修改
            updateOrderDetailQuantityUpdate(deliveryNoticeMap,oldDeliveryNoticeMap,orderDetailList);
        }else if(TYPE.RELEASE.value.equals(type)){
            //释放
            updateOrderDetailQuantityRelease(deliveryNoticeMap,oldDeliveryNoticeMap,orderDetailList);
        }

    }

    /**
     * 新增模式下扣减订单明细-送货通知数量
     * @param deliveryNoticeMap
     * @param oldDeliveryNoticeMap
     * @param orderDetailList
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderDetailQuantityAdd(Map<Long, List<DeliveryNotice>> deliveryNoticeMap,Map<Long, DeliveryNotice> oldDeliveryNoticeMap,List<OrderDetail> orderDetailList){

    }

    /**
     * 更新模式下扣减订单明细-送货通知数量
     * @param deliveryNoticeMap
     * @param oldDeliveryNoticeMap
     * @param orderDetailList
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderDetailQuantityUpdate(Map<Long, List<DeliveryNotice>> deliveryNoticeMap,Map<Long, DeliveryNotice> oldDeliveryNoticeMap,List<OrderDetail> orderDetailList){

    }

    /**
     * 释放模式下扣减订单明细-送货通知数量
     * @param deliveryNoticeMap
     * @param oldDeliveryNoticeMap
     * @param orderDetailList
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderDetailQuantityRelease(Map<Long, List<DeliveryNotice>> deliveryNoticeMap,Map<Long, DeliveryNotice> oldDeliveryNoticeMap,List<OrderDetail> orderDetailList){

    }
}
