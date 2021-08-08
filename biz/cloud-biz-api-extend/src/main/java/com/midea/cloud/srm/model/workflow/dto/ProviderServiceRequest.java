/**
 * 
 */
package com.midea.cloud.srm.model.workflow.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

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
  *  修改日期: 2020年8月7日 下午6:36:15
  *  修改内容:
 * </pre>
 */


@XmlRootElement
public class ProviderServiceRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7653921216927211510L;

	private String formTemplateId;
	
	private String formInstanceId;

	/**
	 * @return the formTemplateId
	 */
	public String getFormTemplateId() {
		return formTemplateId;
	}

	/**
	 * @param formTemplateId the formTemplateId to set
	 */
	public void setFormTemplateId(String formTemplateId) {
		this.formTemplateId = formTemplateId;
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
	
}
