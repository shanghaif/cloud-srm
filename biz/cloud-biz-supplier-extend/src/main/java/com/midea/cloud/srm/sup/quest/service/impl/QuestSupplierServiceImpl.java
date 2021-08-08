package com.midea.cloud.srm.sup.quest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.quest.dto.QuestSupplierDto;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestSupplier;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplate;
import com.midea.cloud.srm.model.supplier.quest.enums.QuestSupplierApproveStatusEnum;
import com.midea.cloud.srm.model.supplier.quest.vo.QuestSupplierVo;
import com.midea.cloud.srm.model.workflow.service.IFlowBusinessCallbackService;
import com.midea.cloud.srm.sup.quest.mapper.QuestSupplierMapper;
import com.midea.cloud.srm.sup.quest.mapper.QuestTemplateMapper;
import com.midea.cloud.srm.sup.quest.service.IQuestSupplierService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
 *  修改日期: Apr 16, 2021 5:33:46 PM
 *  修改内容:
 * </pre>
*/
@Service
public class QuestSupplierServiceImpl extends ServiceImpl<QuestSupplierMapper, QuestSupplier> implements IQuestSupplierService, IFlowBusinessCallbackService {
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private QuestTemplateMapper questTemplateMapper;
    @Autowired
    private SupplierClient supplierClient;
    @Transactional
    public void batchUpdate(List<QuestSupplier> questSupplierList) {
        this.saveOrUpdateBatch(questSupplierList);
    }

