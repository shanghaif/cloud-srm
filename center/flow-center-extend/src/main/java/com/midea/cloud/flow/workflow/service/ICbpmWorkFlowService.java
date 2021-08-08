package com.midea.cloud.flow.workflow.service;

import com.midea.cloud.flow.common.constant.FlowCommonConst;
import com.midea.cloud.srm.model.flow.cbpm.CbpmProcessNodesListDTO;
import com.midea.cloud.srm.model.flow.process.dto.ApproveProcessDTO;
import com.midea.cloud.srm.model.flow.process.dto.CbpmRquestParamDTO;
import com.midea.cloud.srm.model.flow.process.dto.ChangeProcessSubjectDTO;
import com.midea.cloud.srm.model.flow.process.dto.ChangeTemplateEditorDTO;
import com.midea.cloud.srm.model.flow.query.dto.FlowProcessQueryDTO;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  cbpm系统接口
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/10 18:01
 *  修改内容:
 * </pre>
 */
public interface ICbpmWorkFlowService {

    /**
     * Description 创建流程草稿
     * @Param formParam 表单信息
     * @return 流程实例ID
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.16
     * @throws
     **/
    Map<String, Object> saveDraftDirectly(CbpmRquestParamDTO cdpmRquestParamDTO, Map<String, Object> formParam) throws Exception;


    /**
     * Description 流程自定义回调方法(包括getFormData、draftEvent方法等)
     * @Param businessId 表单ID,businessKey-流程模板ID,eventType-事件名称,fdId-流程ID
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.20
     * @throws
     **/
    Map<String, Object> getProcessCustomCallBack(Long businessId, String businessKey, String eventType,String fdId) throws Exception;

    /**
     * Description 审批流程接口（包括：通过、驳回、沟通、转办、回复沟通、取消沟通、起草人提交、保存草稿、起草人废弃、起草人撤回、审批人废弃、传阅、催办）
     * @Param approveProcessDTO 审批流程接口实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.16
     * @throws
     **/
    Map<String, Object> approveProcess(ApproveProcessDTO approveProcessDTO) throws Exception;

    /**
     * Description 审批流程接口（包括：通过、驳回、沟通、转办、回复沟通、取消沟通、起草人提交、保存草稿、起草人废弃、起草人撤回、审批人废弃、传阅、催办）
     * @Param fdId 实例Id，fdTemplateId模板Id，loginName 用户名，processParam 流程参数，formParam 表单参数
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.18
     * @throws
     **/
    String approveProcess(Long fdId, Long fdTemplateId, String loginName, Map<String, Object> processParam,
                          Map<String, Object> formParam) throws Exception;

