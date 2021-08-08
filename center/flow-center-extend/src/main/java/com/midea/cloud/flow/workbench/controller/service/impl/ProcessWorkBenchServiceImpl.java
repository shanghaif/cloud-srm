package com.midea.cloud.flow.workbench.controller.service.impl;

import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.flow.common.constant.FlowCommonConst;
import com.midea.cloud.flow.common.constant.WorkFlowConst;
import com.midea.cloud.flow.workbench.controller.service.IProcessWorkBenchService;
import com.midea.cloud.flow.workflow.service.ICbpmWorkFlowService;
import com.midea.cloud.flow.workflow.service.ITemplateHeaderService;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.flow.query.dto.FlowProcessQueryDTO;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/16 11:20
 *  修改内容:
 * </pre>
 */
@Service
public class ProcessWorkBenchServiceImpl implements IProcessWorkBenchService {

    /**cbpm流程Service接口*/
    @Resource
    private ICbpmWorkFlowService iCbpmWorkFlowService;
    /**流程模板配置头Service接口*/
    @Resource
    private ITemplateHeaderService iTemplateHeaderService;

    /**用户控制中心内部调用类*/
    @Resource
    private RbacClient rbacClient;

    @Override
    public Map<String, Object> getMyRunningProcess(FlowProcessQueryDTO flowProcessQuery) throws Exception {
        String loginName = AppUserUtil.getUserName();
        Assert.notNull(loginName, WorkFlowConst.LOGIN_NAME_NOT_NULL);

        Map<String, Object> myRunningProcessMap = new HashMap<>();
        flowProcessQuery.setLoginName(loginName);
        if(null == flowProcessQuery){
            flowProcessQuery = new FlowProcessQueryDTO();
            flowProcessQuery.setPage(FlowCommonConst.PAGE);
            flowProcessQuery.setPageSize(FlowCommonConst.PAGE_SIZE);
        }
        if(null != flowProcessQuery.getPage() && 0 == flowProcessQuery.getPage()){
            flowProcessQuery.setPage(FlowCommonConst.PAGE);
        }
        if(null != flowProcessQuery.getPageSize() && 0 == flowProcessQuery.getPageSize()){
            flowProcessQuery.setPageSize(FlowCommonConst.PAGE_SIZE);
        }
        myRunningProcessMap = iCbpmWorkFlowService.getMyRunningProcess(flowProcessQuery);
        if(null != myRunningProcessMap){
            //获取供应商类型用户信息
            User queryUser = new User();
            queryUser.setUserType(FlowCommonConst.USER_TYPE_BUYER);
            List<User> userList = rbacClient.listByUser(queryUser);

            List<Map> runningProcessList = (null != myRunningProcessMap.get("list") ? (List<Map>) myRunningProcessMap.get("list") : null);
            if(CollectionUtils.isNotEmpty(runningProcessList)){
                for(Map<String, Object> runningProcessMap : runningProcessList){
                    if(null != runningProcessMap){
                        Long fdNodeStartDate = (null != runningProcessMap.get("fdNodeStarDate") ?
                                Long.parseLong(String.valueOf(runningProcessMap.get("fdNodeStarDate"))) : 0L);
                        runningProcessMap.put("fdNodeStarDate", DateUtil.getDateStrByLong(runningProcessMap.get("fdNodeStarDate"), DateUtil.YYYY_MM_DD));
                        runningProcessMap.put("stayTime", DateUtil.getTimeAndNowInterval(fdNodeStartDate)); //停留时间(单位小时)

                        //创建时间格式yyyy-MM-dd
                        runningProcessMap.put("docCreateTime", DateUtil.getDateStrByLong(runningProcessMap.get("docCreateTime"), DateUtil.YYYY_MM_DD));
                        //根据创建人获取创建人名称
                        String docCreatorId = String.valueOf(runningProcessMap.get("docCreatorId"));
                        String docCreatorName = "";
                        if(StringUtils.isNotBlank(docCreatorId) && CollectionUtils.isNotEmpty(userList)){
                            for(User user : userList){
                                if(null != user && docCreatorId.equals(user.getUsername())){
                                    docCreatorName = user.getNickname();
                                    break;
                                }
                            }
                            runningProcessMap.put("docCreatorName", docCreatorName);
                        }else{
                            runningProcessMap.put("docCreatorName", "");
                        }
                    }
                }
            }
        }
        return myRunningProcessMap;
    }

