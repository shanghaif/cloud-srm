package com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.enums.bid.projectmanagement.bidinitiating.BidType;
import com.midea.cloud.common.enums.bid.projectmanagement.evaluation.BidingAwardWayEnum;
import com.midea.cloud.common.enums.bid.projectmanagement.evaluation.EvaluateMethodEnum;
import com.midea.cloud.common.enums.bid.projectmanagement.evaluation.ScoreDimensionEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidRequirementLineService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidScoreRuleLineService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidVendorService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service.IScoreService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service.IVendorPerformanceService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.ITechProposalService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.entity.ScoreRuleLine;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.entity.VendorPerformance;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.vo.ProgressOfTechBidVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 * 分数计算类
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年3月27日 下午3:32:03
 *  修改内容:
 *          </pre>
 */
@Service
public class ScoreServiceImpl implements IScoreService {

	@Autowired
	private IBidRequirementLineService iBidRequirementLineService;

	@Autowired
	private IVendorPerformanceService iVendorPerformanceService;

	@Autowired
	private IBidVendorService iBidVendorService;

	@Autowired
	private IBidScoreRuleLineService iBidScoreRuleLineService;

	@Autowired
	private ITechProposalService iTechProposalService;

	private BigDecimal getPriceScoreByLowerPrice(BigDecimal price, BigDecimal minPrice) {
		// 价格得分=100*[1-(供应商报价-最低价)/最低价]
		BigDecimal delta = price.subtract(minPrice);
		BigDecimal percent = delta.divide(minPrice, 100, RoundingMode.HALF_DOWN);
		if (percent.doubleValue() >= 1) {
			return new BigDecimal(0);
		}
		BigDecimal priceScore = new BigDecimal(100).multiply(new BigDecimal(1).subtract(percent));
		return priceScore;
	}

	private BigDecimal getPriceScoreByHighPrice(BigDecimal price, BigDecimal maxPrice) {
		// 价格得分=100*[1-(最高价-供应商报价)/最高价]
		BigDecimal delta = maxPrice.subtract(price);
		BigDecimal percent = delta.divide(maxPrice, 100, RoundingMode.HALF_DOWN);
		BigDecimal priceScore = new BigDecimal(100).multiply(new BigDecimal(1).subtract(percent));
		return priceScore;
	}

	private void setGroupPriceScore(List<OrderLine> submissionOrderLineList, EvaluateMethodEnum evaluateMethodEnum) {
		// 计算组合下，每个供应商的组合总金额
		Map<String, Map<Long, DoubleSummaryStatistics>> vendorTotalAmountMap = submissionOrderLineList.stream().collect(Collectors.groupingBy(OrderLine::getItemGroup, Collectors.groupingBy(OrderLine::getBidVendorId, Collectors.summarizingDouble(OrderLine::calculateLineAmount))));
		for (OrderLine orderLine : submissionOrderLineList) {
			orderLine.setTotalAmount(new BigDecimal(vendorTotalAmountMap.get(orderLine.getItemGroup()).get(orderLine.getBidVendorId()).getSum()));
		}
		Map<String, DoubleSummaryStatistics> totalAmountMap = submissionOrderLineList.stream().collect(Collectors.groupingBy(OrderLine::getItemGroup, Collectors.summarizingDouble(OrderLine::getTotalAmountDoubleValue)));
		for (OrderLine orderLine : submissionOrderLineList) {
			if (evaluateMethodEnum == EvaluateMethodEnum.HIGH_PRICE) {
				BigDecimal maxTotalAmount = new BigDecimal(totalAmountMap.get(orderLine.getItemGroup()).getMax());
				orderLine.setPriceScore(getPriceScoreByHighPrice(orderLine.getTotalAmount(), maxTotalAmount).setScale(2, RoundingMode.HALF_DOWN));
			} else if (evaluateMethodEnum == EvaluateMethodEnum.LOWER_PRICE) {
				BigDecimal minTotalAmount = new BigDecimal(totalAmountMap.get(orderLine.getItemGroup()).getMin());
				orderLine.setPriceScore(getPriceScoreByLowerPrice(orderLine.getTotalAmount(), minTotalAmount).setScale(2, RoundingMode.HALF_DOWN));
			}
		}
	}

