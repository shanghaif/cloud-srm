package com.midea.cloud.flow.webservice;

import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.flow.model.dto.WorkflowCallbackDto;
import com.midea.cloud.flow.service.IWorkflowHandlerService;

import lombok.extern.slf4j.Slf4j;

/**
 * 工作流回调入口，webservice方式，适配第三方工作流的入口
 * 
 * <pre>
 * 。
 * </pre>
 * 
 * @author lizl7
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@WebService(targetNamespace = "http://ws.flow.srm.meicloud.com")
@Controller//为了触发spring实例化bean
@Slf4j
public class WorkflowCallbackWebserviceImpl implements WorkflowCallbackWebservice {


    @Autowired
    private IWorkflowHandlerService iWorkflowHandlerService;
    
	/**
	 * 统一接收回调
	 */
	@Override
	public String callBack(String jsonStr) {
		try {
			//调用后端处理，授权校验，后端业务处理处罚等等
			Map<String, Object> param =new HashMap<String, Object>();
			//按照格式构造WorkflowCallbackDto
			WorkflowCallbackDto workflowCallbackDto =new WorkflowCallbackDto();
			workflowCallbackDto.setParam(param);

			iWorkflowHandlerService.callback(workflowCallbackDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String,Object> mapResult =new HashMap<String, Object>();
		mapResult.put("code", "100");
		mapResult.put("message", "success");
		return JsonUtil.entityToJsonStr(mapResult);
	}

}
