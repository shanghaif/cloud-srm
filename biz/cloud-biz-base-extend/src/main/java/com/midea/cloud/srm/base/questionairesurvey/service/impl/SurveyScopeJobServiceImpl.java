package com.midea.cloud.srm.base.questionairesurvey.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;


import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.questionairesurvey.mapper.SurveyScopeJobMapper;
import com.midea.cloud.srm.base.questionairesurvey.service.SurveyScopeJobService;

import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyScopeEmployee;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyScopeJob;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
/**
* <pre>
 *  问卷调查 服务实现类
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 18, 2021 2:57:24 PM
 *  修改内容:
 * </pre>
*/
@Service
public class SurveyScopeJobServiceImpl extends ServiceImpl<SurveyScopeJobMapper, SurveyScopeJob> implements SurveyScopeJobService {


    @Override
    public List<SurveyScopeJob> getJobByQuestionId(List<Long> ids) {
        QueryWrapper wrapper = new QueryWrapper<SurveyScopeJob>();
        wrapper.in("QUESTION_ID",ids);
        return this.list(wrapper);
    }
}
