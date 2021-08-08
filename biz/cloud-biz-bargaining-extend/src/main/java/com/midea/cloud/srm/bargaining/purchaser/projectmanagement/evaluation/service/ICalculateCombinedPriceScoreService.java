package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.evaluation.service;

import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.BidingMapper;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.OrderLine;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * [组合]价格得分计算服务
 *
 * @author zixuan.yan@meicloud.com
 */
public interface ICalculateCombinedPriceScoreService extends ICalculateIndividualPriceScoreService {

    Supplier<IEvaluationService>    evaluationServiceSupplier
            = () -> SpringContextHolder.getBean(IEvaluationService.class);
    EntityManager<Biding>           biddingDao
            = EntityManager.use(BidingMapper.class);


    /**
     * 计算并设置组合价格
     *
     * @param orderLines 供应商报价行集
     */
    default void calculateAndSetCombinedPrice(Collection<OrderLine> orderLines) {
        if (CollectionUtils.isEmpty(orderLines))
            return;


        // 计算并设置 价格
        this.calculateAndSetIndividualPrice(orderLines);


        // 按组合 + 供应商分组，并计算折息总金额
        Map<String, Map<Long, DoubleSummaryStatistics>> vendorTotalAmountMap = orderLines.stream()
                .collect(Collectors.groupingBy(
                        OrderLine::getItemGroup,
                        Collectors.groupingBy(OrderLine::getBidVendorId, Collectors.summarizingDouble(orderLine ->
                                orderLine.getQuantity() != null && orderLine.getDiscountPrice() != null
                                        ? orderLine.getQuantity() * orderLine.getDiscountPrice().doubleValue()
                                        : 0D)
                        ))
                );
        orderLines.forEach(orderLine -> {
            double discountAmount = vendorTotalAmountMap.get(orderLine.getItemGroup()).get(orderLine.getBidVendorId()).getSum();
            orderLine.setTotalDiscountAmount(BigDecimal.valueOf(discountAmount));
        });


        // 按组合分组，并计算本轮次最低最高折息总金额
        Map<String, DoubleSummaryStatistics> totalAmountMap = orderLines.stream()
                .collect(Collectors.groupingBy(
                        OrderLine::getItemGroup,
                        Collectors.summarizingDouble(orderLine -> orderLine.getTotalDiscountAmount() != null
                                ? orderLine.getTotalDiscountAmount().doubleValue()
                                : 0D)
                ));
        orderLines.forEach(orderLine -> {
            DoubleSummaryStatistics statistics = totalAmountMap.get(orderLine.getItemGroup());
            orderLine.setCurrentRoundMinDiscountAmount(BigDecimal.valueOf(statistics.getMin()));
            orderLine.setCurrentRoundMaxDiscountAmount(BigDecimal.valueOf(statistics.getMax()));
        });

    }
}
