package com.midea.cloud.flow.workflow.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.enums.Enable;
import com.midea.cloud.common.enums.flow.CbpmEventTypeEnum;
import com.midea.cloud.common.enums.flow.CbpmOperationTypeEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.component.filter.HttpServletHolder;
import com.midea.cloud.flow.common.constant.FlowCommonConst;
import com.midea.cloud.flow.common.constant.WorkFlowConst;
import com.midea.cloud.flow.common.enums.CbpmProcessStatuseEnum;
import com.midea.cloud.flow.common.method.WorkFLowCommonMethod;
import com.midea.cloud.flow.utils.CommonReflectMethod;
import com.midea.cloud.flow.workflow.service.*;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.flow.cbpm.*;
import com.midea.cloud.srm.model.flow.process.dto.ApproveProcessDTO;
import com.midea.cloud.srm.model.flow.process.dto.CbpmRquestParamDTO;
import com.midea.cloud.srm.model.flow.process.dto.ChangeProcessSubjectDTO;
import com.midea.cloud.srm.model.flow.process.dto.TemplateHeaderDTO;
import com.midea.cloud.srm.model.flow.process.entity.FlowinstanceEvent;
import com.midea.cloud.srm.model.flow.process.entity.TemplateHeader;
import com.midea.cloud.srm.model.flow.process.entity.TemplateInstance;
import com.midea.cloud.srm.model.flow.process.entity.TemplateLines;
import com.midea.cloud.srm.model.flow.query.dto.FlowProcessQueryDTO;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * <pre>
 *  工作流Service接口实现类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/16 16:15
 *  修改内容:
 * </pre>
 */
