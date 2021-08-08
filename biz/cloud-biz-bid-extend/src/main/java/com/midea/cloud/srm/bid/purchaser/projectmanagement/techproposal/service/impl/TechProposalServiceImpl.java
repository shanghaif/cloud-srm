package com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.constants.BidConstant;
import com.midea.cloud.common.enums.bid.projectmanagement.bidinitiating.BidType;
import com.midea.cloud.common.enums.bid.projectmanagement.evaluation.OrderStatusEnum;
import com.midea.cloud.common.enums.bid.projectmanagement.evaluation.ScoreDimensionEnum;
import com.midea.cloud.common.enums.bid.projectmanagement.projectpublish.BiddingProjectStatus;
import com.midea.cloud.common.enums.bid.projectmanagement.techproposal.ProgressEnum;
import com.midea.cloud.common.enums.bid.projectmanagement.techproposal.TenderStatusEnum;
import com.midea.cloud.common.enums.bid.projectmanagement.techscore.ScoreStatusEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidScoreRuleLineService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidVendorService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidingService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IGroupService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.businessproposal.service.IRoundService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service.ICalculateTechnologyScoreService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.IOrderHeadService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.ITechProposalService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techscore.service.ITechScoreHeadService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techscore.service.ITechScoreLineService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Group;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.businessproposal.entity.Round;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.evaluation.entity.ScoreRuleLine;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderHead;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.vo.ProgressOfTechBidVO;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.vo.ProgressOfTechScoreVO;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techscore.entity.TechScoreHead;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techscore.entity.TechScoreLine;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techscore.vo.CalculateVendorTechnologyScoreParameter;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techscore.vo.TechScoreLineVO;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techscore.vo.VendorTechnologyScore;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 * 技术标管理
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年3月20日 下午5:33:23
 *  修改内容:
 *          </pre>
 */
@Service
public class TechProposalServiceImpl implements ITechProposalService {

	@Autowired
	private IOrderHeadService iOrderHeadService;

	@Autowired
	private IBidingService iBidingService;

	@Autowired
	private ITechScoreHeadService iTechScoreHeadService;

	@Autowired
	private ITechScoreLineService iTechScoreLineService;

	@Autowired
	private IBidScoreRuleLineService iBidScoreRuleLineService;

	@Autowired
	private IBidVendorService iBidVendorService;

	@Autowired
	private IGroupService iGroupService;

	@Autowired
	private IRoundService iRoundService;

	@Resource
	private ICalculateTechnologyScoreService	calculateTechnologyScoreService;


	@Override
	public String openBidCheck(Long bidingId) {
		Biding biding = iBidingService.getById(bidingId);
		Assert.notNull(biding, "招投标ID不存在");
		Assert.notNull(biding.getCurrentRound(), "未发起投标，不能进行技术开标");
		Assert.isTrue(BidType.get(biding.getBidingType()) == BidType.TECHNOLOGY_BUSINESS, "非技术+商务标，不能进行技术开标");
		Assert.isTrue(!"Y".equals(biding.getTechOpenBid()), "已经进行过技术开标,无需重复执行");
		Round round = iRoundService.getOne(new QueryWrapper<Round>(new Round().setBidingId(bidingId).setRound(BidConstant.FIRST_ROUND)));
		Assert.notNull(round, "招标轮次不存在");
		// 提前结束投标时，校验是否所有报名供应商都已经投标
		if (round.getEndTime().after(new Date())) {
			List<BidVendor> bidVendorList = iBidVendorService.list(new QueryWrapper<BidVendor>(new BidVendor().setBidingId(bidingId).setJoinFlag("Y")));
			List<OrderHead> orderHeadList = iOrderHeadService.list(new QueryWrapper<OrderHead>(new OrderHead().setBidingId(bidingId).setRound(BidConstant.FIRST_ROUND).setOrderStatus(OrderStatusEnum.SUBMISSION.getValue())));
			Set<Long> bidVendorIdSet = bidVendorList.stream().map(BidVendor::getBidVendorId).collect(Collectors.toSet());
			Set<Long> orderHeadBidVendorIdSet = orderHeadList.stream().map(OrderHead::getBidVendorId).collect(Collectors.toSet());
			if (bidVendorList.size() == orderHeadBidVendorIdSet.size() && !bidVendorIdSet.containsAll(orderHeadBidVendorIdSet)) {
				return LocaleHandler.getLocaleMsg("有用户未完成投标是否继续开标？");
			}
		}
		return null;
	}

