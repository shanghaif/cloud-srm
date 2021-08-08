package com.midea.cloud.srm.model.workflow.service;

/**
 * 业务单据需要实现的工作流回调接口，由回调入口动态调用
 * @author lizl7
 *
 */
public interface IFlowBusinessCallbackService {

	/**
	 * 提交，注意这个是firame模式，提交工作流流程后才回调触发业务的提交动作
	 * @param businessIds
	 * @throws Exception
	 */
	public void submitFlow(Long businessId, String param) throws Exception;
	
	/**
	 * 通过
	 * @param businessIds
	 * @param objects
	 * @throws Exception
	 */
	public void passFlow(Long businessId, String param) throws Exception;
	
	/**
	 * 驳回
	 * @param businessIds
	 * @throws Exception
	 */
	public void rejectFlow(Long businessId, String param) throws Exception;
	
	/**
	 * 撤回
	 * @param businessIds
	 * @throws Exception
	 */
	public void withdrawFlow(Long businessId, String param) throws Exception;
	
	/**
	 * 废弃
	 * @param businessIds
	 * @throws Exception
	 */
	public void destoryFlow(Long businessId, String param) throws Exception;

	/**
	 * 获取流程用到的业务变量，用于分支等，这里根据业务需要返回具体的分支
	 * @param businessIds
	 * @throws Exception
	 */
	public String getVariableFlow(Long businessId, String param) throws Exception;

	/**
	 * 获取业务推送数据，用于推送模式下，推送到oa的数据，这里根据具体的OA需要返回数据
	 * @param businessIds
	 * @throws Exception
	 */
	public String getDataPushFlow(Long businessId, String param) throws Exception;
	
	
}
