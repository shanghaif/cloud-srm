package com.midea.cloud.srm.sup.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midea.cloud.srm.model.common.WorkflowCommonController;
import com.midea.cloud.srm.model.workflow.dto.CallbackRequestDto;
import com.midea.cloud.srm.model.workflow.dto.CallbackResponseDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;
import com.midea.cloud.srm.sup.demo.service.IDemoService;

/**
 * 
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
  *  修改日期: 2020年8月10日 下午2:30:39
  *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping(value = "/demo")
public class DemoController extends WorkflowCommonController{
	
	@Autowired
	private IDemoService demoService;
	
	@GetMapping(value = "/test")
	public String test() {
		return "hello world!!!";
	}
	
	@PostMapping(value = "/submit")
	public String submit(Object obj) {
		return demoService.submit(obj);
	}
	
	@Override
	@PostMapping(value = "/getFormDto")
	public WorkflowFormDto getFormDto(@RequestBody CallbackRequestDto requestDto) {
		Assert.notNull(requestDto, "request is nullable");
		return demoService.getFormDto(requestDto);
	}

	@Override
	@PostMapping(value = "/reject")
	public CallbackResponseDto reject(@RequestBody CallbackRequestDto requestDto) {
		Assert.notNull(requestDto, "request is nullable");
		return demoService.reject(requestDto);
	}

	@Override
	@PostMapping(value = "/pass")
	public CallbackResponseDto pass(@RequestBody CallbackRequestDto requestDto) {
		Assert.notNull(requestDto, "request is nullable");
		return demoService.pass(requestDto);
	}

}
