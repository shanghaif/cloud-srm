package com.midea.cloud.flow.model.dto;

import java.util.Date;
import java.util.Map;

/**
 *
 * <pre>
 * 待办Dto
 * </pre>
 *
 * @author liangtf2 liangtf2@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */

public class TodoDto {

    /**
     * 待办类型 1待阅 2待办
     */
    private String todoType;
    

    /**
     * 待办标题
     */
    private String todoTitle;
    

    /**
     * 待办内容
     */
    private String todoContent;
    

    /**
     * 待办时间
     */
    private Date todoDate;


    /**
     * 接收人，用户id，需关联
     */
    private String receiver;


    /**
     * 关键字，必填
     */
    private String keyword;

    /**
     * 流程定义id
     */
    private String procdefId;


    /**
     * 流程实例id
     */
    private String procInstId;

    /**
     * 任务id
     */
    private String taskId;


    /**
     * 任务名称
     */
    private String taskName;


    /**
     * 节点标识
     */
    private String taskKey;

    /**
     * url参数扩展
     */
    private Map<String,String> urlParamExt;

	public String getTodoType() {
		return todoType;
	}

	public void setTodoType(String todoType) {
		this.todoType = todoType;
	}

	public String getTodoTitle() {
		return todoTitle;
	}

	public void setTodoTitle(String todoTitle) {
		this.todoTitle = todoTitle;
	}

	public String getTodoContent() {
		return todoContent;
	}

	public void setTodoContent(String todoContent) {
		this.todoContent = todoContent;
	}

	public Date getTodoDate() {
		return todoDate;
	}

	public void setTodoDate(Date todoDate) {
		this.todoDate = todoDate;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getProcdefId() {
		return procdefId;
	}

	public void setProcdefId(String procdefId) {
		this.procdefId = procdefId;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

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

	public Map<String, String> getUrlParamExt() {
		return urlParamExt;
	}

	public void setUrlParamExt(Map<String, String> urlParamExt) {
		this.urlParamExt = urlParamExt;
	}

    
    
}
