package com.midea.cloud.flow.model.dto;

/**
 * 流程废弃入参
 * @author wenjie.liang@meicloud.com
 * @version 1.0.0
 * @date 2020-06-20
 **/
public class WfDiscardParam {

    private String taskId;

    private String sessionId;

    private String formDataId;

	private String businessKey;
	private String instanceId;
	private String procdefId;
	private String handlerId;
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getFormDataId() {
		return formDataId;
	}

	public void setFormDataId(String formDataId) {
		this.formDataId = formDataId;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getProcdefId() {
		return procdefId;
	}

	public void setProcdefId(String procdefId) {
		this.procdefId = procdefId;
	}

	public String getHandlerId() {
		return handlerId;
	}

	public void setHandlerId(String handlerId) {
		this.handlerId = handlerId;
	}

}
