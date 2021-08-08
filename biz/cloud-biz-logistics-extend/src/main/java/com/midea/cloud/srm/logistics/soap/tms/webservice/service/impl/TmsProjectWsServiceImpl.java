package com.midea.cloud.srm.logistics.soap.tms.webservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.api.ApiClient;
import com.midea.cloud.srm.logistics.expense.service.ILogisticsProjectService;
import com.midea.cloud.srm.logistics.soap.tms.service.ITmsProjectService;
import com.midea.cloud.srm.logistics.soap.tms.webservice.service.ITmsProjectWsService;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.logistics.expense.entity.LogisticsProject;
import com.midea.cloud.srm.model.logistics.soap.tms.entity.TmsProject;
import com.midea.cloud.srm.model.logistics.soap.tms.request.RequestEsbInfo;
import com.midea.cloud.srm.model.logistics.soap.tms.request.TmsProjectEntity;
import com.midea.cloud.srm.model.logistics.soap.tms.request.TmsProjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 * TMS 服务项目数据 WebService 接口实现类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/26 19:25
 *  修改内容:
 * </pre>
 */
@Slf4j
@WebService(targetNamespace = "http://www.aurora-framework.org/schema",
        endpointInterface = "com.midea.cloud.srm.logistics.soap.tms.webservice.service.ITmsProjectWsService")
@Component("iTmsProjectWsService")
public class TmsProjectWsServiceImpl implements ITmsProjectWsService {

    /** tms服务项目 接口表Service */
    @Resource
    private ITmsProjectService iTmsProjectService;

    /** logistics服务项目 正式表Service */
    @Resource
    private ILogisticsProjectService iLogisticsProjectService;

