package com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.enums.bid.projectmanagement.evaluation.ScoreDimensionEnum;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidScoreRuleLineMapper;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.entity.ScoreRuleLine;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techscore.vo.CalculateVendorPerformanceScoreParameter;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techscore.vo.VendorPerformanceScore;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 绩效得分计算服务
 *
 * @author zixuan.yan@meicloud.com
 */
public interface ICalculatePerformanceScoreService {

    /**
     * 计算 供应商绩效得分
     *
     * @param parameter 计算参数
     */
    List<VendorPerformanceScore> calculate(CalculateVendorPerformanceScoreParameter parameter);

    /**
     * 计算并设置 供应商绩效得分
     *
     * @param orderLines 供应商报价行集
     */
    void calculateAndSet(Collection<OrderLine> orderLines);


    /* =========================================================================== */

    /**
     * 报价行匹配指定供应商
     */
    Function<BidVendor, Predicate<CalculateVendorPerformanceScoreParameter.OrderLine>> filterVendor =
            bidVendor -> orderLine -> bidVendor.getBidVendorId().equals(orderLine.getBidVendorId());

    /**
     * 获取 价格权重
     *
     * @param bidding 招标单ID
     * @return 价格权重
     */
    default Integer getPerformanceScoreWeight(Long bidding) {
        return Optional.ofNullable(
                EntityManager.use(BidScoreRuleLineMapper.class).findOne(
                        Wrappers.lambdaQuery(ScoreRuleLine.class)
                                .eq(ScoreRuleLine::getBidingId, bidding)
                                .eq(ScoreRuleLine::getScoreDimension, ScoreDimensionEnum.ACHIEVEMENT.getValue())
                )
        ).map(ScoreRuleLine::getScoreWeight).orElse(0);
    }
}
