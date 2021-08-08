package com.midea.cloud.srm.sup.quest.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.quest.dto.QuestResultDto;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestResult;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestSupplier;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplateProp;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplatePropGroup;
import com.midea.cloud.srm.model.supplier.quest.vo.QuestResultVo;
import com.midea.cloud.srm.sup.quest.mapper.QuestResultMapper;
import com.midea.cloud.srm.sup.quest.mapper.QuestTemplatePropGroupMapper;
import com.midea.cloud.srm.sup.quest.mapper.QuestTemplatePropMapper;
import com.midea.cloud.srm.sup.quest.service.IQuestResultService;
import com.midea.cloud.srm.sup.quest.service.IQuestSupplierService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
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
 *  修改日期: Apr 16, 2021 5:34:12 PM
 *  修改内容:
 * </pre>
*/
@Service
public class QuestResultServiceImpl extends ServiceImpl<QuestResultMapper, QuestResult> implements IQuestResultService {
    @Autowired
    private QuestTemplatePropGroupMapper questTemplatePropGroupMapper;
    @Autowired
    private QuestResultMapper questResultMapper;
    @Autowired
    private IQuestSupplierService questSupplierService;
    @Autowired
    private QuestTemplatePropMapper questTemplatePropMapper;
    @Transactional
    public void batchUpdate(List<QuestResult> questResultList) {
        this.saveOrUpdateBatch(questResultList);
    }

