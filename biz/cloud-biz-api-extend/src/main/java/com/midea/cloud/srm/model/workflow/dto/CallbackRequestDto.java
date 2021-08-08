/**
 * 
 */
package com.midea.cloud.srm.model.workflow.dto;

import java.io.Serializable;

/**
 * <pre>
 *  
 * </pre>
 *
 * @author nianhuanh@meicloud.com
 * @version 1.00.00
 *
 * <pre>
  *  修改记录
  *  修改后版本:
  *  修改人:
  *  修改日期: 2020年8月10日 上午10:59:16
  *  修改内容:
 * </pre>
 */
public class CallbackRequestDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7261552163475040773L;

	private String formInstanceId;
	
	private String formTemplateId;
	
	/**
	 * 
	 * @param formInstanceId
	 * @return
	 */
	public static CallbackRequestDto build(String formInstanceId) {
		CallbackRequestDto dto = new CallbackRequestDto();
		dto.setFormInstanceId(formInstanceId);
		return dto;
	}
	
	
	/**
	 * 
	 * @param formTemplateId
	 * @param formInstanceId
	 * @return
	 */
	public static CallbackRequestDto build(String formTemplateId, String formInstanceId) {
		CallbackRequestDto dto = new CallbackRequestDto();
		dto.setFormInstanceId(formInstanceId);
		dto.setFormTemplateId(formTemplateId);
		return dto;
	}
	
	

	/**
	 * @return the formInstanceId
	 */
	public String getFormInstanceId() {
		return formInstanceId;
	}

	/**
	 * @param formInstanceId the formInstanceId to set
	 */
	public void setFormInstanceId(String formInstanceId) {
		this.formInstanceId = formInstanceId;
	}
	
	/**
	 * @param formTemplateId the formTemplateId to set
	 */
	public void setFormTemplateId(String formTemplateId) {
		this.formTemplateId = formTemplateId;
	}
	
	/**
	 * @return the formTemplateId
	 */
	public String getFormTemplateId() {
		return formTemplateId;
	}
	
}