    @Override
    public Map<String, Object> getMyWorkedProcess(FlowProcessQueryDTO flowProcessQuery) throws Exception {
        String loginName = AppUserUtil.getUserName();
        Assert.notNull(loginName, WorkFlowConst.LOGIN_NAME_NOT_NULL);

        Map<String, Object> myWorkedProcess = new HashMap<>();
        flowProcessQuery.setLoginName(loginName);
        if(null == flowProcessQuery){
            flowProcessQuery = new FlowProcessQueryDTO();
            flowProcessQuery.setPage(FlowCommonConst.PAGE);
            flowProcessQuery.setPageSize(FlowCommonConst.PAGE_SIZE);
        }
        if(null != flowProcessQuery.getPage() && 0 == flowProcessQuery.getPage()){
            flowProcessQuery.setPage(FlowCommonConst.PAGE);
        }
        if(null != flowProcessQuery.getPageSize() && 0 == flowProcessQuery.getPageSize()){
            flowProcessQuery.setPageSize(FlowCommonConst.PAGE_SIZE);
        }
        myWorkedProcess = iCbpmWorkFlowService.getMyWorkedProcess(flowProcessQuery);
        if(null != myWorkedProcess){
            //获取供应商类型用户信息
            User queryUser = new User();
            queryUser.setUserType(FlowCommonConst.USER_TYPE_BUYER);
            List<User> userList = rbacClient.listByUser(queryUser);

            //获取流程模板配置头信息
/*            TemplateHeader templateHeader = new TemplateHeader();
            templateHeader.setDeleteFlag(FlowCommonConst.DELETE_FLAG_NO);
            List<TemplateHeader> templateHeaderList = iTemplateHeaderService.list(new QueryWrapper<>(templateHeader));*/

            List<Map> myWorkedProcessList = (null != myWorkedProcess.get("list") ? (List<Map>) myWorkedProcess.get("list") : null);
            if(CollectionUtils.isNotEmpty(myWorkedProcessList)){
                for(Map<String, Object> myWorkedProcessMap : myWorkedProcessList){
                    if(null != myWorkedProcessMap) {
                        //流程到达该节点的时间
                        myWorkedProcessMap.put("fdStarDate", DateUtil.getDateStrByLong(myWorkedProcessMap.get("fdStarDate"), DateUtil.YYYY_MM_DD));
                        //在该节点处理结束的时间
                        myWorkedProcessMap.put("fdEndDate",  DateUtil.getDateStrByLong(myWorkedProcessMap.get("fdEndDate"), DateUtil.YYYY_MM_DD));

                        //设置流程类型(如资质审查、询价类型流程)
/*                        String modelId = String.valueOf(myWorkedProcessMap.get("fdModuleId"));
                        if(StringUtils.isNotBlank(modelId) && CollectionUtils.isNotEmpty(templateHeaderList)){

                        }else{

                        }*/

                        //创建时间格式yyyy-MM-dd
                        myWorkedProcessMap.put("docCreateTime", DateUtil.getDateStrByLong(myWorkedProcessMap.get("docCreateTime"), DateUtil.YYYY_MM_DD));
                        //根据创建人获取创建人名称
                        String docCreatorId = String.valueOf(myWorkedProcessMap.get("docCreatorId"));
                        String docCreatorName = "";
                        if(StringUtils.isNotBlank(docCreatorId) && CollectionUtils.isNotEmpty(userList)){
                            for(User user : userList){
                                if(null != user && docCreatorId.equals(user.getUsername())){
                                    docCreatorName = user.getNickname();
                                    break;
                                }
                            }
                            myWorkedProcessMap.put("docCreatorName", docCreatorName);
                        }else{
                            myWorkedProcessMap.put("docCreatorName", "");
                        }

                        /**根据用户名获取处理人名称*/
                        if (null != myWorkedProcessMap.get("fdHandlerId") && CollectionUtils.isNotEmpty(userList)) {
                            String fdHandlerId = String.valueOf(myWorkedProcessMap.get("fdHandlerId"));
                            String fdHandlerName = "";
                            for (User userDto : userList) {
                                if (null != userDto && fdHandlerId.equals(userDto.getUsername())) {
                                    fdHandlerName = userDto.getNickname();
                                    break;
                                }
                            }
                            myWorkedProcessMap.put("fdHandlerName", fdHandlerName);

                        } else {
                            myWorkedProcessMap.put("fdHandlerName", "");
                        }
                    }
                }
            }
        }
        return myWorkedProcess;
    }

