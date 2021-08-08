package com.midea.cloud.srm.model.logistics.baseprice.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

/**
* <pre>
 *  物流招标基础价格 excel导出模型
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 26, 2021 4:32:59 PM
 *  修改内容:
 * </pre>
*/

@Data
@ColumnWidth(15) //列宽
public class ExcelBasePriceDto {
private static final long serialVersionUID = 385829L;

 /**
 * 业务模式
 */
 @ExcelProperty( value = "业务模式",index = 1)
 private String businessModeCode;
 /**
 * 运输方式
 */
 @ExcelProperty( value = "运输方式",index = 2)
 private String transportModeCode;
 /**
 * 行政区域编码
 */
 @ExcelProperty( value = "行政区域编码",index = 3)
 private String regionCode;
 /**
 * 行政区域名称
 */
 @ExcelProperty( value = "行政区域名称",index = 4)
 private String regionName;
 /**
 * 区域层级编码
 */
 @ExcelProperty( value = "区域层级编码",index = 5)
 private String regionLevelCode;
 /**
 * 港口代码
 */
 @ExcelProperty( value = "港口代码",index = 6)
 private String portCode;
 /**
 * 港口中文名称
 */
 @ExcelProperty( value = "港口中文名称",index = 7)
 private String portNameZhs;
 /**
 * 港口英文名称
 */
 @ExcelProperty( value = "港口英文名称",index = 8)
 private String portNameEn;
 /**
 * LEG 字典:LEG
 */
 @ExcelProperty( value = "LEG 字典:LEG",index = 9)
 private String leg;
 /**
 * 费项 字典:CHARGE_NAME
 */
 @ExcelProperty( value = "费项 字典:CHARGE_NAME",index = 10)
 private String expenseItem;
 /**
 * 计费单位 字典:SUB_LEVEL
 */
 @ExcelProperty( value = "计费单位 字典:SUB_LEVEL",index = 11)
 private String chargeUnit;
 /**
 * 计费方式 字典:CHARGE_LEVEL
 */
 @ExcelProperty( value = "计费方式 字典:CHARGE_LEVEL",index = 12)
 private String chargeMethod;
 /**
 * 价格
 */
 @ExcelProperty( value = "价格",index = 13)
 private Long price;
 /**
 * 状态 字典:LOGISTICS_STATUS
 */
 @ExcelProperty( value = "状态 字典:LOGISTICS_STATUS",index = 14)
 private String status;
 /**
 * 创建人名字
 */
 @ExcelProperty( value = "创建人名字",index = 15)
 private String createdByName;
 /**
 * 更新人名字
 */
 @ExcelProperty( value = "更新人名字",index = 16)
 private String lastUpdatedByName;
 /**
 * ID
 */
 @ExcelProperty( value = "ID",index = 17)
 private Long signUpId;
 /**
 * 创建人ID
 */
 @ExcelProperty( value = "创建人ID",index = 18)
 private Long createdId;
 /**
 * 创建人
 */
 @ExcelProperty( value = "创建人",index = 19)
 private String createdBy;
 /**
 * 创建时间
 */
 @ExcelProperty( value = "创建时间",index = 20)
 private Date creationDate;
 /**
 * 创建人IP
 */
 @ExcelProperty( value = "创建人IP",index = 21)
 private String createdByIp;
 /**
 * 最后更新人ID
 */
 @ExcelProperty( value = "最后更新人ID",index = 22)
 private Long lastUpdatedId;
 /**
 * 更新人
 */
 @ExcelProperty( value = "更新人",index = 23)
 private String lastUpdatedBy;
 /**
 * 最后更新时间
 */
 @ExcelProperty( value = "最后更新时间",index = 24)
 private Date lastUpdateDate;
 /**
 * 最后更新人IP
 */
 @ExcelProperty( value = "最后更新人IP",index = 25)
 private String lastUpdatedByIp;
 /**
 * 租户ID
 */
 @ExcelProperty( value = "租户ID",index = 26)
 private String tenantId;
 /**
 * 版本号
 */
 @ExcelProperty( value = "版本号",index = 27)
 private Long version;
 /**
 * 错误信息提示
 */
 @ExcelProperty( value = "错误信息提示",index = 27)
 private String errorMsg;

}