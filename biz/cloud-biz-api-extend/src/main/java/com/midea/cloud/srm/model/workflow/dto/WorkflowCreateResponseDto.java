/**
 * 
 */
package com.midea.cloud.srm.model.workflow.dto;

import java.io.Serializable;

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
  *  修改日期: 2020年8月10日 上午11:16:35
  *  修改内容:
 * </pre>
 */
public class WorkflowCreateResponseDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2624647925233615435L;
	private String processId;
	
	public static WorkflowCreateResponseDto build() {
		return new WorkflowCreateResponseDto();
	}
	
	/**
	 * @return the processId
	 */
	public String getProcessId() {
		return processId;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
	public WorkflowCreateResponseDto processId(String processId) {
		this.processId = processId;
		return this;
	}
	
	
}
