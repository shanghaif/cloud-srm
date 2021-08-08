/**
 *
 */
package com.midea.cloud.srm.model.workflow.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
  *  修改日期: 2020年8月11日 上午10:14:03
  *  修改内容:
 * </pre>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("SCC_FLOW_BUS_WORKFLOW")
public class SrmFlowBusWorkflow extends BaseEntity{
	/**
	 *
	 */
	private static final long serialVersionUID = -3706117824276086069L;

	/**
	 * 主键ID
	 */
	@TableId(value = "ID")
	private Long id;

	/**
	 * 表单实例ID
	 */
	@TableField("FORM_INSTANCE_ID")
	private String formInstanceId;

	/**
	 * 流程模板ID
	 */
	@TableField("FORM_TEMPLATE_ID")
	private String formTemplateId;

	/**
	 * 流程实例ID
	 */
	@TableField("FLOW_INSTANCE_ID")
	private String flowInstanceId;

	/**
	 * 业务备注信息
	 */
	@TableField("FORM_REMARK")
	private String formRemark;

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
	@TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
	private Long lastUpdatedId;

	/**
	 * 最后更新
	 */
	@TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
	private String lastUpdatedBy;

	/**
	 * 最后更新时间
	 */
	@TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
	private Date lastUpdateDate;

	/**
	 * 最后更新人IP
	 */
	@TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
	private String lastUpdatedByIp;

	/**
	 * 租户ID
	 */
	@TableField("TENANT_ID")
	private Long tenantId;

	/**
	 * 版本号
	 */
	@TableField("VERSION")
	private Long version;


	/**
	 * 版本号
	 */
	@TableField("SRM_ORDER_STATUS")
	private String srmOrderStatus;

	/**
	 * 版本号
	 */
	@TableField("REPUSH_TIMES")
	private Integer repushTimes;


}