    @Override
    @Transactional
    public void batchSaveOrUpdate(List<QuestResult> questResultList) throws IOException {
        for(QuestResult questResult : questResultList){
            if(questResult.getQuestResultId() == null){
                Long id = IdGenrator.generate();
                questResult.setQuestResultId(id);
            }
        }
        if(!CollectionUtils.isEmpty(questResultList)) {
            batchUpdate(questResultList);
        }
    }

    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        this.removeByIds(ids);
    }
    @Override
    public PageInfo<QuestResult> listPage(QuestResult questResult) {
        PageUtil.startPage(questResult.getPageNum(), questResult.getPageSize());
        List<QuestResult> questResults = getQuestResults(questResult);
        return new PageInfo<>(questResults);
    }

    public List<QuestResult> getQuestResults(QuestResult questResult) {
        QueryWrapper<QuestResult> wrapper = new QueryWrapper<>();
  //      wrapper.eq("NOTICE_ID",questResult.getNoticeId()); // 精准匹配
//        wrapper.like("TITLE",questResult.getTitle());      // 模糊匹配
//        wrapper.and(queryWrapper ->
//                queryWrapper.ge("CREATION_DATE",questResult.getStartDate()).
//                        le("CREATION_DATE",questResult.getEndDate())); // 时间范围查询
        wrapper.orderByDesc("CREATION_DATE");
        return this.list(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuestResultDto saveOrUpdateQuestResultForm(QuestResultDto questResultDto) {
        //如果是已填写，则做数据校验
        if ("WRITED".equals(questResultDto.getApprovalStatus())) {
            Set<Long> fillOneLineFlagSet = new HashSet<Long>();
            Map<Long, QuestTemplatePropGroup> propGroupMap = new HashMap<Long, QuestTemplatePropGroup>();
            List<QuestTemplatePropGroup> questTemplatePropGroupList = questTemplatePropGroupMapper.selectList(Wrappers.lambdaQuery(QuestTemplatePropGroup.class).eq(QuestTemplatePropGroup::getQuestTemplateId,questResultDto.getQuestTemplateId()));
            for (QuestTemplatePropGroup questTemplatePropGroup : questTemplatePropGroupList) {
                if ("Y".equals(questTemplatePropGroup.getFillOneLineFlag())) {
                    fillOneLineFlagSet.add(questTemplatePropGroup.getQuestTemplatePropGroupId());
                }
                propGroupMap.put(questTemplatePropGroup.getQuestTemplatePropGroupId(), questTemplatePropGroup);
            }
            if (CollectionUtils.isNotEmpty(questResultDto.getGroupInfoList())) {
                for (QuestResultDto.GroupInfo groupInfo : questResultDto.getGroupInfoList()) {
                    QueryWrapper<QuestTemplateProp> propWrapper = new QueryWrapper<>();
                    propWrapper.eq("QUEST_TEMPLATE_ID", questResultDto.getQuestTemplateId());
                    propWrapper.eq("QUEST_TEMPLATE_PROP_GROUP_ID", groupInfo.getQuestTemplatePropGroupId());
                    propWrapper.eq("ENABLED_FLAG", "Y");
                    propWrapper.eq("EMPTY_FLAG", "Y");
                    List<QuestTemplateProp> propList = questTemplatePropMapper.selectList(propWrapper);
                    //不能为空的字段
                    Map<Long, QuestTemplateProp> propMap = propList.stream().collect(Collectors.toMap(QuestTemplateProp::getQuestTemplatePropId, d -> d));
                    //如果是表单
                    if ("form".equals(groupInfo.getQuestTemplatePropGroupType())) {
                        if (CollectionUtils.isNotEmpty(groupInfo.getFieldInfoList())) {
                            for (QuestResultDto.GroupInfo.FieldInfo fieldInfo : groupInfo.getFieldInfoList()) {
                                if (propMap.get(fieldInfo.getQuestTemplatePropId()) != null && StringUtils.isBlank(fieldInfo.getQuestTemplatePropFieldData())) {
                                    throw new BaseException("页签【" + propGroupMap.get(groupInfo.getQuestTemplatePropGroupId()).getQuestTemplatePropGroupName() + "】的【" + propMap.get(fieldInfo.getQuestTemplatePropId()).getQuestTemplatePropFieldDesc() + "】字段数据不能为空");
                                }
                            }
                        }
                    } else {
                        //如果是表格
                        //如果当前标记是必须有一行
                        if (fillOneLineFlagSet.contains(groupInfo.getQuestTemplatePropGroupId())) {
                            if (CollectionUtils.isNotEmpty(groupInfo.getFieldInfoList())) {
                                QuestResultDto.GroupInfo.FieldInfo fieldInfoTemp = groupInfo.getFieldInfoList().get(0);
                                if (fieldInfoTemp != null) {
                                    //看第一个字段拼接了多个逗号，判断有多少行数据
                                    String[] fieldDataArr = fieldInfoTemp.getQuestTemplatePropFieldData().split(",");
                                    int length = fieldDataArr.length;
                                    //说明有length行数据，拼接每一行的所有字段，判断这行是否为空
                                    for (int i = 0; i < length; i++) {
                                        StringJoiner stringJoiner = new StringJoiner("");
                                        for (QuestResultDto.GroupInfo.FieldInfo fieldInfo : groupInfo.getFieldInfoList()) {
                                            String[] data = fieldInfo.getQuestTemplatePropFieldData().split(",");
                                            String str = data.length > 0 ? data[i] : "";
                                            stringJoiner.add(str);
                                        }
                                        if (StringUtils.isBlank(stringJoiner.toString())) {
                                            throw new BaseException("页签【" + propGroupMap.get(groupInfo.getQuestTemplatePropGroupId()).getQuestTemplatePropGroupName() + "】不能有空行数据");
                                        }
                                    }
                                }
                            }
                        }
                        if (CollectionUtils.isNotEmpty(groupInfo.getFieldInfoList())) {
                            for (QuestResultDto.GroupInfo.FieldInfo fieldInfo : groupInfo.getFieldInfoList()) {
                                if (propMap.get(fieldInfo.getQuestTemplatePropId()) != null) {
                                    String[] data = fieldInfo.getQuestTemplatePropFieldData().split(",");
                                    for (String datum : data) {
                                        if (StringUtils.isBlank(datum)) {
                                            throw new BaseException("页签【" + propGroupMap.get(groupInfo.getQuestTemplatePropGroupId()).getQuestTemplatePropGroupName() + "】的【" + propMap.get(fieldInfo.getQuestTemplatePropId()).getQuestTemplatePropFieldDesc() + "】字段列数据不能有空值");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //先批量删除
        QuestResult questResult = new QuestResult();
        questResult.setQuestSupId(questResultDto.getQuestSupId());
        QueryWrapper<QuestResult> wrapper = new QueryWrapper<>(questResult);
        this.remove(wrapper);
        //再批量插入
        List<QuestResult> batchList = new ArrayList<>();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (CollectionUtils.isNotEmpty(questResultDto.getGroupInfoList())) {
            for (QuestResultDto.GroupInfo groupInfo : questResultDto.getGroupInfoList()) {
                if (CollectionUtils.isNotEmpty(groupInfo.getFieldInfoList())) {
                    for (QuestResultDto.GroupInfo.FieldInfo fieldInfo : groupInfo.getFieldInfoList()) {
                        QuestResult questResultTemp = new QuestResult();
                        Long questResultId = IdGenrator.generate();
                        questResultTemp.setQuestResultId(questResultId);
                        questResultTemp.setQuestSupId(questResultDto.getQuestSupId());
                        questResultTemp.setQuestTemplateId(questResultDto.getQuestTemplateId());
                        questResultTemp.setQuestTemplatePropGroupId(groupInfo.getQuestTemplatePropGroupId());
                        questResultTemp.setQuestTemplatePropId(fieldInfo.getQuestTemplatePropId());
                        questResultTemp.setQuestTemplatePropField(fieldInfo.getQuestTemplatePropField());
                        questResultTemp.setQuestTemplatePropFieldData(fieldInfo.getQuestTemplatePropFieldData());
                        questResultTemp.setQuestTemplatePropFieldLable(fieldInfo.getQuestTemplatePropFieldLable());
                        questResultTemp.setCreatedFullName(loginAppUser.getNickname());
                        questResultTemp.setLastUpdatedFullName(loginAppUser.getNickname());
                        batchList.add(questResultTemp);
                    }
                }
            }
        }
        this.saveBatch(batchList);
        QuestSupplier questSupplier = questSupplierService.getById(questResultDto.getQuestSupId());
        questSupplier.setQuestFeedback(questResultDto.getQuestFeedback());
        questSupplier.setApprovalStatus(questResultDto.getApprovalStatus());
        questSupplier.setLastUpdatedFullName(loginAppUser.getNickname());
        questSupplierService.updateById(questSupplier);
        return questResultDto;
    }

    @Override
    public QuestResultVo getQuestResultVoByQuestSupId(Long questSupId) {
        QuestResultVo questResultVo = new QuestResultVo();
        QuestSupplier questSupplier = questSupplierService.getById(questSupId);
        BeanCopyUtil.copyProperties(questResultVo, questSupplier);
        List<QuestResultVo.GroupInfo> groupInfoList = questResultVo.getGroupInfoList();
        List<QuestTemplatePropGroup> questTemplatePropGroupList = questTemplatePropGroupMapper.queryByQuestSupId(questSupId);
        if (CollectionUtils.isNotEmpty(questTemplatePropGroupList)) {
            for (QuestTemplatePropGroup questTemplatePropGroup : questTemplatePropGroupList) {
                QuestResultVo.GroupInfo groupInfo = new QuestResultVo.GroupInfo();
                groupInfo.setQuestTemplatePropGroupId(questTemplatePropGroup.getQuestTemplatePropGroupId());
                groupInfo.setQuestTemplatePropGroupCode(questTemplatePropGroup.getQuestTemplatePropGroupCode());
                groupInfo.setQuestTemplatePropGroupName(questTemplatePropGroup.getQuestTemplatePropGroupName());
                groupInfo.setQuestTemplatePropGroupType(questTemplatePropGroup.getQuestTemplatePropGroupType());
                List<QuestResultVo.GroupInfo.FieldInfo> fieldInfoList = questResultMapper.queryByQuestSupId(questSupId, questTemplatePropGroup.getQuestTemplatePropGroupId());
                if (CollectionUtils.isNotEmpty(fieldInfoList)) {
                    groupInfo.setFieldInfoList(fieldInfoList);
                }
                groupInfoList.add(groupInfo);
            }
        }
        return questResultVo;
    }
}
