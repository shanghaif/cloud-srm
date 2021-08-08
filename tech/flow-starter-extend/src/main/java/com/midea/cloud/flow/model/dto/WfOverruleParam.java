package com.midea.cloud.flow.model.dto;

/**
 * 流程驳回入参
 * @author wenjie.liang@meicloud.com
 * @version 1.0.0
 * @date 2020-06-20
 **/
public class WfOverruleParam {

	private String formDataId;
	private String taskId;
	private String toTaskKey;
	private String toTaskName;
	private String description;
	private String sessionId;
	private String businessKey;
	private String instanceId;
	private String procdefId;
	private String handlerId;
	/**
	 * 当审批节点为驳回目标节点时，isJump为Y则审批完后直接跳到驳回节点，N为正常路线
	 */
	private String isJump;
	public String getFormDataId() {
		return formDataId;
	}
	public void setFormDataId(String formDataId) {
		this.formDataId = formDataId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getToTaskKey() {
		return toTaskKey;
	}
	public void setToTaskKey(String toTaskKey) {
		this.toTaskKey = toTaskKey;
	}
	public String getToTaskName() {
		return toTaskName;
	}
	public void setToTaskName(String toTaskName) {
		this.toTaskName = toTaskName;
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
	public String getIsJump() {
		return isJump;
	}
	public void setIsJump(String isJump) {
		this.isJump = isJump;
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