    @Override
    public Map<String, Object> getMyStartProcess(FlowProcessQueryDTO flowProcessQuery) throws Exception {
        String loginName = AppUserUtil.getUserName();
        Assert.notNull(loginName, WorkFlowConst.LOGIN_NAME_NOT_NULL);

        Map<String, Object> myStartProcess = new HashMap<>();
        flowProcessQuery.setLoginName(loginName);
        if(null == flowProcessQuery){
            flowProcessQuery = new FlowProcessQueryDTO();
            flowProcessQuery.setPage(FlowCommonConst.PAGE);
            flowProcessQuery.setPageSize(FlowCommonConst.PAGE_SIZE);
        }
        if(null != flowProcessQuery.getPage() && 0 == flowProcessQuery.getPage()){
            flowProcessQuery.setPage(FlowCommonConst.PAGE);
        }
        if(null != flowProcessQuery.getPageSize() && 0 == flowProcessQuery.getPageSize()){
            flowProcessQuery.setPageSize(FlowCommonConst.PAGE_SIZE);
        }
        myStartProcess = iCbpmWorkFlowService.getMyStartProcess(flowProcessQuery);
        if(null != myStartProcess){
            //获取供应商类型用户信息
            User queryUser = new User();
            queryUser.setUserType(FlowCommonConst.USER_TYPE_BUYER);
            List<User> userList = rbacClient.listByUser(queryUser);

            List<Map> myStartProcessList = (null != myStartProcess.get("list") ? (List<Map>) myStartProcess.get("list") : null);
            if(CollectionUtils.isNotEmpty(myStartProcessList)){
                for(Map<String, Object> myStartProcessMap : myStartProcessList){
                    if(null != myStartProcessMap) {
                        myStartProcessMap.put("docCreateTime", DateUtil.getDateStrByLong(myStartProcessMap.get("docCreateTime"), DateUtil.YYYY_MM_DD));

                        //docSubject统一修改为fdSubject
                        myStartProcessMap.put("fdSubject", myStartProcessMap.get("docSubject"));
                        myStartProcessMap.remove("docSubject");

                        /**根据创建人获取创建人名称*/
                        if(null != myStartProcessMap.get("docCreatorId") && CollectionUtils.isNotEmpty(userList)){
                            String docCreatorId = String.valueOf(myStartProcessMap.get("docCreatorId"));
                            String docCreatorName = "";
                            for (User userDto : userList) {
                                if (null != userDto && docCreatorId.equals(userDto.getUsername())) {
                                        docCreatorName = userDto.getNickname();
                                        break;
                                }
                            }
                            myStartProcessMap.put("docCreatorName", docCreatorName);
                        }else{
                            myStartProcessMap.put("docCreatorName", "");
                        }
                    }
                }
            }
        }
        return  myStartProcess;
    }

