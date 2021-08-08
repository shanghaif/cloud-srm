package com.midea.cloud.flow.webservice;

import javax.jws.WebService;

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
public interface WorkflowCallbackWebservice {
	
	/**
	 * 统一接收回调
	 */
	public String callBack(String jsonStr);

}
