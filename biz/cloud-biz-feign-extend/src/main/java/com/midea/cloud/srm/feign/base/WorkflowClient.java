/**
 * 
 */
package com.midea.cloud.srm.feign.base;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.midea.cloud.srm.model.workflow.dto.WorkflowCreateResponseDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;

/**
 * <pre>
 *  
 * </pre>
 *
 * @author nianhuanh@meicloud.com
 * @version 1.00.00
 *
 * <pre>
  *  修改记录
  *  修改后版本:
  *  修改人:
  *  修改日期: 2020年8月10日 下午3:27:10
  *  修改内容:
 * </pre>
 */
@FeignClient(value = "cloud-biz-base", contextId  = "WorkflowClient")
public interface WorkflowClient {

	/**
	 * 提交流程(包含创建流程，更新流程，自动判断是更新还是创建)
	 * @param formDto
	 * @return
	 */
	@PostMapping(value = "/base-anon/internal/workflow/submitFlow")
	WorkflowCreateResponseDto submitFlow(@RequestBody WorkflowFormDto formDto);

	/**
	 * 创建流程
	 * @param formDto
	 * @return
	 */
	@PostMapping(value = "/base-anon/internal/workflow/createFlow")
	WorkflowCreateResponseDto createFlow(@RequestBody WorkflowFormDto formDto);

	/**
	 * 更新流程
	 * @param formDto
	 * @return
	 */
	@PostMapping(value = "/base-anon/internal/workflow/updateFlow")
	WorkflowCreateResponseDto updateFlow(@RequestBody WorkflowFormDto formDto);

}
