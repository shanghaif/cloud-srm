package com.midea.cloud.srm.sup.quest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.quest.dto.QuestTemplateDto;
import com.midea.cloud.srm.model.supplier.quest.dto.QuestTemplatePropDto;
import com.midea.cloud.srm.model.supplier.quest.dto.QuestTemplatePropGroupDto;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplate;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplateOrg;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplateProp;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplatePropGroup;
import com.midea.cloud.srm.model.supplier.quest.vo.QuestTemplatePropGroupVo;
import com.midea.cloud.srm.model.supplier.quest.vo.QuestTemplatePropVo;
import com.midea.cloud.srm.model.supplier.quest.vo.QuestTemplateVo;
import com.midea.cloud.srm.sup.quest.mapper.QuestTemplateMapper;
import com.midea.cloud.srm.sup.quest.service.IQuestTemplateOrgService;
import com.midea.cloud.srm.sup.quest.service.IQuestTemplatePropGroupService;
import com.midea.cloud.srm.sup.quest.service.IQuestTemplatePropService;
import com.midea.cloud.srm.sup.quest.service.IQuestTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
 *  修改日期: Apr 16, 2021 5:17:12 PM
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class QuestTemplateServiceImpl extends ServiceImpl<QuestTemplateMapper, QuestTemplate> implements IQuestTemplateService {

    @Resource
    private IQuestTemplatePropGroupService iQuestTemplatePropGroupService;

    @Resource
    private IQuestTemplatePropService iQuestTemplatePropService;

    @Autowired
    private BaseClient baseClient;
    @Autowired
    private QuestTemplateMapper questTemplateMapper;
    @Autowired
    private IQuestTemplateOrgService questTemplateOrgService;


    @Transactional
    public void batchUpdate(List<QuestTemplate> questTemplateList) {
        this.saveOrUpdateBatch(questTemplateList);
    }

    @Override
    @Transactional
    public void batchSaveOrUpdate(List<QuestTemplate> questTemplateList) throws IOException {
        for (QuestTemplate questTemplate : questTemplateList) {
            if (questTemplate.getQuestTemplateId() == null) {
                Long id = IdGenrator.generate();
                questTemplate.setQuestTemplateId(id);
            }
        }
        if (CollectionUtils.isNotEmpty(questTemplateList)) {
            batchUpdate(questTemplateList);
        }
    }

    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        this.removeByIds(ids);
    }

    @Override
    public PageInfo<QuestTemplate> listPageByParm(QuestTemplate questTemplate) {
        if (questTemplate == null) return new PageInfo<>();
        PageUtil.startPage(questTemplate.getPageNum(), questTemplate.getPageSize());
        List<QuestTemplate> questTemplates = getQuestTemplates(questTemplate);
        return new PageInfo<>(questTemplates);
    }


    public List<QuestTemplate> getQuestTemplates(QuestTemplate questTemplate) {
        QueryWrapper<QuestTemplate> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(questTemplate.getQuestTemplateName())) {
            wrapper.like("QUEST_TEMPLATE_NAME", questTemplate.getQuestTemplateName());      // 模糊匹配
        }
        if (StringUtils.isNotBlank(questTemplate.getQuestTemplateType())) {
            wrapper.eq("QUEST_TEMPLATE_TYPE", questTemplate.getQuestTemplateType()); // 精准匹配
        }
        wrapper.orderByDesc("CREATION_DATE");
        return this.list(wrapper);
    }

    /**
     * <pre>
     *  查询模板数据
     * </pre>
     *
     * @author yourname@meicloud.com
     * @version 1.00.00
     *
     * <pre>
     *  修改记录
     *  修改后版本:
     *  修改人:
     *  修改日期: ${DATE} ${TIME}
     *  修改内容:
     * </pre>
     */
    @Override
    public QuestTemplateVo getQuestTemplateById(Long templateId) {
        if (null == templateId) {
            throw new BaseException("模板id不能为空");
        }
        QuestTemplateVo questTemplateVo = new QuestTemplateVo();

        QuestTemplate questTemplate = this.baseMapper.selectById(templateId);
        BeanCopyUtil.copyProperties(questTemplateVo, questTemplate);

        QueryWrapper<QuestTemplateOrg> wrapper = new QueryWrapper<>();
        wrapper.eq("QUEST_TEMPLATE_ID", templateId);
        List<QuestTemplateOrg> orgList = questTemplateOrgService.list(wrapper);
        if(CollectionUtils.isNotEmpty(orgList)) {
            questTemplateVo.setQuestTemplateOrgArr(orgList);
            questTemplateVo.setOrganizationIds(orgList.stream().map(questTemplateOrg -> Long.valueOf(questTemplateOrg.getOrgId())).collect(Collectors.toList()));
        }
        List<QuestTemplatePropGroupVo> templatePropGroups = getTemplatePropGroup(templateId);
        if (CollectionUtils.isEmpty(templatePropGroups)) {
            return questTemplateVo;
        }
        questTemplateVo.setQuestTemplateTabArr(templatePropGroups);
        return questTemplateVo;
    }

    /**
     * 保存模板数据
     *
     * @param questTemplateDto
     * @return
     */
    @Transactional
    @Override
    public Long saveQuestTemplateData(QuestTemplateDto questTemplateDto) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Long questTemplateId = questTemplateDto.getQuestTemplateId();
        if (null != questTemplateId) {
            questTemplateDto.setLastUpdatedFullName(loginAppUser.getNickname());
            this.saveOrUpdate(questTemplateDto);
            //删除原先的数据
            removeOldData(questTemplateId);
            //保存标签组
            saveTemplatePropGroup(questTemplateDto, questTemplateId);
            try {
                removeOldOrgData(questTemplateId);
                questTemplateOrgService.batchSaveOrUpdate(questTemplateDto.getQuestTemplateOrgArr());
            } catch (IOException e) {
                e.printStackTrace();
                throw new BaseException("分配组织失败");
            }
            return questTemplateId;
        }
        //保存模板
        Long templateId = IdGenrator.generate();
        questTemplateDto.setQuestTemplateId(templateId)
                .setQuestTemplateCode(baseClient.seqGen(SequenceCodeConstant.SEQ_SUP_QUEST_TEMPLATE_NO));
        questTemplateDto.setQuestTemplateStatus("N");
        questTemplateDto.setCreatedFullName(loginAppUser.getNickname());
        questTemplateDto.setLastUpdatedFullName(loginAppUser.getNickname());
        this.save(questTemplateDto);

        //保存标签组
        saveTemplatePropGroup(questTemplateDto, templateId);
        try {
            if (CollectionUtils.isNotEmpty(questTemplateDto.getQuestTemplateOrgArr())) {
                for (QuestTemplateOrg questTemplateOrg : questTemplateDto.getQuestTemplateOrgArr()) {
                    questTemplateOrg.setQuestTemplateId(templateId);
                }
                questTemplateOrgService.batchSaveOrUpdate(questTemplateDto.getQuestTemplateOrgArr());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return templateId;
    }

    @Override
    public Integer checkUseBySupplier(Long questTemplateId) {
        return questTemplateMapper.checkUseBySupplier(questTemplateId);
    }

    private void removeOldData(Long questTemplateId) {
        QueryWrapper<QuestTemplatePropGroup> groupWrapper = new QueryWrapper<>();
        groupWrapper.eq("QUEST_TEMPLATE_ID", questTemplateId);
        //删除标签组
        iQuestTemplatePropGroupService.remove(groupWrapper);
        QueryWrapper<QuestTemplateProp> wrapper = new QueryWrapper<>();
        wrapper.eq("QUEST_TEMPLATE_ID", questTemplateId);
        //删除字段
        iQuestTemplatePropService.remove(wrapper);
    }

    private void removeOldOrgData(Long questTemplateId) {
        QueryWrapper<QuestTemplateOrg> wrapper = new QueryWrapper<>();
        wrapper.eq("QUEST_TEMPLATE_ID", questTemplateId);
        //删除原来分配的组织
        questTemplateOrgService.remove(wrapper);

    }

    private void saveTemplatePropGroup(QuestTemplateDto questTemplateDto, Long templateId) {
        List<QuestTemplatePropGroupDto> templatePropGroups = questTemplateDto.getQuestTemplateTabArr();
        List<QuestTemplatePropGroup> saveQuestTemplatePropGroup = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(templatePropGroups)) {
            List<String> groupNameList = templatePropGroups.stream().map(QuestTemplatePropGroupDto::getQuestTemplatePropGroupName).collect(Collectors.toList());
            long count = groupNameList.stream().distinct().count();
            if (count < groupNameList.size()) {
                throw new BaseException("存在重复的页签名称，请核对后保存");
            }
            templatePropGroups.forEach(templatePropGroupDto -> {
                long propGroupId = IdGenrator.generate();
                templatePropGroupDto.setQuestTemplateId(templateId)
                        .setQuestTemplatePropGroupCode(baseClient.seqGen(SequenceCodeConstant.SEQ_SUP_QUEST_TEMPLATE_PROP_GROUP_NO))
                        .setQuestTemplatePropGroupId(propGroupId);

                //保存模板字段
                saveTemplateProp(questTemplateDto, templateId, templatePropGroupDto, propGroupId);
                saveQuestTemplatePropGroup.add(templatePropGroupDto);

            });
            iQuestTemplatePropGroupService.saveBatch(saveQuestTemplatePropGroup);
        }
    }

    private void saveTemplateProp(QuestTemplateDto questTemplateDto, Long templateId, QuestTemplatePropGroupDto templatePropGroupDto, long propGroupId) {
        List<QuestTemplatePropDto> questTemplateProps = templatePropGroupDto.getQuestTemplatePropArr();
        List<QuestTemplateProp> saveQuestTemplates = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(questTemplateProps)) {
            List<String> propFieldList = questTemplateProps.stream().map(QuestTemplateProp::getQuestTemplatePropField).collect(Collectors.toList());
            long count = propFieldList.stream().distinct().count();
            if (count < propFieldList.size()) {
                throw new BaseException("页签【" + templatePropGroupDto.getQuestTemplatePropGroupName() + "】下存在重复的字段编码，请核对后保存");
            }
            List<String> propFieldDescList = questTemplateProps.stream().map(QuestTemplateProp::getQuestTemplatePropFieldDesc).collect(Collectors.toList());
            long count2 = propFieldDescList.stream().distinct().count();
            if (count2 < propFieldDescList.size()) {
                throw new BaseException("页签【" + templatePropGroupDto.getQuestTemplatePropGroupName() + "】下存在重复的字段描述，请核对后保存");
            }
            saveQuestTemplates.addAll(questTemplateProps);
        }
        saveQuestTemplates.forEach(questTemplateProp -> {
            questTemplateProp.setQuestTemplateId(templateId)
                    .setQuestTemplateCode(questTemplateDto.getQuestTemplateCode())
                    .setQuestTemplatePropGroupId(propGroupId)
                    .setQuestTemplatePropGroupCode(templatePropGroupDto.getQuestTemplatePropGroupCode())
                    .setQuestTemplatePropId(IdGenrator.generate());
        });

        //保存字段
        iQuestTemplatePropService.saveBatch(saveQuestTemplates);
    }

    /**
     * 获取模板中所有字段
     *
     * @param questTemplateId
     */
    private Map<Long, List<QuestTemplatePropVo>> getTemplateProp(Long questTemplateId) {
        QueryWrapper<QuestTemplateProp> wrapper = new QueryWrapper<>();
        wrapper.eq("QUEST_TEMPLATE_ID", questTemplateId);
        List<QuestTemplateProp> templateProps =
                iQuestTemplatePropService.list(wrapper);

        if (null == templateProps || templateProps.size() < 1) {
            return new HashMap<>();
        }
        Map<Long, List<QuestTemplatePropVo>> templateMap = new HashMap<>();
        List<QuestTemplatePropVo> questTemplatePropVos = new ArrayList<>();
        for (QuestTemplateProp questTemplateProp : templateProps) {

            Long questTemplatePropId = questTemplateProp.getQuestTemplatePropGroupId();
            if (!templateMap.containsKey(questTemplatePropId)) {
                questTemplatePropVos = new ArrayList<>();
            }
            QuestTemplatePropVo questTemplatePropGroupVo = new QuestTemplatePropVo();
            BeanCopyUtil.copyProperties(questTemplatePropGroupVo, questTemplateProp);
            questTemplatePropVos.add(questTemplatePropGroupVo);
            templateMap.put(questTemplatePropId, questTemplatePropVos);
        }
        return templateMap;
    }

    /**
     * 获取模板的所有组
     *
     * @param questTemplateId
     */
    private List<QuestTemplatePropGroupVo> getTemplatePropGroup(Long questTemplateId) {
        QueryWrapper<QuestTemplatePropGroup> wrapper = new QueryWrapper<>();
        wrapper.eq("QUEST_TEMPLATE_ID", questTemplateId);
        List<QuestTemplatePropGroup> templatePropGroup =
                iQuestTemplatePropGroupService.list(wrapper);
        if (null == templatePropGroup || templatePropGroup.size() < 1) {
            return null;
        }

        //模板下的所有字段
        Map<Long, List<QuestTemplatePropVo>> templatePropMap = getTemplateProp(questTemplateId);

        List<QuestTemplatePropGroupVo> questTemplatePropGroupVoList = new ArrayList<>();
        templatePropGroup.stream().forEach(propGroup -> {
            QuestTemplatePropGroupVo questTemplatePropGroupVo = new QuestTemplatePropGroupVo();
            BeanCopyUtil.copyProperties(questTemplatePropGroupVo, propGroup);
            List<QuestTemplatePropVo> questTemplatePropVos = templatePropMap.get(propGroup.getQuestTemplatePropGroupId());
            questTemplatePropGroupVo.setQuestTemplatePropArr(questTemplatePropVos);
            questTemplatePropGroupVoList.add(questTemplatePropGroupVo);
        });
        return questTemplatePropGroupVoList;
    }
}
