/**
 * 
 */
package com.midea.cloud.srm.model.rbac.department.entity;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;

/**
 * <pre>
 * 部门组织 
 * </pre>
 *
 * @author nianhuanh@meicloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年9月7日 下午7:14:03
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_rbac_department")
public class Department extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9006221419394542386L;
	
	/**
	 * 部门ID
	 */
	@TableId("DEPARTMENT_ID")
	private Long departmentId;
	
	/**
	 * 部门编码
	 */
	@TableField("DEPARTMENT_CODE")
	private String departmentCode;
	
	/**
	 * 部门名称
	 */
	@TableField("DEPARTMENT_NAME")
	private String departmentName;
	
	/**
	 * 父级部门ID
	 */
	@TableField("PARENT_DEPARTMENT_ID")
	private Long parentDepartmentId;
	
	/**
	 * 部门等级
	 */
	@TableField("DEPARTMENT_LEVEL")
	private Integer departmentLevel;
	
	/**
     * 创建人ID
     */
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;

    /**
     * 创建人
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;

    /**
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
    private String lastUpdatedByIp;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;
}