	@Override
	public void calculateScore(Biding biding, List<OrderLine> submissionOrderLineList) {
//		BidType bidTypeEnum = BidType.get(biding.getBidingType());
//		//评分方法
//		EvaluateMethodEnum evaluateMethodEnum = EvaluateMethodEnum.get(biding.getEvaluateMethod());
//		//决标方式
//		BidingAwardWayEnum bidingAwardWayEnum = BidingAwardWayEnum.get(biding.getBidingAwardWay());
//
//		if (bidTypeEnum == BidType.BUSINESS && bidingAwardWayEnum == BidingAwardWayEnum.INDIVIDUAL_DECISION && (evaluateMethodEnum == EvaluateMethodEnum.HIGH_PRICE || evaluateMethodEnum == EvaluateMethodEnum.LOWER_PRICE)) {
//			// 商务+单项决标+合理高价法 OR 商务+单项决标+合理低价法
//			for (OrderLine orderLine : submissionOrderLineList) {
//				if (evaluateMethodEnum == EvaluateMethodEnum.HIGH_PRICE) {
//					orderLine.setPriceScore(getPriceScoreByHighPrice(orderLine.getPrice(), orderLine.getCurrentRoundMaxPrice()).setScale(2, RoundingMode.HALF_DOWN));
//				} else if (evaluateMethodEnum == EvaluateMethodEnum.LOWER_PRICE) {
//					orderLine.setPriceScore(getPriceScoreByLowerPrice(orderLine.getPrice(), orderLine.getCurrentRoundMinPrice()).setScale(2, RoundingMode.HALF_DOWN));
//				}
//			}
//		} else if (bidTypeEnum == BidType.BUSINESS && bidingAwardWayEnum == BidingAwardWayEnum.COMBINED_DECISION && (evaluateMethodEnum == EvaluateMethodEnum.HIGH_PRICE || evaluateMethodEnum == EvaluateMethodEnum.LOWER_PRICE)) {
//			// 商务+组合决标+合理高价法 OR 商务+组合决标+合理低价法
//			setGroupPriceScore(submissionOrderLineList, evaluateMethodEnum);
//		} else if (evaluateMethodEnum == EvaluateMethodEnum.COMPOSITE_SCORE) {
//			// 商务标+综合评标法+单项决标 OR 商务标+综合评标法+组合决标 OR 商务/技术+综合评标法+组合决标
//			List<BidVendor> vendorList = iBidVendorService.list(new QueryWrapper<BidVendor>(new BidVendor().setBidingId(biding.getBidingId())));
//			Map<Long, BidVendor> vendorMap = vendorList.stream().collect(Collectors.toMap(BidVendor::getBidVendorId, Function.identity()));
//			List<VendorPerformance> performanceList = iVendorPerformanceService.list(new QueryWrapper<VendorPerformance>(new VendorPerformance().setBidingId(biding.getBidingId())));
//			Map<String, VendorPerformance> vendorPerformanceMap = performanceList.stream().collect(Collectors.toMap(VendorPerformance::groupKey, Function.identity()));
//			List<BidRequirementLine> requirementLineList = iBidRequirementLineService.list(new QueryWrapper<BidRequirementLine>(new BidRequirementLine().setBidingId(biding.getBidingId())));
//			Map<Long, BidRequirementLine> requirementLineMap = requirementLineList.stream().collect(Collectors.toMap(BidRequirementLine::getRequirementLineId, Function.identity()));
//			// 绩效得分 (招标类型：商务，评分规则：综合评分法；校验绩效是否录入)
//			if (bidTypeEnum == BidType.BUSINESS) {
//				for (OrderLine orderLine : submissionOrderLineList) {
//					BidRequirementLine requirementLine = requirementLineMap.get(orderLine.getRequirementLineId());
//					BidVendor vendor = vendorMap.get(orderLine.getBidVendorId());
//					Assert.notNull(requirementLine, "投标单上的招标需求行ID不存在");
//					Assert.notNull(vendor, "投标单上的供应商ID不存在");
//					String key = requirementLine.getOrgId() + "-" + orderLine.getBidVendorId() + "-" + requirementLine.getCategoryId();
//					if (!vendorPerformanceMap.containsKey(key)) {
//						throw new BaseException(LocaleHandler.getLocaleMsg("没有维护供应商：,采购组织：,采购品类：,的绩效得分", vendor.getVendorName(), requirementLine.getOrgName(), requirementLine.getCategoryName()));
//					}
//					orderLine.setPerfScore(vendorPerformanceMap.get(key).getPerformanceScore());
//				}
//			}
//			// 价格得分
//			if (bidingAwardWayEnum == BidingAwardWayEnum.INDIVIDUAL_DECISION) {
//				submissionOrderLineList.forEach(l -> l.setPriceScore(getPriceScoreByLowerPrice(l.getPrice(), l.getCurrentRoundMinPrice())));
//			} else if (bidingAwardWayEnum == BidingAwardWayEnum.COMBINED_DECISION) {
//				setGroupPriceScore(submissionOrderLineList, EvaluateMethodEnum.LOWER_PRICE);// 指定用合理低价
//			}
//			// 技术得分
//			List<ProgressOfTechBidVO> progressOfTechBidList = iTechProposalService.queryProgressOfTechBidList(biding.getBidingId(), null, null, false);
//			Map<Long, ProgressOfTechBidVO> progressOfTechBidMap = progressOfTechBidList.stream().collect(Collectors.toMap(ProgressOfTechBidVO::getBidVendorId, Function.identity()));
//			for (OrderLine orderLine : submissionOrderLineList) {
//				ProgressOfTechBidVO progressOfTechBidVO = progressOfTechBidMap.get(orderLine.getBidVendorId());
//				if (progressOfTechBidVO != null) {
//					orderLine.setTechScore(progressOfTechBidVO.getTechTotalScore());
//				}
//			}
//			List<ScoreRuleLine> scoreRuleLineList = iBidScoreRuleLineService.list(new QueryWrapper<ScoreRuleLine>(new ScoreRuleLine().setBidingId(biding.getBidingId())));
//			BigDecimal pricePercent = new BigDecimal(0);// 价格占比
//			BigDecimal technologyPercent = new BigDecimal(0);// 技术占比（可能配置多个）
//			BigDecimal achievementPercent = new BigDecimal(0);// 绩效占比
//			for (ScoreRuleLine line : scoreRuleLineList) {
//				ScoreDimensionEnum scoreDimensionEnum = ScoreDimensionEnum.get(line.getScoreDimension());
//				if (scoreDimensionEnum == ScoreDimensionEnum.PRICE) {
//					pricePercent = pricePercent.add(new BigDecimal(line.getScoreWeight()));
//				}
//				if (scoreDimensionEnum == ScoreDimensionEnum.TECHNOLOGY) {
//					technologyPercent = technologyPercent.add(new BigDecimal(line.getScoreWeight()));
//				}
//				if (scoreDimensionEnum == ScoreDimensionEnum.ACHIEVEMENT) {
//					achievementPercent = achievementPercent.add(new BigDecimal(line.getScoreWeight()));
//				}
//			}
//			for (OrderLine orderLine : submissionOrderLineList) {
//				BigDecimal compositeScore = new BigDecimal(0);
//				if (orderLine.getTechScore() != null) {
//					compositeScore = compositeScore.add(orderLine.getTechScore().multiply(technologyPercent));
//				}
//				if (orderLine.getPriceScore() != null) {
//					compositeScore = compositeScore.add(orderLine.getPriceScore().multiply(pricePercent));
//				}
//				if (orderLine.getPerfScore() != null) {
//					compositeScore = compositeScore.add(orderLine.getPerfScore().multiply(achievementPercent));
//				}
//				orderLine.setCompositeScore(compositeScore.divide(new BigDecimal(100), 100, RoundingMode.HALF_DOWN).setScale(2, RoundingMode.HALF_DOWN));
//			}
//		}
	}

