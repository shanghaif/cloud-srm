package com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidingMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.mapper.OrderlinePaymentTermMapper;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.enums.EvaluateMethod;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.entity.OrderlinePaymentTerm;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * [单项]价格得分计算服务
 *
 * @author zixuan.yan@meicloud.com
 */
public interface ICalculateIndividualPriceScoreService extends ICalculatePriceScoreService {

    Supplier<IEvaluationService>    evaluationServiceSupplier
            = () -> SpringContextHolder.getBean(IEvaluationService.class);
    EntityManager<Biding>           biddingDao
            = EntityManager.use(BidingMapper.class);
    EntityManager<OrderlinePaymentTerm> paymentDao = EntityManager.use(OrderlinePaymentTermMapper.class);


    /**
     * 计算并设置 价格
     *
     * @param orderLines 供应商报价行集
     */
    default void calculateAndSetIndividualPrice(Collection<OrderLine> orderLines) {
        if (CollectionUtils.isEmpty(orderLines))
            return;

        // 获取 招标单
        Biding bidding = orderLines.stream().findAny()
                .map(orderLine -> biddingDao.findById(orderLine.getBidingId()))
                .orElseThrow(() -> new BaseException("获取招标单失败。"));
        List<Long> orderLineIds = orderLines.stream().map(OrderLine::getOrderLineId).collect(Collectors.toList());
        Map<Long, List<OrderlinePaymentTerm>> collect = paymentDao.findAll(Wrappers.lambdaQuery(OrderlinePaymentTerm.class)
                .in(OrderlinePaymentTerm::getOrderLineId, orderLineIds)
        ).stream().collect(Collectors.groupingBy(OrderlinePaymentTerm::getOrderLineId));
        // 计算 折息价
        orderLines.forEach(orderLine -> {
            BigDecimal discountPrice = evaluationServiceSupplier.get().calculateDiscountPrice(orderLine,
//                    bidding.getMonthlyInterest(),
//                    Objects.equals(bidding.getEvaluateMethod(), EvaluateMethod.HIGH_PRICE.getItemValue()),
                    null ,true,
                    collect.get(orderLine.getOrderLineId()));
            orderLine.setDiscountPrice(discountPrice);
        });

        // 按物料需求行分组，计算本轮次最低最高折息价
        Map<Long, DoubleSummaryStatistics> discountPriceMap = orderLines.stream()
                .collect(Collectors.groupingBy(
                        OrderLine::getRequirementLineId,
                        Collectors.summarizingDouble(orderLine -> orderLine.getDiscountPrice() != null
                                ? orderLine.getDiscountPrice().doubleValue()
                                : 0D)
                ));
        orderLines.forEach(orderLine -> {
            DoubleSummaryStatistics statistics = discountPriceMap.get(orderLine.getRequirementLineId());
            orderLine.setCurrentRoundMinDiscountPrice(BigDecimal.valueOf(statistics.getMin()));
            orderLine.setCurrentRoundMaxDiscountPrice(BigDecimal.valueOf(statistics.getMax()));

            // 设置 本轮最低最高价
            orderLine.setCurrentRoundMinPrice(orderLine.getCurrentRoundMinDiscountPrice());
            orderLine.setCurrentRoundMaxPrice(orderLine.getCurrentRoundMaxDiscountPrice());
        });
    }
}
