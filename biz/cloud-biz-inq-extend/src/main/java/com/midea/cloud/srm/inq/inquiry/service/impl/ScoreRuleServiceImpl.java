package com.midea.cloud.srm.inq.inquiry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.inq.inquiry.mapper.ScoreRuleMapper;
import com.midea.cloud.srm.inq.inquiry.service.IScoreRuleService;
import com.midea.cloud.srm.model.inq.inquiry.entity.ScoreRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ScoreRuleServiceImpl extends ServiceImpl<ScoreRuleMapper, ScoreRule> implements IScoreRuleService {

    @Autowired
    private IScoreRuleService iScoreRuleService;

    @Override
    public ScoreRule getByHeadId(Long inquiryId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("INQUIRY_ID", inquiryId);
        ScoreRule one = iScoreRuleService.getOne(wrapper);
        return one;
    }
}