	@Override
	public void openBid(Long bidingId) {
		Biding biding = iBidingService.getById(bidingId);
		Assert.notNull(biding, "招投标ID不存在");
		Assert.notNull(biding.getCurrentRound(), "未发起投标，不能进行技术开标");
		Assert.isTrue(BidType.get(biding.getBidingType()) == BidType.TECHNOLOGY_BUSINESS, "非技术+商务标，不能进行技术开标");
		Assert.isTrue(!"Y".equals(biding.getTechOpenBid()), "已经进行过技术开标,无需重复执行");
		biding.setBidingStatus(BiddingProjectStatus.TECHNICAL_EVALUATION.getValue()).setTechOpenBid("Y").setTechOpenBidTime(new Date());
		Round round = iRoundService.getOne(new QueryWrapper<Round>(new Round().setBidingId(bidingId).setRound(BidConstant.FIRST_ROUND)));
		Assert.notNull(round, "招标轮次不存在");
		iBidingService.updateById(biding);
		// 提前结束时，将结束时间改成开标时间
		if (round.getEndTime().after(new Date())) {
			round.setEndTime(new Date());
		}
		iRoundService.updateById(round);
	}

	@Override
	public Map<String, Integer> countStatus(Long bidingId) {
		Biding biding = iBidingService.getById(bidingId);
		Assert.notNull(biding, "招投标ID不存在");
		int needToReviewCount = 0, alreadyReviewCount = 0;
		if (BidType.get(biding.getBidingType()) == BidType.TECHNOLOGY_BUSINESS) {
			needToReviewCount = iOrderHeadService.count(new QueryWrapper<OrderHead>(new OrderHead().setBidingId(bidingId).setOrderStatus(OrderStatusEnum.SUBMISSION.getValue()).setRound(BidConstant.FIRST_ROUND)));
			List<TechScoreHead> techScoreHeadList = iTechScoreHeadService.list(new QueryWrapper<TechScoreHead>(new TechScoreHead().setBidingId(bidingId).setScoreStatus(ScoreStatusEnum.FINISHED.getValue())));
			Map<Long, List<TechScoreHead>> techScoreHeadMap = techScoreHeadList.stream().collect(Collectors.groupingBy(TechScoreHead::getBidVendorId));
			List<Group> groupList = iGroupService.list(new QueryWrapper<Group>(new Group().setBidingId(bidingId).setJudgeFlag("Y")));
			Set<Long> groupUserSet = groupList.stream().map(Group::getUserId).collect(Collectors.toSet());
			for (Long bidVendorId : techScoreHeadMap.keySet()) {
				Set<Long> techScoreHeadSet = techScoreHeadMap.get(bidVendorId).stream().map(TechScoreHead::getCreatedId).collect(Collectors.toSet());
				if (groupUserSet.size() == techScoreHeadSet.size() && groupUserSet.containsAll(techScoreHeadSet)) {
					alreadyReviewCount++;
				}
			}
		}
		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put("needToReviewCount", needToReviewCount);
		result.put("alreadyReviewCount", alreadyReviewCount);
		return result;
	}

