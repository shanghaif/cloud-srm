package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidScoreRuleConfigMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidScoreRuleConfigService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidScoreRuleLineConfigService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.entity.ScoreRuleConfig;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.entity.ScoreRuleLineConfig;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.vo.ScoreRuleConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <pre>
 *  招标评分规则模板表 服务实现类
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-29 09:39:06
 *  修改内容:
 * </pre>
 */
@Service
public class BidScoreRuleConfigServiceImpl extends ServiceImpl<BidScoreRuleConfigMapper, ScoreRuleConfig> implements IBidScoreRuleConfigService {

    @Autowired
    private BaseClient baseClient;
    @Autowired
    private IBidScoreRuleLineConfigService iBidScoreRuleLineConfigService;

    @Override
    @Transactional
    public void saveScoreRuleAndLineConfig(ScoreRuleConfigVO scoreRuleConfigVO) {
        ScoreRuleConfig scoreRuleConfig = scoreRuleConfigVO.getScoreRuleConfig();
        List<ScoreRuleLineConfig> scoreRuleLineConfigList = scoreRuleConfigVO.getScoreRuleLineConfigList();

        Assert.notNull(scoreRuleConfigVO, "评分规则模板不能为空");

        Long id = IdGenrator.generate();
        scoreRuleConfig.setRuleConfigId(id).setRuleConfigCode(baseClient.seqGen(SequenceCodeConstant.SEQ_BID_SCORE_RULE_CONFIG_CODE));
        this.save(scoreRuleConfig);

        iBidScoreRuleLineConfigService.saveScoreRuleLineConfigList(scoreRuleLineConfigList, scoreRuleConfig);
    }

    @Override
    @Transactional
    public void updateScoreRuleAndLineConfig(ScoreRuleConfigVO scoreRuleConfigVO) {
        ScoreRuleConfig scoreRuleConfig = scoreRuleConfigVO.getScoreRuleConfig();
        List<ScoreRuleLineConfig> scoreRuleLineConfigList = scoreRuleConfigVO.getScoreRuleLineConfigList();
        Assert.notNull(scoreRuleConfig.getRuleConfigId(), "评分规则模板-主键id不能为空");

        this.updateById(scoreRuleConfig);

        iBidScoreRuleLineConfigService.updateBatch(scoreRuleLineConfigList, scoreRuleConfig);
    }

    @Override
    @Transactional
    public void removeScoreRuleConfigAndLineById(Long ruleConfigId) {
        Assert.notNull(ruleConfigId, "评分规则模板id不能为空");

        this.removeById(ruleConfigId);

        QueryWrapper<ScoreRuleLineConfig> ruleLineConfigQueryWrapper
                = new QueryWrapper<ScoreRuleLineConfig>(new ScoreRuleLineConfig().setRuleConfigId(ruleConfigId));
        iBidScoreRuleLineConfigService.remove(ruleLineConfigQueryWrapper);
    }
}