    @Autowired
    private ApiClient apiClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SoapResponse execute(TmsProjectRequest request) {

        Long startTime = System.currentTimeMillis();

        SoapResponse response = new SoapResponse();
        SoapResponse.RESPONSE innerResponse = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo responseEsbInfo = new SoapResponse.RESPONSE.EsbInfo();

        RequestEsbInfo esbInfo = request.getEsbInfo();
        String instId = "";
        String requestTime = "";
        if (null != esbInfo) {
            instId = esbInfo.getInstId();
            requestTime = esbInfo.getRequestTime();
        }

        TmsProjectRequest.RequestInfo requestInfo = request.getRequestInfo();
        TmsProjectRequest.RequestInfo.Projects projectsClass = null;
        List<TmsProjectEntity> projectEntityList = null;
        if (null != requestInfo) {
            projectsClass = requestInfo.getProjects();
            if (null != projectsClass) {
                projectEntityList = projectsClass.getProject();
            }
        }
        // 接口传入的项目数据为空时
        if (CollectionUtils.isEmpty(projectEntityList)) {
            response.setSuccess("E");
            responseEsbInfo.setInstId(instId);
            responseEsbInfo.setReturnStatus("E");
            responseEsbInfo.setReturnMsg("传入的服务项目数据为空.");
            responseEsbInfo.setRequestTime(requestTime);
            responseEsbInfo.setResponseTime(requestTime);
            innerResponse.setEsbInfo(responseEsbInfo);
            response.setResponse(innerResponse);

            // 保存请求和响应报文到接口日志中心
            saveRequestAndResponseToLog(request, response);

            return response;
        } else {
            log.info("开始获取tms服务项目数据: " + (null != request ? request.toString() : "空"));
            // 保存数据到接口表
            List<TmsProject> tmsProjectList = null;
            try {
                tmsProjectList = convertToTmsProject(projectEntityList);
            } catch (Exception e) {
                response.setSuccess("E");
                responseEsbInfo.setInstId(instId);
                responseEsbInfo.setReturnStatus("E");
                responseEsbInfo.setReturnMsg(e.getMessage());
                responseEsbInfo.setRequestTime(requestTime);
                responseEsbInfo.setResponseTime(requestTime);
                innerResponse.setEsbInfo(responseEsbInfo);
                response.setResponse(innerResponse);
                return response;
            }
            saveTmsProjects(tmsProjectList);

            // 保存数据到正式表
            List<LogisticsProject> logisticsProjectList = convertToLogisticsProject(projectEntityList);
            response = saveOrUpdateProjects(logisticsProjectList, instId, requestTime);

            // 保存请求和响应报文到接口日志中心
            saveRequestAndResponseToLog(request, response);

            // 回写接口表数据的状态
            updateTmsProjects(tmsProjectList);
            log.info("结束获取tms服务项目数据, 用时:" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
            return response;
        }
    }

    /**
     * 保存tms项目数据到 服务项目接口表
     *
     * @param tmsProjectList
     */
    private void saveTmsProjects(List<TmsProject> tmsProjectList) {
        if (CollectionUtils.isNotEmpty(tmsProjectList)) {
            iTmsProjectService.saveBatch(tmsProjectList);
        }
    }

    /**
     * 保存tms服务项目数据到 服务项目正式表
     *
     * @param logisticsProjectList
     * @param instId
     * @param requestTime
     */
    private SoapResponse saveOrUpdateProjects(List<LogisticsProject> logisticsProjectList, String instId, String requestTime) {
        SoapResponse soapResponse = new SoapResponse();
        SoapResponse.RESPONSE innerResponse = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo responseEsbInfo = new SoapResponse.RESPONSE.EsbInfo();
        String returnMsg = "接收tms系统服务项目数据成功. ";
        int emptyProjectCodeNum = 0;
        int duplicateProjectCodeNum = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 过滤掉项目编码为空的数据
        List<LogisticsProject> filterEmptyProjectCode = logisticsProjectList.stream().filter(x -> StringUtils.isNotEmpty(x.getProjectCode())).collect(Collectors.toList());
        emptyProjectCodeNum = logisticsProjectList.size() - filterEmptyProjectCode.size();
        // 过滤掉项目编码重复的数据
        Map<String, LogisticsProject> map = filterEmptyProjectCode.stream().collect(Collectors.toMap(LogisticsProject::getProjectCode, Function.identity(), (o1, o2) -> o2));
        duplicateProjectCodeNum = filterEmptyProjectCode.size() - map.size();
        List<LogisticsProject> logisticsProjects = new ArrayList<>();
        for (Map.Entry<String, LogisticsProject> m : map.entrySet()) {
            LogisticsProject value = m.getValue();
            logisticsProjects.add(value);
        }

        // 开始进行校验和保存, 存在即更新, 不存在即新增
        saveOrUpdateLogisticsProjects(logisticsProjects);

        if (emptyProjectCodeNum > 0) {
            returnMsg += "已忽略项目编码为空的数据: " + emptyProjectCodeNum + "条";
        }
        if (duplicateProjectCodeNum > 0) {
            returnMsg += "已忽略项目编码重复的数据: " + duplicateProjectCodeNum + "条";
        }
        soapResponse.setSuccess("S");
        responseEsbInfo.setInstId(instId);
        responseEsbInfo.setReturnStatus("S");
        responseEsbInfo.setReturnMsg(returnMsg);
        responseEsbInfo.setRequestTime(requestTime);

        responseEsbInfo.setResponseTime(simpleDateFormat.format(new Date()));
        innerResponse.setEsbInfo(responseEsbInfo);
        soapResponse.setResponse(innerResponse);
        return soapResponse;
    }

    /**
     * 保存或更新数据到正式表
     *
     * @param logisticsProjects
     */
    private void saveOrUpdateLogisticsProjects(List<LogisticsProject> logisticsProjects) {

        // 获取传入数据的项目编码集合
        List<String> tmsProjectCodes = logisticsProjects.stream().filter(x -> StringUtils.isNotEmpty(x.getProjectCode())).map(x -> x.getProjectCode()).collect(Collectors.toList());
        Map<String, LogisticsProject> map = logisticsProjects.stream().collect(Collectors.toMap(LogisticsProject::getProjectCode, Function.identity()));

        // 根据传入数据的项目编码 获取数据库中存在的数据
        Map<String, LogisticsProject> dbMap = iLogisticsProjectService.list(Wrappers.lambdaQuery(LogisticsProject.class)
                .in(LogisticsProject::getProjectCode, tmsProjectCodes)).stream().collect(Collectors.toMap(LogisticsProject::getProjectCode, Function.identity(), (o1, o2) -> o2));
        List<String> dbLogisticsProjectCodes = iLogisticsProjectService.list(Wrappers.lambdaQuery(LogisticsProject.class)
                .in(LogisticsProject::getProjectCode, tmsProjectCodes)).stream().map(x -> x.getProjectCode()).collect(Collectors.toList());

        List<LogisticsProject> saveLogisticsProjectList = new ArrayList<>();
        List<LogisticsProject> updateLogisticsProjectList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(logisticsProjects)) {
            for (Map.Entry<String, LogisticsProject> m : map.entrySet()) {
                String projectCode = m.getKey();
                // 存在 更新操作
                if (dbLogisticsProjectCodes.contains(projectCode)) {
                    LogisticsProject value = dbMap.get(projectCode);
                    LogisticsProject updateLogisticsProject = map.get(projectCode);
                    updateLogisticsProject.setProjectId(value.getProjectId());
                    updateLogisticsProjectList.add(updateLogisticsProject);
                }
                // 不存在 即新增操作
                else {
                    LogisticsProject saveLogisticsProject = map.get(projectCode);
                    saveLogisticsProjectList.add(saveLogisticsProject);
                }
            }
        }

        if (CollectionUtils.isNotEmpty(updateLogisticsProjectList)) {
            log.info("更新tms服务项目数据: "+updateLogisticsProjectList.size()+"条");
            iLogisticsProjectService.updateBatchById(updateLogisticsProjectList);
        }
        if (CollectionUtils.isNotEmpty(saveLogisticsProjectList)) {
            log.info("保存tms服务项目数据: "+saveLogisticsProjectList.size()+"条");
            iLogisticsProjectService.saveBatch(saveLogisticsProjectList);
        }
    }

