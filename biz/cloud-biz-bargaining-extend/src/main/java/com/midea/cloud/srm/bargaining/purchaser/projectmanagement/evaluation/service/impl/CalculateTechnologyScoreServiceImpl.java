package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.evaluation.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.enums.bargaining.projectmanagement.evaluation.ScoreDimensionEnum;
import com.midea.cloud.common.enums.bargaining.projectmanagement.techscore.ScoreStatusEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.BidScoreRuleLineMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.BidVendorMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.GroupMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.evaluation.service.ICalculateTechnologyScoreService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techscore.mapper.TechScoreHeadMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techscore.mapper.TechScoreLineMapper;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Group;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.entity.ScoreRuleLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.entity.TechScoreHead;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.entity.TechScoreLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.vo.CalculateVendorTechnologyScoreParameter;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.vo.VendorTechnologyScore;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implement of {@link ICalculateTechnologyScoreService}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Service
public class CalculateTechnologyScoreServiceImpl implements ICalculateTechnologyScoreService {

    private final EntityManager<BidVendor>      bidVendorDao
            = EntityManager.use(BidVendorMapper.class);
    private final EntityManager<ScoreRuleLine>  scoreRuleLineDao
            = EntityManager.use(BidScoreRuleLineMapper.class);
    private final EntityManager<Group>          gradeGroupDao
            = EntityManager.use(GroupMapper.class);
    private final EntityManager<TechScoreHead>  techScoreHeaderDao
            = EntityManager.use(TechScoreHeadMapper.class);
    private final EntityManager<TechScoreLine>  techScoreLineDao
            = EntityManager.use(TechScoreLineMapper.class);