    @Override
    @Transactional
    public void batchSaveOrUpdate(List<QuestSupplier> questSupplierList) throws IOException {
        for(QuestSupplier questSupplier : questSupplierList){
            if(questSupplier.getQuestSupId() == null){
                Long id = IdGenrator.generate();
                questSupplier.setQuestSupId(id);
            }
        }
        if(!CollectionUtils.isEmpty(questSupplierList)) {
            batchUpdate(questSupplierList);
        }
    }

    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        this.removeByIds(ids);
    }
    @Override
    public PageInfo<QuestSupplierVo> listPageByParm(QuestSupplierDto questSupplierDto) {
        if("Y".equals(questSupplierDto.getOrgCondition())) {
            //过滤组织
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            List<OrganizationUser> organizations = loginAppUser.getOrganizationUsers();
            List<String> questTemplateOrgIdList = organizations.stream().map(organizationUser -> organizationUser.getOrganizationId().toString()).collect(Collectors.toList());
            questSupplierDto.setQuestTemplateOrgIdList(questTemplateOrgIdList);
        }
        PageUtil.startPage(questSupplierDto.getPageNum(), questSupplierDto.getPageSize());
        List<QuestSupplier> questSuppliers = getQuestSuppliers(questSupplierDto);
        List<QuestSupplierVo> questSupplierVoList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(questSuppliers)){
            for (QuestSupplier supplier : questSuppliers) {
                QuestSupplierVo questSupplierVo = new QuestSupplierVo();
                BeanCopyUtil.copyProperties(questSupplierVo,supplier);
                QuestTemplate questTemplate=questTemplateMapper.selectById(supplier.getQuestTemplateId());
                if(questTemplate!=null) {
                    questSupplierVo.setQuestTemplateId(questTemplate.getQuestTemplateId());
                    questSupplierVo.setQuestTemplateCode(questTemplate.getQuestTemplateCode());
                    questSupplierVo.setQuestTemplateName(questTemplate.getQuestTemplateName());
                }
                questSupplierVoList.add(questSupplierVo);
            }
        }
        PageInfo<QuestSupplier> pageResult = new PageInfo<>(questSuppliers);
        PageInfo<QuestSupplierVo> pageInfo=new PageInfo<QuestSupplierVo>();
        pageInfo.setPageNum(pageResult.getPageNum());
        pageInfo.setPageSize(pageResult.getPageSize());
        pageInfo.setList(questSupplierVoList);
        pageInfo.setTotal(pageResult.getTotal());
        return pageInfo;
    }
    public List<QuestSupplier> getQuestSuppliers(QuestSupplierDto questSupplierDto) {
        QueryWrapper<QuestSupplier> wrapper = new QueryWrapper<>();
        wrapper.eq(questSupplierDto.getQuestSupIdForQuery() != null, "QUEST_SUP_ID", questSupplierDto.getQuestSupIdForQuery()); // 调查表编号

        wrapper.eq(StringUtils.isNotBlank(questSupplierDto.getQuestNoForQuery()), "QUEST_NO", questSupplierDto.getQuestNoForQuery()); // 调查表编号

        wrapper.eq(StringUtils.isNotBlank(questSupplierDto.getQuestTemplateOrgId()), "QUEST_TEMPLATE_ORG_ID", questSupplierDto.getQuestTemplateOrgId()); // 所属组织编码
        wrapper.in(CollectionUtils.isNotEmpty(questSupplierDto.getQuestTemplateOrgIdList())," QUEST_TEMPLATE_ORG_ID", questSupplierDto.getQuestTemplateOrgIdList());

        wrapper.eq(questSupplierDto.getCompanyIdForQuery() != null, "COMPANY_ID", questSupplierDto.getCompanyIdForQuery()); // 所属组织编码

        wrapper.in(CollectionUtils.isNotEmpty(questSupplierDto.getApprovalStatusList()),"APPROVAL_STATUS", questSupplierDto.getApprovalStatusList());
        wrapper.ge(questSupplierDto.getCreationDateBegin() != null, "CREATION_DATE", questSupplierDto.getCreationDateBegin());
        wrapper.le(questSupplierDto.getCreationDateEnd() != null, "CREATION_DATE", questSupplierDto.getCreationDateEnd()); // 时间范围查询
//        wrapper.like("TITLE",questSupplier.getTitle());      // 模糊匹配
        wrapper.orderByDesc("CREATION_DATE");
        return this.list(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateQuestSupplierForm(QuestSupplierDto questSupplierDto) {
        List<QuestSupplierDto.CompanyInfo> companyInfoList = questSupplierDto.getCompanyInfoList();
        if (CollectionUtils.isNotEmpty(companyInfoList)) {
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            for (QuestSupplierDto.CompanyInfo companyInfo : companyInfoList) {
                QuestSupplier questSupplier = null;
                if (null == companyInfo.getQuestSupId()) {
                    questSupplier = new QuestSupplier();
                    BeanCopyUtil.copyProperties(questSupplier, questSupplierDto);
                    Long questSupId = IdGenrator.generate();
                    questSupplier.setQuestSupId(questSupId);
                    questSupplier.setQuestNo(baseClient.seqGen(SequenceCodeConstant.SEQ_SUP_QUEST_NO));
                    questSupplier.setCompanyId(companyInfo.getCompanyId());
                    questSupplier.setCompanyCode(companyInfo.getCompanyCode());
                    questSupplier.setCompanyName(companyInfo.getCompanyName());
                    questSupplier.setLcCode(companyInfo.getLcCode());
                    questSupplier.setContactName(companyInfo.getContactName());
                    questSupplier.setCeeaContactMethod(companyInfo.getCeeaContactMethod());
                    questSupplier.setEmail(companyInfo.getEmail());
                    if(QuestSupplierApproveStatusEnum.DRAFT.getValue().equals(questSupplierDto.getOpType())) {
                        questSupplier.setApprovalStatus(QuestSupplierApproveStatusEnum.DRAFT.getValue());
                    }else if(QuestSupplierApproveStatusEnum.PUBLISH.getValue().equals(questSupplierDto.getOpType())) {
                        questSupplier.setApprovalStatus(QuestSupplierApproveStatusEnum.PUBLISH.getValue());
                    }
                    questSupplier.setCreatedFullName(loginAppUser.getNickname());
                    questSupplier.setLastUpdatedFullName(loginAppUser.getNickname());
                } else {
                    questSupplier = this.getById(companyInfo.getQuestSupId());
                    BeanCopyUtil.copyProperties(questSupplier, questSupplierDto);
                    if(QuestSupplierApproveStatusEnum.DRAFT.getValue().equals(questSupplierDto.getOpType())) {
                        questSupplier.setApprovalStatus(QuestSupplierApproveStatusEnum.DRAFT.getValue());
                    }else if(QuestSupplierApproveStatusEnum.PUBLISH.getValue().equals(questSupplierDto.getOpType())) {
                        questSupplier.setApprovalStatus(QuestSupplierApproveStatusEnum.PUBLISH.getValue());
                    }
                    questSupplier.setLastUpdatedFullName(loginAppUser.getNickname());
                }
                this.saveOrUpdate(questSupplier);
            }
        }
    }
    @Override
    public QuestSupplierVo getQuestSupplierVo(Long questSupIdForQuery) {
        QuestSupplier questSupplier = this.getById(questSupIdForQuery);
        QuestSupplierVo questSupplierVo = new QuestSupplierVo();
        BeanCopyUtil.copyProperties(questSupplierVo, questSupplier);
        QuestTemplate questTemplate = questTemplateMapper.selectById(questSupplier.getQuestTemplateId());
        if (questTemplate != null) {
            questSupplierVo.setQuestTemplateId(questTemplate.getQuestTemplateId());
            questSupplierVo.setQuestTemplateCode(questTemplate.getQuestTemplateCode());
            questSupplierVo.setQuestTemplateName(questTemplate.getQuestTemplateName());
        }
        return questSupplierVo;
    }

    @Override
    public void submitFlow(Long businessId, String param) throws Exception {
        QuestSupplier questSupplier = new QuestSupplier();
        questSupplier.setQuestSupId(businessId);
        this.updateById(questSupplier.setApprovalStatus(QuestSupplierApproveStatusEnum.SUBMITTED.getValue()));
    }

    @Override
    public void passFlow(Long businessId, String param) throws Exception {
        QuestSupplier questSupplier = new QuestSupplier();
        questSupplier.setQuestSupId(businessId);
        this.pass(questSupplier);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pass(QuestSupplier questSupplier) {
        this.updateById(new QuestSupplier().setQuestSupId(questSupplier.getQuestSupId()).setApprovalStatus(QuestSupplierApproveStatusEnum.APPROVED.getValue()));

    }

    @Override
    public void rejectFlow(Long businessId, String param) throws Exception {
        QuestSupplier questSupplier = new QuestSupplier();
        questSupplier.setQuestSupId(businessId);
        this.updateById(questSupplier.setApprovalStatus(QuestSupplierApproveStatusEnum.REJECTED.getValue()));
    }

    @Override
    public void withdrawFlow(Long businessId, String param) throws Exception {
        QuestSupplier questSupplier = new QuestSupplier();
        questSupplier.setQuestSupId(businessId);
        this.updateById(questSupplier.setApprovalStatus(QuestSupplierApproveStatusEnum.WITHDRAW.getValue()));
    }

    @Override
    public void destoryFlow(Long businessId, String param) throws Exception {
        QuestSupplier questSupplier = new QuestSupplier();
        questSupplier.setQuestSupId(businessId);
        //用撤回代替
        this.updateById(questSupplier.setApprovalStatus(QuestSupplierApproveStatusEnum.WITHDRAW.getValue()));
    }

    @Override
    public String getVariableFlow(Long businessId, String param) throws Exception {
        QuestSupplier questSupplier = this.getById(businessId);
        return JsonUtil.entityToJsonStr(questSupplier);
    }

    @Override
    public String getDataPushFlow(Long businessId, String param) throws Exception {
        QuestSupplier questSupplier = this.getById(businessId);
        return JsonUtil.entityToJsonStr(questSupplier);
    }
}
