package com.midea.cloud.flow.model.dto;

/**
 * 
 * <pre>
 * 人员选择器POJO
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
public class EmpSelectViewDto {
	
	/**
	 * 员工id
	 */
	private String employeeId;
	
	
	/**
	 * 员工姓名
	 */
	private String employeeName;


	/**
	 * 员工工号
	 */
	private String employeeCode;


	/**
	 * 组织id
	 */
	private Integer unitId;


	/**
	 * 组织名称
	 */
	private String unitName;
	

	/**
	 * 职位名称
	 */
	private String positionName;


	public String getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}


	public String getEmployeeName() {
		return employeeName;
	}


	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}


	public String getEmployeeCode() {
		return employeeCode;
	}


	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}


	public Integer getUnitId() {
		return unitId;
	}


	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}


	public String getUnitName() {
		return unitName;
	}


	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}


	public String getPositionName() {
		return positionName;
	}


	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

}
