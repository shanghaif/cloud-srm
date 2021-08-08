package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidScoreRuleLineMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidScoreRuleLineService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.entity.ScoreRule;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.entity.ScoreRuleLine;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * 评分规则行表
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年3月28日 上午11:34:02
 *  修改内容:
 *          </pre>
 */
@Service
public class BidScoreRuleLineServiceImpl extends ServiceImpl<BidScoreRuleLineMapper, ScoreRuleLine>
        implements IBidScoreRuleLineService {

    @Override
    @Transactional
    public void saveScoreRuleLineList(List<ScoreRuleLine> scoreRuleLineList, ScoreRule scoreRule) {
        Assert.isTrue(CollectionUtils.isNotEmpty(scoreRuleLineList), "评分规则明细不能为空");

        for (ScoreRuleLine line : scoreRuleLineList) {
            Long id = IdGenrator.generate();
            line.setRuleLineId(id).setRuleId(scoreRule.getRuleId());
        }
        this.saveBatch(scoreRuleLineList);
    }

    @Override
    @Transactional
    public void updateBatch(List<ScoreRuleLine> scoreRuleLineList, ScoreRule scoreRule) {
        Assert.isTrue(CollectionUtils.isNotEmpty(scoreRuleLineList), "评分规则明细不能为空");

        ScoreRuleLine scoreRuleLine = new ScoreRuleLine();
        scoreRuleLine.setRuleId(scoreRule.getRuleId());
        QueryWrapper<ScoreRuleLine> queryWrapper = new QueryWrapper<>(scoreRuleLine);
        List<ScoreRuleLine> oldLineList = this.list(queryWrapper);
        List<Long> oldLineIdList = oldLineList.stream().map(ScoreRuleLine::getRuleLineId).collect(Collectors.toList());
        List<Long> newLineIdList = new ArrayList<>();
        for (ScoreRuleLine line : scoreRuleLineList) {
            Long ruleLineId = line.getRuleLineId();

            //新增
            if (ruleLineId == null) {
                Long id = IdGenrator.generate();
                line.setRuleLineId(id).setRuleId(scoreRule.getRuleId());
                this.save(line);
            } else {
                newLineIdList.add(ruleLineId);
                //更新
                this.updateById(line);
            }
        }
        //删除
        for (Long oldId : oldLineIdList) {
            if(!newLineIdList.contains(oldId)){
                this.removeById(oldId);
            }
        }
    }
}
