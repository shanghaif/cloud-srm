package com.midea.cloud.srm.model.perf.supplierenotice.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

/**
* <pre>
 *  21 excel导出模型
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
@ColumnWidth(15) //列宽
public class ExcelQuaSupplierEnoticeDto {
private static final long serialVersionUID = 410725L;
 /**
 * 供应商ID
 */
 @ExcelProperty( value = "供应商ID",index = 0)
 private Long vendorId;
 /**
 * 供应商名称
 */
 @ExcelProperty( value = "供应商名称",index = 1)
 private String vendorName;
 /**
 * 供应商编码
 */
 @ExcelProperty( value = "供应商编码",index = 2)
 private String vendorCode;
 /**
 * 发布日期
 */
 @ExcelProperty( value = "发布日期",index = 3)
 private Date releaseDate;
 /**
 * 单据状态
 */
 @ExcelProperty( value = "单据状态",index = 4)
 private String orderStatus;
 /**
 * 业务实体ID
 */
 @ExcelProperty( value = "业务实体ID",index = 5)
 private Long orgId;
 /**
 * 业务实体
 */
 @ExcelProperty( value = "业务实体",index = 6)
 private String orgName;
 /**
 * 采购员
 */
 @ExcelProperty( value = "采购员",index = 7)
 private String purchaserName;
 /**
 * 问题类别
 */
 @ExcelProperty( value = "问题类别",index = 8)
 private String problemType;
 /**
 * 处罚金额
 */
 @ExcelProperty( value = "处罚金额",index = 9)
 private Long fineAmount;
 /**
 * 处理依据
 */
 @ExcelProperty( value = "处理依据",index = 10)
 private String accoundingDeal;
 /**
 * 异常问题描述
 */
 @ExcelProperty( value = "异常问题描述",index = 11)
 private String exceptionDescribe;
 /**
 * 备注
 */
 @ExcelProperty( value = "备注",index = 12)
 private String noticeComments;
 /**
 * 异常问题处理结果
 */
 @ExcelProperty( value = "异常问题处理结果",index = 13)
 private String exceptionDealingResult;
 /**
 * 供方查阅时间
 */
 @ExcelProperty( value = "供方查阅时间",index = 14)
 private Date supplierReadTime;
 /**
 * 创建人ID
 */
 @ExcelProperty( value = "创建人ID",index = 15)
 private Long createdId;
 /**
 * 创建人
 */
 @ExcelProperty( value = "创建人",index = 16)
 private String createdBy;
 /**
 * 创建时间
 */
 @ExcelProperty( value = "创建时间",index = 17)
 private Date creationDate;
 /**
 * 创建人IP
 */
 @ExcelProperty( value = "创建人IP",index = 18)
 private String createdByIp;
 /**
 * 最后更新人ID
 */
 @ExcelProperty( value = "最后更新人ID",index = 19)
 private Long lastUpdatedId;
 /**
 * 更新人
 */
 @ExcelProperty( value = "更新人",index = 20)
 private String lastUpdatedBy;
 /**
 * 最后更新时间
 */
 @ExcelProperty( value = "最后更新时间",index = 21)
 private Date lastUpdatedDate;
 /**
 * 最后更新人IP
 */
 @ExcelProperty( value = "最后更新人IP",index = 22)
 private String lastUpdatedByIp;
 /**
 * 租户ID
 */
 @ExcelProperty( value = "租户ID",index = 23)
 private String tenantId;
 /**
 * 版本号
 */
 @ExcelProperty( value = "版本号",index = 24)
 private Long version;
 /**
 * 错误信息提示
 */
 @ExcelProperty( value = "错误信息提示",index = 25)
 private String errorMsg;

}