package com.midea.cloud.flow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.flow.model.dto.WorkflowEventDto;
import com.midea.cloud.flow.service.IWorkflowHandlerService;
import com.midea.cloud.flow.service.IWorkflowSelfService;
import com.midea.cloud.srm.model.common.BaseController;

import lombok.extern.slf4j.Slf4j;

/**
 * 工作流操作，只用于自带界面模式，对于自带页面的情况，所有界面操作都独立的类中实现，因为这是只有这个模式特有的，不要和WorkflowHandlerServiceImpl一起，在这里实现适配第三方工作流
 * @author lizl7
 *
 */
@RestController
@RequestMapping("/flow/self")
@Slf4j
public class WorkflowSelfController extends BaseController  {
	
    @Autowired
    private IWorkflowSelfService iWorkflowSelfService;
    
	/**
	 * 工作流提交入口，适配第三方工作流需要重写，单纯处理工作流，不处理业务，由前端提交按钮触发，或者后端方法触发，主要用于推送流程数据，有提交动作涉及到前置校验，数据保存等，不适合采用回调业务的提交方法
	 * @return
	 */
	@PostMapping(value = "submit")
	private String submit(Long businessId, String businessType) throws Exception{
		return iWorkflowSelfService.submit(businessId, businessType);
	}
	
	/**
	 * 工作流审批入口，适配第三方工作流需要重写，单纯处理工作流，不处理业务，业务统一通过回调触发，适用于自带界面模式
	 * @return
	 */
	@PostMapping(value = "audit")
	private String audit(Long businessId, String businessType) throws Exception{
		return iWorkflowSelfService.audit(businessId, businessType);
	}
	
	
	/**
	 * 根据业务id，业务类型获取工作流节点列表入口，提交时候用，需要根据业务字段判断加载分支作，适配第三方工作流需要重写，适用于自带界面模式
	 * @param businessId
	 * @param businessType
	 * @param businessVariables
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "queryFlowNodeForSubmit")
	public String queryFlowNodeForSubmit(String businessId, String businessType, String businessVariables) throws Exception {
		return iWorkflowSelfService.queryFlowNodeForSubmit(Long.parseLong(businessId), businessType);
	}
	
	/**
	 * 根据业务id，业务类型获取工作流实例入口，适配第三方工作流需要重写，适用于自带界面模式
	 * @param businessId
	 * @param businessType
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "queryFlowInstanceByBusiness.ac")
	public String queryFlowInstanceByBusiness(String businessId,String businessType) throws Exception{
		return iWorkflowSelfService.queryFlowInstanceByBusiness(Long.parseLong(businessId), businessType);
	}
	
	/**
	 * 获取已经完成的节点入口，用于驳回到指定节点，适配第三方工作流需要重写，适用于自带界面模式
	 * @param flowinstanceId
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "queryFlowNodeDone.ac")
	public String queryFlowNodeDone(String flowinstanceId) throws Exception{
		return null;
	}
	
	/**
	 * 根据业务id，业务类型获取工作流流程节点入口，适配第三方工作流需要重写，适用于自带界面模式
	 * @param flowinstanceId
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "queryFlowNodeByFlowinstanceId.ac")
	public String queryFlowNodeByFlowinstanceId(String flowinstanceId) throws Exception{
		return null;
	}
	
	/**
	 * 根据实例id获取工作流流审批记录入口，适配第三方工作流需要重写，适用于自带界面模式
	 * @param flowinstanceId
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "queryFlowLogByFlowinstanceId.ac")
	public String queryFlowLogByFlowinstanceId(String flowinstanceId) throws Exception{
		return null;
	}

	/**
	 * 根据实例id获取获取当前节点入口，适配第三方工作流需要重写，适用于自带界面模式
	 * @param businessType
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "queryCurrentModelByBusinessType.ac")
	public String queryCurrentModelByBusinessType(String businessType) throws Exception{
		return null;
	}
	
}
