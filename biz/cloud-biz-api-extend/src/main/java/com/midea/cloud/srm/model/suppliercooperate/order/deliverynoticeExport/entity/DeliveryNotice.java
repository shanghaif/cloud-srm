package com.midea.cloud.srm.model.suppliercooperate.order.deliverynoticeExport.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
* <pre>
 *  导出 模型
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Feb 5, 2021 10:54:16 AM
 *  修改内容:
 * </pre>
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sc_delivery_notice")
public class DeliveryNotice extends BaseEntity {
private static final long serialVersionUID = 640980L;
 /**
 * 主键ID
 */
 @TableId("DELIVERY_NOTICE_ID")
 private Long deliveryNoticeId;
 /**
 * 采购订单ID
 */
 @TableField("ORDER_ID")
 private Long orderId;
 /**
 * 采购订单明细ID
 */
 @TableField("ORDER_DETAIL_ID")
 private Long orderDetailId;
 /**
 * 送货通知单号
 */
 @TableField("DELIVERY_NOTICE_NUM")
 private String deliveryNoticeNum;
 /**
 * 通知单状态
 */
 @TableField("DELIVERY_NOTICE_STATUS")
 private String deliveryNoticeStatus;
 /**
 * 累计收货数量
 */
 @TableField("RECEIVE_SUM")
 private Long receiveSum;
 /**
 * 送货时间
 */
 @TableField("DELIVERY_TIME")
 private Date deliveryTime;
 /**
 * 备注
 */
 @TableField("COMMENTS")
 private String comments;
 /**
 * 企业编码/公司代码
 */
 @TableField("COMPANY_CODE")
 private String companyCode;
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
 private String tenantId;
 /**
 * 版本号
 */
 @TableField("VERSION")
 private Long version;
 /**
 * 序号
 */
 @TableField("LINE_NUM")
 private Long lineNum;
 /**
 * 通知送货数量
 */
 @TableField("NOTICE_SUM")
 private Long noticeSum;
 /**
 * 采购订单行号
 */
 @TableField("ORDER_LINE_NUM")
 private Long orderLineNum;
 /**
 * 拒绝原因
 */
 @TableField("REFUSED_REASON")
 private String refusedReason;

}