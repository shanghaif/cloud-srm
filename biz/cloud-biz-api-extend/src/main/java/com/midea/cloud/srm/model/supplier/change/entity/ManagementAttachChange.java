package com.midea.cloud.srm.model.supplier.change.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

/**
* <pre>
 *  认证附件
 * </pre>
*
* @author sss@sss.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 13, 2021 1:50:14 PM
 *  修改内容:
 * </pre>
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_management_attach_change")
public class ManagementAttachChange extends BaseEntity {
private static final long serialVersionUID = 648443L;
 /**
 * 认证文件详情变更ID
 */
 @TableId("MANAGEMENT_ATTACH_CHANGE_ID")
 private Long managementAttachChangeId;
 /**
 * 变更ID
 */
 @TableField("CHANGE_ID")
 private Long changeId;
 /**
 * ID
 */
 @TableField("FILEUPLOAD_ID")
 private Long fileuploadId;
 /**
 * 业务ID，用于业务单据关联
 */
 @TableField("BUSINESS_ID")
 private Long businessId;
 /**
 * 认证类型
 */
 @TableField("AUTH_TYPE")
 private String authType;
 /**
 * 认证描述
 */
 @TableField("AUTH_DESCRIPTION")
 private String authDescription;
 /**
 * 认证编号
 */
 @TableField("AUTH_NUM")
 private String authNum;
 /**
 * 认证时间
 */
 @TableField("AUTH_DATE")
 private LocalDate authDate;
 /**
 * 认证机构
 */
 @TableField("AUTH_ORG")
 private String authOrg;
 /**
 * 公司ID
 */
 @TableField("COMPANY_ID")
 private Long companyId;
 /**
 * 生效日期(YYYY-MM-DD)
 */
 @TableField("START_DATE")
 private LocalDate startDate;
 /**
 * 失效日期(YYYY-MM-DD)（需要校验非空）
 */
 @TableField("END_DATE")
 private LocalDate endDate;
  /**
  * 创建人ID
  */
  @TableField(value ="CREATED_ID",fill = FieldFill.INSERT)
  private Long createdId;
 /**
 * 创建人
 */
 @TableField(value = "CREATED_BY",fill = FieldFill.INSERT)
 private String createdBy;
 /**
 * 创建时间
 */
 @TableField(value = "CREATION_DATE",fill = FieldFill.INSERT)
 private Date creationDate;
 /**
 * 创建人IP
 */
 @TableField(value ="CREATED_BY_IP" ,fill = FieldFill.INSERT)
 private String createdByIp;
 /**
 * 最后更新人ID
 */
 @TableField(value = "LAST_UPDATED_ID",fill = FieldFill.INSERT_UPDATE)
 private Long lastUpdatedId;
 /**
 * 最后更新人
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
 * 操作类型
 */
 @TableField("OP_TYPE")
 private String opType;
}