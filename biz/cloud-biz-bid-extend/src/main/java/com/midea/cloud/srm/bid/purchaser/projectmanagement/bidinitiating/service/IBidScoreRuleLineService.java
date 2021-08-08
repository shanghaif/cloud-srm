package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.evaluation.entity.ScoreRule;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.evaluation.entity.ScoreRuleLine;

import java.util.List;

/**
 * 
 * 
 * <pre>
 * 评分规则行表
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月28日 上午11:31:33  
 *  修改内容:
 *          </pre>
 */
public interface IBidScoreRuleLineService extends IService<ScoreRuleLine> {

    void saveScoreRuleLineList(List<ScoreRuleLine> scoreRuleLineList, ScoreRule scoreRule);

    void updateBatch(List<ScoreRuleLine> scoreRuleLineList, ScoreRule scoreRule);
}
