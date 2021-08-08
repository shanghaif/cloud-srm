/**
 * 
 */
package com.midea.cloud.srm.base.workflow.provider.webservice.impl;

import javax.jws.WebService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.srm.base.workflow.provider.webservice.WorkflowProviderWebService;
import com.midea.cloud.srm.model.base.soap.erp.dto.REQUEST;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse.RESPONSE;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse.RESPONSE.ResultInfo;
import com.midea.cloud.srm.model.common.WorkflowCallbackService;
import com.midea.cloud.srm.model.workflow.dto.CallbackRequestDto;
import com.midea.cloud.srm.model.workflow.dto.CallbackResponseDto;

/**
 * <pre>
 *  路程回调
 * </pre>
 *
 * @author nianhuanh@meicloud.com
 * @version 1.00.00
 *
 * <pre>
  *  修改记录
  *  修改后版本:
  *  修改人:
  *  修改日期: 2020年8月7日 下午6:32:40
  *  修改内容:
 * </pre>
 */

@WebService(targetNamespace = "http://www.aurora-framework.org/schema", endpointInterface = "com.midea.cloud.srm.base.workflow.provider.webservice.WorkflowProviderWebService")
@Component
@Slf4j
public class WorkflowProviderWebServiceImpl implements WorkflowProviderWebService {
	
	private static final String SUCCESS = "SUCCESSED";
	
	private static final String FAIL = "N";
	
	private static final String FLAG_PASS = "Y";
	
	private static final String FLAG_REJECT = "N";
	
	@Override
	public SoapResponse execute(REQUEST request) {
		SoapResponse soapResponse = new SoapResponse();
		RESPONSE response = new RESPONSE();
		ResultInfo resultInfo = new ResultInfo();
		try {
			
			// 根据流程引擎回调参数，调用不同接口
			String formTemplateId = getFormTemplateId(request);
			String flag = getFlag(request);
			String formInstanceId = getFormInstanceId(request);
			
			CallbackResponseDto notifyResponse = null;
			
			// 审批通过回调
			if (FLAG_PASS.contentEquals(flag)) {
				notifyResponse = notifyPass(formTemplateId, formInstanceId);
			// 驳回回调
			} else if (FLAG_REJECT.equals(flag)) {
				notifyResponse = notifyReject(formTemplateId, formInstanceId);
			} else {
				throw new RuntimeException("不存在的参数");
			}
			
			System.err.println(JSONObject.toJSONString(notifyResponse));
			// 回调处理成功
			resultInfo.setRESPONSESTATUS(SUCCESS);
			resultInfo.setRESPONSEMESSAGE(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作失败",e);
			// 回调处理失败
			resultInfo.setRESPONSESTATUS(FAIL);
			resultInfo.setRESPONSEMESSAGE(e.getLocalizedMessage());
		}
		response.setResultInfo(resultInfo);
		soapResponse.setResponse(response);
		return soapResponse;
	}
	
	/**
	 * 获取流程ID
	 * @param request
	 * @return
	 */
	private String getFormInstanceId(REQUEST request) {
		// TODO
		
		return "";
	}

	/**
	 * 获取FLAG，N为驳回，Y为通过
	 * @param request
	 * @return
	 */
	private String getFlag(REQUEST request) {
		// TODO
		
		return FLAG_PASS;
	}
	
	/**
	 * 获取模板ID
	 * @param request
	 * @return
	 */
	private String getFormTemplateId(REQUEST request) {
		// TODO
		
		return "DEMO";
	}
	
	/**
	 * 
	 * @param requestDto
	 * @return
	 */
	private CallbackResponseDto notifyPass(String formTemplateId, String formInstanceId) {
		return WorkflowCallbackService.getCallbackService(formTemplateId).pass(CallbackRequestDto.build(formInstanceId));
	}
	
	/**
	 * 
	 * @param requestDto
	 * @return
	 */
	private CallbackResponseDto notifyReject(String formTemplateId, String formInstanceId) {
		return WorkflowCallbackService.getCallbackService(formTemplateId).reject(CallbackRequestDto.build(formInstanceId));
	}
}