    /**
     * 将报文中的实体tmsProjectEntity转为接口表的实体tmsProject
     *
     * @param projectEntityList
     * @return
     */
    private List<TmsProject> convertToTmsProject(List<TmsProjectEntity> projectEntityList) {
        List<TmsProject> tmsProjectList = new ArrayList<>();

        for (TmsProjectEntity tmsProjectEntity : projectEntityList) {
            if (null != tmsProjectEntity) {
                TmsProject tmsProject = new TmsProject();
                BeanUtils.copyProperties(tmsProjectEntity, tmsProject);
                String projectTotal = tmsProjectEntity.getProjectTotal();
                if (StringUtils.isNotEmpty(projectTotal)) {
                    try {
                        BigDecimal total = new BigDecimal(projectTotal);
                        tmsProject.setProjectTotal(total);
                    } catch (Exception e) {
                        throw new BaseException("传入的服务项目数据, 项目编码:["+tmsProjectEntity.getProjectCode()+"]的项目总量:["+projectTotal+"]不符合小数格式.");
                    }
                }
                Long id = IdGenrator.generate();
                tmsProject.setProjectId(id).setImportStatus(0);
                tmsProjectList.add(tmsProject);
            }
        }
        return tmsProjectList;
    }

    /**
     * 将报文中的实体tmsProjectEntity转为正式表的实体logisticsProject
     *
     * @param projectEntityList
     * @return
     */
    private List<LogisticsProject> convertToLogisticsProject(List<TmsProjectEntity> projectEntityList) {
        List<LogisticsProject> logisticsProjectList = new ArrayList<>();

        for (TmsProjectEntity tmsProjectEntity : projectEntityList) {
            if (null != tmsProjectEntity) {
                LogisticsProject logisticsProject = new LogisticsProject();
                BeanUtils.copyProperties(tmsProjectEntity, logisticsProject);
                String projectTotal = tmsProjectEntity.getProjectTotal();
                if (StringUtils.isNotEmpty(projectTotal)) {
                    try {
                        BigDecimal total = new BigDecimal(projectTotal);
                        logisticsProject.setProjectTotal(total);
                    } catch (Exception e) {
                        throw new BaseException("传入的服务项目数据, 项目编码:["+tmsProjectEntity.getProjectCode()+"]的项目总量:["+projectTotal+"]不符合小数格式.");
                    }
                }
                logisticsProject.setProjectId(IdGenrator.generate());
                logisticsProjectList.add(logisticsProject);
            }
        }
        return logisticsProjectList;
    }

    /**
     * 回写接口表数据状态
     * @param tmsProjects
     */
    private void updateTmsProjects(List<TmsProject> tmsProjects) {
        tmsProjects.forEach(tmsProject -> {
            tmsProject.setImportStatus(1).setImportDate(new Date());
        });
        iTmsProjectService.updateBatchById(tmsProjects);
    }

    /**
     * 保存请求和响应报文到日志中心
     * @param request
     * @param response
     */
    public void saveRequestAndResponseToLog(TmsProjectRequest request, SoapResponse response) {
        log.info("开始保存tms服务项目接收日志...");
        InterfaceLogDTO interfaceLogDTO = new InterfaceLogDTO();
        // 创建开始时间
        interfaceLogDTO.setCreationDateBegin(new Date());
        // 服务名字
        interfaceLogDTO.setServiceName("tms服务项目接收");
        // 请求方式
        interfaceLogDTO.setServiceType("WEBSERVICE");
        // 发送方式
        interfaceLogDTO.setType("RECEIVE");
        // 单据类型
        interfaceLogDTO.setBillType("tms服务项目接收");

        // 请求参数
        try {
            interfaceLogDTO.setServiceInfo(JSON.toJSONString(request));
        } catch (Exception e) {
            log.error("tms服务项目接收数据记录日志报错{}"+e.getMessage());
        }
        // 报文id
        String instId = request.getEsbInfo().getInstId();
        interfaceLogDTO.setBillId(instId);

        // 响应信息
        interfaceLogDTO.setReturnInfo(JSON.toJSONString(response));

        // 状态
        if (response.getResponse().getEsbInfo().getReturnStatus().equals("S")) {
            interfaceLogDTO.setStatus("SUCCESS");
        } else {
            interfaceLogDTO.setErrorInfo(response.getResponse().getEsbInfo().getReturnMsg());
            interfaceLogDTO.setStatus("FAIL");
        }

        interfaceLogDTO.setTargetSys("SRM");
        // 完成时间
        interfaceLogDTO.setFinishDate(new Date());

        try {
            apiClient.createInterfaceLogForAnon(interfaceLogDTO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("保存<tms接收服务项目数据>日志报错{}"+e.getMessage());
        }
        log.info("保存tms服务项目接收日志结束...");
    }

}
