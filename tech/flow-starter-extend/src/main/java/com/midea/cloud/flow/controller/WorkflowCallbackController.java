package com.midea.cloud.flow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.flow.model.dto.Result;
import com.midea.cloud.flow.model.dto.WorkflowEventDto;
import com.midea.cloud.srm.model.common.BaseController;

import lombok.extern.slf4j.Slf4j;

/**
 * 工作流回调，各个模式通用，在这里实现适配第三方工作流
 * @author lizl7
 *
 */
@Controller
@RequestMapping("/flow-anon/external/workflowcallback")
@Slf4j
public class WorkflowCallbackController extends BaseController  {

	/**
	 * 检查接口授权信息
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public boolean checkAccessAuthorize(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		//this.writeToStreamForJsonObject(httpServletResponse, Result.fail("非法访问"));
		return true;
	}
	
	/**
	 * 统一接收回调
	 */
	@RequestMapping(value = "callback.ac", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView callback(@RequestBody String requestBody,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception{
		if( !checkAccessAuthorize(httpServletRequest, httpServletResponse) ) {
			return null;
		}
//		//调用后端处理，授权校验，后端业务处理触发等等
//		WorkflowHandlerService workflowHandlerService =(WorkflowHandlerService)ContextUtils.getBeanConsumer(WorkflowHandlerService.class);
//		Map<String, Object> param =new HashMap<String, Object>();
//		//按照格式构造WorkflowCallbackDto
//		WorkflowCallbackDto workflowCallbackDto =new WorkflowCallbackDto();
//		workflowCallbackDto.setParam(param);
//
//		workflowHandlerService.callback(workflowCallbackDto);
//		
//		Map<String,Object> mapResult =new HashMap<String, Object>();
//		mapResult.put("code", "100");
//		mapResult.put("message", "success");
//		this.writeToStreamForJsonObject(httpServletResponse, mapResult);
		return null;
	}
	
}
