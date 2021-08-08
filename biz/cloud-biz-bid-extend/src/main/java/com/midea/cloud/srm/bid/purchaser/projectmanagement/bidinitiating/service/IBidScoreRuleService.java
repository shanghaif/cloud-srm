package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.evaluation.entity.ScoreRule;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.evaluation.vo.ScoreRuleVO;

/**
 * 
 * 
 * <pre>
 * 评分规则头表
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月28日 上午11:31:06  
 *  修改内容:
 *          </pre>
 */
public interface IBidScoreRuleService extends IService<ScoreRule> {

    void saveScoreRuleAndLine(ScoreRuleVO scoreRuleVO);

    void updateScoreRuleAndLine(ScoreRuleVO scoreRuleVO);
}
