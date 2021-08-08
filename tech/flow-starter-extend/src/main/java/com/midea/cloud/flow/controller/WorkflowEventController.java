package com.midea.cloud.flow.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.flow.model.dto.WorkflowEventDto;
import com.midea.cloud.flow.model.dto.WorkflowInfoDto;
import com.midea.cloud.flow.service.IWorkflowHandlerService;
import com.midea.cloud.srm.model.common.BaseController;

import lombok.extern.slf4j.Slf4j;

/**
 * 工作流前端页面的通用事件，各个模式通用，流程加载前后触发的事件，在这里实现适配第三方工作流的
 * @author lizl7
 *
 */
@RestController
@RequestMapping("/flow/event")
@Slf4j
public class WorkflowEventController extends BaseController  {

    @Value("${oasis.iframeurl:http://10.17.145.73/oasis}")
    private String iframeurl;
    
    @Value("${oasis.restapi.client.baseUrl:http://10.17.145.73/backend/oasis}")
    private String baseUrl;

    @Autowired
    private IWorkflowHandlerService iWorkflowHandlerService;
    
	/**
	 * 加载流程前触发，可以是获取token，参数等操作，适配第三方工作流需要重写（iframe嵌入也可以用）
	 * @param businessType
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "beforeProcess")
	public WorkflowEventDto beforeProcess(@RequestBody String requestBody) throws Exception{
		
		String businessId ="";
		String businessType ="";
		
		Map<String, Object> mapParam =new HashMap<>();
		if( requestBody!=null && !"".equals(requestBody) ) {
			mapParam =JsonUtil.parseJsonStrToMap(requestBody);
		}
		if( mapParam.get("businessId")!=null ) {
			businessId =mapParam.get("businessId").toString();
		}
		if( mapParam.get("businessType")!=null ) {
			businessType =mapParam.get("businessType").toString();
		}

		WorkflowEventDto workflowEventDto =new WorkflowEventDto();
		//公司共性组件工作流，开始
		//根据当前用户生成token，返回前端，提供iframe用于自动登录工作流
        RestTemplate restTemplate = new RestTemplate();
		//例如http://localhost:8888/backend/oasis
		String userId =AppUserUtil.getLoginAppUser().getUserId().toString();
		String userName =AppUserUtil.getLoginAppUser().getUsername();
		String url =baseUrl+"/access-anon/getFrontendToken?userId="+userId+"&userName="+userName;
		String result =restTemplate.getForObject(url, String.class);
		log.info("getFrontendToken - "+url+" - "+result);
		workflowEventDto.setToken(result);

		//获取iframe的地址，例如http://10.17.145.72/oasis
		workflowEventDto.setIframeUrl(iframeurl);
		
		//根据businessId，当前人获取taskId，flowinstanceId
		try {
			//遍历优先获取本人的，注意该接口只有审批中才有数据，要全部待办的都要拿出去，如果是本人的也要设置，提交人没有撤回功能
			String currentTaskUrl =baseUrl+"/access-anon/getUserTaskByBusinessId?businessType="+businessType+"&businessId="+businessId;
			String resultTask =restTemplate.getForObject(currentTaskUrl, String.class);
			log.info("getTaskByBusinessKey - "+currentTaskUrl+" - "+resultTask);
			List<WorkflowInfoDto> listResultData =(List)JsonUtil.parseJsonStrToList(resultTask, WorkflowInfoDto.class);
			if( listResultData!=null && listResultData.size()>0 ) {
				WorkflowInfoDto workflowInfoDtoOne =null;
				//优先用当前用户的，没有的话用第一个，如果不是自己的待办，引擎会判断不能操作
				workflowInfoDtoOne =listResultData.get(0);
				for( WorkflowInfoDto workflowInfoDtoTemp : listResultData ) {
					if( userId.equals( workflowInfoDtoTemp.getAssignee() ) ) {
						workflowInfoDtoOne =workflowInfoDtoTemp;
					}
				}
				workflowEventDto.setTaskId(workflowInfoDtoOne.getTaskId());
				workflowEventDto.setFlowinstanceId(workflowInfoDtoOne.getProcessInstanceId());
			}else {
				//完结的流程要通过另外一个接口找历史任务节点，进行显示，前端直接传flowinstanceId会报错
				currentTaskUrl =baseUrl+"/access-anon/getTaskLastByBusinessId?businessType="+businessType+"&businessId="+businessId;
				resultTask =restTemplate.getForObject(currentTaskUrl, String.class);
				log.info("getTaskLastByBusinessId - "+resultTask);
				Map<String, Object> mapTemp =JsonUtil.parseJsonStrToMap(resultTask);
				if( mapTemp!=null && mapTemp.get("procInstId")!=null ) {
					workflowEventDto.setTaskId(mapTemp.get("id").toString());
					workflowEventDto.setFlowinstanceId(mapTemp.get("procInstId").toString());

				}
			}
			
//			Map<String, Object> mapResultTask =(Map)JsonUtil.parseJsonStrToMap(resultTask);
//			List<Map<String, Object>> listResultData =(List)JsonUtil.parseJsonStrToList(mapResultTask.get("resultData").toString(), HashMap.class);
//			if( listResultData!=null && listResultData.size()>0 ) {
//				Map<String, Object> mapOne =listResultData.get(0);
//				workflowEventDto.setTaskId(mapOne.get("taskId").toString());
//				workflowEventDto.setFlowinstanceId(mapOne.get("procInstId").toString());
//			}else {
//				//只获取实例id
//				url =baseUrl+"/access-anon/getInstanceInfoByBusinessId?businessId="+businessId;
//				String resultInstance =restTemplate.getForObject(url, String.class);
//				log.info("getInstanceInfoByBusinessId - "+url+" - "+resultInstance);
//				Map<String, Object> mapTemp =JsonUtil.parseJsonStrToMap(resultInstance);
//				if( mapTemp!=null && mapTemp.get("actInstanceId")!=null ) {
//					workflowEventDto.setFlowinstanceId(mapTemp.get("actInstanceId").toString());
//				}
//			}
			
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
		//公司共性组件工作流，结束

		return workflowEventDto;
	}

	@GetMapping(value = "queryTodo")
	public List<Map<String, Object>> listQueryTodo(String businessType) throws Exception{
		return this.queryTodo(businessType);
	}
	/**
	 * 查询待办列表，业务类型可选，适配第三方工作流需要重写
	 * @param businessType
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "queryTodo")
	public List<Map<String, Object>> queryTodo(String businessType) throws Exception{

		Map<String, Object> mapResult =new HashMap<String, Object>();
		List<Map<String, Object>> listResult =new ArrayList<Map<String,Object>>();

		//公司共性组件工作流，开始
		 RestTemplate restTemplate = new RestTemplate();
		//例如http://localhost:8888/backend/oasis
		 String userId =AppUserUtil.getLoginAppUser().getUserId().toString();
		String url =baseUrl+"/access-anon/getUserTaskByEmpId?empId="+userId+"&pageIndex=1&pageSize=100";
		String result =restTemplate.getForObject(url, String.class);
		
		//封装为前端需要的数据结构
		log.info("getUserTaskByEmpId - "+result);
		List<WorkflowInfoDto> listResultData =(List)JsonUtil.parseJsonStrToList(result, WorkflowInfoDto.class);
		//过滤单据类型
		if( businessType!=null && !"".equals(businessType) ) {
			if( listResultData!=null && listResultData.size()>0 ) {
				List<WorkflowInfoDto> listResultDataFilter =new ArrayList<WorkflowInfoDto>();
				for( WorkflowInfoDto workflowInfoDtoTemp : listResultData ) {
					if( workflowInfoDtoTemp.getProcessDefinitionId().startsWith(businessType) ) {
						listResultDataFilter.add(workflowInfoDtoTemp);
					}
				}
				listResultData =listResultDataFilter;
			}
		}
		
		//封装为前端需要的数据结构
		if( listResultData!=null && listResultData.size()>0 ) {
			for( WorkflowInfoDto workflowInfoDtoTemp : listResultData ) {
				Map<String, Object> mapDataTemp =new HashMap<String, Object>();
				mapDataTemp.put("businessId",workflowInfoDtoTemp.getBusinessKey());
				mapDataTemp.put("businessType",workflowInfoDtoTemp.getProcessDefinitionId().split(":")[0]);
				mapDataTemp.put("userId",workflowInfoDtoTemp.getAssignee());
				mapDataTemp.put("flowinstanceId",workflowInfoDtoTemp.getProcessInstanceId());
				mapDataTemp.put("title",workflowInfoDtoTemp.getProcessName()+"-"+workflowInfoDtoTemp.getTaskName());
				listResult.add(mapDataTemp);
			}
		}
		//公司共性组件工作流，结束

		return listResult;
	}
	
	/**
	 * 工作流提交入口，适配第三方工作流需要重写，单纯处理工作流，不处理业务，由前端提交按钮触发，或者后端方法触发，主要用于推送流程数据，有提交动作涉及到前置校验，数据保存等，不适合采用回调业务的提交方法
	 * @return
	 */
	@PostMapping(value = "submitEngine")
	private String submitEngine(@RequestBody String requestBody) throws Exception{
		//注入不了，不清楚为什么，临时用这种方式
		Map<String, Object> mapParam =JsonUtil.parseJsonStrToMap(requestBody);
		Long businessId =Long.parseLong(mapParam.get("businessId").toString());
		String businessType =mapParam.get("businessType").toString();
		String businessData =null;
		if( mapParam.get("businessData")!=null ) {
			businessData =JsonUtil.entityToJsonStr(mapParam.get("businessData"));
		}
		iWorkflowHandlerService =SpringContextHolder.getBean(IWorkflowHandlerService.class);
		return iWorkflowHandlerService.submitEngine(businessId, businessType, businessData);
	}

