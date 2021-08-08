/**
 * 
 */
package com.midea.cloud.srm.base.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.workflow.entity.SrmFlowBusWorkflow;

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
  *  修改日期: 2020年8月11日 上午10:29:42
  *  修改内容:
 * </pre>
 */
public interface ISrmFlowBusWorkflowService extends IService<SrmFlowBusWorkflow>{
	
	/**
	 * 
	 * @param formId
	 * @return
	 */
	public SrmFlowBusWorkflow findByFormId(String formId);

	/**
	 * 根据单据id获取流程OA流程id
	 * @param formId
	 * @return
	 */
	String getFlowIdByFormId(Long formId);
}
