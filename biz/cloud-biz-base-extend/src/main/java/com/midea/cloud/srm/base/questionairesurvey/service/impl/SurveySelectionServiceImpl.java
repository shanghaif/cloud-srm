package com.midea.cloud.srm.base.questionairesurvey.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;


import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.questionairesurvey.mapper.SurveySelectionMapper;
import com.midea.cloud.srm.base.questionairesurvey.service.SurveySelectionService;

import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyQuestion;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveySelection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
 *  修改日期: Apr 15, 2021 2:47:16 PM
 *  修改内容:
 * </pre>
*/
@Service
public class SurveySelectionServiceImpl extends ServiceImpl<SurveySelectionMapper, SurveySelection> implements SurveySelectionService {
    @Transactional
    public void batchUpdate(List<SurveySelection> surveySelectionList) {
        this.saveOrUpdateBatch(surveySelectionList);
    }

    @Override
    @Transactional
    public void batchSaveOrUpdate(List<SurveySelection> surveySelectionList) throws IOException {
        for(SurveySelection surveySelection : surveySelectionList){
            if(surveySelection.getSelectionId() == null){
                Long id = IdGenrator.generate();
                surveySelection.setSelectionId(id);
            }
        }
        if(!CollectionUtils.isEmpty(surveySelectionList)) {
            batchUpdate(surveySelectionList);
        }
    }

    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        this.removeByIds(ids);
    }

    @Override
    public List<SurveySelection> getSelectionByQuestionId(List<Long> ids) {
        QueryWrapper wrapper = new QueryWrapper<SurveySelection>();
        wrapper.in("QUESTION_ID",ids);
        return this.list(wrapper);
    }

    @Override
    public void batchDeleteByQuestionId(List<Long> ids) {
        QueryWrapper wrapper = new QueryWrapper<SurveySelection>();
        if(ids.size() != 0) {
            wrapper.in("QUESTION_ID",ids);
            this.remove(wrapper);
        }

    }

    @Override
    public PageInfo<SurveySelection> listPage(SurveySelection surveySelection) {
        PageUtil.startPage(surveySelection.getPageNum(), surveySelection.getPageSize());
        List<SurveySelection> surveySelections = getSurveySelections(surveySelection);
        return new PageInfo<>(surveySelections);
    }

    public List<SurveySelection> getSurveySelections(SurveySelection surveySelection) {
        QueryWrapper<SurveySelection> wrapper = new QueryWrapper<>();
  //      wrapper.eq("NOTICE_ID",surveySelection.getNoticeId()); // 精准匹配
//        wrapper.like("TITLE",surveySelection.getTitle());      // 模糊匹配
//        wrapper.and(queryWrapper ->
//                queryWrapper.ge("CREATION_DATE",surveySelection.getStartDate()).
//                        le("CREATION_DATE",surveySelection.getEndDate())); // 时间范围查询
        return this.list(wrapper);
    }
}
