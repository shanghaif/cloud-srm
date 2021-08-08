package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidScoreRuleLineService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.entity.ScoreRuleLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/evaluation/scoreRuleLine")
public class ScoreRuleLineController {

	@Autowired
	private IBidScoreRuleLineService iBidScoreRuleLineService;

	/**
	 * 分页查询
	 * @param scoreRuleLine
	 * @return
	 */
	@PostMapping("/listPage")
	public PageInfo<ScoreRuleLine> listPage(@RequestBody ScoreRuleLine scoreRuleLine) {
		PageUtil.startPage(scoreRuleLine.getPageNum(), scoreRuleLine.getPageSize());
		QueryWrapper<ScoreRuleLine> wrapper = new QueryWrapper<>(scoreRuleLine);
		return new PageInfo<ScoreRuleLine>(iBidScoreRuleLineService.list(wrapper));
	}
}
