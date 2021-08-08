package com.midea.cloud.flow.common.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.flow.common.service.IWorkflowService;
import com.midea.cloud.srm.model.common.BaseController;

@RestController
@RequestMapping("/flow")
public class FlowController extends BaseController  {
	
	/**
	 * 提交流程
	 * @return
	 */
	@PostMapping(value = "/submit")
	private String submit(Long id , String type,String clazz) throws Exception{
		IWorkflowService iworkflow =  SpringContextHolder.getBean(clazz);
		return iworkflow.submit(id, type);
	}
	
}