	@Override
	public List<ProgressOfTechBidVO> queryProgressOfTechBidList(Long bidingId, String vendorName, String progressOfTechBid, boolean needScale) {
		List<ProgressOfTechBidVO> progressOfTechBidList = new ArrayList<ProgressOfTechBidVO>();
		List<BidVendor> vendorList = iBidVendorService.list(new QueryWrapper<BidVendor>(new BidVendor().setBidingId(bidingId).setJoinFlag("Y")).like(StringUtils.isNotBlank(vendorName), "VENDOR_NAME", vendorName));// 报名成功的供应商
		if (vendorList.size() == 0) {
			return progressOfTechBidList;
		}
		List<TechScoreHead> techScoreHeadList = iTechScoreHeadService.list(new QueryWrapper<TechScoreHead>(new TechScoreHead().setBidingId(bidingId).setScoreStatus(ScoreStatusEnum.FINISHED.getValue())));
//		List<Long> techScoreHeadIdList = techScoreHeadList.stream().map(TechScoreHead::getTechScoreHeadId).collect(Collectors.toList());
//		List<TechScoreLine> techScoreLineList = new ArrayList<TechScoreLine>();
//		if (techScoreHeadIdList.size() > 0) {
//			techScoreLineList = iTechScoreLineService.list(new QueryWrapper<TechScoreLine>(new TechScoreLine().setBidingId(bidingId)).in("TECH_SCORE_HEAD_ID", techScoreHeadIdList));
//		}
//		Map<Long, List<TechScoreLine>> techScoreLineMap = techScoreLineList.stream().collect(Collectors.groupingBy(TechScoreLine::getTechScoreHeadId));
		Map<Long, Map<Long, TechScoreHead>> techScoreHeadMap = techScoreHeadList.stream().collect(Collectors.groupingBy(TechScoreHead::getBidVendorId, Collectors.toMap(TechScoreHead::getCreatedId, Function.identity(), (k1, k2) -> k1)));
		List<OrderHead> orderHeadList = iOrderHeadService.list(new QueryWrapper<OrderHead>(new OrderHead().setBidingId(bidingId).setRound(BidConstant.FIRST_ROUND).setOrderStatus(OrderStatusEnum.SUBMISSION.getValue())));
		Map<Long, OrderHead> orderHeadMap = orderHeadList.stream().collect(Collectors.toMap(OrderHead::getBidVendorId, Function.identity()));
//		List<ScoreRuleLine> scoreRuleLineList = iBidScoreRuleLineService.list(new QueryWrapper<ScoreRuleLine>(new ScoreRuleLine().setBidingId(bidingId).setScoreDimension(ScoreDimensionEnum.TECHNOLOGY.getValue())));
		List<Group> groupList = iGroupService.list(new QueryWrapper<Group>(new Group().setBidingId(bidingId).setJudgeFlag("Y")));
		Set<Long> groupUserIdSet = groupList.stream().map(Group::getUserId).collect(Collectors.toSet());// 招标专家小组ID集合
//		BigDecimal totalScoreWeight = new BigDecimal(scoreRuleLineList.stream().collect(Collectors.summarizingInt(ScoreRuleLine::getScoreWeight)).getSum());// 技术评分维度总和
//		Map<Long, ScoreRuleLine> scoreRuleLineMap = scoreRuleLineList.stream().collect(Collectors.toMap(ScoreRuleLine::getRuleLineId, Function.identity()));
		for (BidVendor vendor : vendorList) {
			ProgressOfTechBidVO vo = new ProgressOfTechBidVO();
			vo.setBidVendorId(vendor.getBidVendorId()).setVendorId(vendor.getVendorId()).setVendorName(vendor.getVendorName());
			OrderHead orderHead = orderHeadMap.get(vendor.getBidVendorId());
			vo.setBidDetail(orderHead != null ? orderHead.getBidOrderNum() : null);
			vo.setBidStatus(orderHead != null ? TenderStatusEnum.SUBMITTED.getValue() : TenderStatusEnum.UMSUBMITTED.getValue());
			if (groupUserIdSet.size() > 0) {
				Map<Long, TechScoreHead> vendorTechScoreHeadMap = techScoreHeadMap.get(vendor.getBidVendorId());
				ProgressEnum progressEnum = ProgressEnum.UNFINISHED;
				// 评分已完成
				if (vendorTechScoreHeadMap != null && groupUserIdSet.size() == vendorTechScoreHeadMap.keySet().size() && groupUserIdSet.containsAll(vendorTechScoreHeadMap.keySet())) {
					progressEnum = ProgressEnum.FINISHED;
//					BigDecimal totalScore = new BigDecimal(0);
//					for (Long groupUserId : groupUserIdSet) {
//						TechScoreHead techScoreHead = vendorTechScoreHeadMap.get(groupUserId);
//						List<TechScoreLine> tmpTechScoreLineList = techScoreLineMap.get(techScoreHead.getTechScoreHeadId());
//						Assert.notEmpty(tmpTechScoreLineList, "评分头ID：" + techScoreHead.getTechScoreHeadId() + "对应的评分行数据不存在！");
//						for (TechScoreLine techScoreLine : tmpTechScoreLineList) {
//							ScoreRuleLine scoreRuleLine = scoreRuleLineMap.get(techScoreLine.getRuleLineId());
//							Assert.notNull(scoreRuleLine, "评分规则行ID：" + techScoreLine.getRuleLineId() + "不存在");
//							totalScore = totalScore.add(techScoreLine.getScore().multiply(new BigDecimal(scoreRuleLine.getScoreWeight()).divide(totalScoreWeight, 100, RoundingMode.HALF_DOWN)));
//						}
//					}
//					totalScore = totalScore.divide(new BigDecimal(groupUserIdSet.size()), 100, RoundingMode.HALF_DOWN);// 所有专家评分总和/专家人数
//					if (needScale) {
//						totalScore = totalScore.setScale(2, RoundingMode.HALF_DOWN);
//					}
//					vo.setTechTotalScore(totalScore);
				}
				vo.setProgressOfTechBid(progressEnum.getValue());
			}
			progressOfTechBidList.add(vo);
		}
		// 条件过滤
		if (StringUtils.isNotBlank(progressOfTechBid)) {
			progressOfTechBidList = progressOfTechBidList.stream().filter(p -> p.getProgressOfTechBid().equals(progressOfTechBid.trim())).collect(Collectors.toList());
		}

		/**
		 * 计算 技术得分
		 *
		 * @author zixuan.yan@meicloud.com
		 */
		if (!CollectionUtils.isEmpty(progressOfTechBidList)) {

			// 计算 技术得分（按[投标供应商]获取）
			Map<Long, VendorTechnologyScore> vendorTechnologyScores = calculateTechnologyScoreService.calculate(
					CalculateVendorTechnologyScoreParameter.builder()
							.bidVendorIds(progressOfTechBidList.stream()
									.map(ProgressOfTechBidVO::getBidVendorId)
									.collect(Collectors.toSet())
							)
							.build()
			).stream().collect(Collectors.toMap(VendorTechnologyScore::getBidVendorId, x -> x));

			// 设置技术得分
			progressOfTechBidList.forEach(info -> {

				// 根据[投标供应商]获取 技术得分
				BigDecimal techTotalScore = Optional.ofNullable(vendorTechnologyScores.get(info.getBidVendorId()))
						.map(VendorTechnologyScore::getScoreWithoutWeight)
						.orElse(BigDecimal.ZERO);

				// set.
				info.setTechTotalScore(techTotalScore);
			});
		}

		return progressOfTechBidList;
	}

