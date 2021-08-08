package com.midea.cloud.flow.service;

import com.midea.cloud.flow.model.dto.WorkflowCallbackDto;
import com.midea.cloud.flow.model.dto.WorkflowTodoDto;

/**
 * 工作流操作，只用于自带界面模式，对于自带页面的情况，所有界面操作都独立的类中实现，因为这是只有这个模式特有的，在这里实现适配第三方工作流
 * @author lizl7
 *
 */
public interface IWorkflowSelfService {
	
	/**
	 * 提交流程
	 * @param id
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public String submit(Long businessId, String businessIdType)  throws Exception;
	
	/**
	 * 审批
	 * @param id
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public String audit(Long businessId, String businessIdType)  throws Exception;
	
	/**
	 * 查询节点，用于提交
	 * @param id
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public String queryFlowNodeForSubmit(Long businessId, String businessType)  throws Exception;
	
	/**
	 * 根据业务id，业务类型获取工作流实例入口
	 * @param id
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public String queryFlowInstanceByBusiness(Long businessId, String businessType) throws Exception;
	
	
}
