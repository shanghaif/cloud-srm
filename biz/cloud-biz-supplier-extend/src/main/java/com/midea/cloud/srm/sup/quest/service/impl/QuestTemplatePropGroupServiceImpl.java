package com.midea.cloud.srm.sup.quest.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;


import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplatePropGroup;
import com.midea.cloud.srm.sup.quest.mapper.QuestTemplatePropGroupMapper;


import com.midea.cloud.srm.sup.quest.service.IQuestTemplatePropGroupService;
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
 *  修改日期: Apr 16, 2021 6:29:05 PM
 *  修改内容:
 * </pre>
*/
@Service
public class QuestTemplatePropGroupServiceImpl extends ServiceImpl<QuestTemplatePropGroupMapper, QuestTemplatePropGroup> implements IQuestTemplatePropGroupService {
    @Transactional
    public void batchUpdate(List<QuestTemplatePropGroup> questTemplatePropGroupList) {
        this.saveOrUpdateBatch(questTemplatePropGroupList);
    }

    @Override
    @Transactional
    public void batchSaveOrUpdate(List<QuestTemplatePropGroup> questTemplatePropGroupList) throws IOException {
        for(QuestTemplatePropGroup questTemplatePropGroup : questTemplatePropGroupList){
            if(questTemplatePropGroup.getQuestTemplatePropGroupId() == null){
                Long id = IdGenrator.generate();
                questTemplatePropGroup.setQuestTemplatePropGroupId(id);
            }
        }
        if(!CollectionUtils.isEmpty(questTemplatePropGroupList)) {
            batchUpdate(questTemplatePropGroupList);
        }
    }

    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        this.removeByIds(ids);
    }
    @Override
    public PageInfo<QuestTemplatePropGroup> listPage(QuestTemplatePropGroup questTemplatePropGroup) {
        PageUtil.startPage(questTemplatePropGroup.getPageNum(), questTemplatePropGroup.getPageSize());
        List<QuestTemplatePropGroup> questTemplatePropGroups = getQuestTemplatePropGroups(questTemplatePropGroup);
        return new PageInfo<>(questTemplatePropGroups);
    }

    public List<QuestTemplatePropGroup> getQuestTemplatePropGroups(QuestTemplatePropGroup questTemplatePropGroup) {
        QueryWrapper<QuestTemplatePropGroup> wrapper = new QueryWrapper<>();
  //      wrapper.eq("NOTICE_ID",questTemplatePropGroup.getNoticeId()); // 精准匹配
//        wrapper.like("TITLE",questTemplatePropGroup.getTitle());      // 模糊匹配
//        wrapper.and(queryWrapper ->
//                queryWrapper.ge("CREATION_DATE",questTemplatePropGroup.getStartDate()).
//                        le("CREATION_DATE",questTemplatePropGroup.getEndDate())); // 时间范围查询
        return this.list(wrapper);
    }
}
