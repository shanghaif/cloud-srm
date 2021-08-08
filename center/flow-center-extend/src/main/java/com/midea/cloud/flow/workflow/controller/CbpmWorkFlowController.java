package com.midea.cloud.flow.workflow.controller;

import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.flow.workflow.service.ICbpmWorkFlowService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.flow.query.dto.FlowProcessQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  CbmpWorkFlowController类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/10 19:46
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/flow/cbpmWorkFlow")
public class CbpmWorkFlowController extends BaseController {

    @Autowired
    private ICbpmWorkFlowService iCbpmWorkFlowService;

    /**
     * Description 获取模板信息
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     * @throws
     **/
    @GetMapping("/getFlowTemplate")
    public Map<String, Object> getFlowTemplate(@RequestBody FlowProcessQueryDTO flowProcessQuery) {
        try {
            return iCbpmWorkFlowService.getFlowTemplate(flowProcessQuery);
        }catch (Exception e){
            throw new BaseException(ResultCode.RPC_ERROR, e.toString());
        }
    }

    /**
     * Description 是否存在流程
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     * @throws
     **/
    @GetMapping("/isExistProcess")
    public Map<String, Object> isExistProcess(@RequestBody FlowProcessQueryDTO flowProcessQuery){
        try{
            return iCbpmWorkFlowService.isExistProcess(flowProcessQuery);
        }catch (Exception e){
            throw new BaseException(ResultCode.RPC_ERROR, e.toString());
        }
    }

    /**
     * Description 删除流程(硬删除,慎用)
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     * @throws
     **/
    @GetMapping("/deleteProcess")
    public Map<String, Object> deleteProcess(@RequestBody FlowProcessQueryDTO flowProcessQuery){
        try{
            return iCbpmWorkFlowService.deleteProcess(flowProcessQuery);
        }catch (Exception e){
            throw new BaseException(ResultCode.RPC_ERROR, e.toString());
        }
    }

    /**
     * Description 初始化流程
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     **/
    @PostMapping("/initProcess")
    public String initProcess(@RequestBody FlowProcessQueryDTO flowProcessQuery){
        try{
            return iCbpmWorkFlowService.initProcess(flowProcessQuery);
        }catch (Exception e){
            throw new BaseException(ResultCode.RPC_ERROR, e.toString());
        }
    }

    /**
     * Description 获取所有流程节点信息
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     * @throws Exception
     **/
    @GetMapping("/getProcessNodesInfo")
    public Map<String, Object> getProcessNodesInfo(@RequestBody FlowProcessQueryDTO flowProcessQuery){
        try{
            return iCbpmWorkFlowService.getProcessNodesInfo(flowProcessQuery);
        }catch (Exception e){
            throw new BaseException(ResultCode.RPC_ERROR, e.toString());
        }
    }

    /**
     * Description 根据模板ID获取模板详情
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     **/
    @GetMapping("/getTemplateInfo")
    public Map<String, Object> getTemplateInfo(@RequestBody FlowProcessQueryDTO flowProcessQuery){
        try{
            return iCbpmWorkFlowService.getTemplateInfo(flowProcessQuery);
        }catch (Exception e){
            throw new BaseException(ResultCode.RPC_ERROR, e.toString());
        }
    }

    /**
     * Description 获取用户待处理的流程列表
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     **/
    @GetMapping("/getMyRunningProcess")
    public Map<String, Object> getMyRunningProcess(@RequestBody FlowProcessQueryDTO flowProcessQuery){
        try{
            return iCbpmWorkFlowService.getMyRunningProcess(flowProcessQuery);
        }catch (Exception e){
            throw new BaseException(ResultCode.RPC_ERROR, e.toString());
        }
    }

    /**
     * Description 获取根据角色分组后的流程可用操作
     * @Param flowProcessQuery 流程条件查询实体类
     * @return List
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    @GetMapping("/getOperationList")
    public List getOperationList(FlowProcessQueryDTO flowProcessQuery){
        try{
            return iCbpmWorkFlowService.getOperationList(flowProcessQuery);
        }catch (Exception e){
            throw new BaseException(ResultCode.RPC_ERROR, e.toString());
        }
    }

    @GetMapping("/test")
    public BaseResult<String> test( FlowProcessQueryDTO flowProcessQuery){
            BaseResult<String> str = new BaseResult<>();
            return str;
    }



}
