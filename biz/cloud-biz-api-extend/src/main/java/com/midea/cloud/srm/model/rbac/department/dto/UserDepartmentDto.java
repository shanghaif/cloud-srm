/**
 * 
 */
package com.midea.cloud.srm.model.rbac.department.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * <pre>
 *  
 * </pre>
 *
 * @author nianhuanh@meicloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年9月8日 上午11:29:07
 *  修改内容:
 * </pre>
 */
@Data
public class UserDepartmentDto implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 3677704930511921258L;
	
	/**
	 * 
	 */
	private String userId;
	
	/**
	 * 
	 */
	private String userType;
	
	/**
	 * 
	 */
	private String userName;
	
	/**
	 * 
	 */
	private String departmentId;
	
	/**
	 * 
	 */
	private String departmentCode;
	
	/**
	 * 
	 */
	private String departmentName;
	
	/**
	 * 
	 */
	private String level;
	
	/**
	 * 
	 */
	private String position;
	
}