	@Override
	public List<ProgressOfTechScoreVO> queryProgressOfTechScoreList(Long biddingId, Long bidVendorId) {

		// 获取 技术评分信息[头] & 按[创建人]获取
		Map<Long, TechScoreHead> techScoreHeaders = iTechScoreHeadService
				.list(Wrappers.lambdaQuery(TechScoreHead.class)
						.eq(TechScoreHead::getBidingId, biddingId)
						.eq(TechScoreHead::getBidVendorId, bidVendorId)
				)
				.stream()
				.collect(Collectors.toMap(TechScoreHead::getCreatedId, x -> x));

		// 获取 技术评分信息[行] & 按[评分头]获取
		Map<Long, List<TechScoreLine>> techScoreLines = techScoreHeaders.isEmpty() ? Collections.emptyMap() : iTechScoreLineService
				.list(Wrappers.lambdaQuery(TechScoreLine.class).in(
						TechScoreLine::getTechScoreHeadId,
						techScoreHeaders.values().stream()
								.map(TechScoreHead::getTechScoreHeadId)
								.collect(Collectors.toSet())
				))
				.stream()
				.collect(Collectors.groupingBy(TechScoreLine::getTechScoreHeadId));

		// 获取 评分小组成员
		List<Group> bidGroups = iGroupService.list(
				Wrappers.lambdaQuery(Group.class)
						.eq(Group::getBidingId, biddingId)
						.eq(Group::getJudgeFlag, "Y")
		);

		// 获取 评分规则明细
		Map<Long, ScoreRuleLine> scoreRuleLines = iBidScoreRuleLineService
				.list(Wrappers.lambdaQuery(ScoreRuleLine.class)
						.eq(ScoreRuleLine::getBidingId, biddingId)
						.eq(ScoreRuleLine::getScoreDimension, ScoreDimensionEnum.TECHNOLOGY.getValue())
				)
				.stream()
				.collect(Collectors.toMap(ScoreRuleLine::getRuleLineId, x -> x));

		// 获取 技术评分总维度
		BigDecimal totalTechScoreWeight = BigDecimal.valueOf(
				scoreRuleLines.values().stream()
						.map(ScoreRuleLine::getScoreWeight)
						.reduce(0, Integer::sum)
		);


		return bidGroups.stream().map(bidGroup -> {

			// 创建 VO.
			ProgressOfTechScoreVO techScoreVO = new ProgressOfTechScoreVO()
					.setUserId(bidGroup.getUserId())
					.setUserName(bidGroup.getUserName())
					.setMaxEvaluateScore(bidGroup.getMaxEvaluateScore())
					.setIsFirstResponse(bidGroup.getIsFirstResponse())
					.setEmail(bidGroup.getEmail())
					.setFullName(bidGroup.getFullName())
					.setPhone(bidGroup.getPhone())
					.setPosition(bidGroup.getPosition())
					.setProgressOfTechScore(ScoreStatusEnum.UNFINISHED.getValue());

			// 填充 已评分相关信息
			Optional.ofNullable(techScoreHeaders.get(bidGroup.getUserId()))
					.ifPresent(techScoreHeader -> {

						// 设置 评分头ID
						techScoreVO.setTechScoreHeadId(techScoreHeader.getTechScoreHeadId());
						// 设置 意见
						techScoreVO.setTechComments(techScoreHeader.getTechComments());
						// 设置 状态
						techScoreVO.setProgressOfTechScore(techScoreHeader.getScoreStatus());

						// 非[完成]状态的，不计算技术总得分
						if (!ScoreStatusEnum.FINISHED.getValue().equals(techScoreVO.getProgressOfTechScore()))
							return;

						// 计算 当前评委
						BigDecimal techScore = techScoreLines.getOrDefault(techScoreHeader.getTechScoreHeadId(), Collections.emptyList()).stream()
								.map(techScoreLine -> {

									// 获取 评分明细
									ScoreRuleLine scoreRuleLine = Optional.ofNullable(scoreRuleLines.get(techScoreLine.getRuleLineId()))
											.orElseThrow(() -> new BaseException("获取评分规则明细失败。 | ruleLineId: [" + techScoreLine.getRuleLineId() + "]"));

									// 计算 权重占比
									BigDecimal weight = BigDecimal.ZERO.compareTo(totalTechScoreWeight) == 0
											? BigDecimal.ZERO
											: BigDecimal.valueOf(scoreRuleLine.getScoreWeight()).divide(totalTechScoreWeight, 100, RoundingMode.HALF_DOWN);

									// 获取 技术得分
									return techScoreLine.getScore().multiply(weight);
								})
								.reduce(BigDecimal.ZERO, BigDecimal::add)
								.setScale(2, RoundingMode.HALF_DOWN);

						// 设置 技术评分
						techScoreVO.setScore(techScore);
					});

			return techScoreVO;

		}).collect(Collectors.toList());
	}

