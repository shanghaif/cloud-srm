package com.midea.cloud.srm.inq.scoremodel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.inq.scoremodel.mapper.ScoreRuleModelItemMapper;
import com.midea.cloud.srm.inq.scoremodel.service.IScoreRuleModelItemService;
import com.midea.cloud.srm.model.inq.scoremodel.entity.ScoreRuleModelItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 *  评分规则明细表 服务实现类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-19 19:21:24
 *  修改内容:
 * </pre>
 */
@Service
public class ScoreRuleModelItemServiceImpl extends ServiceImpl<ScoreRuleModelItemMapper, ScoreRuleModelItem> implements IScoreRuleModelItemService {

    @Autowired
    private IScoreRuleModelItemService iScoreRuleModelItemService;

    @Override
    public List<ScoreRuleModelItem> getByHeadId(Long modelId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("SCORE_RULE_MODEL_ID", modelId);
        return iScoreRuleModelItemService.list(wrapper);
    }
}