    /**
     * 删除流程(硬删除,慎用)
     * @param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @throws Exception
     */
    Map<String, Object> deleteProcess(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * 获取流程模板
     * @param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @throws Exception
     */
    Map<String, Object> getFlowTemplate(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
      * Description 是否存在流程
      * @Param flowProcessQuery 流程条件查询实体类
      * @return Map<String, Object>
      * @Author wuwl18@meicloud.com
      * @Date 2020.03.11
      * @throws Exception
      **/
    Map<String, Object> isExistProcess(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 是否存在流程
     * @Param flowProcessQuery 流程条件查询实体类
     * @return 存在，返回true，否则返回false
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     * @throws Exception
     **/
    Boolean isExistProcessReturnBoolean(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 初始化流程
     * @Param flowProcessQuery 流程条件查询实体类
     * @return 流程实例ID
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     * @throws Exception
     **/
    String initProcess(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取所有流程节点信息
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     * @throws Exception
     **/
    Map<String, Object> getProcessNodesInfo(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 根据模板ID获取模板详情
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     * @throws Exception
     **/
    Map<String, Object> getTemplateInfo(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取用户待处理的流程列表
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     * @throws Exception
     **/
    Map<String, Object> getMyRunningProcess(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取流程待审人信息
     * @Param processIdList 多条流程ID,最大200条,超过200报错,不传入值 则返回null
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.12
     * @throws Exception
     **/
    List getProcessApproveInfo(List<String> processIdList) throws Exception;

    /**
     * Description 获取用户处理相关的节点信息
     * @Param flowProcessQuery 流程条件查询实体类
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.12
     * @throws
     **/
    List listFlowNodeInfo(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取流程状态
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.12
     * @throws Exception
     **/
    Map<String, Object> getProcessStatus(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取所有流程列表
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.12
     * @throws Exception
     **/
    Map<String, Object> getProcessList(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取所有流程总数
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.12
     * @throws Exception
     **/
    Map<String, Object> getProcessCount(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取流程详情
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    Map<String, Object> getProcessInfo(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取流程审批意见
     * @Param flowProcessQuery 流程条件查询实体类
     * @return List
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    List getAuditeNote(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取流程审批记录
     * @Param flowProcessQuery 流程条件查询实体类
     * @return List
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.20
     * @throws Exception
     **/
    List getFlowLog(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取用户相关流程数量
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    Map<String, Object> selectUserProcessCount(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取用户已审批/处理的流程列表
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    Map<String, Object> getMyWorkedProcess(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取可驳回节点
     * @Param flowProcessQuery 流程条件查询实体类
     * @return List
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    List getProcessRefuseNode(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取历史审批意见和未来审批节点接口
     * @Param flowProcessQuery 流程条件查询实体类
     * @return List
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    List getAuditeNoteInAllNodes(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取上次审批人
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    Map<String, Object> getPrevProcessApprovers(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取模板节点事件列表
     * @Param flowProcessQuery 流程条件查询实体类
     * @return List
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    List listTemplateNodeEnvents(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取我启动的流程
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    Map<String, Object> getMyStartProcess(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取抄送给我的流程
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    Map<String, Object> getSendNodesToMe(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取流程传阅信息
     * @Param flowProcessQuery 流程条件查询实体类
     * @return List
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    List listCirculateNote(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取传阅给我的流程
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    Map<String, Object> getCircularizeNodesToMe(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取流程表格与人工决策选择项列表(包括节点处理人姓名和角色名称)
     * @Param flowProcessQuery 流程条件查询实体类
     * @return CbpmProcessNodesListDTO
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.14
     * @throws Exception
     **/
    CbpmProcessNodesListDTO getProcessTableInfoAndManualNodesList(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取流程表格与人工决策选择项列表
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.14
     * @throws Exception
     **/
    Map<String, Object> getProcessTableInfoAndManualNodes(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取流程表格与人工决策选择项列表和标题名称、状态和角色名称
     * @Param fdId 流程实例ID， loginName 用户名,roleName角色名称
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.14
     * @throws Exception
     **/
//    Map<String, Object> getProcessTableInfoAndManualNodes(String fdId, String loginName,String roleId, String roleName) throws Exception;

    /**
     * Description 获取流程的某些信息
     * @Param idList 流程Id集合
     * @return List
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    List getProcessInfoById(List<String> idList) throws Exception;

    /**
     * Description 获取流程待审人信息(批量)
     * @Param processIdList 流程Id集合
     * @return List
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    List getProcessApproveInfoList(List<String> processIdList) throws Exception;

    /**
     * Description 获取根据角色分组后的流程可用操作
     * @Param flowProcessQuery 流程条件查询实体类
     * @return List
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.13
     * @throws Exception
     **/
    List getOperationList(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 更新模板权限（全量更新）
     * @Param chanceTemplateEditor 更新模板权限实体类
     * @return List
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.14
     * @throws Exception
     **/
    List updateTemplateAuth(ChangeTemplateEditorDTO chanceTemplateEditor) throws Exception;

    /**
     * Description 获取超级驳回的可驳回节点
     * @Param flowProcessQuery 流程条件查询实体类
     * @return List
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.14
     * @throws Exception
     **/
    List getProcessSuperRefuseNode(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 获取用户常用回复 uat: http://iflowuat.midea.com/mse/iflow.html?fdrouter=/processForm/Opinion/#/processForm/Opinion/
     * @Param flowProcessQuery 流程条件查询实体类
     * @return List
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.14
     * @throws Exception
     **/
    List selectCommonReply(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 更新流程主题(如果修改成功返回body为空："body":{"data":null,"resultCode":"010","resultMsg":"操作成功"}})
     * @Param chanceProcessSubject 更新流程主题实体类
     * @return List
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.14
     * @throws Exception
     **/
    List updateProcessSubject(ChangeProcessSubjectDTO chanceProcessSubject) throws Exception;

    /**
     * Description 替换流程实例处理人
     * @Param flowProcessQuery 流程条件查询实体类
     * @return Map<String, Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.14
     * @throws Exception
     **/
    Map<String, Object> replaceProcessHandler(FlowProcessQueryDTO flowProcessQuery) throws Exception;

    /**
     * Description 根据accessKeyId和accessKeySecret获取门户IdassToken
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.09
     * @throws
     **/
    String getIdaasToken();
}
