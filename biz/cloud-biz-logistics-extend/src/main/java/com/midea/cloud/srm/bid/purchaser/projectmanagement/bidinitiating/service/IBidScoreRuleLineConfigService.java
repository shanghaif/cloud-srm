package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.entity.ScoreRuleConfig;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.entity.ScoreRuleLineConfig;

import java.util.List;

/**
*  <pre>
 *  招标评分规则明细模板表 服务类
 * </pre>
*
* @author fengdc3@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-29 09:39:07
 *  修改内容:
 * </pre>
*/
public interface IBidScoreRuleLineConfigService extends IService<ScoreRuleLineConfig> {

    void saveScoreRuleLineConfigList(List<ScoreRuleLineConfig> scoreRuleLineConfigList, ScoreRuleConfig scoreRuleConfig);

    void updateBatch(List<ScoreRuleLineConfig> scoreRuleLineConfigList, ScoreRuleConfig scoreRuleConfig);
}
