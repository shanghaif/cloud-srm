/**
 * 
 */
package com.midea.cloud.srm.base.workflow.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midea.cloud.srm.base.workflow.consumer.service.IFlowService;
import com.midea.cloud.srm.base.workflow.consumer.service.IWorkflowProcessService;
import com.midea.cloud.srm.model.common.BaseController;
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
  *  修改日期: 2020年8月10日 上午11:19:35
  *  修改内容:
 * </pre>
 */
@RestController
public class WorkflowController extends BaseController {
	
	@Autowired
	private IWorkflowProcessService workflowProcessService;
	@Autowired
	private IFlowService iFlowService;
	
	/**
	 * 提交流程
	 * @param formDto
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/base-anon/internal/workflow/submitFlow")
	public WorkflowCreateResponseDto submitFlow(@RequestBody WorkflowFormDto formDto) throws Exception {
		verifyValue(formDto);
		//kuangzm  工作流拉通需要先直接跳过
		return iFlowService.approved(formDto.getFdTemplateId(), Long.valueOf(formDto.getFormInstanceId()));
//		return workflowProcessService.submitFlow(formDto);
	}
	
	/**
	 * 创建流程
	 * @param formDto
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value = "/base-anon/internal/workflow/createFlow")
	public WorkflowCreateResponseDto createFlow(@RequestBody WorkflowFormDto formDto) throws Exception {
		verifyValue(formDto);
		//kuangzm	工作流拉通需要先直接跳过
		return iFlowService.approved(formDto.getFdTemplateId(), Long.valueOf(formDto.getFormInstanceId()));
//		return workflowProcessService.createFlow(formDto);
	}
	
	/**
	 * 更新流程
	 * @param formDto
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value = "/base-anon/internal/workflow/updateFlow")
	public WorkflowCreateResponseDto updateFlow(@RequestBody WorkflowFormDto formDto) throws Exception {
		verifyValue(formDto);
		return workflowProcessService.updateFlow(formDto);
	}
	
	/**
	 * 参数校验
	 * @param formDto
	 */
	private static void verifyValue(WorkflowFormDto formDto) {
		Assert.notNull(formDto, "参数不能为空");
//		Assert.notNull(formDto.getFormInstanceId(), "业务实例ID不能为空");
	}

}