    @Override
    public Map<String, Object> getSendNodesToMe(FlowProcessQueryDTO flowProcessQuery) throws Exception {
        String loginName = AppUserUtil.getUserName();
        Assert.notNull(loginName, WorkFlowConst.LOGIN_NAME_NOT_NULL);

        Map<String, Object> sendNodesToMeResult = new HashMap<>();
        flowProcessQuery.setLoginName(loginName);

        if(null == flowProcessQuery){
            flowProcessQuery = new FlowProcessQueryDTO();
            flowProcessQuery.setPage(FlowCommonConst.PAGE);
            flowProcessQuery.setPage(FlowCommonConst.PAGE_SIZE);
        }
        if(null != flowProcessQuery.getPage() && 0 == flowProcessQuery.getPage()){
            flowProcessQuery.setPage(FlowCommonConst.PAGE);
        }
        if(null != flowProcessQuery.getPageSize() && 0 == flowProcessQuery.getPageSize()){
            flowProcessQuery.setPage(FlowCommonConst.PAGE_SIZE);
        }
        sendNodesToMeResult = iCbpmWorkFlowService.getSendNodesToMe(flowProcessQuery);
        if(null != sendNodesToMeResult){
            //获取供应商类型用户信息
            User queryUser = new User();
            queryUser.setUserType(FlowCommonConst.USER_TYPE_BUYER);
            List<User> userList = rbacClient.listByUser(queryUser);

            List<Map> sendNodesToMeList = (null != sendNodesToMeResult.get("list") ? (List<Map>) sendNodesToMeResult.get("list") : null);
            if(CollectionUtils.isNotEmpty(sendNodesToMeList)){
                for(Map<String, Object> endNodesToMeMap : sendNodesToMeList){
                    if(null != endNodesToMeMap){
                        //Long型时间转为yyyy-MM-dd HH:mm:ss
                        endNodesToMeMap.put("docCreateTime", DateUtil.getDateStrByLong(endNodesToMeMap.get("docCreateTime"), DateUtil.YYYY_MM_DD));
                        //docSubject统一修改为fdSubject
                        endNodesToMeMap.put("fdSubject", endNodesToMeMap.get("docSubject"));
                        endNodesToMeMap.remove("docSubject");

                        /**根据用户名获取用户名称*/
                        if(null != endNodesToMeMap.get("docCreatorId") && CollectionUtils.isNotEmpty(userList)) {
                            String fdHandlerId = String.valueOf(endNodesToMeMap.get("docCreatorId"));
                            String fdHandlerName = "";
                            for (User userDto : userList) {
                                if (null != userDto && fdHandlerId.equals(userDto.getUsername())) {
                                    fdHandlerName = userDto.getNickname();
                                    break;
                                }
                            }
                            endNodesToMeMap.put("docCreatorName", fdHandlerName);
                        }else {
                            endNodesToMeMap.put("docCreatorName", "");
                        }
                    }else{
                        endNodesToMeMap.put("docCreateTime", "");
                        endNodesToMeMap.put("docCreatorName", "");
                    }
                }
            }
        }
        return  sendNodesToMeResult;
    }

    @Override
    public Map<String, Object> getProcessList(FlowProcessQueryDTO flowProcessQuery) throws Exception {
        String loginName = AppUserUtil.getUserName();
        Assert.notNull(loginName, WorkFlowConst.LOGIN_NAME_NOT_NULL);

        Map<String, Object> processMapResult = new HashMap<>();
        flowProcessQuery.setLoginName(loginName);
        if(null == flowProcessQuery){
            flowProcessQuery = new FlowProcessQueryDTO();
            flowProcessQuery.setPage(FlowCommonConst.PAGE);
            flowProcessQuery.setPage(FlowCommonConst.PAGE_SIZE);
        }
        if(null != flowProcessQuery.getPage() && 0 == flowProcessQuery.getPage()){
            flowProcessQuery.setPage(FlowCommonConst.PAGE);
        }
        if(null != flowProcessQuery.getPageSize() && 0 == flowProcessQuery.getPageSize()){
            flowProcessQuery.setPage(FlowCommonConst.PAGE_SIZE);
        }
        processMapResult = iCbpmWorkFlowService.getProcessList(flowProcessQuery);
        if(null != processMapResult){
            //获取供应商类型用户信息
            User queryUser = new User();
            queryUser.setUserType(FlowCommonConst.USER_TYPE_BUYER);
            List<User> userList = rbacClient.listByUser(queryUser);

            List<Map> processList = (null != processMapResult.get("list") ? (List<Map>) processMapResult.get("list") : null);
            if(CollectionUtils.isNotEmpty(processList)){
                for(Map<String, Object> processMap : processList){
                    if(null != processMap){
                        //Long型时间转为yyyy-MM-dd HH:mm:ss
//                        myWorkedProcessMap.put("docCreateTime", DateUtil.getDateStrByLong(myWorkedProcessMap.get("docCreateTime")));

                        /**根据用户名获取用户名称*/
                        if(null != processMap.get("fdHandlerId") && CollectionUtils.isNotEmpty(userList)) {
                            String fdHandlerId = String.valueOf(processMap.get("fdHandlerId"));
                            String fdHandlerName = "";
                            for (User userDto : userList) {
                                if (null != userDto && fdHandlerId.equals(userDto.getUsername())) {
                                    fdHandlerName = userDto.getNickname();
                                    break;
                                }
                            }
                            processMap.put("fdHandlerName", fdHandlerName);
                        }else{
                            processMap.put("fdHandlerName", "");
                        }

                    }
                }
            }
        }
        return  processMapResult;
    }


}
