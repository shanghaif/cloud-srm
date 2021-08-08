package com.midea.cloud.flow.model.dto;

/**
 * 流程转办入参
 * @author wenjie.liang@meicloud.com
 * @version 1.0.0
 * @date 2020-06-20
 **/
public class WfTransferParam {

	private String taskId;
	private String toUserId;
	private String description;
	private String sessionId;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getToUserId() {
		return toUserId;
	}
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	
}