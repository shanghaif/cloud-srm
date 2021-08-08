package com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service.impl;

import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service.ICalculateCombinedPriceScoreService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service.ICalculatePriceScoreService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.enums.CalculatePriceScorePolicy;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;

import static com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.enums.CalculatePriceScorePolicy.COMBINED_COMPOSITE_PRICE;

/**
 * Implement of {@link ICalculatePriceScoreService} with policy {@code COMBINED_COMPOSITE_PRICE}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Service
public class CalculateCombinedCompositePriceScoreServiceImpl implements ICalculateCombinedPriceScoreService {

    @Override
    public void calculateAndSet(Collection<OrderLine> orderLines) {
        if (orderLines.isEmpty())
            return;

        // 获取 招标单ID
        Long bidding = orderLines.stream().findAny()
                .map(OrderLine::getBidingId)
                .orElseThrow(() -> new BaseException("获取招标单ID失败。"));


        // 获取 价格权重
        Integer scoreWeight = this.getPriceScoreWeight(bidding);

        // 计算并设置组合价格
        this.calculateAndSetCombinedPrice(orderLines);

        // 综合评分法计算价格得分时，默认使用[低价法]
        orderLines.forEach(orderLine -> {
            BigDecimal priceScore = this.calculateLowPriceScore(
                    scoreWeight,
                    orderLine.getTotalDiscountAmount(),
                    orderLine.getCurrentRoundMinDiscountAmount()
            );
            orderLine.setPriceScore(priceScore);
        });
    }

    @Override
    public CalculatePriceScorePolicy getPolicy() {
        return COMBINED_COMPOSITE_PRICE;
    }
}
