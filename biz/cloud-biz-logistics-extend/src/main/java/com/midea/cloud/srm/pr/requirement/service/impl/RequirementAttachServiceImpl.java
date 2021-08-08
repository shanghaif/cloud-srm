package com.midea.cloud.srm.pr.requirement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementAttach;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.pr.requirement.mapper.RequirementAttachMapper;
import com.midea.cloud.srm.pr.requirement.service.IRequirementAttachService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
*  <pre>
 *  采购需求附件表 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-28 11:18:34
 *  修改内容:
 * </pre>
*/
@Service
public class RequirementAttachServiceImpl extends ServiceImpl<RequirementAttachMapper, RequirementAttach> implements IRequirementAttachService {

    @Override
    public void addRequirementAttachBatch(RequirementHead requirementHead, List<RequirementAttach> requirementAttaches) {
        if (CollectionUtils.isNotEmpty(requirementAttaches)) {
            for (int i = 0; i < requirementAttaches.size(); i++) {
                RequirementAttach requirementAttach = requirementAttaches.get(i);
                if (requirementAttach == null) continue;
                saveRequirementAttach(requirementHead, requirementAttach, i + 1);
            }
        }
    }

    private void saveRequirementAttach(RequirementHead requirementHead, RequirementAttach requirementAttach, int rowNum) {
        requirementAttach.setAttachId(IdGenrator.generate())
                .setRequirementHeadId(requirementHead.getRequirementHeadId())
                .setRowNum(rowNum);
        this.save(requirementAttach);
    }

    @Override
    @Transactional
    public void updateRequirementAttachBatch(RequirementHead requirementHead, List<RequirementAttach> requirementAttaches) {
        if (CollectionUtils.isNotEmpty(requirementAttaches)) {
            List<RequirementAttach> oldAttaches = this.list(new QueryWrapper<>(new RequirementAttach().setRequirementHeadId(requirementHead.getRequirementHeadId())));
            List<Long> oldAttachIds = oldAttaches.stream().map(RequirementAttach::getAttachId).collect(Collectors.toList());
            List<Long> newAttachIds = new ArrayList<>();
            for (int i = 0; i < requirementAttaches.size(); i++) {
                RequirementAttach requirementAttach = requirementAttaches.get(i);
                if (requirementAttach == null) continue;
                Long attachId = requirementAttach.getAttachId();
                //新增
                if (attachId == null) {
                    saveRequirementAttach(requirementHead, requirementAttach, i + 1);
                }else {
                    //修改
                    this.updateById(requirementAttach);
                    newAttachIds.add(attachId);
                }
            }
            //删除
            if (CollectionUtils.isNotEmpty(oldAttachIds)) {
                for (Long oldAttachId : oldAttachIds) {
                    if (!newAttachIds.contains(oldAttachId)) {
                        this.removeById(oldAttachId);
                    }
                }
            }
        }
    }
}
