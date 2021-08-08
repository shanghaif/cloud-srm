package com.midea.cloud.srm.model.suppliercooperate.orderreceive.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
* <pre>
 *  xx 模型
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 30, 2021 10:31:54 AM
 *  修改内容:
 * </pre>
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sc_storage_return")
public class OrderReceive extends BaseEntity {
private static final long serialVersionUID = 471252L;
 /**
 * 入库/退货明细id
 */
 @TableId("WAREHOUSING_RETURN_DETAIL_ID")
 private Long warehousingReturnDetailId;
 /**
 * 供应商ID
 */
 @TableField("VENDOR_ID")
 private Long vendorId;
 /**
 * 供应商编码
 */
 @TableField("VENDOR_CODE")
 private String vendorCode;
 /**
 * 供应商名称
 */
 @TableField("VENDOR_NAME")
 private String vendorName;
 /**
 * 业务实体ID
 */
 @TableField("ORG_ID")
 private Long orgId;
 /**
 * 业务实体编码
 */
 @TableField("ORG_CODE")
 private String orgCode;
 /**
 * 业务实体名称
 */
 @TableField("ORG_NAME")
 private String orgName;
 /**
 * 采购订单号
 */
 @TableField("ORDER_NUMBER")
 private String orderNumber;
 /**
 * 采购订单行ID
 */
 @TableField("PO_LINE_ID")
 private Long poLineId;
 /**
 * 送货单号
 */
 @TableField("DELIVERY_NUMBER")
 private String deliveryNumber;
 /**
 * 物料编码
 */
 @TableField("MATERIAL_CODE")
 private String materialCode;
 /**
 * 物料名称
 */
 @TableField("MATERIAL_NAME")
 private String materialName;
 /**
 * 物料ID
 */
 @TableField("MATERIAL_ID")
 private Long materialId;
 /**
 * 订单数量
 */
 @TableField("ORDER_NUM")
 private Long orderNum;
 /**
 * 送货数量
 */
 @TableField("DELIVERY_QUANTITY")
 private Long deliveryQuantity;
 /**
 * 收货数量
 */
 @TableField("RECEIVED_NUM")
 private Long receivedNum;
 /**
 * 不良数量
 */
 @TableField("BAD_NUM")
 private Long badNum;
 /**
 * 差异常数量
 */
 @TableField("DIFFERENCE_NUM")
 private Long differenceNum;
 /**
 * 实退数量
 */
 @TableField("ACTUAL_RETURNED_NUM")
 private Long actualReturnedNum;
 /**
 * 补货数量
 */
 @TableField("REPLENISH_NUM")
 private Long replenishNum;
 /**
 * 扣款数量
 */
 @TableField("DEDUCTION_NUM")
 private Long deductionNum;
 /**
 * 备注
 */
 @TableField("COMMENTS")
 private String comments;
 /**
 * 收货日期/接收日期
 */
 @TableField("RECEIVE_DATE")
 private Date receiveDate;
 /**
 * 状态
 */
 @TableField("RECEIVE_STATUS")
 private String receiveStatus;
 /**
  * 送货单行号
  */
 @TableField(value = "DELIVERY_LINE_NUM")
 private String deliveryLineNum;
 /**
  * 采购订单行号
  */
 @TableField(value = "ORDER_LINE_NUM")
 private String orderLineNum;
 /**
  * 累计收货数量
  */
 @TableField(value = "RECEIVE_SUM")
 private Long receiveSum;

 /**
  * 创建人ID
  */
  @TableField(value ="CREATED_ID",fill = FieldFill.INSERT)
  private Long createdId;
 /**
 * 创建时间
 */
 @TableField(value = "CREATION_DATE",fill = FieldFill.INSERT)
 private Date creationDate;
 /**
 * 创建人
 */
 @TableField(value = "CREATED_BY",fill = FieldFill.INSERT)
 private String createdBy;
 /**
 * 创建人IP
 */
 @TableField(value ="CREATED_BY_IP" , fill = FieldFill.UPDATE)
 private String createdByIp;
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
 * 最后更新人ID
 */
 @TableField(value = "LAST_UPDATED_ID",fill = FieldFill.INSERT_UPDATE)
 private Long lastUpdatedId;
 /**
 * 最后更新人IP
 */
 @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
 private String lastUpdatedByIp;

 /**
  * 开始时间
  */
 @TableField(exist = false)
 private String startDate;
 /**
  * 结束时间
  */
 @TableField(exist = false)
 private String endDate;

}