@Service
public class WorkFlowServiceImpl implements IWorkFlowService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkFlowServiceImpl.class);

    /**cbpm流程接口Service*/
    @Resource
    private ICbpmWorkFlowService iCbpmWorkFlowService;
    /**流程实例Service接口*/
    @Resource
    private ITemplateInstanceService iTemplateInstanceService;
    /**流程模板头Service接口*/
    @Resource
    private ITemplateHeaderService iTemplateHeaderService;

    /**用户控制中心内部调用类*/
    @Resource
    private RbacClient rbacClient;

    /**流程事件表服务类*/
    @Resource
    private IFlowinstanceEventService iFlowinstanceEventService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> initProcessAndSaveInstance(CbpmRquestParamDTO cbpmRquestParam) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message", ResultCode.OPERATION_FAILED.getMessage());

        //数据校验
        Assert.notNull(cbpmRquestParam, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        Long businessId = (StringUtils.isNotBlank(cbpmRquestParam.getBusinessId()) ? Long.parseLong(cbpmRquestParam.getBusinessId()) : null);
        Assert.notNull(businessId, WorkFlowConst.FORM_PARAM_NOT_NULL);
        String subject = cbpmRquestParam.getSubject();
        Assert.notNull(subject, WorkFlowConst.SUBUJECT_NOT_NULL);

        //根据流程模板编码获取模板ID
        String templateCode = cbpmRquestParam.getTemplateCode();
        Assert.notNull(templateCode, WorkFlowConst.TEMPLATE_CODE_NOT_NULL);
        TemplateHeader templateHeader = new TemplateHeader();
        templateHeader.setTemplateCode(templateCode);
        QueryWrapper<TemplateHeader> headerQueryWrapper = new QueryWrapper<>(templateHeader);
        List<TemplateHeader> templateHeaderList = iTemplateHeaderService.list(headerQueryWrapper);
        String modelId = "";
        if(!CollectionUtils.isEmpty(templateHeaderList) && null != templateHeaderList.get(0)){
            modelId = templateHeaderList.get(0).getModelId();
        }
        Assert.notNull(modelId, WorkFlowConst.BUDINESS_KEY_NOT_NULL);

        //获取用户信息
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String loginName = "";
        Long userId = null;
        String nickName = "";
        if(null != loginAppUser){
            loginName = loginAppUser.getUsername();
            userId = loginAppUser.getUserId();
            nickName = loginAppUser.getNickname();
        }
        Assert.notNull(loginName, WorkFlowConst.LOGIN_NAME_NOT_NULL);
        Assert.notNull(userId, WorkFlowConst.USER_ID_NOT_NULL);

        Boolean notInitProcess = false;  //是否是初始化流程为false(如果流程状态为已拟定或驳回时，不需要实例化流程即调用iCbpmWorkFlowService.initProcess方法)

        String fdId = cbpmRquestParam.getFdId();
        if(StringUtils.isNotBlank(fdId)){
            FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
            flowProcessQuery.setFdId(fdId);
            flowProcessQuery.setLoginName(loginName);
            Map<String, Object> processStatusMap = iCbpmWorkFlowService.getProcessStatus(flowProcessQuery);
            String fdStatusCode = (null != processStatusMap ? String.valueOf(processStatusMap.get("fdStatusCode")) : "");
            //流程状态为草稿或驳回时，不需实例化流程
            if(null != processStatusMap && StringUtils.isNotBlank(fdStatusCode)
                    && (CbpmProcessStatuseEnum.REFUSE_STATUS.getKey().equals(fdStatusCode) || CbpmProcessStatuseEnum.DRAFT_STATUS.getKey().equals(fdStatusCode))){
                notInitProcess = true;
            }
        }

        //先判断流程是否存在，并状态为拟定、驳回，不是则实例化流程；或流程ID为空时
        if(!notInitProcess || StringUtils.isBlank(fdId)) {
            //实例化流程
            FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
            flowProcessQuery.setLoginName(loginName);
            flowProcessQuery.setFdTemplateId(modelId);
            Long initProcesstart = System.currentTimeMillis();
            fdId = iCbpmWorkFlowService.initProcess(flowProcessQuery);
            Long initProcessEnd = System.currentTimeMillis();
            LOGGER.info("初始化流程时initProcess hours" + (initProcessEnd - initProcesstart));

            if(StringUtils.isBlank(fdId)){
                resultMap.put("message", WorkFlowConst.TEMPLATE_INSTANCE_NOT_NULL);
            }else {
                //插入流程实例记录
                Date nowDate = new Date();
                TemplateInstance templateInstanceNew = new TemplateInstance();
                templateInstanceNew.setInstanceId(IdGenrator.generate());
                templateInstanceNew.setBusinessId(businessId);
                templateInstanceNew.setTemplateCode(modelId);
                templateInstanceNew.setCbpmInstanceId(Long.parseLong(fdId));
                templateInstanceNew.setModelId(Long.parseLong(modelId));
                templateInstanceNew.setFlowStatus(CbpmOperationTypeEnum.INSTANCE_ID.getKey());
                templateInstanceNew.setVersion(FlowCommonConst.VERSION_0);
                templateInstanceNew.setCreatedId(userId);
                templateInstanceNew.setCreatedByIp(IPUtil.getRemoteIpAddr(HttpServletHolder.getRequest()));
                templateInstanceNew.setLoginName(loginName);
                templateInstanceNew.setCreatedBy(loginName);
                templateInstanceNew.setCreationDate(nowDate);
                templateInstanceNew.setCreatedFullName(nickName);
                templateInstanceNew.setLastUpdatedBy(loginName);
                templateInstanceNew.setLastUpdateDate(nowDate);
                templateInstanceNew.setAttribute1("initProcess hours: " + (initProcessEnd - initProcesstart));
                try {
                    iTemplateInstanceService.save(templateInstanceNew);
                }catch (Exception e){
                    LOGGER.error("初始化流程"+"模板Id"+modelId+",表ID"+businessId+"时报错：{}",e);
                }

            }
        }
        resultMap.put("templateCode",  cbpmRquestParam.getTemplateCode());
        resultMap.put("businessId",  businessId);
        resultMap.put("subject", subject);
        resultMap.put("modelId", modelId);
        resultMap.put("fdId", fdId);
        resultMap.put("message", ResultCode.SUCCESS.getMessage());

        return resultMap;
    }



    @Override
    public Map<String, Object> getProcessNodesInfo(FlowProcessQueryDTO flowProcessQuery) throws BaseException {
        Map<String, Object> processNodesInfoMap = new HashMap<>();
        if(null != flowProcessQuery){
            try {
                processNodesInfoMap = iCbpmWorkFlowService.getProcessNodesInfo(flowProcessQuery);
            }catch (Exception e){
                throw new BaseException(e.toString());
            }
            if(null != processNodesInfoMap){
                String nodeType = (null != processNodesInfoMap.get("nodeType")
                        ? String.valueOf(processNodesInfoMap.get("nodeType")) : "");
                processNodesInfoMap.put("nodeDesc", WorkFLowCommonMethod.getNodeTypeByValue(nodeType));
            }
        }
        return processNodesInfoMap;
    }

    @Override
    public boolean getFlowEnable(Long menuId, Long functionId, String templateCode) throws BaseException{
        boolean result = false;
        Assert.notNull(menuId, WorkFlowConst.MENU_ID_NOT_NULL);
        Assert.notNull(functionId, WorkFlowConst.FUNCTION_ID__NOT_NULL);
        Assert.notNull(templateCode, WorkFlowConst.TEMPLATE_CODE_NOT_NULL);

        //获取是否启动工作流
        Permission permission = new Permission();
        permission.setEnableWorkFlow(Enable.Y.toString());
        permission.setFunctionId(functionId);
        permission.setPermissionId(menuId);
        try {
            List<Permission> permissionList = rbacClient.queryEnablePermission(permission);
            if (!CollectionUtils.isEmpty(permissionList) && null != permissionList.get(0)) {
                TemplateHeader queryTemplateHeader = new TemplateHeader();
                queryTemplateHeader.setEnableFlag(Enable.Y.toString());
                queryTemplateHeader.setTemplateCode(templateCode);
                List<TemplateHeader> templateHeaderList = iTemplateHeaderService.list(new QueryWrapper<>(queryTemplateHeader));
                if (!CollectionUtils.isEmpty(templateHeaderList) && null != templateHeaderList.get(0)) {
                    result = true;
                }
            }
        }catch (Exception e){
            LOGGER.error("判断是否启动工作流时,参数：菜单ID{},功能ID{},流程模板Code{},报错：",menuId,functionId,templateCode,e);
            throw new BaseException("判断是否启动工作流时报错");
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> saveDraftDirectly(CbpmRquestParamDTO cbpmRquestParamDTO) throws Exception {
        //判断数据
        if(null == cbpmRquestParamDTO){
            Assert.isNull(ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        }
        String businessKey = cbpmRquestParamDTO.getBusinessKey();
        Long businessId = StringUtils.isNotBlank(cbpmRquestParamDTO.getBusinessId()) ?
                Long.parseLong(cbpmRquestParamDTO.getBusinessId()) : null ;
        String fdId = cbpmRquestParamDTO.getFdId();
        Assert.notNull(businessKey, WorkFlowConst.BUDINESS_KEY_NOT_NULL);
        Assert.notNull(businessId, WorkFlowConst.BUSINESS_ID_NOT_NULL);
        Assert.notNull(fdId, WorkFlowConst.TEMPLATE_INSTANCE_NOT_NULL);
        Map<String, Object> cbpmFormMap = cbpmRquestParamDTO.getCbpmFormMap();

        Long getFormDataStart = System.currentTimeMillis();
        //获取自己定义表单信息
        Map<String, Object> myFormMap = iCbpmWorkFlowService.getProcessCustomCallBack(businessId,businessKey, CbpmOperationTypeEnum.GET_FORM_DATA.getKey(),fdId);
        Long getFormDataEnd = System.currentTimeMillis();
        LOGGER.info("getFormData hours" + (getFormDataEnd - getFormDataStart));

        Map<String, Object> formParam = new HashMap<>();
        if(null == myFormMap){
            myFormMap = new HashMap<>();
        }else{

        }
        myFormMap.put("fdId", cbpmRquestParamDTO.getFdId());
        myFormMap.put("formData", cbpmFormMap);
        formParam.put("formParam", myFormMap);

        //cbpm那边的表单信息，现在先手动配置
//        cbpmFormData.put("day", "5");

        Long createProcessDraftStart = System.currentTimeMillis();
        Map<String, Object> oparetionList = iCbpmWorkFlowService.saveDraftDirectly(cbpmRquestParamDTO, formParam);
        Long createProcessDraftEnd = System.currentTimeMillis();
        LOGGER.info("createProcessDraft hours" + (createProcessDraftEnd - createProcessDraftStart));

        //拼接默认审批人规则
//        appendApproveUsers(mapFormData, cdpmRquestParamDTO.getOrgCode(), cdpmRquestParamDTO.getLoginName());

        //插入流程实例记录
/*        Date nowDate = new Date();
        TemplateInstance templateInstanceNew = new TemplateInstance();
        templateInstanceNew.setInstanceId(IdGenrator.generate());
        templateInstanceNew.setBusinessId(businessId);
        templateInstanceNew.setTemplateCode(businessKey);
        templateInstanceNew.setCbpmInstanceId(fdId);
        templateInstanceNew.setModelId(null!= fdTemplateId ? Long.parseLong(fdTemplateId) : null);
        templateInstanceNew.setLoginName(cbpmRquestParamDTO.getLoginName());
        templateInstanceNew.setFlowStatus(CbpmOperationTypeEnum.DRAFT.getKey());
        templateInstanceNew.setVersion(FlowCommonConst.VERSION_0);
        templateInstanceNew.setCreatedId(userId);
        templateInstanceNew.setCreatedByIp(IPUtil.getRemoteIpAddr(HttpServletHolder.getRequest()));
        templateInstanceNew.setCreatedBy(loginName);
        templateInstanceNew.setCreationDate(nowDate);
        templateInstanceNew.setCreatedFullName(nickName);
        templateInstanceNew.setLastUpdatedBy(loginName);
        templateInstanceNew.setLastUpdateDate(nowDate);
        templateInstanceNew.setAttribute4("getFormData hours: " + (getFormDataEnd - getFormDataStart));
        templateInstanceNew.setAttribute5("createProcessDraft hours: " + (createProcessDraftEnd - createProcessDraftStart));
        iTemplateInstanceService.updateById(templateInstanceNew);*/

        return oparetionList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> approveProcess(CbpmRquestParamDTO cbpmRquestParamDTO) throws Exception {
        Assert.notNull(cbpmRquestParamDTO, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());

        String fdId = cbpmRquestParamDTO.getFdId();
        String businessId = cbpmRquestParamDTO.getBusinessId();
        String businessKey = cbpmRquestParamDTO.getBusinessKey();
        String subject = cbpmRquestParamDTO.getSubject();
        String operationType = cbpmRquestParamDTO.getOperationType(); //审批人流程处理方式
        Assert.notNull(fdId, WorkFlowConst.BUSINESS_ID_NOT_NULL);
        Assert.notNull(businessId, WorkFlowConst.BUSINESS_ID_NOT_NULL);
        Assert.notNull(businessKey, WorkFlowConst.BUDINESS_KEY_NOT_NULL);
        //不是草稿操作时才operationType才必填
        if(!CbpmProcessStatuseEnum.DRAFT_STATUS.getKey().equals(operationType)) {
            Assert.notNull(operationType, WorkFlowConst.OPERATION_TYPE_NOT_NULL);
        }else{ //草稿状态是主题必填
            Assert.notNull(subject, WorkFlowConst.SUBUJECT_NOT_NULL);
        }

        //根据fdId获取当前操作人
        String loginName ="";
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(null != loginAppUser){
            loginName = loginAppUser.getUsername();
        }
        if(StringUtils.isBlank(loginName)){
            Assert.notNull(loginName,WorkFlowConst.LOGIN_NAME_NOT_NULL);
        }

        //根据流程模板ID获取functionCode
        String functionCode = "";
        TemplateHeader queryTemplateHeader = new TemplateHeader();
        queryTemplateHeader.setDeleteFlag(FlowCommonConst.DELETE_FLAG_NO);
        queryTemplateHeader.setModelId(businessKey);
        List<TemplateHeader> templateHeaderList = iTemplateHeaderService.list(new QueryWrapper<>(queryTemplateHeader));
        if(!CollectionUtils.isEmpty(templateHeaderList) && null != templateHeaderList.get(0)){
            functionCode = templateHeaderList.get(0).getTemplateCode();
        }
        Assert.notNull(functionCode, WorkFlowConst.FORM_TEMPLATE_ID);

        //根据流程ID获取taskId
        String taskId = null;  //任务Id
        String activetyType = null; //节点任务类型
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        flowProcessQuery.setLoginName(loginName);
        flowProcessQuery.setFdId(fdId);
        List operationList = null;
        try {
            operationList = iCbpmWorkFlowService.getOperationList(flowProcessQuery);
            if (!CollectionUtils.isEmpty(operationList)) {
                JSONArray operationListJsonObjcet = (JSONArray) operationList;
                List<CbpmOperationListDTO> cbpmOperationListDTOList = JSONObject.parseArray(operationListJsonObjcet.toJSONString(), CbpmOperationListDTO.class);
                if (!CollectionUtils.isEmpty(cbpmOperationListDTOList)) {
                    for (CbpmOperationListDTO cbpmOperationListDTO : cbpmOperationListDTOList) {
                        String roleType = (null != cbpmOperationListDTO ? cbpmOperationListDTO.getRoleType(): ""); //角色类型（审批人：approve，起草人：draft）
                        //审批流程相关节点
                        if(CbpmOperationTypeEnum.HANDLER_RETURN_COMMUNICATE.getKey().equals(operationType)
                                || CbpmOperationTypeEnum.HANDLER_CANCEL_COMMUNICATE.getKey().equals(operationType)
                                || CbpmOperationTypeEnum.HANDLER_PASS.getKey().equals(operationType)
                                || CbpmOperationTypeEnum.HANDLER_REFUSE.getKey().equals(operationType)
                           || CbpmOperationTypeEnum.HANDLER_COMMUNICATE.getKey().equals(operationType)
                                || CbpmOperationTypeEnum.HANDLER_COMMISSION.getKey().equals(operationType)){
                            if (null != cbpmOperationListDTO && "approve".equals(roleType) && !CollectionUtils.isEmpty(cbpmOperationListDTO.getOperationList())) {
                                List<CbpmOperationDTO> operationDTOList = cbpmOperationListDTO.getOperationList();
                                for (CbpmOperationDTO cbpmOperation : operationDTOList) {
                                    //不同操作，但taskId和activetyType一致
                                    if (null != cbpmOperation && !CollectionUtils.isEmpty(cbpmOperation.getOperationTaskList())
                                            && null != cbpmOperation.getOperationTaskList().get(0)) {
                                        CbpmOperationTaskListDTO operationTaskListDTO = cbpmOperation.getOperationTaskList().get(0);
                                        taskId = operationTaskListDTO.getFdTaskId();
                                        activetyType = operationTaskListDTO.getFdActivityType();
                                        if(StringUtils.isNotBlank(taskId) && StringUtils.isNotBlank(activetyType)) {
                                            break;
                                        }
                                    }
                                }
                            }
                        }else { //起草人流程相关节点
                            if (null != cbpmOperationListDTO && "draft".equals(roleType) && !CollectionUtils.isEmpty(cbpmOperationListDTO.getOperationList())) {
                                List<CbpmOperationDTO> operationDTOList = cbpmOperationListDTO.getOperationList();
                                for (CbpmOperationDTO cbpmOperation : operationDTOList) {
                                    //不同操作，但taskId一致，activetyType不一致
                                    if (null != cbpmOperation && !CollectionUtils.isEmpty(cbpmOperation.getOperationTaskList())
                                            && null != cbpmOperation.getOperationTaskList().get(0)) {
                                        CbpmOperationTaskListDTO operationTaskListDTO = cbpmOperation.getOperationTaskList().get(0);
                                        taskId = operationTaskListDTO.getFdTaskId();
                                        String draftOperationType = cbpmOperation.getOperationType();
                                        if(operationType.equals(draftOperationType)){
                                            activetyType = operationTaskListDTO.getFdActivityType();
                                        }
                                        if(StringUtils.isNotBlank(taskId) && StringUtils.isNotBlank(activetyType)) {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("提交审批流程,获取根据角色分组后的流程可用操作结果转化时报错：" + e.toString());
        }
        Assert.notNull(taskId, WorkFlowConst.TASK_ID__NOT_NULL);
        Assert.notNull(activetyType, WorkFlowConst.ACTIVITY_TYPE_NOT_NULL);
        Assert.notNull(operationType, WorkFlowConst.OPERATION_TYPE_NOT_NULL);

        //流程实例
/*        TemplateInstance queryTemplateInstance = new TemplateInstance();
        queryTemplateInstance.setCbpmInstanceId(Long.parseLong(fdId));
        queryTemplateInstance.setBusinessId(Long.parseLong(businessId));
        queryTemplateInstance.setTemplateCode(businessKey);
        List<TemplateInstance> queryTemplateInstanceList = iTemplateInstanceService.queryTemplateInstanceByParam(queryTemplateInstance);
        TemplateInstance templateInstanceDto = null;
        if(!CollectionUtils.isEmpty(queryTemplateInstanceList)){
            templateInstanceDto = queryTemplateInstanceList.get(0);
        }
        if(templateInstanceDto == null){
            throw new Exception(WorkFlowConst.TEMPLATE_INSTANCE_NOT_NULL);
        }
        Long modelId = templateInstanceDto.getModelId();
        Long cbpmInstanceId = templateInstanceDto.getCbpmInstanceId();*/
        Map<String, Object> formParam = new HashMap<>();
//        String reallySubject = subject +FlowCommonConst.BAR_UNDERLINE + FlowCommonConst.NEW_REVIEW_FROM_PROCESS_TITLE;
        //业务系统模块id(填写对应的业务系统模块,如资质审查模块属于SUPPLIERAUTH)
        formParam.put("fdModuleId", WorkFLowCommonMethod.getModuleIdKeyByFormTemplateId(functionCode));
        formParam.put("formTemplateId", functionCode);      //业务系统模板id(填写对应的流程类型，如资质审查-quaOfReview)
        formParam.put("formInstanceId", businessId);  //业务系统实例id(流程引擎那边会保存)
        formParam.put("fdUrl","");  //写入消息中心的url(当前流程实例默认的表单url)
        formParam.put("formData", cbpmRquestParamDTO.getFormDataMap());  //FormParam的formData参数，有条件节点的时候必填
        //流程实例主题(多语言主题)
        String localKey = StringUtil.getUnderlineConverBar(LocaleHandler.getLocaleKey());

        //草稿状态才保存流程名称
        if(!CbpmProcessStatuseEnum.DRAFT_STATUS.getKey().equals(operationType)) {
            formParam.put("subject", subject);  //流程实际标题
            if (cbpmRquestParamDTO.getSubjectForMultiLanguages() != null) {
                formParam.put("subjectForMultiLanguages", cbpmRquestParamDTO.getSubjectForMultiLanguages());
            } else {
                formParam.put("mainLanguageType4Subject", localKey);
                List<Map<String, Object>> subjectForMultiLanguages = new ArrayList<>();
                Map<String, Object> reallyTitleMap = new HashMap<>();
                reallyTitleMap.put("languageType", localKey);
                reallyTitleMap.put("subject", subject);
                subjectForMultiLanguages.add(reallyTitleMap);
                formParam.put("subjectForMultiLanguages", subjectForMultiLanguages);
            }
        }
        formParam.put("auditFileDocIdList", cbpmRquestParamDTO.getAuditFileDocIdList()); //审批意见的附件id

        //processParam中的operationType值为draft_submit【起草人提交】或draft【保存草稿】时，本参数必填
        if(CbpmOperationTypeEnum.DRAFT.getKey().equals(operationType) || CbpmOperationTypeEnum.DRAFT_SUBMIT.getKey().equals(operationType)) {
            Assert.notNull(subject, WorkFlowConst.SUBJECT_NOT_NULL);
        }

        //设置流程参数
        Map<String, Object> processParam = processParam = new HashMap<>();
        //processParam中的operationType值不是draft【保存草稿】时，本参数必填
        if(!CbpmOperationTypeEnum.DRAFT.getKey().equals(operationType)){
            processParam.put("taskId", taskId);
            processParam.put("activityType", activetyType);
            Assert.notNull(taskId, WorkFlowConst.TASK_ID__NOT_NULL);
            Assert.notNull(activetyType, WorkFlowConst.ACTIVITY_TYPE_NOT_NULL);
        }
        processParam.put("operationType", operationType);
        processParam.put("auditNote", cbpmRquestParamDTO.getAuditNote()); //流程处理意见
        processParam.put("toOtherPersons",cbpmRquestParamDTO.getToOtherPersons()); //转办，传阅、沟通用户登录名,多个用户名用“;”隔开

        //驳回的节点id-默认值为N1
        String jumpToNodeId = cbpmRquestParamDTO.getJumpToNodeId();
        if(StringUtils.isBlank(jumpToNodeId)){
            jumpToNodeId = FlowCommonConst.NODE_ID_N1;
        }
        processParam.put("jumpToNodeId", jumpToNodeId);

        processParam.put("refusePassedToThisNode", cbpmRquestParamDTO.getRefusePassedToThisNode()); //驳回后是否直接返回本节点（true为直接返回本节点
        processParam.put("changeNodeHandler", cbpmRquestParamDTO.getChangeNodeHandler()); //流程节点处理人参数
        processParam.put("changeNodeFormUrls", cbpmRquestParamDTO.getChangeNodeFormUrls()); //更改节点对应表单url的参数
        processParam.put("changeNodeProcessTypes", cbpmRquestParamDTO.getChangeNodeProcessTypes());  //更改节点配置参数
        processParam.put("mainLanguageType4NodeName", localKey); //节点名称的主语言的语言编码
        processParam.put("manualBranchToNodes",cbpmRquestParamDTO.getManualBranchToNodes());

        if(!CollectionUtils.isEmpty(cbpmRquestParamDTO.getChangeNodeHandler())){
            processParam.put("changeNodeHandler", cbpmRquestParamDTO.getChangeNodeHandler());
        }

        //转办，传阅、沟通以下条件必填
        if(CbpmOperationTypeEnum.CIRCULATE.getKey().equals(operationType) ||
                CbpmOperationTypeEnum.HANDLER_COMMUNICATE.getKey().equals(operationType) ||
                CbpmOperationTypeEnum.HANDLER_COMMISSION.getKey().equals(operationType)){
            if(!processParam.containsKey("toOtherPersons")){
                throw new Exception(WorkFlowConst.TO_OTHER_PERSONS_NOT_NULL);
            } else {
                if(null == processParam.get("toOtherPersons")){
                    throw new Exception(WorkFlowConst.TO_OTHER_PERSONS_NOT_NULL);
                }
            }

        }
        //驳回时必填
        if(CbpmOperationTypeEnum.HANDLER_REFUSE.getKey().equals(operationType)){
            if(!processParam.containsKey("refusePassedToThisNode") || null == processParam.get("refusePassedToThisNode")){
                throw new Exception(WorkFlowConst.REFUSE_PASSED_TO_THIS_NODE_NOT_NULL);
            }
        }

        ApproveProcessDTO approveProcess = new ApproveProcessDTO();
        approveProcess.setFormParam(formParam);
        approveProcess.setProcessParam(processParam);
        approveProcess.setFdId(fdId);
        approveProcess.setLoginName(loginName);
        approveProcess.setFdTemplateId(businessKey);
//        approveProcess.setFdTemplateCode(businessKey); //不能随便传值，否则会提示模板找不到
        Map<String, Object> mapResultBody = iCbpmWorkFlowService.approveProcess(approveProcess);
//        String result = iCbpmWorkFlowService.approveProcess(cbpmInstanceId, modelId, loginName, processParam,formParam);
        String resultCode = (null != mapResultBody.get("resultCode") ? String.valueOf(mapResultBody.get("resultCode")) : "");
        if (!FlowCommonConst.SUCCESS_CODE_010.equals(resultCode)) {
            String resultMsg = String.valueOf(mapResultBody.get("resultMsg"));
            Assert.isNull(resultMsg, resultMsg);
        }

        //审批成功之后，获取流程可操作列表
        Map<String, Object> processListMap = new HashMap<>();
        List<Map> processListJson = new ArrayList<>();
        flowProcessQuery = new FlowProcessQueryDTO();
        flowProcessQuery.setFdId(fdId);
        flowProcessQuery.setLoginName(loginName);
        List processList = iCbpmWorkFlowService.getOperationList(flowProcessQuery);
        if(!CollectionUtils.isEmpty(processList)){
            JSONArray processListArray = (JSONArray) processList;
            processListJson = JSONObject.parseArray(processListArray.toJSONString(), Map.class);
        }
        processListMap.put("operationJsonList", processListJson);
        processListMap.put("businessKey", businessKey);
        processListMap.put("businessId", businessId);
        processListMap.put("fdId", fdId);
        processListMap.put("fdModuleId", cbpmRquestParamDTO.getFdModuleId()); //模板Id
        processListMap.put("subject", subject);
        processListMap.put("message", ResultCode.SUCCESS.getMessage());


        //保存审批附件
/*
        List<Map<String, Object>> fileList = null;
        if(requestParam.get("fileList") != null){
            fileList = (List<Map<String, Object>>) requestParam.get("fileList");
        }
        if(fileList != null && fileList.size() > 0){
            for(Map<String, Object> fileMap : fileList){
                String docId = fileMap.get("docId").toString();
                String fileName = fileMap.get("fileName").toString();
                String fileType01 = fileMap.get("fileType01").toString();
                SysFileCommonRelatsDto sysFileCommonRelatsDto = new SysFileCommonRelatsDto();
                sysFileCommonRelatsDto.setDocId(docId);
                sysFileCommonRelatsDto.setFileName(fileName);
                sysFileCommonRelatsDto.setBussinessId(Long.parseLong(businessId));
                sysFileCommonRelatsDto.setFileType(businessKey);
                sysFileCommonRelatsDto.setFileType01(fileType01);
                sysFileCommonRelatsDto.setFileType02(processParam.get("activityType").toString());

                sysFileCommonRelatsDto.setCreatedBy(loginName);
                sysFileCommonRelatsDto.setCreationDate(new Date());
                sysFileCommonRelatsDto.setLastUpdatedBy(loginName);
                sysFileCommonRelatsDto.setLastUpdateDate(new Date());
                sysFileCommonRelatsDto.setVersion(0L);
                sysFileCommonRelatsDto.setDeleteFlag(0);
                srmBaseFeignClient.insertSysFileCommonRelats(sysFileCommonRelatsDto);
            }
        }*/

        return processListMap;
    }

    @Override
    public Map<String, Object> getCurrentProcessInfo(String fdId) throws Exception{
        Assert.notNull(fdId, WorkFlowConst.TEMPLATE_INSTANCE_NOT_NULL);
        String loginName = AppUserUtil.getUserName();
        Assert.notNull(loginName, WorkFlowConst.LOGIN_NAME_NOT_NULL);

        Map<String, Object> result = new HashMap<>();
        result.put("message", WorkFlowConst.GET_CURRENT_PROCESS_INFO); //错误时候提示
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        flowProcessQuery.setLoginName(loginName);
        flowProcessQuery.setFdId(fdId);

        //获取流程当前节点、流程状态、状态code
        String subject = "";
        String fdStatus = "";
        String fdStatusCode ="";
        List<Map<String, Object>> currNodeList = new ArrayList<>();
        String currentNode = "";    //当前流程节点
        Map<String, Object> processNodesInfoMap = iCbpmWorkFlowService.getProcessInfo(flowProcessQuery);
        if(null != processNodesInfoMap){
            subject = String.valueOf(processNodesInfoMap.get("subject"));
            fdStatus = String.valueOf(processNodesInfoMap.get("fdStatus"));
            fdStatusCode = String.valueOf(processNodesInfoMap.get("fdStatusCode"));
            currNodeList = (null != processNodesInfoMap.get("currNodes") ? (List<Map<String, Object>>) processNodesInfoMap.get("currNodes") : null);
            if(!CollectionUtils.isEmpty(currNodeList) && null != currNodeList.get(0)){
                Map<String, Object> currNodeMap = currNodeList.get(0);
                currentNode = String.valueOf(currNodeMap.get("nodeId"));
            }
        }

        //草稿状态(fsStatusCode为null)或空的时候的要修改为拟定
        if(StringUtils.isBlank(fdStatusCode) || CbpmProcessStatuseEnum.DRAFT_STATUS.getKey().equals(fdStatusCode)) {
            fdStatus = CbpmProcessStatuseEnum.DRAW_UP_STATUS.getValue();
            fdStatusCode = CbpmProcessStatuseEnum.DRAW_UP_STATUS.getKey();
        }
        result.put("fdId", fdId);
        result.put("subject", subject);
        result.put("fdStatusCode", fdStatusCode);
        result.put("fdStatus", fdStatus);
        result.put("currNodeList", currNodeList);

        //获取流程导向图（获取根据角色分组后的流程可用操作）
        CbpmProcessNodesListDTO processNodesListDTO = iCbpmWorkFlowService.getProcessTableInfoAndManualNodesList(flowProcessQuery);
        List<CbpmProcessNodesDTO> processNodesDTOList = new ArrayList<>();
        if(null != processNodesListDTO && !CollectionUtils.isEmpty(processNodesListDTO.getProcessNodes())){
            processNodesDTOList = processNodesListDTO.getProcessNodes();
        }
        result.put("processNodes", processNodesDTOList);
        result.put("message", ResultCode.SUCCESS.getMessage());

        //获取或流程可操作节点信息
        List<Map<String, Object>> resultProcessListJson = new ArrayList<>();
        flowProcessQuery = new FlowProcessQueryDTO();
        flowProcessQuery.setLoginName(loginName);
        flowProcessQuery.setFdId(fdId);
        List processList = iCbpmWorkFlowService.getOperationList(flowProcessQuery);
        if(!CollectionUtils.isEmpty(processList)){
            JSONArray processListArray = (JSONArray) processList;
            List<Map> processListJson = JSONObject.parseArray(processListArray.toJSONString(), Map.class);

            //如果不是N1节点，要去掉废弃按钮
           if(!CollectionUtils.isEmpty(processListJson) ){
               for(Map<String, Object> processLisJsonMap : processListJson){
                   Map<String, Object> resultOperationMap = new HashMap<>();
                   if(null != processLisJsonMap){
                        JSONArray operationArray = (null != processLisJsonMap.get("operationList") ? (JSONArray) processLisJsonMap.get("operationList") : null);
                        List<Map> operationList = (JSONObject.parseArray(operationArray.toJSONString(), Map.class));
                        if(!CollectionUtils.isEmpty(operationList)){
                            List<Map> resultOperationList = new ArrayList<>();
                            for(Map<String, Object> operationListMap : operationList){
                                String operationType = (null != operationListMap ? String.valueOf(operationListMap.get("operationType")) : "");
                                //不是N1接口的只显示驳回、通过、起草人撤回、和起草人废弃
                                if(StringUtils.isNotBlank(operationType) && null != operationListMap && !FlowCommonConst.NODE_ID_N1.equals(currentNode) &&
                                        (CbpmOperationTypeEnum.HANDLER_PASS.getKey().equals(operationType) || CbpmOperationTypeEnum.HANDLER_REFUSE.getKey().equals(operationType)
                                        || CbpmOperationTypeEnum.DRAFT_RETURN.getKey().equals(operationType)) || CbpmOperationTypeEnum.DRAFT_ABANDON.getKey().equals(operationType) ){
                                    resultOperationList.add(operationListMap);
                                }else if(StringUtils.isNotBlank(operationType) && null != operationListMap && FlowCommonConst.NODE_ID_N1.equals(currentNode) &&
                                        (CbpmOperationTypeEnum.DRAFT.getKey().equals(operationType) || CbpmOperationTypeEnum.DRAFT_SUBMIT.getKey().equals(operationType)
                                        || CbpmOperationTypeEnum.DRAFT_ABANDON.getKey().equals(operationType))){ //是N1接口的只显示草稿、提交和废弃
                                    resultOperationList.add(operationListMap);
                                }
                                resultOperationMap.put("operationList", resultOperationList);
                            }
                        }
                        resultOperationMap.put("roleType",processLisJsonMap.get("roleType"));
                    }
                   resultProcessListJson.add(resultOperationMap);
               }
           }
        }
        result.put("operationJsonList", resultProcessListJson);

        //获取模板名称和业务表单ID(注意：这里是否要以后要考虑并发量大或分布式)
        Long businessId = null;
        Long businessKey = null;
        TemplateInstance templateInstance = new TemplateInstance();
        templateInstance.setCbpmInstanceId(Long.parseLong(fdId));
        List<TemplateInstance> templateInstanceList = iTemplateInstanceService.list(new QueryWrapper<>(templateInstance));
        if(!CollectionUtils.isEmpty(templateInstanceList) && null != templateInstanceList.get(0)){
            businessId = templateInstanceList.get(0).getBusinessId();
            businessKey = templateInstanceList.get(0).getModelId();

        }
        result.put("businessId", String.valueOf(businessId));
        result.put("businessKey", String.valueOf(businessKey));

        return result;
    }

    @Override
    public Map<String, Object> getMyPrevProcessApprovers(String businessKey, String fdId) throws Exception {
        String loginName = AppUserUtil.getUserName();
        Assert.notNull(loginName, WorkFlowConst.LOGIN_NAME_NOT_NULL);

        //如果businessKey不为空，则根据businessKey查询。否则先获取businessKey
        if( StringUtils.isBlank(businessKey) ){
            Assert.notNull(fdId, WorkFlowConst.TEMPLATE_INSTANCE_NOT_NULL);
            TemplateInstance templateInstance = new TemplateInstance();
            templateInstance.setCbpmInstanceId(Long.parseLong(fdId));
            QueryWrapper<TemplateInstance> queryWrapper = new QueryWrapper<>(templateInstance);
            List<TemplateInstance> templateInstanceList = iTemplateInstanceService.list(queryWrapper);
            if(!CollectionUtils.isEmpty(templateInstanceList) && null != templateInstanceList.get(0)){
                businessKey = String.valueOf(templateInstanceList.get(0).getModelId());
            }
            Assert.notNull(businessKey, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        }

        Assert.notNull(businessKey, WorkFlowConst.BUDINESS_KEY_NOT_NULL);
        FlowProcessQueryDTO flowProcessQueryDTO = new FlowProcessQueryDTO();
        flowProcessQueryDTO.setLoginName(loginName);
        flowProcessQueryDTO.setFdTemplateId(businessKey);
        Map<String, Object> prevProcessMap = iCbpmWorkFlowService.getPrevProcessApprovers(flowProcessQueryDTO);
        return prevProcessMap;
    }

    @Override
    public List<Map> getAuditeNoteList(String fdId) throws Exception{
        //数据校验
        Assert.notNull(fdId, WorkFlowConst.TEMPLATE_INSTANCE_NOT_NULL);
        String loginName = AppUserUtil.getUserName();
        Assert.notNull(loginName, WorkFlowConst.LOGIN_NAME_NOT_NULL);

        List<Map> auditeNodesListMap = new ArrayList<>();
        //获取流程审批相关信息(操作、附件、审批意见、审批时间)
        FlowProcessQueryDTO flowProcessQueryDTO = new FlowProcessQueryDTO();
        flowProcessQueryDTO.setFdId(fdId);
        flowProcessQueryDTO.setLoginName(loginName);
        List auditeNodesList =iCbpmWorkFlowService.getAuditeNote(flowProcessQueryDTO);
        if(!CollectionUtils.isEmpty(auditeNodesList)){
            JSONArray auditeNodesArray = (JSONArray) auditeNodesList;
            auditeNodesListMap = JSONObject.parseObject(auditeNodesArray.toJSONString(), List.class);
            if(!CollectionUtils.isEmpty(auditeNodesListMap)){
                //获取供应商类型用户信息
                User queryUser = new User();
                queryUser.setUserType(FlowCommonConst.USER_TYPE_BUYER);
                List<User> userList = rbacClient.listByUser(queryUser);

                for (Map<String, Object> auditeNodesMap : auditeNodesListMap) {
                    //时间格式转换
                    auditeNodesMap.put("fdHandlerTime", DateUtil.getDateStrByLong(String.valueOf(auditeNodesMap.get("fdHandlerTime")), DateUtil.YYYYMMDD_HHMMSS));

                    //添加审批人姓名
                    String fdHandlerIds = String.valueOf(auditeNodesMap.get("fdHandlerId"));
                    if(!CollectionUtils.isEmpty(userList) && StringUtils.isNotBlank(fdHandlerIds)){
                        for(User user : userList){
                            if(null != user && fdHandlerIds.equals(user.getUsername()) && StringUtils.isNotBlank(user.getNickname())){
                                auditeNodesMap.put("fdHandlerName", user.getNickname());
                                break;
                            }
                        }
                    }

                }
            }
        }
        return auditeNodesListMap;
    }

    @Override
    public List<Map<String, Object>> getProcessRefuseNode(String fdId) throws Exception{
        //数据校验
        Assert.notNull(fdId, WorkFlowConst.TEMPLATE_INSTANCE_NOT_NULL);
        String loginName = AppUserUtil.getUserName();
        Assert.notNull(loginName, WorkFlowConst.LOGIN_NAME_NOT_NULL);

        //获取流程详细信息(可以先根据getProcessInfo接口查询到nodeInstanceId)
        List<Map<String, Object>> processRefuseNodeList = new ArrayList<>();
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        flowProcessQuery.setLoginName(loginName);
        flowProcessQuery.setFdId(fdId);
        Map<String, Object> processInfoMap = iCbpmWorkFlowService.getProcessInfo(flowProcessQuery);
        if(null != processInfoMap){
            JSONArray currNodeArray = (null != processInfoMap.get("currNodes") ? (JSONArray)processInfoMap.get("currNodes") : null );
            if(null != currNodeArray && null != currNodeArray.get(0)){
                Map<String,Object> currNodeMap = (Map<String,Object>) currNodeArray.get(0);
                String nodeInstanceId = String.valueOf(currNodeMap.get("nodeInstanceId"));
                if(StringUtils.isNotBlank(nodeInstanceId)){
                    flowProcessQuery.setNodeInstanceId(nodeInstanceId);
                    processRefuseNodeList = iCbpmWorkFlowService.getProcessRefuseNode(flowProcessQuery);
                }
            }
        }
        return processRefuseNodeList;
    }

    @Override
    public Map<String, Object> newEventFlowCallBackCBPM(String appId, String token, Map<String, Object> mipCallBackParams) throws Exception {
        Map<String, Object> callBack = new HashMap<>();
        callBack.put("success", false);
        //日志输出回调事件参数
        String evenData = JsonUtil.entityToJsonStr(mipCallBackParams);
        LOGGER.info("进入回调事件：appId: {},token: {},Map: {}",appId,token, evenData);

        //数据校验
        Assert.notNull(appId, FlowCommonConst.APPID_NOT_NULL);
        Assert.notNull(token, FlowCommonConst.TOKEN_NOT_NULL);

        //获取请求体，请求参数在请求体里，包括：processId-实例ID、formParam-表单关联参数、
        // processParam-流程参数数组、eventParamList-模板自定义属性、formData-业务表单参数
        Map<String, Object> bodyMap = (null != mipCallBackParams.get("body") ? (Map<String, Object>) mipCallBackParams.get("body") : null);
        Assert.notNull(bodyMap, FlowCommonConst.CBPM_BODY_NOT_NULL);
        String processId = String.valueOf(bodyMap.get("processId"));
        Assert.notNull(processId, WorkFlowConst.TEMPLATE_INSTANCE_NOT_NULL);
/*        Map<String, Object> formParam = (null !=  bodyMap.get("formParam") ? (Map<String, Object>) bodyMap.get("formParam") : null);
        Assert.notNull(formParam, FlowCommonConst.CBPM_FORM_PARAM_NOT_NULL);
        String modelId = String.valueOf(formParam.get("formTemplateId")); //业务模板id*/

        //根据流程实例ID获取流程实例实体
        TemplateInstance queryTemplateInstance = new TemplateInstance();
        queryTemplateInstance.setCbpmInstanceId(Long.parseLong(processId));
        List<TemplateInstance> templateInstanceList = iTemplateInstanceService.queryTemplateInstanceByParam(queryTemplateInstance);
        if(CollectionUtils.isEmpty(templateInstanceList) && null == templateInstanceList.get(0) && null == templateInstanceList.get(0).getBusinessId()){
            throw new Exception("回调事件异常：找不到流程实例对应的表单记录；流程实例ID为：" + processId);
        }
        TemplateInstance templateInstance = templateInstanceList.get(0);

        //记录回调事件
        this.recordCallBackEvent(processId, templateInstance, evenData, FlowCommonConst.EVENT_STATUS_PENDDING, null);

        //进入回调处理
        List<Map<String, Object>> processParam = (List<Map<String, Object>>) bodyMap.get("processParam");
        if(!CollectionUtils.isEmpty(processParam)){
            for(Map<String, Object> processMap : processParam){
                String eventType = String.valueOf(processMap.get("eventType"));
                String operationtype = String.valueOf(processMap.get("operationtype"));
                String handlerId = String.valueOf(processMap.get("handlerId"));
                String handlerName = ""; //用户名
/*                Long userId = null;     //用戶表ID
                //如果需要调用rbacClient.listByUser方法，需要用户登录才能查询，但回调方法不需要登录
                if(StringUtils.isNotBlank(handlerId)){
                    User user = new User();
                    user.setUsername(handlerId);
                    List<User> userList = rbacClient.listByUser(user);
                    if(!CollectionUtils.isEmpty(userList) && null != userList.get(0)) {
                        userId = userList.get(0).getUserId();
                        handlerName = userList.get(0).getNickname();
                    }
                }
                processMap.put("userId",userId);
                */
                mipCallBackParams.put("eventType", eventType);
                mipCallBackParams.put("operationtype", operationtype);
                processMap.put("handlerName", handlerName);
                processMap.put("handlerIp", IPUtil.getRemoteIpAddr(HttpServletHolder.getRequest()));
                ((Map<String, Object>) mipCallBackParams.get("body")).put("processParam", processMap);
                //更新流程状态
                this.updateInstranceDtoForCallBack(templateInstance, eventType);

                //进入开始节点事件，判断是驳回操作，并且节点是开始节点，进入业务回调
/*                if(MipEventTypeEnum.HANDLE_REFUSE_EVENT.getType().equals(eventType)){
                    //驳回起草节点，设置参数
                    String toNodeId = refuseToNodeId(processParam);
                    if("N1".equals(toNodeId)){
                        mipCallBackParams.put("isRefuseStart", "Y");
                    } else {
                        continue;
                    }
                }*/

                //注意：模板ID使用tempplateInstance里面的
                TemplateHeaderDTO queryTemplateHeader = new TemplateHeaderDTO();
                queryTemplateHeader.setDeleteFlag(FlowCommonConst.DELETE_FLAG_NO);
                queryTemplateHeader.setModelId(String.valueOf(templateInstanceList.get(0).getModelId()));
                queryTemplateHeader.setBussinessFunction(eventType);
                TemplateHeaderDTO templateHeaderDTO = iTemplateHeaderService.queryProcessTemplateNotTemplateNameByParam(queryTemplateHeader);
                //没有配置业务回调事件，不需进入业务回调事件
                if(templateHeaderDTO == null || (null != templateHeaderDTO && CollectionUtils.isEmpty(templateHeaderDTO.getTemplateLinesList()))){
                    LOGGER.info("模板ID为：{}没有配置事件处理过程{},不需进入业务回调事件",templateInstanceList.get(0).getModelId(), eventType);
                    continue;
                }

                //进入业务回调
                this.callBackBusinessService(templateInstance, templateHeaderDTO, mipCallBackParams);

            }
        }

        callBack.put("success", true);
        callBack.put("msg", "回调成功");
        return callBack;
    }

    private void callBackBusinessService(TemplateInstance templateInstance, TemplateHeaderDTO templateHeader, Map<String, Object> businessMap) throws Exception {
        //设置业务回调参数
        List<TemplateLines> templateLinesList = templateHeader.getTemplateLinesList();
        TemplateLines templateLines = templateLinesList.get(0);
        Map<String, Object> eventParamMap = new HashMap<>();
        eventParamMap.put("businessKey" , templateInstance.getModelId());
        eventParamMap.put("businessId" , templateInstance.getBusinessId());
        eventParamMap.put("modelId" , templateInstance.getModelId());
        eventParamMap.put("classPath" , templateHeader.getBussinessClass());
        eventParamMap.put("eventType" , templateLines.getBussinessType());
        eventParamMap.put("methodName" , templateLines.getBussinessFunction());
        eventParamMap.put("mapEventData" , businessMap);

        Object[] objs = {eventParamMap};
        Class[] clss = {Map.class};

        LOGGER.info("回调事件参数eventParamMap：", eventParamMap);
        //通过反射机制，Feign调用进入业务回调 SrmSupplierFeignClient,cbpmBaseFeignHandler
        Map<String, Object> businessCallBack = (Map<String, Object>) CommonReflectMethod.commonReflectMethod(
                templateHeader.getFeignClient(), FlowCommonConst.FUNCTION_HANDLER, objs, clss);

        //判断业务回调是否成功
        String result = String.valueOf(businessCallBack.get("data"));
        //回调失败抛出异常，中断流程，可通过特权人账号重新触发处理
        if(!FlowCommonConst.SUCCESS.equals(result)){
            LOGGER.error("回调事件异常：处理业务失败", businessCallBack);
            //截取错误信息记录到事件表
            if(businessCallBack.containsKey("stackMsg")){
                String stackMsg = (String) businessCallBack.get("stackMsg");
                this.updateCallBackEventError(templateInstance.getCbpmInstanceId(), stackMsg);
            }
            throw new Exception("回调事件异常：业务处理失败\n" + businessCallBack.toString());
        }
    }

    /**
     * 更新错误信息到流程事件表
     * @param cbpmInstanceId 流程实例Id
     * @param errorMsg
     * @throws Exception
     */
    private void updateCallBackEventError(Long cbpmInstanceId, String errorMsg) throws Exception {
        if(null != cbpmInstanceId){
            FlowinstanceEvent flowinstanceEvent = new FlowinstanceEvent();
            flowinstanceEvent.setInstanceId(cbpmInstanceId);
            List<FlowinstanceEvent> flowinstanceEventList = iFlowinstanceEventService.list(new QueryWrapper<>(flowinstanceEvent));
            if(!CollectionUtils.isEmpty(flowinstanceEventList)){
                FlowinstanceEvent queryFlowinstanceEvent = flowinstanceEventList.get(0);
                queryFlowinstanceEvent.setErrorMessage(errorMsg);
                queryFlowinstanceEvent.setLastUpdateDate(new Date());
                queryFlowinstanceEvent.setLastUpdatedBy(FlowCommonConst.CBPM);
                iFlowinstanceEventService.updateById(queryFlowinstanceEvent);
            }
        }

    }

    /**
     * Description 流程回调的时候修改流程实例信息
     * @Param eventType 事件类型
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.08
     * @throws
     **/
    private void updateInstranceDtoForCallBack(TemplateInstance templateInstance, String eventType){
        if(templateInstance != null && null != templateInstance.getInstanceId()){
            templateInstance.setFlowStatus(eventType);
            templateInstance.setLastUpdateDate(new Date());
            templateInstance.setVersion(templateInstance.getVersion()+1);
            try {
                iTemplateInstanceService.updateById(templateInstance);
            } catch (Exception e) {
                LOGGER.error("实例ID为{},事件类型为{}流程回调的时候修改流程实例信息时报错：{}",templateInstance.getInstanceId(),eventType,e);
            }
        }
    }

    @Transactional
    @Override
    public void recordCallBackEvent(String processId, TemplateInstance templateInstance, String evenData, String eventStatus, String msg) throws Exception{
        FlowinstanceEvent flowinstanceEvent = new FlowinstanceEvent();
        if(StringUtils.isNotEmpty(processId)){
            flowinstanceEvent.setInstanceId(Long.parseLong(processId));
            //如果流程实例记录为空，重新查一遍
            if(templateInstance == null && StringUtils.isNotBlank(processId)){
                TemplateInstance queryInstanse = new TemplateInstance();
                queryInstanse.setCbpmInstanceId(Long.parseLong(processId));
                List<TemplateInstance> templateInstanceList = iTemplateInstanceService.queryTemplateInstanceByParam(queryInstanse);
                if(!CollectionUtils.isEmpty(templateInstanceList) && null != templateInstanceList.get(0)) {
                    templateInstance = templateInstanceList.get(0);
                }
            }
        }
        if(templateInstance != null){
            flowinstanceEvent.setBusinessId(templateInstance.getBusinessId());
            flowinstanceEvent.setTemplateCode(templateInstance.getTemplateCode());
            flowinstanceEvent.setModelId(templateInstance.getModelId());
        }
        flowinstanceEvent.setVersion(FlowCommonConst.VERSION_0);
        flowinstanceEvent.setDeleteFlag(FlowCommonConst.DELETE_FLAG_NO);
        flowinstanceEvent.setEventData(evenData);
        flowinstanceEvent.setEventStatus(eventStatus);
        flowinstanceEvent.setErrorMessage(msg);
        flowinstanceEvent.setEventType(FlowCommonConst.EVENT_TYPE_CALLBACK);
        flowinstanceEvent.setCreatedId(0L);
        flowinstanceEvent.setCreatedBy("CBPM");
        flowinstanceEvent.setCreationDate(new Date());
        flowinstanceEvent.setLastUpdatedBy("CBPM");
        flowinstanceEvent.setLastUpdateDate(new Date());
        flowinstanceEvent.setCreatedByIp("127.0.0.1");

        iFlowinstanceEventService.save(flowinstanceEvent);
    }


}
