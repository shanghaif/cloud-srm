package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidScoreRuleService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.evaluation.entity.ScoreRule;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.evaluation.vo.ScoreRuleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * 
 * <pre>
 * 招标评分规则 前端控制器
 * </pre>
 * 
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月28日 下午16:30:59
 *  修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/evaluation/scoreRule")
public class ScoreRuleController {

	@Autowired
	private IBidScoreRuleService iBidScoreRuleService;

	/**
	 * 新增
	 * @param scoreRuleVO
	 */
	@PostMapping("/add")
	public void add(@RequestBody ScoreRuleVO scoreRuleVO) {
		iBidScoreRuleService.saveScoreRuleAndLine(scoreRuleVO);
	}

	/**
	 * 修改
	 *
	 * @param scoreRuleVO
	 */
	@PostMapping("/modify")
	public void modify(@RequestBody ScoreRuleVO scoreRuleVO) {
		iBidScoreRuleService.updateScoreRuleAndLine(scoreRuleVO);
	}

	/**
	 * 根据招标id获取
	 *
	 * @param bidingId
	 */
	@GetMapping("/getByBidingId")
	public ScoreRule getByBidingId(Long bidingId) {
		Assert.notNull(bidingId, "招标id不能为空");
		return iBidScoreRuleService.getOne(new QueryWrapper<ScoreRule>(new ScoreRule().setBidingId(bidingId)));
	}
}
