package com.midea.cloud.srm.model.suppliercooperate.orderreceive.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

/**
* <pre>
 *  收货明细 excel导出模型
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
@ColumnWidth(15) //列宽
public class ExcelOrderReceiveDto {
private static final long serialVersionUID = 268160L;
 /**
  * 供应商ID
  */
 @ExcelProperty( value = "供应商ID",index = 0)
 private Long vendorId;
 /**
  * 供应商编码
  */
 @ExcelProperty( value = "供应商编码",index = 1)
 private String vendorCode;
 /**
  * 供应商名称
  */
 @ExcelProperty( value = "供应商名称",index = 2)
 private String vendorName;
 /**
  * 业务实体ID
  */
 @ExcelProperty( value = "业务实体ID",index = 3)
 private Long orgId;
 /**
  * 业务实体编码
  */
 @ExcelProperty( value = "业务实体编码",index = 4)
 private String orgCode;
 /**
  * 业务实体名称
  */
 @ExcelProperty( value = "业务实体名称",index = 5)
 private String orgName;
 /**
  * 采购订单号
  */
 @ExcelProperty( value = "采购订单号",index = 6)
 private String orderNumber;
 /**
  * 采购订单行
  */
 @ExcelProperty( value = "采购订单行",index = 7)
 private String orderLineNum;
 /**
  * 采购订单行ID
  */
 @ExcelProperty( value = "采购订单行ID",index = 8)
 private Long poLineId;
 /**
  * 送货单号
  */
 @ExcelProperty( value = "送货单号",index = 9)
 private String deliveryNumber;
 /**
  * 采购订单行
  */
 @ExcelProperty( value = "送货单行",index = 10)
 private String deliveryLineNum;
 /**
  * 物料编码
  */
 @ExcelProperty( value = "物料编码",index = 11)
 private String materialCode;
 /**
  * 物料名称
  */
 @ExcelProperty( value = "物料名称",index = 12)
 private String materialName;
 /**
  * 物料ID
  */
 @ExcelProperty( value = "物料ID",index = 13)
 private Long materialId;
 /**
  * 订单数量
  */
 @ExcelProperty( value = "订单数量",index = 14)
 private Long orderNum;
 /**
  * 送货数量
  */
 @ExcelProperty( value = "送货数量",index = 15)
 private Long deliveryQuantity;
 /**
  * 收货数量
  */
 @ExcelProperty( value = "收货数量",index = 16)
 private Long receivedNum;
 /**
  * 不良数量
  */
 @ExcelProperty( value = "不良数量",index = 17)
 private Long badNum;
 /**
  * 差异常数量
  */
 @ExcelProperty( value = "差异常数量",index = 18)
 private Long differenceNum;
 /**
  * 实退数量
  */
 @ExcelProperty( value = "实退数量",index = 19)
 private Long actualReturnedNum;
 /**
  * 补货数量
  */
 @ExcelProperty( value = "补货数量",index = 20)
 private Long replenishNum;
 /**
  * 扣款数量
  */
 @ExcelProperty( value = "扣款数量",index = 21)
 private Long deductionNum;
 /**
  * 备注
  */
 @ExcelProperty( value = "备注",index = 22)
 private String comments;
 /**
  * 收货日期/接收日期
  */
 @ExcelProperty( value = "收货日期/接收日期",index = 23)
 private Date receiveDate;
 /**
  * 创建人ID
  */
 @ExcelProperty( value = "创建人ID",index = 24)
 private Long createdId;
 /**
  * 创建日期
  */
 @ExcelProperty( value = "创建日期",index = 25)
 private Date creationDate;
 /**
  * 创建人
  */
 @ExcelProperty( value = "创建人",index = 26)
 private String createdBy;
 /**
  * 创建人IP
  */
 @ExcelProperty( value = "创建人IP",index = 27)
 private String createdByIp;
 /**
  * 最后更新人
  */
 @ExcelProperty( value = "最后更新人",index = 28)
 private String lastUpdatedBy;
 /**
  * 最后更新日期
  */
 @ExcelProperty( value = "最后更新日期",index = 29)
 private Date lastUpdateDate;
 /**
  * 最后更新人ID
  */
 @ExcelProperty( value = "最后更新人ID",index = 30)
 private Long lastUpdatedId;
 /**
  * 最后更新人IP
  */
 @ExcelProperty( value = "最后更新人IP",index = 31)
 private String lastUpdatedByIp;
 /**
  * 错误信息提示
  */
 @ExcelProperty( value = "错误信息提示",index = 32)
 private String errorMsg;
}