/**
 * 
 */
package com.midea.cloud.srm.model.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import com.midea.cloud.srm.model.workflow.dto.CallbackRequestDto;
import com.midea.cloud.srm.model.workflow.dto.CallbackResponseDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;

/**
 * <pre>
 *  回调接口
 * </pre>
 *
 * @author nianhuanh@meicloud.com
 * @version 1.00.00
 *
 * <pre>
  *  修改记录
  *  修改后版本:
  *  修改人:
  *  修改日期: 2020年8月11日 下午2:04:03
  *  修改内容:
 * </pre>
 */
public interface WorkflowCallbackService extends InitializingBean {
	
	public static final Map<String, WorkflowCallbackService> CALLBACK_SERVICE_MAP = new HashMap<>();
	
	public static WorkflowCallbackService getCallbackService(String templateId) {
		return CALLBACK_SERVICE_MAP.get(templateId);
	}
	
	/**
	 * 根据请求条件获取业务表单信息
	 * @param requestDto
	 * @return
	 */
	public abstract WorkflowFormDto getFormDto(CallbackRequestDto requestDto);

	/**
	 * 审批驳回
	 * @param requestDto
	 * @return
	 */
	public abstract CallbackResponseDto reject(CallbackRequestDto requestDto);
	
	/**
	 * 审批通过
	 * @param requestDto
	 * @return
	 */
	public abstract CallbackResponseDto pass(CallbackRequestDto requestDto);
	
	/**
	 * 获取流程模板ID
	 */
	public abstract String getTemplateId();

	
}
