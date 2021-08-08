package com.midea.cloud.srm.sup.quest.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;


import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplateOrg;
import com.midea.cloud.srm.sup.quest.mapper.QuestTemplateOrgMapper;


import com.midea.cloud.srm.sup.quest.service.IQuestTemplateOrgService;
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
 *  修改日期: Apr 16, 2021 5:32:42 PM
 *  修改内容:
 * </pre>
*/
@Service
public class QuestTemplateOrgServiceImpl extends ServiceImpl<QuestTemplateOrgMapper, QuestTemplateOrg> implements IQuestTemplateOrgService {
    @Transactional
    public void batchUpdate(List<QuestTemplateOrg> questTemplateOrgList) {
        this.saveOrUpdateBatch(questTemplateOrgList);
    }

    @Override
    @Transactional
    public void batchSaveOrUpdate(List<QuestTemplateOrg> questTemplateOrgList) throws IOException {
        for(QuestTemplateOrg questTemplateOrg : questTemplateOrgList){
            if(questTemplateOrg.getQuestTemplateOrgId() == null){
                Long id = IdGenrator.generate();
                questTemplateOrg.setQuestTemplateOrgId(id);
            }
        }
        if(!CollectionUtils.isEmpty(questTemplateOrgList)) {
            batchUpdate(questTemplateOrgList);
        }
    }

    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        this.removeByIds(ids);
    }
    @Override
    public PageInfo<QuestTemplateOrg> listPage(QuestTemplateOrg questTemplateOrg) {
        PageUtil.startPage(questTemplateOrg.getPageNum(), questTemplateOrg.getPageSize());
        List<QuestTemplateOrg> questTemplateOrgs = getQuestTemplateOrgs(questTemplateOrg);
        return new PageInfo<>(questTemplateOrgs);
    }

    public List<QuestTemplateOrg> getQuestTemplateOrgs(QuestTemplateOrg questTemplateOrg) {
        QueryWrapper<QuestTemplateOrg> wrapper = new QueryWrapper<>();
        wrapper.eq("QUEST_TEMPLATE_ID",questTemplateOrg.getQuestTemplateId()); // 精准匹配
        return this.list(wrapper);
    }
}
