package com.midea.cloud.srm.sup.quest.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;


import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplateProp;
import com.midea.cloud.srm.sup.quest.mapper.QuestTemplatePropMapper;


import com.midea.cloud.srm.sup.quest.service.IQuestTemplatePropService;
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
* @author bing5.wang@midea.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 16, 2021 5:33:01 PM
 *  修改内容:
 * </pre>
*/
@Service
public class QuestTemplatePropServiceImpl extends ServiceImpl<QuestTemplatePropMapper, QuestTemplateProp> implements IQuestTemplatePropService {
    @Transactional
    public void batchUpdate(List<QuestTemplateProp> questTemplatePropList) {
        this.saveOrUpdateBatch(questTemplatePropList);
    }

    @Override
    @Transactional
    public void batchSaveOrUpdate(List<QuestTemplateProp> questTemplatePropList) throws IOException {
        for(QuestTemplateProp questTemplateProp : questTemplatePropList){
            if(questTemplateProp.getQuestTemplatePropId() == null){
                Long id = IdGenrator.generate();
                questTemplateProp.setQuestTemplatePropId(id);
            }
        }
        if(!CollectionUtils.isEmpty(questTemplatePropList)) {
            batchUpdate(questTemplatePropList);
        }
    }

    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        this.removeByIds(ids);
    }
    @Override
    public PageInfo<QuestTemplateProp> listPage(QuestTemplateProp questTemplateProp) {
        PageUtil.startPage(questTemplateProp.getPageNum(), questTemplateProp.getPageSize());
        List<QuestTemplateProp> questTemplateProps = getQuestTemplateProps(questTemplateProp);
        return new PageInfo<>(questTemplateProps);
    }

    public List<QuestTemplateProp> getQuestTemplateProps(QuestTemplateProp questTemplateProp) {
        QueryWrapper<QuestTemplateProp> wrapper = new QueryWrapper<>();
  //      wrapper.eq("NOTICE_ID",questTemplateProp.getNoticeId()); // 精准匹配
//        wrapper.like("TITLE",questTemplateProp.getTitle());      // 模糊匹配
//        wrapper.and(queryWrapper ->
//                queryWrapper.ge("CREATION_DATE",questTemplateProp.getStartDate()).
//                        le("CREATION_DATE",questTemplateProp.getEndDate())); // 时间范围查询
        return this.list(wrapper);
    }
}
