package com.midea.cloud.srm.base.questionairesurvey.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.base.FileFlagEnum;
import com.midea.cloud.common.enums.base.ResultFlagEnum;
import com.midea.cloud.common.enums.bid.projectmanagement.projectpublish.BiddingProjectStatus;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.base.dict.service.IDictItemService;
import com.midea.cloud.srm.base.organization.service.IOrganizationRelationService;
import com.midea.cloud.srm.base.organization.service.IOrganizationUserService;
import com.midea.cloud.srm.base.questionairesurvey.mapper.SurveyHeaderMapper;
import com.midea.cloud.srm.base.questionairesurvey.service.*;
import com.midea.cloud.srm.base.seq.service.ISeqDefinitionService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationRelation;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.*;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.*;
import com.midea.cloud.srm.model.logistics.bid.dto.LgtBidingDto;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtBiding;
import com.midea.cloud.srm.model.rbac.role.entity.Role;
import com.midea.cloud.srm.model.rbac.role.entity.RoleUser;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
* <pre>
 *  测试 服务实现类
 * </pre>
*
* @author linsb@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 29, 2021 10:18:45 AM
 *  修改内容:
 * </pre>
*/
@Service
public class SurveyHeaderServiceImpl extends ServiceImpl<SurveyHeaderMapper, SurveyHeader> implements SurveyHeaderService {
    @Resource
    private SurveyQuestionService surveyQuestionService;

    @Autowired
    private SurveySelectionService surveySelectionService;

    @Autowired
    private SurveyScopeVendorService surveyScopeVendorService;

    @Autowired
    private ISeqDefinitionService iSeqDefinitionService;

    @Autowired
    private SurveyScopeJobService surveyScopeJobService;

    @Autowired
    private SurveyHeaderMapper surveyHeaderMapper;

    @Autowired
    private SurveyScopeEmployeeService surveyScopeEmployeeService;

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private SupplierClient supplierClient;

    @Autowired
    private RbacClient rbacClient;

    @Autowired
    private IOrganizationRelationService iOrganizationRelationService;

    @Autowired
    private IOrganizationUserService iOrganizationUserService;

    @Autowired
    private IDictItemService iDictItemService;

    @Transactional
    public void batchUpdate(List<SurveyHeader> surveyHeaderList) {
        this.saveOrUpdateBatch(surveyHeaderList);
    }

    @Override
    public Long addOrUpdate(SurveyHeader surveyHeader){
        // 校验数据
        checkSurveyHeader(surveyHeader);
        Long surveyHeaderId = surveyHeader.getSurveyId();
        if(StringUtil.isEmpty(surveyHeaderId)){
            //问卷编号
            surveyHeader.setSurveyNum(iSeqDefinitionService.genSequencesNumBase(SequenceCodeConstant.SEQ_BASE_SURVEY_CODE,new Serializable[0]));
            //问卷id
            surveyHeader.setSurveyId(IdGenrator.generate());
            this.save(surveyHeader);
        }else {
            this.updateById(surveyHeader);
        }

        List<Long> surveyQuestionIds = surveyQuestionService.getSurveyQuestionIds(surveyHeaderId);
        // 删除选项
        surveySelectionService.batchDeleteByQuestionId(surveyQuestionIds);
        // 删除问题 员工记录
        if(surveyHeaderId != null) {
            surveyQuestionService.remove(new QueryWrapper<>(new SurveyQuestion().setSurveyId(surveyHeaderId)));
            surveyScopeJobService.remove(new QueryWrapper<>(new SurveyScopeJob().setSurveyId(surveyHeaderId)));
            surveyScopeEmployeeService.remove(new QueryWrapper<>(new SurveyScopeEmployee().setSurveyId(surveyHeaderId)));
        }
        // 保存问题与选项
        List<SurveyQuestionDTO> surveyQuestionDTOList = surveyHeader.getSurveyQuestionDTOList();
        List<SurveySelection> surveySelections = new ArrayList<>();
        List<SurveyScopeJob> surveyScopeJobs = new ArrayList<>();
        List<SurveyScopeEmployee> surveyScopeEmployees = new ArrayList<>();
        // 问题不为空则
        if(CollectionUtils.isNotEmpty(surveyQuestionDTOList)) {
            for(SurveyQuestionDTO surveyQuestionDTO : surveyQuestionDTOList) {
                // 获取问题
                SurveyQuestion surveyQuestion = surveyQuestionDTO.getSurveyQuestion();
                if(surveyQuestion != null) {
                    // 设置id
                    surveyQuestion.setSurveyId(surveyHeader.getSurveyId());
                    Long questionId = IdGenrator.generate();
                    surveyQuestion.setQuestionId(questionId);
                    setUpQuestionSelection(questionId, surveyQuestionDTO.getSurveySelectionList());
                    surveySelections.addAll(surveyQuestionDTO.getSurveySelectionList());

                    //判断是否员工调查
                    if(CollectionUtils.isNotEmpty(surveyQuestionDTO.getJobList())) {
                        if(CollectionUtils.isNotEmpty(surveyQuestionDTO.getJobList())) {
                            List<SurveyScopeEmployee> surveyScopeEmployeeList = setUpEmployee(surveyQuestionDTO.getJobList(),surveyQuestion,surveyHeader);
                            surveyScopeEmployees.addAll(surveyScopeEmployeeList);
                            //设置岗位
                            if(CollectionUtils.isNotEmpty(surveyScopeEmployeeList)){
                                for(SurveyScopeEmployee employee:surveyScopeEmployeeList){
                                    SurveyScopeJob job = new SurveyScopeJob();
                                    Long jobScopeId = IdGenrator.generate();
                                    job.setJobScopeId(jobScopeId);
                                    job.setScopeId(employee.getScopeId());
                                    job.setJob(employee.getRole());
                                    job.setQuestionId(employee.getQuestionId());
                                    job.setSurveyId(employee.getSurveyId());
                                    //设置员工的岗位ID
                                    employee.setJobScopeId(jobScopeId);
                                    surveyScopeJobs.add(job);
                                }
                            }
                        }
                    }
                }
            }
            //保存问题
            List<SurveyQuestion> surveyQuestionList = surveyQuestionDTOList
                    .stream()
                    .map(p-> p.getSurveyQuestion()).collect(Collectors.toList());
            surveyQuestionService.saveBatch(surveyQuestionList);
            // 保存问题选项
            surveySelectionService.saveBatch(surveySelections);
            //保存问题对应的岗位
            surveyScopeJobService.saveBatch(surveyScopeJobs);
            surveyScopeEmployeeService.saveBatch(surveyScopeEmployees);

        }
        return surveyHeader.getSurveyId();
    }

