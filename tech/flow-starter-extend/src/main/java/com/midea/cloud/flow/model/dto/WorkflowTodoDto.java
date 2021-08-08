package com.midea.cloud.flow.model.dto;

import java.io.Serializable;

/**
 * 工作流待办对象
 * 
 * <pre>
 * 。
 * </pre>
 * 
 * @author lizl7
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class WorkflowTodoDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5640837477923996578L;
	/**
	 * 单据类型
	 */
	private String businessType;
	/**
	 * 单据ID
	 */
	private Long businessId;
	/**
	 * 操作人，二选一
	 */
	private Long handlerId;
	
	/**
	 * 操作人账号，二选一
	 */
	private String handlerAccount;
	
	/**
	 * 代办标题
	 */
	private String title;
	
	
	/**
	 * 参数
	 */
	private Object param;
	
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public Long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	public Long getHandlerId() {
		return handlerId;
	}
	public void setHandlerId(Long handlerId) {
		this.handlerId = handlerId;
	}
	public String getHandlerAccount() {
		return handlerAccount;
	}
	public void setHandlerAccount(String handlerAccount) {
		this.handlerAccount = handlerAccount;
	}
	public Object getParam() {
		return param;
	}
	public void setParam(Object param) {
		this.param = param;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}