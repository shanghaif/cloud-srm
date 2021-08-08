/**
 * 
 */
package com.midea.cloud.srm.base.workflow.provider.callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.midea.cloud.srm.model.common.WorkflowCallbackService;
import com.midea.cloud.srm.model.workflow.dto.CallbackRequestDto;
import com.midea.cloud.srm.model.workflow.dto.CallbackResponseDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;

/**
 * <pre>
 *  Demo回调
 *  Demo回调将FeignClient与service写在一起，便于查看理解
 * </pre>
 *
 * @author nianhuanh@meicloud.com
 * @version 1.00.00
 *
 * <pre>
  *  修改记录
  *  修改后版本:
  *  修改人:
  *  修改日期: 2020年8月11日 下午2:16:00
  *  修改内容:
 * </pre>
 */

@Service
public class DemoCallbackServiceImpl implements WorkflowCallbackService {
	
	@Autowired
	private DemoWorkflowCallbackClient demoCallbackClient;
	
	public DemoCallbackServiceImpl() {
		System.err.println(123);
	}

	@Override
	public WorkflowFormDto getFormDto(CallbackRequestDto requestDto) {
		return demoCallbackClient.getFormDto(requestDto);
	}

	@Override
	public CallbackResponseDto reject(CallbackRequestDto requestDto) {
		return demoCallbackClient.reject(requestDto);
	}

	@Override
	public CallbackResponseDto pass(CallbackRequestDto requestDto) {
		return demoCallbackClient.pass(requestDto);
	}

	@Override
	public String getTemplateId() {
		return "DEMO";
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		CALLBACK_SERVICE_MAP.put(getTemplateId(), this);
	}
	
	@FeignClient(name = "cloud-biz-supplier", contextId = "DemoWorkflowCallbackClient")
	public interface DemoWorkflowCallbackClient {
		
		/**
		 * 获取表单业务数据
		 * @param requestDto
		 * @return
		 */
		@PostMapping(value = "/demo/getFormDto")
		public WorkflowFormDto getFormDto(@RequestBody CallbackRequestDto requestDto);
		
		/**
		 * 驳回处理
		 * @param requestDto
		 * @return
		 */
		@PostMapping(value = "/demo/reject")
		public CallbackResponseDto reject(@RequestBody CallbackRequestDto requestDto);
		
		/**
		 * 审批通过
		 * @param requestDto
		 * @return
		 */
		@PostMapping(value = "/demo/pass")
		public CallbackResponseDto pass(@RequestBody CallbackRequestDto requestDto);
		
	}
}
