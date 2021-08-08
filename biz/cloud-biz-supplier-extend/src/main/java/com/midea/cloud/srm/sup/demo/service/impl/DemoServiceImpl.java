/**
 * 
 */
package com.midea.cloud.srm.sup.demo.service.impl;

import com.midea.cloud.srm.feign.base.WorkflowClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.midea.cloud.srm.model.workflow.dto.CallbackRequestDto;
import com.midea.cloud.srm.model.workflow.dto.CallbackResponseDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;
import com.midea.cloud.srm.sup.demo.service.IDemoService;

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
  *  修改日期: 2020年8月10日 下午2:31:30
  *  修改内容:
 * </pre>
 */
@Service
public class DemoServiceImpl implements IDemoService {
	
	private static final Logger _logger = LoggerFactory.getLogger(DemoServiceImpl.class);
	
	@Autowired
	private WorkflowClient workflowClient;

	@Override
	public WorkflowFormDto getFormDto(CallbackRequestDto requestDto) {
		_logger.info("获取业务表单数据");
		// TODO
		
		return new WorkflowFormDto();
	}

	@Override
	public CallbackResponseDto reject(CallbackRequestDto requestDto) {
		_logger.info("流程驳回处理");
		// TODO
		
		return new CallbackResponseDto();
	}

	@Override
	public CallbackResponseDto pass(CallbackRequestDto requestDto) {
		_logger.info("流程审批通过处理");
		// TODO
		
		return new CallbackResponseDto();
	}

	/* (non-Javadoc)
	 * @see com.midea.cloud.srm.sup.demo.service.IDemoService#submit()
	 */
	@Override
	public String submit(Object obj) {
		String formId = doSth(obj);
		WorkflowFormDto formDto = new WorkflowFormDto();
		formDto.setFormInstanceId(formId);
		workflowClient.createFlow(formDto);
		return formId;
	}

	/**
	 * 自行处理业务
	 * @param obj
	 * @return
	 */
	private String doSth(Object obj) {
		
		// TODO
		
		// 业务处理
		return "formId";
	}
}
