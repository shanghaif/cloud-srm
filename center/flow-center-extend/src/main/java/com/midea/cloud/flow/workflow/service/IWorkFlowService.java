package com.midea.cloud.flow.workflow.service;

import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.flow.common.constant.WorkFlowConst;
import com.midea.cloud.srm.model.flow.cbpm.CbpmProcessNodesDTO;
import com.midea.cloud.srm.model.flow.cbpm.CbpmProcessNodesListDTO;
import com.midea.cloud.srm.model.flow.process.dto.CbpmRquestParamDTO;
import com.midea.cloud.srm.model.flow.process.entity.TemplateInstance;
import com.midea.cloud.srm.model.flow.query.dto.FlowProcessQueryDTO;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 工作流Service接口
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/16
 *  修改内容:
 * </pre>
 */
public interface IWorkFlowService {

    /**
     * Description 初始化流程,并保存流程实例化表
     * @Param flowProcessQuery 流程条件查询实体类
     * @return BaseResult<Map<String, Object>> BaseResult.data里面为流程模板ID/流程Id
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.26
     * @throws Exception
     **/
    Map<String, Object> initProcessAndSaveInstance(CbpmRquestParamDTO cbpmRquestParam) throws Exception;

    /**
     * Description 直接保存流程草稿
     * @Param cbpmFormMap cbpm流程引擎的表单
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.27
     * @return 返回可操作流程信息
     * @throws Exception
     **/
    Map<String, Object> saveDraftDirectly(CbpmRquestParamDTO mipRquestParamDTO) throws Exception;

    /**
     * Description 审批流程接口（包括：通过、驳回、沟通、转办、回复沟通、取消沟通、起草人提交、保存草稿、起草人废弃、起草人撤回、审批人废弃、传阅、催办）
     * @Param
     * @return 返回可操作流程信息
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.18
     * @throws Exception
     **/
    Map<String, Object> approveProcess(CbpmRquestParamDTO cbpmRquestParamDTO) throws Exception;

    /**
     * Description 获取本系统流程审批状态相关信息
     * @Param fdId 流程实例Id
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.31
     * @throws
     **/
    Map<String, Object> getCurrentProcessInfo(String fdId) throws Exception;

    /**
     * Description 获取本系统上次审批人
     * @Param businessKey 模板ID，fdId 实例流程ID
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.01
     * @throws Exception
     **/
    Map<String, Object> getMyPrevProcessApprovers(String businessKey, String fdId) throws Exception;

    /**
     * Description 获取流程审批意见
     * @Param fdId 流程实例ID
     * @return List
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.02
     * @throws Exception
     **/
    List<Map> getAuditeNoteList(String fdId) throws Exception;

    /**
     * Cbpm系统新统一回调事件
     * @param appId
     * @param token
     * @param mipCallBackParams
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.03
     * @return
     * @throws Exception
     */
    Map<String, Object> newEventFlowCallBackCBPM(String appId, String token, Map<String, Object> mipCallBackParams) throws Exception;

    /**
     * Description 获取可驳回流程节点
     * @Param fdId 流程实例ID
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.03
     * @throws
     **/
    List<Map<String, Object>> getProcessRefuseNode(String fdId) throws Exception;

    /**
     * 记录回调事件
     * @param processId
     * @param templateInstance
     * @param evenData
     * @param eventStatus
     * @throws Exception
     */
    public void recordCallBackEvent(String processId, TemplateInstance templateInstance, String evenData, String eventStatus, String msg) throws Exception;

    /**
     * Description 获取所有流程节点信息（添加流转方式中文）
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.26
     * @throws BaseException
     **/
    Map<String, Object> getProcessNodesInfo(FlowProcessQueryDTO flowProcessQuery) throws BaseException;

    /**
     * Description 判断是否启动工作流
     * @Param menuId 菜单ID，functionId-功能ID，templateCode-流程模板Code
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.22
     * @throws
     **/
    boolean getFlowEnable(Long menuId, Long functionId, String templateCode) throws BaseException;
}