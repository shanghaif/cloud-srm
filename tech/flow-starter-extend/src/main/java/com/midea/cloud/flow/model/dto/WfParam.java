package com.midea.cloud.flow.model.dto;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 流程提交入参
 * @author wenjie.liang@meicloud.com
 * @version 1.0.0
 * @date 2020-06-20
 **/
public class WfParam {

	/**
	 * 流程实例名称
	 */
	private String instName;
	
	/**
	 * 表单数据ID -- 表单系统存放
	 */
	private Long formDataId;

	private String formData;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 员工ID
	 */
	private String employeeId;
	
	/**
	 * 流程模板ID -- 关联流程字段
	 */
	private String procdefId;
	
	/**
	 * 流程模板KEY -- 不适用于关联
	 */
	private String procdefKey;
	
	/**
	 * 节点KEY
	 */
	private String taskKey;
	
	/**
	 * 节点ID
	 */
	private String taskId;
	
	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 审批意见
	 */
	private String approved;
	
	private String url;//回调地址

	private String urlType;// 回调地址请求方式

	private String sessionId;

	/**
	 * 指派人信息 - 用户可能设置过流程节点
	 */
	private List<WfAssgineeParam> assgineeList;
	
	/**
	 * 路由节点信息
	 */
	private Map<String,List<String>> gatewayFlows;

	/**
	 * 流程变量所需要的key
	 */
	private Set<String> variableSet;

	private Map<String, Object> variables;


	/**
	 * 应用名称
	 */
	private String application;

	/**
	 * 检查 1为需要检查
	 */
	private String isCheck;

	/**
	 * 流程实例Id
	 */
	private String procInstId;

	/**
	 * 会签票数记录,0为不同意,1为同意
	 */
	private String signResult;

	/**
	 * 业务类型(自定义)
	 */
	private String businessType;

	/**
	 * 业务主体类型(自定义),1人员 2组织
	 */
	private String businessMajor;

	/**
	 * 业务主体id(自定义,员工为员工id，组织为组织id)
	 */
	private String businessId;

	/**
	 * 业务单据编号
	 */
	private String businessKey;

	/**
	 * 业务其他扩展字段信息，json格式
	 */
	private String extJsonData;
	
	/**
	 * 操作人ID，工作流引擎回传
	 */
	private String handlerId;
	
	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public Long getFormDataId() {
		return formDataId;
	}

	public void setFormDataId(Long formDataId) {
		this.formDataId = formDataId;
	}

	public String getFormData() {
		return formData;
	}

	public void setFormData(String formData) {
		this.formData = formData;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getProcdefId() {
		return procdefId;
	}

	public void setProcdefId(String procdefId) {
		this.procdefId = procdefId;
	}

	public String getProcdefKey() {
		return procdefKey;
	}

	public void setProcdefKey(String procdefKey) {
		this.procdefKey = procdefKey;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public List<WfAssgineeParam> getAssgineeList() {
		return assgineeList;
	}

	public void setAssgineeList(List<WfAssgineeParam> assgineeList) {
		this.assgineeList = assgineeList;
	}

	public Map<String, List<String>> getGatewayFlows() {
		return gatewayFlows;
	}

	public void setGatewayFlows(Map<String, List<String>> gatewayFlows) {
		this.gatewayFlows = gatewayFlows;
	}

	public Set<String> getVariableSet() {
		return variableSet;
	}

	public void setVariableSet(Set<String> variableSet) {
		this.variableSet = variableSet;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getSignResult() {
		return signResult;
	}

	public void setSignResult(String signResult) {
		this.signResult = signResult;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessMajor() {
		return businessMajor;
	}

	public void setBusinessMajor(String businessMajor) {
		this.businessMajor = businessMajor;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getExtJsonData() {
		return extJsonData;
	}

	public void setExtJsonData(String extJsonData) {
		this.extJsonData = extJsonData;
	}

	public String getHandlerId() {
		return handlerId;
	}

	public void setHandlerId(String handlerId) {
		this.handlerId = handlerId;
	}

}

