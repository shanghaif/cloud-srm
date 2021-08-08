package com.midea.cloud.flow.service;

import com.midea.cloud.common.annotation.CacheData;
import com.midea.cloud.common.constants.RedisKey;
import com.midea.cloud.flow.model.dto.WorkflowCallbackDto;
import com.midea.cloud.flow.model.dto.WorkflowTodoDto;
import com.midea.cloud.srm.model.flow.process.entity.TemplateHeader;

/**
 * 工作流服务类，工作流的入口，适配第三方工作流接口
 * @author lizl7
 *
 */
public interface IWorkflowHandlerService {
	
	/**
	 * 回调流程(包含审批通过，驳回，撤回)，接收工作流引擎的回调事件
	 * @param id
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public String callback(WorkflowCallbackDto workflowCallbackDto)  throws Exception;

	@CacheData(keyName = RedisKey.WORKFLOW_TEMPLATE_HEADER,cacheTime = 3600*6,interfaceName = "获取流程接口")
	TemplateHeader getTemplateHeaderCache(String businessType);

	/**
	 * 统一待办接收入口
	 * @param id
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public String todo(WorkflowTodoDto workflowTodoDto)  throws Exception;
	
	/**
	 * 提交流程，由业务模块的提交动作触发，根据不同的集成模式处理后续操作，例如推送oa,直接审批通过（一定要业务事务完成后才能做），如果是iframe集成模式的话用不需要这个
	 * @param id
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public String submitEngine(Long businessId, String businessType, String businessData)  throws Exception;
	
	/**
	 * 获取流程用到的业务变量，个别工作流是通过回调用的方式获取
	 * @return
	 * @throws Exception
	 */
	public String getVariableFlow(Long businessId, String businessType) throws Exception;
	
	/**
	 * 根据业务类型获取是否启用
	 * @param id
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Boolean getIsEnableFlow(String businessType)  throws Exception;
	
	/**
	 * 获取集成模式
	 * @return
	 * @throws Exception
	 */
	public String getFlowIntegrationMode(String businessType) throws Exception;
	
}
