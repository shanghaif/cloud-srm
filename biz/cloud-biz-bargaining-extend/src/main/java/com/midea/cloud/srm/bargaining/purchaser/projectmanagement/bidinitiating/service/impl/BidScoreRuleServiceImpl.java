package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.bargaining.projectmanagement.projectpublish.BiddingApprovalStatus;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.BidScoreRuleMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidScoreRuleLineService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidScoreRuleService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidingService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.entity.ScoreRule;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.entity.ScoreRuleLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.vo.ScoreRuleVO;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

/**
 * 
 * 
 * <pre>
 * 评分规则 服务类
 * </pre>
 * 
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月28日 下午16:34:23
 *  修改内容:
 *          </pre>
 */
@Service
public class BidScoreRuleServiceImpl extends ServiceImpl<BidScoreRuleMapper, ScoreRule> implements IBidScoreRuleService {

    @Autowired
    private IBidScoreRuleLineService iScoreRuleLineService;
    @Autowired
    private IBidingService bidingService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveScoreRuleAndLine(ScoreRuleVO scoreRuleVO) {
        ScoreRule scoreRule = scoreRuleVO.getScoreRule();
        Long bidingId = scoreRule.getBidingId();
        Biding biding = bidingService.getOne(Wrappers.lambdaQuery(Biding.class)
                .select(Biding::getAuditStatus, Biding::getBidingId)
                .eq(Biding::getBidingId, bidingId));
        //审批后不操作
        if(Objects.equals(biding.getAuditStatus(), BiddingApprovalStatus.APPROVED.getValue())){
            return;
        }
        List<ScoreRuleLine> scoreRuleLineList = scoreRuleVO.getScoreRuleLineList();

        Assert.notNull(scoreRuleVO, "评分规则不能为空");

        this.remove(new QueryWrapper<>(new ScoreRule().setBidingId(bidingId)));
        iScoreRuleLineService.remove(new QueryWrapper<>(new ScoreRuleLine().setBidingId(bidingId)));

        Long id = IdGenrator.generate();
        scoreRule.setRuleId(id);
        this.save(scoreRule);

        iScoreRuleLineService.saveScoreRuleLineList(scoreRuleLineList, scoreRule);
    }

    @Override
    @Transactional
    public void updateScoreRuleAndLine(ScoreRuleVO scoreRuleVO) {
        ScoreRule scoreRule = scoreRuleVO.getScoreRule();
        List<ScoreRuleLine> scoreRuleLineList = scoreRuleVO.getScoreRuleLineList();
        Assert.notNull(scoreRule.getRuleId(), "评分规则主键id不能为空");

        this.updateById(scoreRule);

        iScoreRuleLineService.updateBatch(scoreRuleLineList, scoreRule);
    }
}
