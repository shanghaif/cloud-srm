package com.midea.cloud.srm.feign.workflow;

/**
 * 业务单据需要实现的工作流回调接口，由回调入口动态调用
 * @author lizl7
 *
 */
public interface FlowBusinessCallbackClient {

	/**
	 * 工作流回调入口，需要各个模块实现本模块下的各个功能的动态调用service
	 * @param businessIds
	 * @param objects
	 * @throws Exception
	 */
	public String callbackFlow(String serviceBean, String callbackMethod, Long businessId, String param) throws Exception;
	
}
