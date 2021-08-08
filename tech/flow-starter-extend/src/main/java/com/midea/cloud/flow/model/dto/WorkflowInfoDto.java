package com.midea.cloud.flow.model.dto;

import java.util.Map;

public class WorkflowInfoDto {

	private String taskName;
	private String taskKey;
	private String processType;
	private String processName;
	private String taskId;
	private String processInstanceId;
	private String processDefinitionId;
	private String createName;
	private String createBy;
	private String instanceName;
	private String processStatus;
	private String approved;
	private boolean multi;
	private boolean startNode;
	private String url;
	private String processData;
	private String jurisdictionData;
	private String description;
	private String hasPower;
	private java.util.Date createDate;
	private java.util.Date startDate;
	private String buttonType;
	private Map<String, Boolean> buttonTypeMap;
	private String isFlowCenter;

	/**
	 * 流程分类编码
	 */
	private String categoryCode;

	/**
	 * 业务流程表单id
	 */
	private String formDataId;

	private String businessKey;

	/**
	 * 当前任务处理人
	 */
	private String assignee;
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskKey() {
		return taskKey;
	}
	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	public java.util.Date getStartDate() {
		return startDate;
	}
	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getProcessData() {
		return processData;
	}
	public void setProcessData(String processData) {
		this.processData = processData;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHasPower() {
		return hasPower;
	}
	public void setHasPower(String hasPower) {
		this.hasPower = hasPower;
	}
	public String getButtonType() {
		return buttonType;
	}
	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getJurisdictionData() {
		return jurisdictionData;
	}
	public void setJurisdictionData(String jurisdictionData) {
		this.jurisdictionData = jurisdictionData;
	}
	public String getIsFlowCenter() {
		return isFlowCenter;
	}
	public void setIsFlowCenter(String isFlowCenter) {
		this.isFlowCenter = isFlowCenter;
	}
	public String getApproved() {
		return approved;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}
	public boolean getMulti() {
		return multi;
	}
	public void setMulti(boolean isMulti) {
		this.multi = isMulti;
	}
	public Map<String, Boolean> getButtonTypeMap() {
		return buttonTypeMap;
	}
	public void setButtonTypeMap(Map<String, Boolean> buttonTypeMap) {
		this.buttonTypeMap = buttonTypeMap;
	}
	public boolean getStartNode() {
		return startNode;
	}
	public void setStartNode(boolean startNode) {
		this.startNode = startNode;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
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

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
}