    private List<SurveyScopeEmployee> setUpEmployee(List<String> roleList,SurveyQuestion surveyQuestion,SurveyHeader surveyHeader){
        List<Long> organizationIds = new ArrayList<>();
        List<SurveyScopeEmployee> surveyScopeEmployees = new ArrayList<>();

        Long buId = surveyHeader.getBuId();
        organizationIds.add(buId);
        //业务实体下的库存组织
        List<OrganizationRelation> organizationRelations = iOrganizationRelationService.listChildrenOrganization(buId);
        //递归获取organizationId
        List<Long> ids = new ArrayList<>();
        ids = getOrganizationId(organizationRelations,ids);
        organizationIds.addAll(ids);

        //获取该业务实体对应的员工id
        List<OrganizationUser> organizationUsers = iOrganizationUserService.getUserByOrganizationId(organizationIds);
        List<Long> userIds = organizationUsers.stream().map(user -> user.getUserId()).collect(Collectors.toList());
        //获取角色
        List<Role> roles = rbacClient.getRoleByRoleCodeForAnon(roleList);
        List<Long> roleIds = roles.stream().map(m->m.getRoleId()).collect(Collectors.toList());
        Map<String,List<Long>> map = new HashMap<>();
        map.put("roleIdList",roleIds);
        map.put("userList",userIds);
        List<RoleUser> roleUsers = rbacClient.getUserByRoleId(map);

        //角色对应的员工
        Map<Long, List<RoleUser>> roleMap = roleUsers.stream().collect(Collectors.groupingBy(RoleUser::getRoleId));

        roles.forEach(role -> {
            //该角色对应的员工ID
            if(CollectionUtils.isNotEmpty(roleMap.get(role.getRoleId()))){
                List<Long> userIdList = roleMap.get(role.getRoleId()).stream().map(m -> m.getUserId()).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(userIdList)){
                    //去重
                    List<Long> list = userIdList.stream().distinct().collect(Collectors.toList());
                    UserDto userDto = new UserDto();
                    userDto.setUserIdList(list);
                    userDto.setUserType("BUYER");
                    List<User> users = rbacClient.listUsersByUsreDto(userDto);
                    if(CollectionUtils.isNotEmpty(users)){
                        for(User user:users){
                            SurveyScopeEmployee employee = new SurveyScopeEmployee();
                            employee.setScopeId(IdGenrator.generate());
                            employee.setSurveyId(surveyQuestion.getSurveyId());
                            employee.setEmployeeName(user.getNickname());
                            employee.setQuestionId(surveyQuestion.getQuestionId());
                            employee.setEmployeeJob(user.getCeeaJobcodeDescr());
                            employee.setEmployeeCode(user.getUserId().toString());
                            employee.setRole(role.getRoleCode());
                            surveyScopeEmployees.add(employee);
                        }
                    }else{
                        //提示找不到对应的员工
                        throw new IllegalArgumentException("题目:"+surveyQuestion.getQuestionName()+"设置的岗位范围找不到对应的员工");
                    }
                }
            }else{
                //提示找不到对应的员工
                throw new IllegalArgumentException("题目:"+surveyQuestion.getQuestionName()+"设置的岗位范围找不到对应的员工");
            }


        });
        return surveyScopeEmployees;
    }

    private List<Long> getOrganizationId(List<OrganizationRelation> organizationRelations,List<Long> ids) {
        if(CollectionUtils.isNotEmpty(organizationRelations)){
            for(OrganizationRelation relation:organizationRelations){
                ids.add(relation.getOrganizationId());
                getOrganizationId(relation.getChildOrganRelation(),ids);
            }
        }
        return ids;
    }

    @Override
    public void delteSurveyHeader(Long id) {
        //删除header
        this.removeById(id);
        //删除问题
        List<SurveyQuestion> surveyQuestionList = surveyQuestionService.list(new QueryWrapper<>(new SurveyQuestion().setSurveyId(id)));
        if( surveyQuestionList.size() != 0) {
            List<Long> ids = surveyQuestionList.stream().map(SurveyQuestion::getQuestionId).collect(Collectors.toList());
            surveyQuestionService.removeByIds(ids);
            //删除选项
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.in("QUESTION_ID",ids);
            surveySelectionService.remove(wrapper);
            //删除供应商
            surveyScopeVendorService.remove(new QueryWrapper<>(new SurveyScopeVendor().setSurveyId(id)));
            //删除员工
            surveyScopeEmployeeService.remove(new QueryWrapper<>(new SurveyScopeEmployee().setSurveyId(id)));
            surveyScopeJobService.remove(new QueryWrapper<>(new SurveyScopeJob().setSurveyId(id)));
        }

    }

    @Override
    public SurveyHeader getDetailById(Long surveyHeaderId) {
        Assert.notNull(surveyHeaderId, "参数:surveyHeaderId,不能为空");
        SurveyHeader surveyHeader = this.getById(surveyHeaderId);
        Assert.notNull(surveyHeader,"找不到详情,surveyHeaderId="+surveyHeaderId);
        List<SurveyQuestion> surveyQuestions = surveyQuestionService.list(new QueryWrapper<>(new SurveyQuestion().setSurveyId(surveyHeaderId)));
        List<SurveyQuestionDTO> surveyQuestionDTOS = new ArrayList<>();
        List<Long> ids = surveyQuestions
                .stream()
                .map(p -> p.getQuestionId()).collect(Collectors.toList());
        if(ids.size() != 0) {
            List<SurveyScopeJob> surveyScopeJobs = surveyScopeJobService.getJobByQuestionId(ids);
            Map<Long, List<SurveyScopeJob>> jobMap = surveyScopeJobs
                    .stream()
                    .collect(Collectors.groupingBy(SurveyScopeJob::getQuestionId));
            Map<Long,List<String>> questionRoleMap = new HashMap<>();

            for(Map.Entry<Long,List<SurveyScopeJob>> m :jobMap.entrySet()) {
                Long questionId = m.getKey();
                List<SurveyScopeJob> jobs = m.getValue();
                List<String> role = jobs.stream().map(SurveyScopeJob::getJob).collect(Collectors.toList());
                //去重
                List<String> collect = role.stream().distinct().collect(Collectors.toList());
                questionRoleMap.put(questionId,collect);
            }
            List<SurveySelection> surveySelections = surveySelectionService.getSelectionByQuestionId(ids);
            Map<Long,List<SurveySelection>> map = surveySelections
                    .stream()
                    .collect(Collectors.groupingBy(SurveySelection::getQuestionId));
            for(int i=0; i< surveyQuestions.size(); ++i) {
                SurveyQuestionDTO surveyQuestionDTO = new SurveyQuestionDTO();
                surveyQuestionDTO.setSurveyQuestion(surveyQuestions.get(i));
                Long id = surveyQuestions.get(i).getQuestionId();
                surveyQuestions.get(i).setJobList(questionRoleMap.get(id));
                surveyQuestionDTO.setSurveySelectionList(map.get(id));
                surveyQuestionDTOS.add(surveyQuestionDTO);
            }
        }
        surveyHeader = this.getById(surveyHeaderId);
        surveyHeader.setSurveyQuestionDTOList(surveyQuestionDTOS);
        return surveyHeader;
    }

    public void checkSurveyHeader(SurveyHeader surveyHeader){
        Assert.notNull(surveyHeader.getBuId(),"事业部id不能为空");
        Assert.notNull(surveyHeader.getBuCode(),"事业部编码不能为空");
        Assert.notNull(surveyHeader.getEndDate(),"反馈时间不能为空");
        Assert.notNull(surveyHeader.getFeedbackFlag(),"反馈结果权限范围不能为空");
        if(FileFlagEnum.Y.getValue().equals(surveyHeader.getFileFlag())){
            Assert.hasText(surveyHeader.getFileName(),"附件名称不能为空");
            Assert.hasText(surveyHeader.getFileRelationId(),"附件ID不能为空");
        }
    }

    private void setUpQuestionSelection(Long questionId, List<SurveySelection> surveySelectionList) {
        if(CollectionUtils.isNotEmpty(surveySelectionList)) {
            for(SurveySelection surveySelection : surveySelectionList) {
                surveySelection.setSelectionId(IdGenrator.generate());
                surveySelection.setQuestionId(questionId);
            }
        }
    }


    @Override
    public PageInfo<SurveyHeader> listPage(SurveyHeader surveyHeader) {
        PageUtil.startPage(surveyHeader.getPageNum(), surveyHeader.getPageSize());
        List<SurveyHeader> surveyHeaders = getSurveyHeaders(surveyHeader);
        for(SurveyHeader header:surveyHeaders){
            FeedbackCountDTO feedbackCountDTO = surveyScopeVendorService.queryFeedbackResult(header.getSurveyId());
            if(feedbackCountDTO!=null){
                header.setBackCount(feedbackCountDTO.getBackCount());
                header.setTotalCount(feedbackCountDTO.getTotalCount());
            }
        }
        return new PageInfo<>(surveyHeaders);
    }

    @Override
    public PageInfo<HeaderScopeVendorDto> supplierListPage(HeaderScopeVendorDto headerScopeVendorDto) {
        PageUtil.startPage(headerScopeVendorDto.getPageNum(),headerScopeVendorDto.getPageSize());
        //获取供应商编码
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Assert.notNull(loginAppUser,"找不到用户基础信息");
        Assert.notNull(loginAppUser.getUserType(),"找不到用户类型信息");
        if(UserType.VENDOR.name().equals(loginAppUser.getUserType())){
            if(Objects.isNull(loginAppUser.getCompanyCode())){
                return new PageInfo<>(Collections.emptyList());
            }
            headerScopeVendorDto.setVendorCode(loginAppUser.getCompanyCode());
        }
        List<HeaderScopeVendorDto> headerScopeVendorDtos = surveyHeaderMapper.queryHeaderList(headerScopeVendorDto);
        if(CollectionUtils.isNotEmpty(headerScopeVendorDtos)){
            headerScopeVendorDtos.forEach(this::updateFeedbackEndDatetime);
        }

        return new PageInfo<>(headerScopeVendorDtos);
    }

    /**
     * 根据头表截止时间更新反馈状态
     * @param headerScopeVendorDto
     */
    private void updateFeedbackEndDatetime(HeaderScopeVendorDto headerScopeVendorDto) {
        if(ResultFlagEnum.N.getValue().equals(headerScopeVendorDto.getResultFlag())){
            Date endDate = headerScopeVendorDto.getEndDate();
            if(!ObjectUtils.isEmpty(endDate) && endDate.compareTo(new Date())<0){
                //更新问卷调查状态为已过期
                headerScopeVendorDto.setResultFlag(ResultFlagEnum.EXPIRED.getValue());
                SurveyScopeVendor surveyScopeVendor = new SurveyScopeVendor();
                surveyScopeVendor.setVendorScopeId(headerScopeVendorDto.getVendorScopeId()).setResultFlag(ResultFlagEnum.EXPIRED.getValue());
                surveyScopeVendorService.updateById(surveyScopeVendor);
            }
        }
    }


    public List<SurveyHeader> getSurveyHeaders(SurveyHeader surveyHeader) {
        PageUtil.startPage(surveyHeader.getPageNum(),surveyHeader.getPageSize());
        QueryWrapper<SurveyHeader> wrapper = new QueryWrapper<>();
        wrapper.eq(surveyHeader.getSurveyNum() != null,"SURVEY_NUM",surveyHeader.getSurveyNum());
        wrapper.eq(surveyHeader.getBuId() != null,"BU_ID",surveyHeader.getBuId());
        wrapper.like(surveyHeader.getSurveyTitle() != null,"SURVEY_TITLE",surveyHeader.getSurveyTitle());
        wrapper.eq(surveyHeader.getStatusCode() != null,"STATUS_CODE",surveyHeader.getStatusCode());
        wrapper.eq(surveyHeader.getCreatedBy() != null,"CREATED_BY",surveyHeader.getCreatedBy());
        wrapper.orderByDesc("CREATION_DATE");
        return this.list(wrapper);
    }
}