	@Override
	public void scoreToRank(Biding biding, List<OrderLine> submissionOrderLineList) {
//		Map<Long, List<OrderLine>> requirementLineMap = submissionOrderLineList.stream().collect(Collectors.groupingBy(OrderLine::getRequirementLineId));
//		EvaluateMethodEnum evaluateMethodEnum = EvaluateMethodEnum.get(biding.getEvaluateMethod());
//		for (Long requirementId : requirementLineMap.keySet()) {
//			List<OrderLine> orderLineList = requirementLineMap.get(requirementId);
//			orderLineList.sort((x, y) -> (evaluateMethodEnum == EvaluateMethodEnum.COMPOSITE_SCORE)
//					? x.getCompositeScore().compareTo(y.getCompositeScore())
//					: x.getPriceScore().compareTo(y.getPriceScore())
//			);
//			orderLineList.get(0).setRank(1);
//			for (int i = 1; i < orderLineList.size(); i++) {
//				OrderLine orderLine = orderLineList.get(i);
//				boolean isSameWithLastRank = (evaluateMethodEnum == EvaluateMethodEnum.COMPOSITE_SCORE) ? orderLine.getCompositeScore().compareTo(orderLineList.get(i - 1).getCompositeScore()) == 0 : orderLine.getPriceScore().compareTo(orderLineList.get(i - 1).getPriceScore()) == 0;
//				orderLine.setRank(isSameWithLastRank ? orderLineList.get(i - 1).getRank() : orderLineList.get(i - 1).getRank() + 1);
//			}
//		}
	}

}
