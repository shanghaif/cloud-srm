package com.midea.cloud.srm.model.perf.supplierenotice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
* <pre>
 *  21 模型
 * </pre>
*
* @author wengzc@media.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Feb 1, 2021 5:12:42 PM
 *  修改内容:
 * </pre>
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_perf_qua_supplier_enotice")
public class QuaSupplierEnotice extends BaseEntity {
private static final long serialVersionUID = 344734L;
 /**
 * 通知编号ID
 */
 @TableId("NOTICE_ID")
 private Long noticeId;
 /**
 * 供应商ID
 */
 @TableField("VENDOR_ID")
 private Long vendorId;
 /**
 * 供应商名称
 */
 @TableField("VENDOR_NAME")
 private String vendorName;
 /**
 * 供应商编码
 */
 @TableField("VENDOR_CODE")
 private String vendorCode;
 /**
 * 发布日期
 */
 @TableField("RELEASE_DATE")
 private Date releaseDate;
 /**
 * 单据状态
 */
 @TableField("ORDER_STATUS")
 private String orderStatus;
 /**
 * 业务实体ID
 */
 @TableField("ORG_ID")
 private Long orgId;
 /**
 * 业务实体
 */
 @TableField("ORG_NAME")
 private String orgName;
 /**
 * 采购员
 */
 @TableField("PURCHASER_NAME")
 private String purchaserName;
 /**
 * 问题类别
 */
 @TableField("PROBLEM_TYPE")
 private String problemType;
 /**
 * 处罚金额
 */
 @TableField("FINE_AMOUNT")
 private Long fineAmount;
 /**
 * 处理依据
 */
 @TableField("ACCOUNDING_DEAL")
 private String accoundingDeal;
 /**
 * 异常问题描述
 */
 @TableField("EXCEPTION_DESCRIBE")
 private String exceptionDescribe;
 /**
 * 备注
 */
 @TableField("NOTICE_COMMENTS")
 private String noticeComments;
 /**
 * 异常问题处理结果
 */
 @TableField("EXCEPTION_DEALING_RESULT")
 private String exceptionDealingResult;
 /**
 * 供方查阅时间
 */
 @TableField("SUPPLIER_READ_TIME")
 private Date supplierReadTime;
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
 @TableField("LAST_UPDATED_DATE")
 private Date lastUpdatedDate;
 /**
 * 最后更新人IP
 */
 @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
 private String lastUpdatedByIp;
 /**
 * 租户ID
 */
 @TableField("TENANT_ID")
 private String tenantId;
 /**
 * 版本号
 */
 @TableField("VERSION")
 private Long version;
 /**
  * 文件上传明细行
  */
 @TableField(exist = false)
 private List<Fileupload> fileUploads;

}