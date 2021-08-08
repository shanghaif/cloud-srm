package com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.enums.bid.projectmanagement.evaluation.ScoreDimensionEnum;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidScoreRuleLineMapper;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.evaluation.entity.ScoreRuleLine;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.evaluation.enums.CalculatePriceScorePolicy;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Optional;

/**
 * 价格得分计算服务
 *
 * @author zixuan.yan@meicloud.com
 */
public interface ICalculatePriceScoreService {

    /**
     * 计算并设置 供应商报价价格得分
     *
     * @param orderLines 供应商报价行集
     */
    void calculateAndSet(Collection<OrderLine> orderLines);

    /**
     * 获取 计算价格得分策略
     *
     * @return The {@link CalculatePriceScorePolicy}.
     */
    CalculatePriceScorePolicy getPolicy();


    /* =========================================================================== */

    /**
     * 合理高价法得分计算
     * 价格得分 = 供应商报价 / 最高价 * 100
     *
     * @param scoreWeight   得分权重
     * @param quotePrice    当前报价
     * @param highestPrice  最高价
     * @return  得分
     */
    default BigDecimal calculateHighPriceScore(Integer scoreWeight, BigDecimal quotePrice, BigDecimal highestPrice) {

        if (BigDecimal.ZERO.compareTo(highestPrice) == 0)
            return BigDecimal.ZERO;

        // 计算 价格得分
        BigDecimal score = quotePrice.divide(highestPrice, 10, RoundingMode.HALF_DOWN).multiply(BigDecimal.valueOf(100));

        // 最终得分 = 价格得分 * 维度权重
        return BigDecimal.valueOf(scoreWeight / 100D).multiply(score).setScale(2, RoundingMode.HALF_DOWN);
    }

    /**
     * 合理低价法得分计算
     * 价格得分 = 最低价 / 供应商报价 * 100
     *
     * @param scoreWeight   得分权重
     * @param quotePrice    当前报价
     * @param lowestPrice   最高价
     * @return  得分
     */
    default BigDecimal calculateLowPriceScore(Integer scoreWeight, BigDecimal quotePrice, BigDecimal lowestPrice) {

        if (BigDecimal.ZERO.compareTo(quotePrice) == 0)
            return BigDecimal.ZERO;

        // 计算 价格得分
        BigDecimal score = lowestPrice.divide(quotePrice, 10, RoundingMode.HALF_DOWN).multiply(BigDecimal.valueOf(100));

        // 最终得分 = 价格得分 * 维度权重
        return BigDecimal.valueOf(scoreWeight / 100D).multiply(score).setScale(2, RoundingMode.HALF_DOWN);
    }

    /**
     * 获取 价格权重
     *
     * @param bidding 招标单ID
     * @return 价格权重
     */
    default Integer getPriceScoreWeight(Long bidding) {
        return Optional.ofNullable(
                EntityManager.use(BidScoreRuleLineMapper.class).findOne(
                        Wrappers.lambdaQuery(ScoreRuleLine.class)
                                .eq(ScoreRuleLine::getBidingId, bidding)
                                .eq(ScoreRuleLine::getScoreDimension, ScoreDimensionEnum.PRICE.getValue())
                )
        ).map(ScoreRuleLine::getScoreWeight).orElse(0);
    }
}
