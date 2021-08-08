package com.midea.cloud.flow.model.dto;

public class WfCommunicateDto {

    private String procinstId;  //流程实例id
    private String taskId;  //任务id
    private String assigneeId;  //沟通发起人
    private String assigneeName;
    private String toAssigneeId;    //回复人
    private String toAssigneeName;
    private String content; //  沟通内容
    private String communicateId;   //沟通id
	public String getProcinstId() {
		return procinstId;
	}
	public void setProcinstId(String procinstId) {
		this.procinstId = procinstId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getAssigneeId() {
		return assigneeId;
	}
	public void setAssigneeId(String assigneeId) {
		this.assigneeId = assigneeId;
	}
	public String getAssigneeName() {
		return assigneeName;
	}
	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}
	public String getToAssigneeId() {
		return toAssigneeId;
	}
	public void setToAssigneeId(String toAssigneeId) {
		this.toAssigneeId = toAssigneeId;
	}
	public String getToAssigneeName() {
		return toAssigneeName;
	}
	public void setToAssigneeName(String toAssigneeName) {
		this.toAssigneeName = toAssigneeName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCommunicateId() {
		return communicateId;
	}
	public void setCommunicateId(String communicateId) {
		this.communicateId = communicateId;
	}
    
    
    
}
