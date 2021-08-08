package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.evaluation.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.BidRequirementLineMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.BidVendorMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.evaluation.mapper.VendorPerformanceMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.evaluation.service.ICalculatePerformanceScoreService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.entity.VendorPerformance;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.vo.CalculateVendorPerformanceScoreParameter;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.vo.VendorPerformanceScore;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implement of {@link ICalculatePerformanceScoreService}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Service
public class CalculatePerformanceScoreServiceImpl implements ICalculatePerformanceScoreService {

    private final EntityManager<BidVendor>          bidVendorDao
            = EntityManager.use(BidVendorMapper.class);
    private final EntityManager<BidRequirementLine> bidRequirementLineDao
            = EntityManager.use(BidRequirementLineMapper.class);
    private final EntityManager<VendorPerformance>  vendorPerformanceDao
            = EntityManager.use(VendorPerformanceMapper.class);


    @Override
    public List<VendorPerformanceScore> calculate(CalculateVendorPerformanceScoreParameter parameter) {
        if (CollectionUtils.isEmpty(parameter.getOrderLines()))
            return Collections.emptyList();


        // 获取 投标供应商信息
        List<BidVendor> bidVendors = bidVendorDao.findAll(Wrappers.lambdaQuery(BidVendor.class).in(
                BidVendor::getBidVendorId,
                parameter.getOrderLines().stream()
                        .map(CalculateVendorPerformanceScoreParameter.OrderLine::getBidVendorId)
                        .collect(Collectors.toSet()))
        );

        // 获取 招标单ID
        Long bidding = bidVendors.stream().findAny()
                .map(BidVendor::getBidingId)
                .orElseThrow(() -> new BaseException("获取招标单ID失败。"));


        // 获取 绩效权重
        Integer scoreWeight = this.getPerformanceScoreWeight(bidding);
        // 如果没有[绩效]维度的评分规则或权重为0，则当0分处理
        if (scoreWeight == 0) {
            return parameter.getOrderLines().stream()
                    .map(orderLine -> VendorPerformanceScore.builder()
                            .orderLineId(orderLine.getOrderLineId())
                            .bidVendorId(orderLine.getBidVendorId())
                            .score(BigDecimal.ZERO)
                            .build())
                    .collect(Collectors.toList());
        }


        // 获取 绩效评分信息
        List<VendorPerformance> performanceScores = vendorPerformanceDao.findAll(
                Wrappers.lambdaQuery(VendorPerformance.class)
                        .eq(VendorPerformance::getBidingId, bidding)
        );
        // 按[投标供应商 + 采购品类 + 采购组织]分组
        Map<Long, Map<Long, Map<Long, List<VendorPerformance>>>> performanceScoresGroups = performanceScores.stream()
                .collect(Collectors.groupingBy(
                        VendorPerformance::getBidVendorId,
                        Collectors.groupingBy(
                                VendorPerformance::getCategoryId,
                                Collectors.groupingBy(VendorPerformance::getOrgId)
                        )
                ));


        // 计算 [每个供应商]的绩效得分
        return bidVendors.stream().flatMap(bidVendor -> parameter.getOrderLines().stream().filter(filterVendor.apply(bidVendor)).map(orderLine -> {

            // 获取 该[供应商]的所有绩效评分信息（按[采购品类 + 采购组织]分组）
            Map<Long, Map<Long, List<VendorPerformance>>> vendorPerformanceScores = performanceScoresGroups.get(bidVendor.getBidVendorId());

            // 若当前供应商没有被评分，则当0分处理
            if (vendorPerformanceScores == null) {
                return VendorPerformanceScore.builder()
                        .orderLineId(orderLine.getOrderLineId())
                        .bidVendorId(orderLine.getBidVendorId())
                        .score(BigDecimal.ZERO)
                        .build();
            }


            // 获取 该[供应商]的[采购品类]下的所有绩效评分信息（按[采购组织]分组
            Map<Long, List<VendorPerformance>> vendorCatePerformanceScores = vendorPerformanceScores.get(orderLine.getCategoryId());

            // 若当前供应商对该品类没有被评分，则当0分处理
            if (vendorCatePerformanceScores == null) {
                return VendorPerformanceScore.builder()
                        .orderLineId(orderLine.getOrderLineId())
                        .bidVendorId(orderLine.getBidVendorId())
                        .score(BigDecimal.ZERO)
                        .build();
            }


            // 获取 该[供应商]的[采购品类]下的[采购组织]绩效评分信息   todo 因为引入了OU组的概念，需求行的orgId有可能为空
            List<VendorPerformance> vendorCateOrgPerformanceScores = vendorCatePerformanceScores.getOrDefault(orderLine.getOrgId(), Collections.emptyList());

            // 若当前供应商对该品类下的组织没有被评分，则当0分处理
            if (vendorCateOrgPerformanceScores.isEmpty()) {
                return VendorPerformanceScore.builder()
                        .orderLineId(orderLine.getOrderLineId())
                        .bidVendorId(orderLine.getBidVendorId())
                        .score(BigDecimal.ZERO)
                        .build();
            }


            // todo 若多条，目前按平均值计算
            BigDecimal hundredPercentageScore = vendorCateOrgPerformanceScores.stream()
                    .map(VendorPerformance::getPerformanceScore)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(vendorCateOrgPerformanceScores.size()), 2, RoundingMode.HALF_DOWN);

            // 绩效得分 * 维度权重
            BigDecimal performanceScore = BigDecimal.valueOf(scoreWeight / 100D).multiply(hundredPercentageScore);

            // 返回供应商对应的绩效得分
            return VendorPerformanceScore.builder()
                    .orderLineId(orderLine.getOrderLineId())
                    .bidVendorId(orderLine.getBidVendorId())
                    .score(performanceScore)
                    .build();

        })).collect(Collectors.toList());
    }

    @Override
    public void calculateAndSet(Collection<OrderLine> orderLines) {
        if (orderLines.isEmpty())
            return;


        // 获取 招标单ID
        Long bidding = orderLines.stream().findAny()
                .map(OrderLine::getBidingId)
                .orElseThrow(() -> new BaseException("获取招标单ID失败。"));

        // 获取 关联物料需求行
        Map<Long, BidRequirementLine> demandLines = bidRequirementLineDao
                .findAll(
                        Wrappers.lambdaQuery(BidRequirementLine.class).eq(BidRequirementLine::getBidingId, bidding)
                )
                .stream()
                .collect(Collectors.toMap(BidRequirementLine::getRequirementLineId, x -> x, (x, y) -> x));


        // 计算 技术得分（按[投标行]获取）
        Map<Long, VendorPerformanceScore> vendorPerformanceScores = this.calculate(
                CalculateVendorPerformanceScoreParameter.builder()
                        .orderLines(
                                orderLines.stream()
                                        .map(orderLine -> {
                                            BidRequirementLine demandLine = Optional.ofNullable(demandLines.get(orderLine.getRequirementLineId()))
                                                    .orElseThrow(() -> new BaseException("获取管理需求行失败。 | requirementLineId: [" + orderLine.getRequirementLineId() + "]"));
                                            return CalculateVendorPerformanceScoreParameter.OrderLine.builder()
                                                    .orderLineId(orderLine.getOrderLineId())
                                                    .biddingId(orderLine.getBidingId())
                                                    .bidVendorId(orderLine.getBidVendorId())
                                                    .requirementLineId(demandLine.getRequirementLineId())
                                                    .targetId(demandLine.getTargetId())
                                                    .categoryId(demandLine.getCategoryId())
                                                    .orgId(demandLine.getOrgId())
                                                    .ouId(demandLine.getOuId())
                                                    .build();
                                        })
                                        .collect(Collectors.toList())
                        )
                        .build()
        ).stream().collect(Collectors.toMap(VendorPerformanceScore::getOrderLineId, x -> x));


        // 为[每个投标行]设置绩效得分
        orderLines.forEach(orderLine -> {

            // 获取 当前供应商绩效得分
            BigDecimal techScore = Optional.ofNullable(vendorPerformanceScores.get(orderLine.getOrderLineId()))
                    .map(VendorPerformanceScore::getScore)
                    .orElse(BigDecimal.ZERO);

            // 设置 当前供应商绩效得分
            orderLine.setPerfScore(techScore);
        });
    }
}