	/**
	 * 旧代码触发后续处理是通过服务之间触发，保留实现，逐步会取消，新的规范都是通过前端触发
	 * @return
	 */
	@PostMapping(value = "submitFlow")
	private String submitFlow(Long businessId, String businessType, String clazz) throws Exception{
		//注入不了，不清楚为什么，临时用这种方式
		iWorkflowHandlerService =SpringContextHolder.getBean(IWorkflowHandlerService.class);
		return iWorkflowHandlerService.submitEngine(businessId, businessType, null);
	}
	
	
	/**
	 * 根据业务类型获取是否启用
	 * @param businessType
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "getIsEnableFlow")
	public Boolean getIsEnableFlow(@RequestBody String requestBody) throws Exception{
		String businessType ="";
		Map<String, Object> mapParam =new HashMap<>();
		if( requestBody!=null && !"".equals(requestBody) ) {
			mapParam =JsonUtil.parseJsonStrToMap(requestBody);
		}
		if( mapParam.get("businessType")!=null ) {
			businessType =mapParam.get("businessType").toString();
		}
		//注入不了，不清楚为什么，临时用这种方式
		iWorkflowHandlerService =SpringContextHolder.getBean(IWorkflowHandlerService.class);
		return iWorkflowHandlerService.getIsEnableFlow(businessType);
	}
	
	/**
	 *  获取集成模式
	 * @param businessType
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "getFlowIntegrationMode")
	public String getFlowIntegrationMode(@RequestBody String requestBody) throws Exception{
		String businessType ="";
		Map<String, Object> mapParam =new HashMap<>();
		if( requestBody!=null && !"".equals(requestBody) ) {
			mapParam =JsonUtil.parseJsonStrToMap(requestBody);
		}
		if( mapParam.get("businessType")!=null ) {
			businessType =mapParam.get("businessType").toString();
		}
		//注入不了，不清楚为什么，临时用这种方式
		iWorkflowHandlerService =SpringContextHolder.getBean(IWorkflowHandlerService.class);
		return iWorkflowHandlerService.getFlowIntegrationMode(businessType);
	}

}
