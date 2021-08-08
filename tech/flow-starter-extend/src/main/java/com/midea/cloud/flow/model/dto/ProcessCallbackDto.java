package com.midea.cloud.flow.model.dto;


public class ProcessCallbackDto {

    /**
     * 流程实例id
     */
    private String instanceId;

    /**
     * 节点标识
     */
    private String taskKey;

    /**
     * 任务id
     */
    private String taskId;

    /**
     *
     */
    private String eventType;

    /**
     * http接口地址
     */
    private String url;

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