    @Override
    public List<VendorTechnologyScore> calculate(CalculateVendorTechnologyScoreParameter parameter) {
        if (CollectionUtils.isEmpty(parameter.getBidVendorIds()))
            return Collections.emptyList();


        // 获取 投标供应商信息
        List<BidVendor> bidVendors = bidVendorDao.findAll(
                Wrappers.lambdaQuery(BidVendor.class)
                        .in(BidVendor::getBidVendorId, parameter.getBidVendorIds())
        );

        // 获取 招标单ID
        Long bidding = bidVendors.stream().findAny()
                .map(BidVendor::getBidingId)
                .orElseThrow(() -> new BaseException("获取招标单ID失败。"));


        // 获取 [技术]维度的评分规则
        List<ScoreRuleLine> scoreRuleLines = scoreRuleLineDao.findAll(
                Wrappers.lambdaQuery(ScoreRuleLine.class)
                        .eq(ScoreRuleLine::getBidingId, bidding)
                        .eq(ScoreRuleLine::getScoreDimension, ScoreDimensionEnum.TECHNOLOGY.getValue())
        );
        // 获取 [技术]维度总权重
        Integer totalWeight = scoreRuleLines.stream()
                .map(scoreRuleLine -> Optional.ofNullable(scoreRuleLine.getScoreWeight()).orElse(0))
                .reduce(0, Integer::sum);
        // 如果没有[技术]维度的评分规则，则当0分处理
        if (scoreRuleLines.isEmpty() || totalWeight == 0) {
            return parameter.getBidVendorIds().stream()
                    .map(bidVendorId -> VendorTechnologyScore.builder()
                            .bidVendorId(bidVendorId)
                            .score(BigDecimal.ZERO)
                            .scoreWithoutWeight(BigDecimal.ZERO)
                            .weight(0)
                            .build())
                    .collect(Collectors.toList());
        }


        // 获取 各评委对供应商的技术评分信息[头]
        List<TechScoreHead> techScoreHeaders = techScoreHeaderDao.findAll(
                Wrappers.lambdaQuery(TechScoreHead.class)
                        .eq(TechScoreHead::getBidingId, bidding)
                        .eq(TechScoreHead::getScoreStatus, ScoreStatusEnum.FINISHED.getValue())
        );
        // 按[投标供应商 + 评委]分组
        Map<Long, Map<Long, TechScoreHead>> techScoreHeadersMap = techScoreHeaders.stream()
                .collect(Collectors.groupingBy(
                        TechScoreHead::getBidVendorId,
                        Collectors.toMap(TechScoreHead::getCreatedId, x -> x, (x, y) -> x))
                );
        // 如果还没有技术评分，则当0分处理
        if (techScoreHeaders.isEmpty()) {
            return parameter.getBidVendorIds().stream()
                    .map(bidVendorId -> VendorTechnologyScore.builder()
                            .bidVendorId(bidVendorId)
                            .score(BigDecimal.ZERO)
                            .scoreWithoutWeight(BigDecimal.ZERO)
                            .weight(0)
                            .build())
                    .collect(Collectors.toList());
        }


        // 获取 各评委对供应商的技术评分信息[行]
        List<TechScoreLine> techScoreLines = techScoreLineDao.findAll(
                Wrappers.lambdaQuery(TechScoreLine.class).in(
                        TechScoreLine::getTechScoreHeadId,
                        techScoreHeaders.stream()
                                .map(TechScoreHead::getTechScoreHeadId)
                                .collect(Collectors.toSet())
                )
        );
        // 按[技术评分信息头]分组
        Map<Long, List<TechScoreLine>> techScoreLinesMap = techScoreLines.stream()
                .collect(Collectors.groupingBy(TechScoreLine::getTechScoreHeadId));


        // 获取 各评委信息
        List<Group> gradeGroups = gradeGroupDao.findAll(
                Wrappers.lambdaQuery(Group.class)
                        .eq(Group::getBidingId, bidding)
                        .eq(Group::getJudgeFlag, "Y")
        );

        // 获取 最大总评分值
        BigDecimal maxEvaluateScore = gradeGroups.stream()
                .map(gradeGroup -> BigDecimal.valueOf(gradeGroup.getMaxEvaluateScore()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        // 计算 [每个供应商]的技术得分
        return bidVendors.stream().map(bidVendor -> {

            // 获取 该供应商的所有技术评分信息[头]（按[评委]分组）
            Map<Long, TechScoreHead> vendorTechScoreHeaders = techScoreHeadersMap.get(bidVendor.getBidVendorId());

            // 若当前供应商没有被评分，则当0分处理
            if (vendorTechScoreHeaders == null) {
                return VendorTechnologyScore.builder()
                        .bidVendorId(bidVendor.getBidVendorId())
                        .score(BigDecimal.ZERO)
                        .scoreWithoutWeight(BigDecimal.ZERO)
                        .weight(0)
                        .build();
            }


            // 获取 该供应商的所有技术评分信息[行]
            List<TechScoreLine> vendorTechScoreLines = vendorTechScoreHeaders.values().stream()
                    .flatMap(header -> techScoreLinesMap.get(header.getTechScoreHeadId()).stream())
                    .collect(Collectors.toList());

            // 计算 技术总得分 - 已乘以总维度权重（按[评分规则明细]分组统计合并）
            BigDecimal techTotalScoreWithWeight = scoreRuleLines.stream()
                    .map(scoreRuleLine -> {

                        // 计算 同维度下的总得分
                        BigDecimal sumScore = vendorTechScoreLines.stream()
                                .filter(vendorTechScoreLine -> scoreRuleLine.getRuleLineId().equals(vendorTechScoreLine.getRuleLineId()))   // 过滤其他维度的评分明细
                                .map(TechScoreLine::getScore)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                        // 计算 总得分换成百分制
                        BigDecimal hundredPercentageScore = BigDecimal.valueOf(100).multiply(
                                sumScore.divide(maxEvaluateScore, 2, RoundingMode.HALF_DOWN)
                        );

                        // 同维度下总得分 * 维度权重
                        return BigDecimal.valueOf(scoreRuleLine.getScoreWeight() / 100D).multiply(hundredPercentageScore);
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // 返回供应商对应的技术得分
            return VendorTechnologyScore.builder()
                    .bidVendorId(bidVendor.getBidVendorId())
                    .score(techTotalScoreWithWeight)
                    .scoreWithoutWeight(techTotalScoreWithWeight.divide(BigDecimal.valueOf(totalWeight * 0.01), 2, RoundingMode.HALF_UP))
                    .weight(totalWeight)
                    .build();

        }).collect(Collectors.toList());
    }

    @Override
    public void calculateAndSet(Collection<OrderLine> orderLines) {
        if (orderLines.isEmpty())
            return;

        // 计算 技术得分（按[投标供应商]获取）
        Map<Long, VendorTechnologyScore> vendorTechnologyScores = this.calculate(
                CalculateVendorTechnologyScoreParameter.builder()
                        .bidVendorIds(orderLines.stream()
                                .map(OrderLine::getBidVendorId)
                                .collect(Collectors.toSet()))
                        .build()
        ).stream().collect(Collectors.toMap(VendorTechnologyScore::getBidVendorId, x -> x));

        // 按[投标供应商]分组，设置技术得分
        orderLines.stream()
                .collect(Collectors.groupingBy(OrderLine::getBidVendorId))
                .forEach((bidVendorId, orderLinesGroup) -> orderLinesGroup.forEach(orderLine -> {

                    // 获取 当前供应商技术得分
                    BigDecimal techScore = Optional.ofNullable(vendorTechnologyScores.get(orderLine.getBidVendorId()))
                            .map(VendorTechnologyScore::getScore)
                            .orElse(BigDecimal.ZERO);

                    // 设置 当前供应商技术得分
                    orderLine.setTechScore(techScore);
                }));
    }
}
