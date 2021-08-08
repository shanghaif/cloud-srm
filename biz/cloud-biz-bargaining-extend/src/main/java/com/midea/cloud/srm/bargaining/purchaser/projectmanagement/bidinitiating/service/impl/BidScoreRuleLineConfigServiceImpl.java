//package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.midea.cloud.common.utils.IdGenrator;
//import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.BidScoreRuleLineConfigMapper;
//import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidScoreRuleLineConfigService;
//import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.entity.ScoreRuleConfig;
//import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.entity.ScoreRuleLineConfig;
//import org.apache.commons.collections4.CollectionUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.Assert;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * <pre>
// *  招标评分规则明细模板表 服务实现类
// * </pre>
// *
// * @author fengdc3@meiCloud.com
// * @version 1.00.00
// *
// * <pre>
// *  修改记录
// *  修改后版本:
// *  修改人:
// *  修改日期: 2020-03-29 09:39:07
// *  修改内容:
// * </pre>
// */
//@Service
//public class BidScoreRuleLineConfigServiceImpl extends ServiceImpl<BidScoreRuleLineConfigMapper, ScoreRuleLineConfig> implements IBidScoreRuleLineConfigService {
//
//    @Override
//    @Transactional
//    public void saveScoreRuleLineConfigList(List<ScoreRuleLineConfig> scoreRuleLineConfigList, ScoreRuleConfig scoreRuleConfig) {
//        Assert.isTrue(CollectionUtils.isNotEmpty(scoreRuleLineConfigList), "评分规则明细不能为空");
//
//        for (ScoreRuleLineConfig line : scoreRuleLineConfigList) {
//            Long id = IdGenrator.generate();
//            line.setRuleLineConfigId(id).setRuleConfigId(scoreRuleConfig.getRuleConfigId());
//        }
//        this.saveBatch(scoreRuleLineConfigList);
//    }
//
//    @Override
//    @Transactional
//    public void updateBatch(List<ScoreRuleLineConfig> scoreRuleLineConfigList, ScoreRuleConfig scoreRuleConfig) {
//        Assert.isTrue(CollectionUtils.isNotEmpty(scoreRuleLineConfigList), "评分规则明细不能为空");
//
//        ScoreRuleLineConfig scoreRuleLineConfig = new ScoreRuleLineConfig();
//        scoreRuleLineConfig.setRuleConfigId(scoreRuleConfig.getRuleConfigId());
//        QueryWrapper<ScoreRuleLineConfig> queryWrapper = new QueryWrapper<>(scoreRuleLineConfig);
//        List<ScoreRuleLineConfig> oldLineList = this.list(queryWrapper);
//        List<Long> oldLineIdList = oldLineList.stream().map(ScoreRuleLineConfig::getRuleLineConfigId).collect(Collectors.toList());
//        List<Long> newLineIdList = new ArrayList<>();
//        for (ScoreRuleLineConfig line : scoreRuleLineConfigList) {
//            Long ruleLineId = line.getRuleLineConfigId();
//            //新增
//            if (ruleLineId == null) {
//                Long id = IdGenrator.generate();
//                line.setRuleLineConfigId(id).setRuleConfigId(scoreRuleConfig.getRuleConfigId());
//                this.save(line);
//            } else {
//                newLineIdList.add(ruleLineId);
//                //更新
//                this.updateById(line);
//            }
//        }
//        //删除
//        for (Long oldId : oldLineIdList) {
//            if (!newLineIdList.contains(oldId)) {
//                this.removeById(oldId);
//            }
//        }
//    }
//
//}
