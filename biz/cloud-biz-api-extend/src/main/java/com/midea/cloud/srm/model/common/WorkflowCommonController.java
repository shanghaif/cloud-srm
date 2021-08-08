/**
 * 
 */
package com.midea.cloud.srm.model.common;

import com.midea.cloud.srm.model.workflow.dto.CallbackRequestDto;
import com.midea.cloud.srm.model.workflow.dto.CallbackResponseDto;
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
  *  修改日期: 2020年8月10日 下午2:23:59
  *  修改内容:
 * </pre>
 */
public abstract class WorkflowCommonController extends BaseController {
	
	
	/**
	 * 获取表单信息
	 * @param requestDto
	 * @return
	 */
	public abstract WorkflowFormDto getFormDto(CallbackRequestDto requestDto);
	
	/**
	 * 驳回
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

	
}
