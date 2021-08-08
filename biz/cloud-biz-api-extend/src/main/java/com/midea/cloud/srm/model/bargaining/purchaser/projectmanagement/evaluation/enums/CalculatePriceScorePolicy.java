package com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.enums;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.enums.BiddingAwardWay.COMBINED_DECISION;
import static com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.enums.BiddingAwardWay.INDIVIDUAL_DECISION;
import static com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.enums.EvaluateMethod.*;

/**
 * 价格得分计算策略
 *
 * @author zixuan.yan@meicloud.com
 */
@Getter
@AllArgsConstructor
public enum CalculatePriceScorePolicy {
    INDIVIDUAL_HIGH_PRICE(INDIVIDUAL_DECISION, HIGH_PRICE),
    INDIVIDUAL_LOW_PRICE(INDIVIDUAL_DECISION, LOW_PRICE),
    INDIVIDUAL_COMPOSITE_PRICE(INDIVIDUAL_DECISION, COMPOSITE_PRICE),

    COMBINED_HIGH_PRICE(COMBINED_DECISION, HIGH_PRICE),
    COMBINED_LOW_PRICE(COMBINED_DECISION, LOW_PRICE),
    COMBINED_COMPOSITE_PRICE(COMBINED_DECISION, COMPOSITE_PRICE);


    private final BiddingAwardWay   biddingAwardWay;
    private final EvaluateMethod    evaluateMethod;

    public static CalculatePriceScorePolicy get(BiddingAwardWay biddingAwardWay, EvaluateMethod evaluateMethod) {
        return Arrays.stream(CalculatePriceScorePolicy.values())
                .filter(policy -> policy.getBiddingAwardWay().equals(biddingAwardWay)
                        && policy.getEvaluateMethod().equals(evaluateMethod))
                .findAny()
                .orElseThrow(() -> new ApiException("Could not find policy with [" + biddingAwardWay + ", " + evaluateMethod + "]"));
    }
}
