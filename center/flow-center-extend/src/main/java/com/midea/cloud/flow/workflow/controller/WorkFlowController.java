package com.midea.cloud.flow.workflow.controller;

import com.midea.cloud.common.enums.flow.CbpmOperationTypeEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.flow.common.constant.WorkFlowConst;
import com.midea.cloud.flow.common.enums.CbpmProcessStatuseEnum;
import com.midea.cloud.flow.workflow.service.ICbpmWorkFlowService;
import com.midea.cloud.flow.workflow.service.IWorkFlowService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.flow.cbpm.CbpmProcessNodesListDTO;
import com.midea.cloud.srm.model.flow.process.dto.CbpmRquestParamDTO;
import com.midea.cloud.srm.model.flow.query.dto.FlowProcessQueryDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 工作流共用Controller 类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/17 16:00
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/flow/workFlow")
public class WorkFlowController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkFlowController.class);

    @Autowired
    private IWorkFlowService iWorkFlowService;

    /**cbpm工作流Service接口*/
    @Autowired
    private ICbpmWorkFlowService iCbpmWorkFlowService;

    /**
     * Description 根据流程实例ID获取所有流程节点信息
     * @Param modelId 流程模板Id
     * @return Map
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.26
     * @throws Exception
     **/
    @GetMapping("/getProcessNodesInfo")
    public CbpmProcessNodesListDTO getProcessNodesInfo(String fdId, String businessId,String businessKey){
        Assert.notNull(fdId, WorkFlowConst.TEMPLATE_INSTANCE_NOT_NULL);
        String loginName = AppUserUtil.getUserName();
        Assert.notNull(loginName, WorkFlowConst.LOGIN_NAME_NOT_NULL);

        //是否可以填写流程审批人员信息(草稿状态和没有状态的时候返回true，其他返回false) 说明：cbmp那边初始化流程接口之后，调用getProcessStatus接口时会报错，所以不用调用这个接口
/*        boolean selectPerson = false;
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        flowProcessQuery.setFdId(fdId);
        flowProcessQuery.setLoginName(loginName);
        try {
            Map<String, Object>  processStatusMap = iCbpmWorkFlowService.getProcessStatus(flowProcessQuery);
            if(null == processStatusMap){
                selectPerson = true;
            }else if(null != processStatusMap.get("fdStatusCode")
                    && CbpmProcessStatuseEnum.DRAFT_STATUS.getKey().equals(processStatusMap.get("fdStatusCode").toString())){
                selectPerson = true;
            }
        }catch (Exception e){
            LOGGER.error("调用流程状态接口时报错: ", e);
            throw new BaseException("调用流程状态接口时异常");
        }*/

        //获取所有流程节点信息
        CbpmProcessNodesListDTO processNodesListDTO = new CbpmProcessNodesListDTO();
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        flowProcessQuery.setLoginName(loginName);
        flowProcessQuery.setFdId(fdId);
        try {
            processNodesListDTO = iCbpmWorkFlowService.getProcessTableInfoAndManualNodesList(flowProcessQuery);
        }catch (Exception e){
            LOGGER.error("根据流程ID获取所有流程节点信息时报错: ", e);
            throw new BaseException("根据流程ID获取所有流程节点信息时报错");
        }
//        processNodesListDTO.setTitleName(supplierName + FlowCommonConst.BAR_UNDERLINE + FlowCommonConst.NEW_REVIEW_FROM_PROCESS_TITLE);

        try {
            if(StringUtils.isNotBlank(businessId) && StringUtils.isNotBlank(businessKey)) {
                //获取表单数据
                Map<String, Object> mapFormData = iCbpmWorkFlowService.getProcessCustomCallBack(Long.parseLong(businessId), businessKey,
                        CbpmOperationTypeEnum.GET_FORM_DATA.getKey(),fdId);
                processNodesListDTO.setFormDataMap(mapFormData);
            }
        }catch (Exception e){
            LOGGER.error("根据流程实例ID获取所有流程节点信息-获取自己定义表单时报错：",e);
        }

        processNodesListDTO.setSelectPerson(true);  //是否可以填写流程审批人员信息(草稿状态和没有状态的时候返回true）
        processNodesListDTO.setFdStatus(CbpmProcessStatuseEnum.DRAW_UP_STATUS.getValue()); //初始化流程进入，默认为拟定状态
        processNodesListDTO.setFdStatusCode(CbpmProcessStatuseEnum.DRAW_UP_STATUS.getKey());
        return processNodesListDTO;
    }

    /**
     * Description 获取当前流程信息(包括流程标题、流程状态、流程状态Code，流程可操作列表和)
     * @Param fdId 流程实例ID
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.31
     * @throws
     **/
    @GetMapping("/getCurrentProcessInfo")
    public Map<String, Object> getCurrentProcessInfo(String fdId) {
        Map<String, Object> processStatusMap = new HashMap<>();
        try {
            processStatusMap = iWorkFlowService.getCurrentProcessInfo(fdId);
        } catch (Exception e) {
            LOGGER.error("获取当前流程信息报错：", e);
            throw new BaseException(WorkFlowConst.GET_CURRENT_PROCESS_INFO);
        }
        try {
            if(null != processStatusMap && null != processStatusMap.get("businessId") && null != processStatusMap.get("businessKey")) {
                //获取表单数据
                Map<String, Object> formDataMap = iCbpmWorkFlowService.getProcessCustomCallBack(Long.parseLong(String.valueOf(processStatusMap.get("businessId"))),
                        String.valueOf(processStatusMap.get("businessKey")), CbpmOperationTypeEnum.GET_FORM_DATA.getKey(),fdId);
                processStatusMap.put("formDataMap", formDataMap);
            }
        }catch (Exception e){
            LOGGER.error("根据流程实例ID获取所有流程节点信息-获取自己定义表单时报错：",e);
        }
        return processStatusMap;
    }

    /**
     * Description 获取流程图信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.26
     * @throws
     **/
    @GetMapping("/getFlowChart")
    public void getFlowChart(String modleId){
        Assert.notNull(modleId, WorkFlowConst.BUDINESS_KEY_NOT_NULL);

    }

    /**
     * Description 初始化流程(并保存流程实例化表)
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.26
     **/
    @PostMapping("initWorkFlow")
    public Map<String, Object> initProcess(@RequestBody CbpmRquestParamDTO cbpmRquestParam)  {
        Map<String, Object> resultMap = new HashMap<>();
        try{
            String subject = cbpmRquestParam.getSubject();
            Assert.notNull(subject, WorkFlowConst.SUBUJECT_NOT_NULL);
            resultMap = iWorkFlowService.initProcessAndSaveInstance(cbpmRquestParam);
        }catch (Exception e){
            LOGGER.error("初始化流程时报错: ", e);
            throw new BaseException("初始化流程时报错: "+e.getMessage());
        }
        return resultMap;
    }

    /**
     * Description 是否可以填写流程审批人员信息
     * @Param
     * @return 草稿状态和没有状态的时候返回true，其他返回false
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.26
     **/
    @GetMapping("/haveSelectPerson")
    public boolean haveSelectPerson(String fdId) {
        Assert.notNull(fdId, WorkFlowConst.TEMPLATE_INSTANCE_NOT_NULL);
        String loginName = AppUserUtil.getUserName();
        Assert.notNull(loginName, WorkFlowConst.LOGIN_NAME_NOT_NULL);

        boolean selectPerson = false;
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        flowProcessQuery.setFdId(fdId);
        flowProcessQuery.setLoginName(loginName);
        try {
            Map<String, Object>  processStatusMap = iCbpmWorkFlowService.getProcessStatus(flowProcessQuery);
            //只有初始化流程的时候返回null，要测试一下草稿状态时，返回的是什么
            if(null == processStatusMap){
                selectPerson = true;
            }else if(null != processStatusMap.get("fdStatusCode")
                    && CbpmProcessStatuseEnum.DRAFT_STATUS.getKey().equals(processStatusMap.get("fdStatusCode").toString())){
                selectPerson = true;
            }
        }catch (Exception e){
            LOGGER.error("调用流程状态接口时报错: ", e);
            throw new BaseException("调用流程状态接口时异常");
        }
        return selectPerson;
    }

    /**
     * Description 直接保存流程草稿
     * @Param mipRquestParamDto
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.27
     * @throws
     **/
    @PostMapping("/saveDraftDirectly")
    public Map<String, Object> saveDraftDirectly(@RequestBody CbpmRquestParamDTO cbpmRquestParamDTO) {
        Map<String, Object> operationListMap = new HashMap<>();
        try {
            if(null == cbpmRquestParamDTO){
                Assert.isNull(ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
            }
            LOGGER.info("直接保存流程草稿：" + JsonUtil.entityToJsonStr(cbpmRquestParamDTO));
            operationListMap = iWorkFlowService.saveDraftDirectly(cbpmRquestParamDTO);
        } catch (Exception e) {
            LOGGER.error("直接保存流程草稿时报错：", e);
            throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
        }
        return operationListMap;
    }

    /**
     * Description 审批流程
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.19
     * @throws
     **/
    @PostMapping("/approveProcess")
    public Map<String, Object> approveProcess(@RequestBody CbpmRquestParamDTO cbpmRquestParamDTO) {
        Map<String, Object> operationListMap = new HashMap<>();
        try {
            operationListMap = iWorkFlowService.approveProcess(cbpmRquestParamDTO);
        } catch (Exception e) {
            LOGGER.error("审批流程时报错：", e);
            throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
        }

        return operationListMap;
    }

    /**
     * Description 获取上次审批人
     * @Param 
     * @return 
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.01
     * @throws 
     **/
    @GetMapping("/getPrevProcessApprovers")
    public Map<String, Object> getPrevProcessApprovers(String businessKey, String fdId) {
        try {
            return iWorkFlowService.getMyPrevProcessApprovers(businessKey, fdId);
        } catch (Exception e) {
            LOGGER.error("获取上次审批人时报错：", e);
            throw new BaseException("获取上次审批人时报错");
        }
    }

    /**
     * Description 获取流程审批记录信息
     * @Param fdId 流程实例ID
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.01
     * @throws BaseException
     **/
    @GetMapping("/getFlowLog")
    public List getFlowLog(String fdId) {
        Assert.notNull(fdId, WorkFlowConst.TEMPLATE_INSTANCE_NOT_NULL);
        String loginName = AppUserUtil.getUserName();
        Assert.notNull(loginName, WorkFlowConst.LOGIN_NAME_NOT_NULL);
        try {
            FlowProcessQueryDTO flowProcessQueryDTO = new FlowProcessQueryDTO();
            flowProcessQueryDTO.setFdId(fdId);
            flowProcessQueryDTO.setLoginName(loginName);
            return iCbpmWorkFlowService.getFlowLog(flowProcessQueryDTO);
        } catch (Exception e) {
            LOGGER.error("获取流程审批记录时报错: ",e);
            throw new BaseException("获取流程审批记录时报错");
        }
    }

    /**
     * Description 获取流程审批意见
     * @Param fdId 流程实例ID
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.01
     * @throws BaseException
     **/
    @GetMapping("/getAuditeNoteList")
    public List getAuditeNoteList(String fdId) {
        try {
            return iWorkFlowService.getAuditeNoteList(fdId);
        } catch (Exception e) {
            LOGGER.error("获取流程审批意见时报错: ", e);
            throw new BaseException("获取流程审批意见时报错");
        }
    }

    /**
     * Description 获取可驳回流程节点
     * @Param
     * @return void
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.03
     * @throws Exception
     **/
    @GetMapping("/getProcessRefuseNode")
    public List<Map<String, Object>> getProcessRefuseNode(String fdId){
        List<Map<String, Object>> processRefunsNodeList = new ArrayList<>();
        try{
            processRefunsNodeList = iWorkFlowService.getProcessRefuseNode(fdId);
        }catch (Exception e){
            LOGGER.error("根据流程ID{}获取可驳回流程节点时报错：",fdId, e);
            throw new BaseException("获取可驳回流程节点时报错");
        }
        return processRefunsNodeList;
    }

    private String[] printStackTrace(Exception e){

        String printStackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            printStackTrace = sw.toString();
            printStackTrace = printStackTrace.replaceAll("\\t","");
            pw.close();
            sw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return printStackTrace.split("\\r\\n");
    }


}
