package com.midea.cloud.srm.supcooperate.job;

import com.midea.cloud.common.enums.supcooperate.DeliverPlanLineStatus;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.quartz.bind.Job;
import com.midea.cloud.quartz.handler.ExecuteableJob;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.DeliverPlanDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.DeliverPlanVO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlan;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlanDetail;
import com.midea.cloud.srm.supcooperate.deliver.service.IDeliverPlanDetailService;
import com.midea.cloud.srm.supcooperate.deliver.service.IDeliverPlanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 到货计划定时任务
 */
@Job("DeliverPlanJob")
@Slf4j
public class DeliverPlanJob implements ExecuteableJob {
    @Autowired
    BaseClient baseClient;
    @Autowired
    IDeliverPlanService iDeliverPlanService;
    @Autowired
    IDeliverPlanDetailService iDeliverPlanDetailService;


    @Override
    public BaseResult executeJob(Map<String, String> params) {
        //获取所有执行到货计划，且周期大于0的品类

        List<PurchaseCategory> purchaseCategories = baseClient.listMinByDPByLockPeriod();
        //如果找不到对应的品类，直接跳过执行
        if (CollectionUtils.isEmpty(purchaseCategories)) {
            return null;
        }

        Map<Long, Integer> purchaseCategorieMap = purchaseCategories.stream().collect(Collectors.toMap(PurchaseCategory::getCategoryId, PurchaseCategory::getCeeaLockPeriod));
        List<DeliverPlanVO> deliverPlanVOS = new ArrayList<>();
        for (Long categoryId : purchaseCategorieMap.keySet()) {
            deliverPlanVOS.add(new DeliverPlanVO().setId(Long.valueOf(categoryId)).setDates(DateUtil.localDateToStr(DateUtil.dateToLocalDate(new Date()).plusDays(purchaseCategorieMap.get(categoryId)))));
        }
        //获取对应品类，及大于今天的所有到货计划明细
        List<DeliverPlanDetail> deliverPlanList = iDeliverPlanService.getDeliverPlanList(new DeliverPlanDTO().setStringList(deliverPlanVOS));
        if (CollectionUtils.isEmpty(deliverPlanList)) {
            return null;
        }
        Map<Long, List<DeliverPlanDetail>> deliverPlanMap = deliverPlanList.stream().collect(Collectors.groupingBy(hero -> hero.getDeliverPlanId()));
        //锁定分配订单
        for (Long id : deliverPlanMap.keySet()) {
            List<DeliverPlanDetail> deliverPlanDetails = deliverPlanMap.get(id);
            //如果没有订单则直接跳过
            if (CollectionUtils.isNotEmpty(deliverPlanDetails)) {
                //开始匹配订单
                this.getDeliverPlanLock(deliverPlanDetails, id);
            }
        }
        StringBuffer sb = new StringBuffer();
        return BaseResult.build(ResultCode.SUCCESS, sb.toString());
    }


    public void test1() {
        //获取所有执行到货计划，且周期大于0的品类

        List<PurchaseCategory> purchaseCategories = baseClient.listMinByDPByLockPeriod();
        //如果找不到对应的品类，直接跳过执行
        if (CollectionUtils.isEmpty(purchaseCategories)) {
            return;
        }

        Map<Long, Integer> purchaseCategorieMap = purchaseCategories.stream().collect(Collectors.toMap(PurchaseCategory::getCategoryId, PurchaseCategory::getCeeaLockPeriod));
        List<DeliverPlanVO> deliverPlanVOS = new ArrayList<>();
        for (Long categoryId : purchaseCategorieMap.keySet()) {
            deliverPlanVOS.add(new DeliverPlanVO().setId(Long.valueOf(categoryId)).setDates(DateUtil.localDateToStr(DateUtil.dateToLocalDate(new Date()).plusDays(purchaseCategorieMap.get(categoryId)))));
        }
        //获取对应品类，及大于今天的所有到货计划明细
        List<DeliverPlanDetail> deliverPlanList = iDeliverPlanService.getDeliverPlanList(new DeliverPlanDTO().setStringList(deliverPlanVOS));
        if (CollectionUtils.isEmpty(deliverPlanList)) {
            return;
        }
        Map<Long, List<DeliverPlanDetail>> deliverPlanMap = deliverPlanList.stream().collect(Collectors.groupingBy(hero -> hero.getDeliverPlanId()));
        //锁定分配订单
        for (Long id : deliverPlanMap.keySet()) {
            List<DeliverPlanDetail> deliverPlanDetails = deliverPlanMap.get(id);
            //如果没有订单则直接跳过
            if (CollectionUtils.isNotEmpty(deliverPlanDetails)) {
                //开始匹配订单
                this.getDeliverPlanLock(deliverPlanDetails, id);
            }
        }
    }


    @Transactional
    public void getDeliverPlanLock(List<DeliverPlanDetail> deliverPlanDetailList, Long id) {
        DeliverPlan deliverPlan = iDeliverPlanService.getById(id);
        for (DeliverPlanDetail deliverPlanDetail : deliverPlanDetailList) {
            //如果是未确认外的单据直接跳过
            if (!DeliverPlanLineStatus.COMFIRM.toString().equals(deliverPlanDetail.getDeliverPlanStatus())) {
                continue;
            }
            try {
                //匹配订单--》组装数据
                ArrayList<DeliverPlanDetail> deliverPlanDetailCopy = new ArrayList<>();
                deliverPlanDetailCopy.add(deliverPlanDetail);
                DeliverPlanDTO deliverPlanDTO = new DeliverPlanDTO().setDeliverPlan(deliverPlan);
                deliverPlanDTO.setDeliverPlanDetailList(deliverPlanDetailCopy);
                //匹配订单--》匹配开始
                iDeliverPlanDetailService.MatchingOrder(deliverPlanDTO);
            } catch (Exception e) {
                continue;
            }
            deliverPlanDetail.setDeliverPlanLock("1");
            iDeliverPlanDetailService.updateById(deliverPlanDetail);
        }
    }
}
