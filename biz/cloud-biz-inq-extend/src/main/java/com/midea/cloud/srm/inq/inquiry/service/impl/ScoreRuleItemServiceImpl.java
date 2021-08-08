package com.midea.cloud.srm.inq.inquiry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.inq.inquiry.mapper.ScoreRuleItemMapper;
import com.midea.cloud.srm.inq.inquiry.service.IScoreRuleItemService;
import com.midea.cloud.srm.model.inq.inquiry.entity.ScoreRuleItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  <pre>
 *  招标评分规则表 服务实现类
 * </pre>
*
* @author zhongbh
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 18:46:55
 *  修改内容:
 * </pre>
*/
@Service
public class ScoreRuleItemServiceImpl extends ServiceImpl<ScoreRuleItemMapper, ScoreRuleItem> implements IScoreRuleItemService {

    @Autowired
    private IScoreRuleItemService iScoreRuleItemService;

    @Override
    public List<ScoreRuleItem> getByRuleId(Long ruleId) {
        QueryWrapper warapper = new QueryWrapper();
        warapper.eq("SCORE_RULE_ID", ruleId);
        return iScoreRuleItemService.list(warapper);
    }
}
