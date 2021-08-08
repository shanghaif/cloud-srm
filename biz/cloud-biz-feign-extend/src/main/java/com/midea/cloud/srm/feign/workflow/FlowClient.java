/**
 * 
 */
package com.midea.cloud.srm.feign.workflow;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.midea.cloud.srm.model.workflow.dto.WorkflowCreateResponseDto;

// 转到base模块，以starter方式
//@FeignClient(value = "flow-center", contextId  = "FlowClient")
@FeignClient(value = "cloud-biz-base", contextId  = "FlowClient")
public interface FlowClient {

	/**
	 * 旧代码触发后续处理是通过服务之间触发，保留实现，逐步会取消，新的规范都是通过前端触发
	 * @param formDto
	 * @return
	 */
	@PostMapping(value = "/flow/event/submitFlow")
	String submitFlow(@RequestParam("businessId") Long businessId , @RequestParam("businessType") String businessType, @RequestParam("clazz") String clazz);

	
	
//	/**
//	 * 提交流程，由业务模块的提交动作触发，根据不同的集成模式处理后续操作，例如推送oa,直接审批通过（一定要业务事务完成后才能做），如果是iframe集成模式的话用不需要这个
//	 * @param formDto
//	 * @return
//	 */
//	@PostMapping(value = "/flow/event/submitFlow")
//	String submitFlow(@RequestParam("businessId") Long businessId , @RequestParam("businessType") String businessType, @RequestParam("clazz") String clazz);

//	/**
//	 * 回调流程(包含审批通过，驳回，撤回)，接收工作流引擎的回调事件
//	 * @param formDto
//	 * @return
//	 */
//	@PostMapping(value = "/flow/callback")
//	String callbackFlow(@RequestParam("id") Long id , @RequestParam("type") String type,@RequestParam("clazz") String clazz);
	
//	/**
//	 * 创建流程
//	 * @param formDto
//	 * @return
//	 */
//	@PostMapping(value = "/base-anon/internal/workflow/createFlow")
//	WorkflowCreateResponseDto createFlow(@RequestBody WorkflowFormDto formDto);
//
//	/**
//	 * 更新流程
//	 * @param formDto
//	 * @return
//	 */
//	@PostMapping(value = "/base-anon/internal/workflow/updateFlow")
//	WorkflowCreateResponseDto updateFlow(@RequestBody WorkflowFormDto formDto);

}
