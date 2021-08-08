package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techscore.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.bargaining.projectmanagement.evaluation.ScoreDimensionEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.BidScoreRuleLineMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.BidVendorMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techscore.mapper.TechScoreHeadMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techscore.mapper.TechScoreLineMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techscore.service.ITechScoreLineService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.entity.ScoreRuleLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.entity.TechScoreHead;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.entity.TechScoreLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.vo.TechScoreLineVO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 *
 * <pre>
 * 技术评分行表
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月19日 下午7:15:45  
 *  修改内容:
 *          </pre>
 */
@Service
public class TechScoreLineServiceImpl extends ServiceImpl<TechScoreLineMapper, TechScoreLine> implements ITechScoreLineService {

	private final EntityManager<BidVendor>		bidVendorDao
			= EntityManager.use(BidVendorMapper.class);
	private final EntityManager<ScoreRuleLine>	scoreRuleLineDao
			= EntityManager.use(BidScoreRuleLineMapper.class);
	private final EntityManager<TechScoreHead>	techScoreHeaderDao
			= EntityManager.use(TechScoreHeadMapper.class);
	private final EntityManager<TechScoreLine>	techScoreLineDao
			= EntityManager.use(TechScoreLineMapper.class);


	@Override
	public List<TechScoreLineVO> queryTechScoreLineList(Long bidVendorId) {

		// 获取 供应商信息
		BidVendor bidVendor = Optional.ofNullable(bidVendorId)
				.map(bidVendorDao::findById)
				.orElseThrow(() -> new BaseException("获取供应商失败。 | bidVendorId: [" + bidVendorId + "]"));

		// 获取 技术评分规则明细
		Map<Long, ScoreRuleLine> scoreRuleLines = scoreRuleLineDao
				.findAll(Wrappers.lambdaQuery(ScoreRuleLine.class)
						.eq(ScoreRuleLine::getBidingId, bidVendor.getBidingId())
						.eq(ScoreRuleLine::getScoreDimension, ScoreDimensionEnum.TECHNOLOGY.getValue())
				)
				.stream()
				.collect(Collectors.toMap(ScoreRuleLine::getRuleLineId, x -> x, (x, y) -> x));

		// 获取 [当前用户]对该供应商的评分明细
		Map<Long, TechScoreLine> techScoreLines = Optional.ofNullable(techScoreHeaderDao
				.findOne(Wrappers.lambdaQuery(TechScoreHead.class)
						.eq(TechScoreHead::getBidingId, bidVendor.getBidingId())
						.eq(TechScoreHead::getBidVendorId, bidVendor.getBidVendorId())
						.eq(TechScoreHead::getCreatedId, AppUserUtil.getLoginAppUser().getUserId())
				))
				.map(techScoreHeader -> techScoreLineDao.findAll(Wrappers.lambdaQuery(TechScoreLine.class)
						.eq(TechScoreLine::getBidingId, bidVendor.getBidingId())
						.eq(TechScoreLine::getTechScoreHeadId, techScoreHeader.getTechScoreHeadId())
				))
				.orElse(Collections.emptyList())
				.stream()
				.collect(Collectors.toMap(TechScoreLine::getRuleLineId, x -> x, (x, y) -> x));


		// 返回结果
		return scoreRuleLines.values()
				.stream()
				.map(scoreRuleLine -> {

					TechScoreLineVO vo = new TechScoreLineVO()
							.setRuleLineId(scoreRuleLine.getRuleLineId())
							.setScoreItem(scoreRuleLine.getScoreItem())
							.setScoreStandard(scoreRuleLine.getScoreStandard())
							.setScoreWeight(scoreRuleLine.getScoreWeight());

					Optional.ofNullable(techScoreLines.get(scoreRuleLine.getRuleLineId()))
							.ifPresent(techScoreLine -> vo.setScore(techScoreLine.getScore()));

					return vo;
				})
				.collect(Collectors.toList());
	}

}
