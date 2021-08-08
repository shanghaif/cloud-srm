/**
 * 
 */
package com.midea.cloud.srm.base.workflow.consumer.service;

import com.midea.cloud.srm.model.workflow.dto.WorkflowCreateResponseDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;

/**
 * <pre>
 *  流程调用接口
 * </pre>
 *
 * @author nianhuanh@meicloud.com
 * @version 1.00.00
 *
 * <pre>
  *  修改记录
  *  修改后版本:
  *  修改人:
  *  修改日期: 2020年8月10日 上午11:08:55
  *  修改内容:
 * </pre>
 */
public interface IWorkflowProcessService {
	
	/**
	 * 提交
	 * @param formDto
	 * @return
	 * @throws Exception
	 */
	public WorkflowCreateResponseDto submitFlow(WorkflowFormDto formDto) throws Exception;
	
	/**
	 * 创建流程
	 * @param formDto
	 * @return
	 */
	public WorkflowCreateResponseDto createFlow(WorkflowFormDto formDto) throws Exception;
	
	/**
	 * 更新流程
	 * @param formDto
	 * @return
	 */
	public WorkflowCreateResponseDto updateFlow(WorkflowFormDto formDto) throws Exception;

}
