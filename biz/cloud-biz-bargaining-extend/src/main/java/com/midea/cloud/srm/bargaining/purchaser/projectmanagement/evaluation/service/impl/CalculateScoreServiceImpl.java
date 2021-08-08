package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.evaluation.service.impl;

import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.evaluation.service.*;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.enums.BiddingAwardWay;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.enums.CalculatePriceScorePolicy;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.enums.EvaluateMethod;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.OrderLine;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implement of {@link ICalculateScoreService}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Service
public class CalculateScoreServiceImpl implements ICalculateScoreService {

    private final Supplier<Collection<ICalculatePriceScoreService>>     calculatePriceScoreServiceSupplier
            = () -> SpringContextHolder.getApplicationContext().getBeansOfType(ICalculatePriceScoreService.class).values();
    private final Supplier<ICalculateTechnologyScoreService>            calculateTechnologyScoreServiceSupplier
            = () -> SpringContextHolder.getBean(ICalculateTechnologyScoreService.class);
    private final Supplier<ICalculatePerformanceScoreService>           calculatePerformanceScoreServiceSupplier
            = () -> SpringContextHolder.getBean(ICalculatePerformanceScoreService.class);
    private final Supplier<ICalculateCompositeScoreService>             calculateCompositeScoreServiceSupplier
            = () -> SpringContextHolder.getBean(ICalculateCompositeScoreService.class);


    @Override
    public void calculateAndSet(Biding bidding, List<OrderLine> orderLines) {

        // 决标方式（单项/总项）
        BiddingAwardWay bidingAwardWay = BiddingAwardWay.get(bidding.getBidingAwardWay());
        // 评分方法（低价/高价/综合）
        EvaluateMethod evaluateMethod = EvaluateMethod.get(bidding.getEvaluateMethod());

        // 计算 价格得分
        calculatePriceScoreServiceSupplier.get().stream()
                .filter(service -> service.getPolicy().equals(CalculatePriceScorePolicy.get(bidingAwardWay, evaluateMethod)))   // 根据策略选择对应价格计算服务
                .findAny()
                .orElseThrow(() -> new BaseException("获取价格计算服务失败。"))
                .calculateAndSet(orderLines);

        // 计算 技术得分
        calculateTechnologyScoreServiceSupplier.get().calculateAndSet(orderLines);

        // 计算 绩效得分
        calculatePerformanceScoreServiceSupplier.get().calculateAndSet(orderLines);


        // 根据[价格、技术、绩效]计算 综合得分
        calculateCompositeScoreServiceSupplier.get().calculateAndSet(orderLines);
    }

    @Override
    public void rankAndSet(List<OrderLine> orderLines) {
        orderLines.stream()
                .collect(Collectors.groupingBy(OrderLine::getRequirementLineId))    // 按物料需求行分组排序
                .forEach(((demandLineId, orderLinesGroup) -> {

                    // 排序 [综合得分]
                    List<OrderLine> sortedOrderLines = orderLinesGroup.stream()
                            .sorted(Comparator.comparing(OrderLine::getCompositeScore).reversed())
                            .collect(Collectors.toList());

                    // 设置排名
                    Stream.iterate(0, index -> index + 1)
                            .limit(sortedOrderLines.size())
                            .forEach(index -> {

                                // 若是第一位，则直接设置
                                OrderLine orderLine = sortedOrderLines.get(index);
                                if (index == 0) {
                                    orderLine.setRank(index + 1);
                                    return;
                                }

                                // 若当前得分与上一位的得分相同，则名次并列
                                OrderLine lastOrderLine = sortedOrderLines.get(index - 1);
                                if (orderLine.getCompositeScore().compareTo(lastOrderLine.getCompositeScore()) == 0) {
                                    orderLine.setRank(lastOrderLine.getRank());
                                }
                                else {
                                    orderLine.setRank(lastOrderLine.getRank() + 1);
                                }

                            });

                }));
    }
}
