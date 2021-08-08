package com.midea.cloud.flow.workbench.controller;

import com.midea.cloud.flow.workbench.controller.service.IProcessWorkBenchService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.flow.query.dto.FlowProcessQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  工作台相关Controller类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/16 9:21
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/workbench/process")
@Slf4j
public class ProcessWorkBenchController extends BaseController {

    /**工作流Service接口*/
    @Autowired
    private IProcessWorkBenchService iProcessWorkBenchService;

    /**
     * Description 获取待处理流程信息列表
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.16
     * @throws
     **/
    @GetMapping("/findMyRunningProcess")
    public Map<String, Object> findMyRunningProcess(int page, int pageSize){
        Map<String, Object> myRunningProcessMap = new HashMap<>();
        try{
            FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
            flowProcessQuery.setPage(page);
            flowProcessQuery.setPageSize(pageSize);
            myRunningProcessMap = iProcessWorkBenchService.getMyRunningProcess(flowProcessQuery);
        }catch (Exception e){
            log.error("待处理流程信息时报错：",e);
        }
        return myRunningProcessMap;
    }

    /**
     * Description 获取用户已审批/处理的流程列表
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.16
     * @throws Exception
     **/
    @GetMapping("/findMyWorkedProcess")
    public Map<String, Object> findMyWorkedProcess(int page, int pageSize, String docStatus){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
            flowProcessQuery.setPage(page);
            flowProcessQuery.setPageSize(pageSize);
            flowProcessQuery.setDocStatus(docStatus);
            resultMap = iProcessWorkBenchService.getMyWorkedProcess(flowProcessQuery);
        }catch (Exception e){
            log.error("获取用户已审批/处理的流程列表时报错:",e);
        }
        return resultMap;
    }

    /**
     * Description 获取我启动的流程
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.16
     * @throws Exception
     **/
    @GetMapping("/findMyStartProcess")
    public Map<String, Object> findMyStartProcess(int page, int pageSize){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
            flowProcessQuery.setPage(page);
            flowProcessQuery.setPageSize(pageSize);
            resultMap = iProcessWorkBenchService.getMyStartProcess(flowProcessQuery);
        }catch (Exception e){
            log.error("获取我启动的流程时报错:",e);
        }
        return resultMap;
    }

    /**
     * Description 获取抄送给我的流程
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.16
     * @throws Exception
     **/
    @GetMapping("/findSendNodesToMe")
    public Map<String, Object> findSendNodesToMe(int page, int pageSize){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
            flowProcessQuery.setPage(page);
            flowProcessQuery.setPageSize(pageSize);
            resultMap = iProcessWorkBenchService.getSendNodesToMe(flowProcessQuery);
        }catch (Exception e){
            log.error("获取抄送给我的流程时报错:",e);
        }
        return resultMap;
    }

}