	@Override
	public List<TechScoreLineVO> queryTechScoreLineList(Long techScoreHeadId) {
		List<TechScoreLine> techScoreLineList = iTechScoreLineService.list(new QueryWrapper<TechScoreLine>(new TechScoreLine().setTechScoreHeadId(techScoreHeadId)));
		Map<Long, ScoreRuleLine> scoreRuleLineMap = new HashMap<Long, ScoreRuleLine>();
		if (techScoreLineList.size() > 0) {
			List<ScoreRuleLine> scoreRuleLineList = iBidScoreRuleLineService.list(new QueryWrapper<ScoreRuleLine>(new ScoreRuleLine().setBidingId(techScoreLineList.get(0).getBidingId()).setScoreDimension(ScoreDimensionEnum.TECHNOLOGY.getValue())));
			scoreRuleLineMap = scoreRuleLineList.stream().collect(Collectors.toMap(ScoreRuleLine::getRuleLineId, Function.identity()));
		}
		List<TechScoreLineVO> result = new ArrayList<TechScoreLineVO>();
		for (TechScoreLine line : techScoreLineList) {
			ScoreRuleLine scoreRuleLine = scoreRuleLineMap.get(line.getRuleLineId());
			if (scoreRuleLine != null) {
				result.add(new TechScoreLineVO().setRuleLineId(line.getRuleLineId()).setScore(line.getScore()).setScoreItem(scoreRuleLine.getScoreItem()).setScoreStandard(scoreRuleLine.getScoreStandard()).setScoreWeight(scoreRuleLine.getScoreWeight()));
			}
		}
		return result;
	}

}
