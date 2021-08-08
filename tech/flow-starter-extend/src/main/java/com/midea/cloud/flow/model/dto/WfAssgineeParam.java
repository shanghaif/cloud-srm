package com.midea.cloud.flow.model.dto;

public class WfAssgineeParam {

	private String taskKey;
	private String taskName;
	private String isNotify;
	/** 是否为多人审批 **/
	private String isMulti;
	/** 是否允许设置 **/
	private String isNeed;
	/** 审批人 多人时，中间用逗号分割  **/
	private String assignee;
	/** 审批人姓名 **/
	private String assigneeName;
	public String getTaskKey() {
		return taskKey;
	}
	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getIsNotify() {
		return isNotify;
	}
	public void setIsNotify(String isNotify) {
		this.isNotify = isNotify;
	}
	public String getIsMulti() {
		return isMulti;
	}
	public void setIsMulti(String isMulti) {
		this.isMulti = isMulti;
	}
	public String getIsNeed() {
		return isNeed;
	}
	public void setIsNeed(String isNeed) {
		this.isNeed = isNeed;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getAssigneeName() {
		return assigneeName;
	}
	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	

}