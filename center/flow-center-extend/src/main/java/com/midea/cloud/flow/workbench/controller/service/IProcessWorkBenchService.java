package com.midea.cloud.flow.workbench.controller.service;

import com.midea.cloud.srm.model.flow.query.dto.FlowProcessQueryDTO;

import java.util.Map;

/**
 * <pre>
 *  流程工作台相关Service接口
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/16 11:18
 *  修改内容:
 * </pre>
 */
public interface IProcessWorkBenchService  {

    /**
     * Description 获取用户待处理的流程列表
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.16
     * @throws Exception
     **/
    Map<String, Object> getMyRunningProcess(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取用户已审批/处理的流程列表
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.16
     * @throws Exception
     **/
    Map<String, Object> getMyWorkedProcess(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取我启动的流程
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.16
     * @throws Exception
     **/
    Map<String, Object> getMyStartProcess(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取抄送给我的流程
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.16
     * @throws Exception
     **/
    Map<String, Object> getSendNodesToMe(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取所有流程列表
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.12
     * @throws Exception
     **/
    Map<String, Object> getProcessList(FlowProcessQueryDTO flowProcessQuery) throws Exception;

}
