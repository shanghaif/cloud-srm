package com.midea.cloud.srm.model.suppliercooperate.order.deliverynoticeExport.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

/**
* <pre>
 *  导出 excel导出模型
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
@ColumnWidth(15) //列宽
public class ExcelDeliveryNoticeDto {
private static final long serialVersionUID = 700436L;
 /**
 * 采购订单ID
 */
 @ExcelProperty( value = "采购订单ID",index = 0)
 private Long orderId;
 /**
 * 采购订单明细ID
 */
 @ExcelProperty( value = "采购订单明细ID",index = 1)
 private Long orderDetailId;
 /**
 * 送货通知单号
 */
 @ExcelProperty( value = "送货通知单号",index = 2)
 private String deliveryNoticeNum;
 /**
 * 通知单状态
 */
 @ExcelProperty( value = "通知单状态",index = 3)
 private String deliveryNoticeStatus;
 /**
 * 累计收货数量
 */
 @ExcelProperty( value = "累计收货数量",index = 4)
 private Long receiveSum;
 /**
 * 送货时间
 */
 @ExcelProperty( value = "送货时间",index = 5)
 private Date deliveryTime;
 /**
 * 备注
 */
 @ExcelProperty( value = "备注",index = 6)
 private String comments;
 /**
 * 企业编码/公司代码
 */
 @ExcelProperty( value = "企业编码/公司代码",index = 7)
 private String companyCode;
 /**
 * 创建人ID
 */
 @ExcelProperty( value = "创建人ID",index = 8)
 private Long createdId;
 /**
 * 创建人
 */
 @ExcelProperty( value = "创建人",index = 9)
 private String createdBy;
 /**
 * 创建时间
 */
 @ExcelProperty( value = "创建时间",index = 10)
 private Date creationDate;
 /**
 * 创建人IP
 */
 @ExcelProperty( value = "创建人IP",index = 11)
 private String createdByIp;
 /**
 * 最后更新人ID
 */
 @ExcelProperty( value = "最后更新人ID",index = 12)
 private Long lastUpdatedId;
 /**
 * 更新人
 */
 @ExcelProperty( value = "更新人",index = 13)
 private String lastUpdatedBy;
 /**
 * 最后更新时间
 */
 @ExcelProperty( value = "最后更新时间",index = 14)
 private Date lastUpdateDate;
 /**
 * 最后更新人IP
 */
 @ExcelProperty( value = "最后更新人IP",index = 15)
 private String lastUpdatedByIp;
 /**
 * 租户ID
 */
 @ExcelProperty( value = "租户ID",index = 16)
 private String tenantId;
 /**
 * 版本号
 */
 @ExcelProperty( value = "版本号",index = 17)
 private Long version;
 /**
 * 序号
 */
 @ExcelProperty( value = "序号",index = 18)
 private Long lineNum;
 /**
 * 通知送货数量
 */
 @ExcelProperty( value = "通知送货数量",index = 19)
 private Long noticeSum;
 /**
 * 采购订单行号
 */
 @ExcelProperty( value = "采购订单行号",index = 20)
 private Long orderLineNum;
 /**
 * 拒绝原因
 */
 @ExcelProperty( value = "拒绝原因",index = 21)
 private String refusedReason;
 /**
 * 错误信息提示
 */
 @ExcelProperty( value = "错误信息提示",index = 22)
 private String errorMsg;

}