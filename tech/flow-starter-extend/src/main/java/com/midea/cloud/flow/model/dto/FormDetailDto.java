package com.midea.cloud.flow.model.dto;

/**
 * @author wenjie.liang@meicloud.com
 * @version 1.0.0
 * @date 2020-06-22
 **/
public class FormDetailDto {

    private Integer formId;
    private String formCode;
    private String formName;
    private String formContent;
	public Integer getFormId() {
		return formId;
	}
	public void setFormId(Integer formId) {
		this.formId = formId;
	}
	public String getFormCode() {
		return formCode;
	}
	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getFormContent() {
		return formContent;
	}
	public void setFormContent(String formContent) {
		this.formContent = formContent;
	}

    
}
