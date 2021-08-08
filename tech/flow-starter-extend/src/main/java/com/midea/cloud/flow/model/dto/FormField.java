package com.midea.cloud.flow.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * <pre>
 * 流程表单字段pojo
 * </pre>
 * 
 * @author developer developer@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class FormField implements Serializable {

	private static final long serialVersionUID = 1L;

	public FormField() {
		super();
	}

	public FormField(Integer formId, String formFieldId, String fieldName, String fieldKey, String orderNo, String grant, List<FormField> childFormFieldList) {
		super();
		this.formId = formId;
		this.formFieldId = formFieldId;
		this.fieldName = fieldName;
		this.fieldKey = fieldKey;
		this.orderNo = orderNo;
		this.grant = grant;
		this.childFormFieldPojoList = childFormFieldList;
	}



	/**
	 * 所属表单ID
	 */
	private Integer formId;
	
	/**
	 * 表单字段ID
	 */
	private String formFieldId;
	
	/**
	 * 字段名称
	 */
	private String fieldName;
	
	/**
	 * 字段KEY
	 */
	private String fieldKey;
	
	/**
	 * 排序号
	 */
	private String orderNo;
	
	/**
	 * 授权信息
	 */
	private String grant;

	/**
	 * 流程关联类型
	 */
	private String wfRelevance;
	
	
	/**
	 * 子字段信息
	 */
	private List<FormField> childFormFieldPojoList ;

	private String application;

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public List<FormField> getChildFormFieldPojoList() {
		return childFormFieldPojoList;
	}

	public void setChildFormFieldPojoList(List<FormField> childFormFieldPojoList) {
		this.childFormFieldPojoList = childFormFieldPojoList;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldKey() {
		return fieldKey;
	}

	public void setFieldKey(String fieldKey) {
		this.fieldKey = fieldKey;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getFormFieldId() {
		return formFieldId;
	}

	public void setFormFieldId(String formFieldId) {
		this.formFieldId = formFieldId;
	}

	public String getGrant() {
		return grant;
	}

	public void setGrant(String grant) {
		this.grant = grant;
	}

	public String getWfRelevance() {
		return wfRelevance;
	}

	public void setWfRelevance(String wfRelevance) {
		this.wfRelevance = wfRelevance;
	}
}
