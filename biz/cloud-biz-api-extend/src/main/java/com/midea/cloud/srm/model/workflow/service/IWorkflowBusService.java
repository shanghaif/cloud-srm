/**
 * 
 */
package com.midea.cloud.srm.model.workflow.service;

import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;
import com.midea.cloud.srm.model.workflow.dto.CallbackRequestDto;
import com.midea.cloud.srm.model.workflow.dto.CallbackResponseDto;

/**
 * <pre>
 *  对接流程业务接口，需要对接流程的都需要实现
 * </pre>
 *
 * @author nianhuanh@meicloud.com
 * @version 1.00.00
 *
 * <pre>
  *  修改记录
  *  修改后版本:
  *  修改人:
  *  修改日期: 2020年8月10日 上午10:43:37
  *  修改内容:
 * </pre>
 */
public interface IWorkflowBusService {
	
	/**
	 * 获取表单信息
	 * @param requestDto
	 * @return
	 */
	public WorkflowFormDto getFormDto(CallbackRequestDto requestDto);
	
	/**
	 * 驳回
	 * @param requestDto
	 * @return
	 */
	public CallbackResponseDto reject(CallbackRequestDto requestDto);
	
	/**
	 * 审批通过
	 * @param requestDto
	 * @return
	 */
	public CallbackResponseDto pass(CallbackRequestDto requestDto);

}
